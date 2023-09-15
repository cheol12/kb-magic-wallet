<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>


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
                            <br><br>
                            <a href="/personalwallet/depositForm" class="btn btn-primary">채우기</a>
                            <a href="/personalwallet/withdraw" class="btn btn-primary">꺼내기</a>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">외화별 잔액</h5>
                            <br>
                            <h5 class="card-title">달러 ${walletDetailDto.getBalance().get("USD")}</h5>
                            <h5 class="card-title">엔 ${walletDetailDto.getBalance().get("JPY")}</h5>


                        </div>
                    </div>
                </div>
            </div>

            <c:forEach var="list" items="${walletDetailDto.getList()}" varStatus="status">
                <div class="card" style="margin-top: 5px;">
                    <div class="card-header">
                            ${list.getAmount()}
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">${list.getDetail()}</h5>

                    </div>
                </div>
            </c:forEach>




        </div>


    </div>

</body>
</html>
