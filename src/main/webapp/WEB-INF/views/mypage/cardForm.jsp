<%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-11
  Time: 오후 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>KB 모임지갑 - 카드 페이지</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css"/>

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

<script type="text/javascript">
    <%
        String alertMessage = (String) session.getAttribute("alertMessage");
        if (alertMessage != null) {
    %>
    alert('<%= alertMessage %>');
    <% session.removeAttribute("alertMessage"); %>
    <%
        }
    %>
</script>

<jsp:include page="${pageContext.request.contextPath}/WEB-INF/views/common/navbar.jsp"/>
<div class="pageWrap">
    <div class="center">
        <div class="container-xxl flex-grow-1 container-p-y">

            <!-- Basic Layout -->
            <div class="row">
                <div class="col-xl">
                    <div class="card mb-4">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <h5 class="mb-0">카드 정보</h5>
                        </div>
                        <div class="row">
                            <div class="col-xl">
                                <img src="${pageContext.request.contextPath}/assets/img/card/card${cardDesign}.png"  style="padding: 13%; width: 100%; padding-top: 0">
                            </div>

                            <div class="col-xl">
                               <h3 style="margin: 10%"> 카드 번호 : ${cardNumber.cardNumber}</h3>
                                <h3 style="margin: 10%">카드 상태 :
                                    <c:choose>
                                        <c:when test="${cardNumber.cardState == 'OK'}">사용 가능</c:when>
                                        <c:when test="${cardNumber.cardState == 'TEMPORAL_STOP'}">일시정지</c:when>
                                        <c:when test="${cardNumber.cardState == 'STOP'}">사용정지</c:when>
                                        <c:otherwise>알 수 없는 상태</c:otherwise>
                                    </c:choose>
                                </h3>
                            </div>
                        </div>
                    </div>


                </div>

            </div>
        </div>


        <div class="row mb-5">
            <div class="col-md-6 col-lg-6">
                <div class="card mb-2">
                    <div class="row g-0">

                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title">새로운 카드 신청</h5>
                                <p class="card-text">새로운 카드를 발급받고자 할 때</p>
                                <a href="${pageContext.request.contextPath}/mypage/card/new" class="btn btn-primary">신청하기</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-lg-6">
                <div class="card mb-2">
                    <div class="row g-0">

                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title">카드 일시정지</h5>
                                <p class="card-text">현재 카드가 분실상태이지만 찾을 수 있을 경우</p>
                                <a href="${pageContext.request.contextPath}/mypage/card/stop" class="btn btn-primary">신청하기</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-lg-6">
                <div class="card mb-2">
                    <div class="row g-0">

                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title">카드 정지 해제</h5>
                                <p class="card-text">일시정지 상태인 카드 정지 해제</p>
                                <a href="${pageContext.request.contextPath}/mypage/card/restart" class="btn btn-primary">신청하기</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-lg-6">
                <div class="card mb-2">
                    <div class="row g-0">

                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title">카드 영구정지</h5>
                                <p class="card-text">카드를 분실하여 찾을 수 없을 경우</p>
                                <a href="${pageContext.request.contextPath}/mypage/card/delete" class="btn btn-primary">신청하기</a>
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
