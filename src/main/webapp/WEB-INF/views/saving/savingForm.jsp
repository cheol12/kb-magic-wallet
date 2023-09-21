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
                                        <!--이용 약관-->
<%--                                        <div class="alert alert-warning">--%>
<%--                                            <h5 class="clert-heading fw-bold mb-1"><이용 약관></h5>--%>
<%--                                            <h6 class="alert-heading fw-bold mb-1">약관을 주의깊게 읽어주세요</h6>--%>
<%--                                            <p class="mb-0">제1조 적용 범위<br>--%>
<%--                                                1. 이 적금의 거래과정에서 발생하는 전자금융거래에 관한 사항은 전자금융거래기본약관을 적용합니다.</p>--%>
<%--                                        </div>--%>
                                        <div class="accordion mt-3" id="accordionExample">
                                            <div class="card accordion-item active">
                                                <h2 class="accordion-header" id="headingOne">
                                                    <button
                                                            type="button"
                                                            class="accordion-button collapsed"
                                                            data-bs-toggle="collapse"
                                                            data-bs-target="#accordionOne"
                                                            aria-expanded="false"
                                                            aria-controls="accordionOne"
                                                    >
                                                        약관 동의
                                                    </button>
                                                </h2>

                                                <div
                                                        id="accordionOne"
                                                        class="accordion-collapse collapse"
                                                >
                                                    <div class="accordion-body">
                                                        <p>적립식적금약관



                                                        <hr>
                                                        <p>제 1 조 (적용범위)

                                                        <p>① 적립식적금(이하 '이 예금'이라 합니다)이라 함은 기간을 정하고 그 기간 중에 미리 정한 금액이나 불특정 금액을 정기 또는 부정기적으로 입금하는 예금을 말합니다.
                                                        <p>② 이 약관에서 정하지 아니한 사항은 예금거래기본약관의 규정을 적용합니다.</p>


                                                        <hr>
                                                        <p>제 2 조 (지급시기)
                                                        <p>이 예금은 약정한 만기일 이후 거래처가 청구할 때 지급합니다. 다만, 거래처가 부득이한 사정으로 청구할 때에는 만기전이라도 지급할 수 있습니다.


                                                        <hr>
                                                        <p>제 3 조 (저축금의 입금)
                                                        <p>이 약관에서 사용하는 용어의 정의는 다음과 같습니다.
                                                        <p>거래처는 계약기간 동안 매월 약정한 날짜에 월저축금을 입금하여야 합니다.


                                                        <hr>
                                                        <p>제 4 조 (거래방법 및 인감신고)
                                                        <p>개인인 경우로서 거래처가 인감(서명) 신고를 원하지 않는 경우에는 인감(서명) 신고를 생략할 수 있습니다.


                                                        <hr>
                                                        <p>제 5 조 (이자 및 만기지급금과 지연입금)
                                                        <p>① 이 예금의 월저축금을 매월 약정한 날짜에 입금하였을 때에는
                                                        은행은 저축금마다 입금일로부터 만기일 전날까지의 기간에 대하여 계약일 당시
                                                        영업점에 게시한 이율로 셈한 이자를 저축금 총액 (이하 '원금'이라 합니다)에 더한 금액 (이하 '계약금액'이라 합니다)을 만기 지급금으로써 지급합니다.</p>
                                                        <p>② 이 예금 중 변동금리를 적용하는 예금은 이율을 바꾼 때 바꾼 날부터 바꾼 이율로 셈하여 이자를 지급합니다.
                                                        <p>③ 거래처가 월저축금을 약정일보다 늦게 입금하였을 때에는 은행은 거래처의 요청에 따라 총지연일수에서 총선납일수를 뺀 순지연일수에 대하여
                                                        계약일 당시 영업점에 게시한 임금지연이율로 셈한 금액을 계약 금액에서 빼거나 순지연일수를 계약 원ㄹ수로 나눈 월평균 지연일수만큼 만기 일을 늦출 수 있습니다.
                                                        <p>④ 총선납일수가 총지연일수보다 많은 경우에는 은행은 계약금액만을 지급합니다.

                                                        <hr>
                                                        <p>제 6 조 (만기앞당김 지금)
                                                        <p>거래처가 모든 회차의 월저축금을 입금한 후 만기일 전 1개월 안에 청구하면 은행은 지급하는 날부터 만기일 전날까지의 기간에 대하여
                                                        계약일 당시 영업점에 게시한 만기 앞당김이율로 셈한 금액을 계약금액에서 빼고 지급합니다.


                                                        <hr>
                                                        <p>제 7 조 (중도해지이율 및 만기후이율)
                                                        <p>① 거래처가 만기일 후 지급청구한 때에는 만기지급액에 만기일부터 지급일 전날까지 기간에 대해
                                                        계약일 당시 영업점에 게시한 만기후 이율로 셈한 이자를 더하여 지급합니다.</p>
                                                        <p>② 거래처가 만기일 전에 지급청구한 때에는 월저축금마다 입금일부터 지급일 전날까지의 기간에
                                                        대해 계약일 당시 영업점에 게시한 중도해지이율로 셈한 이자를 원금에 더하여 지급합니다.</p>
                                                        <p>③ 거래처가 만기일까지 약정한 모든 회차의 월저축금을 입금하지 않고 만기일 이후에 청구하였을 때에는 전항의 중도 해지이율로 셈한이자를 지급합니다.</p>

                                                    </div>
                                                </div>
                                            </div>
                                        <!--약관 동의-->
                                        <div class="form-check mb-3">
                                            <input
                                                    class="form-check-input"
                                                    type="checkbox"
                                                    name="accountActivation"
                                                    id="accountActivation"
                                            />
                                            <label class="form-check-label" for="accountActivation">약관에 동의합니다.</label>
                                        </div>
                                        <!--폼 입력-->
                                        <form method="post" action="${pageContext.request.contextPath}/saving/${id}/form">
                                            <!--적금 상품 이름 read-onlu-->
                                            <div class="row mb-3">
