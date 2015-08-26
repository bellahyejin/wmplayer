package Weather;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import paser.JSONFileParser;

public class AddressChangeModel
{
	private static final String naver_key = "b7444a1db91c995d24f59b427e6cca11";

	private double longi, lat;
	private boolean roadAddr;
	private String addr;
	private JSONFileParser jfp;
	private Map<String, Boolean> key_name;
	private Map<String, Object> data;

	public AddressChangeModel()
	{
		key_name = new HashMap<String, Boolean>();
	}

	private void init()
	{
		this.addr = null;
		this.longi = this.lat = 0;
		key_name.clear();
		key_name.put("/isRoadAddress", false);
	}

	// 좌표 -> 주소 roadAddr : 옛날 주소는 false, 요즘 주소는 true
	public void setGrid(double longi, double lat, boolean roadAddr, boolean weather_flag, int detailAddr) throws MalformedURLException, IOException, InterruptedException
	{
		init();
		getData(Double.toString(this.longi = longi) + "," + Double.toString(this.lat = lat), roadAddr, false, weather_flag, detailAddr);
	}

	// 주소 -> 좌표, 자세한 주소 get_flag : 좌표는 true, 자세한 주소는 false, weather_flag : 기상청 주소로 변환시 true, 아니면 false
	public void setAddr(String addr, boolean get_flag, boolean weather_flag) throws MalformedURLException, IOException, InterruptedException
	{
		setAddr(addr, get_flag, weather_flag, 4);
	}

	// detailAddr : 4이면 모든 주소, 3이면 동면까지, 2이면 시구군까지, 1이면 시도만
	public void setAddr(String addr, boolean get_flag, boolean weather_flag, int detailAddr) throws MalformedURLException, IOException, InterruptedException
	{
		init();
		if (detailAddr <= 4 && detailAddr >= 1) getData(this.addr = addr, false, get_flag, weather_flag, detailAddr);
	}

	private void getData(String query, boolean roadAddr, boolean get_flag, boolean weather_flag, int detailAddr) throws MalformedURLException, IOException, InterruptedException
	{
		jfp = new JSONFileParser();

		try
		{
			query = URLEncoder.encode(query, "utf-8");

			if (jfp.setUrl("http://openapi.map.naver.com/api/" + (this.addr == null ? "reverse" : "") + "geocode?key=" + naver_key + "&encoding=utf-8&coord=latlng&output=json&query=" + query))
			{
				if (this.addr == null) getRegionAddress(jfp, weather_flag, roadAddr, detailAddr);
				else
				{
					if (get_flag) getNumberAddress(jfp);
					else getRegionAddress(jfp, weather_flag, roadAddr, detailAddr);
				}
			}
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}

	private void getNumberAddress(JSONFileParser jfp)
	{
		String root_key = "/result/items";

		key_name.put("/point/x", false);
		key_name.put("/point/y", false);

		List<HashMap<String, Object>> data_list = jfp.parser(root_key, key_name);

		data = data_list.get(0);

		longi = ((BigDecimal) data.get("/point/x")).doubleValue();
		lat = ((BigDecimal) data.get("/point/y")).doubleValue();
	}

	private void getRegionAddress(JSONFileParser jfp, boolean weather_flag, boolean roadAddr, int detailAddress)
	{
		String root_key = "/result/items";

		key_name.put("/addrdetail/sido", false);
		key_name.put("/addrdetail/sigugun", false);
		key_name.put("/addrdetail/dongmyun", false);
		key_name.put("/addrdetail/rest", false);

		List<HashMap<String, Object>> data_list = jfp.parser(root_key, key_name);

		addr = new String();

		Map<String, Object> data = data_list.get(roadAddr ? 1 : 0);

		switch (detailAddress)
		{
			case 4 :
				addr = data.get("/addrdetail/rest") + " " + addr;
			case 3 :
				addr = data.get("/addrdetail/dongmyun") + " " + addr;
			case 2 :
				String sigugun = data.get("/addrdetail/sigugun").toString();

				if (weather_flag)
				{
					String temp[] = sigugun.split(" ");
					sigugun = "";
					for (int i = 0; i < temp.length; i++) sigugun += temp[i];
				}

				addr = sigugun + " " + addr;
			case 1 :
				addr = data.get("/addrdetail/sido") + " " + addr;
		}

		roadAddr = Boolean.parseBoolean(data.get("/isRoadAddress").toString());
	}

	public boolean isRoadAddr() // 좌표 아닌 주소를 받아올때 길주소면 true, 옛날주소면 false
	{
		return roadAddr;
	}

	public String getAddr()
	{
		return addr;
	}

	public double getLongitude()
	{
		return longi;
	}

	public double getLatitude()
	{
		return lat;
	}
}
