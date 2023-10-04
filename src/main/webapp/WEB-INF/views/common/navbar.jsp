<%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-11
  Time: 오후 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ko">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css"/>
</head>
<nav class="navbar navbar-expand-lg navbar-light bg-light" style="font-family: NanumSquareNeo-Variable,serif">
    <div class="container-fluid">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo03"
                aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <a id="logo" class="navbar-brand" href="${pageContext.request.contextPath}/">KBMW</a>
        <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
            <c:if test="${not empty sessionScope.member}">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 ">
                <li class="nav-item">
                    <a class="nav-link active menus"
                       href="${pageContext.request.contextPath}/personalwallet/main">개인지갑</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active menus" href="${pageContext.request.contextPath}/group-wallet/">모임지갑</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active menus" href="${pageContext.request.contextPath}/exchange/" tabindex="-1">환전</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active menus" href="${pageContext.request.contextPath}/saving/"
                       tabindex="-1">적금</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active menus" href="${pageContext.request.contextPath}/mypage/main"
                       tabindex="-1">마이페이지</a>
                </li>
                </c:if>

                <c:if test="${empty sessionScope.member}">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0 "></ul>
                </c:if>
            </ul>
            <form class="d-flex" onsubmit="return false" method="get">
                <c:if test="${empty sessionScope.member}">
                    <button class="btn rounded-pill btn-outline-primary button-spacing" type="submit"
                            onclick="location.href='${pageContext.request.contextPath}/register'"
                            style="font-size: 20px">
                        회원가입
                    </button>
                    <button class="btn rounded-pill btn-primary button-spacing" style="font-size: 20px"
                            id="loginModalOpen">
                        로그인
                    </button>
                </c:if>
                <c:if test="${not empty sessionScope.member}">
                    <button class="btn rounded-pill btn-primary" type="submit"
                            onclick="location.href='${pageContext.request.contextPath}/logout'" style="font-size: 20px">
                        로그아웃
                    </button>
                </c:if>
            </form>
        </div>
    </div>
</nav>