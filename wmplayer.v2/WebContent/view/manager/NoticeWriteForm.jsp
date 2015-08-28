<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="${initParam.root }/js/ajax.js"></script>
<link type="text/css" href="${initParam.root}/css/global.css" rel="stylesheet" />
<link type="text/css" href="${initParam.root}/css/ColumnForm.css" rel="stylesheet" />
<script type="text/javascript" src="../../js/ajax.js"></script>
<form action="noticeadd" method="POST" name="frm">
<div class="noticewrite-form">
   <div class="noticewrite-header">Notice Editor</div>
   <div class="noticewrite-section">
         <table>
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
               <input type="submit" class="styled-button-login" id="column" value="제출" />
                  <input type="button" class="styled-button-login" id="column" value="취소" onclick="location.href='notice'"/></td>
            </tr>
         </table>
   </div>
</div>
</form>