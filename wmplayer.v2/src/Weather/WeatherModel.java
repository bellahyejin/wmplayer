package Weather;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import paser.JSONFileParser;
import paser.XMLFileDOMParser;

public class WeatherModel
{
	private WeatherBeans wb = new WeatherBeans();

	public WeatherModel() { }

	public void setWeatherData(String addr)
	{
		int x, y; // x�� y���� : �ñ��� �ȿ� �ִ� ���鸮�� x, y��ǥ�� ��� �� ���� ����� ��(�Ҽ��� ���� �ݿø�)

		String[] XY = setGridXY(addr).split(" ");
		x = Integer.parseInt(XY[0]);
		y = Integer.parseInt(XY[1]);

		XMLFileDOMParser xdp = XMLFileDOMParser.newInstance();
		String root_key = "wid/body/data";
		Map<CharSequence, Boolean> key_name = new HashMap<CharSequence, Boolean>();
		key_name.put("/hour", false);
		key_name.put("/day", false);
		key_name.put("/temp", false);
		key_name.put("/wfKor", false);

		if (xdp.setUrl("http://www.kma.go.kr/wid/queryDFS.jsp?gridx=" + x + "&gridy=" + y))
		{
			List<Map<CharSequence, Object>> list = xdp.parser(root_key, key_name);
			Map<CharSequence, Object> data = list.get(0);
			System.out.println(list);

			wb.setHour(Integer.parseInt(data.get("/hour").toString()));
			wb.setDay(Integer.parseInt(data.get("/day").toString()));
			wb.setTemp(Double.parseDouble(data.get("/temp").toString()));
			wb.setWeather((String) data.get("/wfKor"));
		}
	}

	public WeatherBeans getWeatherData()
	{
		return wb;
	}

	private String setGridXY(String addr)
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
		else
		{
			String mid = st.nextToken();
			for (Map<String, Object> mid_map : mid_list) if (mid.equals(mid_map.get("/value"))) mid_code = Integer.parseInt(mid_map.get("/code").toString());

			jfp.setUrl("http://www.kma.go.kr/DFSROOT/POINT/DATA/leaf." + mid_code + ".json.txt");

			if (string_size == 2)
			{
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
				leaf_key_name.put("/value", false);
				leaf_list = jfp.parser(null, leaf_key_name);

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
		}

		x = Math.round(x / size);
		y = Math.round(y / size);

		return x + " " + y;
	}
}
