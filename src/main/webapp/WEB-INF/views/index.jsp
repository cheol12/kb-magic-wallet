<%--
  Created by IntelliJ IDEA.
  User: 김진형
  Date: 2023-09-01
  Time: 오후 3:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>깨비의 요술 지갑 - 메인</title>
    <link rel="stylesheet" type="text/css" href="../../css/loginform.css" /><!--로그인 폼 css-->
    <link rel="stylesheet" type="text/css" href="../../css/common.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/index.css"/>

</head>
<body>
<!--네비게이션 바-->
<jsp:include page="common/navbar.jsp"/>

<div class ="pageWrap">

    <div class="mainBanner">
        <div class="bannerImg">
            <h3>모두의 여행 통장!</h3>
            <h1>깨비의 요술 지갑</h1>

        </div>
        <!--로그인 폼-->
        <form>
            <div class="group">
                <input type="text"><span class="highlight"></span><span class="bar"></span>
                <label>ID</label>
            </div>
            <div class="group">
                <input type="email"><span class="highlight"></span><span class="bar"></span>
                <label>PASSWORD</label>
            </div>
            <button type="button" class="button buttonBlue">Subscribe
                <div class="ripples buttonRipples"><span class="ripplesCircle"></span></div>
            </button>
        </form>
        <!--/로그인 폼-->

    </div>

    <!--메인 버튼 4개-->
    <div id="mainPageTab">
        <button class="mainTabButton">
            개인 지갑
        </button>
        <button class="mainTabButton">
            모임 지갑
        </button>
        <button class="mainTabButton">
            환전
        </button>
        <button class="mainTabButton" onclick="location.href='saving/savingMain.html'">
            적금
        </button>

    </div>
    <!--/메인 버튼 4개-->

</div>

</body>
</html>
