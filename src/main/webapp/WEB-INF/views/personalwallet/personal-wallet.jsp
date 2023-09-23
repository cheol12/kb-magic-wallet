<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
    <title>깨비의 요술 지갑 - 개인지갑</title>

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
    <%--    <link rel="stylesheet" href="../../../assets/vendor/fonts/final_font.css"/>--%>
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
        // AJAX READY
        $(document).ready(function () {
            // 로딩 되자마자 거래 내역 리스트 비동기화 통신
            $(document).on("click", ".searchDateResult", function () {
                $('#detailModal').modal('show');

                var id = $(this).closest("tr").data("id");
                var row = $(this).closest("tr");

                $("#detail-date").text(row.find("td:eq(0)").text());
                $("#detail-time").text(row.find("td:eq(1)").text());

                var deposit = row.find("td:eq(2)").text();
                var withdrawal = row.find("td:eq(3)").text();

                if (deposit === "-") {
                    $("#detail-amount").text(withdrawal);
                } else {
                    $("#detail-amount").text(deposit);
                }
                $("#detail-type").text(row.find("td:eq(4)").text());
                $("#detail-content").text(id);
                $("#detail-balance").text(row.find("td:eq(5)").text());

            });
            $(document).on("click", "#can-connect-card", function () {
                $("#changeWallet").modal('show');
            });

            $.ajax({
                url: "/personalwallet/selectDate",
                type: "post",
                dataType: "json",
                success: function (result, status) {
                    // 화면에 갱신
                    var str = "";
                    $.each(result, function (i) {
                        console.log(result[i].dateTime)
                        var dateTime = new Date(result[i].dateTime);
                        var detailString = typeof result[i].detail === 'object' ? JSON.stringify(result[i].detail) : result[i].detail;
                        // 날짜와 시간을 따로 추출
                        var date = dateTime.toLocaleDateString(); // 날짜 형식으로 변환
                        var time = dateTime.toLocaleTimeString(); // 시간 형식으로 변환
                        console.log(date);
                        console.log(time);

                        str += '<TR class="searchDateResult" data-id="' + detailString + '">'
                        // 날짜 시간 처리
                        str += '<TD><h5 id="date" class="text-center" style="margin-bottom: 0">' + date + '</h5></TD>';
                        str += '<TD><h5 id="time" class="text-center" style="margin-bottom: 0">' + time + '</h5></TD>';
                        // 입금액 출금액 처리
                        if (result[i].type === '입금') {
                            str += '<TD><h5 id="depositAmount" class="text-center" style="margin-bottom: 0">' + formatNumberWithCommas(result[i].amount) + '</h5></TD><TD><h5 class="text-center" style="margin-bottom: 0">-</h5></TD>';
                        } else {
                            str += '<TD><h5 id="withdrawAmount" class="text-center" style="margin-bottom: 0">-</h5></TD>' + '<TD><h5 class="text-center" style="margin-bottom: 0">' + formatNumberWithCommas(result[i].amount) + '</h5></TD>';
                        }

                        str += '<TD><h5 id="type" class="text-center" style="margin-bottom: 0">' + result[i].type + '</TD>';
                        if (result[i].type === '환전' || result[i].type === '재환전') {
                            str += '<TD><h5 id="afterBalance" class="text-center" style="margin-bottom: 0">' + formatNumberWithCommas(result[i].balance) + '</TD>';
                        } else {
                            str += '<TD><h5 id="afterBalance" class="text-center" style="margin-bottom: 0">' + formatNumberWithCommas(result[i].balance) + '</TD>';
                        }


                        str += '</TR>';
                    });
                    $("#dateSelectHistory").append(str);
                },
                error: function (result, status) {

                },
            })
        });


        // 차트 그리기
        document.addEventListener("DOMContentLoaded", function () {
            var data = [];
            data.push(${walletDetailDto.balance.get("KRW")});
            data.push(${usdDto.expectedAmount});
            data.push(${jpyDto.expectedAmount});
            console.log(data);

            var options = {
                // 추후 매개변수로 변경 필요
                series: data,
                chart: {
                    type: 'donut',
                },
                labels: ['KRW', 'USD', 'JPY'],
                responsive: [{
                    breakpoint: 480,
                    options: {
                        chart: {
                            width: 200
                        },
                        legend: {
                            position: 'bottom'
                        }
                    }
                }]
            };

            var chart = new ApexCharts(document.querySelector("#chart"), options);
            chart.render();
        });
    </script>

