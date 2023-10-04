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
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" type="text/css" href="../../../css/exchangeMain.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>

<%--    <script type="text/javascript">
        $(document).ready(function () {
            new Chart(document.getElementById("line-chart-USD"), {
                type: 'line',
                data: {
                    labels: ['8일전', '7일전', '6일전', '5일전', '4일전', '3일전', '2일전', '1일전', '금일', '1일후', '2일후', '3일후', '4일후', '5일후', '6일후', '7일후'],
                    datasets: [{
                        data: [1334.4, 1332.4, 1326.1, 1327.8, 1326.1, 1327.6, 1326, 1323.7, 1329.3],
                        label: "USD",
                        borderColor: "#808080",
                        fill: true
                    }, {
                        data: [, , , , , , , , 1329.3, ${predict.get(0)}, ${predict.get(1)}, ${predict.get(2)}, ${predict.get(3)}, ${predict.get(4)}, ${predict.get(5)}, ${predict.get(6)}],
                        label: "USD Predict",
                        borderColor: "#FF0000",
                        fill: true
                    },
                    ]
                },
                options: {
                    title: {
                        display: true,
                        text: 'USD 환율'
                    },
                    responsive: false, // 반응형 크기 조절 비활성화
                    maintainAspectRatio: false, // 종횡비 유지 비활성화

                    scales: {
                        xAxes: [{
                            gridLines: {
                                display: true,
                                drawBorder: true,
                                drawOnChartArea: false, // 차트 영역 안에 그리지 않음
                            },

                        }]
                    }
                }
            });
            new Chart(document.getElementById("line-chart-JPY"), {
                type: 'line',
                data: {
                    labels: ['8일전', '7일전', '6일전', '5일전', '4일전', '3일전', '2일전', '1일전', '금일', '1일후', '2일후', '3일후', '4일후', '5일후', '6일후', '7일후'],
                    datasets: [{
                        data: [905.75, 909.08, 901.59, 901.18, 899.08, 897.91, 898.34, 896.18, 896.45],
                        label: "JPY",
                        borderColor: "#808080",
                        fill: true
                    }, {
                        data: [, , , , , , , , 896.45, ${predictJPY.get(0)}, ${predictJPY.get(1)}, ${predictJPY.get(2)}, ${predictJPY.get(3)}, ${predictJPY.get(4)}, ${predictJPY.get(5)}, ${predictJPY.get(6)}],
                        label: "JPY Predict",
                        borderColor: "#FF0000",
                        fill: true
                    },
                    ]
                },
                options: {
                    title: {
                        display: true,
                        text: 'JPY 환율'
                    },
                    responsive: false, // 반응형 크기 조절 비활성화
                    maintainAspectRatio: false, // 종횡비 유지 비활성화

                    scales: {
                        xAxes: [{
                            gridLines: {
                                display: true,
                                drawBorder: true,
                                drawOnChartArea: false, // 차트 영역 안에 그리지 않음
                            },

                        }]
                    }
                }
            });
        });

    </script>--%>
</head>
<body>
<header>
    <jsp:include page="../common/navbar.jsp"/>
</header>
<main>
    <div class="pageWrap">
        <div class="center">
            <div class="banner">
                <img src="${pageContext.request.contextPath}/images/exchange/exchangeBanner.jpg"
                     style="width: 1200px; height: 400px;  border-radius: 25px;">
            </div>
            <br>
            <div class="row mb-2">
                <div class="col-sm-6">
                    <div class="card">
                        <div class="card-body">
                            <h3 class="card-title">환전</h3>
                            <p class="card-text">내 개인지갑으로 간편하게 원화에서 외화로!</p>
                            <a href="/exchange/online/form" class="btn btn-primary">환전하러 가기</a>
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

                <div class="col-sm-6">
                    <div class="card">
                        <div class="card-body">
                            <h3 class="card-title">재환전</h3>
                            <p class="card-text">내 개인지갑으로 간편하게 원화에서 외화로!</p>
                            <a href="/exchange/online/re-form" class="btn btn-primary">환전하러 가기</a>
                        </div>
                    </div>
                </div>

            </div>
            <div class="card mb-2 ">
                <div class="card-header">
                    <h3>USD 환율 및 예측</h3>
                </div>
                <div class="card-body">
                    <canvas id="line-chart-USD" width="1100" height="400" class="center"></canvas>
                </div>
            </div>
            <div class="card mb-2">
                <div class="card-header">
                    <h3>JPY 환율 및 예측</h3>
                </div>
                <div class="card-body">
                    <canvas id="line-chart-JPY" width="1100" height="400" class="center"></canvas>
                </div>
            </div>
        </div>
        <br>
        <br>
        <br>
        <br>

    </div>
</main>
<footer>
</footer>
</body>
</html>
