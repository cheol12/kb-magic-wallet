<%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-11
  Time: 오후 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>깨비의 요술 지갑 - 환전</title>

    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link rel="stylesheet" type="text/css" href="/css/exchangeMain.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            var usdRate = ${usdRate}; // USD 환율 데이터
            var optionsUSD = {
                series: [{
                    name: "USD",
                    data: usdRate // USD 환율 데이터 배열
                }],
                chart: {
                    height: 350,
                    type: 'area',
                    zoom: {
                        enabled: false
                    }
                },
                dataLabels: {
                    enabled: false
                },
                stroke: {
                    curve: 'smooth'
                },
                title: {
                    text: '달러(USD) 환율',
                    align: 'left'
                },
                grid: {
                    row: {
                        colors: ['#f3f3f3', 'transparent'],
                        opacity: 0.5
                    },
                },
            };

            var jpyRate = ${jpyRate}; // USD 환율 데이터
            var optionsJPY = {
                series: [{
                    name: "JPY",
                    data: jpyRate // USD 환율 데이터 배열
                }],
                chart: {
                    height: 350,
                    type: 'area',
                    zoom: {
                        enabled: false
                    }
                },
                dataLabels: {
                    enabled: false
                },
                stroke: {
                    curve: 'smooth'
                },
                title: {
                    text: '엔화(JPY)환율',
                    align: 'left'
                },
                grid: {
                    row: {
                        colors: ['#f3f3f3', 'transparent'],
                        opacity: 0.5
                    },
                },
                colors:['#F44336', '#E91E63', '#9C27B0']
            };

            var chart1 = new ApexCharts(document.querySelector("#usdExchangeChart"), optionsUSD);
            var chart2 = new ApexCharts(document.querySelector("#jpyExchangeChart"), optionsJPY);
            chart1.render();
            chart2.render();
        });
    </script>

</head>
<body>
<header>
    <jsp:include page="../common/navbar.jsp"/>
</header>
<main>
    <div class="pageWrap">
        <div class="center">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <div class="card">
                        <div class="card-body">
                            <h3 class="card-title">온라인 환전</h3>
                            <p class="card-text">내 개인지갑으로 간편하게 온라인 환전!</p>
                            <a href="/exchange/onlineExchange" class="btn btn-primary">환전하러 가기</a>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="card">
                        <div class="card-body">
                            <h3 class="card-title">오프라인 환전</h3>
                            <p class="card-text">실제 현금으로!! 오프라인 환전!</p>
                            <a href="/exchange/offline" class="btn btn-primary">환전하러 가기</a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row mb-2">
                <div class="card">
                    <div id="usdExchangeChart">
                    </div>
                </div>
            </div>

            <div class="row mb-2">
                <div class="card">
                    <div id="jpyExchangeChart">
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<footer>

</footer>


</body>
</html>
