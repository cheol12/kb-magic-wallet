<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-11
  Time: 오후 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>깨비의 요술 지갑 - 환전</title>
</head>
<body>
    오프라인 수령 폼
    <div>
        <form action="${pageContext.request.contextPath}/offline/form" method="post">
            <label for="amount">환전신청금액:</label>
            <input type="text" id="amount" name="amount" required><br><br>

            <label for="currency">통화 선택:</label>
            <select id="currency" name="currency" required>
                <option value="USD">미국 달러 (USD)</option>
                <option value="EUR">유로 (EUR)</option>
                <option value="JPY">일본 엔 (JPY)</option>
                <!-- 원하는 통화를 추가할 수 있습니다. -->
            </select><br><br>

            <label for="wallet">지갑 선택:</label>
            <select id="wallet" name="wallet" required>
                <c:forEach items="${WalletList}" var="wallet" varStatus="loop">
                    <option value="">${wallet.nickname} : ${wallet.walletId}</option>
                </c:forEach>
            </select>
            <!-- 필요에 따라 지갑 옵션을 추가/수정할 수 있습니다. --><br><br>

            <label for="bank">은행 선택:</label>
            <select id="bank" name="bank" required>
                <c:forEach items="${bankList}" var="bank" varStatus="loop">
                <option value="bank1">${bank.name}</option>
                </c:forEach>
                <!-- 원하는 은행을 추가할 수 있습니다. -->
            </select><br><br>

            <label for="date">환전날짜 선택:</label>
            <input type="date" id="date" name="date" required><br><br>

            <input type="submit" value="신청">
        </form>
    </div>
</body>
</html>
