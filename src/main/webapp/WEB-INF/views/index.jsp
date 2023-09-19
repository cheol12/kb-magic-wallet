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
    <link rel="stylesheet" type="text/css" href="../../css/common.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/index.css"/>

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
<!--네비게이션 바-->
<jsp:include page="common/navbar.jsp"/>

<div>
    <div class="above-container"></div>

    <div class="container">
        <!-- 사각형 1 -->
        <div class="rectangle">
            <div class="inner-content">
                <img src="${pageContext.request.contextPath}/assets/img/icons/1.png">
            </div>
            <div class="divider">
                <div class="title">
                    Sign Up
                </div>
                <div class="content">
                    Sign up for your free NEFA Wallet on web, <br> iOS or Android and follow our easy process to set up your profile
                </div>
            </div>
        </div>

        <!-- 사각형 2 -->
        <div class="rectangle">
            <div class="inner-content">
                <img src="${pageContext.request.contextPath}/assets/img/icons/2.png">
            </div>
            <div class="divider">
                <div class="title">
                    Fund
                </div>
                <div class="content">
                    Choose your preferred payment method <br>such as bank transfer or credit card to top up your NEFA Wallet
                </div>
            </div>
        </div>

        <!-- 사각형 3 -->
        <div class="rectangle">
            <div class="inner-content">
                <img src="${pageContext.request.contextPath}/assets/img/icons/3.png">
            </div>
            <div class="divider">
                <div class="title">
                    Buy Crypto
                </div>
                <div class="content">
                    Buy Bitcoin or Ethereum, then securely store it <br>in your Wallet or send it on easily to your friends anywhere
                </div>
            </div>
        </div>
    </div>


</div>


</body>
</html>
