<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>깨비의 요술 지갑 - 적금 가입</title>
    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>

    <!-- Core CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/css/core.css" class="template-customizer-core-css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/css/theme-default.css" class="template-customizer-theme-css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/demo.css" />

    <!-- Vendors CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css" />

    <!-- Page CSS -->

    <!-- Helpers -->
    <script src="${pageContext.request.contextPath}/assets/vendor/js/helpers.js"></script>

    <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
    <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
    <script src="${pageContext.request.contextPath}/assets/js/config.js"></script>

    <!--배너용 css-->
    <script src="${pageContext.request.contextPath}/css/heroBanner.css"></script>
<%--    <link rel="stylesheet" type="text/css" href="../../css/loginform.css" /><!--로그인 폼 css-->--%>
<%--    <link rel="stylesheet"type="text/css" href="/css/common.css">--%>

</head>
<body>

    <jsp:include page="../common/navbar.jsp"/>

    <div class="pageWrap">
            <div class="center">
                <h1 class="titDep1">모임지갑 목록</h1>
                <h5>${SavingInstallmentDto.getGroupWalletId}</h5>
                <div>
<%--                    <c:forEach var="list" varStatus="status" items="${gWalletList}">--%>
<%--                        <div style="margin-top: 5px">--%>
<%--                            <div class="card">--%>
<%--                                <div class="card-header">--%>
<%--                                        ${list.getNickname()}--%>
<%--                                </div>--%>
<%--                                <div class="card-body">--%>
<%--                                    <h5 class="card-title">원화 잔액 : ${list.getBalance()}</h5>--%>
<%--                                    <a href="/group-wallet/${list.getGroupWalletId()}" class="btn btn-primary">상세보기</a>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                    </c:forEach>--%>
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

                    <input type="submit" class="button buttonBlue" value="가입하기">
                    <div class="ripples buttonRipples"><span class="ripplesCircle"></span></div>
                    </input>
                </form>

            </div>
    </div>
</body>
</html>
