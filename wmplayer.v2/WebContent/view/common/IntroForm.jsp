<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<link rel="stylesheet" href="${initParam.root }/css/intro.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script> 
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/1.15.0/TweenMax.min.js"></script> 
<script>
$(document).ready(function(){
	$('#loginforom').css("display","none");

	var $obj=$(".modal-login")
		,$obj1 = $('.modal-join')
		,$obj2 = $(".modal-findid")
  		,$overlay=$(".modal-login-o")
  		,$overlay1=$('.modal-join-o')
    	,blur=$("#blur-filter").get(0);
	//blur
	function setBlur(v){
		blur.setAttribute("stdDeviation", v);
	}
	  //position
	function getPosLogin(){
	    return $obj.position();
	}
	function getPosJoin(){
		return $obj1.position();
	}
  
	  /* login */
	var lastPos=getPosLogin();
	function update(){
	    var pos=getPosLogin();
	    var limit=20;
	    var dx=Math.min(limit,Math.abs(pos.left-lastPos.left)*0.5);
	    var dy=Math.min(limit,Math.abs(pos.top-lastPos.top)*0.5);
	    setBlur(dx+","+dy);
	    
	    lastPos=pos;
	    requestAnimationFrame(update);
	}
	  
	var lastPos1 = getPosJoin();
	function update1(){
	    var pos=getPosJoin();
	    var limit=20;
	    var dx=Math.min(limit,Math.abs(pos.left-lastPos1.left)*0.5);
	    var dy=Math.min(limit,Math.abs(pos.top-lastPos1.top)*0.5);
	    setBlur(dx+","+dy);
	    
	    lastPos1=pos;
	    requestAnimationFrame(update1);
	}
	
	//update
	update();
	update1();
	  
	var isOpen=false;
	function openModalLogin(){
	    $overlay.css({
	      display:"block"
	    });
	    TweenMax.to($overlay,0.1,{autoAlpha:1});
	    
	    TweenMax.fromTo($obj,0.6,{y:-($(window).height()+$obj.height())},{delay:0.2,y:"0%",ease:Elastic.easeOut,easeParams:[1.1,0.7],force3D:true});
	}
	function openModalJoin(){
	    $overlay1.css({
	      display:"block"
	    });
	    TweenMax.to($overlay1,0.1,{autoAlpha:1});
	    
	    TweenMax.fromTo($obj1,0.6,{y:-($(window).height()+$obj1.height())},{delay:0.2,y:"0%",ease:Elastic.easeOut,easeParams:[1.1,0.7],force3D:true});
	}
	function closeModalLogin(){
	  TweenMax.to($overlay,0.1,{delay:0.55,autoAlpha:0});
	  TweenMax.to($obj,0.55,{y:$(window).height()+$obj.height(),ease:Back.easeIn,force3D:true});
	}
	function closeModalJoin(){
	  TweenMax.to($overlay1,0.1,{delay:0.55,autoAlpha:0});
	  TweenMax.to($obj1,0.55,{y:$(window).height()+$obj1.height(),ease:Back.easeIn,force3D:true});
	}
	
	$(".login").click(function(){
		openModalLogin();
	    $("#loginform").css("display","block");
	 });
	$(".join").click(function(){
	    openModalJoin();
	});
	  
	$(".close-modal,.modal-login-o, .modal-join-o").click(function(){
	   	closeModalLogin();
	   	closeModalJoin()
	   	$("#findid").css("display","none");
	   	$("#findpass").css("display","none");
	});
	
	
	$('#submit-id').click(function(){
		alert($("#findid #name").val())
		$.ajax({
			url:'findId',
			type:'post',
			data: {
				name : $("#findid #name").val(), 
				email : $("#findid #email").val()
			},
			success:function(data){
				$('#findid .finddata').html(data);
			}			
		});
	});
	
	$('#submit-pass').click(function(){
		$.ajax({
			url:'findPass',
			type:'post',
			data: {
				userID : $("#findpass #userID").val(), 
				name : $("#findpass #name").val(), 
				email : $("#findpass #email").val()
			},
			success:function(data){
				$('#findpass .finddata').html(data);
			}			
		});
	});
	
});

function findid(){
	var loginform = document.getElementById("loginform");
	var idform = document.getElementById("findid")
	
	idform.style.display = 'block';
	loginform.style.display = 'none';
}
function findpass(){
	var loginform = document.getElementById("loginform");
	var passform = document.getElementById("findpass")
	
	passform.style.display = 'block';
	loginform.style.display = 'none';
}
function moveback(){
	var loginform = document.getElementById("loginform");
	var idform = document.getElementById("findid");
	var passform = document.getElementById("findpass");
	
	passform.style.display = 'none';
	idform.style.display = 'none';
	loginform.style.display = 'block';
}

