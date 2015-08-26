<%@page import="kr.co.wmplayer.sinmina.dao.user.UserInfoDAO"%>
<%@page import="kr.co.wmplayer.sinmina.model.dto.user.UserInfoDTO"%>
<%@page import="kr.co.wmplayer.sinmina.interfaces.WMPlayerDAO"%>
<%@page import="Weather.AddressChangeModel"%>
<%@page import="Weather.WeatherModel"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<link rel="stylesheet" href="${initParam.root }/css/style.css">
<link type="text/css" href="${initParam.root}/css/global.css"
	rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/MusicPlayer.css"
	rel="stylesheet" />
 <script type="text/javascript" src="${initParam.root }/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${initParam.root }/js/jquery-latest.min.js"></script>
<script src="${initParam.root }/js/jsapi.js" type="text/javascript"></script>
<script src="${initParam.root }/js/ajax.js" type="text/javascript"></script>
<script type="text/javascript">
      google.load("swfobject", "2.1");
    </script>
<script type="text/javascript">
//data send
var params = '';
window.onload=function (){
	getLocation_cord();	   
}
/*���� ��ġ ã�� */
function getLocation_cord()
{
	if (navigator.geolocation)
    {
        navigator.geolocation.getCurrentPosition(showPosition);
    }
    else
    {
        $("#location").html("Geolocation is not supported by this browser.");
    }
}
function showPosition(position)
{
	var cur_Latitude = Math.floor(position.coords.latitude*100)/100; //����
   	var cur_Longitude = Math.floor(position.coords.longitude*100)/100; //�浵
	params  += 'cur_Latitude='+cur_Latitude+'&cur_Longitude='+cur_Longitude;
	$.ajax({
		url: '${initParam.root}/ajax/receiveData.jsp',
   		type: 'POST',
   		data : params,
   		cache: false,
   		success : function(doc){
   			printData(doc);
   			parent.section.loaded(doc);
   		}
	});
   	
}

	//index
    var curIdx = 13;
    var nexIdx = 0;
    var autoNext = 0;
    //play list ����
	var playlist = new Array();
	var playinfo = new Array();
	var playimage = new Array();
	var listLength = 0;
    // ��ü ����
	 //�ݹ��Լ�
	//��� ��� ����
	
	function printData(doc) {
		/* var doc = xhr.responseXML; */

		var weather_text = doc.getElementsByTagName('currentWeather');
		var location = doc.getElementsByTagName('currentLocation');
		var temper = doc.getElementsByTagName('currentTemper');
		var musicID = doc.getElementsByTagName('musicID');
		var title = doc.getElementsByTagName('title');
		var artist = doc.getElementsByTagName('artist');
		var image = doc.getElementsByTagName('image');

		//������ ���� div
		var play_title = document.getElementById('slidetitle');
		var doc_location = document.getElementById('location');
		var doc_weathertxt = document.getElementById('weathertext');
		var doc_temper = document.getElementById('temper');
		var doc_weathericon = document.getElementById('weathericon');

		var weathertxt_value = weather_text.item(0).firstChild.nodeValue;

		if (weathertxt_value.includes('����')) {
			doc_weathericon.className = "icon-sun icon-weather";
		} else if (weathertxt_value.includes('�帲')) {
			doc_weathericon.className = "icon-cloudy2 icon-weather";
		} else if (weathertxt_value.includes('��')) {
			doc_weathericon.className = "icon-rainy2 icon-weather";
		} else if (weathertxt_value.includes('��')) {
			doc_weathericon.className = "icon-snowy3 icon-weather";
		} else if (weathertxt_value.includes('����')) {
			doc_weathericon.className = "icon-cloudy icon-weather";
		}

		for (var i = 0; i < musicID.length; i++) {
			playlist[i] = musicID.item(i).firstChild.nodeValue;
			playinfo[i] = title.item(i).firstChild.nodeValue + ' - '
					+ artist.item(i).firstChild.nodeValue;
			playimage[i] = image.item(i).firstChild.nodeValue;
		}
		doc_location.innerHTML = location.item(0).firstChild.nodeValue;
		doc_weathertxt.innerHTML = weathertxt_value;
		doc_temper.innerHTML = temper.item(0).firstChild.nodeValue;
		listLength = playlist.length - 1;
		curIdx = Math.floor(Math.random() * listLength + 1);
		play_title.innerHTML = playinfo[curIdx];
		document.albumcover.src = playimage[curIdx];
		loadPlayer(playlist[curIdx]);
		likeselect(playlist[curIdx]);
	}

	function mix(curIndex) {
		var tempList = null;
		var tempInfo = null;
		var tempImage = null;
		var msg = "";
		var msg2 = "";
		// ���� ����ϴ� ���� �������� ���������������� ���� ���� */
		for (var i = curIndex + 1; i < listLength; i++) {

			var random = Math.floor((Math.random() * listLength) + 1);
			if (i == random) {
				random--;
			}
			//list
			tempList = playlist[i];
			playlist[i] = playlist[random];
			playlist[random] = tempList;

			//info
			tempInfo = playinfo[i];
			playinfo[i] = playinfo[random];
			playinfo[random] = tempInfo;

			//tempImage
			tempImage = playimage[i];
			playimage[i] = playimage[random];
			playimage[random] = tempImage;
		}
	}
	var pre = 0;
	function nextPlay() {
		//������ ���� div
		var play_title = document.getElementById('slidetitle');
		mix(curIdx);
		nexIdx = curIdx + 1;
		play_title.innerHTML = playinfo[nexIdx];
		document.albumcover.src = playimage[nexIdx];
		/* loadPlayer(playlist[nexIdx]); */
		ytplayer.loadVideoById(playlist[nexIdx]);
		curIdx = nexIdx;
	 	likeselect(playlist[curIdx]);

	}
	function prePlay() {
		//������ ���� div
		var preIdx = curIdx - 1;
		var play_title = document.getElementById('slidetitle');

		play_title.innerHTML = playinfo[preIdx];
		document.albumcover.src = playimage[preIdx];
		/* loadPlayer(playlist[preIdx]); */
		ytplayer.loadVideoById(playlist[preIdx]);
		curIdx = preIdx;
	 	likeselect(playlist[curIdx]);

	}
	/*
	 * Chromeless player has no controls.
	 */
	// Update a particular HTML element with a new value
	function updateHTML(elmId, value) {
		var current = 0;
		var duration = 0;
		var favorTime = 0;
		if (elmId == 'playerState' && value == 0) {
			nextPlay();
		} else if (elmId == 'videoDuration' || elmId == 'videoCurrentTime') {
			document.getElementById(elmId).innerHTML = value;
		}/*  else if (elmId == 'favorDuration') {
			duration = value;
			favorTime = (current + duration) * 0.8;
			/*        	alert('current : '+current+", duration :"+duration+', favor : '+ favorTime)
	  }  */
	}

	// This function is called when an error is thrown by the player
	function onPlayerError(errorCode) {
		//alert("An error occured of type:" + errorCode);
		if (errorCode == 150) {
			loadPlayer(playlist[curIdx]);
		}
	}

	// This function is called when the player changes state
	function onPlayerStateChange(newState) {
		updateHTML("playerState", newState);
	}

	// Display information about the current state of the player
	function updatePlayerInfo() {
		// Also check that at least one function exists since when IE unloads the
		// page, it will destroy the SWF before clearing the interval.

		var duration = ytplayer.getDuration();
		var currentTime = ytplayer.getCurrentTime();
		var durMin = parseInt(duration / 60);
		var durSec = parseInt(duration % 60);
		var curMin = parseInt(currentTime / 60);
		var curSec = parseInt(currentTime % 60);
		var favorTime = (currentTime + duration) * 0.8;
		//timebarIncrease(intCur, intDur);
		if (durMin < 10) {
			durMin = '0' + durMin;
		}
		if (durSec < 10) {
			durSec = '0' + durSec
		}

		if (curMin < 10) {
			curMin = '0' + curMin;
		}

		if (curSec < 10) {
			curSec = '0' + curSec;
		}
		if (ytplayer && ytplayer.getDuration) {
			/* updateHTML("favorDuration", duration);
			updateHTML("favorCurrentTime", currentTime); */
			updateHTML("videoDuration", durMin + " : " + durSec);
			updateHTML("videoCurrentTime", curMin + " : " + curSec);

			document.changer.style.width = (280 / ytplayer.getDuration())
					* ytplayer.getCurrentTime();
		}

	}

	var cnt = 0; // ����߰� �Ͻ����� ǥ��
	// Allow the user to set the volume from 0-100

	function playVideo() {
		if (ytplayer) {
			ytplayer.playVideo();
		}
	}

	function pauseVideo() {
		if (ytplayer) {
			ytplayer.pauseVideo();
			cnt = 0;
		}
	}
	function muteVideo() {
		if (ytplayer) {
			ytplayer.mute();
		}
	}

	function unMuteVideo() {
		if (ytplayer) {
			ytplayer.unMute();
		}
	}

	// This function is automatically called by the player once it loads
	function onYouTubePlayerReady(playerId) {
		ytplayer = document.getElementById("ytPlayer");
		// This causes the updatePlayerInfo function to be called every 250ms to
		// get fresh data from the player
		setInterval(updatePlayerInfo, 250);
		updatePlayerInfo();//���� ��ȯ
		ytplayer.addEventListener("onStateChange", "onPlayerStateChange");
		ytplayer.addEventListener("onError", "onPlayerError");

		//ytplayer.cueVideoById(playerId);
	}
	// The "main method" of this sample. Called when someone clicks "Run".
	function loadPlayer(playmusic) {
		// Lets Flash from another domain call JavaScript
		// ������ �Ӽ� ����
		var params = {
			allowScriptAccess : "always"
		};
		// The element id of the Flash embed
		var atts = {
			id : "ytPlayer"
		}; //���̵�
		// All of the magic handled by SWFObject (http://code.google.com/p/swfobject/)
		swfobject
				.embedSWF(
						"http://www.youtube.com/v/"
								+ playmusic
								+ "?version=3&rel=0&autoplay=1&enablejsapi=1&playerapiid=ytplayer&",
						"videoDiv", "0", "0", "8", null, null, params, atts);
	}

	//��� , �Ͻ����� �̹��� ��ȯ
	function imgChange() {
		if (cnt == 0) {
			document.getElementById("play").className = "icon-size-medium icon-play3 icon";
			pauseVideo();
			cnt = 1;
		} else {
			document.getElementById("play").className = "icon-size-medium icon-pause2 icon";
			playVideo();
			cnt = 0;
		}
	}
