<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-11
  Time: 오후 11:31
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

    function validateForm() {
        // 1. 모든 값을 입력했는지 검사
        const walletType = document.querySelector('input[name="walletType"]:checked');
        const walletId = document.getElementById('selectedWallet');
        const amount = document.querySelector('input[name="buyAmount"]');

        if (!walletType || !walletId || !amount.value) {
            alert("모든 필드를 입력해주세요.");
            return false;
        }

        // 2. amount가 50 이상인지 검사
        const amountValue = parseFloat(amount.value);
        if (amountValue < 50) {
            alert("환전 신청 금액은 50 이상이어야 합니다.");
            return false;
        }

        // 지갑 잔액 확인 여부 검사
        const walletBalance = document.getElementById("walletBalance").value;
        if (walletBalance === "") {
            alert("지갑 잔액을 확인해주세요.");
            return false;
        }


        // 모든 유효성 검사를 통과한 경우 true 반환
        return true;
    }

    let expectedAmountCK = () => {
        var code = $('select[name="buyCurrencyCode"]').val();
        var amount = $('input[name="buyAmount"]').val();

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
                $('#withdrawableBalance').attr('placeholder', response.toLocaleString());
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
                <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">환전/</span> 온라인</h4>
                <!-- Basic Layout -->
                <form action="${pageContext.request.contextPath}/exchange/online/form" method="post" id="exchangeOnlineForm" onsubmit="return validateForm();">
                    <div class="row">
                        <div class="col-xl">
                            <div class="card mb-4">
                                <div class="card-header d-flex justify-content-between align-items-center">
                                    <h5 class="mb-0">출금 정보</h5>
                                    <small class="text-muted float-end"></small>
                                </div>
                                <div class="card-body">

                                    <div class="row gx-3 gy-2 align-items-center">
                                        <div class="mb-3">
                                            출금 지갑 선택
                                        <div class="row">
                                        <div class="col-3 form-check">
                                            <input class="form-check-input" type="radio" name="walletType" value="0" checked>
                                            <label class="form-check-label">개인지갑</label>
                                        </div>
                                        <div class="col-3 form-check">
                                            <input class="form-check-input" type="radio" name="walletType" value="1">
                                            <label class="form-check-label">모임지갑</label>
                                        </div>
                                        </div>
                                            <select id="selectedWallet" class="form-select color-dropdown" name="walletId">
                                                <option selected>지갑을 선택하세요</option>
                                                <c:forEach items="${walletList}" var="wallet" varStatus="loop">
                                                    <option value="${wallet.walletId}">${wallet.nickname}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-mb-3">
                                            <button type="button" class="btn btn-outline-warning" onclick="balanceCK();">잔액 조회</button>
                                        </div>
                                        <div id="hide-balance">
                                            <label class="form-label">출금가능잔액</label>
                                            <input id="withdrawableBalance" type="text" class="form-control" placeholder="잔액을 조회하세요" readonly/>

                                            <label class="form-label">총잔액</label>
                                            <input id="walletBalance" type="text" class="form-control" placeholder="잔액을 조회하세요" readonly/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl">
                            <div class="card mb-4">
                                <div class="card-header d-flex justify-content-between align-items-center">
                                    <h5 class="mb-0">환전 정보</h5>
                                </div>
                                <div class="card-body">

                                    <div class="row gx-3 gy-2 align-items-center">
                                        <div class="col-mb-2">
                                            신청 금액
                                        </div>
                                        <div class="col-3">
                                            <select class="form-select color-dropdown" name="buyCurrencyCode">
                                                <option selected>통화선택</option>
                                                <option value="1">USD</option>
                                                <option value="2">JPY</option>
                                            </select>
                                        </div>
                                        <div class="col-6">
                                            <input type="number" class="form-control" placeholder="금액을 입력하세요" name="buyAmount">
                                        </div>
                                        <div class="col-3">
                                            <button type="button" class="btn btn-outline-warning" onclick="expectedAmountCK();">환전 예상 금액 조회</button>
                                        </div>
                                        <div>
                                            <label class="form-label">출금금액</label>
                                            <input id="expectedAmount" type="text" class="form-control" placeholder="" readonly/>

                                            <label class="form-label">현재 고시 환율</label>
                                            <input id="tradingBaseRate" type="text" class="form-control" placeholder="" readonly/>

                                            <label class="form-label">적용 환율</label>
                                            <input id="applicableExchangeRate" type="text" class="form-control" placeholder="" readonly/>
                                        </div>
                                    </div>
                                </div>
                                        <button type="submit" class="btn btn-primary">환전하기</button>
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
