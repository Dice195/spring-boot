<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>i.jsp</title>
</head>
<body>
<h1> i.jsp 입니다.</h1>
<%
String result = (String)request.getAttribute("result");
%>
<%=result%>

</body>
</html>