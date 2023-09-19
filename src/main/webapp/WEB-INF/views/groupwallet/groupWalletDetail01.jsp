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
    <title>깨비의 요술 지갑 - 모임지갑</title>

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

            // 모임지갑 상세내역
            $.ajax({
                url: "${pageContext.request.contextPath}/group-wallet/${id}/history",
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
                            str += '<TD> ' + result[i].amount + ' ' + result[i].currencyCode + '</TD><TD> </TD>';
                        } else {
                            str += '<TD> </TD>' + '<TD> ' + result[i].amount + ' ' + result[i].currencyCode + '</TD>';
                        }
                        str += '<TD>  ' + result[i].type + '</TD>';
                        str += '<TD>' + result[i].balance + ' ' + result[i].currencyCode + '</TD>';
                        str += '</TR>';
                    });
                    $("#dateSelectHistory").append(str);
                },
                error: function (result, status) {

                },
            })


            // 모임지갑 모임원 리스트 조회
            function memberCall() {
                let myMemberId = ${loginMemberDto.memberId};
                let isChairman = ${isChairman};

                // 이후 JavaScript 코드에서 myMemberId 변수를 사용할 수 있음

                $.ajax({
                    url: "${pageContext.request.contextPath}/group-wallet/${id}/member-list",
                    type: "post",
                    dataType: "json",
                    success: function (result, status) {
                        // 화면에 갱신
                        var str = "";
                        $.each(result, function (i) {
                            str += '<tr id="searchMemberResult">'
                            str += '<td>' + result[i].name + '</td>';
                            str += '<td>' + result[i].roleToString + result[i].memberId + '</td>';

                            // memberId가 자신의 memberId와 일치하지 않는 경우에만 강퇴 버튼 생성
                            if (isChairman && (result[i].memberId !== myMemberId)) {
                                str += '<td><button class="alert-warning" data-member-id="' + result[i].memberId + '" data-member-name="' + result[i].name + '">강퇴</button></td>';
                            } else {
                                str += '<td></td>'; // 자신의 memberId와 일치하면 빈 칸 생성
                            }

                            str += '</tr>';
                        });
                        $("#getMemberList").empty();
                        $("#getMemberList").append(str);

                        // 강퇴 버튼 클릭 이벤트 핸들러
                        //    모임장 권한 아직
                    },
                    error: function (result, status) {
                        // 오류 처리
                    },
                });
            }

            memberCall();

            // 모임지갑에서 강퇴 버튼 클릭

            // $(document).on("click", , function(){ }) 형식을 쓰는 이유
            // = 동적 요소에 대한 이벤트 처리: 이 방식을 사용하면 페이지가 로드된 이후에
            // 동적으로 생성되는 요소에 대해서도 이벤트 처리를 할 수 있다
            $(document).on("click", '.alert-warning', function () {
                let memberId = $(this).data("member-id");
                let memberName = $(this).data("member-name")

                var confirmation = confirm(memberName + "님을 강퇴하시겠습니까?");

                if (confirmation) {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/group-wallet/${id}/out",
                        type: "post",
                        data: {memberId: memberId},
                        success: function (result, response) {
                            console.log(result);
                            if (result > 0) {
                                // 강퇴 성공 시 필요한 작업 수행
                                alert(memberName + "님을 강퇴했어요")
                                memberCall();
                            } else {
                                alert("강퇴를 실패했어요");
                            }
                        },
                        error: function () {
                            // 강퇴 실패 시 필요한 작업 수행
                        }
                    });
                } else {
                    alert("강퇴를 취소했습니다.");
                }

            });


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

            // 모달 닫힌 후에 스크롤, 배경색 관련 처리
            $("#basicModal").on("hidden.bs.modal", function () {

                // 모달이 완전히 사라진 후에 배경색 변경 및 스크롤 관련 처리
                $("body").removeClass("modal-open");
                $(".modal-backdrop").remove();

                // 필요한 스크롤 관련 설정
                $("body").css("overflow", "auto");
                // 여기에서 스크롤을 허용하도록 설정하는 코드를 추가해야 합니다.
            });

            function formatNumber(number) {
                return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            }
        });

        document.addEventListener("DOMContentLoaded", function () {
            var options = {
                // 추후 매개변수로 변경 필요
                series: [1010000, 100 * 1300, 100000],
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

        document.addEventListener("DOMContentLoaded", function () {
            var options = {
                series: [
                    {
                        name: "KRW",
                        data: [0, 100000, 200000, 900000, 600000, 800000, 1010000]
                    },
                    {
                        name: "USD",
                        data: [0, 0, 0, 0, 0, 50 * 1300, 100 * 1300]
                    },
                    {
                        name: "JPY",
                        data: [100000, 100000, 100000, 100000, 100000, 100000, 100000]
                    }
                ],
                chart: {
                    height: 490,
                    type: 'line',
                    dropShadow: {
                        enabled: true,
                        color: '#000',
                        top: 18,
                        left: 7,
                        blur: 10,
                        opacity: 0.2
                    },
                    toolbar: {
                        show: false
                    }
                },
                colors: ['#77B6EA', '#545454', '#900000'],
                dataLabels: {
                    enabled: true,
                },
                stroke: {
                    curve: 'smooth'
                },
                title: {
                    text: '자산 현황 (KRW)',
                    align: 'left'
                },
                grid: {
                    borderColor: '#e7e7e7',
                    row: {
                        colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
                        opacity: 0.5
                    },
                },
                markers: {
                    size: 1
                },
                xaxis: {
                    categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul'],
                    title: {
                        text: '월'
                    }
                },
                yaxis: {
                    title: {
                        text: '자산'
                    },
                    min: 0,
                    max: 1500000
                },
                legend: {
                    position: 'top',
                    horizontalAlign: 'right',
                    floating: true,
                    offsetY: -25,
                    offsetX: -5
                }
            };

            var chart = new ApexCharts(document.querySelector("#totalBalance"), options);
            chart.render();
        });

        // 환율 그래프
        document.addEventListener("DOMContentLoaded", function () {
            var options = {
                series: [{
                    name: 'XYZ MOTORS',
                    data: [1, 2, 3, 4, 5]
                }],
                chart: {
                    type: 'area',
                    stacked: false,
                    height: 350,
                    zoom: {
                        type: 'x',
                        enabled: true,
                        autoScaleYaxis: true
                    },
                    toolbar: {
                        autoSelected: 'zoom'
                    }
                },
                dataLabels: {
                    enabled: false
                },
                markers: {
                    size: 0,
                },
                title: {
                    text: 'USD 환율',
                    align: 'left'
                },
                fill: {
                    type: 'gradient',
                    gradient: {
                        shadeIntensity: 1,
                        inverseColors: false,
                        opacityFrom: 0.5,
                        opacityTo: 0,
                        stops: [0, 90, 100]
                    },
                },
                yaxis: {
                    labels: {
                        formatter: function (val) {
                            return (val / 1000000).toFixed(0);
                        },
                    },
                    title: {
                        text: 'Price'
                    },
                },
                xaxis: {
                    type: 'datetime',
                },
                tooltip: {
                    shared: false,
                    y: {
                        formatter: function (val) {
                            return (val / 1000000).toFixed(0)
                        }
                    }
                }
            };

            var chart = new ApexCharts(document.querySelector("#exchangeChart"), options);
            chart.render();
        });


        document.getElementById("deleteButton").addEventListener("click", function (event) {
            if (${countMember} >
            1
        )
            {
                event.preventDefault();
                alert("모임원이 없을 때 모임 지갑을 삭제할 수 있습니다.");
            }
        });

    </script>

</head>
<body>
<jsp:include page="/WEB-INF/views/common/navbar.jsp"/>

<div class="pageWrap">
    <div class="center">
        <div class="row">

            <div class="col-md-6 col-lg-6 col-xl-6 mb-4 h-100">

                <h6 class="text-muted">${groupWallet.nickname}의 지갑 정보</h6>
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


                                <h2 class="mb-2">${walletDetailDto.balance.get("KRW")
                                        +usdExchangeRate.tradingBaseRate*walletDetailDto.balance.get("USD")
                                        +jpyExchangeRate.tradingBaseRate*walletDetailDto.balance.get("JPY")}₩</h2>
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
                                        <small class="text-muted">미 달러</small>
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
                            <a href="/personalwallet/depositForm" class="btn btn-primary">채우기</a>
                            <a href="/personalwallet/withdrawForm" class="btn btn-primary">꺼내기</a>
                        </ul>
                    </div>
                </div>
            </div>


            <%-- 자산 정보 --%>
            <div class="col-md-6 col-lg-6 col-xl-6 mb-4 h-100">
                <%--            <div class="col-md-6">--%>
                <h6 class="text-muted">자산 정보</h6>
                <div class="nav-align-top d-flex mb-8">
                    <div class="card h-20">
                        <div id="totalBalance"></div>
                    </div>
                </div>
            </div>
        </div>


        <div class="col-xl-12">
            <h6 class="text-muted"></h6>
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


                    <div class="tab-pane fade show" id="navs-top-member" role="tabpanel">

                        <div class="card">
                            <h5 class="card-header">
                                모임원 목록
                            </h5>

                            <div class="table-responsive text-nowrap">

                                <table class="table table">
                                    <thead>
                                    <tr>
                                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i>이름</th>
                                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i>역할</th>
                                    </tr>
                                    </thead>
                                    <tbody class="table-border-bottom-0" id="getMemberList">

                                    </tbody>
                                </table>

                            </div>

                        </div>

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
                        <div class="card" style="margin-top: 5px;">
                            <div class="card-header">

                            </div>
                            <div class="card-body">
                                <c:choose>
                                    <c:when test="${installmentDto == null}">
                                        <c:choose>
                                            <c:when test="${isChairman == null}">
                                                적금을 가입하세요!
                                                <a href="${pageContext.request.contextPath}/saving"
                                                   class="btn btn-primary">적금 보러가기</a>
                                            </c:when>
                                            <c:otherwise>
                                                적금이 없어요ㅜㅜ 모임장에게 적금 가입 조르기!
                                            </c:otherwise>
                                        </c:choose>

                                    </c:when>
                                    <c:otherwise>
                                        적금명 : ${installmentDto.savingName},
                                        <br>
                                        금리 : ${installmentDto.interestRate}%,
                                        <br>
                                        기간 : ${installmentDto.period}개월,
                                        <br>
                                        가입일 : ${installmentDto.insertDate},
                                        <br>
                                        만기일 : ${installmentDto.maturityDate}
                                        <br>
                                        현재까지 : ${installmentDto.totalAmount}원
                                        <br>
                                        납입일 : 매월 ${installmentDto.savingDate}일
                                        <br>
                                        납입금 : ${installmentDto.savingAmount}원
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>

                    <div class="tab-pane fade" id="navs-top-card" role="tabpanel">
                        <c:forEach var="cardList" items="${cardIssuanceDtoList}" varStatus="status">
                            <div class="card" style="margin-top: 5px;">
                                <div class="card-header">

                                </div>
                                <div class="card-body">
                                    <c:choose>
                                        <c:when test="${cardList == null}">
                                            연결된 카드가 없어요!
                                            연결 버튼
                                        </c:when>
                                        <c:otherwise>
                                            ${cardList.cardNumber}
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <br>
        <br>
        <br>
        <br>

        <div class="col-xl-12">
            <c:choose>
                <c:when test="${isChairman == true}">
                    <button id="deleteButton">
                        <a href="${pageContext.request.contextPath}/group-wallet/${id}"
                           class="btn btn-primary">모임 지갑 삭제</a>
                    </button>
                </c:when>
                <c:otherwise>
                    <button>
                        <a href="${pageContext.request.contextPath}/group-wallet/${id}/out"
                           class="btn btn-primary">모임 지갑 탈퇴</a>
                    </button>
                </c:otherwise>
            </c:choose>
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

<p></p>
<br>
<br>
<br>
<br>
<br>
<br>

<footer>

</footer>
</body>
</html>
