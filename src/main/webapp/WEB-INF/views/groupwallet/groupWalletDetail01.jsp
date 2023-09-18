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

            // 로딩 되자마자 거래 내역 리스트 비동기화 통신
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


            // 로딩 되자마자 모임원 리스트 비동기 통신
            $.ajax({
                url: "${pageContext.request.contextPath}/group-wallet/${id}/member-list",
                type: "post",
                dataType: "json",
                success: function (result, status) {
                    // 화면에 갱신
                    var str = "";
                    $.each(result, function (i) {
                        str += '<TR id="searchMemberResult" onclick="" data-bs-toggle="" data-bs-target="">'
                        // 날짜 시간 처리
                        str += '<TD>' + result[i].name + '</TD>';
                        str += '<TD>' + result[i].roleToString + '</TD>';

                        str += '</TR>';
                    });
                    $("#getMemberList").append(str);
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

        document.getElementById("deleteButton").addEventListener("click", function(event) {
            if (${countMember} > 1) {
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


                                <h2 class="mb-2">1000000.0₩</h2>
                                <span>총 보유금</span>
                            </div>

                            <div id="chart" style="min-height: 182.7px;"><div id="apexchartsz0uz64eq" class="apexcharts-canvas apexchartsz0uz64eq apexcharts-theme-light" style="width: 300px; height: 182.7px;"><svg id="SvgjsSvg1973" width="300" height="182.7" xmlns="http://www.w3.org/2000/svg" version="1.1" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:svgjs="http://svgjs.dev" class="apexcharts-svg" xmlns:data="ApexChartsNS" transform="translate(0, 0)" style="background: transparent;"><foreignObject x="0" y="0" width="300" height="182.7"><div class="apexcharts-legend apexcharts-align-center apx-legend-position-right" xmlns="http://www.w3.org/1999/xhtml" style="position: absolute; left: auto; top: 24px; right: 5px;"><div class="apexcharts-legend-series" rel="1" seriesname="KRW" data:collapsed="false" style="margin: 2px 5px;"><span class="apexcharts-legend-marker" rel="1" data:collapsed="false" style="background: rgb(0, 143, 251) !important; color: rgb(0, 143, 251); height: 12px; width: 12px; left: 0px; top: 0px; border-width: 0px; border-color: rgb(255, 255, 255); border-radius: 12px;"></span><span class="apexcharts-legend-text" rel="1" i="0" data:default-text="KRW" data:collapsed="false" style="color: rgb(55, 61, 63); font-size: 12px; font-weight: 400; font-family: Helvetica, Arial, sans-serif;">KRW</span></div><div class="apexcharts-legend-series" rel="2" seriesname="USD" data:collapsed="false" style="margin: 2px 5px;"><span class="apexcharts-legend-marker" rel="2" data:collapsed="false" style="background: rgb(0, 227, 150) !important; color: rgb(0, 227, 150); height: 12px; width: 12px; left: 0px; top: 0px; border-width: 0px; border-color: rgb(255, 255, 255); border-radius: 12px;"></span><span class="apexcharts-legend-text" rel="2" i="1" data:default-text="USD" data:collapsed="false" style="color: rgb(55, 61, 63); font-size: 12px; font-weight: 400; font-family: Helvetica, Arial, sans-serif;">USD</span></div><div class="apexcharts-legend-series" rel="3" seriesname="JPY" data:collapsed="false" style="margin: 2px 5px;"><span class="apexcharts-legend-marker" rel="3" data:collapsed="false" style="background: rgb(254, 176, 25) !important; color: rgb(254, 176, 25); height: 12px; width: 12px; left: 0px; top: 0px; border-width: 0px; border-color: rgb(255, 255, 255); border-radius: 12px;"></span><span class="apexcharts-legend-text" rel="3" i="2" data:default-text="JPY" data:collapsed="false" style="color: rgb(55, 61, 63); font-size: 12px; font-weight: 400; font-family: Helvetica, Arial, sans-serif;">JPY</span></div></div><style type="text/css">

                                .apexcharts-legend {
                                    display: flex;
                                    overflow: auto;
                                    padding: 0 10px;
                                }
                                .apexcharts-legend.apx-legend-position-bottom, .apexcharts-legend.apx-legend-position-top {
                                    flex-wrap: wrap
                                }
                                .apexcharts-legend.apx-legend-position-right, .apexcharts-legend.apx-legend-position-left {
                                    flex-direction: column;
                                    bottom: 0;
                                }
                                .apexcharts-legend.apx-legend-position-bottom.apexcharts-align-left, .apexcharts-legend.apx-legend-position-top.apexcharts-align-left, .apexcharts-legend.apx-legend-position-right, .apexcharts-legend.apx-legend-position-left {
                                    justify-content: flex-start;
                                }
                                .apexcharts-legend.apx-legend-position-bottom.apexcharts-align-center, .apexcharts-legend.apx-legend-position-top.apexcharts-align-center {
                                    justify-content: center;
                                }
                                .apexcharts-legend.apx-legend-position-bottom.apexcharts-align-right, .apexcharts-legend.apx-legend-position-top.apexcharts-align-right {
                                    justify-content: flex-end;
                                }
                                .apexcharts-legend-series {
                                    cursor: pointer;
                                    line-height: normal;
                                }
                                .apexcharts-legend.apx-legend-position-bottom .apexcharts-legend-series, .apexcharts-legend.apx-legend-position-top .apexcharts-legend-series{
                                    display: flex;
                                    align-items: center;
                                }
                                .apexcharts-legend-text {
                                    position: relative;
                                    font-size: 14px;
                                }
                                .apexcharts-legend-text *, .apexcharts-legend-marker * {
                                    pointer-events: none;
                                }
                                .apexcharts-legend-marker {
                                    position: relative;
                                    display: inline-block;
                                    cursor: pointer;
                                    margin-right: 3px;
                                    border-style: solid;
                                }

                                .apexcharts-legend.apexcharts-align-right .apexcharts-legend-series, .apexcharts-legend.apexcharts-align-left .apexcharts-legend-series{
                                    display: inline-block;
                                }
                                .apexcharts-legend-series.apexcharts-no-click {
                                    cursor: auto;
                                }
                                .apexcharts-legend .apexcharts-hidden-zero-series, .apexcharts-legend .apexcharts-hidden-null-series {
                                    display: none !important;
                                }
                                .apexcharts-inactive-legend {
                                    opacity: 0.45;
                                }</style></foreignObject><g id="SvgjsG1975" class="apexcharts-inner apexcharts-graphical" transform="translate(22, 0)"><defs id="SvgjsDefs1974"><clipPath id="gridRectMaskz0uz64eq"><rect id="SvgjsRect1976" width="186" height="204" x="-3" y="-1" rx="0" ry="0" opacity="1" stroke-width="0" stroke="none" stroke-dasharray="0" fill="#fff"></rect></clipPath><clipPath id="forecastMaskz0uz64eq"></clipPath><clipPath id="nonForecastMaskz0uz64eq"></clipPath><clipPath id="gridRectMarkerMaskz0uz64eq"><rect id="SvgjsRect1977" width="184" height="206" x="-2" y="-2" rx="0" ry="0" opacity="1" stroke-width="0" stroke="none" stroke-dasharray="0" fill="#fff"></rect></clipPath><filter id="SvgjsFilter1986" filterUnits="userSpaceOnUse" width="200%" height="200%" x="-50%" y="-50%"><feFlood id="SvgjsFeFlood1987" flood-color="#000000" flood-opacity="0.45" result="SvgjsFeFlood1987Out" in="SourceGraphic"></feFlood><feComposite id="SvgjsFeComposite1988" in="SvgjsFeFlood1987Out" in2="SourceAlpha" operator="in" result="SvgjsFeComposite1988Out"></feComposite><feOffset id="SvgjsFeOffset1989" dx="1" dy="1" result="SvgjsFeOffset1989Out" in="SvgjsFeComposite1988Out"></feOffset><feGaussianBlur id="SvgjsFeGaussianBlur1990" stdDeviation="1 " result="SvgjsFeGaussianBlur1990Out" in="SvgjsFeOffset1989Out"></feGaussianBlur><feMerge id="SvgjsFeMerge1991" result="SvgjsFeMerge1991Out" in="SourceGraphic"><feMergeNode id="SvgjsFeMergeNode1992" in="SvgjsFeGaussianBlur1990Out"></feMergeNode><feMergeNode id="SvgjsFeMergeNode1993" in="[object Arguments]"></feMergeNode></feMerge><feBlend id="SvgjsFeBlend1994" in="SourceGraphic" in2="SvgjsFeMerge1991Out" mode="normal" result="SvgjsFeBlend1994Out"></feBlend></filter><filter id="SvgjsFilter1999" filterUnits="userSpaceOnUse" width="200%" height="200%" x="-50%" y="-50%"><feFlood id="SvgjsFeFlood2000" flood-color="#000000" flood-opacity="0.45" result="SvgjsFeFlood2000Out" in="SourceGraphic"></feFlood><feComposite id="SvgjsFeComposite2001" in="SvgjsFeFlood2000Out" in2="SourceAlpha" operator="in" result="SvgjsFeComposite2001Out"></feComposite><feOffset id="SvgjsFeOffset2002" dx="1" dy="1" result="SvgjsFeOffset2002Out" in="SvgjsFeComposite2001Out"></feOffset><feGaussianBlur id="SvgjsFeGaussianBlur2003" stdDeviation="1 " result="SvgjsFeGaussianBlur2003Out" in="SvgjsFeOffset2002Out"></feGaussianBlur><feMerge id="SvgjsFeMerge2004" result="SvgjsFeMerge2004Out" in="SourceGraphic"><feMergeNode id="SvgjsFeMergeNode2005" in="SvgjsFeGaussianBlur2003Out"></feMergeNode><feMergeNode id="SvgjsFeMergeNode2006" in="[object Arguments]"></feMergeNode></feMerge><feBlend id="SvgjsFeBlend2007" in="SourceGraphic" in2="SvgjsFeMerge2004Out" mode="normal" result="SvgjsFeBlend2007Out"></feBlend></filter><filter id="SvgjsFilter2012" filterUnits="userSpaceOnUse" width="200%" height="200%" x="-50%" y="-50%"><feFlood id="SvgjsFeFlood2013" flood-color="#000000" flood-opacity="0.45" result="SvgjsFeFlood2013Out" in="SourceGraphic"></feFlood><feComposite id="SvgjsFeComposite2014" in="SvgjsFeFlood2013Out" in2="SourceAlpha" operator="in" result="SvgjsFeComposite2014Out"></feComposite><feOffset id="SvgjsFeOffset2015" dx="1" dy="1" result="SvgjsFeOffset2015Out" in="SvgjsFeComposite2014Out"></feOffset><feGaussianBlur id="SvgjsFeGaussianBlur2016" stdDeviation="1 " result="SvgjsFeGaussianBlur2016Out" in="SvgjsFeOffset2015Out"></feGaussianBlur><feMerge id="SvgjsFeMerge2017" result="SvgjsFeMerge2017Out" in="SourceGraphic"><feMergeNode id="SvgjsFeMergeNode2018" in="SvgjsFeGaussianBlur2016Out"></feMergeNode><feMergeNode id="SvgjsFeMergeNode2019" in="[object Arguments]"></feMergeNode></feMerge><feBlend id="SvgjsFeBlend2020" in="SourceGraphic" in2="SvgjsFeMerge2017Out" mode="normal" result="SvgjsFeBlend2020Out"></feBlend></filter></defs><g id="SvgjsG1978" class="apexcharts-pie"><g id="SvgjsG1979" transform="translate(0, 0) scale(1)"><circle id="SvgjsCircle1980" r="53.17317073170732" cx="90" cy="90" fill="transparent"></circle><g id="SvgjsG1981" class="apexcharts-slices"><g id="SvgjsG1982" class="apexcharts-series apexcharts-pie-series" seriesName="KRW" rel="1" data:realIndex="0"><path id="SvgjsPath1983" d="M 90 8.195121951219505 A 81.8048780487805 81.8048780487805 0 1 1 14.82476828843285 57.73976736463241 L 41.13609938748135 69.03084878701107 A 53.17317073170732 53.17317073170732 0 1 0 90 36.82682926829268 L 90 8.195121951219505 z" fill="rgba(0,143,251,1)" fill-opacity="1" stroke-opacity="1" stroke-linecap="butt" stroke-width="2" stroke-dasharray="0" class="apexcharts-pie-area apexcharts-donut-slice-0" index="0" j="0" data:angle="293.2258064516129" data:startAngle="0" data:strokeWidth="2" data:value="1010000" data:pathOrig="M 90 8.195121951219505 A 81.8048780487805 81.8048780487805 0 1 1 14.82476828843285 57.73976736463241 L 41.13609938748135 69.03084878701107 A 53.17317073170732 53.17317073170732 0 1 0 90 36.82682926829268 L 90 8.195121951219505 z" stroke="#ffffff"></path></g><g id="SvgjsG1995" class="apexcharts-series apexcharts-pie-series" seriesName="USD" rel="2" data:realIndex="1"><path id="SvgjsPath1996" d="M 14.82476828843285 57.73976736463241 A 81.8048780487805 81.8048780487805 0 0 1 50.2999321383111 18.474181693928557 L 64.19495588990222 43.50821810105356 A 53.17317073170732 53.17317073170732 0 0 0 41.13609938748135 69.03084878701107 L 14.82476828843285 57.73976736463241 z" fill="rgba(0,227,150,1)" fill-opacity="1" stroke-opacity="1" stroke-linecap="butt" stroke-width="2" stroke-dasharray="0" class="apexcharts-pie-area apexcharts-donut-slice-1" index="0" j="1" data:angle="37.741935483870975" data:startAngle="293.2258064516129" data:strokeWidth="2" data:value="130000" data:pathOrig="M 14.82476828843285 57.73976736463241 A 81.8048780487805 81.8048780487805 0 0 1 50.2999321383111 18.474181693928557 L 64.19495588990222 43.50821810105356 A 53.17317073170732 53.17317073170732 0 0 0 41.13609938748135 69.03084878701107 L 14.82476828843285 57.73976736463241 z" stroke="#ffffff"></path></g><g id="SvgjsG2008" class="apexcharts-series apexcharts-pie-series" seriesName="JPY" rel="3" data:realIndex="2"><path id="SvgjsPath2009" d="M 50.2999321383111 18.474181693928557 A 81.8048780487805 81.8048780487805 0 0 1 89.98572235541104 8.195123197179043 L 89.99071953101718 36.826830078166374 A 53.17317073170732 53.17317073170732 0 0 0 64.19495588990222 43.50821810105356 L 50.2999321383111 18.474181693928557 z" fill="rgba(254,176,25,1)" fill-opacity="1" stroke-opacity="1" stroke-linecap="butt" stroke-width="2" stroke-dasharray="0" class="apexcharts-pie-area apexcharts-donut-slice-2" index="0" j="2" data:angle="29.0322580645161" data:startAngle="330.9677419354839" data:strokeWidth="2" data:value="100000" data:pathOrig="M 50.2999321383111 18.474181693928557 A 81.8048780487805 81.8048780487805 0 0 1 89.98572235541104 8.195123197179043 L 89.99071953101718 36.826830078166374 A 53.17317073170732 53.17317073170732 0 0 0 64.19495588990222 43.50821810105356 L 50.2999321383111 18.474181693928557 z" stroke="#ffffff"></path></g><g id="SvgjsG1984" class="apexcharts-datalabels"><text id="SvgjsText1985" font-family="Helvetica, Arial, sans-serif" x="127.13871848042984" y="146.3514330144169" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#ffffff" class="apexcharts-text apexcharts-pie-label" filter="url(#SvgjsFilter1986)" style="font-family: Helvetica, Arial, sans-serif;">81.5%</text></g><g id="SvgjsG1997" class="apexcharts-datalabels"><text id="SvgjsText1998" font-family="Helvetica, Arial, sans-serif" x="39.92222703361117" y="44.75638091538483" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#ffffff" class="apexcharts-text apexcharts-pie-label" filter="url(#SvgjsFilter1999)" style="font-family: Helvetica, Arial, sans-serif;">10.5%</text></g><g id="SvgjsG2010" class="apexcharts-datalabels"><text id="SvgjsText2011" font-family="Helvetica, Arial, sans-serif" x="73.08370513691482" y="24.665419713201686" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#ffffff" class="apexcharts-text apexcharts-pie-label" filter="url(#SvgjsFilter2012)" style="font-family: Helvetica, Arial, sans-serif;">8.1%</text></g></g></g></g><line id="SvgjsLine2021" x1="0" y1="0" x2="180" y2="0" stroke="#b6b6b6" stroke-dasharray="0" stroke-width="1" stroke-linecap="butt" class="apexcharts-ycrosshairs"></line><line id="SvgjsLine2022" x1="0" y1="0" x2="180" y2="0" stroke-dasharray="0" stroke-width="0" stroke-linecap="butt" class="apexcharts-ycrosshairs-hidden"></line></g></svg><div class="apexcharts-tooltip apexcharts-theme-dark"><div class="apexcharts-tooltip-series-group" style="order: 1;"><span class="apexcharts-tooltip-marker" style="background-color: rgb(0, 143, 251);"></span><div class="apexcharts-tooltip-text" style="font-family: Helvetica, Arial, sans-serif; font-size: 12px;"><div class="apexcharts-tooltip-y-group"><span class="apexcharts-tooltip-text-y-label"></span><span class="apexcharts-tooltip-text-y-value"></span></div><div class="apexcharts-tooltip-goals-group"><span class="apexcharts-tooltip-text-goals-label"></span><span class="apexcharts-tooltip-text-goals-value"></span></div><div class="apexcharts-tooltip-z-group"><span class="apexcharts-tooltip-text-z-label"></span><span class="apexcharts-tooltip-text-z-value"></span></div></div></div><div class="apexcharts-tooltip-series-group" style="order: 2;"><span class="apexcharts-tooltip-marker" style="background-color: rgb(0, 227, 150);"></span><div class="apexcharts-tooltip-text" style="font-family: Helvetica, Arial, sans-serif; font-size: 12px;"><div class="apexcharts-tooltip-y-group"><span class="apexcharts-tooltip-text-y-label"></span><span class="apexcharts-tooltip-text-y-value"></span></div><div class="apexcharts-tooltip-goals-group"><span class="apexcharts-tooltip-text-goals-label"></span><span class="apexcharts-tooltip-text-goals-value"></span></div><div class="apexcharts-tooltip-z-group"><span class="apexcharts-tooltip-text-z-label"></span><span class="apexcharts-tooltip-text-z-value"></span></div></div></div><div class="apexcharts-tooltip-series-group" style="order: 3;"><span class="apexcharts-tooltip-marker" style="background-color: rgb(254, 176, 25);"></span><div class="apexcharts-tooltip-text" style="font-family: Helvetica, Arial, sans-serif; font-size: 12px;"><div class="apexcharts-tooltip-y-group"><span class="apexcharts-tooltip-text-y-label"></span><span class="apexcharts-tooltip-text-y-value"></span></div><div class="apexcharts-tooltip-goals-group"><span class="apexcharts-tooltip-text-goals-label"></span><span class="apexcharts-tooltip-text-goals-value"></span></div><div class="apexcharts-tooltip-z-group"><span class="apexcharts-tooltip-text-z-label"></span><span class="apexcharts-tooltip-text-z-value"></span></div></div></div></div></div></div>
                        </div>
                        <ul class="p-0 m-0">
                            <li class="d-flex mb-4 pb-1">
                                <div class="avatar flex-shrink-0 me-3">
                                    <span class="avatar-initial rounded bg-label-secondary"><img src="https://emojiguide.com/wp-content/uploads/platform/apple/43847.png"></span>
                                </div>
                                <div class="d-flex w-100 flex-wrap align-items-center justify-content-between gap-2">
                                    <div class="me-2">
                                        <h6 class="mb-0">KRW</h6>
                                        <small class="text-muted">대한민국 원</small>
                                    </div>
                                    <div class="user-progress">
                                        1000000 KRW
                                    </div>
                                </div>
                            </li>
                            <li class="d-flex mb-4 pb-1">
                                <div class="avatar flex-shrink-0 me-3">
                                    <span class="avatar-initial rounded bg-label-secondary"><img src="https://emojiguide.com/wp-content/uploads/platform/apple/44356.png"></span>
                                </div>
                                <div class="d-flex w-100 flex-wrap align-items-center justify-content-between gap-2">
                                    <div class="me-2">
                                        <h6 class="mb-0">USD</h6>
                                        <small class="text-muted">미 달러</small>
                                    </div>
                                    <div class="user-progress">
                                        0 USD
                                    </div>
                                </div>
                            </li>
                            <li class="d-flex mb-4 pb-1">
                                <div class="avatar flex-shrink-0 me-3">
                                    <span class="avatar-initial rounded bg-label-secondary"><img src="https://emojiguide.com/wp-content/uploads/platform/apple/43839.png"></span>
                                </div>
                                <div class="d-flex w-100 flex-wrap align-items-center justify-content-between gap-2">
                                    <div class="me-2">
                                        <h6 class="mb-0">JPY</h6>
                                        <small class="text-muted">일본 엔</small>
                                    </div>
                                    <div class="user-progress">
                                        0 JPY
                                    </div>
                                </div>
                            </li>
                            <a href="${pageContext.request.contextPath}/group-wallet/${id}/deposit" class="btn btn-primary">채우기</a>
                            <a href="${pageContext.request.contextPath}/group-wallet/${id}/withdraw" class="btn btn-primary">꺼내기</a>
                        </ul>
                    </div>
                </div>
            </div>


            <div class="col-md-6 col-lg-6 col-xl-6 mb-4 h-100">

                <h6 class="text-muted">자산 정보</h6>
                <div class="nav-align-top d-flex mb-8">
                    <div class="card h-20">
                        <div id="totalBalance" style="min-height: 505px;"><div id="apexchartswvt9heqi" class="apexcharts-canvas apexchartswvt9heqi apexcharts-theme-light" style="width: 587px; height: 490px;"><svg id="SvgjsSvg2023" width="587" height="490" xmlns="http://www.w3.org/2000/svg" version="1.1" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:svgjs="http://svgjs.dev" class="apexcharts-svg apexcharts-zoomable hovering-zoom" xmlns:data="ApexChartsNS" transform="translate(0, 0)" style="background: transparent;"><foreignObject x="0" y="0" width="587" height="490"><div class="apexcharts-legend apexcharts-align-right apx-legend-position-top" xmlns="http://www.w3.org/1999/xhtml" style="right: 0px; position: absolute; left: 15px; top: 13.4px; max-height: 245px;"><div class="apexcharts-legend-series" rel="1" seriesname="KRW" data:collapsed="false" style="margin: 2px 5px;"><span class="apexcharts-legend-marker" rel="1" data:collapsed="false" style="background: rgb(119, 182, 234) !important; color: rgb(119, 182, 234); height: 12px; width: 12px; left: 0px; top: 0px; border-width: 0px; border-color: rgb(255, 255, 255); border-radius: 12px;"></span><span class="apexcharts-legend-text" rel="1" i="0" data:default-text="KRW" data:collapsed="false" style="color: rgb(55, 61, 63); font-size: 12px; font-weight: 400; font-family: Helvetica, Arial, sans-serif;">KRW</span></div><div class="apexcharts-legend-series" rel="2" seriesname="USD" data:collapsed="false" style="margin: 2px 5px;"><span class="apexcharts-legend-marker" rel="2" data:collapsed="false" style="background: rgb(84, 84, 84) !important; color: rgb(84, 84, 84); height: 12px; width: 12px; left: 0px; top: 0px; border-width: 0px; border-color: rgb(255, 255, 255); border-radius: 12px;"></span><span class="apexcharts-legend-text" rel="2" i="1" data:default-text="USD" data:collapsed="false" style="color: rgb(55, 61, 63); font-size: 12px; font-weight: 400; font-family: Helvetica, Arial, sans-serif;">USD</span></div><div class="apexcharts-legend-series" rel="3" seriesname="JPY" data:collapsed="false" style="margin: 2px 5px;"><span class="apexcharts-legend-marker" rel="3" data:collapsed="false" style="background: rgb(144, 0, 0) !important; color: rgb(144, 0, 0); height: 12px; width: 12px; left: 0px; top: 0px; border-width: 0px; border-color: rgb(255, 255, 255); border-radius: 12px;"></span><span class="apexcharts-legend-text" rel="3" i="2" data:default-text="JPY" data:collapsed="false" style="color: rgb(55, 61, 63); font-size: 12px; font-weight: 400; font-family: Helvetica, Arial, sans-serif;">JPY</span></div></div><style type="text/css">

                            .apexcharts-legend {
                                display: flex;
                                overflow: auto;
                                padding: 0 10px;
                            }
                            .apexcharts-legend.apx-legend-position-bottom, .apexcharts-legend.apx-legend-position-top {
                                flex-wrap: wrap
                            }
                            .apexcharts-legend.apx-legend-position-right, .apexcharts-legend.apx-legend-position-left {
                                flex-direction: column;
                                bottom: 0;
                            }
                            .apexcharts-legend.apx-legend-position-bottom.apexcharts-align-left, .apexcharts-legend.apx-legend-position-top.apexcharts-align-left, .apexcharts-legend.apx-legend-position-right, .apexcharts-legend.apx-legend-position-left {
                                justify-content: flex-start;
                            }
                            .apexcharts-legend.apx-legend-position-bottom.apexcharts-align-center, .apexcharts-legend.apx-legend-position-top.apexcharts-align-center {
                                justify-content: center;
                            }
                            .apexcharts-legend.apx-legend-position-bottom.apexcharts-align-right, .apexcharts-legend.apx-legend-position-top.apexcharts-align-right {
                                justify-content: flex-end;
                            }
                            .apexcharts-legend-series {
                                cursor: pointer;
                                line-height: normal;
                            }
                            .apexcharts-legend.apx-legend-position-bottom .apexcharts-legend-series, .apexcharts-legend.apx-legend-position-top .apexcharts-legend-series{
                                display: flex;
                                align-items: center;
                            }
                            .apexcharts-legend-text {
                                position: relative;
                                font-size: 14px;
                            }
                            .apexcharts-legend-text *, .apexcharts-legend-marker * {
                                pointer-events: none;
                            }
                            .apexcharts-legend-marker {
                                position: relative;
                                display: inline-block;
                                cursor: pointer;
                                margin-right: 3px;
                                border-style: solid;
                            }

                            .apexcharts-legend.apexcharts-align-right .apexcharts-legend-series, .apexcharts-legend.apexcharts-align-left .apexcharts-legend-series{
                                display: inline-block;
                            }
                            .apexcharts-legend-series.apexcharts-no-click {
                                cursor: auto;
                            }
                            .apexcharts-legend .apexcharts-hidden-zero-series, .apexcharts-legend .apexcharts-hidden-null-series {
                                display: none !important;
                            }
                            .apexcharts-inactive-legend {
                                opacity: 0.45;
                            }</style></foreignObject><text id="SvgjsText2026" font-family="Helvetica, Arial, sans-serif" x="10" y="16.5" text-anchor="start" dominant-baseline="auto" font-size="14px" font-weight="900" fill="#373d3f" class="apexcharts-title-text" style="font-family: Helvetica, Arial, sans-serif; opacity: 1;">자산 현황 (KRW)</text><rect id="SvgjsRect2031" width="0" height="0" x="0" y="0" rx="0" ry="0" opacity="1" stroke-width="0" stroke="none" stroke-dasharray="0" fill="#fefefe"></rect><g id="SvgjsG2257" class="apexcharts-yaxis" rel="0" transform="translate(47.106056213378906, 0)"><g id="SvgjsG2258" class="apexcharts-yaxis-texts-g"><text id="SvgjsText2260" font-family="Helvetica, Arial, sans-serif" x="20" y="56.20000076293945" text-anchor="end" dominant-baseline="auto" font-size="11px" font-weight="400" fill="#373d3f" class="apexcharts-text apexcharts-yaxis-label " style="font-family: Helvetica, Arial, sans-serif;"><tspan id="SvgjsTspan2261">1500000</tspan><title>1500000</title></text><text id="SvgjsText2263" font-family="Helvetica, Arial, sans-serif" x="20" y="93.8790405632019" text-anchor="end" dominant-baseline="auto" font-size="11px" font-weight="400" fill="#373d3f" class="apexcharts-text apexcharts-yaxis-label " style="font-family: Helvetica, Arial, sans-serif;"><tspan id="SvgjsTspan2264">1350000</tspan><title>1350000</title></text><text id="SvgjsText2266" font-family="Helvetica, Arial, sans-serif" x="20" y="131.55808036346434" text-anchor="end" dominant-baseline="auto" font-size="11px" font-weight="400" fill="#373d3f" class="apexcharts-text apexcharts-yaxis-label " style="font-family: Helvetica, Arial, sans-serif;"><tspan id="SvgjsTspan2267">1200000</tspan><title>1200000</title></text><text id="SvgjsText2269" font-family="Helvetica, Arial, sans-serif" x="20" y="169.2371201637268" text-anchor="end" dominant-baseline="auto" font-size="11px" font-weight="400" fill="#373d3f" class="apexcharts-text apexcharts-yaxis-label " style="font-family: Helvetica, Arial, sans-serif;"><tspan id="SvgjsTspan2270">1050000</tspan><title>1050000</title></text><text id="SvgjsText2272" font-family="Helvetica, Arial, sans-serif" x="20" y="206.91615996398923" text-anchor="end" dominant-baseline="auto" font-size="11px" font-weight="400" fill="#373d3f" class="apexcharts-text apexcharts-yaxis-label " style="font-family: Helvetica, Arial, sans-serif;"><tspan id="SvgjsTspan2273">900000</tspan><title>900000</title></text><text id="SvgjsText2275" font-family="Helvetica, Arial, sans-serif" x="20" y="244.59519976425167" text-anchor="end" dominant-baseline="auto" font-size="11px" font-weight="400" fill="#373d3f" class="apexcharts-text apexcharts-yaxis-label " style="font-family: Helvetica, Arial, sans-serif;"><tspan id="SvgjsTspan2276">750000</tspan><title>750000</title></text><text id="SvgjsText2278" font-family="Helvetica, Arial, sans-serif" x="20" y="282.2742395645141" text-anchor="end" dominant-baseline="auto" font-size="11px" font-weight="400" fill="#373d3f" class="apexcharts-text apexcharts-yaxis-label " style="font-family: Helvetica, Arial, sans-serif;"><tspan id="SvgjsTspan2279">600000</tspan><title>600000</title></text><text id="SvgjsText2281" font-family="Helvetica, Arial, sans-serif" x="20" y="319.95327936477656" text-anchor="end" dominant-baseline="auto" font-size="11px" font-weight="400" fill="#373d3f" class="apexcharts-text apexcharts-yaxis-label " style="font-family: Helvetica, Arial, sans-serif;"><tspan id="SvgjsTspan2282">450000</tspan><title>450000</title></text><text id="SvgjsText2284" font-family="Helvetica, Arial, sans-serif" x="20" y="357.632319165039" text-anchor="end" dominant-baseline="auto" font-size="11px" font-weight="400" fill="#373d3f" class="apexcharts-text apexcharts-yaxis-label " style="font-family: Helvetica, Arial, sans-serif;"><tspan id="SvgjsTspan2285">300000</tspan><title>300000</title></text><text id="SvgjsText2287" font-family="Helvetica, Arial, sans-serif" x="20" y="395.31135896530145" text-anchor="end" dominant-baseline="auto" font-size="11px" font-weight="400" fill="#373d3f" class="apexcharts-text apexcharts-yaxis-label " style="font-family: Helvetica, Arial, sans-serif;"><tspan id="SvgjsTspan2288">150000</tspan><title>150000</title></text><text id="SvgjsText2290" font-family="Helvetica, Arial, sans-serif" x="20" y="432.9903987655639" text-anchor="end" dominant-baseline="auto" font-size="11px" font-weight="400" fill="#373d3f" class="apexcharts-text apexcharts-yaxis-label " style="font-family: Helvetica, Arial, sans-serif;"><tspan id="SvgjsTspan2291">0</tspan><title>0</title></text></g><g id="SvgjsG2292" class="apexcharts-yaxis-title"><text id="SvgjsText2293" font-family="Helvetica, Arial, sans-serif" x="-27.00605583190918" y="242.5951997642517" text-anchor="end" dominant-baseline="auto" font-size="11px" font-weight="900" fill="#373d3f" class="apexcharts-text apexcharts-yaxis-title-text " style="font-family: Helvetica, Arial, sans-serif;" transform="rotate(-90 -37.90605354309082 238.1951994895935)">자산</text></g></g><g id="SvgjsG2025" class="apexcharts-inner apexcharts-graphical" transform="translate(77.1060562133789, 54.20000076293945)"><defs id="SvgjsDefs2024"><clipPath id="gridRectMaskwvt9heqi"><rect id="SvgjsRect2033" width="508.8939437866211" height="381.7903980026245" x="-4.5" y="-2.5" rx="0" ry="0" opacity="1" stroke-width="0" stroke="none" stroke-dasharray="0" fill="#fff"></rect></clipPath><clipPath id="forecastMaskwvt9heqi"></clipPath><clipPath id="nonForecastMaskwvt9heqi"></clipPath><clipPath id="gridRectMarkerMaskwvt9heqi"><rect id="SvgjsRect2034" width="535.8939437866211" height="412.7903980026245" x="-18" y="-18" rx="0" ry="0" opacity="1" stroke-width="0" stroke="none" stroke-dasharray="0" fill="#fff"></rect></clipPath><filter id="SvgjsFilter2073" filterUnits="userSpaceOnUse" width="200%" height="200%" x="-50%" y="-50%"><feFlood id="SvgjsFeFlood2074" flood-color="#000000" flood-opacity="0.2" result="SvgjsFeFlood2074Out" in="SourceGraphic"></feFlood><feComposite id="SvgjsFeComposite2075" in="SvgjsFeFlood2074Out" in2="SourceAlpha" operator="in" result="SvgjsFeComposite2075Out"></feComposite><feOffset id="SvgjsFeOffset2076" dx="7" dy="18" result="SvgjsFeOffset2076Out" in="SvgjsFeComposite2075Out"></feOffset><feGaussianBlur id="SvgjsFeGaussianBlur2077" stdDeviation="10 " result="SvgjsFeGaussianBlur2077Out" in="SvgjsFeOffset2076Out"></feGaussianBlur><feMerge id="SvgjsFeMerge2078" result="SvgjsFeMerge2078Out" in="SourceGraphic"><feMergeNode id="SvgjsFeMergeNode2079" in="SvgjsFeGaussianBlur2077Out"></feMergeNode><feMergeNode id="SvgjsFeMergeNode2080" in="[object Arguments]"></feMergeNode></feMerge><feBlend id="SvgjsFeBlend2081" in="SourceGraphic" in2="SvgjsFeMerge2078Out" mode="normal" result="SvgjsFeBlend2081Out"></feBlend></filter><filter id="SvgjsFilter2119" filterUnits="userSpaceOnUse" width="200%" height="200%" x="-50%" y="-50%"><feFlood id="SvgjsFeFlood2120" flood-color="#000000" flood-opacity="0.2" result="SvgjsFeFlood2120Out" in="SourceGraphic"></feFlood><feComposite id="SvgjsFeComposite2121" in="SvgjsFeFlood2120Out" in2="SourceAlpha" operator="in" result="SvgjsFeComposite2121Out"></feComposite><feOffset id="SvgjsFeOffset2122" dx="7" dy="18" result="SvgjsFeOffset2122Out" in="SvgjsFeComposite2121Out"></feOffset><feGaussianBlur id="SvgjsFeGaussianBlur2123" stdDeviation="10 " result="SvgjsFeGaussianBlur2123Out" in="SvgjsFeOffset2122Out"></feGaussianBlur><feMerge id="SvgjsFeMerge2124" result="SvgjsFeMerge2124Out" in="SourceGraphic"><feMergeNode id="SvgjsFeMergeNode2125" in="SvgjsFeGaussianBlur2123Out"></feMergeNode><feMergeNode id="SvgjsFeMergeNode2126" in="[object Arguments]"></feMergeNode></feMerge><feBlend id="SvgjsFeBlend2127" in="SourceGraphic" in2="SvgjsFeMerge2124Out" mode="normal" result="SvgjsFeBlend2127Out"></feBlend></filter><filter id="SvgjsFilter2165" filterUnits="userSpaceOnUse" width="200%" height="200%" x="-50%" y="-50%"><feFlood id="SvgjsFeFlood2166" flood-color="#000000" flood-opacity="0.2" result="SvgjsFeFlood2166Out" in="SourceGraphic"></feFlood><feComposite id="SvgjsFeComposite2167" in="SvgjsFeFlood2166Out" in2="SourceAlpha" operator="in" result="SvgjsFeComposite2167Out"></feComposite><feOffset id="SvgjsFeOffset2168" dx="7" dy="18" result="SvgjsFeOffset2168Out" in="SvgjsFeComposite2167Out"></feOffset><feGaussianBlur id="SvgjsFeGaussianBlur2169" stdDeviation="10 " result="SvgjsFeGaussianBlur2169Out" in="SvgjsFeOffset2168Out"></feGaussianBlur><feMerge id="SvgjsFeMerge2170" result="SvgjsFeMerge2170Out" in="SourceGraphic"><feMergeNode id="SvgjsFeMergeNode2171" in="SvgjsFeGaussianBlur2169Out"></feMergeNode><feMergeNode id="SvgjsFeMergeNode2172" in="[object Arguments]"></feMergeNode></feMerge><feBlend id="SvgjsFeBlend2173" in="SourceGraphic" in2="SvgjsFeMerge2170Out" mode="normal" result="SvgjsFeBlend2173Out"></feBlend></filter></defs><line id="SvgjsLine2032" x1="249.44697189331055" y1="0" x2="249.44697189331055" y2="376.7903980026245" stroke="#b6b6b6" stroke-dasharray="3" stroke-linecap="butt" class="apexcharts-xcrosshairs" x="249.44697189331055" y="0" width="1" height="376.7903980026245" fill="#b1b9c4" filter="none" fill-opacity="0.9" stroke-width="1"></line><line id="SvgjsLine2199" x1="0" y1="377.7903980026245" x2="0" y2="383.7903980026245" stroke="#e0e0e0" stroke-dasharray="0" stroke-linecap="butt" class="apexcharts-xaxis-tick"></line><line id="SvgjsLine2200" x1="83.31565729777019" y1="377.7903980026245" x2="83.31565729777019" y2="383.7903980026245" stroke="#e0e0e0" stroke-dasharray="0" stroke-linecap="butt" class="apexcharts-xaxis-tick"></line><line id="SvgjsLine2201" x1="166.63131459554037" y1="377.7903980026245" x2="166.63131459554037" y2="383.7903980026245" stroke="#e0e0e0" stroke-dasharray="0" stroke-linecap="butt" class="apexcharts-xaxis-tick"></line><line id="SvgjsLine2202" x1="249.94697189331055" y1="377.7903980026245" x2="249.94697189331055" y2="383.7903980026245" stroke="#e0e0e0" stroke-dasharray="0" stroke-linecap="butt" class="apexcharts-xaxis-tick"></line><line id="SvgjsLine2203" x1="333.26262919108075" y1="377.7903980026245" x2="333.26262919108075" y2="383.7903980026245" stroke="#e0e0e0" stroke-dasharray="0" stroke-linecap="butt" class="apexcharts-xaxis-tick"></line><line id="SvgjsLine2204" x1="416.57828648885095" y1="377.7903980026245" x2="416.57828648885095" y2="383.7903980026245" stroke="#e0e0e0" stroke-dasharray="0" stroke-linecap="butt" class="apexcharts-xaxis-tick"></line><line id="SvgjsLine2205" x1="499.89394378662115" y1="377.7903980026245" x2="499.89394378662115" y2="383.7903980026245" stroke="#e0e0e0" stroke-dasharray="0" stroke-linecap="butt" class="apexcharts-xaxis-tick"></line><g id="SvgjsG2195" class="apexcharts-grid"><g id="SvgjsG2196" class="apexcharts-gridlines-horizontal"><line id="SvgjsLine2207" x1="0" y1="37.67903980026245" x2="499.8939437866211" y2="37.67903980026245" stroke="#e7e7e7" stroke-dasharray="0" stroke-linecap="butt" class="apexcharts-gridline"></line><line id="SvgjsLine2208" x1="0" y1="75.3580796005249" x2="499.8939437866211" y2="75.3580796005249" stroke="#e7e7e7" stroke-dasharray="0" stroke-linecap="butt" class="apexcharts-gridline"></line><line id="SvgjsLine2209" x1="0" y1="113.03711940078736" x2="499.8939437866211" y2="113.03711940078736" stroke="#e7e7e7" stroke-dasharray="0" stroke-linecap="butt" class="apexcharts-gridline"></line><line id="SvgjsLine2210" x1="0" y1="150.7161592010498" x2="499.8939437866211" y2="150.7161592010498" stroke="#e7e7e7" stroke-dasharray="0" stroke-linecap="butt" class="apexcharts-gridline"></line><line id="SvgjsLine2211" x1="0" y1="188.39519900131225" x2="499.8939437866211" y2="188.39519900131225" stroke="#e7e7e7" stroke-dasharray="0" stroke-linecap="butt" class="apexcharts-gridline"></line><line id="SvgjsLine2212" x1="0" y1="226.0742388015747" x2="499.8939437866211" y2="226.0742388015747" stroke="#e7e7e7" stroke-dasharray="0" stroke-linecap="butt" class="apexcharts-gridline"></line><line id="SvgjsLine2213" x1="0" y1="263.75327860183717" x2="499.8939437866211" y2="263.75327860183717" stroke="#e7e7e7" stroke-dasharray="0" stroke-linecap="butt" class="apexcharts-gridline"></line><line id="SvgjsLine2214" x1="0" y1="301.4323184020996" x2="499.8939437866211" y2="301.4323184020996" stroke="#e7e7e7" stroke-dasharray="0" stroke-linecap="butt" class="apexcharts-gridline"></line><line id="SvgjsLine2215" x1="0" y1="339.11135820236206" x2="499.8939437866211" y2="339.11135820236206" stroke="#e7e7e7" stroke-dasharray="0" stroke-linecap="butt" class="apexcharts-gridline"></line></g><g id="SvgjsG2197" class="apexcharts-gridlines-vertical"></g><rect id="SvgjsRect2217" width="499.8939437866211" height="37.67903980026245" x="0" y="0" rx="0" ry="0" opacity="0.5" stroke-width="0" stroke="none" stroke-dasharray="0" fill="#f3f3f3" clip-path="url(#gridRectMaskwvt9heqi)" class="apexcharts-grid-row"></rect><rect id="SvgjsRect2218" width="499.8939437866211" height="37.67903980026245" x="0" y="37.67903980026245" rx="0" ry="0" opacity="0.5" stroke-width="0" stroke="none" stroke-dasharray="0" fill="transparent" clip-path="url(#gridRectMaskwvt9heqi)" class="apexcharts-grid-row"></rect><rect id="SvgjsRect2219" width="499.8939437866211" height="37.67903980026245" x="0" y="75.3580796005249" rx="0" ry="0" opacity="0.5" stroke-width="0" stroke="none" stroke-dasharray="0" fill="#f3f3f3" clip-path="url(#gridRectMaskwvt9heqi)" class="apexcharts-grid-row"></rect><rect id="SvgjsRect2220" width="499.8939437866211" height="37.67903980026245" x="0" y="113.03711940078736" rx="0" ry="0" opacity="0.5" stroke-width="0" stroke="none" stroke-dasharray="0" fill="transparent" clip-path="url(#gridRectMaskwvt9heqi)" class="apexcharts-grid-row"></rect><rect id="SvgjsRect2221" width="499.8939437866211" height="37.67903980026245" x="0" y="150.7161592010498" rx="0" ry="0" opacity="0.5" stroke-width="0" stroke="none" stroke-dasharray="0" fill="#f3f3f3" clip-path="url(#gridRectMaskwvt9heqi)" class="apexcharts-grid-row"></rect><rect id="SvgjsRect2222" width="499.8939437866211" height="37.67903980026245" x="0" y="188.39519900131225" rx="0" ry="0" opacity="0.5" stroke-width="0" stroke="none" stroke-dasharray="0" fill="transparent" clip-path="url(#gridRectMaskwvt9heqi)" class="apexcharts-grid-row"></rect><rect id="SvgjsRect2223" width="499.8939437866211" height="37.67903980026245" x="0" y="226.0742388015747" rx="0" ry="0" opacity="0.5" stroke-width="0" stroke="none" stroke-dasharray="0" fill="#f3f3f3" clip-path="url(#gridRectMaskwvt9heqi)" class="apexcharts-grid-row"></rect><rect id="SvgjsRect2224" width="499.8939437866211" height="37.67903980026245" x="0" y="263.75327860183717" rx="0" ry="0" opacity="0.5" stroke-width="0" stroke="none" stroke-dasharray="0" fill="transparent" clip-path="url(#gridRectMaskwvt9heqi)" class="apexcharts-grid-row"></rect><rect id="SvgjsRect2225" width="499.8939437866211" height="37.67903980026245" x="0" y="301.4323184020996" rx="0" ry="0" opacity="0.5" stroke-width="0" stroke="none" stroke-dasharray="0" fill="#f3f3f3" clip-path="url(#gridRectMaskwvt9heqi)" class="apexcharts-grid-row"></rect><rect id="SvgjsRect2226" width="499.8939437866211" height="37.67903980026245" x="0" y="339.11135820236206" rx="0" ry="0" opacity="0.5" stroke-width="0" stroke="none" stroke-dasharray="0" fill="transparent" clip-path="url(#gridRectMaskwvt9heqi)" class="apexcharts-grid-row"></rect><line id="SvgjsLine2228" x1="0" y1="376.7903980026245" x2="499.8939437866211" y2="376.7903980026245" stroke="transparent" stroke-dasharray="0" stroke-linecap="butt"></line><line id="SvgjsLine2227" x1="0" y1="1" x2="0" y2="376.7903980026245" stroke="transparent" stroke-dasharray="0" stroke-linecap="butt"></line></g><g id="SvgjsG2198" class="apexcharts-grid-borders"><line id="SvgjsLine2206" x1="0" y1="0" x2="499.8939437866211" y2="0" stroke="#e7e7e7" stroke-dasharray="0" stroke-linecap="butt" class="apexcharts-gridline"></line><line id="SvgjsLine2216" x1="0" y1="376.7903980026245" x2="499.8939437866211" y2="376.7903980026245" stroke="#e7e7e7" stroke-dasharray="0" stroke-linecap="butt" class="apexcharts-gridline"></line><line id="SvgjsLine2256" x1="0" y1="377.7903980026245" x2="499.8939437866211" y2="377.7903980026245" stroke="#e0e0e0" stroke-dasharray="0" stroke-width="1" stroke-linecap="butt"></line></g><g id="SvgjsG2035" class="apexcharts-line-series apexcharts-plot-series"><g id="SvgjsG2036" class="apexcharts-series" seriesName="KRW" data:longestSeries="true" rel="1" data:realIndex="0"><path id="SvgjsPath2072" d="M 0 376.7903980026245C 29.160480054219562 376.7903980026245 54.155177243550625 351.67103813578285 83.31565729777019 351.67103813578285C 112.47613735198975 351.67103813578285 137.4708345413208 326.55167826894126 166.63131459554037 326.55167826894126C 195.79179464975994 326.55167826894126 220.78649183909098 150.7161592010498 249.94697189331055 150.7161592010498C 279.10745194753014 150.7161592010498 304.10214913686116 226.0742388015747 333.26262919108075 226.0742388015747C 362.4231092453003 226.0742388015747 387.41780643463136 175.83551906789143 416.5782864888509 175.83551906789143C 445.7387665430705 175.83551906789143 470.7334637324015 123.08486334752399 499.8939437866211 123.08486334752399" fill="none" fill-opacity="1" stroke="rgba(119,182,234,0.85)" stroke-opacity="1" stroke-linecap="butt" stroke-width="5" stroke-dasharray="0" class="apexcharts-line" index="0" clip-path="url(#gridRectMaskwvt9heqi)" filter="url(#SvgjsFilter2073)" pathTo="M 0 376.7903980026245C 29.160480054219562 376.7903980026245 54.155177243550625 351.67103813578285 83.31565729777019 351.67103813578285C 112.47613735198975 351.67103813578285 137.4708345413208 326.55167826894126 166.63131459554037 326.55167826894126C 195.79179464975994 326.55167826894126 220.78649183909098 150.7161592010498 249.94697189331055 150.7161592010498C 279.10745194753014 150.7161592010498 304.10214913686116 226.0742388015747 333.26262919108075 226.0742388015747C 362.4231092453003 226.0742388015747 387.41780643463136 175.83551906789143 416.5782864888509 175.83551906789143C 445.7387665430705 175.83551906789143 470.7334637324015 123.08486334752399 499.8939437866211 123.08486334752399" pathFrom="M -1 376.7903980026245 L -1 376.7903980026245 L 83.31565729777019 376.7903980026245 L 166.63131459554037 376.7903980026245 L 249.94697189331055 376.7903980026245 L 333.26262919108075 376.7903980026245 L 416.5782864888509 376.7903980026245 L 499.8939437866211 376.7903980026245" fill-rule="evenodd"></path><g id="SvgjsG2037" class="apexcharts-series-markers-wrap apexcharts-hidden-element-shown" data:realIndex="0"><g id="SvgjsG2039" class="apexcharts-series-markers" clip-path="url(#gridRectMarkerMaskwvt9heqi)"><circle id="SvgjsCircle2040" r="1" cx="0" cy="376.7903980026245" class="apexcharts-marker no-pointer-events w2rrxr8bx" stroke="#ffffff" fill="#77b6ea" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="0" j="0" index="0" default-marker-size="1"></circle><circle id="SvgjsCircle2041" r="1" cx="83.31565729777019" cy="351.67103813578285" class="apexcharts-marker no-pointer-events wc8bi3nmw" stroke="#ffffff" fill="#77b6ea" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="1" j="1" index="0" default-marker-size="1"></circle></g><g id="SvgjsG2047" class="apexcharts-series-markers" clip-path="url(#gridRectMarkerMaskwvt9heqi)"><circle id="SvgjsCircle2048" r="1" cx="166.63131459554037" cy="326.55167826894126" class="apexcharts-marker no-pointer-events ws5f6pgxu" stroke="#ffffff" fill="#77b6ea" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="2" j="2" index="0" default-marker-size="1"></circle></g><g id="SvgjsG2052" class="apexcharts-series-markers" clip-path="url(#gridRectMarkerMaskwvt9heqi)"><circle id="SvgjsCircle2053" r="1" cx="249.94697189331055" cy="150.7161592010498" class="apexcharts-marker no-pointer-events wjcpc9cw8" stroke="#ffffff" fill="#77b6ea" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="3" j="3" index="0" default-marker-size="1"></circle></g><g id="SvgjsG2057" class="apexcharts-series-markers" clip-path="url(#gridRectMarkerMaskwvt9heqi)"><circle id="SvgjsCircle2058" r="1" cx="333.26262919108075" cy="226.0742388015747" class="apexcharts-marker no-pointer-events wse6u6ccs" stroke="#ffffff" fill="#77b6ea" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="4" j="4" index="0" default-marker-size="1"></circle></g><g id="SvgjsG2062" class="apexcharts-series-markers" clip-path="url(#gridRectMarkerMaskwvt9heqi)"><circle id="SvgjsCircle2063" r="1" cx="416.5782864888509" cy="175.83551906789143" class="apexcharts-marker no-pointer-events wdm127stz" stroke="#ffffff" fill="#77b6ea" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="5" j="5" index="0" default-marker-size="1"></circle></g><g id="SvgjsG2067" class="apexcharts-series-markers" clip-path="url(#gridRectMarkerMaskwvt9heqi)"><circle id="SvgjsCircle2068" r="1" cx="499.8939437866211" cy="123.08486334752399" class="apexcharts-marker no-pointer-events wvc6tys3f" stroke="#ffffff" fill="#77b6ea" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="6" j="6" index="0" default-marker-size="1"></circle></g></g></g><g id="SvgjsG2082" class="apexcharts-series" seriesName="USD" data:longestSeries="true" rel="2" data:realIndex="1"><path id="SvgjsPath2118" d="M 0 376.7903980026245C 29.160480054219562 376.7903980026245 54.155177243550625 376.7903980026245 83.31565729777019 376.7903980026245C 112.47613735198975 376.7903980026245 137.4708345413208 376.7903980026245 166.63131459554037 376.7903980026245C 195.79179464975994 376.7903980026245 220.78649183909098 376.7903980026245 249.94697189331055 376.7903980026245C 279.10745194753014 376.7903980026245 304.10214913686116 376.7903980026245 333.26262919108075 376.7903980026245C 362.4231092453003 376.7903980026245 387.41780643463136 360.4628140891774 416.5782864888509 360.4628140891774C 445.7387665430705 360.4628140891774 470.7334637324015 344.1352301757304 499.8939437866211 344.1352301757304" fill="none" fill-opacity="1" stroke="rgba(84,84,84,0.85)" stroke-opacity="1" stroke-linecap="butt" stroke-width="5" stroke-dasharray="0" class="apexcharts-line" index="1" clip-path="url(#gridRectMaskwvt9heqi)" filter="url(#SvgjsFilter2119)" pathTo="M 0 376.7903980026245C 29.160480054219562 376.7903980026245 54.155177243550625 376.7903980026245 83.31565729777019 376.7903980026245C 112.47613735198975 376.7903980026245 137.4708345413208 376.7903980026245 166.63131459554037 376.7903980026245C 195.79179464975994 376.7903980026245 220.78649183909098 376.7903980026245 249.94697189331055 376.7903980026245C 279.10745194753014 376.7903980026245 304.10214913686116 376.7903980026245 333.26262919108075 376.7903980026245C 362.4231092453003 376.7903980026245 387.41780643463136 360.4628140891774 416.5782864888509 360.4628140891774C 445.7387665430705 360.4628140891774 470.7334637324015 344.1352301757304 499.8939437866211 344.1352301757304" pathFrom="M -1 376.7903980026245 L -1 376.7903980026245 L 83.31565729777019 376.7903980026245 L 166.63131459554037 376.7903980026245 L 249.94697189331055 376.7903980026245 L 333.26262919108075 376.7903980026245 L 416.5782864888509 376.7903980026245 L 499.8939437866211 376.7903980026245" fill-rule="evenodd"></path><g id="SvgjsG2083" class="apexcharts-series-markers-wrap apexcharts-hidden-element-shown" data:realIndex="1"><g id="SvgjsG2085" class="apexcharts-series-markers" clip-path="url(#gridRectMarkerMaskwvt9heqi)"><circle id="SvgjsCircle2086" r="1" cx="0" cy="376.7903980026245" class="apexcharts-marker no-pointer-events wy4hbbtrp" stroke="#ffffff" fill="#545454" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="0" j="0" index="1" default-marker-size="1"></circle><circle id="SvgjsCircle2087" r="1" cx="83.31565729777019" cy="376.7903980026245" class="apexcharts-marker no-pointer-events w760a7z6q" stroke="#ffffff" fill="#545454" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="1" j="1" index="1" default-marker-size="1"></circle></g><g id="SvgjsG2093" class="apexcharts-series-markers" clip-path="url(#gridRectMarkerMaskwvt9heqi)"><circle id="SvgjsCircle2094" r="1" cx="166.63131459554037" cy="376.7903980026245" class="apexcharts-marker no-pointer-events wc01r93tt" stroke="#ffffff" fill="#545454" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="2" j="2" index="1" default-marker-size="1"></circle></g><g id="SvgjsG2098" class="apexcharts-series-markers" clip-path="url(#gridRectMarkerMaskwvt9heqi)"><circle id="SvgjsCircle2099" r="1" cx="249.94697189331055" cy="376.7903980026245" class="apexcharts-marker no-pointer-events wk1sdpa2n" stroke="#ffffff" fill="#545454" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="3" j="3" index="1" default-marker-size="1"></circle></g><g id="SvgjsG2103" class="apexcharts-series-markers" clip-path="url(#gridRectMarkerMaskwvt9heqi)"><circle id="SvgjsCircle2104" r="1" cx="333.26262919108075" cy="376.7903980026245" class="apexcharts-marker no-pointer-events w9tvpfzbv" stroke="#ffffff" fill="#545454" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="4" j="4" index="1" default-marker-size="1"></circle></g><g id="SvgjsG2108" class="apexcharts-series-markers" clip-path="url(#gridRectMarkerMaskwvt9heqi)"><circle id="SvgjsCircle2109" r="1" cx="416.5782864888509" cy="360.4628140891774" class="apexcharts-marker no-pointer-events wjhqo9ll2" stroke="#ffffff" fill="#545454" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="5" j="5" index="1" default-marker-size="1"></circle></g><g id="SvgjsG2113" class="apexcharts-series-markers" clip-path="url(#gridRectMarkerMaskwvt9heqi)"><circle id="SvgjsCircle2114" r="1" cx="499.8939437866211" cy="344.1352301757304" class="apexcharts-marker no-pointer-events wekhs0zg4" stroke="#ffffff" fill="#545454" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="6" j="6" index="1" default-marker-size="1"></circle></g></g></g><g id="SvgjsG2128" class="apexcharts-series" seriesName="JPY" data:longestSeries="true" rel="3" data:realIndex="2"><path id="SvgjsPath2164" d="M 0 351.67103813578285C 29.160480054219562 351.67103813578285 54.155177243550625 351.67103813578285 83.31565729777019 351.67103813578285C 112.47613735198975 351.67103813578285 137.4708345413208 351.67103813578285 166.63131459554037 351.67103813578285C 195.79179464975994 351.67103813578285 220.78649183909098 351.67103813578285 249.94697189331055 351.67103813578285C 279.10745194753014 351.67103813578285 304.10214913686116 351.67103813578285 333.26262919108075 351.67103813578285C 362.4231092453003 351.67103813578285 387.41780643463136 351.67103813578285 416.5782864888509 351.67103813578285C 445.7387665430705 351.67103813578285 470.7334637324015 351.67103813578285 499.8939437866211 351.67103813578285" fill="none" fill-opacity="1" stroke="rgba(144,0,0,0.85)" stroke-opacity="1" stroke-linecap="butt" stroke-width="5" stroke-dasharray="0" class="apexcharts-line" index="2" clip-path="url(#gridRectMaskwvt9heqi)" filter="url(#SvgjsFilter2165)" pathTo="M 0 351.67103813578285C 29.160480054219562 351.67103813578285 54.155177243550625 351.67103813578285 83.31565729777019 351.67103813578285C 112.47613735198975 351.67103813578285 137.4708345413208 351.67103813578285 166.63131459554037 351.67103813578285C 195.79179464975994 351.67103813578285 220.78649183909098 351.67103813578285 249.94697189331055 351.67103813578285C 279.10745194753014 351.67103813578285 304.10214913686116 351.67103813578285 333.26262919108075 351.67103813578285C 362.4231092453003 351.67103813578285 387.41780643463136 351.67103813578285 416.5782864888509 351.67103813578285C 445.7387665430705 351.67103813578285 470.7334637324015 351.67103813578285 499.8939437866211 351.67103813578285" pathFrom="M -1 376.7903980026245 L -1 376.7903980026245 L 83.31565729777019 376.7903980026245 L 166.63131459554037 376.7903980026245 L 249.94697189331055 376.7903980026245 L 333.26262919108075 376.7903980026245 L 416.5782864888509 376.7903980026245 L 499.8939437866211 376.7903980026245" fill-rule="evenodd"></path><g id="SvgjsG2129" class="apexcharts-series-markers-wrap apexcharts-hidden-element-shown" data:realIndex="2"><g id="SvgjsG2131" class="apexcharts-series-markers" clip-path="url(#gridRectMarkerMaskwvt9heqi)"><circle id="SvgjsCircle2132" r="1" cx="0" cy="351.67103813578285" class="apexcharts-marker no-pointer-events ww6spq0pe" stroke="#ffffff" fill="#900000" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="0" j="0" index="2" default-marker-size="1"></circle><circle id="SvgjsCircle2133" r="1" cx="83.31565729777019" cy="351.67103813578285" class="apexcharts-marker no-pointer-events wm9td9lb5" stroke="#ffffff" fill="#900000" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="1" j="1" index="2" default-marker-size="1"></circle></g><g id="SvgjsG2139" class="apexcharts-series-markers" clip-path="url(#gridRectMarkerMaskwvt9heqi)"><circle id="SvgjsCircle2140" r="1" cx="166.63131459554037" cy="351.67103813578285" class="apexcharts-marker no-pointer-events w046vt0vs" stroke="#ffffff" fill="#900000" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="2" j="2" index="2" default-marker-size="1"></circle></g><g id="SvgjsG2144" class="apexcharts-series-markers" clip-path="url(#gridRectMarkerMaskwvt9heqi)"><circle id="SvgjsCircle2145" r="1" cx="249.94697189331055" cy="351.67103813578285" class="apexcharts-marker no-pointer-events wqwe7s2gf" stroke="#ffffff" fill="#900000" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="3" j="3" index="2" default-marker-size="1"></circle></g><g id="SvgjsG2149" class="apexcharts-series-markers" clip-path="url(#gridRectMarkerMaskwvt9heqi)"><circle id="SvgjsCircle2150" r="1" cx="333.26262919108075" cy="351.67103813578285" class="apexcharts-marker no-pointer-events w7c7rah1p" stroke="#ffffff" fill="#900000" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="4" j="4" index="2" default-marker-size="1"></circle></g><g id="SvgjsG2154" class="apexcharts-series-markers" clip-path="url(#gridRectMarkerMaskwvt9heqi)"><circle id="SvgjsCircle2155" r="1" cx="416.5782864888509" cy="351.67103813578285" class="apexcharts-marker no-pointer-events w7n5b59ay" stroke="#ffffff" fill="#900000" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="5" j="5" index="2" default-marker-size="1"></circle></g><g id="SvgjsG2159" class="apexcharts-series-markers" clip-path="url(#gridRectMarkerMaskwvt9heqi)"><circle id="SvgjsCircle2160" r="1" cx="499.8939437866211" cy="351.67103813578285" class="apexcharts-marker no-pointer-events wug7ee76k" stroke="#ffffff" fill="#900000" fill-opacity="1" stroke-width="2" stroke-opacity="0.9" rel="6" j="6" index="2" default-marker-size="1"></circle></g></g></g><g id="SvgjsG2038" class="apexcharts-datalabels" data:realIndex="0"><g id="SvgjsG2042" class="apexcharts-data-labels"><rect id="SvgjsRect2174" width="14.675000190734863" height="15.600000381469727" x="-7.337500095367432" y="366.5904235839844" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#77b6ea"></rect><text id="SvgjsText2044" font-family="Helvetica, Arial, sans-serif" x="0" y="378.7903980026245" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="0" cy="378.7903980026245" style="font-family: Helvetica, Arial, sans-serif;">0</text><rect id="SvgjsRect2175" width="48.04999923706055" height="15.600000381469727" x="59.2906608581543" y="341.4710388183594" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#77b6ea"></rect><text id="SvgjsText2046" font-family="Helvetica, Arial, sans-serif" x="83.31565729777019" y="353.67103813578285" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="83.31565729777019" cy="353.67103813578285" style="font-family: Helvetica, Arial, sans-serif;">100000</text></g><g id="SvgjsG2049" class="apexcharts-data-labels"><rect id="SvgjsRect2176" width="48.04999923706055" height="15.600000381469727" x="142.6063232421875" y="316.3516540527344" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#77b6ea"></rect><text id="SvgjsText2051" font-family="Helvetica, Arial, sans-serif" x="166.63131459554037" y="328.55167826894126" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="166.63131459554037" cy="328.55167826894126" style="font-family: Helvetica, Arial, sans-serif;">200000</text></g><g id="SvgjsG2054" class="apexcharts-data-labels"><rect id="SvgjsRect2177" width="48.04999923706055" height="15.600000381469727" x="225.92198181152344" y="140.5161590576172" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#77b6ea"></rect><text id="SvgjsText2056" font-family="Helvetica, Arial, sans-serif" x="249.94697189331055" y="152.7161592010498" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="249.94697189331055" cy="152.7161592010498" style="font-family: Helvetica, Arial, sans-serif;">900000</text></g><g id="SvgjsG2059" class="apexcharts-data-labels"><rect id="SvgjsRect2178" width="48.04999923706055" height="15.600000381469727" x="309.2376403808594" y="215.87425231933594" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#77b6ea"></rect><text id="SvgjsText2061" font-family="Helvetica, Arial, sans-serif" x="333.26262919108075" y="228.0742388015747" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="333.26262919108075" cy="228.0742388015747" style="font-family: Helvetica, Arial, sans-serif;">600000</text></g><g id="SvgjsG2064" class="apexcharts-data-labels"><rect id="SvgjsRect2179" width="48.04999923706055" height="15.600000381469727" x="392.55328369140625" y="165.63552856445312" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#77b6ea"></rect><text id="SvgjsText2066" font-family="Helvetica, Arial, sans-serif" x="416.5782864888509" y="177.83551906789143" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="416.5782864888509" cy="177.83551906789143" style="font-family: Helvetica, Arial, sans-serif;">800000</text></g><g id="SvgjsG2069" class="apexcharts-data-labels"><rect id="SvgjsRect2180" width="54.72500228881836" height="15.600000381469727" x="472.5314636230469" y="112.8848648071289" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#77b6ea"></rect><text id="SvgjsText2071" font-family="Helvetica, Arial, sans-serif" x="499.8939437866211" y="125.08486334752399" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="499.8939437866211" cy="125.08486334752399" style="font-family: Helvetica, Arial, sans-serif;">1010000</text></g></g><g id="SvgjsG2084" class="apexcharts-datalabels" data:realIndex="1"><g id="SvgjsG2088" class="apexcharts-data-labels"><rect id="SvgjsRect2181" width="14.675000190734863" height="15.600000381469727" x="-7.337500095367432" y="366.5904235839844" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#545454"></rect><text id="SvgjsText2090" font-family="Helvetica, Arial, sans-serif" x="0" y="378.7903980026245" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="0" cy="378.7903980026245" style="font-family: Helvetica, Arial, sans-serif;">0</text><rect id="SvgjsRect2182" width="14.675000190734863" height="15.600000381469727" x="75.97816467285156" y="366.5904235839844" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#545454"></rect><text id="SvgjsText2092" font-family="Helvetica, Arial, sans-serif" x="83.31565729777019" y="378.7903980026245" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="83.31565729777019" cy="378.7903980026245" style="font-family: Helvetica, Arial, sans-serif;">0</text></g><g id="SvgjsG2095" class="apexcharts-data-labels"><rect id="SvgjsRect2183" width="14.675000190734863" height="15.600000381469727" x="159.2938232421875" y="366.5904235839844" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#545454"></rect><text id="SvgjsText2097" font-family="Helvetica, Arial, sans-serif" x="166.63131459554037" y="378.7903980026245" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="166.63131459554037" cy="378.7903980026245" style="font-family: Helvetica, Arial, sans-serif;">0</text></g><g id="SvgjsG2100" class="apexcharts-data-labels"><rect id="SvgjsRect2184" width="14.675000190734863" height="15.600000381469727" x="242.60948181152344" y="366.5904235839844" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#545454"></rect><text id="SvgjsText2102" font-family="Helvetica, Arial, sans-serif" x="249.94697189331055" y="378.7903980026245" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="249.94697189331055" cy="378.7903980026245" style="font-family: Helvetica, Arial, sans-serif;">0</text></g><g id="SvgjsG2105" class="apexcharts-data-labels"><rect id="SvgjsRect2185" width="14.675000190734863" height="15.600000381469727" x="325.9251403808594" y="366.5904235839844" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#545454"></rect><text id="SvgjsText2107" font-family="Helvetica, Arial, sans-serif" x="333.26262919108075" y="378.7903980026245" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="333.26262919108075" cy="378.7903980026245" style="font-family: Helvetica, Arial, sans-serif;">0</text></g><g id="SvgjsG2110" class="apexcharts-data-labels"><rect id="SvgjsRect2186" width="41.375" height="15.600000381469727" x="395.8907775878906" y="350.2627868652344" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#545454"></rect><text id="SvgjsText2112" font-family="Helvetica, Arial, sans-serif" x="416.5782864888509" y="362.4628140891774" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="416.5782864888509" cy="362.4628140891774" style="font-family: Helvetica, Arial, sans-serif;">65000</text></g><g id="SvgjsG2115" class="apexcharts-data-labels"><rect id="SvgjsRect2187" width="48.04999923706055" height="15.600000381469727" x="475.86895751953125" y="333.93524169921875" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#545454"></rect><text id="SvgjsText2117" font-family="Helvetica, Arial, sans-serif" x="499.8939437866211" y="346.1352301757304" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="499.8939437866211" cy="346.1352301757304" style="font-family: Helvetica, Arial, sans-serif;">130000</text></g></g><g id="SvgjsG2130" class="apexcharts-datalabels" data:realIndex="2"><g id="SvgjsG2134" class="apexcharts-data-labels"><rect id="SvgjsRect2188" width="48.04999923706055" height="15.600000381469727" x="-24.024999618530273" y="341.4710388183594" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#900000"></rect><text id="SvgjsText2136" font-family="Helvetica, Arial, sans-serif" x="0" y="353.67103813578285" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="0" cy="353.67103813578285" style="font-family: Helvetica, Arial, sans-serif;">100000</text><rect id="SvgjsRect2189" width="48.04999923706055" height="15.600000381469727" x="59.2906608581543" y="341.4710388183594" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#900000"></rect><text id="SvgjsText2138" font-family="Helvetica, Arial, sans-serif" x="83.31565729777019" y="353.67103813578285" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="83.31565729777019" cy="353.67103813578285" style="font-family: Helvetica, Arial, sans-serif;">100000</text></g><g id="SvgjsG2141" class="apexcharts-data-labels"><rect id="SvgjsRect2190" width="48.04999923706055" height="15.600000381469727" x="142.6063232421875" y="341.4710388183594" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#900000"></rect><text id="SvgjsText2143" font-family="Helvetica, Arial, sans-serif" x="166.63131459554037" y="353.67103813578285" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="166.63131459554037" cy="353.67103813578285" style="font-family: Helvetica, Arial, sans-serif;">100000</text></g><g id="SvgjsG2146" class="apexcharts-data-labels"><rect id="SvgjsRect2191" width="48.04999923706055" height="15.600000381469727" x="225.92198181152344" y="341.4710388183594" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#900000"></rect><text id="SvgjsText2148" font-family="Helvetica, Arial, sans-serif" x="249.94697189331055" y="353.67103813578285" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="249.94697189331055" cy="353.67103813578285" style="font-family: Helvetica, Arial, sans-serif;">100000</text></g><g id="SvgjsG2151" class="apexcharts-data-labels"><rect id="SvgjsRect2192" width="48.04999923706055" height="15.600000381469727" x="309.2376403808594" y="341.4710388183594" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#900000"></rect><text id="SvgjsText2153" font-family="Helvetica, Arial, sans-serif" x="333.26262919108075" y="353.67103813578285" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="333.26262919108075" cy="353.67103813578285" style="font-family: Helvetica, Arial, sans-serif;">100000</text></g><g id="SvgjsG2156" class="apexcharts-data-labels"><rect id="SvgjsRect2193" width="48.04999923706055" height="15.600000381469727" x="392.55328369140625" y="341.4710388183594" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#900000"></rect><text id="SvgjsText2158" font-family="Helvetica, Arial, sans-serif" x="416.5782864888509" y="353.67103813578285" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="416.5782864888509" cy="353.67103813578285" style="font-family: Helvetica, Arial, sans-serif;">100000</text></g><g id="SvgjsG2161" class="apexcharts-data-labels"><rect id="SvgjsRect2194" width="48.04999923706055" height="15.600000381469727" x="475.86895751953125" y="341.4710388183594" rx="2" ry="2" opacity="0.9" stroke="#ffffff" stroke-width="1" stroke-dasharray="0" fill="#900000"></rect><text id="SvgjsText2163" font-family="Helvetica, Arial, sans-serif" x="499.8939437866211" y="353.67103813578285" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="600" fill="#fff" class="apexcharts-datalabel" cx="499.8939437866211" cy="353.67103813578285" style="font-family: Helvetica, Arial, sans-serif;">100000</text></g></g></g><line id="SvgjsLine2229" x1="0" y1="0" x2="499.8939437866211" y2="0" stroke="#b6b6b6" stroke-dasharray="0" stroke-width="1" stroke-linecap="butt" class="apexcharts-ycrosshairs"></line><line id="SvgjsLine2230" x1="0" y1="0" x2="499.8939437866211" y2="0" stroke-dasharray="0" stroke-width="0" stroke-linecap="butt" class="apexcharts-ycrosshairs-hidden"></line><g id="SvgjsG2231" class="apexcharts-xaxis" transform="translate(0, 0)"><g id="SvgjsG2232" class="apexcharts-xaxis-texts-g" transform="translate(0, -4)"><text id="SvgjsText2234" font-family="Helvetica, Arial, sans-serif" x="0" y="405.7903980026245" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="400" fill="#373d3f" class="apexcharts-text apexcharts-xaxis-label " style="font-family: Helvetica, Arial, sans-serif;"><tspan id="SvgjsTspan2235">Jan</tspan><title>Jan</title></text><text id="SvgjsText2237" font-family="Helvetica, Arial, sans-serif" x="83.31565729777017" y="405.7903980026245" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="400" fill="#373d3f" class="apexcharts-text apexcharts-xaxis-label " style="font-family: Helvetica, Arial, sans-serif;"><tspan id="SvgjsTspan2238">Feb</tspan><title>Feb</title></text><text id="SvgjsText2240" font-family="Helvetica, Arial, sans-serif" x="166.63131459554037" y="405.7903980026245" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="400" fill="#373d3f" class="apexcharts-text apexcharts-xaxis-label " style="font-family: Helvetica, Arial, sans-serif;"><tspan id="SvgjsTspan2241">Mar</tspan><title>Mar</title></text><text id="SvgjsText2243" font-family="Helvetica, Arial, sans-serif" x="249.94697189331058" y="405.7903980026245" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="400" fill="#373d3f" class="apexcharts-text apexcharts-xaxis-label " style="font-family: Helvetica, Arial, sans-serif;"><tspan id="SvgjsTspan2244">Apr</tspan><title>Apr</title></text><text id="SvgjsText2246" font-family="Helvetica, Arial, sans-serif" x="333.2626291910808" y="405.7903980026245" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="400" fill="#373d3f" class="apexcharts-text apexcharts-xaxis-label " style="font-family: Helvetica, Arial, sans-serif;"><tspan id="SvgjsTspan2247">May</tspan><title>May</title></text><text id="SvgjsText2249" font-family="Helvetica, Arial, sans-serif" x="416.578286488851" y="405.7903980026245" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="400" fill="#373d3f" class="apexcharts-text apexcharts-xaxis-label " style="font-family: Helvetica, Arial, sans-serif;"><tspan id="SvgjsTspan2250">Jun</tspan><title>Jun</title></text><text id="SvgjsText2252" font-family="Helvetica, Arial, sans-serif" x="499.89394378662115" y="405.7903980026245" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="400" fill="#373d3f" class="apexcharts-text apexcharts-xaxis-label " style="font-family: Helvetica, Arial, sans-serif;"><tspan id="SvgjsTspan2253">Jul</tspan><title>Jul</title></text></g><g id="SvgjsG2254" class="apexcharts-xaxis-title"><text id="SvgjsText2255" font-family="Helvetica, Arial, sans-serif" x="249.94697189331055" y="430.1999988555908" text-anchor="middle" dominant-baseline="auto" font-size="12px" font-weight="900" fill="#373d3f" class="apexcharts-text apexcharts-xaxis-title-text " style="font-family: Helvetica, Arial, sans-serif;">월</text></g></g><g id="SvgjsG2294" class="apexcharts-yaxis-annotations"></g><g id="SvgjsG2295" class="apexcharts-xaxis-annotations"></g><g id="SvgjsG2296" class="apexcharts-point-annotations"></g><rect id="SvgjsRect2297" width="0" height="0" x="0" y="0" rx="0" ry="0" opacity="1" stroke-width="0" stroke="none" stroke-dasharray="0" fill="#fefefe" class="apexcharts-zoom-rect"></rect><rect id="SvgjsRect2298" width="0" height="0" x="0" y="0" rx="0" ry="0" opacity="1" stroke-width="0" stroke="none" stroke-dasharray="0" fill="#fefefe" class="apexcharts-selection-rect"></rect></g></svg><div class="apexcharts-tooltip apexcharts-theme-light" style="left: 199.166px; top: 294.19px;"><div class="apexcharts-tooltip-title" style="font-family: Helvetica, Arial, sans-serif; font-size: 12px;">Apr</div><div class="apexcharts-tooltip-series-group apexcharts-active" style="order: 1; display: flex;"><span class="apexcharts-tooltip-marker" style="background-color: rgb(119, 182, 234);"></span><div class="apexcharts-tooltip-text" style="font-family: Helvetica, Arial, sans-serif; font-size: 12px;"><div class="apexcharts-tooltip-y-group"><span class="apexcharts-tooltip-text-y-label">KRW: </span><span class="apexcharts-tooltip-text-y-value">900000</span></div><div class="apexcharts-tooltip-goals-group"><span class="apexcharts-tooltip-text-goals-label"></span><span class="apexcharts-tooltip-text-goals-value"></span></div><div class="apexcharts-tooltip-z-group"><span class="apexcharts-tooltip-text-z-label"></span><span class="apexcharts-tooltip-text-z-value"></span></div></div></div><div class="apexcharts-tooltip-series-group apexcharts-active" style="order: 2; display: flex;"><span class="apexcharts-tooltip-marker" style="background-color: rgb(84, 84, 84);"></span><div class="apexcharts-tooltip-text" style="font-family: Helvetica, Arial, sans-serif; font-size: 12px;"><div class="apexcharts-tooltip-y-group"><span class="apexcharts-tooltip-text-y-label">USD: </span><span class="apexcharts-tooltip-text-y-value">0</span></div><div class="apexcharts-tooltip-goals-group"><span class="apexcharts-tooltip-text-goals-label"></span><span class="apexcharts-tooltip-text-goals-value"></span></div><div class="apexcharts-tooltip-z-group"><span class="apexcharts-tooltip-text-z-label"></span><span class="apexcharts-tooltip-text-z-value"></span></div></div></div><div class="apexcharts-tooltip-series-group apexcharts-active" style="order: 3; display: flex;"><span class="apexcharts-tooltip-marker" style="background-color: rgb(144, 0, 0);"></span><div class="apexcharts-tooltip-text" style="font-family: Helvetica, Arial, sans-serif; font-size: 12px;"><div class="apexcharts-tooltip-y-group"><span class="apexcharts-tooltip-text-y-label">JPY: </span><span class="apexcharts-tooltip-text-y-value">100000</span></div><div class="apexcharts-tooltip-goals-group"><span class="apexcharts-tooltip-text-goals-label"></span><span class="apexcharts-tooltip-text-goals-value"></span></div><div class="apexcharts-tooltip-z-group"><span class="apexcharts-tooltip-text-z-label"></span><span class="apexcharts-tooltip-text-z-value"></span></div></div></div></div><div class="apexcharts-xaxistooltip apexcharts-xaxistooltip-bottom apexcharts-theme-light" style="left: 306.228px; top: 432.99px;"><div class="apexcharts-xaxistooltip-text" style="font-family: Helvetica, Arial, sans-serif; font-size: 12px; min-width: 19.0546px;">Apr</div></div><div class="apexcharts-yaxistooltip apexcharts-yaxistooltip-0 apexcharts-yaxistooltip-left apexcharts-theme-light"><div class="apexcharts-yaxistooltip-text"></div></div></div></div>
                    </div>
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
