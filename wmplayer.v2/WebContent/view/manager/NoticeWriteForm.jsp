<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%request.setCharacterEncoding("UTF-8"); %>
<script type="text/javascript" src="${initParam.root }/js/ajax.js"></script>
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/ColumnForm.css" rel="stylesheet" />
<script type="text/javascript" src="../../js/ajax.js"></script>
<script type="text/javascript">
 function noticeInput(){
    var title = document.frm.title.value;
    var contents = document.frm.contents.value;
      
   var params = '';
   params += 'title='+title;
   params += '&contents='+contents;
   alert(params);
   sendRequest('../view/manager/Notice.jsp', params, callback, 'POST')
 }
 
 function callback(){
      if (xhr.readyState == 4) {
         if (xhr.status == 200) {
         }
      }
 }
</script>   

         <form method="POST" name="frm">
<div class="noticewrite-form">
   <div class="noticewrite-header">Notice Editor</div>
   <div class="noticewrite-section">
         <table width="70%">
            <tr>
               <td width="30%"><span class="noticewrite-title">제목</span></td>
               <td align="right"><input type="text" size="60%" name='title'>
               </td>
            </tr>
            <tr>
               <td colspan="2"><span class="noticewrite-title">내용</span></td>
            </tr>
            <tr>
               <td colspan="2"><textarea rows="20%" cols="70%" style="resize: none;" name="contents"></textarea></td>
            </tr>
            <tr>
               <td colspan="2" align="center" id="btn_no">
               <input type="submit" class="styled-button-login" id="column" value="제출" onclick="noticeInput()"/> 
                  <input type="button" class="styled-button-login" id="column" value="수정" onclick="location.href='noticelist.do'"/></td>
            </tr>
         </table>
   </div>
</div>
</form>