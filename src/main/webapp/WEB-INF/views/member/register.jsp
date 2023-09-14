<%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-11
  Time: 오후 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원 가입</title>
    <link rel="stylesheet"type="text/css" href="/css/common.css">
</head>
<body>
    <jsp:include page="../common/navbar.jsp"/>

    <div class="pageWrap">
        <div class="center">
            <form>
                <input type="text" name="id">
                <input type="password" name="password">
                <input type="text" name="name">
                <input type="text" name="account">
            </form>
        </div>
    </div>
</body>
</html>
