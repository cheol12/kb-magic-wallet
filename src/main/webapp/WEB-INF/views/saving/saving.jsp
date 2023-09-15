<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-11
  Time: 오후 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>깨비의 요술 지갑 - 적금</title>

    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link rel="stylesheet" type="text/css" href="/css/saving.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/navbar.jsp"/>

    <div class="pageWrap">
        <div class="center">
            <div class="banner">
                <img src="/images/kb_saving_banner.jpg" style="width: 1200px" height="400px">
            </div>

            <div class="savingList">
                <c:forEach var="saving" items="${savingList}" varStatus="status">
                    <div style="margin-top: 5px">
                        <div class="card">
                            <div class="card-header">
                                    ${saving.getName()}
                            </div>
                            <div class="card-body">
                                <h5 class="card-title">금리 : ${saving.getInterestRate()}</h5>
                                <h5 class="card-title">최대 한도 : ${saving.getAmountLimit()}</h5>
                                <a href="${pageContext.request.contextPath}/saving/${saving.getSavingId()}" class="btn btn-primary">상세보기</a>
                            </div>
                        </div>
                    </div>






                </c:forEach>
            </div>



        </div>

    </div>

</body>
</html>
