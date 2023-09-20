<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: 김진형
  Date: 2023-09-19
  Time: 오후 7:22
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
    <link rel="stylesheet" href="../../assets/vendor/fonts/boxicons.css"/>

    <!-- Core CSS -->
    <link rel="stylesheet" href="../../assets/vendor/css/core.css" class="template-customizer-core-css"/>
    <link rel="stylesheet" href="../../assets/vendor/css/theme-default.css" class="template-customizer-theme-css"/>
    <link rel="stylesheet" href="../../assets/css/demo.css"/>

    <!-- Vendors CSS -->
    <link rel="stylesheet" href="../../assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css"/>

    <!-- Page CSS -->

    <!-- Helpers -->
    <script src="../../assets/vendor/js/helpers.js"></script>
    <script src="../../assets/js/validation.js"></script>
    <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
    <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
    <script src="../../assets/js/config.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>

    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">


    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>


    <script type="text/javascript">
        $(document).ready(function () {
            function initTest(urlPath, data) {
                $.ajax({
                    url: urlPath,
                    type: "get",
                    dataType: "json",
                    data: "id="+data,
                    success: function (result, status) {
                        $("#table").empty();
                        // 화면에 갱신
                        var str = "";
                        $.each(result, function (i) {
                            str += '<tr data-id=' + result[i].memberId + '>';
                            str += '<TD>' + result[i].name + '</TD>';
                            str += '<TD>' + result[i].roleToString + '</TD>';
                            if (result[i].cardIsConnect) {
                                str += '<td>연결 중&nbsp&nbsp&nbsp&nbsp<i class="material-icons" style="color: green">credit_card</i></td>';
                            } else {
                                if (result[i].role == "GENERAL") {
                                    str += '<td id="cant-connect-card" class="open-modal">연결 불가 <i class="material-icons" style="color:red;">credit_card</i></td>';
                                } else {
                                    str += `<td id="can-connect-card" class="open-modal">연결 가능 <i class="material-icons">credit_card</i></td>`
                                }
                            }
                            str += '</TR>';
                        });
                        $("#table").append(str);
                    },
                    error: function (result, status) {
                    },
                });
            }
            //initTest() end

            initTest("${pageContext.request.contextPath}/test/load-card-data");

            // 연결 불가 선택 시 연결 불가를 알려주는 모달 창 출력
            $(document).on("click", "#cant-connect-card", function () {
                $("#cantConnectCard").modal('show');
            })

            // 연결 가능 선택 시 연결 가능을 알려주는 모달 창 출력
            $(document).on("click", "#can-connect-card", function () {
                var memberId = $(this).closest("tr").data("id");
                if(${sessionScope.member.memberId} == memberId){
                    $("input[name='connect-memberId']").val(memberId);
                    $("#changeWallet").modal('show');
                }else{
                    $("#cantConnectCardByLogin").modal('show');
                }
            });

            $(document).on("click", "#change-confirm-button", function () {
                var memberId = $("input[name='connect-memberId']").val();
                initTest("${pageContext.request.contextPath}/test/change-card-connection", memberId);
            });
        });
    </script>
</head>
<body>

<%--카드 변경 모달창--%>
<div class="modal fade" id="changeWallet" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="changeWalletLabel">모달 제목</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="changeWalletBody">
                변경?
            </div>
            <div class="modal-footer">
                <input type="hidden" name="connect-memberId">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                <button type="button" class="btn btn-primary" id="change-confirm-button" data-bs-dismiss="modal">변경</button>
            </div>
        </div>
    </div>
</div>

<%--카드 변경 불가(권한 X) 모달창--%>
<div class="modal fade" id="cantConnectCard" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="cantConnectCardLabel">모달 제목</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="cantConnectCardBody">
                모임원은 카드를 연결할 수 없습니다
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="confirm-button" data-bs-dismiss="modal">확인</button>
            </div>
        </div>
    </div>
</div>

<%--카드 변경 불가(로그인한 멤버와 다름) 모달창--%>
<div class="modal fade" id="cantConnectCardByLogin" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="cantConnectCardByLoginLabel">모달 제목</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="cantConnectCardByLoginBody">
                카드 소유자만 변경할 수 있습니다
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="confirm-button2" data-bs-dismiss="modal">확인</button>
            </div>
        </div>
    </div>
</div>

<div class="pageWrap">
    <div class="center">
        <div class="row">
            <div class="col-md-6 col-lg-6 col-xl-6 mb-4 h-100">
                <div class="card h-20">
                    <div class="card-header d-flex align-items-center justify-content-between pb-0">
                        <div class="card-title mb-0">
                            <h5 class="m-0 me-2">지갑 보유내역</h5>
                            <small class="text-muted">원화 외화 비율</small>
                        </div>
                    </div>
                    <table class="table table">
                        <thead>
                        <tr>
                            <th><i class="fab fa-angular fa-lg text-danger me-3"></i>이름</th>
                            <th><i class="fab fa-angular fa-lg text-danger me-3"></i>권한</th>
                            <th><i class="fab fa-angular fa-lg text-danger me-3"></i>카드연결</th>
                        </tr>
                        </thead>
                        <tbody class="table-border-bottom-0" id="table">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>