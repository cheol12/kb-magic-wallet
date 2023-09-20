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
    <script scr="../../../assets/js/common.js"></script>

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
            document.getElementById("detail-type").innerHTML = row_td[4].innerHTML;
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
                        console.log(result[i].dateTime)
                        var dateTime = new Date(result[i].dateTime);
                        var detailString = typeof result[i].detail === 'object' ? JSON.stringify(result[i].detail) : result[i].detail;
                        // 날짜와 시간을 따로 추출
                        var date = dateTime.toLocaleDateString(); // 날짜 형식으로 변환
                        var time = dateTime.toLocaleTimeString(); // 시간 형식으로 변환
                        console.log(date);
                        console.log(time);

                        str += '<TR id="searchDateResult" onclick="PopupDetail(this, \'' + detailString  + '\')" data-bs-toggle="modal" data-bs-target="#detailModal">'
                        // 날짜 시간 처리
                        str += '<TD>' + date + '</TD>';
                        str += '<TD>' + time + '</TD>';
                        // 입금액 출금액 처리
                        if (result[i].type === '입금') {
                            str += '<TD> 입금액: ' + formatNumberWithCommas(result[i].amount) + ' ' + result[i].currencyCode + '</TD><TD> 출금액: -</TD>';
                        } else {
                            str += '<TD> 입금액: -</TD>' + '<TD> 출금액: ' + formatNumberWithCommas(result[i].amount) + ' ' + result[i].currencyCode + '</TD>';
                        }
                        str += '<TD>' + result[i].type + '</TD>';
                        str += '<TD>' + formatNumberWithCommas(result[i].balance) + ' ' + result[i].currencyCode + '</TD>';
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

            // 모달 닫힌 후에 스크롤, 배경색 관련 처리
            $("#basicModal").on("hidden.bs.modal", function () {

                // 모달이 완전히 사라진 후에 배경색 변경 및 스크롤 관련 처리
                $("body").removeClass("modal-open");
                $(".modal-backdrop").remove();

                // 필요한 스크롤 관련 설정
                $("body").css("overflow", "auto");
                // 여기에서 스크롤을 허용하도록 설정하는 코드를 추가해야 합니다.
            });
        });

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
<body>
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
                            <h5 class="m-0 me-2">지갑 보유내역</h5>
                            <small class="text-muted">원화 외화 비율</small>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center mb-3" style="position: relative;">
                            <div class="d-flex flex-column align-items-center gap-1">
                                <h2 class="mb-2" >
                                    <fmt:formatNumber value="${walletDetailDto.balance.get(&quot;KRW&quot;) + usdDto.expectedAmount + usdDto.expectedAmount}" type="number" pattern="#,###" />
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
                                        <h6 class="mb-0">KRW</h6>
                                        <small class="text-muted">대한민국 원</small>
                                    </div>
                                    <div class="user-progress">
                                        <fmt:formatNumber value="${walletDetailDto.balance.get(&quot;KRW&quot;)}" type="number" pattern="#,###" /> KRW
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
                                        <fmt:formatNumber value="${walletDetailDto.balance.get(&quot;USD&quot;)}" type="number" pattern="#,###" /> USD
                                        USD
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
                                        <fmt:formatNumber value="${walletDetailDto.balance.get(&quot;JPY&quot;)}" type="number" pattern="#,###" /> JPY
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
                    <div class="card">
                        <div class="card-header d-flex align-items-center justify-content-between pb-0">
                            <div class="card-title mb-0">
                                <h5 class="m-0 me-2">자산현황(KRW)</h5>
                            </div>
                        </div>
                        <div id="totalBalance"></div>
                    </div>
                </div>
            </div>
        </div>


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
                                                <button type="submit" class="btn btn-primary" id="submitButton">조회
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
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

                <div class="col mb-0">
                    <div class="col mb-0 col-lg-5 col-md-auto">
                        <!-- Modal -->
                        <div class="modal fade" id="detailModal" tabindex="-1" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel11">거래상세내역</h5>
                                    </div>
                                    <div class="modal-body">
                                        <div class="row">
                                            <div class="row g-2">
                                                <div class="col mb-3">
                                                    <label class="form-label">거래 날짜</label>
                                                    <div id="detail-date"></div>
                                                </div>

                                                <div class="col mb-3">
                                                    <label class="form-label">거래 시간</label>
                                                    <div id="detail-time"></div>
                                                </div>
                                            </div>

                                            <div class="row g-2">
                                                <div class="col mb-3">
                                                    <label class="form-label">거래종류</label>
                                                    <div id="detail-type"></div>
                                                </div>
                                            </div>

                                            <div class="row g-2">
                                                <div class="col mb-3">
                                                    <label class="form-label">상세내용</label>
                                                    <div id="detail-content"></div>
                                                </div>
                                            </div>



                                            <div class="row g-2">
                                                <div class="col mb-0">
                                                    <label class="form-label">금액</label>
                                                    <div class="col mb-3">
                                                        <div id="detail-amount"></div>
                                                    </div>
                                                </div>
                                                <div class="col mb-0">
                                                    <label class="form-label">거래후 잔액</label>
                                                    <div class="col mb-3">
                                                        <div id="detail-balance"></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">
                                            Close
                                        </button>
                                        <button type="button" class="btn btn-primary">Save</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--/ Striped Rows -->

</div>
</body>
</html>
