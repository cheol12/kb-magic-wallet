<%--
  Created by IntelliJ IDEA.
  User: 김진형
  Date: 2023-09-20
  Time: 오후 7:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>모임 거래 내역</title>
</head>
<body>
<div class="tab-pane fade show active" id="navs-top-home" role="tabpanel">
    <div class="card">
        <h5 class="card-header">
            <div class="row g-2">
                <div class="col mb-0">
                    거래 내역
                </div>
            </div>
        </h5>

        <div class="table-responsive text-nowrap">
            <table class="table table">
                <thead>
                <tr>
                    <th><i class="text-center fab fa-angular fa-lg text-danger me-3"></i>거래일자</th>
                    <th><i class="text-center fab fa-angular fa-lg text-danger me-3"></i>거래시간</th>
                    <th><i class="text-center fab fa-angular fa-lg text-danger me-3"></i>입금()</th>
                    <th><i class="text-center fab fa-angular fa-lg text-danger me-3"></i>출금()</th>
                    <th><i class="text-center fab fa-angular fa-lg text-danger me-3"></i>내용</th>
                    <th><i class="text-center fab fa-angular fa-lg text-danger me-3"></i>잔액()</th>
                </tr>
                </thead>
                <tbody class="table-border-bottom-0" id="dateSelectHistory">
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
