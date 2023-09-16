<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-11
  Time: 오후 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>깨비의 요술 지갑 - 개인지갑</title>

    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>

    <!-- Icons. Uncomment required icon fonts -->
    <link rel="stylesheet" href="../../../assets/vendor/fonts/boxicons.css"/>

    <!-- Core CSS -->
    <link rel="stylesheet" href="../../../assets/vendor/css/core.css" class="template-customizer-core-css"/>
    <link rel="stylesheet" href="../../../assets/vendor/css/theme-default.css" class="template-customizer-theme-css"/>
    <link rel="stylesheet" href="../../../assets/css/demo.css"/>

    <!-- Vendors CSS -->
    <link rel="stylesheet" href="../../../assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css"/>

    <!-- Page CSS -->

    <!-- Helpers -->
    <script src="../../../assets/vendor/js/helpers.js"></script>
    <script src="../../../assets/js/validation.js"></script>
    <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
    <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
    <script src="../../../assets/js/config.js"></script>

</head>
<body>
<jsp:include page="/WEB-INF/views/common/navbar.jsp"/>

<div class="pageWrap">

    <div class="center">
        <div class="row">
            <div class="col-sm-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">개인 지갑</h5>
                        <br>
                        <h5> 원화 ${walletDetailDto.balance.get("KRW")}₩ </h5>
                        <br><br>
                        <a href="/personalwallet/depositForm" class="btn btn-primary">채우기</a>
                        <a href="/personalwallet/withdrawForm" class="btn btn-primary">꺼내기</a>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">외화별 잔액</h5>
                        <br>
                        <h5 class="card-title">달러 ${walletDetailDto.getBalance().get("USD")}$</h5>
                        <h5 class="card-title">엔 ${walletDetailDto.getBalance().get("JPY")}￥</h5>


                    </div>
                </div>
            </div>
        </div>


        <div class="card">
            <h5 class="card-header">거래 내역</h5>
            <div class="table-responsive text-nowrap">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i>거래일자</th>
                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i>거래시간</th>
                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i>출금()</th>
                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i>입금()</th>
                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i>내용</th>
                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i>잔액()</th>
                    </tr>
                    </thead>
                    <c:forEach var="list" items="${walletDetailDto.getList()}" varStatus="status">
                        <tbody class="table-border-bottom-0">
                        <tr>
                            <fmt:parseDate value="${ list.getDateTime() }" pattern="yyyy-MM-dd'T'HH:mm"
                                           var="parsedDateTime" type="both"/>
                            <td><i class="fab fa-angular fa-lg text-danger me-3"></i> <fmt:formatDate
                                    value="${parsedDateTime}" pattern="yyyy-MM-dd"/></td>
                            <td><i class="fab fa-angular fa-lg text-danger me-3"></i><fmt:formatDate
                                    value="${parsedDateTime}" pattern="hh:mm:ss"/></td>
                            <td><i class="fab fa-angular fa-lg text-danger me-3"></span></td>
                        </tr>
                        </tbody>
                    </c:forEach>
                </table>
            </div>
        </div>
        <!--/ Striped Rows -->

        <c:forEach var="list" items="${walletDetailDto.getList()}" varStatus="status">
            <div class="card" style="margin-top: 5px;">
                <div class="card-header">
                        ${list.getDateTime()} ${list.getType()}
                </div>
                <div class="card-body">
                    <h5 class="card-title">${list.getAmount()}</h5>
                        ${list.getDetail()}
                </div>
            </div>
        </c:forEach>


    </div>


</div>
<div id="incomeChart"></div>
<div class="d-flex justify-content-center pt-4 gap-2">
    <div class="flex-shrink-0">
        <div id="expensesOfWeek"></div>
    </div>
    <div>
        <p class="mb-n1 mt-1">Expenses This Week</p>
        <small class="text-muted">$39 less than last week</small>
    </div>
</div>
</body>
</html>
