<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link rel="stylesheet" href="http://netdna.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<link rel="stylesheet" href="${initParam.root }/css/intro.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/1.15.0/TweenMax.min.js"></script> 
${alertMsg }
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
	
	buttonclick();
	$(document).ajaxSuccess(
		buttonclick()
	);
	
	$("#passwd, #passwdcheck").keyup(function(){
			var pass = $("#passwd").val();
			var passcheck = $("#passwdcheck").val();
			var message = checkPass(pass);
			var message2 = checkPassRe(pass, passcheck)
			
			if(message != false) $('#passwd').css({'border-bottom-color':'#2ECC71'});
			else $('#passwd').css({'border-bottom-color':'#E74C3C'});
				
			if(message2 != false) $('#passwdcheck').css({'border-bottom-color':'#2ECC71'});
			else $('#passwdcheck').css({'border-bottom-color':'#E74C3C'});
	});
	
	$('#userid').hover(function(){
		$('.toolid').fadeIn();
	},function(){
		$('.toolid').fadeOut();
	});
	
	$('#passwd').hover(function(){
		$('.toolpass').fadeIn();
	},function(){
		$('.toolpass').fadeOut();
	});
	
	$('#userid').keyup(function(){
		duplicationid();
	});
});

//아이디 찾기 비밀번호 찾기 버튼
function buttonclick(){
	//id 찾기
	$('#submit-id').click(function(){
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
	//비밀번호 찾기 
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
}

//중복확인 
function duplicationid(){
	$.ajax({
		url: 'duplicationid',
		type:'post',
		data : {
			userID : $('#modal-join #userid').val()
		},
		dataType: 'text',
		success:function(data){
			if(data.toString == 'unable'){
				$('#userid').css({'border-bottom-color' : '#2ECC71'});
			}else
				$('#userid').css({'border-bottom-color' : '#E74C3C'});
		}
	});
}

// 유효성 검사 함수
function checkPass(val){
	var passRegx = /^[\d|a-z|A-Z|]{10,20}$/gi;
	var message;
	if (val == '') {
		message = false;
	} else if (passRegx.test(val)){
		message = true;
	} else {
		message = false;
	}
	return message;
}
function checkPassRe(pass, passcheck){
	var message;
	if (passcheck == '') {
		message = '';
	} else {
		if (pass == '') {
			message = true;
		} else if (pass == passcheck) {
			message = true;
		} else {
			message = false;
		}
	}
	return message;
}
//아이디 찾기 비밀번호 찾기 폼 띄우기
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
//뒤로가기
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
				<input class="input-submit" type="button" id="submit-id" value="아이디 찾기" />
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
			<input class="input-submit" type="button" id="submit-pass" value="비밀번호찾기" />
			</div>
		</div>
	</div>
	<form id="joinform" name="frmjoin" action="join" method="POST">
		<div class="modal-overlay modal-join-o"></div>
		<div class="modal modal-join">
			<a class="close-modal modal-join-b">×</a>
			<div class="icon">
				<i class="fa fa-plus-square-o"></i>
			</div>
			<h1>JOIN</h1>
			<input class="input-text-join" name="userID" type="text" id="userid"  placeholder="ID" />
			<div class="tooltip toolid">아이디는 영문, 숫자 및 8자이상<br>16자이하만 입력가능합니다</div>
			<input class="input-text-join" name="passwd" type="password" id="passwd" placeholder="Password" /> 
			<div class="tooltip toolpass">비밀번호는 영문 대소문자와 숫자,<br>특수문자를포함 10자 이상이여야 합니다.</div>
			<input class="input-text-join" name="passwdcheck" type="password" id="passwdcheck" placeholder="Password Check" />
			<input class="input-text-join" name="name" type="text" placeholder="Name" />
			<div class="birth-div">
				<span>생년월일</span><br> 
				<select class="birth" name="year">
				<c:forEach var="i" begin="20" end="115" step="1">
					<option value="${i+1900 }">${ i+1900 }</option>
				</c:forEach>
				</select> 
				<select class="birth" name="month">
				<c:forEach var="i" begin="1" end="12" step="1">
					<c:choose>
						<c:when test="${i <10 }">
							<option value="${i }">0${i }</option>
						</c:when>
						<c:otherwise>
							<option value="${ i}">${i }</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				</select> 
				<select class="birth" name="date">
				<c:forEach var="i" begin="1" end="31" step="1">
					<c:choose>
						<c:when test="${i <10 }">
							<option value="${i }">0${i }</option>
						</c:when>
						<c:otherwise>
							<option value="${ i}">${i }</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				</select> 
			</div>
			<div class="gender-div">
				<span>성 별</span><br> 
				<input type="radio" name="gender" value="남"><span>남</span>
				<input type="radio" name="gender" value="여"><span>여</span>
			</div>
			<div class="email-div">
				<span>이메일</span><br> 
				<input class="input-text" type="text" name="email_id"><span>@</span>
				<select class="sel_email" name="email_addr">
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
			<input class="input-submit" type="submit" id="submit-join" value="Plus you" />
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