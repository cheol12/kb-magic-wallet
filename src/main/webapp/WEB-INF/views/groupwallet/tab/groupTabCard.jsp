<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: 김진형
  Date: 2023-09-20
  Time: 오후 7:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>모임 연결 카드</title>
</head>
<body>

<div class="tab-pane fade" id="navs-top-card" role="tabpanel">
    <div class="card">
        <div class="card-body">
            <div class="row" id="tab5">
                <c:set var="cardExists" value="false"/>
                <c:forEach var="card" items="${cardIssuanceDtoList}" varStatus="status">
                    <c:if test="${card.member.memberId == sessionScope.member.memberId}">
                        <c:set var="cardExists" value="true"/>
                    </c:if>
                    <div class="col-md-6 col-xl-4">
                        <div class="card shadow-none bg-transparent border border-secondary mb-3">
                            <div class="card-body">
                                <h5 class="card-title">${card.member.name}</h5>
                                <img src="${pageContext.request.contextPath}/assets/img/card/card${fn:substring(card.cardNumber, fn:length(card.cardNumber)-1, fn:length(card.cardNumber))}.png"
                                     alt="Card Image" style="width: 100%">
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${not cardExists}">
                    <div class="col-md-6 col-xl-4">
                        <div class="card shadow-none bg-transparent border border-secondary mb-3">
                            <div class="card-body">
                                <h5 class="card-title">카드 연결</h5>
                                <div style="width: 100%; text-align: center">
                                    <img src="${pageContext.request.contextPath}/assets/img/icons/squre_plus.png"
                                         alt="Card Image" style="width: 60%;"
                                         onclick="location.href='${pageContext.request.contextPath}/group-wallet/${id}/card_2'"
                                         id="cardChange">
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>