</head>
<body style="font-family: NanumSquareNeo-Variable,serif">
<jsp:include page="/WEB-INF/views/common/navbar.jsp"/>

<div class="pageWrap">
    <div class="center">
        <div class="row">
            <%-- 지갑 정보 --%>
            <div class="col-md-6 col-lg-6 col-xl-6 mb-4 h-100">
                <h6 class="text-muted">지갑 정보</h6>
                <div class="card h-20">
                    <div class="card-header d-flex align-items-center justify-content-between pb-0">
                        <div class="card-title mb-0">
                            <h2 class="m-0 me-2">지갑 보유내역</h2>
                            <small class="text-muted">원화 외화 비율</small>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center mb-3" style="position: relative;">
                            <div class="d-flex flex-column align-items-center gap-1">
                                <h2 class="mb-2">
                                    <fmt:formatNumber
                                            value="${walletDetailDto.balance.get(&quot;KRW&quot;) + jpyDto.expectedAmount + usdDto.expectedAmount}"
                                            type="number" pattern="#,###"/>
                                    ₩</h2>
                                <span>총 보유금</span>
                            </div>

                            <div id="chart"></div>
                        </div>
                        <ul class="p-0 m-0">
                            <li class="d-flex mb-4 pb-1">
                                <div class="avatar flex-shrink-0 me-3">
                                <span class="avatar-initial rounded bg-label-secondary"><img
                                        src="https://emojiguide.com/wp-content/uploads/platform/apple/43847.png"></span>
                                </div>
                                <div class="d-flex w-100 flex-wrap align-items-center justify-content-between gap-2">
                                    <div class="me-2">
                                        <h4 class="mb-0">KRW</h4>
                                        <small class="text-muted">대한민국 원</small>
                                    </div>
                                    <h5 class="user-progress">
                                        <fmt:formatNumber value="${walletDetailDto.balance.get(&quot;KRW&quot;)}"
                                                          type="number" pattern="#,###"/> KRW
                                    </h5>
                                </div>
                            </li>
                            <li class="d-flex mb-4 pb-1">
                                <div class="avatar flex-shrink-0 me-3">
                                <span class="avatar-initial rounded bg-label-secondary"><img
                                        src="https://emojiguide.com/wp-content/uploads/platform/apple/44356.png"></span>
                                </div>
                                <div class="d-flex w-100 flex-wrap align-items-center justify-content-between gap-2">
                                    <div class="me-2">
                                        <h4 class="mb-0">USD</h4>
                                        <small class="text-muted">미 달러</small>
                                    </div>
                                    <h5 class="user-progress">
                                        <fmt:formatNumber value="${walletDetailDto.balance.get(&quot;USD&quot;)}"
                                                          type="number" pattern="#,###"/> USD
                                    </h5>
                                </div>
                            </li>
                            <li class="d-flex mb-4 pb-1">
                                <div class="avatar flex-shrink-0 me-3">
                                <span class="avatar-initial rounded bg-label-secondary"><img
                                        src="https://emojiguide.com/wp-content/uploads/platform/apple/43839.png"></span>
                                </div>
                                <div class="d-flex w-100 flex-wrap align-items-center justify-content-between gap-2">
                                    <div class="me-2">
                                        <h4 class="mb-0">JPY</h4>
                                        <small class="text-muted">일본 엔</small>
                                    </div>
                                    <h5 class="user-progress">
                                        <fmt:formatNumber value="${walletDetailDto.balance.get(&quot;JPY&quot;)}"
                                                          type="number" pattern="#,###"/> JPY
                                    </h5>
                                </div>
                            </li>
                            <a href="/personalwallet/depositForm" class="btn btn-primary">채우기</a>
                            <a href="/personalwallet/withdrawForm" class="btn btn-primary">꺼내기</a>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="col-md-6 col-lg-6 col-xl-6 mb-4 h-100">
                <h6 class="text-muted">환차익 정보</h6>
                <div class="card h-20" style="margin-bottom: 10px">
                    <div class="card-header d-flex align-items-center justify-content-between pb-0">
                        <div class="card-title mb-0">
                            <h2 class="m-0 me-2">환차익 내역</h2>
                            <small class="text-muted">환전에 의한 손익</small>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center mb-3" style="position: relative;">
                            <div class="d-flex flex-column align-items-center gap-1">
                                <h3 class="mb-2">
                                    <fmt:formatNumber
                                            value="${walletDetailDto.balance.get(&quot;KRW&quot;) + jpyDto.expectedAmount + usdDto.expectedAmount}"
                                            type="number" pattern="#,###"/>
                                    ₩</h3>
                                <span>현재 총 보유금</span>
                            </div>

                            <div class="text-center">
                                <h1 class="text-center">
                                    <c:choose>
                                        <c:when test="${walletDetailDto.balance['KRW'] + jpyDto.expectedAmount + usdDto.expectedAmount > walletDetailDto.balance['KRW'] + surplus}">
                                            <fmt:formatNumber
                                                    value="${walletDetailDto.balance['KRW'] + jpyDto.expectedAmount + usdDto.expectedAmount - (walletDetailDto.balance['KRW'] + surplus)}"
                                                    type="number" pattern="#,###"/> 원 <span
                                                style="color: red; font-size: 55px"> ↑ </span> &nbsp&nbsp&nbsp&nbsp
                                        </c:when>
                                        <c:otherwise>
                                            fmt:formatNumber
                                            value="${walletDetailDto.balance['KRW'] + surplus - (walletDetailDto.balance['KRW'] + jpyDto.expectedAmount + usdDto.expectedAmount)}"
                                            type="number" pattern="#,###"/> 원 <span
                                                style="color: #007bff; font-size: 55px"> ↓ </span> &nbsp&nbsp&nbsp&nbsp
                                        </c:otherwise>
                                    </c:choose>
                                </h1>
                            </div>
                        </div>

                        <div class="d-flex justify-content-between align-items-center mb-3" style="position: relative;">
                            <div class="d-flex flex-column align-items-center gap-1">
                                <h3 class="mb-2">
                                    <fmt:formatNumber
                                            value="${walletDetailDto.balance.get(&quot;KRW&quot;) + surplus}"
                                            type="number" pattern="#,###"/> ₩
                                </h3>
                                <span>환전 이전 보유금</span>
                            </div>
                            <div class="text-center">
                                <h4 class="text-center" style="font-family: NeoDunggeunmoPro-Regular">
                                    <c:choose>
                                        <c:when test="${walletDetailDto.balance['KRW'] + jpyDto.expectedAmount + usdDto.expectedAmount > walletDetailDto.balance['KRW'] + surplus}">
                                            ✈️ 환차익을 보셨네요! ✈️
                                        </c:when>
                                        <c:otherwise>
                                            ✈️ 환차익을 노려보세요! ✈️
                                        </c:otherwise>
                                    </c:choose>
                                </h4>
                            </div>
                        </div>
                    </div>
                </div>
                <h6 class="text-muted">카드 정보</h6>
                <div class="card h-100">
                    <div class="card-header d-flex align-items-center justify-content-between pb-0"
                         style="margin-top: 0">
                        <div class="card-title mb-0">
                            <h4 class="m-0 me-2">카드 정보</h4>
                            <small class="text-muted">개인 카드 연결</small>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6 col-lg-6 col-xl-6 mb-4 h-100">
                                <img
                                        src="${pageContext.request.contextPath}/assets/img/card/card${fn:substring(cardNumber.cardNumber, fn:length(cardNumber.cardNumber)-1, fn:length(cardNumber.cardNumber))}.png"
                                        alt="Card Image" style="width: 88%">
                            </div>
                            <div class="col-md-6 col-lg-6 col-xl-6 mb-4 h-100">
                                <div id="result">
                                    <h3 class="open-modal text-center">연결 중</h3>
                                    <h1 class="text-center"><i class="material-icons text-center"
                                                               style="font-size: 40px;color: green"> credit_card </i>
