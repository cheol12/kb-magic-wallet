<%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-13
  Time: 오후 5:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>깨비의 요술 지갑 - 개인 지갑</title>

    <link rel="stylesheet" type="text/css" href="/css/common.css">

</head>
<body>
<jsp:include page="/WEB-INF/views/common/navbar.jsp"/>

<div class="pageWrap">
    <div class="center">
        <div class="walletCard">
            <div class="walletForm">
                <span class="walletName">개인 지갑</span>

            </div>
            <div class="walletBalance">
                <span>통화별 잔액</span>
                <span><%--${walletDetailDto.balance("won")}--%>원</span>
                <span><%--${walletDetailDto.balance("doller")}--%>달러</span>
                <span><%--${walletDetailDto.balance("en")}--%>엔</span>
            </div>


        </div>


        꺼내는 금액<input type="text"> 원
        <button>금액 꺼내기</button>
    </div>
</div>

</body>
</html>
