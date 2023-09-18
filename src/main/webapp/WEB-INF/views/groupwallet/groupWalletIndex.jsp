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
    <title>Title</title>

    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</head>
<body>
<header>
    <jsp:include page="../common/navbar.jsp"/>
</header>



<main>
    <div class="pageWrap">


        <div class="center">
            <div class="d-flex align-items-end row">
                <div class="col-sm-7">
                    <div class="card-body">
                        <h1 class="card-title text-break" >${member.name}님이 참여 중인 모임지갑이에요 🎉</h1>
                        <p class="mb-4"></p>

                    </div>
                </div>
                <div class="col-sm-5 text-center text-sm-left">
                    <div class="card-body pb-0 px-0 px-md-4">
                        <img src="../assets/img/illustrations/man-with-laptop-light.png" height="140" alt="View Badge User" data-app-dark-img="illustrations/man-with-laptop-dark.png" data-app-light-img="illustrations/man-with-laptop-light.png">
                    </div>
                </div>
            </div>
            <div>

                <c:forEach var="list" varStatus="status" items="${gWalletList}">


                    <div style="margin-top: 5px">
                        <div class="card">
                            <div class="card-header">
                                    ${list.getNickname()}
                            </div>
                            <div class="card-body">
                                <h5 class="card-title">원화 잔액 : ${list.getBalance()}</h5>
                                <a href="${pageContext.request.contextPath}/group-wallet/${list.getGroupWalletId()}" class="btn btn-primary">상세보기</a>
                            </div>
                        </div>
                    </div>

                </c:forEach>
                <section>
                    <div style="margin-top: 5px">
                        <div class="card">
                            <div class="card-header">
                                새로운 모임 지갑 생성
                            </div>
                            <div class="card-body">
                                <h5 class="card-title"></h5>
                                <a href="${pageContext.request.contextPath}/group-wallet/new" class="btn btn-primary">생성하기</a>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div>

    </div>
</main>


<footer>

</footer>
</body>
</html>
