<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-11
  Time: 오후 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        @font-face {
            font-family: 'NanumSquareNeo-Variable';
            src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_11-01@1.0/NanumSquareNeo-Variable.woff2') format('woff2');
            font-weight: normal;
            font-style: normal;
        }
    </style>
    <title>깨비의 요술 지갑 - 모임목록</title>

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
    <script src="../../../assets/js/common.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script type="text/javascript">
        $(document).ready(function () {

            /// gpt의 뜬금포 모달
            function inviteResponse() {
                $.ajax({
                    url: "${pageContext.request.contextPath}/group-wallet/invited-list",
                    type: "post",
                    dataType: "json",
                    success: function (data, result, status) {
                        var str = "";

                        $.each(data, function (index, item) {
                            str += '<tr id="searchGroupWalletList">'
                            str += '<td>' + item.nickname + '</td>';
                            str += '<td>' + item.chairmanName + '</td>';
                            str += '<td><button class="alert-warning" data-group-id="' + item.groupWalletId + '" data-nickname="' + item.nickname + '">초대 응답</button>';
                            str += '</tr>';
                        });
                        $("#invitedMeList").empty().append(str);

                        // 초대된 목록이 비어있으면 숨기기
                        if (data.length === 0) {
                            $("#invitedMeListContainer").hide();
                        } else {
                            $("#invitedMeListContainer").show();
                        }
                    },
                    error: function (result, status) {
                        // 오류 처리
                    },
                });
            }

            inviteResponse();

            // 초대 응답 버튼 누르면
            $(document).on("click", '.alert-warning', function () {
                let groupId = $(this).data("group-id");
                let nickname = $(this).data("nickname");

                // 모달 다이얼로그 생성
                var modalHtml = '<div class="modal fade" id="inviteModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">';
                modalHtml += '<div class="modal-dialog">';
                modalHtml += '<div class="modal-content">';
                modalHtml += '<div class="modal-header">';
                modalHtml += '<h5 class="modal-title" id="exampleModalLabel">초대 응답</h5>';
                modalHtml += '<button id="inviteModalCloseBtn" type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>';
                modalHtml += '</div>';
                modalHtml += '<div class="modal-body">';
                modalHtml += '모임지갑명 : ' + nickname + '에 참여하시겠습니까?';
                modalHtml += '</div>';
                modalHtml += '<div class="modal-footer">';
                modalHtml += '<button type="button" class="btn btn-primary" id="acceptBtn">수락</button>';
                modalHtml += '<button type="button" class="btn btn-secondary" id="refuseBtn" data-bs-dismiss="modal">거절</button>';
                modalHtml += '</div>';
                modalHtml += '</div>';
                modalHtml += '</div>';
                modalHtml += '</div>';

                // 모달 다이얼로그 추가
                $("body").append(modalHtml);

                // 모달 다이얼로그 표시
                var modal = new bootstrap.Modal(document.getElementById('inviteModal'));
                modal.show();

                // 수락 버튼 클릭 시 이벤트 처리
                $("#acceptBtn").on("click", function () {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/group-wallet/" + groupId + "/invite-accept",
                        type: "post",
                        data: {groupId: groupId},
                        success: function (result, response) {
                            console.log(result);
                            if (result > 0) {
                                // 강퇴 성공 시 필요한 작업 수행
                                alert("새로운 모임지갑에 들어갔어요!")
                                inviteResponse();
                                location.href = "${pageContext.request.contextPath}/group-wallet/" + groupId; // 해당 페이지로 이동
                            } else {
                                alert("실패했어요");
                            }
                            modal.hide(); // 모달 닫기
                        },
                        error: function () {

                        }
                    });
                });

                // 거절 버튼 클릭 시 이벤트 처리
                $('#refuseBtn').on('click', function () {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/group-wallet/" + groupId + "/invite-refuse",
                        type: "post",
                        data: {groupId: groupId},
                        success: function (result, response) {
                            console.log(result);
                            if (result > 0) {
                                // 거절 성공 시 필요한 작업 수행
                                alert("모임지갑의 초대를 거절했어요!");
                                modal.hide();
                                inviteResponse();
                            } else {
                                alert("실패했어요");
                            }
                        },
                        error: function () {

                        }
                    });
                });
                $("#inviteModalCloseBtn").on("click", function () {
                    modal.hide();
                });
            });

        })

    </script>
