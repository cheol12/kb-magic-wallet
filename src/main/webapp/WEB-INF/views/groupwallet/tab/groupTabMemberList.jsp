<%--
  Created by IntelliJ IDEA.
  User: 김진형
  Date: 2023-09-20
  Time: 오후 7:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>모임 멤버 조회</title>
    <script>
    </script>
</head>
<body>
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

</body>
</html>
