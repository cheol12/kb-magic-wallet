<%--
  Created by IntelliJ IDEA.
  User: hyunji
  Date: 2023-09-15
  Time: 오후 3:13
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>적금 가입</title>
    <link rel="stylesheet" type="text/css" href="../../css/loginform.css" /><!--로그인 폼 css-->
    <link rel="stylesheet"type="text/css" href="/css/common.css">

</head>
<body>

    <jsp:include page="../common/navbar.jsp"/>

    <div class="pageWrap">
            <div class="center">
                <h1 class="titDep1">적금가입-모임지갑으로</h1>
                <h5>${SavingInstallmentDto.getGroupWalletId}</h5>
                <div>
                    <c:forEach var="list" varStatus="status" items="${gWalletList}">
                        <div style="margin-top: 5px">
                            <div class="card">
                                <div class="card-header">
                                        ${list.getNickname()}
                                </div>
                                <div class="card-body">
                                    <h5 class="card-title">원화 잔액 : ${list.getBalance()}</h5>
                                    <a href="/group-wallet/${list.getGroupWalletId()}" class="btn btn-primary">상세보기</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <%--가입 금액, 출금 지갑 정보 받고 전송하도록 form--%>
                <form method="post" action="${pageContext.request.contextPath}/saving/${id}/form">
                    <div class="group">
                        <input type="text" name="savingAmount"><span class="highlight"></span><span class="bar"></span>
                        <label>가입 금액</label>
                    </div>
                    <div class="group">
<%--                        <select name="groupWalletId" name="groupWalletId">--%>
<%--                            <option value="1">두근두근 외화적금</option>--%>
<%--                            <option value="2">쿵쾅쿵쾅 외화적금</option>--%>
<%--                            <option value="3">근두근두 외화적금</option>--%>
<%--                            <option value="4">허겁지겁 외화적금</option>--%>
<%--                        </select>--%>
                        <input type="text" name="groupWalletId"><span class="highlight"></span><span class="bar"></span>
                        <label>출금 지갑 선택(모임 지갑 별칭으로)</label>
                    </div>

                    <input type="submit" class="button buttonBlue" value="적금가입">
                    <div class="ripples buttonRipples"><span class="ripplesCircle"></span></div>
                    </input>
                </form>

            </div>
    </div>
</body>
</html>
