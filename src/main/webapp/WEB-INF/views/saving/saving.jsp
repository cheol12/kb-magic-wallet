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
            <%----%>
            <div class="banner">
                <img src="${pageContext.request.contextPath}/images/saving/savingBanner.jpg" style="width: 1200px; height: 400px;  border-radius: 25px;" >

            </div>
        <!-- 나머지 HTML 내용 -->
            <br>
            <div class="row row-cols-1 row-cols-md-3 g-4 mb-5">
                <c:forEach var="saving" items="${savingList}" varStatus="status">
                    <div class="col">
                        <div class="card h-100">

                            <div class="card-body">

                                <h2 class="card-title text-dark"><strong>${saving.getName()}</strong></h2>
                                <hr>
                                <p class="card-text text-dark">${saving.getSavingComment()}</p>
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
                            </div>
                            <div class="d-grid gap-1 col-lg-4 mx-auto">
                                <a href="${pageContext.request.contextPath}/saving/${saving.getSavingId()}" class="btn btn-primary">상세보기</a>
                                <br>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>



        </div>
    </div>

</body>
</html>
