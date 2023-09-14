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
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/navbar.jsp"/>

    <div class="pageWrap">
        <div class="center">
            <div class="banner">
                <img src="/images/kb_saving_banner.jpg" style="width: 1200px" height="400px">
            </div>
            <%--
           <c:forEach var="saving" items="${savingList}" varStatus="status">
            <div class="savingForm">

                <div class="savingContentForm">
                    <span class="savingHeader">${saving.name}</span><br>
                    <span class="savingContent">${saving.interestRate}</span><br>
                    <span class="savingContent">${saving.peroid}</span><br>
                    <span class="savingContent">${saving.amountLimit}</span><br>
                    <span class="savingContent">${saving.comment}</span><br>
                </div>

            </div>
           </c:forEach>
           --%>

            <div class="savingForm">

                <div class="savingContentForm">
                    <span class="savingHeader">상품 1</span><br>
                    <span class="savingContent">금리</span><br>
                    <span class="savingContent">기간</span><br>
                    <span class="savingContent">최대 금액</span><br>
                    <span class="savingContent">설명</span><br>
                </div>

            </div>


        </div>

    </div>

</body>
</html>
