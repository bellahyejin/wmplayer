<?xml version='1.0' encoding='euc-kr'?>
<%@page import="kr.co.wmplayer.sinmina.model.dto.music.MusicInfoDTO"%>
<%@page import="Weather.WeatherModel"%>
<%@page import="Weather.AddressChangeModel"%>
<%@page import="kr.co.wmplayer.sinmina.dao.music.MusicDAO"%>
<%@ page language="java" contentType="text/xml; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- <%

	//str.append("<?xml version='1.0' encoding='euc-kr'?>");
	str.append("<todaymusic>");
	str.append("<currentLocation>${current_addr}</currentLocation>");
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
%> --%>
<todaymusic>
<currentLocation>${current_addr}</currentLocation>
<currentTemper>${wm.weatherData.temp }</currentTemper>
<currentWeather>${wm.eatherData.weather}</currentWeather>
<c:forEach items="${lists }" var="todaylist" >
	<music>
		<musicID><![CDATA[${todaylist.musicID}]]></musicID>
		<title><![CDATA[${todaylist.title}]]></title>
		<artist><![CDATA[${todaylist.artist}]]></artist>
		<image><![CDATA[${todaylist.image}]]></image>
		<bpm><![CDATA[${todaylist.bpm}]]></bpm>
		<location><![CDATA[${todaylist.location}]]></location>
	</music>
</c:forEach>
</todaymusic>