</script>
<script type="text/javascript">
/* Like function jquery ajax*/
var status ;
var toggle = 1;
$(document).ready(function() {
	$('.like').on('click',function(event) {
		if(toggle == 1){
			status = 'add';
			var dataSend = 'musicid='+playlist[curIdx]+'&status='+status;
			$.ajax({
					url: '${initParam.root}/ajax/MusicLikeData.jsp',
					type: 'POST',
					data: dataSend,
					cache: false,
					success : function(data){
						alert("Add::Data : " + data+", status : "+status+", toggle : "+ toggle);
					}
				});
			$('#like').css('color','#81d4fa');
			toggle=0;
			status = 'delete';
		}else{
			status = 'delete';
			var dataSend = 'musicid='+playlist[curIdx]+'&status='+status;
			$.ajax({
					url: '${initParam.root}/ajax/MusicLikeData.jsp',
					type: 'POST',
					data: dataSend,
					cache: false,
					success : function(data){
						alert("Delete::Data : " + data+", status : "+status+", toggle : "+ toggle);

					}
				});
			$('#like').css('color','#ffffff');
			toggle=1;
			status = 'add';
		}
	});
});
/* select */
var response = '';
function likeselect(playlist){
	status = 'select';
	var dataSend = 'musicid='+playlist+'&status='+status;
	$.ajax({
		url: '${initParam.root}/ajax/MusicLikeData.jsp',
		type: 'POST',
		data: dataSend,
		dataType:'text',
		cache: false,
		success : function(data){
			/* alert("select0::Data : "+data+", "+status+", "+toggle); */
			response = data;
		},
		error: function(xhr, status, error){
			alert("Error :: "+xhr+", Status :: "+status)
		}
	});
	
	/* alert(response); */
	/* if(response == 'like'){
		alert('like');
	}else if(response == 'unlike'){
		alert('unlike');
	}else {
		alert('error::::');
	} */
}

