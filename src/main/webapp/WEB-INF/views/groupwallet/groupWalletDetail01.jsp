<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <script type="text/javascript">

        // 모달창을 띄우는 function
        function PopupDetail(clicked_element, content) {
            var row_td = clicked_element.getElementsByTagName("td");
            var modal = document.getElementById("detail-modal");

            document.getElementById("detail-date").innerHTML = row_td[0].innerHTML;
            document.getElementById("detail-time").innerHTML = row_td[1].innerHTML;
            if (row_td[2].innerHTML === "입금액: -") {
                document.getElementById("detail-amount").innerHTML = row_td[3].innerHTML;
            } else {
                document.getElementById("detail-amount").innerHTML = row_td[2].innerHTML;
            }
            document.getElementById("detail-content").innerHTML = content;
            document.getElementById("detail-balance").innerHTML = row_td[5].innerHTML;
            modal.style.display = 'block';
        }

        // AJAX READY
        $(document).ready(function () {

            // 로딩 되자마자 거래 내역 리스트 비동기화 통신
            $.ajax({
                url: "/personalwallet/selectDate",
                type: "post",
                dataType: "json",
                success: function (result, status) {
                    // 화면에 갱신
                    var str = "";
                    $.each(result, function (i) {
                        str += '<TR id="searchDateResult" onclick="PopupDetail(this)" data-bs-toggle="modal" data-bs-target="#detailModal">'
                        // 날짜 시간 처리
                        str += '<TD>' + result[i].dateTime + '</TD>';
                        str += '<TD>' + result[i].dateTime + '</TD>';
                        // 입금액 출금액 처리
                        if (result[i].type === '입금') {
                            str += '<TD> 입금액: ' + result[i].amount + ' ' + result[i].currencyCode + '</TD><TD> 출금액: -</TD>';
                        } else {
                            str += '<TD> 입금액: -</TD>' + '<TD> 출금액: ' + result[i].amount + ' ' + result[i].currencyCode + '</TD>';
                        }
                        str += '<TD>' + result[i].type + '</TD>';
                        str += '<TD>' + result[i].balance + ' ' + result[i].currencyCode + '</TD>';
                        str += '</TR>';
                    });
                    $("#dateSelectHistory").append(str);
                },
                error: function (result, status) {

                },
            })

            // 조회기간 설정 조회 버튼 누를 시 비동기화 통싱
            $("#selectDateForm").on("submit", function (e) {
                e.preventDefault()
                var formValues = $("form[name=selectDateForm]").serialize();
                $.ajax({
                    url: "/personalwallet/selectDate",
                    type: "post",
                    dataType: "json",
                    data: formValues,
                    success: function (result, status) {
                        $("#dateSelectHistory").empty();
                        // 화면에 갱신
                        var str = "";
                        $.each(result, function (i) {
                            str += '<TR id="searchDateResult" onclick="PopupDetail(this)" data-bs-toggle="modal" data-bs-target="#detailModal">'
                            // 날짜 시간 처리
                            str += '<TD>' + result[i].dateTime + '</TD>';
                            str += '<TD>' + result[i].dateTime + '</TD>';
                            // 입금액 출금액 처리
                            if (result[i].type === '입금') {
                                str += '<TD> 입금액: ' + result[i].amount + ' ' + result[i].currencyCode + '</TD><TD> 출금액: -</TD>';
                            } else {
                                str += '<TD> 입금액: -</TD>' + '<TD> 출금액: ' + result[i].amount + ' ' + result[i].currencyCode + '</TD>';
                            }
                            str += '<TD>' + result[i].type + '</TD>';
                            str += '<TD>' + result[i].balance + ' ' + result[i].currencyCode + '</TD>';
                            str += '</TR>';
                        });
                        $("#dateSelectHistory").append(str);
                    },
                    error: function (result, status) {

                    },
                })
            });

            // 모달 닫기 (조회기간 설정 버튼 누른 후)
            $("#submitButton").on("click", function () {
                $("#basicModal").modal("hide");
            });

            // // 모달 닫힌 후에 스크롤, 배경색 관련 처리
            // $("#basicModal").on("hidden.bs.modal", function () {
            //
            //     // 모달이 완전히 사라진 후에 배경색 변경 및 스크롤 관련 처리
            //     $("body").removeClass("modal-open");
            //     $(".modal-backdrop").remove();
            //
            //     // 필요한 스크롤 관련 설정
            //     $("body").css("overflow", "auto");
            //     // 여기에서 스크롤을 허용하도록 설정하는 코드를 추가해야 합니다.
            // });

            $("#basicModal").on("hidden.bs.modal", function () {
                // 모달이 완전히 사라진 후에 배경색 변경 및 스크롤 관련 처리
                $("body").removeClass("modal-open");

                // 필요한 스크롤 관련 설정
                $("body").css("overflow", "auto");

                // 모달 백드롭 제거
                $(".modal-backdrop").remove();
            });

            $("#detailModal").on("hidden.bs.modal", function () {
                // 모달이 완전히 사라진 후에 배경색 변경 및 스크롤 관련 처리
                $("body").removeClass("modal-open");

                // 필요한 스크롤 관련 설정
                $("body").css("overflow", "auto");

                // 모달 백드롭 제거
                $(".modal-backdrop").remove();
            });
        });
    </script>

