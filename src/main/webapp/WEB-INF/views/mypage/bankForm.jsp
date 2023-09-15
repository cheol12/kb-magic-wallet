<%--
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
<jsp:include page="../common/navbar.jsp"/>
<div class="pageWrap">
    <div class="center">

        <form action="${pageContext.request.contextPath}/mypage/bank" method="post">
            <div class="row g-3 align-items-center" >
                <div class="col-auto">
                    <label for="account" class="col-form-label">계좌 번호</label>
                </div>
                <div class="col-auto" >

                    <input type="text" id="account" class="form-control" aria-describedby="passwordHelpInline" name="account">


                </div>

            </div>
            <input type="submit" value="연결하기">
        </form>


    </div>
</div>


</body>
</html>
