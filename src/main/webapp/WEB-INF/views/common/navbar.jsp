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
    <!-- Icons. Uncomment required icon fonts -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/fonts/boxicons.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/fonts/boxicons.css"/>

    <!-- Core CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/css/core.css" class="template-customizer-core-css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/css/theme-default.css" class="template-customizer-theme-css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/demo.css"/>

    <!-- Vendors CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css"/>

    <!-- Page CSS -->

    <!-- Helpers -->
    <script src="${pageContext.request.contextPath}/assets/vendor/js/helpers.js"></script>

    <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
    <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
    <script src="${pageContext.request.contextPath}/assets/js/config.js"></script>

</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light" style="font-family: NanumSquareNeo-Variable,serif">
    <div class="container-fluid">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <a id="logo" class="navbar-brand" href="${pageContext.request.contextPath}/">KBMW</a>
        <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
            <c:if test="${not empty sessionScope.member}">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 ">
                <li class="nav-item">
                    <a class="nav-link active menus" href="${pageContext.request.contextPath}/personalwallet/main">개인지갑</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active menus" href="${pageContext.request.contextPath}/group-wallet/">모임지갑</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active menus" href="${pageContext.request.contextPath}/exchange/" tabindex="-1">환전</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active menus" href="${pageContext.request.contextPath}/saving/" tabindex="-1">적금</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active menus" href="${pageContext.request.contextPath}/mypage/main" tabindex="-1">마이페이지</a>
                </li>
                </c:if>

                <c:if test="${empty sessionScope.member}"><ul class="navbar-nav me-auto mb-2 mb-lg-0 "></ul></c:if>
            </ul>
            <form class="d-flex" onsubmit="return false" method="get">
                <c:if test="${empty sessionScope.member}">
                <button class="btn rounded-pill btn-primary button-spacing" type="submit"
                        onclick="location.href='${pageContext.request.contextPath}/login'" style="font-size: 20px">로그인</button>
                <button class="btn rounded-pill btn-outline-primary" type="submit"
                        onclick="location.href='${pageContext.request.contextPath}/register'" style="font-size: 20px">회원가입</button>
                </c:if>
                <c:if test="${not empty sessionScope.member}">
                    <button class="btn rounded-pill btn-primary" type="submit"
                            onclick="location.href='${pageContext.request.contextPath}/logout'" style="font-size: 20px">로그아웃</button>
                </c:if>
            </form>
        </div>
    </div>
</nav>
</body>
</html>