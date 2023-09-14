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

</head>
<body>
    <jsp:include page="/WEB-INF/views/common/navbar.jsp"/>

    <div class="pageWrap">

        <div class="center">

            <div class="walletCard">
                <div class="walletForm">
                    <span class="walletName">개인 지갑</span>
                    <button class="walletFormBtn" onclick="location.href='/personal-wallet/deposit'">채우기</button>
                    <button class="walletFormBtn" onclick="location.href='/personal-wallet/withdraw'">꺼내기</button>
                </div>
                <div class="walletBalance">
                    <span>통화별 잔액</span>
                    <span><%--${walletDetailDto.balance("won")}--%>원</span>
                    <span><%--${walletDetailDto.balance("doller")}--%>달러</span>
                    <span><%--${walletDetailDto.balance("en")}--%>엔</span>
                </div>


            </div>

            <div class="walletUsingList">
                <div class="walletUsingHistory">
                    <span><%--${list.dateTime}--%>날짜</span><br>
                    <span><%--${list.detail}--%>결제처</span><br>
                    <span><%--${list.type}--%>구분</span><br>
                    <span><%--${list.balance}--%>금액</span><br>
                </div>
<%--
                <c:forEach var="list" items="${walletDetailDto.list}" varStatus="status">
                    <div class="walletUsingHistory">
                        <span>&lt;%&ndash;${list.dateTime}&ndash;%&gt;날짜</span><br>
                        <span>&lt;%&ndash;${list.detail}&ndash;%&gt;결제처</span><br>
                        <span>&lt;%&ndash;${list.type}&ndash;%&gt;구분</span><br>
                        <span>&lt;%&ndash;${list.balance}&ndash;%&gt;금액</span><br>
                    </div>
                </c:forEach>--%>
            </div>
        </div>


    </div>

</body>
</html>