</head>
<body>
<jsp:include page="/WEB-INF/views/common/navbar.jsp"/>

<div class="pageWrap">
    <div class="center">
        <div class="row">
            <div class="col-sm-6">
                <div class="card d-flex flex-column h-100">
                    <div class="card-body">
                        <h3 class="card-title">모임지갑 : ${groupWallet.nickname}</h3>
                        <br>
                        <h4>  ${walletDetailDto.balance.get("KRW")}₩ </h4>
                        <br><br>
                        <a href="${pageContext.request.contextPath}/group-wallet/${id}/deposit" class="btn btn-primary">채우기</a>
                        <a href="${pageContext.request.contextPath}/group-wallet/${id}/withdraw" class="btn btn-primary">꺼내기</a>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="card d-flex flex-column h-100">
                    <div class="card-body">
                        <h3 class="card-title">외화별 잔액</h3>
                        <br>
                        <h4 class="card-title"><img src="https://oimg1.kbstar.com/img/obank/2015/fund/icn_usd.png"
                                                    alt="USD"> ${walletDetailDto.getBalance().get("USD")} $</h4>
                        <h4 class="card-title"><img src="https://oimg1.kbstar.com/img/obank/2015/fund/icn_jpy.png"
                                                    alt="JPY"> ${walletDetailDto.getBalance().get("JPY")} ￥</h4>
                    </div>
                </div>
            </div>
        </div>
        <br>

        <div class="row">
            <div class="col-md-10 col-lg-10 col-xl-6 order-0 mb-4">
                <h6 class="text-muted">지갑 정보</h6>
                <div class="card h-20">
                    <div class="card-header d-flex align-items-center justify-content-between pb-0">
                        <div class="card-title mb-0">
                            <h5 class="m-0 me-2">지갑 보유내역</h5>
                            <small class="text-muted">원화 외화 비율</small>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center mb-3" style="position: relative;">
                            <div class="d-flex flex-column align-items-center gap-1">
                                <%--                            여기서 환율 계산해서 총액을 표시--%>
                                <%--                            환율 정보를 받아오는 DTO 필요--%>
                                <h2 class="mb-2">${walletDetailDto.balance.get("KRW")
                                        +usdExchangeRate.tradingBaseRate*walletDetailDto.balance.get("USD")
                                        +jpyExchangeRate.tradingBaseRate*walletDetailDto.balance.get("JPY")}₩</h2>
                                <span>총 보유금</span>
                            </div>

                            <div id="orderStatisticsChart" style="min-height: 137.55px;">
                                <div id="apexchartsyqrmetfxj"
                                     class="apexcharts-canvas apexchartsyqrmetfxj apexcharts-theme-light"
                                     style="width: 130px; height: 137.55px;">
                                    <svg id="SvgjsSvg2585" width="130" height="137.55"
                                         xmlns="http://www.w3.org/2000/svg"
                                         version="1.1" xmlns:xlink="http://www.w3.org/1999/xlink"

                                         transform="translate(0, 0)" style="background: transparent;">
                                        <g id="SvgjsG2587" class="apexcharts-inner apexcharts-graphical"
                                           transform="translate(-7, 0)">
                                            <defs id="SvgjsDefs2586">
                                                <clipPath id="gridRectMaskyqrmetfxj">
                                                    <rect id="SvgjsRect2589" width="150" height="173" x="-4.5" y="-2.5"
                                                          rx="0" ry="0" opacity="1" stroke-width="0" stroke="none"
                                                          stroke-dasharray="0" fill="#fff"></rect>
                                                </clipPath>
                                                <clipPath id="forecastMaskyqrmetfxj"></clipPath>
                                                <clipPath id="nonForecastMaskyqrmetfxj"></clipPath>
                                                <clipPath id="gridRectMarkerMaskyqrmetfxj">
                                                    <rect id="SvgjsRect2590" width="145" height="172" x="-2" y="-2"
                                                          rx="0"
                                                          ry="0" opacity="1" stroke-width="0" stroke="none"
                                                          stroke-dasharray="0" fill="#fff"></rect>
                                                </clipPath>
                                            </defs>
                                            <g id="SvgjsG2591" class="apexcharts-pie">
                                                <g id="SvgjsG2592" transform="translate(0, 0) scale(1)">
                                                    <circle id="SvgjsCircle2593" r="44.835365853658544" cx="70.5"
                                                            cy="70.5"
                                                            fill="transparent"></circle>
                                                    <g id="SvgjsG2594" class="apexcharts-slices">
                                                        <g id="SvgjsG2595"
                                                           class="apexcharts-series apexcharts-pie-series"
                                                           seriesName="Electronic" rel="1" data:realIndex="0">
                                                            <path id="SvgjsPath2596"
                                                                  d="
                                                                  M 70.5 10.71951219512195
                                                                  A 59.78048780487805 59.78048780487805 0 0 1 97.63977353321047 123.7648046533095
                                                                  L 90.85483014990785 110.44860348998213
                                                                  A 44.835365853658544 44.835365853658544 0 0 0 70.5 25.664634146341456
                                                                  L 70.5 10.71951219512195
                                                                  z"
                                                                  fill="rgba(105,108,255,1)" fill-opacity="1"
                                                                  stroke-opacity="1" stroke-linecap="butt"
                                                                  stroke-width="5"
                                                                  stroke-dasharray="0"
                                                                  class="apexcharts-pie-area apexcharts-donut-slice-0"
                                                                  index="0" j="0" data:angle="153" data:startAngle="0"
                                                                  data:strokeWidth="5" data:value="85"
                                                                  data:pathOrig="M 70.5 10.71951219512195 A 59.78048780487805 59.78048780487805 0 0 1 97.63977353321047 123.7648046533095 L 90.85483014990785 110.44860348998213 A 44.835365853658544 44.835365853658544 0 0 0 70.5 25.664634146341456 L 70.5 10.71951219512195 z"
                                                                  stroke="#ffffff"></path>
                                                        </g>
                                                        <g id="SvgjsG2599"
                                                           class="apexcharts-series apexcharts-pie-series"
                                                           seriesName="Decor" rel="2" data:realIndex="1">
                                                            <path id="SvgjsPath2600"
                                                                  d="
                                                                  M 70.5 130.28048780487805
                                                                  A 59.78048780487805 59.78048780487805 0 0 1 10.71951219512195 70.50000000000001
                                                                  L 25.664634146341456 70.5 A 44.835365853658544 44.835365853658544 0 0 0 70.5 115.33536585365854
                                                                  L 70.5 130.28048780487805
                                                                  z"
                                                                  fill="rgba(3,195,236,1)" fill-opacity="1"
                                                                  stroke-opacity="1" stroke-linecap="butt"
                                                                  stroke-width="5"
                                                                  stroke-dasharray="0"
                                                                  class="apexcharts-pie-area apexcharts-donut-slice-2"
                                                                  index="0" j="1" data:angle="90" data:startAngle="180"
                                                                  data:strokeWidth="5" data:value="50"
                                                                  data:pathOrig="M 70.5 130.28048780487805 A 59.78048780487805 59.78048780487805 0 0 1 10.71951219512195 70.50000000000001 L 25.664634146341456 70.5 A 44.835365853658544 44.835365853658544 0 0 0 70.5 115.33536585365854 L 70.5 130.28048780487805 z"
                                                                  stroke="#ffffff"></path>
                                                        </g>
                                                        <g id="SvgjsG2601"
                                                           class="apexcharts-series apexcharts-pie-series"
                                                           seriesName="Fashion" rel="3" data:realIndex="2">
                                                            <path id="SvgjsPath2602"
                                                                  d="M 10.71951219512195 70.50000000000001 A 59.78048780487805 59.78048780487805 0 0 1 70.48956633664653 10.719513105630845 L 70.4921747524849 25.664634829223125 A 44.835365853658544 44.835365853658544 0 0 0 25.664634146341456 70.5 L 10.71951219512195 70.50000000000001 z"
                                                                  fill="rgba(113,221,55,1)" fill-opacity="1"
                                                                  stroke-opacity="1" stroke-linecap="butt"
                                                                  stroke-width="5"
                                                                  stroke-dasharray="0"
                                                                  class="apexcharts-pie-area apexcharts-donut-slice-3"
                                                                  index="0" j="2" data:angle="90" data:startAngle="270"
                                                                  data:strokeWidth="5" data:value="50"
                                                                  data:pathOrig="M 10.71951219512195 70.50000000000001 A 59.78048780487805 59.78048780487805 0 0 1 70.48956633664653 10.719513105630845 L 70.4921747524849 25.664634829223125 A 44.835365853658544 44.835365853658544 0 0 0 25.664634146341456 70.5 L 10.71951219512195 70.50000000000001 z"
                                                                  stroke="#ffffff"></path>
                                                        </g>
                                                    </g>
                                                </g>
                                                <g id="SvgjsG2603" class="apexcharts-datalabels-group"
                                                   transform="translate(0, 0) scale(1)" style="opacity: 1;">
                                                    <text id="SvgjsText2604" font-family="Helvetica, Arial, sans-serif"
                                                          x="70.5" y="90.5" text-anchor="middle"
                                                          dominant-baseline="auto"
                                                          font-size="0.8125rem" font-weight="400" fill="#373d3f"
                                                          class="apexcharts-text apexcharts-datalabel-label"
                                                          style="font-family: Helvetica, Arial, sans-serif; fill: rgb(113, 221, 55);">
                                                        Weekly
                                                    </text>
                                                    <text id="SvgjsText2605" font-family="Public Sans" x="70.5" y="71.5"
                                                          text-anchor="middle" dominant-baseline="auto"
                                                          font-size="1.5rem"
                                                          font-weight="400" fill="#566a7f"
                                                          class="apexcharts-text apexcharts-datalabel-value"
                                                          style="font-family: &quot;Public Sans&quot;;">38%
                                                    </text>
                                                </g>
                                            </g>
                                            <line id="SvgjsLine2606" x1="0" y1="0" x2="141" y2="0" stroke="#b6b6b6"
                                                  stroke-dasharray="0" stroke-width="1" stroke-linecap="butt"
                                                  class="apexcharts-ycrosshairs"></line>
                                            <line id="SvgjsLine2607" x1="0" y1="0" x2="141" y2="0" stroke-dasharray="0"
                                                  stroke-width="0" stroke-linecap="butt"
                                                  class="apexcharts-ycrosshairs-hidden"></line>
                                        </g>
                                        <g id="SvgjsG2588" class="apexcharts-annotations"></g>
                                    </svg>
                                    <div class="apexcharts-legend"></div>
                                </div>
                            </div>
                        </div>
                        <ul class="p-0 m-0">
                            <li class="d-flex mb-4 pb-1">
                                <div class="avatar flex-shrink-0 me-3">
                                <span class="avatar-initial rounded bg-label-secondary"><img
                                        src="https://emojiguide.com/wp-content/uploads/platform/apple/43847.png"></span>
                                </div>
                                <div class="d-flex w-100 flex-wrap align-items-center justify-content-between gap-2">
                                    <div class="me-2">
                                        <h6 class="mb-0">KRW</h6>
                                        <small class="text-muted">대한민국 원</small>
                                    </div>
                                    <div class="user-progress">
                                        ${walletDetailDto.balance.get("KRW")} KRW
                                    </div>
                                </div>
                            </li>
                            <li class="d-flex mb-4 pb-1">
                                <div class="avatar flex-shrink-0 me-3">
                                <span class="avatar-initial rounded bg-label-secondary"><img
                                        src="https://emojiguide.com/wp-content/uploads/platform/apple/44356.png"></span>
                                </div>
                                <div class="d-flex w-100 flex-wrap align-items-center justify-content-between gap-2">
                                    <div class="me-2">
                                        <h6 class="mb-0">USD</h6>
                                        <small class="text-muted">미국 달러</small>
                                    </div>
                                    <div class="user-progress">
                                        ${walletDetailDto.balance.get("USD")} USD
                                    </div>
                                </div>
                            </li>
                            <li class="d-flex mb-4 pb-1">
                                <div class="avatar flex-shrink-0 me-3">
                                <span class="avatar-initial rounded bg-label-secondary"><img
                                        src="https://emojiguide.com/wp-content/uploads/platform/apple/43839.png"></span>
                                </div>
                                <div class="d-flex w-100 flex-wrap align-items-center justify-content-between gap-2">
                                    <div class="me-2">
                                        <h6 class="mb-0">JPY</h6>
                                        <small class="text-muted">일본 엔</small>
                                    </div>
                                    <div class="user-progress">
                                        ${walletDetailDto.balance.get("JPY")} JPY
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="col-xl-12">
                <h6 class="text-muted">환율 정보</h6>
                <div class="nav-align-top d-flex mb-8">
                    <ul class="nav nav-tabs flex-fill" role="tablist">
                        <li class="nav-item">
                            <button
                                    type="button"
                                    class="nav-link active"
                                    role="tab"
                                    data-bs-toggle="tab"
                                    data-bs-target="#navs-top-home"
                                    aria-controls="navs-top-home"
                                    aria-selected="true"
                            >
                                모임 거래 내역
                            </button>
                        </li>
                        <li class="nav-item">
                            <button
                                    type="button"
                                    class="nav-link"
                                    role="tab"
                                    data-bs-toggle="tab"
                                    data-bs-target="#navs-top-member"
                                    aria-controls="navs-top-member"
                                    aria-selected="false"
                            >
                                모임 멤버 조회
                            </button>
                        </li>
                        <li class="nav-item">
                            <button
                                    type="button"
                                    class="nav-link"
                                    role="tab"
                                    data-bs-toggle="tab"
                                    data-bs-target="#navs-top-rule"
                                    aria-controls="navs-top-rule"
                                    aria-selected="false"
                            >
                                모임 회비 규칙
                            </button>
                        </li>
                        <li class="nav-item">
                            <button
                                    type="button"
                                    class="nav-link"
                                    role="tab"
                                    data-bs-toggle="tab"
                                    data-bs-target="#navs-top-save"
                                    aria-controls="navs-top-save"
                                    aria-selected="false"
                            >
                                모임 적금 조회
                            </button>
                        </li>
                        <li class="nav-item">
                            <button
                                    type="button"
                                    class="nav-link"
                                    role="tab"
                                    data-bs-toggle="tab"
                                    data-bs-target="#navs-top-card"
                                    aria-controls="navs-top-card"
                                    aria-selected="false"
                            >
                                모임 연결 카드
                            </button>
                        </li>
                    </ul>
                    <div class="tab-content" style="padding: 0px">
                        <div class="tab-pane fade show active" id="navs-top-home" role="tabpanel">

                            <div class="card">
                                <h5 class="card-header">
                                    <div class="row g-2">
                                        <div class="col mb-0">
                                            거래 내역
                                        </div>
                                        <div class="col mb-0">
                                            <div class="col mb-0 col-lg-5 col-md-auto">
                                                <!-- Button trigger modal -->
                                                <button
                                                        type="button"
                                                        class="btn btn-primary"
                                                        data-bs-toggle="modal"
                                                        data-bs-target="#basicModal"
                                                >
                                                    조회 기간 설정
                                                </button>


                                            </div>
                                        </div>
                                    </div>
                                </h5>

                                <div class="table-responsive text-nowrap">
                                    <table class="table table">
                                        <thead>
                                        <tr>
                                            <th><i class="fab fa-angular fa-lg text-danger me-3"></i>거래일자</th>
                                            <th><i class="fab fa-angular fa-lg text-danger me-3"></i>거래시간</th>
                                            <th><i class="fab fa-angular fa-lg text-danger me-3"></i>입금()</th>
                                            <th><i class="fab fa-angular fa-lg text-danger me-3"></i>출금()</th>
                                            <th><i class="fab fa-angular fa-lg text-danger me-3"></i>내용</th>
                                            <th><i class="fab fa-angular fa-lg text-danger me-3"></i>잔액()</th>
                                        </tr>
                                        </thead>
                                        <tbody class="table-border-bottom-0" id="dateSelectHistory">

                                        </tbody>
                                    </table>


                                </div>

                            </div>

                        </div>

                        <div class="tab-pane fade" id="navs-top-member" role="tabpanel">
                            <c:forEach var="memberList" items="${groupMemberDtoList}" varStatus="status">
                                <div class="card" style="margin-top: 5px;">
                                    <div class="card-header">
                                            ${memberList.name}, ${memberList.roleToString}
                                    </div>
                                    <div class="card-body">

                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <div class="tab-pane fade" id="navs-top-rule" role="tabpanel">
                            <div class="card" style="margin-top: 5px;">
                                <div class="card-header">
                                    <c:choose>
                                    <c:when test="${groupWallet.dueCondition}">
                                    회비 규칙 ${groupWallet.dueCondition},
                                    <p></p>
                                    매월 : ${groupWallet.dueDate}일, ${groupWallet.due}원
                                    <br>
                                    현재 누적 회비 : ${groupWallet.dueAccumulation}원
                                    <c:choose>
                                    <c:when test="${isChairman == true}">
                                    <p>
                                        <a href="${pageContext.request.contextPath}/group-wallet/${id}/rule"
                                           class="btn btn-primary">회비 규칙 수정</a>
                                        </c:when>
                                        </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                        회비가 없습니다.
                                        <c:choose>
                                        <c:when test="${isChairman == true}">
                                        <!-- 모임장 일 때만 -->
                                        <!-- 회비 규칙 생성 폼으로 넘어가는 버튼 -->
                                        <a href="${pageContext.request.contextPath}/group-wallet/${id}/rule"
                                           class="btn btn-primary">회비 규칙 생성</a>
                                        </c:when>
                                        </c:choose>
                                        </c:otherwise>
                                        </c:choose>
                                </div>
                            </div>
                        </div>

                        <div class="tab-pane fade" id="navs-top-save" role="tabpanel">
                        </div>

                        <div class="tab-pane fade" id="navs-top-card" role="tabpanel">
                        </div>
                    </div>
                </div>
            </div>
        </div>



    </div>
    <!--/ Striped Rows -->


