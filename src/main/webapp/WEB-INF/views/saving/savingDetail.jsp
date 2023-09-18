<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>깨비의 요술 지갑 - 적금</title>
    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>

    <!-- Core CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/css/core.css" class="template-customizer-core-css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/css/theme-default.css" class="template-customizer-theme-css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/demo.css" />

    <!-- Vendors CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css" />

    <!-- Page CSS -->

    <!-- Helpers -->
    <script src="${pageContext.request.contextPath}/assets/vendor/js/helpers.js"></script>

    <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
    <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
    <script src="${pageContext.request.contextPath}/assets/js/config.js"></script>

    <!--배너용 css-->
    <script src="${pageContext.request.contextPath}/css/heroBanner.css"></script>


<%--    <link rel="stylesheet" type="text/css" href="/css/common.css">--%>
<%--    <link rel="stylesheet" type="text/css" href="/css/saving.css">--%>
<%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">--%>
<%--    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>--%>

</head>
<body>
<jsp:include page="/WEB-INF/views/common/navbar.jsp"/>

<div class="pageWrap">
    <div class="center">


        <div class="card">
            <div class="card-header">
                <h1 class="card-title text-dark"><strong>${saving.getName()}</strong></h1>
            </div>
            <div class="card-body">
                <h5 class="card-title">
                    <img src="${pageContext.request.contextPath}/images/saving/interestRate.svg">
                    금리 : ${saving.getInterestRate()} %
                </h5>
                <h5 class="card-title">
                    <img src="${pageContext.request.contextPath}/images/saving/amountLimit.svg">
                    최대 한도 : ${saving.getAmountLimit()} 원
                </h5>
                <h5 class="card-title">
                    <img src="${pageContext.request.contextPath}/images/saving/period.svg">
                    가입 기한 : ${saving.getPeriod()} 개월
                </h5>
                <h2 class="card-text text-dark">${saving.getSavingComment()}</h2>
                <a href="${pageContext.request.contextPath}/saving/${saving.getSavingId()}/form" class="btn btn-primary">신청하기</a>
            </div>
        </div>
    </div>

</div>

</body>
</html>