<%--                                                <div class="col-sm-10">--%>
                                                    <label for="exampleFormControlReadOnlyInput1" class="form-label">적금 이름</label>
                                                    <input
                                                            class="form-control"
                                                            type="text"
                                                            id="exampleFormControlReadOnlyInput1"
                                                            placeholder="${saving.getName()}"
                                                            readonly
                                                    />
<%--                                                </div>--%>
                                            </div>
                                            <!--적금 상품 아이디-->
                                            <input type="hidden" name="savingId" value=${id}>
                                            <!-- 모임지갑 선택 -->
                                            <div class="row mb-3">
                                                <label for="largeSelect" class="form-label">모임지갑 선택</label>
<%--                                                <div class="col-sm-10">--%>
                                                    <select id="largeSelect" class="form-select form-select-lg" name="groupWalletId" required>
                                                        <!-- 개인이 가지고 있는 그룹 통장 중에서 선택 -->
                                                        <c:forEach var="wallet" items="${gWalletList}">
                                                            <option value="${wallet.getGroupWalletId()}">${wallet.getNickname()}</option>
                                                        </c:forEach>
                                                    </select>

<%--                                                </div>--%>
                                            </div>
                                            <!--만기일-->
                                            <input type="hidden" name="maturityDate" id="maturityDateField">
                                            <!-- 납입 금액 -->
                                            <div class="row mb-3">
                                                <label for="largeInput" class="form-label">납부 금액(/월)</label>
<%--                                                <div class="col-sm-10">--%>
                                                    <input
                                                            id="largeInput"
                                                            class="form-control form-control-lg"
                                                            name="savingAmount"
                                                            type="text"
                                                            placeholder="(원)"
                                                    />
<%--                                                </div>--%>
                                            </div>
                                            <!-- 납부일-->
                                            <div class="row mb-3">
                                                <label for="largeSelect" class="form-label">납부일</label>
<%--                                                <div class="col-md-10">--%>
                                                    <select name="savingDate" class="form-select form-select-lg" id="largeSelect">
                                                        <option value="">날짜(일) 선택</option>
                                                        <c:forEach var="day" begin="1" end="28">
                                                            <option value="${day}">${day}일</option>
                                                        </c:forEach>
                                                    </select>
<%--                                                </div>--%>
                                            </div>
                                            <!-- 적금 가입하기 -->
                                            <div class="row mb-3">
<%--                                                <div class="col-sm-10">--%>
                                                    <button type="submit" class="btn btn-primary">가입하기</button>
<%--                                                </div>--%>
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
