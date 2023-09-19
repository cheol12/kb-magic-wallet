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
    <div class="banner-container">
    <div class="above-container">
        <div id="first-box" class="box">
            <div class="first-contents">
                <h1>KB<p id="first-text">MAGIC WALLET</p><p id="second-text">깨비의 요술지갑</p></h1>
                <p>Experience hassle-free foreign currency top-ups with our Foreign currency recharge wallet service
                </p>
                <div class="btnClass">
                    <div class="BtnAboveClass">
                    <button type="button" class="btn rounded-pill btn-outline-primary">
                        <span class="tf-icons bx bx-pie-chart-alt me-1"></span>Primary
                    </button>
                    <button type="button" class="btn rounded-pill btn-outline-primary">
                        <span class="tf-icons bx bx-pie-chart-alt me-1"></span>Primary
                    </button>
                    </div>
                    <div class="BtnBottomClass">
                    <button type="button" class="btn rounded-pill btn-outline-primary">
                        <span class="tf-icons bx bx-pie-chart-alt me-1"></span>Primary
                    </button>
                    <button type="button" class="btn rounded-pill btn-outline-primary">
                        <span class="tf-icons bx bx-pie-chart-alt me-1"></span>Primary
                    </button>
                    </div>
                </div>
            </div>
        </div>
        <div id="second-box" class="box">
            <img src="${pageContext.request.contextPath}/assets/img/icons/4.png">
        </div>

    </div>
    </div>

    <div class="container">
        <div class="text-above-rectangles">
            <h2>카드 한장으로</h2>
            <h2>떠나는 세계여행</h2>
            <div class="explainDiv">
            <p>깨비의 요술지갑과 함께 세계를 누비며</p>
            <p>놀랍도록 간편하고 안전한 여행을 누리세요.</p></div>
        </div>
        <!-- 사각형 1 -->
        <div class="rectangles">
        <div class="rectangle">
            <div class="inner-content">
                <img src="${pageContext.request.contextPath}/assets/img/icons/1.png">
            </div>
            <div class="divider">
                <div class="title">
                    Exchange
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
                    Wallet
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
                    Savings
                </div>
                <div class="content">
                    Buy Bitcoin or Ethereum, then securely store it <br>in your Wallet or send it on easily to your friends anywhere
                </div>
            </div>
        </div>
        </div>
    </div>


</div>


</body>
</html>
