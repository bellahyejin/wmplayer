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
   String gender = user.getGender();// "����Ư���� ������"
   String email = user.getEmail();
%>
<script type="text/javascript">
   function checkPass() {
      var password = document.frm.pass.value;
      var password_check = document.frm.passcheck.value;
      if (password != password_check) {
         document.getElementById("message1").innerHTML = "��й�ȣ ����ġ";
         document.getElementById("message2").innerHTML = "";
      } else {
         document.getElementById("message1").innerHTML = "";
         document.getElementById("message2").innerHTML = "��й�ȣ ��ġ";

      }
   }     

</script>
<!-- javascript -->
<script type="text/javascript">
  
   window.onload = function () {
      
      var gen = "${ user.gender }";
         if(gen == '��'){
            document.getElementsByName('gender').item(0).checked=true;
            
         }else{
            document.getElementsByName('gender').item(1).checked=true;
            
         }
         for(i=0;i < weather.length; i++){
            if(weather[i] == '����'){
               document.getElementsByName('favor').item(0).checked = true;
            }
            if(weather[i] == '��'){
               document.getElementsByName('favor').item(1).checked = true;
            }
            if(weather[i] == '�帲'){
               document.getElementsByName('favor').item(2).checked = true;
            }
            if(weather[i] == '�ٶ�'){
               document.getElementsByName('favor').item(3).checked = true;
            }
            if(weather[i] == '��'){
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
         <td class="title_edit">��й�ȣ *</td>
         <td class="con_edit"><input class="in_text_edit" type="password"
            name="pass" value="${user.passwd }"></td>
      </tr>
      <tr>
         <td class="title_edit">��й�ȣ Ȯ�� *</td>
         <td class="con_edit"><input class="in_text_edit" type="password"
            name="passcheck" onkeyup="checkPass();" value="${user.passwd }">&nbsp;<span
            id="message1"></span><span id="message2"></span></td>
      </tr>
      <tr>
         <td class="title_edit">�� �� *</td>
         <td class="con_edit"><input class="in_text_edit" type="text"
            name="name" value="${user.name }"></td>
      </tr>
      <tr>
         <td class="title_edit">�� �� �� �� *</td>
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
         <td class="title_edit">�� �� *</td>
         <td class="con_edit"><input type="radio" name="gender" value="��">&nbsp;��&nbsp;
            <input type="radio" name="gender" value="��">&nbsp;��</td>
      </tr>
      <tr>
         <td class="title_edit">E-mail</td>
         <td class="con_edit"><input class="email_id_edit" type="text"
            name="email_id" value='${user.email.split("@")[0] }'> @ <select
            id="emailaddr" class="sel_email_edit" name="email_addr">
               <%
                  String[] domains = { "naver.com", "freechal.com", "dreamwiz.com",
                        "korea.com", "lycos.co.kr", "yahoo.co.kr", "hanmail.net",
                        "gmail.com", "paran.com", "hotmail.com", "nate.com", "�����Է�" };

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
         <td class="title_edit">�� �� *</td>
         <td class="con_edit">
            <select class="city_edit" id='city' name="city" onchange="getGugundong(this)">
               <option>=������ �ּ���</option>
            </select>
            <select class="city_edit" id='gudong' name="gudong">
               <option>=������ �ּ���</option>
            </select>
         </td>
      </tr>
      <tr>
         <td class="title_edit">�� ȣ �� �� *</td>
         <td class="con_edit"><input type="checkbox" name="favor" value="����">
            ���� <input type="checkbox" name="favor" value="��"> �� <input
            type="checkbox" name="favor" value="�帲"> �帲 <input type="checkbox"
            name="favor" value="�ٶ�"> �ٶ� <input type="checkbox" name="favor" value="��">
            ��</td>
      </tr>
      <tr>
         <td class="edit_submit" colspan="2" align="center"><input
            type="submit" class="styled-button-login" id="edit" value="����"> <input
            type="button" class="styled-button-login" id="edit" value="���"
            onclick="location.href='${initParam.root}/wmplayer/userinfo.do'">
         </td>
      </tr>
   </table>
</form>