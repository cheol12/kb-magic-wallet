<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>

    <!-- Icons. Uncomment required icon fonts -->
    <link rel="stylesheet" href="../../../assets/vendor/fonts/boxicons.css"/>

    <!-- Core CSS -->
    <link rel="stylesheet" href="../../../assets/vendor/css/core.css" class="template-customizer-core-css"/>
    <link rel="stylesheet" href="../../../assets/vendor/css/theme-default.css" class="template-customizer-theme-css"/>
    <link rel="stylesheet" href="../../../assets/css/demo.css"/>

    <!-- Vendors CSS -->
    <link rel="stylesheet" href="../../../assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css"/>

    <!-- Page CSS -->

    <!-- Helpers -->
    <script src="../../../assets/vendor/js/helpers.js"></script>

    <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
    <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
    <script src="../../../assets/js/config.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<script>

    /* 취소 버튼 비활성화 */
    window.onload = function() {

        var receiptStates = document.querySelectorAll('.badge.bg-label-primary');

        // 모든 취소 버튼
        var cancelButtons = document.querySelectorAll('.btn.btn-outline-secondary');

        // 각 "receiptState"에 대한 반복문
        for (var i = 0; i < receiptStates.length; i++) {
            var receiptState = receiptStates[i].innerText.trim();

            // "receiptState"가 "WAITING"인 경우만 취소 버튼 활성화
            if (receiptState === "WAITING") {
                // 해당 취소 버튼을 활성화
                cancelButtons[i].disabled = false;
            } else {
                // 해당 취소 버튼을 비활성화
                cancelButtons[i].disabled = true;
            }
        }
    };

    /* 취소 이벤트 */
    let cancelEvent = (rowIndex) => {

        var receiptId = $("#receiptId" + rowIndex).val();

        $.ajax({
            type: "delete",
            url: "${pageContext.request.contextPath}/exchange/offline/form", // 실제 서버 URL로 변경
            data: {offlineReceiptId: receiptId},
            dataType: "text",
            success: function (response) {
                alert("성공")
                location.reload();
            },
            error: function (error) {
                console.error("오류: " + error);
            }
        });
    }



</script>
<jsp:include page="../common/navbar.jsp"></jsp:include>
<div class="pageWrap">
    <div class="center">
        <div class="content-wrapper">
            <!-- Content -->
            <div class="container-xxl flex-grow-1 container-p-y">
                <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">환전/ </span>오프라인</h4>
                <div class="row">
                    <div class="col-sm-6">
                        <div class="card">
                            <div class="card-body">
                                <h3 class="card-title">오프라인 환전</h3>
                                <p class="card-text">원화에서 외화로 직접 수령!!</p>
                                <a href="/exchange/offline/form" class="btn btn-primary">환전하러 가기</a>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <div class="card">
                    <h2 class="card-header">오프라인 환전 내역</h2>
                    <div class="table-responsive text-nowrap">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th><h4 style="margin-bottom: 0" class="text-center">수령 지점</h4></th>
                                <th><h4 style="margin-bottom: 0" class="text-center">금액</h4></th>
                                <th><h4 style="margin-bottom: 0" class="text-center">수령일</h4></th>
                                <th><h4 style="margin-bottom: 0">상태</h4></th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody class="table-border-bottom-0">
                            <c:forEach items="${offlineExchangeHistoryList}" var="receipt" varStatus="loop">
                            <tr>
                                <td><h5 style="margin-bottom: 0" class="text-center">${receipt.bankName}</h5></td>
                                <td><h5 style="margin-bottom: 0" class="text-center">${receipt.currencyCode} ${receipt.amount}</h5></td>
                                <fmt:formatDate value="${receipt.receiptDate}" pattern="yyyy-MM-dd" />
                                <td><h5 style="margin-bottom: 0" class="text-center dateTd">${receipt.receiptDate}</h5></td>
                                <td><span style="margin-bottom: 0" class="badge bg-label-primary me-1 receiptState" data-receipt-state="${receipt.receiptState}">${receipt.receiptState}</span></td>
                                <td><button onclick="cancelEvent(${loop.index + 1});" id="cancelBtn${loop.index + 1}" class="btn btn-outline-secondary">취소하기</button></td>
                            </tr>
                                <input id="receiptId${loop.index + 1}" type="hidden" value="${receipt.offlineReceiptId}">
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
