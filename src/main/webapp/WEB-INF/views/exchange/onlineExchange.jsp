<%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-11
  Time: 오후 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>깨비의 요술 지갑 - 환전</title>

    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</head>
<body>

    <jsp:include page="../common/navbar.jsp"/>

    <div class="pageWrap">
        <div class="center">

            <div class="row">
                <div class="col-sm-6">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">환전</h5>
                            <p class="card-text">외국으로 가자!</p>
                            <p class="card-text">원화에서 외화로!!</p>
                            <a href="/exchange/online/form" class="btn btn-primary">환전하러 가기</a>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">재환전</h5>
                            <p class="card-text">다시 한국으로!</p>
                            <p class="card-text">외화에서 원화로!!</p>
                            <a href="/exchange/online/re-form" class="btn btn-primary">재환전하러 가기</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
