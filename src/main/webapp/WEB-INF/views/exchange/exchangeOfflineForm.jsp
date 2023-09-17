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

    document.addEventListener("DOMContentLoaded", function () {
        const walletSelect = document.getElementById("walletSelect");

        walletSelect.addEventListener("change", function () {
            const selectedOption = walletSelect.options[walletSelect.selectedIndex];
            const selectedRole = selectedOption.getAttribute("data-role");

            // "CHAIRMAN"이 아닌 경우 선택을 비활성화
            if (selectedRole !== "CHAIRMAN") {
                walletSelect.selectedIndex = 0; // 기본 선택 옵션으로 돌아감
                selectedOption.disabled = true; // 옵션을 비활성화
                selectedOption.style.color = "#D8D8D8";
                selectedOption.style.backgroundColor = "#F2F2F2";
            }
        });
    });

    function validateForm() {
        // 모든 요소가 입력되었는지 검사
        const currencyCode = document.forms["offlineReceiptForm"]["currencyCode"].value;
        const amount = document.forms["offlineReceiptForm"]["amount"].value;
        const walletType = document.forms["offlineReceiptForm"]["walletType"].value;
        const walletId = document.forms["offlineReceiptForm"]["walletId"].value;
        const bankId = document.forms["offlineReceiptForm"]["bankId"].value;
        const receiptDate = document.forms["offlineReceiptForm"]["receiptDate"].value;

        if (
            currencyCode === "통화선택" ||
            amount === "" ||
            walletType === "" ||
            walletId === "지갑을 선택하세요" ||
            bankId === "수령 지점을 선택하세요" ||
            receiptDate === ""
        ) {
            alert("모든 값을 입력해주세요.");
            return false;
        }

        // amount가 0 이상인지 검사
        if (parseFloat(amount) <= 49) {
            alert("금액은 50 이상이어야 합니다.");
            return false;
        }

        // 지갑 잔액 확인 여부 검사
        const walletBalance = document.getElementById("walletBalance").value;
        if (walletBalance === "") {
            alert("지갑 잔액을 확인해주세요.");
            return false;
        }

        // 모든 검사 통과 시 폼 제출
        return true;
    }

