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
                                            <!--만기일-->
                                            <input type="hidden" name="maturityDate" id="maturityDateField">
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
                                        <script>
                                            const savingPeriod = ${saving.getPeriod()};

                                            // 개월 수를 더하는 함수
                                            function addMonths(date, months) {
                                                const resultDate = new Date(date);
                                                // 연도 추가
                                                const newYear = resultDate.getFullYear() + Math.floor((resultDate.getMonth() + months) / 12);
                                                // 개월 추가
                                                const newMonth = (resultDate.getMonth() + months) % 12;
                                                resultDate.setFullYear(newYear);
                                                resultDate.setMonth(newMonth);
                                                return resultDate;
                                            }

                                            // 현재 날짜와 개월 수를 가져와서 maturityDate를 설정하는 함수
                                            function setMaturityDate() {
                                                const now = new Date();
                                                // 개월 수를 가져옴
                                                const monthsToAdd =savingPeriod;
                                                // 개월 수를 현재 날짜에 더함
                                                const maturityDate = addMonths(now, monthsToAdd);

                                                // maturityDateField에 날짜를 설정 (ISO 8601 형식)
                                                const maturityDateField = document.getElementById("maturityDateField");
                                                // "YYYY-MM-DDTHH:mm:ss" 형식을 ISO 8601 형식으로 변환
                                                const formattedDate = maturityDate.toISOString().slice(0, 19).replace('T', ' ');
                                                maturityDateField.value = formattedDate;
                                            }

                                            // setMaturityDate 함수를 호출하여 maturityDate를 설정
                                            setMaturityDate();


                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
</body>
</html>
