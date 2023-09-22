<%--<jsp:useBean id="predictions" scope="request" type="java.lang.String"/>--%>
<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2023-09-22
  Time: 오후 12:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>환율 예측 테스트</title>
</head>
<body>
<h1>환율 예측</h1>
<form action="${pageContext.request.contextPath}/exchange/exchangePrediction" method="post">
    <input type="submit" value="예측 시작">
</form>
<p>예측 결과: ${predictions}</p>
</body>
</html>