let expectedAmountCK = () => {
        var code = $('select[name="currencyCode"]').val();
        var amount = $('input[name="amount"]').val();

        let data = {
            code: code,
            amount : amount
        }

        // AJAX POST 요청
        $.ajax({
            type: "post",
            url: "/exchange/expectedAmount",
            data: JSON.stringify(data),
            contentType: "application/json",
            dataType: "json", // 예상되는 응답 형식(JSON 등)
            success: function (response) {
                // 성공 시 실행할 코드
                alert("성공")
                $('#expectedAmount').attr('placeholder', response.expectedAmount.toLocaleString());
                $('#tradingBaseRate').attr('placeholder', response.tradingBaseRate.toLocaleString());
                $('#applicableExchangeRate').attr('placeholder', response.applicableExchangeRate.toLocaleString());
            },
            error: function (error) {
                // 오류 발생 시 실행할 코드
                console.error("오류: " + error);
            }
        });
    }

    let balanceCK = () => {
        let walletId = $('select[name="walletId"]').val();
        let walletType = $('input[name="walletType"]:checked').val();

        let data = {
            walletId: walletId,
            walletType : walletType
        }

        // AJAX POST 요청
        $.ajax({
            type: "post",
            url: "/exchange/walletBalance",
            data: JSON.stringify(data),
            contentType: "application/json",
            dataType: "json", // 예상되는 응답 형식(JSON 등)
            success: function (response) {
                // 성공 시 실행할 코드
                alert("성공")
                $('#walletBalance').attr('placeholder', response.toLocaleString());
                $('#walletBalance').val(response.toLocaleString());
            },
            error: function (error) {
                // 오류 발생 시 실행할 코드
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
                <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">환전/</span>오프라인</h4>
                <!-- Basic Layout -->
                <form id="offlineReceiptForm" action="${pageContext.request.contextPath}/exchange/offline/form" method="post" onsubmit="return validateForm();">
                    <div class="row">
                        <div class="row align-items-start">
                            <div class="card mb-4">
                                <div class="card-header d-flex justify-content-between align-items-center">
                                    <h5 class="mb-0">환전 정보</h5>
                                    <small class="text-muted float-end"></small>
                                </div>
                                <div class="card-body">

                                    <div class="row gx-3 gy-2 align-items-center">
                                        <div class="col-2">
                                            환전 신청 금액
                                        </div>
                                        <div class="col-2">
                                            <select class="form-select color-dropdown" name="currencyCode">
                                                <option selected>통화선택</option>
                                                <option value="1">USD</option>
                                                <option value="2">JPY</option>
                                            </select>
                                        </div>
                                        <div class="col-5">
                                            <input type="number" class="form-control" placeholder="최소금액 50" name="amount">
                                        </div>
                                        <div class="col-3">
                                            <button type="button" class="btn btn-outline-warning" onclick="expectedAmountCK();">환전 예상 금액 확인</button>
                                        </div>
                                        <div id="">
                                            <label class="form-label">출금금액</label>
                                            <input type="text" id="expectedAmount" class="form-control" placeholder="" readonly/>

                                            <label class="form-label">현재 고시 환율</label>
                                            <input type="text" id="tradingBaseRate" class="form-control" placeholder="" readonly/>

                                            <label class="form-label">적용 환율</label>
                                            <input type="text" id="applicableExchangeRate" class="form-control" placeholder="" readonly/>
                                            <div class="form-text">* 환율 변동에 따라 예상 원화금액과 실제 출금금액 간에 차이가 발생할 수 있습니다.
                                            </div>
                                        </div>
                                        <div class="col-2">
                                            환전 사유
                                        </div>
                                        <div class="col-10">
                                            <select class="form-select color-dropdown">
                                                <option selected>관광, 친지방문 등 일반 해외경비</option>
                                            </select>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row align-items-center">
                            <div class="card mb-4">
                                <div class="card-header d-flex justify-content-between align-items-center">
                                    <h5 class="mb-0">출금 지갑 선택</h5>
                                    <small class="text-muted float-end"></small>
                                </div>
                                <div class="card-body">
                                        <div class="row">
                                            <div class="col-2 form-check">
                                                <input class="form-check-input" type="radio" name="walletType" value="0" checked>
                                                <label class="form-check-label">개인지갑</label>
                                            </div>
                                            <div class="col-2 form-check">
                                                <input class="form-check-input" type="radio" name="walletType" value="1">
                                                <label class="form-check-label">모임지갑</label>
                                            </div>
                                        </div>
                                    <div class="row gx-3 gy-2 align-items-center">
                                        <div class="col-10">
                                            <select id="walletSelect" class="form-select color-dropdown" name="walletId">
                                                <option selected>지갑을 선택하세요</option>
                                                <c:forEach items="${walletList}" var="wallet" varStatus="loop">
                                                    <option value="${wallet.walletId}" data-role="${wallet.role}">${wallet.nickname}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-2">
                                            <button type="button" class="btn btn-outline-warning" onclick="balanceCK();">지갑 잔액 확인</button>
                                        </div>
                                        <div id="##">
                                            <label class="form-label">지갑잔액</label>
                                            <input id="walletBalance" type="text" class="form-control" placeholder="지갑 잔액을 확인하세요" readonly/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row align-items-end">
                            <div class="card mb-4">
                                <div class="card-header d-flex justify-content-between align-items-center">
                                    <h5 class="mb-0">수령 정보</h5>
                                </div>
                                <div class="card-body">
                                    <div class="mb-3">
                                        수령희망지점
                                        <label class="form-label"></label>
                                        <div class="input-group input-group-merge">
                                            <select id="#" class="form-select color-dropdown" name="bankId">
                                                <option selected>수령 지점을 선택하세요</option>
                                                <c:forEach items="${bankList}" var="bank" varStatus="loop">
                                                <option value="${bank.bankId}">${bank.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="mb-3">
                                        은행 위치 마크
                                    </div>

                                    <div class="mb-3">
                                        수령희망날짜
                                        <label class="form-label"></label>
                                        <div class="input-group input-group-merge">
                                            <input id="receiptDate" class="form-control form-label" type="datetime-local" name="receiptDate">
                                        </div>
                                    </div>

                                </div>
                                <button type="submit" class="btn btn-primary">수령 신청</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <!-- / Content -->

    </div>
</div>
</div>
</body>
</html>
