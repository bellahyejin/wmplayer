package youtube;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import paser.JSONFileParser;

public class Final_FindData
{
	private static final String echonest_key = "LDM4TYRGIZQNP6QOD";
	private static final String google_key = "AIzaSyALdcD4nkhqTMbfNnfnxmCv2Lhjo9uqCew";

	// private static final String lastfm_key =
	// "a1c4b896454ff0aed8f22d22cbb7b2ee";

	public void sleep(int milli)
	{
		try
		{
			Thread.sleep(milli);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public Map<String, Object> contents_replace(Map<String, Object> map, Map<String, Boolean> key_name_list)
	{
		for (String key : key_name_list.keySet())
		{
			if (key_name_list.get(key))
			{
				String str = map.get(key).toString();

				if (str.contains("&")) return new HashMap<String, Object>();
				if (str.contains("(") && str.lastIndexOf("(") != 0) str = str.substring(0, str.lastIndexOf("(") - 1);
				if (str.contains("[") && str.lastIndexOf("[") != 0) str = str.substring(0, str.lastIndexOf("[") - 1);
				if (str.contains("-") && str.lastIndexOf("-") != 0) str = str.substring(0, str.lastIndexOf("-") - 1);
				if (str.contains(",")) str = str.replace(",", ", ").replace(",  ", ", ");
				if (str.contains(".")) str = str.replace(".", "");
				if (str.contains("'")) str = str.replace("'", "&#39;");
				map.put(key, str);
			}
		}

		return map;
	}

	public Final_FindData() throws InterruptedException, IOException
	{
		int year = 2010; // ���� ����(?) �⵵
		int file_num = 0; // ���������� ���������� ����
							// "�����ڵ� ���� complete"���� ���ڸ� �����ϸ��
		int max_print = 30; // ���� �ʹ� ���� ��������� ���� �÷��� ��, ���
							// �������� �׸�ŭ �ð��� �� ����
		int print_start_idx = file_num * max_print;
		String location = "US"; // ���� �ڵ� US, UK, CA

		JSONFileParser jfp = new JSONFileParser();
		String url;
		List<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();

		List<Integer> start_index_list = new ArrayList<Integer>();
		for (int i = 0; i < 1; i++)
			start_index_list.add(i * 100);

		List<Integer> year_list = new ArrayList<Integer>();
		year_list.add(year);

		// echonest get artist_list
		List<Map<String, Object>> echonest_artist_list = new ArrayList<Map<String, Object>>();

		String echonest_artist_root_key = "/root/response/artists";

		Map<String, Boolean> echonest_artist_key_name_list = new HashMap<String, Boolean>();
		echonest_artist_key_name_list.put("/id", false);
		echonest_artist_key_name_list.put("/name", true);

		for (int start_idx : start_index_list)
		{
			url = "http://developer.echonest.com/api/v4/artist/search?api_key=" + echonest_key + "&format=json&min_hotttnesss=0.0&max_hotttnesss=1.0&sort=hotttnesss-desc&results=1&start=" + start_idx + "&artist_location=" + location;

			if (jfp.setUrl(url))
			{
				data_list = jfp.parser(echonest_artist_root_key, echonest_artist_key_name_list);

				if (data_list.size() != 0)
				{
					for (Map<String, Object> map : data_list)
						echonest_artist_list.add((HashMap<String, Object>) contents_replace(map, echonest_artist_key_name_list));
					System.out.println(year + " " + location + " " + (start_idx + data_list.size()) + " complete");
				}
				else System.out.println(year + " " + location + " not found");

			}
			else return;
		}

		if (echonest_artist_list.size() > 0)
		{
			JSONFileParser echonest_jfp = new JSONFileParser();
			JSONFileParser youtube_jfp = new JSONFileParser();

			// get music_list
			List<Map<String, Object>> echonest_music_list = new ArrayList<Map<String, Object>>();

			String echonest_music_root_key = "/root/response/songs";

			Map<String, Boolean> echonest_music_key_name_list = new HashMap<String, Boolean>();
			echonest_music_key_name_list.put("/artist_id", false);
			echonest_music_key_name_list.put("/artist_name", false); // artist
			echonest_music_key_name_list.put("/id", false); // music_id
			echonest_music_key_name_list.put("/title", true); // title
			echonest_music_key_name_list.put("/audio_summary/tempo", false); // bpm

			// echonest music_list get youtube music_url_list
			List<Map<String, Object>> youtube_music_list = new ArrayList<Map<String, Object>>();

			String youtube_music_url_root_key = "/root/items";

			Map<String, Boolean> youtube_music_url_key_name_list = new HashMap<String, Boolean>();
			youtube_music_url_key_name_list.put("/id/videoId", false); // url
			youtube_music_url_key_name_list.put("/snippet/thumbnails/high/url", false); // image
			youtube_music_url_key_name_list.put("/snippet/title", false);
			int idx = 1;
			// echonest artist_list get music_list
			for (Map<String, Object> artist : echonest_artist_list)
			{
				for (int start_idx : start_index_list)
				{
					String echonest_url = "http://developer.echonest.com/api/v4/song/search?api_key=" + echonest_key + "&format=json&song_max_hotttnesss=1&song_min_hotttnesss=0&&bucket=audio_summary&bucket=song_type&results=100&sort=song_hotttnesss-desc&start=" + start_idx + "&artist_id=" + artist.get("/id");

					if (echonest_jfp.setUrl(echonest_url))
					{
						data_list = echonest_jfp.parser(echonest_music_root_key, echonest_music_key_name_list);
						int count = 0;

						count += data_list.size();

						if (data_list.size() != 0)
						{
							for (Map<String, Object> map : data_list)
							{
								map = contents_replace(map, echonest_music_key_name_list);

								if (map.size() > 0) echonest_music_list.add((HashMap<String, Object>) map);
								else count--;
							}

							System.out.println(idx++ + " " + artist.get("/name") + " " + (count != 0 ? count : "not") + " found");
						}
						else break;

					}
				}
				for (int i = 0; i < print_start_idx; i++)
					youtube_music_list.add(new HashMap<String, Object>());

				for (int i = print_start_idx; i < echonest_music_list.size(); i++)
				{
					Map<String, Object> music = echonest_music_list.get(i);

					String artist_name = (String) music.get("/artist_name");
					String music_name = (String) music.get("/title");

					String coded_artist = null;
					String coded_music_name = null;

					int music_idx = echonest_music_list.indexOf(music);

					try
					{
						coded_artist = new String(artist_name.getBytes(), "iso-8859-1");
						coded_music_name = new String(music_name.getBytes(), "iso-8859-1");

						String youtube_url = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&maxResults=1&q=" + coded_artist.replace(" ", "%20").replace("&", "%26").replace("'", "%27").replace(";", "%3B") + "-" + coded_music_name.replace(" ", "%20").replace("&", "%26").replace("'", "%27").replace(";", "%3B") + "&key=" + google_key;

						if (youtube_jfp.setUrl(youtube_url))
						{
							data_list = youtube_jfp.parser(youtube_music_url_root_key, youtube_music_url_key_name_list);

							boolean found = false;

							if (found = (data_list.size() != 0))
							{
								for (Map<String, Object> map : data_list)
								{
									map = contents_replace(map, youtube_music_url_key_name_list);

									if (map.size() != 0) youtube_music_list.add((HashMap<String, Object>) map);
									else
									{
										echonest_music_list.set(music_idx, new HashMap<String, Object>());
										youtube_music_list.add(new HashMap<String, Object>());
									}
								}
							}
							else
							{
								echonest_music_list.set(music_idx, new HashMap<String, Object>());
								youtube_music_list.add(new HashMap<String, Object>());
							}

							System.out.println(artist_name + " " + music_name + (found ? "" : " not") + " found");
						}
						else return;

						System.out.println(youtube_music_list.size() + " " + echonest_music_list.size() + " " + (youtube_music_list.size() == echonest_music_list.size()));
						if ((youtube_music_list.size() % max_print == 0 || youtube_music_list.size() == echonest_music_list.size()) && youtube_music_list.size() > print_start_idx)
						{
							String file_name = artist_name + "-" + print_start_idx / max_print + "-" + location + ".txt";
							print_start_idx = setInsert(location, file_name, print_start_idx, max_print, youtube_music_list, echonest_music_list);
						}

						// sleep(500);
					}
					catch (UnsupportedEncodingException e1)
					{
						System.out.println("Message: " + e1.getMessage());
						System.out.println("Error : " + e1.getCause());
						e1.printStackTrace();
						return;
					}
					catch (IOException e2)
					{
						System.out.println("Message: " + e2.getMessage());
						System.out.println("Error : " + e2.getCause());
						youtube_music_list.add(new HashMap<String, Object>());
					}
				}
			}
		}
	}

	public int setInsert(String location, String file_name, int start_idx, int print_max, List<Map<String, Object>> youtube_music_list, List<Map<String, Object>> echonest_music_list)
	{
		int i = 0;
		CharSequence sb = new StringBuffer();

		List<String> insert_query = new ArrayList<String>(), title_list = new ArrayList<String>();

		for (i = start_idx; i < (start_idx + print_max > youtube_music_list.size() ? youtube_music_list.size() : start_idx + print_max); i++)
		{
			Map<String, Object> youtube_map = youtube_music_list.get(i);
			Map<String, Object> echonest_map = echonest_music_list.get(i);

			if (youtube_map.size() > 0 && echonest_map.size() > 0)
			{
				String temp = new String();
				temp = temp.concat("'" + youtube_map.get("/id/videoId") + "', " + "'" + echonest_map.get("/id") + "', ").concat("'" + echonest_map.get("/title") + "', ").concat("'" + echonest_map.get("/artist_name") + "', ").concat("'" + youtube_map.get("/snippet/thumbnails/high/url") + "', " + echonest_map.get("/audio_summary/tempo") + ", ").concat("'" + location + "'");
				title_list.add((String) youtube_map.get("/snippet/title"));
				insert_query.add(temp);
			}
			else
			{
				title_list.add(new String());
				insert_query.add(new String());
			}
		}

		for (String query : insert_query)
		{
			if (!query.equals(""))
			{
				((StringBuffer) sb).append("insert into musicInfo values (" + query + ");\n");
				((StringBuffer) sb).append(title_list.get(insert_query.indexOf(query)) + ";\n");
			}
		}

		try
		{
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(new File(file_name)));

			System.out.print(file_name);

			osw.append(sb, 0, sb.length());
			osw.close();

			System.out.println(" success");
		}
		catch (IOException e)
		{
			System.out.println("Message: " + e.getMessage());
			System.out.println("Error : " + e.getCause());
			System.out.println(" failed");
			e.printStackTrace();
		}
		return i;
	}

	public void listPrint(List<ArrayList<HashMap<String, Object>>> data, Map<String, Boolean> key_name)
	{
		for (List<HashMap<String, Object>> list : data)
			for (Map<String, Object> map : list)
				for (String key : key_name.keySet())
					System.out.println(data.indexOf(list) + " " + list.indexOf(map) + " " + key + " " + map.get(key));
	}

	public static void main(String[] args) throws InterruptedException, IOException
	{
		new Final_FindData();
	}
}
