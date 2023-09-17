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
                <div class="content-wrapper">
                    <div class="container-xxl flex-grow-1 container-p-y">
                        <h4 class="fw-bold py-3 mb-4">적금 가입하기</h4>
                        <!-- 폼 레이아웃 -->
                        <div class="row">
                            <div class="col-xxl">
                                <div class="card mb-4">
                                    <div class="card-body">
                                        <form method="post" action="${pageContext.request.contextPath}/saving/${id}/form">
                                            <!--적금 상품 아이디-->
                                            <input type="hidden" name="savingId" value=${id}>
                                            <!-- 모임지갑 선택 -->
                                            <div class="row mb-3">
                                                <label class="col-sm-2 col-form-label">모임지갑 선택</label>
                                                <div class="col-sm-10">
                                                    <select name="groupWalletId" required>
                                                        <!-- 개인이 가지고 있는 그룹 통장 중에서 선택 -->
                                                        <c:forEach var="wallet" items="${gWalletList}">
                                                            <option value="${wallet.getGroupWalletId()}">${wallet.getNickname()}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <!-- 납입 금액 -->
                                            <div class="row mb-3">
                                                <label class="col-sm-2 col-form-label" >납부 금액(/월)</label>
                                                <div class="col-sm-10">
                                                    <input
                                                            type="text"
                                                            class="form-control"
                                                            name="savingAmount"
                                                            placeholder="(원)"
                                                    />
                                                </div>
                                            </div>
                                            <!-- 납부일-->
                                            <div class="mb-3 row">
                                                <label for="html5-date-input" class="col-md-2 col-form-label">납부일</label>
                                                <div class="col-md-10">
                                                    <select name="savingDate" class="form-control" id="html5-date-input">
                                                        <option value="">일 선택</option>
                                                        <c:forEach var="day" begin="1" end="28">
                                                            <option value="${day}">${day}일</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <!-- 적금 가입하기 -->
                                            <div class="row justify-content-end">
                                                <div class="col-sm-10">
                                                    <button type="submit" class="btn btn-primary">가입하기</button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>




<%--                &lt;%&ndash;가입 금액, 출금 지갑 정보 받고 전송하도록 form&ndash;%&gt;--%>
<%--                <form method="post" action="${pageContext.request.contextPath}/saving/${id}/form">--%>
<%--                    <div class="row g-3 align-items-center">--%>
<%--                        <input type="text" name="savingAmount"><span class="highlight"></span><span class="bar"></span>--%>
<%--                        <label>가입 금액</label>--%>
<%--                    </div>--%>
<%--                    <div class="group">--%>
<%--                        <select name="groupWalletId" required>--%>
<%--                            <!-- 개인이 가지고 있는 그룹 통장 중에서 선택 -->--%>
<%--                            <c:forEach var="wallet" items="${gWalletList}">--%>
<%--                                <option value="${wallet.getGroupWalletId()}">${wallet.getNickname()}</option>--%>
<%--                            </c:forEach>--%>
<%--                        </select>--%>

<%--                        <input type="text" name="groupWalletId"><span class="highlight"></span><span class="bar"></span>--%>
<%--                        <label>출금 지갑 선택(모임 지갑 별칭으로)</label>--%>
<%--                    </div>--%>

<%--                    <input type="submit" class="button buttonBlue" value="가입하기">--%>
<%--                    <div class="ripples buttonRipples"><span class="ripplesCircle"></span></div>--%>
<%--                    </input>--%>
<%--                </form>--%>

<%--&lt;%&ndash;                    &ndash;%&gt;--%>


<%--                <h1 class="titDep1">모임지갑 목록</h1>--%>
<%--                <h5>${SavingInstallmentDto.getGroupWalletId}</h5>--%>
<%--                <div>--%>
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
<%--                </div>--%>
<%--                &lt;%&ndash;가입 금액, 출금 지갑 정보 받고 전송하도록 form&ndash;%&gt;--%>
<%--                <form method="post" action="${pageContext.request.contextPath}/saving/${id}/form">--%>
<%--                    <div class="group">--%>
<%--                        <input type="text" name="savingAmount"><span class="highlight"></span><span class="bar"></span>--%>
<%--                        <label>가입 금액</label>--%>
<%--                    </div>--%>
<%--                    <div class="group">--%>
<%--                            <select name="groupWalletId" required>--%>
<%--                                <!-- 개인이 가지고 있는 그룹 통장 중에서 선택 -->--%>
<%--                                <c:forEach var="wallet" items="${gWalletList}">--%>
<%--                                    <option value="${wallet.getGroupWalletId()}">${wallet.getNickname()}</option>--%>
<%--                                </c:forEach>--%>
<%--                            </select>--%>

<%--                        <input type="text" name="groupWalletId"><span class="highlight"></span><span class="bar"></span>--%>
<%--                        <label>출금 지갑 선택(모임 지갑 별칭으로)</label>--%>
<%--                    </div>--%>

<%--                    <input type="submit" class="button buttonBlue" value="가입하기">--%>
<%--                    <div class="ripples buttonRipples"><span class="ripplesCircle"></span></div>--%>
<%--                    </input>--%>
<%--                </form>--%>

<%--            </div>--%>
<%--    </div>--%>
</body>
</html>
