<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-11
  Time: 오후 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>깨비의 요술 지갑 - 모임지갑</title>

    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>

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
    <script src="../../../assets/js/validation.js"></script>
    <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
    <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
    <script src="../../../assets/js/config.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
    <script type="text/javascript">
        // 모임장이면 모임원 관리
        function displayMemberList() {
            let isChairman = ${isChairman}

            if (isChairman) {
                document.getElementById("hiddenNavItem").style.display = "block";
            }
        }

        // 모임지갑 탈퇴 확인창 메소드
        function confirmLeave(id) {
            // 모임지갑 이름이 안불러와짐
            let leave = confirm('모임지갑에서 떠나시겠습니까?');
            if (leave) {
                // Ajax 요청을 보냅니다.
                $.ajax({
                    type: "GET",
                    url: "${pageContext.request.contextPath}/group-wallet/" + id + "/leave",
                    success: function (data) {
                        // 요청이 성공하면 여기에서 추가 로직을 수행할 수 있습니다.
                        // 예를 들어, 성공한 후에 어떤 동작을 수행할 수 있습니다.
                        console.log("컨트롤러 메소드 호출 성공!");
                        // 페이지 새로고침 또는 다른 동작 수행
                        location.href = "${pageContext.request.contextPath}/group-wallet/"; // 페이지 새로고침
                    },
                    error: function () {
                        // 요청이 실패하면 여기에서 오류 처리를 수행할 수 있습니다.
                        console.log("컨트롤러 메소드 호출 실패!");
                        // 오류 처리 로직 추가
                    }
                });
            }
        }

        // 모달창을 띄우는 function
        function PopupDetail(clicked_element, content) {
            var row_td = clicked_element.getElementsByTagName("td");
            var modal = document.getElementById("detail-modal");

            document.getElementById("detail-date").innerHTML = row_td[0].innerHTML;
            document.getElementById("detail-time").innerHTML = row_td[1].innerHTML;
            if (row_td[2].innerHTML === "입금액: -") {
                document.getElementById("detail-amount").innerHTML = row_td[3].innerHTML;
            } else {
                document.getElementById("detail-amount").innerHTML = row_td[2].innerHTML;
            }
            document.getElementById("detail-content").innerHTML = content;
            document.getElementById("detail-balance").innerHTML = row_td[5].innerHTML;
            modal.style.display = 'block';
        }

        // 모임지갑 상세내역
        function historyCall() {
            $.ajax({
                url: "${pageContext.request.contextPath}/group-wallet/${id}/history",
                type: "post",
                dataType: "json",
                success: function (result, status) {
                    // 화면에 갱신
                    var str = "";
                    $.each(result, function (i) {
                        str += '<TR id="searchDateResult" onclick="PopupDetail(this)" data-bs-toggle="modal" data-bs-target="#detailModal">'
                        // 날짜 시간 처리
                        str += '<TD>' + result[i].dateTime + '</TD>';
                        str += '<TD>' + result[i].dateTime + '</TD>';
                        // 입금액 출금액 처리
                        if (result[i].type === '입금') {
                            str += '<TD> ' + result[i].amount + ' ' + result[i].currencyCode + '</TD><TD> </TD>';
                        } else {
                            str += '<TD> </TD>' + '<TD> ' + result[i].amount + ' ' + result[i].currencyCode + '</TD>';
                        }
                        str += '<TD>  ' + result[i].type + '</TD>';
                        str += '<TD>' + result[i].balance + ' ' + result[i].currencyCode + '</TD>';
                        str += '</TR>';
                    });
                    $("#dateSelectHistory").append(str);
                },
                error: function (result, status) {

                },
            })
        }

        // AJAX READY
        $(document).ready(function () {
            console.log("모임 상세 페이지 로드");
            memberCall();
            historyCall();
            displayMemberList();

            let countMember = ${countMember};

            document.getElementById("deleteButton").addEventListener("click", function (event) {
                if (countMember > 1) {
                    event.preventDefault();
                    alert("모임원이 없을 때 모임 지갑을 삭제할 수 있습니다.");
                }
            });

            // 모달 닫기 (조회기간 설정 버튼 누른 후)
            $("#submitButton").on("click", function () {
                $("#basicModal").modal("hide");
            });

            // 모달 닫힌 후에 스크롤, 배경색 관련 처리
            $("#basicModal").on("hidden.bs.modal", function () {

                // 모달이 완전히 사라진 후에 배경색 변경 및 스크롤 관련 처리
                $("body").removeClass("modal-open");
                $(".modal-backdrop").remove();

                // 필요한 스크롤 관련 설정
                $("body").css("overflow", "auto");
                // 여기에서 스크롤을 허용하도록 설정하는 코드를 추가해야 합니다.
            });

        });

        function cardList() {
            let memberId = ${loginMemberDto.memberId};

            $.ajax({
                url: '${pageContext.request.contextPath}/group-wallet/${id}/card/list',
                type: 'GET',
                dataType: 'json',
                success: function (response) {
                    let cardExists = false;
                    let content = '';

                    response.cardIssuanceDtoList.forEach(card => {
                        if (card.member.memberId === memberId) {
                            cardExists = true;
                        }
                        let imagePath = `${pageContext.request.contextPath}/assets/img/card/card${card.cardNumber.slice(-1)}.png`;
                        content += `
                <div class="col-md-6 col-xl-4">
                    <div class="card shadow-none bg-transparent border border-secondary mb-3">
                        <div class="card-body">
                            <h5 class="card-title">${card.member.name}</h5>
                            <img src="${pageContext.request.contextPath}/assets/img/card/card${fn:substring(card.cardNumber, fn:length(card.cardNumber)-1, fn:length(card.cardNumber))}.png" alt="Card Image" style="width: 100%">
                        </div>
                    </div>
                </div>
            `;
                    });

                    if (!cardExists) {
                        content += `
                <div class="col-md-6 col-xl-4">
                    <div class="card shadow-none bg-transparent border border-secondary mb-3">
                        <div class="card-body">
                            <h5 class="card-title">카드 연결</h5>
                            <div style="width: 100%; text-align: center">
                                <img src="${pageContext.request.contextPath}/assets/img/icons/squre_plus.png" alt="Card Image" style="width: 60%;" onclick="location.href='${pageContext.request.contextPath}/group-wallet/${response.id}/card_2'" id="cardChange">
                            </div>
                        </div>
                    </div>
                </div>
            `;
                    }

                    $('#tab5').html(content); // 대상 div의 ID를 변경해야 합니다.
                },
                error: function (err) {
                    console.error("Error fetching data", err);
                }
            });
        }

        let deleteWallet = (event) => {

            let countMember = ${countMember};
            let balanceKRW = ${walletDetailDto.balance.get("KRW")};
            let balanceJPY = ${walletDetailDto.balance.get("JPY")};
            let balanceUSD = ${walletDetailDto.balance.get("USD")};
            let savingAmount = 0
            // let savingAmount = ${installmentDto.savingAmount};

            console.log(savingAmount)

            if (countMember > 1) {
                event.preventDefault();
                alert("모임원이 한 명 이상 남아있을 경우 모임지갑을 삭제할 수 없습니다.");
            } else if (balanceKRW > 0) {
                alert(`모임지갑에 돈이 남아있을 경우 모임지갑을 삭제할 수 없습니다. - KRW ${balanceKRW}`);
            } else if (balanceJPY > 0) {
                alert(`모임지갑에 돈이 남아있을 경우 모임지갑을 삭제할 수 없습니다. - JPY ${balanceJPY}`);
            } else if (balanceUSD > 0) {
                alert(`모임지갑에 돈이 남아있을 경우 모임지갑을 삭제할 수 없습니다. - USD ${balanceUSD}`);
            } else if (savingAmount > 0) {
                alert("가입한 적금이 있는 경우 모임지갑을 삭제할 수 없습니다.");
            } else {
                // 삭제
                let confirmation = confirm("모임 지갑을 정말 삭제하시겠습니까? 😥");

                if (confirmation) {
                    let groupWalletId = "${groupWallet.groupWalletId}"; // 그룹 월렛 아이디 변수로 설정

                    $.ajax({
                        type: "delete",
                        url: `${pageContext.request.contextPath}/group-wallet/${groupWalletId}`,
                        success: function (data) {
                            console.log(data)
                            alert("모임지갑 삭제 완료")
                            location.href = "${pageContext.request.contextPath}/group-wallet/"; // 페이지 새로고침
                        },
                        error: function () {
                            alert("모임지갑 삭제 실패")
                        }
                    });

                }

            }


        }

    </script>

