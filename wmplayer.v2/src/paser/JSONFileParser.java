package paser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class JSONFileParser
{
	private InputStream is;

	public JSONFileParser()
	{
	}

	public boolean setUrl(String url_string)
	{
		/*
		 * try
		 * {
		 * int status_code;
		 * URL url = new URL(url_string);
		 *
		 * while ((status_code = ((HttpURLConnection)
		 * url.openConnection()).getResponseCode()) != 200)
		 * {
		 * if (status_code == 400) return false;
		 * int sleep = (int) (Math.random() * 10000) + 5000;
		 * System.out.println(status_code + " error " + sleep +
		 * " millisecond sleep");
		 * Thread.sleep(sleep);
		 * }
		 *
		 * return (is = url.openStream()) != null;
		 * }
		 * catch (InterruptedException | IOException e)
		 * {
		 * e.printStackTrace();
		 * return false;
		 * }
		 */

		Random randomGenerator = new Random();
		try
		{
			return (is = new URL(url_string).openStream()) != null;
		}
		catch (GoogleJsonResponseException e)
		{
			if (e.hashCode() == 403 && (e.getDetails().getErrors().get(0).getReason().equals("rateLimitExceeded") || e.getDetails().getErrors().get(0).getReason().equals("userRateLimitExceeded")))
			{
				// Apply exponential backoff.
				try
				{
					Thread.sleep(3 * 1000 + randomGenerator.nextInt(1001));
				}
				catch (InterruptedException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean readFile(String file_path)
	{
		try
		{
			return (is = new FileInputStream(new File(file_path))) != null;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean writeFile(String file_path)
	{
		boolean isNull = true;
		JsonObject jsonData = null;
		OutputStream os = null;

		try
		{
			isNull &= (os = new FileOutputStream(new File(file_path))) != null;

			isNull &= (jsonData = Json.createReader(is).readObject()) != null;
			Json.createWriter(os).write(jsonData);

			return isNull;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			try
			{
				os.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public List<Map<String, Object>> parser(String root_key, Map<String, Boolean> key_name)
	{
		return processNode(Json.createParser(is), "/root" + (root_key == null ? "" : root_key), key_name);
	}

	private String location_check(String key_location, List<String> array_key)
	{
		return key_location.substring(0, (array_key.size() != 0 && key_location.equals(array_key.get(array_key.size() - 1)) ? key_location.length() : key_location.lastIndexOf("/")));
	}

	private Map<String, Object> key_name_check(String key_location, String root_key, Map<String, Boolean> key_name, JsonParser node, Event event, Map<String, Object> child_data)
	{
		Object data;
		boolean flag = false;

		Set<String> keys = key_name.keySet();

		for (String key : keys)
		{
			if (flag = key_location.equals(root_key.concat(key)))
			{
				switch (event)
				{
					case VALUE_STRING :
						data = node.getString();
						break;
					case VALUE_NUMBER :
						data = node.getBigDecimal();
						break;
					case VALUE_TRUE :
						data = true;
						break;
					case VALUE_FALSE :
						data = false;
						break;
					case VALUE_NULL :
					default :
						data = "null";
				}

				if (child_data.get(key) == null)
				{
					child_data.put(key, data);
					break;
				}
				else
				{
					if (key_name.get(key)) child_data.put(key, child_data.get(key).toString() + " | " + data);
				}
			}
			else
			{
				if (flag) break;
				else continue;
			}
		}

		return child_data;
	}

	public List<Map<String, Object>> processNode(JsonParser node, String root_key, Map<String, Boolean> key_name)
	{
		List<String> array_key = new ArrayList<String>(1);
		Map<String, Object> child_data = new HashMap<String, Object>(1);
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>(1);

		Event event;
		String key_location = "/root";

		while (node.hasNext())
		{
			switch (event = node.next())
			{
				case START_ARRAY :
					array_key.add(key_location);
					break;
				case START_OBJECT :
					break;
				case KEY_NAME :
					key_location += "/" + node.getString();
					break;
				case VALUE_STRING :
				case VALUE_NUMBER :
				case VALUE_TRUE :
				case VALUE_FALSE :
				case VALUE_NULL :
					child_data = key_name_check(key_location, root_key, key_name, node, event, child_data);
					key_location = location_check(key_location, array_key);
					break;
				case END_OBJECT :
					if (key_location.equals(root_key) && child_data.size() == key_name.size() && data.indexOf(child_data) == -1)
					{
						boolean insert_flag = false;

						for (Map<String, Object> map : data)
							for (String key : key_name.keySet())
								if (key_name.get(key)) insert_flag |= map.get(key).toString().equals(child_data.get(key).toString());

						if (!insert_flag) data.add(data.size(), (HashMap<String, Object>) child_data);
						child_data = new HashMap<String, Object>();
					}

					key_location = location_check(key_location, array_key);
					break;
				case END_ARRAY :
					key_location = key_location.substring(0, key_location.lastIndexOf("/"));
					array_key.remove(array_key.size() - 1);
					break;
				default :
			}
		}

		return data;
	}
}
