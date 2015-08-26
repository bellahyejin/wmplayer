package Weather;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import paser.JSONFileParser;
import paser.XMLParser;

public class WeatherModel
{
	private WeatherBeans wb = new WeatherBeans();

	public WeatherModel() { }

	public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {
		WeatherModel a = new WeatherModel();
		a.setWeatherData("서울특별시 중랑구 망우3동");
		System.out.println(a.getWeatherData().getTemp());
		System.out.println(a.getWeatherData().getWeather());
	}

	public void setWeatherData(String addr) throws MalformedURLException, IOException, InterruptedException // ����� �ּ�(������ ip�� ������ �˰� �;�����)
	{
		int x, y; // x�� y���� : �ñ��� �ȿ� �ִ� ���鸮�� x, y��ǥ�� ��� �� ���� ����� ��(�Ҽ��� ���� �ݿø�)
		ArrayList<WeatherBeans> weather = new ArrayList<WeatherBeans>();

		String[] XY = setGridXY(addr).split(" ");
		x = Integer.parseInt(XY[0]);
		y = Integer.parseInt(XY[1]);

		XMLParser xp = new XMLParser("http://www.kma.go.kr/wid/queryDFS.jsp?gridx=" + x + "&gridy=" + y);
		NodeList list = xp.getDocument().getElementsByTagName("data");

		for (int i = 0; i < list.getLength(); i++) // �ʿ��� �κи� �̱�
		{
			NodeList items = list.item(i).getChildNodes();
			WeatherBeans temp = new WeatherBeans();

			for (int j = 0; j < items.getLength(); j++)
			{
				Node item = items.item(j);

				switch (j)
				{
					case 1 : // ���� �ð�(3�ð� ����)
						temp.setHour(Integer.parseInt(item.getTextContent()));
						break;
					case 3 :
						temp.setDay(Integer.parseInt(item.getTextContent()));
						break;
					case 5 :
						temp.setTemp(Double.parseDouble(item.getTextContent()));
						break;
					case 15 :
						temp.setWeather(item.getTextContent());
				}
			}
			weather.add(temp);
		}

		wb = weather.get(0);
	}

	public WeatherBeans getWeatherData()
	{
		return wb;
	}

	private String setGridXY(String addr) throws MalformedURLException, IOException, InterruptedException // ���û ���� ��ǥ ���ϱ�
	{
		if (addr == null) throw new NullPointerException();

		int x = 0, y = 0, size = 0;
		StringTokenizer st = new StringTokenizer(addr, " ");
		int string_size = st.countTokens();

		JSONFileParser jfp = new JSONFileParser();

		Map<String, Boolean> top_key_name = new HashMap<String, Boolean> ();
		top_key_name.put("/code", false);
		top_key_name.put("/value", false);
		
		jfp.setUrl("http://www.kma.go.kr/DFSROOT/POINT/DATA/top.json.txt");
		List<HashMap<String, Object>> top_list = jfp.parser(null, top_key_name);

		int top_code = 0;
		String top = st.nextToken();
		for (Map<String, Object> top_map : top_list) if (top.equals(top_map.get("/value"))) top_code = Integer.parseInt(top_map.get("/code").toString());

		Map<String, Boolean> mid_key_name = new HashMap<String, Boolean> ();
		mid_key_name.put("/code", false);
		mid_key_name.put("/value", false);

		jfp.setUrl("http://www.kma.go.kr/DFSROOT/POINT/DATA/mdl." + top_code + ".json.txt");
		List<HashMap<String, Object>> mid_list = jfp.parser(null, mid_key_name);

		int mid_code = 0;
		List<HashMap<String, Object>> leaf_list;
		Map<String, Boolean> leaf_key_name = new HashMap<String, Boolean>();
		leaf_key_name.put("/x", false);
		leaf_key_name.put("/y", false);

		if (string_size == 1)
		{
			for (Map<String, Object> mid_map : mid_list)
			{
				jfp.setUrl("http://www.kma.go.kr/DFSROOT/POINT/DATA/leaf." + mid_map.get("/code") + ".json.txt");
				leaf_list = jfp.parser(null, leaf_key_name);

				for (Map<String, Object> leaf_map : leaf_list)
				{
					x += Integer.parseInt(leaf_map.get("/x").toString());
					y += Integer.parseInt(leaf_map.get("/x").toString());
				}

				size += leaf_list.size();
			}
		}
		else if (string_size == 2)
		{
			String mid = st.nextToken();
			for (Map<String, Object> mid_map : mid_list) if (mid.equals(mid_map.get("/value"))) mid_code = Integer.parseInt(mid_map.get("/code").toString());

			jfp.setUrl("http://www.kma.go.kr/DFSROOT/POINT/DATA/leaf." + mid_code + ".json.txt");
			leaf_list = jfp.parser(null, leaf_key_name);

			for (Map<String, Object> leaf_map : leaf_list)
			{
				x += Integer.parseInt(leaf_map.get("/x").toString());
				y += Integer.parseInt(leaf_map.get("/y").toString());
			}

			size += leaf_list.size();
		}
		else
		{
			String mid = st.nextToken();
			for (Map<String, Object> mid_map : mid_list) if (mid.equals(mid_map.get("/value"))) mid_code = Integer.parseInt(mid_map.get("/code").toString());

			leaf_key_name.put("/value", false);

			jfp.setUrl("http://www.kma.go.kr/DFSROOT/POINT/DATA/leaf." + mid_code + ".json.txt");
			leaf_list = jfp.parser("", leaf_key_name);

			String leaf = st.nextToken();
			for (Map<String, Object> leaf_map : leaf_list)
			{
				if (leaf.equals(leaf_map.get("/value")))
				{
					x = Integer.parseInt(leaf_map.get("/x").toString());
					y = Integer.parseInt(leaf_map.get("/y").toString());
				}
			}

			size = 1;
		}

		x = Math.round(x / size);
		y = Math.round(y / size);

		return x + " " + y;
	}
}