</head>
<body>
<jsp:include page="/WEB-INF/views/common/navbar.jsp"/>

<div class="pageWrap">
    <div class="center">
        <div class="row">
            <h2>${member.name}님은 ${groupWallet.nickname}의 ${groupMemberDto.roleToString}이에요!</h2>
        </div>

        <div class="row">
            <!--지갑 통화 현황 차트-->
            <jsp:include page="/WEB-INF/views/common/walletChart.jsp"/>

            <!-- 차트->멤버 목록 변경 완료
                 수정자: 김진형 -->
            <div class="col-md-6 col-lg-6 col-xl-6 mb-4 h-100">
                <h6 class="text-muted">${groupWallet.nickname}의 지갑 정보</h6>
                <div class="card h-20">
                    <jsp:include page="groupWalletMemberAndCard.jsp"/>
                </div>
            </div>
            <!-- 차트->멤버 목록 변경 완료
                 수정자: 김진형 -->
        </div>


        <div class="col-xl-12">
            <div class="nav-align-top d-flex mb-8">
                <!--탭 리스트-->
                <ul class="nav nav-tabs flex-fill" role="tablist">
                    <li class="nav-item">
                        <button
                                type="button"
                                class="nav-link active"
                                role="tab"
                                data-bs-toggle="tab"
                                data-bs-target="#navs-top-home"
                                aria-controls="navs-top-home"
                                aria-selected="true"
                        >모임 거래 내역
                        </button>
                    </li>
                    <li class="nav-item">
                        <button
                                type="button"
                                class="nav-link"
                                role="tab"
                                data-bs-toggle="tab"
                                data-bs-target="#navs-top-rule"
                                aria-controls="navs-top-rule"
                                aria-selected="false"
                        >
                            모임 회비 규칙
                        </button>
                    </li>
                    <li class="nav-item">
                        <button
                                type="button"
                                class="nav-link"
                                role="tab"
                                data-bs-toggle="tab"
                                data-bs-target="#navs-top-save"
                                aria-controls="navs-top-save"
                                aria-selected="false"
                        >
                            모임 적금 조회
                        </button>
                    </li>
                    <li class="nav-item">
                        <button
                                type="button"
                                class="nav-link"
                                role="tab"
                                data-bs-toggle="tab"
                                data-bs-target="#navs-top-card"
                                aria-controls="navs-top-card"
                                aria-selected="false"
                        >
                            모임 연결 카드
                        </button>
                    </li>
                    <li class="nav-item" id="hiddenNavItem" style="display: none;">
                        <button
                                type="button"
                                class="nav-link"
                                role="tab"
                                data-bs-toggle="tab"
                                data-bs-target="#navs-top-member"
                                aria-controls="navs-top-member"
                                aria-selected="false"
                        >
                            모임 멤버 관리
                        </button>
                    </li>
                </ul>


                <div class="tab-content" style="padding: 0px">

                    <!--모임 거래내역 START-->
                    <jsp:include page="tab/groupTabTranserHistory.jsp"/>
                    <!--모임 거래내역 END-->

                    <!-- 회비 규칙 START -->
                    <jsp:include page="tab/groupTabDueRule.jsp"/>
                    <!-- 회비 규칙 END -->

                    <!-- 모임적금 조회 START -->
                    <jsp:include page="tab/groupTabSaving.jsp"/>
                    <!-- 모임적금 조회 END -->

                    <!-- 모임 연결 카드 START -->
                    <jsp:include page="tab/groupTabCard.jsp"/>
                    <!-- 모임 연결 카드 END -->

                    <!--모임 멤버조회 START-->
                    <jsp:include page="tab/groupTabMemberList.jsp"/>
                    <!--모임 멤버조회 END-->

                </div>
            </div>
        </div>

        <br>
        <br>
        <br>
        <br>

        <div class="col-xl-12">
            <c:choose>
                <c:when test="${isChairman == true}">
                    <button id="deleteButton"
                            class="btn btn-primary" onclick="deleteWallet(event)">모임 지갑 삭제
                    </button>
                    <a href="${pageContext.request.contextPath}/group-wallet/${id}/invite-form" id="inviteButton"
                       class="btn btn-primary">모임 지갑에 초대하기</a>
                </c:when>
                <c:otherwise>
                    <a href="javascript:void(0);" id="groupLeave" class="btn btn-primary"
                       onclick="confirmLeave(${id});">
                        모임 지갑 떠나기</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<!-- Modal -->

