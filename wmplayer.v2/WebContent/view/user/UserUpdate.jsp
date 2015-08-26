<%@page import="kr.co.wmplayer.sinmina.model.dto.user.UserInfoDTO"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
   pageEncoding="EUC-KR"%>
<script type="text/javascript" src="${initParam.root}/js/ajax.js"></script>
<link type="text/css" href="${initParam.root}/css/global.css"
   rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/UserUpdate.css"
   rel="stylesheet" />
<%
   UserInfoDTO user = (UserInfoDTO) request.getAttribute("user");
    System.out.println("USER:"+user);
   String password = user.getPasswd();
   String birth = user.getBirth();
   String birthArr[] = birth.split("/");
   String gender = user.getGender();// "서울특별시 도봉구"
   String email = user.getEmail();
%>
<script type="text/javascript">
   function checkPass() {
      var password = document.frm.pass.value;
      var password_check = document.frm.passcheck.value;
      if (password != password_check) {
         document.getElementById("message1").innerHTML = "비밀번호 불일치";
         document.getElementById("message2").innerHTML = "";
      } else {
         document.getElementById("message1").innerHTML = "";
         document.getElementById("message2").innerHTML = "비밀번호 일치";

      }
   }     

</script>
<!-- javascript -->
<script type="text/javascript">
  
   window.onload = function () {
      
      var gen = "${ user.gender }";
         if(gen == '남'){
            document.getElementsByName('gender').item(0).checked=true;
            
         }else{
            document.getElementsByName('gender').item(1).checked=true;
            
         }
         for(i=0;i < weather.length; i++){
            if(weather[i] == '맑음'){
               document.getElementsByName('favor').item(0).checked = true;
            }
            if(weather[i] == '비'){
               document.getElementsByName('favor').item(1).checked = true;
            }
            if(weather[i] == '흐림'){
               document.getElementsByName('favor').item(2).checked = true;
            }
            if(weather[i] == '바람'){
               document.getElementsByName('favor').item(3).checked = true;
            }
            if(weather[i] == '눈'){
               document.getElementsByName('favor').item(4).checked = true;
            }
         }
</script>
<form name="frm" method="post" id="edit-form" action='${initParam.root}/wmplayer/update/edit.do?action=update&id=${success}'>
   <legend>Update</legend>
   <br>
   
   <table>
      <tr>
         <td class="title_edit">I D *</td>
         <td class="con_edit"><input class="in_text_edit" type="text"
            name="id" disabled="disabled"
            value="<%=session.getAttribute("success")%>"></td>
      </tr>
      <tr>
         <td class="title_edit">비밀번호 *</td>
         <td class="con_edit"><input class="in_text_edit" type="password"
            name="pass" value="${user.passwd }"></td>
      </tr>
      <tr>
         <td class="title_edit">비밀번호 확인 *</td>
         <td class="con_edit"><input class="in_text_edit" type="password"
            name="passcheck" onkeyup="checkPass();" value="${user.passwd }">&nbsp;<span
            id="message1"></span><span id="message2"></span></td>
      </tr>
      <tr>
         <td class="title_edit">이 름 *</td>
         <td class="con_edit"><input class="in_text_edit" type="text"
            name="name" value="${user.name }"></td>
      </tr>
      <tr>
         <td class="title_edit">생 년 월 일 *</td>
         <td class="con_edit"><select id="birthyear" class="brith_edit"
            name="year">
               <%
                  for (int i = 20; i <= 115; i++) {
                     int year = 1900 + i;
                     if (birthArr[0].equals(year + ""))
                        out.print("<option value=" + year + " selected>" + year
                              + "</option>");
                     else
                        out.print("<option value=" + year + ">" + year
                              + "</option>");
                  }
               %>
         </select> <select id="birthmonth" class="brith_edit" name="month">
               <%
                  for (int i = 1; i <= 12; i++) {
                     String month = (i < 10 ? "0" : "") + i;
                     if (birthArr[1].equals(month))
                        out.print("<option value=" + month + " selected>" + month
                              + "</option>");
                     else
                        out.print("<option value=" + month + ">" + month
                              + "</option>");
                  }
               %>
         </select> <select id="birthdate" class="brith_edit" name="date">
               <%
                  for (int i = 1; i <= 31; i++) {
                     String date = (i < 10 ? "0" : "") + i;
                     if (birthArr[2].equals(date))
                        out.print("<option value=" + date + " selected>" + date
                              + "</option>");
                     else
                        out.print("<option value=" + date + ">" + date
                              + "</option>");
                  }
               %>
         </select></td>
      </tr>
      <tr>
         <td class="title_edit">성 별 *</td>
         <td class="con_edit"><input type="radio" name="gender" value="남">&nbsp;남&nbsp;
            <input type="radio" name="gender" value="여">&nbsp;여</td>
      </tr>
      <tr>
         <td class="title_edit">E-mail</td>
         <td class="con_edit"><input class="email_id_edit" type="text"
            name="email_id" value='${user.email.split("@")[0] }'> @ <select
            id="emailaddr" class="sel_email_edit" name="email_addr">
               <%
                  String[] domains = { "naver.com", "freechal.com", "dreamwiz.com",
                        "korea.com", "lycos.co.kr", "yahoo.co.kr", "hanmail.net",
                        "gmail.com", "paran.com", "hotmail.com", "nate.com", "직접입력" };

                  for (int i = 0; i < domains.length; i++) {
                     out.print("<option value=\"" + domains[i] + "\"");
                     if (domains[i].equals(email.split("@")[1])) {
                        out.print(" selected");
                     }
                     out.print(">" + domains[i] + "</option>\n");
                  }
               %>
         </select></td>
      </tr>
      <tr>
         <td class="title_edit">주 소 *</td>
         <td class="con_edit">
            <select class="city_edit" id='city' name="city" onchange="getGugundong(this)">
               <option>=선택해 주세요</option>
            </select>
            <select class="city_edit" id='gudong' name="gudong">
               <option>=선택해 주세요</option>
            </select>
         </td>
      </tr>
      <tr>
         <td class="title_edit">선 호 날 씨 *</td>
         <td class="con_edit"><input type="checkbox" name="favor" value="맑음">
            맑음 <input type="checkbox" name="favor" value="비"> 비 <input
            type="checkbox" name="favor" value="흐림"> 흐림 <input type="checkbox"
            name="favor" value="바람"> 바람 <input type="checkbox" name="favor" value="눈">
            눈</td>
      </tr>
      <tr>
         <td class="edit_submit" colspan="2" align="center"><input
            type="submit" class="styled-button-login" id="edit" value="수정"> <input
            type="button" class="styled-button-login" id="edit" value="취소"
            onclick="location.href='${initParam.root}/wmplayer/userinfo.do'">
         </td>
      </tr>
   </table>
</form>