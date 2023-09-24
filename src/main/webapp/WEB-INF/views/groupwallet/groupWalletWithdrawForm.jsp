<%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-14
  Time: 오후 6:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>깨비의 요술 지갑 - 모임 지갑에서 꺼내기</title>

    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>

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
    <script src="../../../assets/js/validation.js"></script>
    <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
    <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
    <script src="../../../assets/js/config.js"></script>

    <script>
        function showBalance() {
            // Get a reference to the input element
            var balanceInput = document.getElementById("balance");

            // Set the value of the input element to 3,000,000
            balanceInput.value = numberWithCommas(${groupWallet.balance}) + " 원";
        }

        function addValue(amount) {
            var inputElement = document.getElementById("basic-icon-default-company");
            var currentValue = parseFloat(inputElement.value.replace(/,/g, "")) || 0; // 현재 값 가져오기, 숫자가 아닌 경우 0으로 설정
            var newValue = currentValue + amount; // 새로운 값 계산
            inputElement.value = newValue; // 새로운 값을 세자리마다 콤마를 추가하여 설정
        }

        function numberWithCommas(x) {
            return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        }

    </script>

</head>
<body>

<jsp:include page="../common/navbar.jsp"></jsp:include>

<div class="pageWrap">
    <div class="center">
        <div class="content-wrapper">
            <!-- Content -->
            <div class="container-xxl flex-grow-1 container-p-y">
                <h2 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">모임지갑/</span> 꺼내기</h2>
                <!-- Basic Layout -->
                <form id="withdrawForm" method="post" action="${pageContext.request.contextPath}/group-wallet/${id}/withdraw">
                    <div class="row">
                        <div class="col-xl">
                            <div class="card mb-4">
                                <div class="card-header d-flex justify-content-between align-items-center">
                                    <h3 class="mb-0">출금 정보</h3>
                                    <small class="text-muted float-end"></small>
                                </div>
                                <div class="card-body">
                                    <div class="row gx-3 gy-2 align-items-center">
                                        <div class="mb-3">
                                            <h5 class="mb3">
                                                꺼내기할 모임 지갑
                                            </h5>
                                            <select id="selectPersonalWalletWithdraw"
                                                    class="form-select color-dropdown">
                                                <option selected>${groupWallet.nickname}</option>
                                            </select>
                                        </div>
                                        <div class="md-3">
                                            <button type="button" class="btn btn-outline-warning"
                                                    onclick="showBalance()">잔액 조회
                                            </button>
                                        </div>
                                        <div id="hide-balance">
                                            <h5 class="mb3">출금가능잔액</h5>
                                            <input type="text" id="balance" class="form-control" readonly/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl">
                            <div class="card mb-4">
                                <div class="card-header d-flex justify-content-between align-items-center">
                                    <h3 class="mb-0">입금정보</h3>
                                </div>
                                <div class="card-body">
                                    <div class="row gx-3 gy-2 align-items-center">
                                        <div class="mb-3">
                                            <h5 class="mb-3">
                                                입금개인지갑
                                            </h5>
                                            <select id="selectTypeOpt" class="form-select color-dropdown">
                                                <option value="${member.memberId}" selected>${member.name}의 개인지갑</option>
                                            </select>
                                        </div>

                                        <h5 class="mb-3">
                                            <div class="mb-3">
                                                이체금액
                                            </div>
                                            <input type="text" id="basic-icon-default-company" class="form-control"
                                                   name="amount" value="0" readonly/>
                                            <br>

                                            <div class="mb-3">
                                                <button type="button" class="btn btn-outline-warning"
                                                        style="font-size: 0.875rem;" onclick="addValue(10000)">1만
                                                </button>
                                                <button type="button" class="btn btn-outline-warning"
                                                        style="font-size: 0.875rem;" onclick="addValue(30000)">3만
                                                </button>
                                                <button type="button" class="btn btn-outline-warning"
                                                        style="font-size: 0.875rem;" onclick="addValue(50000)">5만
                                                </button>
                                                <button type="button" class="btn btn-outline-warning"
                                                        style="font-size: 0.875rem;" onclick="addValue(100000)">10만
                                                </button>
                                                <button type="button" class="btn btn-outline-warning"
                                                        style="font-size: 0.875rem;" onclick="addValue(500000)">50만
                                                </button>
                                                <button type="button" class="btn btn-outline-warning"
                                                        style="font-size: 0.875rem;" onclick="addValue(1000000)">100만
                                                </button>
                                            </div>
                                        </h5>
                                        <div class="mb-3">
                                            <h5>
                                                받는 지갑 메모
                                            </h5>
                                            <div class="input-group input-group-merge">
                                                <input
                                                        type="text"
                                                        class="form-control"
                                                        placeholder="(선택) 7자 이내 입력"
                                                />
                                            </div>
                                            <div class="form-text">* 미 입력 시 출금계좌의 예금주명(내이름)이 기본 표기
                                            </div>
                                        </div>
                                        <div class="mb-3">
                                            <h5>
                                                내 통장 메모
                                            </h5>
                                            <div class="input-group input-group-merge">
                                                <input
                                                        type="text"
                                                        class="form-control"
                                                        placeholder="(선택) 7자 이내 입력"
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- Button trigger modal -->
                                <button type="button" class="btn btn-primary" id="withdrawButton" data-bs-toggle="modal"
                                        data-bs-target="#exampleModal">
                                    꺼내기
                                </button>

                                <!-- Modal -->
                                <div class="modal fade" id="exampleModal" tabindex="-1"
                                     aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">결제 비밀번호 확인</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                        aria-label="Close">
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <jsp:include page="../common/virtualKeyboard.jsp"/>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                                    닫기
                                                </button>
                                                <button type="button" id="saveChangesButton" class="btn btn-primary">
                                                    비밀번호 확인
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <!-- / Content -->

    </div>
</div>
<script>
    $(document).ready(function () {
        $("#saveChangesButton").click(function () {
            handleEnter()
        });
    });

    function summitForm() {
        $("#withdrawForm").submit();
    }
</script>
</body>
</html>
