<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-11
  Time: 오후 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Navbar</title>
    <link rel="stylesheet" type="text/css" href="../../../css/navbar.css" />
</head>
<body>
<nav class="navbar">
    <div class="navbar-container container">
        <ul class="menu-items">
            <c:if test="${empty sessionScope.member}">


                <li><a href="${pageContext.request.contextPath}/register">회원가입</a></li>

            </c:if>

            <c:if test="${not empty sessionScope.member}">
                <li><a href="${pageContext.request.contextPath}/personalwallet/main">개인 지갑</a></li>
                <li><a href="${pageContext.request.contextPath}/group-wallet/">모임 지갑</a></li>
                <li><a href="${pageContext.request.contextPath}/exchange/">환전</a></li>
                <li><a href="${pageContext.request.contextPath}/saving/">적금</a></li>
                <li><a href="${pageContext.request.contextPath}/mypage/main">마이페이지</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">로그아웃</a></li>
            </c:if>
        </ul>

        <h1 class="logo">요술지갑</h1>
    </div>
</nav>
</body>
</html>