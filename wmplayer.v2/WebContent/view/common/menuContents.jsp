<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>  
<link type="text/css" href="${initParam.root}/css/MainForm.css" rel="stylesheet" />  
<tiles:insertAttribute name="header"/>
<div id="section">
<tiles:insertAttribute name="section" defaultValue="homelist"/>
</div>