</script>
</head>
<body>
	<div class="top">
		<div class="center introbox">
			<ul>
				<li class="logo">WMPlayer
				<li class="slogun">If you want, Enjoy our player
			</ul>
		</div>
		<div class="btn">
			<input type='button' class="styled-button login" value='Login'>
			<input type='button' class="join styled-button" value='Join'>
		</div>
	</div>
		<div class="modal-overlay modal-login-o"></div>
		<div class="modal modal-login">
		<form name="frm" action='login' method="POST">
			<div id="loginform">
				<a class="close-modal modal-join-b">×</a>
				<div class="icon">
					<i class="fa fa-lock"></i>
				</div>
				<h1>LOGIN</h1>
				<input class="input-text" type="text" name="userID" placeholder="ID" />
				<input class="input-text" type="password" name="passwd" placeholder="Password" />
				<div class="finddata">
					<a class="findid" href="#" onclick="findid()">아이디 찾기</a>
					<a class="findpass" href="#" onclick="findpass()">비밀번호 찾기</a>
				</div>
				<input class="input-submit" type="submit" id="submit-login" value="Start Player" />
			</div>
		</form>
		<div id="findid">
			<a class="close-modal modal-join-b">×</a>
			<div class="icon">
				<i class="fa fa-info-circle"></i>
			</div>
			<h2>아이디 찾기</h2>
			<input class="input-text" type="text" id="name" name="name" placeholder="Name" /> 
			<input class="input-text" type="text" id="email" name="email" placeholder="Email은 @와 함께 써주세요" />
			<div class="finddata">
				<input class="input-submit" type="submit" id="submit-id" value="아이디 찾기" />
			</div>
		</div>
		<div id="findpass">
			<a class="close-modal modal-join-b">×</a>
			<div class="icon">
				<i class="fa fa-info-circle"></i>
			</div>
			<h2>비밀번호 찾기</h2>
			<input class="input-text" type="text" id="userID" name="userID" placeholder="ID" />
			<input class="input-text" type="text" id="name" name="name" placeholder="Name" />
			<input class="input-text" type="text" id="email" name="email" placeholder="Email은 @와 함께 써주세요" />
			<div class="finddata">
			<input class="input-submit" type="submit" id="submit-pass" value="비밀번호찾기" />
			</div>
		</div>
	</div>
	<form id="joinform" name="frmjoin" action="" method="get">
		<div class="modal-overlay modal-join-o"></div>
		<div class="modal modal-join">
			<a class="close-modal modal-join-b">×</a>
			<div class="icon">
				<i class="fa fa-plus-square-o"></i>
			</div>
			<h1>JOIN</h1>
			<input class="input-text-join" type="text" placeholder="ID" /> <input
				class="input-text-join" type="password" placeholder="Password" /> <input
				class="input-text-join" type="password" placeholder="Password Check" />
			<div class="birth">
				<span>생년월일</span> <select class="birth" name="year">
					<%
						for (int i = 20; i <= 115; i++) {
							int year = 1900 + i;
							out.print("<option>" + year + "</option>");
						}
					%>
				</select> <select class="birth" name="month">
					<%
						for (int i = 1; i <= 12; i++) {
							String month = (i < 10 ? "0" : "") + i;
							out.print("<option value=" + month + ">" + (i < 10 ? "0" : "")
									+ i + "</option>");
						}
					%>
				</select> <select class="birth" name="date">
					<%
						for (int i = 1; i <= 31; i++) {
							String date = (i < 10 ? "0" : "") + i;
							out.print("<option value=" + date + ">" + (i < 10 ? "0" : "")
									+ i + "</option>");
						}
					%>
				</select> <input class="input-submit" type="submit" id="submit-join"
					value="Plus you" />
			</div>
			<div class="gender">
				<span>성 별</span> <input type="radio" name="gender" value="남">&nbsp;남&nbsp;
				<input type="radio" name="gender" value="여">&nbsp;여
			</div>
			<div class="email">
				<span>이메일</span> <input class="email_id" type="text" name="email_id"> @ <select class="sel_email" name="email_addr">
					<option>naver.com</option>
					<option>freechal.com</option>
					<option>dreamwiz.com</option>
					<option>korea.com</option>
					<option>lycos.co.kr</option>
					<option>yahoo.co.kr</option>
					<option>hanmail.net</option>
					<option>gmail.com</option>
					<option>paran.com</option>
					<option>hotmail.com</option>
					<option>nate.com</option>
					<option>직접입력</option>
				</select>
			</div>
		</div>
	</form>
	<svg xmlns="http://www.w3.org/2000/svg" version="1.1" class="filters">
	<defs> 
	<filter id="blur"> 
		<feGaussianBlur id="blur-filter" in="SourceGraphic" stdDeviation="15,15" /> 
	</filter> 
	</defs> 
	</svg>
</body>
</html>