<%--                                    </h1>--%>
<%--                                    <c:choose>--%>
<%--                                        <c:when test="${connected}">--%>
<%--                                            <h3 class="text-center">연결 중&nbsp&nbsp&nbsp&nbsp<i class="material-icons"--%>
<%--                                                                                               style="color: green">credit_card</i>--%>
<%--                                            </h3>--%>
<%--                                        </c:when>--%>
<%--                                        <c:otherwise>--%>
<%--                                            <h3 class="open-modal text-center">연결 가능</h3>--%>
<%--                                            <h1 id="can-connect-card" class="text-center"><i--%>
<%--                                                    class="material-icons text-center" style="font-size: 40px">--%>
<%--                                                credit_card </i></h1>--%>
<%--                                        </c:otherwise>--%>
<%--                                    </c:choose>--%>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="card">
            <h5 class="card-header">
                <div class="row g-2">
                    <h2 class="col mb-0">
                        거래 내역
                    </h2>
                </div>
            </h5>

            <div class="table-responsive text-nowrap">
                <table class="table table">
                    <thead>
                    <tr>
                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i><h4 class="text-center"
                                                                                     style="margin-bottom: 0">거래일자</h4>
                        </th>
                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i><h4 class="text-center"
                                                                                     style="margin-bottom: 0">거래시간</h4>
                        </th>
                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i><h4 class="text-center"
                                                                                     style="margin-bottom: 0">입금액</h4>
                        </th>
                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i><h4 class="text-center"
                                                                                     style="margin-bottom: 0">출금액</h4>
                        </th>
                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i><h4 class="text-center"
                                                                                     style="margin-bottom: 0">내용</h4>
                        </th>
                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i><h4 class="text-center"
                                                                                     style="margin-bottom: 0">잔액</h4>
                        </th>
                    </tr>
                    </thead>
                    <tbody class="table-border-bottom-0" id="dateSelectHistory">
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <!--/ Striped Rows -->
</div>
<br>
<br>
<br>
<br>
<br>
<br>


