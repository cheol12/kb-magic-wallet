<%@ page import="kb04.team02.web.mvc.common.dto.LoginMemberDto" %><%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-11
  Time: 오후 11:18
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
<%
    Object obj = session.getAttribute("member");
    LoginMemberDto member = (LoginMemberDto) obj;
%>
<jsp:include page="../common/navbar.jsp"/>
<div class="pageWrap">
    <div class="center">

        <div class="card mb-3">
            <div class="row g-0">
                <div class="col-md-4">
                    <img src="/images/kb_logo.png" class="img-fluid rounded-start" alt="...">
                </div>
                <div class="col-md-8">
                    <div class="card-body">
                        <h5 class="card-title"><%out.print(member.getName());%></h5>

                    </div>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="row">
                <div class="col">
                    <div class="card" style="width: 18rem;">
                        <img src="/images/kb_logo.png" class="card-img-top" alt="...">
                        <div class="card-body">
                            <h5 class="card-title">카드 정보</h5>
                            <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                            <a href="${pageContext.request.contextPath}/mypage/mycard" class="btn btn-primary">카드 조회하러 가기</a>
                        </div>
                    </div>
                </div>

                <div class="col">
                    <div class="card" style="width: 18rem;">
                        <img src="/images/kb_logo.png" class="card-img-top" alt="...">
                        <div class="card-body">
                            <h5 class="card-title">계좌 연결</h5>
                            <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                            <a href="${pageContext.request.contextPath}/mypage/bankForm" class="btn btn-primary">연결하러 가기</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>


    </div>
</div>


</body>
</html>