</script>

<div id="nav">
	<%
	UserInfoDAO dao = new UserInfoDAO();
	String userID = (String) session.getAttribute("success");
	UserInfoDTO user = dao.select(userID);
	
	System.out.print("Name::"+user.getName());
	%>
	<div id="profile">
		<%
			if(user.getGender().equals("��")){ %>
		<img id="gender" src="${ initParam.root}/img/button/gender_wman.png"
			width="35px" height="35px">
		<%}else{ %>
		<img id="gender" src="${ initParam.root}/img/button/gender_woman.png"
			width="35px" height="35px">
		<%} %>
		<div id="text">
			<span id="login_id">
			<%if(userID == null){%>�Խ�Ʈ<%}
			else if(userID.equals("admin")){%> ������ <%}
			else{%> <%=user.getName() %><%} %></span>
			 <span id="fix">��</span>
		</div>
			<button class="like"><i class="icon icon-heart icon-size-small" id="like"></i></button>
		<div id="videoDiv"></div>
	</div>
	<div id="album_cover">
		<img id="album_image" name="albumcover"
			src="${ initParam.root}/img/button/loading.png">
	</div>
	<div class="player-all">
		<div id="title_player">
			<marquee scrollamount="5" behavior="scroll" width="260"
				id="slidetitle">Loading...</marquee>
		</div>
		<div id="player">
			<a class="pre icon-top-margin" href="javascript: void(0)" onclick="prePlay()">
				<i class="icon-size-small icon-backward2 icon"></i>
			</a>
			<a class="play " href="javascript: void(0)" onclick="imgChange()">
				<i class="icon-size-medium icon-pause2 icon" id="play"></i>
			</a>
			<a class="next icon-top-margin" href="javascript: void(0)" onclick="nextPlay()">
				<i class="icon-size-small icon-forward3 icon"></i>
			</a>
		</div>
		<div id="timeline">
			<div id="timebar">
				<!-- <img src="" width="100%" /> -->
				<img id="timechange" name="changer"
					src="${initParam.root }/img/button/timebar.png">
			</div>
			<div id="time">
				<span id="videoCurrentTime">--:--</span> <span id="playerState"
					style="display: block">--</span> <span id="videoDuration">--:--</span>
			</div>
		</div>
	</div>
	<div id="weather">
		<div class="weather-margin">
			<div id="loc_icon">
				<i class="icon-location2 icon-size-small icon"></i><span id="location"></span>
			</div>
			<div id="temp">
				<i class="icon-thermometer icon-size-xsmall "></i><span id="temper"></span>��
			</div>
		</div>
		<div class="weathericon">
			<i class="" id="weathericon"></i>
			<%-- <% if(wm.getWeatherData().getWeather().equals("����")){ %>
			<i class="icon-sun icon-weather"></i>
			<%}else if(wm.getWeatherData().getWeather().equals("�帲")){ %>
			<i class="icon-cloudy2 icon-weather"></i>
			<%}else if(wm.getWeatherData().getWeather().contains("��")){ %>
			<i class="icon-rainy2 icon-weather"></i>
			<%}else if(wm.getWeatherData().getWeather().contains("��")){ %>
			<i class="icon-snowy3 icon-weather"></i>
			<%}else if(wm.getWeatherData().getWeather().contains("����")){ %>
			<i class="icon-cloudy icon-weather"></i>
			<%} %> --%>
		</div>
		<div id="weathertext">
		</div>
	</div>
</div>
<!-- #nav -->
