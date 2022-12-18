<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>j.jsp</title>
</head>
<body>
<h1> j.jsp입니다. </h1>
<hr>
<%String result = (String)request.getAttribute("result"); %>
<%=result %>
</body>
</html>