<%--
  Created by IntelliJ IDEA.
  User: 김진형
  Date: 2023-09-01
  Time: 오후 3:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<jsp:include page="common/navbar.jsp"/>
<h1>깨비의 요술지갑</h1>
<button>모임지갑</button><br>
<button>개인지갑</button><br>
<button>적금</button><br>
<button>환전</button><br>

<input type="text" class="form-control" id="userId" name="userId"
       placeholder="userId">
<input type="password" class="form-control" id="pwd" name="pwd"
       placeholder="password">
</body>
</html>
