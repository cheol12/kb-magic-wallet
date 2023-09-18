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

<div >


    <div class="center">
        <div class="row mb-5">
            <div class="col-md">
                <div class="card mb-3">
                    <div class="row g-0">
                        <div class="col-md-4">
                            <img class="card-img card-img-left" src="../assets/img/elements/11.jpg" alt="Card image" />
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title">개인 지갑 기능</h5>
                                <p class="card-text">
                                    무려 카드를 같이 드려요!
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row mb-5">

            <div class="col-md">
                <div class="card mb-3">
                    <div class="row g-0">
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title">모임 지갑 기능</h5>
                                <p class="card-text">
                                    돈 같이 넣고 같이 쓰자!
                                </p>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <img class="card-img card-img-right" src="../assets/img/elements/17.jpg" alt="Card image" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row mb-5">
            <div class="col-md">
                <div class="card mb-3">
                    <div class="row g-0">
                        <div class="col-md-4">
                            <img class="card-img card-img-left" src="../assets/img/elements/12.jpg" alt="Card image" />
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title">환전 기능</h5>
                                <p class="card-text">
                                    외국 갈 꺼니까~~!
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row mb-5">

            <div class="col-md">
                <div class="card mb-3">
                    <div class="row g-0">
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title">적금 혜택</h5>
                                <p class="card-text">
                                    돈 모아서 적금 들고, 그 돈으로 여행 갈래?
                                </p>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <img class="card-img card-img-right" src="../assets/img/elements/19.jpg" alt="Card image" />
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row mb-5">
            <div class="col-md">
                <div class="card mb-3">
                    <div class="row g-0">
                        <div class="col-md-4">
                            <img class="card-img card-img-left" src="../assets/img/elements/4.jpg" alt="Card image" />
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title">활용성 높은 카드</h5>
                                <p class="card-text">
                                    자신의 지갑 목록 중 원하는 지갑을 선택해서 사용 가능한 카드!
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>




    </div>



</div>


</body>
</html>