</head>
<body>
<header>
    <jsp:include page="/WEB-INF/views/common/navbar.jsp"/>
</header>



<main>
    <div class="pageWrap">
        <div class="center">
            <div class="d-flex align-items-end row">
                <div class="col-sm-7">
                    <div class="card-body">
                        <h1 class="card-title text-break" >${member.name}님이 참여 중인 모임지갑이에요 🎉</h1>
                        <p class="mb-4"></p>
                    </div>
                </div>
                <div class="col-sm-5 text-center text-sm-left">
                    <div class="card-body pb-0 px-0 px-md-4">
                        <img src="../assets/img/illustrations/man-with-laptop-light.png" height="140" alt="View Badge User" data-app-dark-img="illustrations/man-with-laptop-dark.png" data-app-light-img="illustrations/man-with-laptop-light.png">
                    </div>
                </div>
            </div>
            <div id="invitedMeListContainer" style="display:none; margin-top: 5px">
                <h2 class="card-title text-break"> 잠깐! 모임지갑으로부터 초대가 왔어요!</h2>
                <div class="table-responsive text-nowrap">

                    <table class="table table">
                        <thead>
                        <tr>
                            <th><i class="fab fa-angular fa-lg text-danger me-3"></i>모임지갑명</th>
                            <th><i class="fab fa-angular fa-lg text-danger me-3"></i>모임장</th>
                        </tr>
                        </thead>
                        <tbody class="table-border-bottom-0" id="invitedMeList">

                        </tbody>
                    </table>


                </div>
            </div>

            <div class="row row-cols-1 row-cols-md-2 g-4 mb-5">

                <c:forEach var="list" varStatus="status" items="${gWalletList}">
                    <div style="margin-top: 5px">
                        <div class="col">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h2 class="card-title text-dark"><strong>${list.getNickname()}</strong></h2>
                                    <hr>
                                    <h5 class="card-title">
                                        <img src="${pageContext.request.contextPath}/images/saving/amountLimit.svg">
                                        원화 잔액 : <span id="balance-${status.index}"></span> 원
                                    </h5>
                                    <script>
                                        // list.getBalance()의 결과값 가져오기
                                        var balanceValue = ${list.getBalance()};

                                        // 3자리마다 쉼표 추가하는 함수
                                        function addCommas(num) {
                                            return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                                        }

                                        // 결과값을 쉼표로 구분된 형식으로 변환하여 출력
                                        document.getElementById("balance-" + ${status.index}).innerText = addCommas(balanceValue);
                                    </script>
                                    <br>
                                    <div class="d-grid gap-1 col-lg-4 mx-auto">
                                        <a href="${pageContext.request.contextPath}/group-wallet/${list.getGroupWalletId()}" class="btn btn-primary">상세보기</a>
                                        <br>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <section>
                    <div style="...">
                        <div class="col">
                            <div class="card h-100">
                                <div class="card-body">
                                    <h2 class="card-title text-dark"><strong>새로운 모임 지갑 생성</strong></h2>
                                </div>
                                <div class="d-grid gap-1 col-lg-4 mx-auto">
                                    <div class="svg-container">
                                        <img src="${pageContext.request.contextPath}/images/groupwallet/wallet-solid.svg">
                                    </div>
                                    <a href="${pageContext.request.contextPath}/group-wallet/new" class="btn btn-primary">생성하기</a>
                                    <br>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div>

    </div>
</main>


<footer>

</footer>
</body>
</html>