<!-- Modal -->
<div class="modal fade" id="detailModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h2 class="modal-title" id="exampleModalLabel11">거래상세내역</h2>
            </div>
            <div class="modal-body" style="margin: 20px">
                <div class="row">
                    <div class="row g-2" style="margin-bottom: 20px">
                        <div class="col mb-3">
                            <h3 style="margin-bottom: 0">거래 날짜</h3>
                            <br>
                            <h4 id="detail-date"></h4>
                        </div>

                        <div class="col mb-3">
                            <h3 style="margin-bottom: 0">거래 시간</h3>
                            <br>
                            <h4 id="detail-time"></h4>
                        </div>
                    </div>

                    <div class="row g-2" style="margin-bottom: 20px">
                        <div class="col mb-3">
                            <h3 style="margin-bottom: 0">거래종류</h3>
                            <br>
                            <h4 id="detail-type"></h4>
                        </div>
                    </div>

                    <div class="row g-2" style="margin-bottom: 20px">
                        <div class="col mb-3">
                            <h3 style="margin-bottom: 0">상세내용</h3>
                            <br>
                            <h4 id="detail-content"></h4>
                        </div>
                    </div>


                    <div class="row g-2" style="margin-bottom: 20px">
                        <div class="col mb-0">
                            <h3 style="margin-bottom: 0">금액</h3>
                            <br>
                            <div class="col mb-3">
                                <h4 id="detail-amount"></h4>
                            </div>
                        </div>
                        <div class="col mb-0">
                            <h3 style="margin-bottom: 0">거래후 잔액</h3>
                            <br>
                            <div class="col mb-3">
                                <h4 id="detail-balance"></h4>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-bs-dismiss="modal">
                    확인
                </button>
            </div>
        </div>
    </div>
</div>

<%--카드 변경 모달창--%>
<div class="modal fade" id="changeWallet" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" id="changeWalletLabel">카드 변경</h3>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="changeWalletBody">
                카드 연결을 변경하시겠습니까?
            </div>
            <div class="modal-footer">
                <input type="hidden" name="connect-memberId">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                <button type="button" class="btn btn-primary" id="change-confirm-button" data-bs-dismiss="modal">변경
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
