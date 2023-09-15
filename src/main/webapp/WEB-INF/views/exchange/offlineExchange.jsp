<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-11
  Time: 오후 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>깨비의 요술 지갑 - 환전</title>
    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<header>

</header>
<main>
<a href="/exchange/offline/form">수령 신청</a>
<div>
    <table border="1">
        <tr>
            <td>열 1, 행 1</td>
            <td>열 1, 행 1</td>
            <td>열 2, 행 1</td>
            <td>열 3, 행 1</td>
            <td>열 4, 행 1</td>
            <td>열 5, 행 1</td>
            <td>열 5, 행 1</td>
        </tr>
        <c:forEach items="${offlineExchangeHistoryList}" var="receipt" varStatus="loop">
            <tr>
                <td>${receipt.offlineReceiptId}</td>
                <td>${receipt.receiptDate}</td>
                <td>${receipt.currencyCode}</td>
                <td>${receipt.address.city}</td>
                <td>${receipt.bankName}</td>
                <td>${receipt.receiptState}</td>
                <td>
                    <button id="cancelBtn${loop.index + 1}">취소</button>
                </td>
            </tr>
        </c:forEach>
        <!-- 추가 행을 필요한 만큼 반복적으로 추가할 수 있습니다. -->
    </table>
</div>
</main>
<footer></footer>

<script>
    $(document).ready(function () {
        $("#cancelBtn").click(function () {
            // 보낼 데이터를 객체로 생성
            var dataToSend = {
                offlineReceiptId: 'value1',
                receiptState: 'value2',
                // 원하는 데이터 추가 가능
            };

            // AJAX POST 요청
            $.ajax({
                type: "${pageContext.request.contextPath}/group-wallet/new",
                url: "your_server_url_here", // 실제 서버 URL로 변경
                data: dataToSend,
                dataType: "json", // 예상되는 응답 형식(JSON 등)
                success: function (response) {
                    // 성공 시 실행할 코드
                    console.log("성공: " + response);
                },
                error: function (error) {
                    // 오류 발생 시 실행할 코드
                    console.error("오류: " + error);
                }
            });
        });
    });
</script>
</body>
</html>
