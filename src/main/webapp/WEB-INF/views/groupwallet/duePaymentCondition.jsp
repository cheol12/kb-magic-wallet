<%--
  Created by IntelliJ IDEA.
  User: 김진형
  Date: 2023-09-20
  Time: 오전 11:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회비 납부 내역</title>
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

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>

    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">



    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>

    <style>
        .material-icons {
            font-size: 40px; /* 원하는 크기로 조정합니다. */
            vertical-align: middle
        }
    </style>

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

            //initTest("${pageContext.request.contextPath}/test/load-card-data");

            // 연결 불가 선택 시 연결 불가를 알려주는 모달 창 출력
            $(document).on("click", "#payDueBtn", function () {
                $("#payModal").modal('show');
            })

            $(document).on("click", "#pay-button", function () {
                alert("컨트롤러로 납부 전송");
            });


        });
    </script>

</head>
<body>
<%--회비 납부 모달창--%>
<jsp:include page="/WEB-INF/views/common/navbar.jsp"/>
<div class="modal fade" id="payModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="changeWalletLabel">회비 납부</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="changeWalletBody">
                납부?
            </div>
            <div class="modal-footer">
                <input type="hidden" name="connect-memberId">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                <button type="button" class="btn btn-primary" id="pay-button" data-bs-dismiss="modal">납부</button>
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
            <div class="col-xl-12">
                <div class="card h-20">
                    <div class="card-header d-flex align-items-center justify-content-between pb-0">
                        <div class="card-title mb-0">
                            <h5 class="m-2 me-2">회비 납부내역</h5>
<%--                            <small class="text-muted"><hr></small>--%>
                        </div>
                    </div>

                    <table class="table table">
                        <thead>
                        <tr>
                            <th class="text-center"><i class="fab fa-angular fa-lg text-danger me-3"></i>이름</th>
                            <th class="text-center"><i class="fab fa-angular fa-lg text-danger me-3"></i>이번 달 납부 여부</th>
                            <th class="text-center"><i class="fab fa-angular fa-lg text-danger me-3"></i>납부금액</th>
                            <th class="text-center"><i class="fab fa-angular fa-lg text-danger me-3"></i>누적 납부금액</th>
                        </tr>
                        </thead>
                        <tbody class="table-border-bottom-0" id="table">
                        <tr>
                            <td class="text-center">김진형</td>
                            <td class="text-center"><i class="material-icons" style="color:green;">check_circle</i> <button class="btn btn-secondary disabled" disabled>납부완료</button></td>
                            <td class="text-center">100000 원</td>
                            <td class="text-center">400000 원</td>
                        </tr>
                        <tr>
                            <td class="text-center">홍길동</td>
                            <td class="text-center"><i class="material-icons" style="color:red;">cancel</i> <button id="payDueBtn" class="btn btn-primary">납부하기</button></td>
                            <td class="text-center">0 원</td>
                            <td class="text-center">300000 원</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