</div>


</div>

<!-- Modal -->
<div class="modal fade" id="basicModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel1">조회기간 설정</h5>
                <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                ></button>
            </div>
            <form action="/personalwallet/selectDate" method="post" id="selectDateForm"
                  name="selectDateForm">
                <div class="modal-body">
                    <div class="row g-2">
                        <div class="col mb-0">
                            <label for="startDate" class="form-label">시작일</label>
                            <input type="date" id="startDate" class="form-control"
                                   name="startDate"
                                   placeholder="DD / MM / YY"/>
                        </div>
                        <div class="col mb-0">
                            <label for="endDate" class="form-label">종료일</label>
                            <input type="date" id="endDate" class="form-control"
                                   name="endDate"
                                   placeholder="DD / MM / YY"/>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary"
                            data-bs-dismiss="modal">
                        취소
                    </button>
                    <button type="submit" class="btn btn-primary" id="submitButton">조회</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="col mb-0">
    <div class="col mb-0 col-lg-5 col-md-auto">
        <!-- Modal -->
        <div class="modal fade show" id="detailModal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel11">거래상세내역</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div>
                        <p>거래 날짜</p>
                        <p class="col mb-0" style="height: 50px" id="detail-date"
                           readonly>
                    </div>
                    <hr>
                    <div>
                        <p>거래 시간</p>
                        <p class="col mb-0" style="height: 50px" id="detail-time"
                           readonly>
                    </div>
                    <hr>
                    <div>
                        <p>금액</p>
                        <p class="col mb-0" style="height: 50px" id="detail-amount"
                           readonly>
                    </div>
                    <hr>
                    <div>
                        <p>상세 내용</p>
                        <p class="col mb-0" style="height: 50px" id="detail-content"
                           readonly>
                    </div>
                    <hr>
                    <div>
                        <p>거래후 잔액</p>
                        <p class="col mb-0" style="height: 50px" id="detail-balance"
                           readonly>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
