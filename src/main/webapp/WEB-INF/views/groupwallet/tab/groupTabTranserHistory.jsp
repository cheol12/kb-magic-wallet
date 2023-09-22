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
                    <h3 class="col mb-0">
                        거래 내역
                    </h3>
                </div>
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
</body>
</html>
