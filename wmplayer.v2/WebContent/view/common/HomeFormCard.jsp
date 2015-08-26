<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/HomeForm.css" rel="stylesheet" />
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="${initParam.root }/js/jquery-latest.min.js"></script>
<script type="text/javascript" src="${initParam.root }/js/jquery-1.11.3.min.js"></script>
<script src="${initParam.root }/js/jsapi.js" type="text/javascript"></script>
<script src="${initParam.root }/js/ajax.js"> </script>
<script type="text/javascript">
	var titleLength = 0;
	
	function loaded(doc) {

		var today_title = doc.getElementsByTagName('title');
		var today_artist = doc.getElementsByTagName('artist');
		var today_image = doc.getElementsByTagName('image');

		var title = document.getElementById('todaymusic_title');
		var artist = document.getElementById('todaymusic_artist');

		var imgArr = new Array();
		var titleArr = new Array();
		var artistArr = new Array();
		var img_cover = new Array();

		var todaylist_home = document.getElementById("music-jacket-list-all");
		var i = 0;

		do {
			titleArr[i] = today_title.item(i).firstChild.nodeValue;
			artistArr[i] = today_artist.item(i).firstChild.nodeValue;
			imgArr[i] = today_image.item(i).firstChild.nodeValue;

			//dl父甸扁
			var dlDiv = document.createElement('dl');
			//dt
			var imgDt = document.createElement('dt');
			var titleDd = document.createElement('dd');
			var artistDd = document.createElement('dd');

			img_cover[i] = document.createElement('img');

			//img_cover 加己
			img_cover[i].setAttribute("class", "jacket");
			dlDiv.setAttribute("class", "list");
			imgDt.setAttribute("id", "image");
			titleDd.setAttribute("id", "title");
			artistDd.setAttribute("id", "artist");
			//divAll 加己 
			//divScroll 加己
			//dl, dt 
			dlDiv.appendChild(imgDt);
			dlDiv.appendChild(titleDd);
			dlDiv.appendChild(artistDd);

			imgDt.appendChild(img_cover[i]);

			//单捞磐 涝仿 
			img_cover[i].src = imgArr[i];
			titleDd.innerHTML = titleArr[i].length > 10 ? titleArr[i]
					.substring(0, 10)
					+ "..." : titleArr[i];
			artistDd.innerHTML = artistArr[i].length > 10 ? artistArr[i]
					.substring(0, 10)
					+ "..." : artistArr[i];

			todaylist_home.appendChild(dlDiv);
		} while (today_title.item(++i) != null);

		todaylist_home.style.width = imgArr.length * 120;
	}

</script>
<style>
<!--
#music-jacket-list { width:550px; height:170px; overflow-y:hidden; overflow-x:scroll;  }
#music-jacket-list-all { width:3000px; text-align:left;  }
#music-jacket-list .list { display:inline-block; width: 110px; margin-left: 10px;}

.list #image { padding: 10px 0 0 0 ; width: 100px; height: 100px;}
#music-jacket-list-all #title{margin: 0; width:  100px; font-size: 10pt; text-align: center; margin-top: 5px;}
#music-jacket-list-all #artist{margin: 0; width: 100px; font-size: 9pt; text-align: center;}
-->
</style>
<div id="music-jacket-list">
	<div id="music-jacket-list-all"></div>
</div>