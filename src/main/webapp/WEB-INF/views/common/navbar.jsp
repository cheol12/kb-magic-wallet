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
            <li><a href="/personal-wallet/">개인 지갑</a></li>
            <li><a href="/group-wallet/">모임 지갑</a></li>
            <li><a href="/exchange/">환전</a></li>
            <li><a href="/saving/">적금</a></li>
            <li><a href="/">마이페이지</a></li>
            <li><a href="/register">회원가입</a></li>
            <li><a href="/">로그아웃</a></li>
        </ul>
        <h1 class="logo">요술지갑</h1>
    </div>
</nav>
</body>
</html>