<div class="modal fade" id="basicModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel1">조회기간 설정</h5>
                <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                ></button>
            </div>
            <form action="/personalwallet/selectDate" method="post" id="selectDateForm"
                  name="selectDateForm">
                <div class="modal-body">
                    <div class="row g-2">
                        <div class="col mb-0">
                            <label for="startDate" class="form-label">시작일</label>
                            <input type="date" id="startDate" class="form-control"
                                   name="startDate"
                                   placeholder="DD / MM / YY"/>
                        </div>
                        <div class="col mb-0">
                            <label for="endDate" class="form-label">종료일</label>
                            <input type="date" id="endDate" class="form-control"
                                   name="endDate"
                                   placeholder="DD / MM / YY"/>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary"
                            data-bs-dismiss="modal">
                        취소
                    </button>
                    <button type="submit" class="btn btn-primary" id="submitButton">조회</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="col mb-0">
    <div class="col mb-0 col-lg-5 col-md-auto">
        <!-- Modal -->
        <div class="modal fade show" id="detailModal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel11">거래상세내역</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div>
                        <p>거래 날짜</p>
                        <p class="col mb-0" style="height: 50px" id="detail-date"
                           readonly>
                    </div>
                    <hr>
                    <div>
                        <p>거래 시간</p>
                        <p class="col mb-0" style="height: 50px" id="detail-time"
                           readonly>
                    </div>
                    <hr>
                    <div>
                        <p>금액</p>
                        <p class="col mb-0" style="height: 50px" id="detail-amount"
                           readonly>
                    </div>
                    <hr>
                    <div>
                        <p>상세 내용</p>
                        <p class="col mb-0" style="height: 50px" id="detail-content"
                           readonly>
                    </div>
                    <hr>
                    <div>
                        <p>거래후 잔액</p>
                        <p class="col mb-0" style="height: 50px" id="detail-balance"
                           readonly>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<!-- 회비 납부 가능 Modal -->
<div class="modal fade" id="payModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="changeWalletLabel">회비 납부</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="changeWalletBody">
                회비: ${groupWallet.due}
                <br>
                잔액: ${personalWalletBalance}
            </div>
            <div class="modal-footer">
                <input type="hidden" name="connect-memberId">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                <button type="button" class="btn btn-primary" id="pay-button" data-bs-dismiss="modal">납부</button>
            </div>
        </div>
    </div>
</div>

<!-- 회비 납부 불가능 Modal -->
<div class="modal fade" id="cantPayModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">회비 납부</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                회비: ${groupWallet.due}
                <br>
                잔액: ${personalWalletBalance}
                <hr>
                잔액부족입니다
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">확인</button>
            </div>
        </div>
    </div>
</div>

<!-- 회비 규칙 생성 Modal -->
<jsp:include page="modal/groupModalDueRule.jsp"/>

<br>
<br>
<br>
<br>
<br>
<br>
</body>
</html>