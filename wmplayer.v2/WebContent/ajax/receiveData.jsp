<?xml version='1.0' encoding='euc-kr'?>
<%@page import="kr.co.wmplayer.sinmina.model.dto.music.MusicInfoDTO"%>
<%@page import="Weather.WeatherModel"%>
<%@page import="Weather.AddressChangeModel"%>
<%@page import="kr.co.wmplayer.sinmina.dao.music.MusicDAO"%>
<%@ page language="java" contentType="text/xml; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%
	double current_latitude = Double.valueOf(request.getParameter("cur_Latitude")).doubleValue();
	double current_longitude = Double.valueOf(request.getParameter("cur_Longitude")).doubleValue();
	
	
	MusicDAO dao = new MusicDAO();
	
	AddressChangeModel addr = new AddressChangeModel();
	addr.setGrid(current_longitude, current_latitude, false, true, 2);
	System.out.print("la : "+current_latitude+", long : "+current_longitude);
	
	String current_addr = addr.getAddr();
	WeatherModel wm = new WeatherModel();
	
	wm.setWeatherData(current_addr);
	
	double current_temper = wm.getWeatherData().getTemp();
	String current_weather = wm.getWeatherData().getWeather();
	List<MusicInfoDTO> lists = dao.selectTodayList(current_temper);
	
	System.out.println("addr : "+ current_addr+", temper:" +current_temper+", lists: "+lists.get(1).getTitle());
	
	StringBuffer str = new StringBuffer();
	
	//str.append("<?xml version='1.0' encoding='euc-kr'?>");
	str.append("<todaymusic>");
	str.append("<currentLocation>"+current_addr+"</currentLocation>");
	str.append("<currentTemper>"+current_temper+"</currentTemper>");
	str.append("<currentWeather>"+current_weather+"</currentWeather>");
	for (MusicInfoDTO dto : lists) {
		str.append("<music>");
		str.append("<musicID><![CDATA[" + dto.getMusicID()
				+ "]]></musicID>");
		str.append("<title><![CDATA[" + dto.getTitle() + "]]></title>");
		str.append("<artist><![CDATA[" + dto.getArtist()
				+ "]]></artist>");
		str.append("<image><![CDATA[" + dto.getImage() + "]]></image>");
		str.append("<bpm><![CDATA[" + dto.getBpm() + "]]></bpm>");
		str.append("<location><![CDATA[" + dto.getLocation()
				+ "]]></location>");
		str.append("</music>");
	}
	str.append("</todaymusic>");

	out.write(str.toString());
%>
