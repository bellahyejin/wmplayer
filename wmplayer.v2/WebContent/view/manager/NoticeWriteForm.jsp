<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
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
               <td width="30%"><span class="noticewrite-title">力格</span></td>
               <td align="right"><input type="text" size="60%" name='title'>
               </td>
            </tr>
            <tr>
               <td colspan="2"><span class="noticewrite-title">郴侩</span></td>
            </tr>
            <tr>
               <td colspan="2"><textarea rows="20%" cols="70%" style="resize: none;" name="contents"></textarea></td>
            </tr>
            <tr>
               <td colspan="2" align="center" id="btn_no">
               <input type="submit" class="styled-button-login" id="column" value="力免" />
                  <input type="button" class="styled-button-login" id="column" value="秒家" onclick="location.href='notice'"/></td>
            </tr>
         </table>
   </div>
</div>
</form>