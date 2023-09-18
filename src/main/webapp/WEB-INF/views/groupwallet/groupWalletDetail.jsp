<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-14
  Time: 오후 6:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>

    <!-- Icons. Uncomment required icon fonts -->
    <link rel="stylesheet" href="../../../assets/vendor/fonts/boxicons.css"/>

    <!-- Core CSS -->
    <link rel="stylesheet" href="../../../assets/vendor/css/core.css" class="template-customizer-core-css"/>
    <link rel="stylesheet" href="../../../assets/vendor/css/theme-default.css" class="template-customizer-theme-css"/>
    <link rel="stylesheet" href="../../../assets/css/demo.css"/>

    <!-- Vendors CSS -->
    <link rel="stylesheet" href="../../../assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css"/>

    <!-- Page CSS -->

    <!-- Helpers -->
    <script src="../../../assets/vendor/js/helpers.js"></script>

    <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
    <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
    <script src="../../../assets/js/config.js"></script>

    <!-- jQuery 라이브러리 추가 -->
    <%--    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>--%>
    <script src="../../../js/jquery-3.6.1.js"></script>

    <script>
        $(document).ready(function () {
            let nav = $('#tab-button-nav');
            let sections = $('.tab-section');

            nav.on('click', '.tab-button', function () {
                let $clickedButton = $(this);
                let focusedTabId = $clickedButton.data('tab-section');

                sections.each(function () {
                    let section = $(this);
                    if (section.attr('id') === focusedTabId) {
                        section.removeAttr('hidden');
                    } else {
                        section.attr('hidden', true);
                    }
                });
            });
        });


    </script>


</head>
<body>

<header>
    <jsp:include page="../common/navbar.jsp"/>
</header>


<main>
    <div class="pageWrap">

        <div class="center">

            <div class="row">

                <div class="col-sm-6">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title"> ${groupWallet.getNickname()} 의 상세내역 </h5>
                            <br><br>
                            <a href="${pageContext.request.contextPath}/group-wallet/${id}/deposit"
                               class="btn btn-primary">채우기</a>
                            <a href="${pageContext.request.contextPath}/group-wallet/${id}/withdraw"
                               class="btn btn-primary">꺼내기</a>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">외화별 잔액</h5>
                            <br>
                            <h5 class="card-title">달러 ${walletDetailDto.getBalance().get("USD")}</h5>
                            <h5 class="card-title">엔 ${walletDetailDto.getBalance().get("JPY")}</h5>


                        </div>
                    </div>
                </div>

                <div>
                    <div class="allmenu_cont_wp">
                        <nav id="tab-button-nav">
                            <button class="tab-button" data-tab-section="tab-section-1">
                                모임지갑 내역
                            </button>
                            <button class="tab-button" data-tab-section="tab-section-2"
                                    datasrc="">
                                모임원 정보
                            </button>
                            <button class="tab-button" data-tab-section="tab-section-3" datasrc="">
                                모임 회비 규칙
                            </button>
                            <button class="tab-button" data-tab-section="tab-section-4" datasrc="">
                                모임 적금 조회
                            </button>
                            <button class="tab-button" data-tab-section="tab-section-5" datasrc="">
                                모임 연결 카드 조회
                            </button>


                        </nav>
                    </div>

                    <main>
                        <br>

                        <section id="tab-section-1" class="tab-section">
                            <c:forEach var="list" items="${walletDetailDto.getList()}" varStatus="status">
                                <div class="card" style="margin-top: 5px;">
                                    <div class="card-header">
                                            ${list.getAmount()}, ${list.type}
                                    </div>
                                    <div class="card-body">
                                        <h5 class="card-title">${list.getDetail()}</h5>

                                    </div>
                                </div>
                            </c:forEach>
                        </section>

                        <section id="tab-section-2" class="tab-section" hidden>
                            <c:forEach var="memberList" items="${groupMemberDtoList}" varStatus="status">
                                <div class="card" style="margin-top: 5px;">
                                    <div class="card-header">
                                            ${memberList.name}, ${memberList.roleToString}
                                    </div>
                                    <div class="card-body">

                                    </div>
                                </div>
                            </c:forEach>
                        </section>


                        <section id="tab-section-3" class="tab-section" hidden>
                            <div class="card" style="margin-top: 5px;">
                                <div class="card-header">
                                    <c:choose>
                                        <c:when test="${groupWallet.dueCondition}">
                                            회비 규칙 ${groupWallet.dueCondition},
                                            <p></p>
                                            매월 : ${groupWallet.dueDate}일, ${groupWallet.due}원
                                            <br>
                                            현재 누적 회비 : ${groupWallet.dueAccumulation}원
                                            <c:choose>
                                                <c:when test="${isChairman == true}">
                                                    <p>
                                                    <a href="${pageContext.request.contextPath}/group-wallet/${id}/rule"
                                                       class="btn btn-primary">회비 규칙 수정</a>
                                                </c:when>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            회비가 없습니다.
                                            <c:choose>
                                                <c:when test="${isChairman == true}">
                                                    <!-- 모임장 일 때만 -->
                                                    <!-- 회비 규칙 생성 폼으로 넘어가는 버튼 -->
                                                    <a href="${pageContext.request.contextPath}/group-wallet/${id}/rule"
                                                       class="btn btn-primary">회비 규칙 생성</a>
                                                </c:when>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                            </div>
                        </section>

                        <section id="tab-section-4" class="tab-section" hidden>
                                <div class="card" style="margin-top: 5px;">
                                    <div class="card-header">

                                    </div>
                                    <div class="card-body">
                                        <c:choose>
                                            <c:when test="${installmentDto == null}">
                                                적금을 가입하세요!
                                                <a href="${pageContext.request.contextPath}/saving"
                                                   class="btn btn-primary">적금 보러가기</a>
                                            </c:when>
                                            <c:otherwise>
                                                적금명 : ${installmentDto.savingName},
                                                <br>
                                                금리 : ${installmentDto.interestRate}%,
                                                <br>
                                                기간 : ${installmentDto.period}개월,
                                                <br>
                                                가입일 : ${installmentDto.insertDate},
                                                <br>
                                                만기일 : ${installmentDto.maturityDate}
                                                <br>
                                                현재까지 : ${installmentDto.totalAmount}원
                                                <br>
                                                납입일 : 매월 ${installmentDto.savingDate}일
                                                <br>
                                                납입금 : ${installmentDto.savingAmount}원
                                            </c:otherwise>
                                        </c:choose>

                                    </div>
                                </div>
                        </section>

                        <section id="tab-section-5" class="tab-section" hidden>
                            <c:forEach var="cardList" items="${cardIssuanceDtoList}" varStatus="status">
                                <div class="card" style="margin-top: 5px;">
                                    <div class="card-header">
                                            ${cardList.cardNumber}
                                    </div>
                                    <div class="card-body">

                                    </div>
                                </div>
                            </c:forEach>
                        </section>

                    </main>
                </div>
            </div>

        </div>

    </div>

</main>


<footer>

</footer>

</body>
</html>
