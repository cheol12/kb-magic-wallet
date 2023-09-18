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
    <title>Title</title>

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

<%--    <script type="text/javascript">--%>
<%--        $(document).ready(function () {--%>
<%--            $("#frm").on("submit", function (e) {--%>
<%--                $("input").each(function (index, item) {--%>
<%--                    if (validateCheck(item)) {--%>
<%--                        focus(item);--%>
<%--                        return false;--%>
<%--                        // each function만 탈출하는 return 문--%>
<%--                        // => 추가적으로 e.preventDefault();를 통해 막아주어야한다--%>
<%--                    }--%>
<%--                });--%>
<%--                e.preventDefault();--%>
<%--            });--%>

<%--            function validateCheck(obj) { //obj = item전달--%>
<%--                if (obj.value === "") {//obj가 이름, 이메일, 연락, 비번....--%>
<%--                    alert("값을 입력해주세요.");--%>
<%--                    obj.focus();--%>
<%--                    return true;--%>
<%--                } else {--%>
<%--                    return false;--%>
<%--                }--%>
<%--            }--%>


<%--        });--%>
<%--    </script>--%>

</head>
<body>

<jsp:include page="../common/navbar.jsp"></jsp:include>

<div class="pageWrap">
    <div class="center">
        <%--        <form action="/personalwallet/withdraw">--%>
        <%--            <div class="row g-3 align-items-center" >--%>
        <%--                <div class="col-auto">--%>
        <%--                    <label for="amount" class="col-form-label">환불하실 금액</label>--%>
        <%--                </div>--%>
        <%--                <div class="col-auto" >--%>
        <%--                    <input type="text" id="amount" class="form-control" aria-describedby="passwordHelpInline" name="amount">--%>
        <%--                </div>--%>
        <%--            </div>--%>

        <%--            <input type="submit" value="환불하기">--%>
        <%--        </form>--%>
        <div class="content-wrapper">
            <!-- Content -->
            <div class="container-xxl flex-grow-1 container-p-y">
                <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">개인지갑/</span> 꺼내기</h4>
                <!-- Basic Layout -->

                <form id="withdrawForm" method="post" action="/personalwallet/withdraw">
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
                                            출금개인지갑
                                            <select id="selectPersonalWalletWithdraw"
                                                    class="form-select color-dropdown">
                                                <option value="bg-primary" selected>XXX의 개인지갑</option>
                                            </select>
                                        </div>
                                        <div class="col-md-3">
                                            <button type="button" class="btn btn-outline-warning">잔액 조회</button>
                                        </div>
                                        <div id="hide-balance">
                                            <label class="form-label">출금가능잔액</label>
                                            <input type="text" class="form-control" value="${balance.get("KRW")}" readonly/>

                                            <label class="form-label">총잔액</label>
                                            <input type="text" class="form-control" value="${balance.get("KRW")}" readonly/>
                                        </div>
                                        <div class="col-md-3">
                                            지갑 비밀번호
                                            <input type="password" class="form-control" maxlength="4">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl">
                            <div class="card mb-4">
                                <div class="card-header d-flex justify-content-between align-items-center">
                                    <h5 class="mb-0">입금정보</h5>
                                </div>
                                <div class="card-body">
                                    <div class="mb-3">
                                        입금개인통장
                                        <label class="form-label"></label>
                                        <div class="input-group input-group-merge">
                                            <select id="selectTypeOpt" class="form-select color-dropdown">
                                                <option value="bg-primary" selected>[국민] 937702-00-888XXX</option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="mb-3">
                                        이체금액
                                        <div class="input-group input-group-merge">
                                            <input type="number" id="basic-icon-default-company" class="form-control"
                                                   name="amount"/>원
                                        </div>
                                        <%--                                        <div class="alert alert-danger" role="alert">This is a danger alert — check it out!</div>--%>

                                        <div class="mb-auto">
                                            <button type="button" class="btn btn-sm btn-outline-warning">1만</button>
                                            <button type="button" class="btn btn-sm btn-outline-warning">3만</button>
                                            <button type="button" class="btn btn-sm btn-outline-warning">5만</button>
                                            <button type="button" class="btn btn-sm btn-outline-warning">10만</button>
                                            <button type="button" class="btn btn-sm btn-outline-warning">50만</button>
                                            <button type="button" class="btn btn-sm btn-outline-warning">100만</button>
                                        </div>
                                    </div>
                                    <div class="mb-3">
                                        받는 통장 메모
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
                                        내 지갑 메모
                                        <div class="input-group input-group-merge">
                                            <input
                                                    type="text"
                                                    class="form-control"
                                                    placeholder="(선택) 7자 이내 입력"
                                            />
                                        </div>
                                    </div>
                                </div>
                                <!-- Button trigger modal -->
                                <button type="button" class="btn btn-primary" id="withdrawButton" data-bs-toggle="modal"
                                        data-bs-target="#exampleModal">
                                    환불하기
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
                                                <button type="button" id="saveChangesButton" class="btn btn-primary">비밀번호 확인</button>
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
