<%--
  Created by IntelliJ IDEA.
  User: 김진형
  Date: 2023-09-19
  Time: 오전 9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
    <title>Title</title>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            // 환율 데이터 가져오기 (이 부분을 자신의 컨트롤러에서 데이터를 가져오도록 수정)
            var usdRate = ${usdRate}; // USD 환율 데이터
            <%--var jpyRate = ${jpyRate}; // JPY 환율 데이터--%>

            function generateDates(count) {
                var dates = [];
                var currentDate = new Date(); // 현재 날짜를 가져옵니다.

                for (var i = 0; i < count; i++) {
                    // 날짜를 1일씩 이전으로 이동합니다.
                    var date = new Date(currentDate);
                    date.setDate(currentDate.getDate() - i);

                    // 날짜를 "YYYY-MM-DD" 형식의 문자열로 변환합니다.
                    var dateString = date.toISOString().split('T')[0];
                    dates.push(dateString);
                }

                return dates.reverse(); // 배열을 뒤집어서 과거부터 현재로 정렬합니다.
            }


            var dates = generateDates(usdRate.length);

            var options = {
                series: [{
                    name: "USD",
                    data: usdRate // USD 환율 데이터 배열
                }],
                chart: {
                    height: 350,
                    type: 'line',
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
                    text: 'Exchange Rates by Date',
                    align: 'left'
                },
                grid: {
                    row: {
                        colors: ['#f3f3f3', 'transparent'],
                        opacity: 0.5
                    },
                },
                // xaxis: {
                //     tickAmount: 1000000,
                //     categories:  // 날짜 데이터
                // }
            };

            var chart = new ApexCharts(document.querySelector("#exchangeChart"), options);
            chart.render();
        });
    </script>
</head>
<body>

<div id="exchangeChart">

</div>
${usdRate.size()}
<hr>
${jpyRate.size()}

</body>
</html>
