<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: 김진형
  Date: 2023-09-20
  Time: 오후 5:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>지갑정보 차트</title>

    <script>
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
                <a href="${pageContext.request.contextPath}/group-wallet/${id}/deposit"
                   class="btn btn-primary">채우기</a>
                <a href="${pageContext.request.contextPath}/group-wallet/${id}/withdraw"
                   class="btn btn-primary">꺼내기</a>
            </ul>
        </div>
    </div>
</div>

</body>
</html>
