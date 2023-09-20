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

        // AJAX READY
        $(document).ready(function () {

            // 모임지갑 상세내역
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


            // 모임지갑 모임원 리스트 조회
            function memberCall() {
                let myMemberId = ${loginMemberDto.memberId};
                let isChairman = ${isChairman};

                // 이후 JavaScript 코드에서 myMemberId 변수를 사용할 수 있음

                $.ajax({
                    url: "${pageContext.request.contextPath}/group-wallet/${id}/member-list",
                    type: "post",
                    dataType: "json",
                    success: function (result, status) {
                        // 화면에 갱신
                        var str = "";
                        $.each(result, function (i) {
                            str += '<tr id="searchMemberResult">'
                            str += '<td>' + result[i].name + '</td>';
                            str += '<td>' + result[i].roleToString + '</td>';

                            // 내가 모임장인 경우 && 나와 다른 memberId인 경우에만 버튼 생성
                            if (isChairman && (result[i].memberId !== myMemberId)) {
                                str += '<td><button class="alert-warning" data-member-id="' + result[i].memberId + '" data-member-name="' + result[i].name + '">강퇴</button>' +
                                    '<button class="alert-primary" data-member-id="' + result[i].memberId + '" data-member-name="' + result[i].name + '">권한 부여</button>' +
                                    '<button class="alert-secondary" data-member-id="' + result[i].memberId + '" data-member-name="' + result[i].name + '">권한 철회</button></td>';
                            } else {
                                str += '<td></td>'; // 자신의 memberId와 일치하면 빈 칸 생성
                            }

                            str += '</tr>';
                        });
                        $("#getMemberList").empty();
                        $("#getMemberList").append(str);

                        // 강퇴 버튼 클릭 이벤트 핸들러
                        //    모임장 권한 아직
                    },
                    error: function (result, status) {
                        // 오류 처리
                    },
                });
            }

            memberCall();

            // 모임지갑에서 강퇴 버튼 클릭

            // $(document).on("click", , function(){ }) 형식을 쓰는 이유
            // = 동적 요소에 대한 이벤트 처리: 이 방식을 사용하면 페이지가 로드된 이후에
            // 동적으로 생성되는 요소에 대해서도 이벤트 처리를 할 수 있다
            $(document).on("click", '.alert-warning', function () {
                let memberId = $(this).data("member-id");
                let memberName = $(this).data("member-name")

                var confirmation = confirm(memberName + "님을 강퇴하시겠습니까?");

                if (confirmation) {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/group-wallet/${id}/out",
                        type: "post",
                        data: {memberId: memberId},
                        success: function (result, response) {
                            console.log(result);
                            if (result > 0) {
                                // 강퇴 성공 시 필요한 작업 수행
                                alert(memberName + "님을 강퇴했어요")
                                memberCall();
                            } else {
                                alert("강퇴를 실패했어요");
                            }
                        },
                        error: function () {
                            // 강퇴 실패 시 필요한 작업 수행
                        }
                    });
                } else {
                    alert("강퇴를 취소했습니다.");
                }

            });

            // 모임지갑 권한 부여 버튼 클릭
            $(document).on("click", '.alert-primary', function () {
                let memberId = $(this).data("member-id");
                let memberName = $(this).data("member-name")

                var confirmation = confirm(memberName + memberId + "님에게 공동모임장 권한을 부여하시겠습니까?");

                if (confirmation) {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/group-wallet/${id}/grant",
                        type: "post",
                        data: {memberId: memberId},
                        success: function (data, result, response) {
                            console.log(result);
                            console.log(data);
                            if (data > 0) {
                                // 강퇴 성공 시 필요한 작업 수행
                                alert(memberName + "님이 공동모임장이 되었어요!")
                                memberCall();
                            } else {
                                alert("권한 부여를 실패했어요");
                            }
                        },
                        error: function () {
                            // 강퇴 실패 시 필요한 작업 수행
                        }
                    });
                } else {
                    alert("권한 부여를 취소했습니다.");
                }

            });

            // 모임지갑 권한 철회 버튼 클릭
            $(document).on("click", '.alert-secondary', function () {
                let memberId = $(this).data("member-id");
                let memberName = $(this).data("member-name")

                var confirmation = confirm(memberName + "님의 공동모임장 권한을 철회하시겠습니까?");

                if (confirmation) {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/group-wallet/${id}/revoke",
                        type: "post",
                        data: {memberId: memberId},
                        success: function (data, result, response) {
                            console.log(result);
                            console.log(data);
                            if (data > 0) {
                                // 강퇴 성공 시 필요한 작업 수행
                                alert(memberName + "님이 모임원이 되었어요!")
                                memberCall();
                            } else {
                                alert("권한 철회를 실패했어요");
                            }
                        },
                        error: function () {
                            // 강퇴 실패 시 필요한 작업 수행
                        }
                    });
                } else {
                    alert("권한 철회를 취소했습니다.");
                }

            });


            // 조회기간 설정 조회 버튼 누를 시 비동기화 통싱
            $("#selectDateForm").on("submit", function (e) {
                e.preventDefault()
                var formValues = $("form[name=selectDateForm]").serialize();
                $.ajax({
                    url: "/personalwallet/selectDate",
                    type: "post",
                    dataType: "json",
                    data: formValues,
                    success: function (result, status) {
                        $("#dateSelectHistory").empty();
                        // 화면에 갱신
                        var str = "";
                        $.each(result, function (i) {
                            str += '<TR id="searchDateResult" onclick="PopupDetail(this)" data-bs-toggle="modal" data-bs-target="#detailModal">'
                            // 날짜 시간 처리
                            str += '<TD>' + result[i].dateTime + '</TD>';
                            str += '<TD>' + result[i].dateTime + '</TD>';
                            // 입금액 출금액 처리
                            if (result[i].type === '입금') {
                                str += '<TD> 입금액: ' + result[i].amount + ' ' + result[i].currencyCode + '</TD><TD> 출금액: -</TD>';
                            } else {
                                str += '<TD> 입금액: -</TD>' + '<TD> 출금액: ' + result[i].amount + ' ' + result[i].currencyCode + '</TD>';
                            }
                            str += '<TD>' + result[i].type + '</TD>';
                            str += '<TD>' + result[i].balance + ' ' + result[i].currencyCode + '</TD>';
                            str += '</TR>';
                        });
                        $("#dateSelectHistory").append(str);
                    },
                    error: function (result, status) {

                    },
                })
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

            function formatNumber(number) {
                return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            }
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
            let savingAmount = ${installmentDto.savingAmount};

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
            }else {
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

            <jsp:include page="/WEB-INF/views/common/walletChart.jsp"/>

            <!-- 차트->멤버 목록 변경 완료
                 수정자: 김진형 -->
            <div class="col-md-6 col-lg-6 col-xl-6 mb-4 h-100">
                <h4 class="text-muted">${groupWallet.nickname}의 지갑 정보</h4>
                <div class="card h-20">
                    <jsp:include page="groupWalletMemberAndCard.jsp"/>
                </div>
            </div>
            <!-- 차트->멤버 목록 변경 완료
                 수정자: 김진형 -->

        </div>


        <div class="col-xl-12">
            <h6 class="text-muted"></h6>
            <div class="nav-align-top d-flex mb-8">
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
                        >
                            모임 거래 내역
                        </button>
                    </li>
                    <li class="nav-item">
                        <button
                                type="button"
                                class="nav-link"
                                role="tab"
                                data-bs-toggle="tab"
                                data-bs-target="#navs-top-member"
                                aria-controls="navs-top-member"
                                aria-selected="false"
                        >
                            모임 멤버 조회
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
                </ul>
                <div class="tab-content" style="padding: 0px">
                    <div class="tab-pane fade show active" id="navs-top-home" role="tabpanel">

                        <div class="card">
                            <h5 class="card-header">
                                <div class="row g-2">
                                    <div class="col mb-0">
                                        거래 내역
                                    </div>
                                    <div class="col mb-0">
                                        <div class="col mb-0 col-lg-5 col-md-auto">
                                            <!-- Button trigger modal -->
                                            <button
                                                    type="button"
                                                    class="btn btn-primary"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#basicModal"
                                            >
                                                조회 기간 설정
                                            </button>


                                        </div>
                                    </div>
                                </div>
                            </h5>

                            <div class="table-responsive text-nowrap">
                                <table class="table table">
                                    <thead>
                                    <tr>
                                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i>거래일자</th>
                                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i>거래시간</th>
                                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i>입금()</th>
                                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i>출금()</th>
                                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i>내용</th>
                                        <th><i class="fab fa-angular fa-lg text-danger me-3"></i>잔액()</th>
                                    </tr>
                                    </thead>
                                    <tbody class="table-border-bottom-0" id="dateSelectHistory">

                                    </tbody>
                                </table>


                            </div>

                        </div>

                    </div>


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

                    <!-- 회비 규칙 START -->
                    <jsp:include page="tab/groupTabDueRule.jsp"/>
                    <!-- 회비 규칙 END -->

                    <div class="tab-pane fade" id="navs-top-save" role="tabpanel">
                        <div class="card" style="margin-top: 5px;">
                            <%--                                <div class="card-header">--%>

                            <%--                                </div>--%>
                            <%--                                <div class="card-body">--%>
                            <c:choose>
                                <c:when test="${installmentDto == null}">
                                    <c:choose>
                                        <c:when test="${isChairman}">
                                            <p><strong>적금을 가입하지 않으셨습니다.</strong></p>
                                            <a href="${pageContext.request.contextPath}/saving/"
                                               class="btn btn-primary">적금 보러가기</a>
                                        </c:when>
                                        <c:otherwise>
                                            <p><strong>가입된 적금이 없습니다. 모임장에게 적금 가입을 추천하는건 어떨까요?</strong></p>
                                            <!--적금 가입 추천 버튼 넣을까?-->
                                        </c:otherwise>
                                    </c:choose>

                                </c:when>
                                <c:otherwise>
                                    <div class="card">
                                        <h5 class="card-header">${installmentDto.savingName}</h5>
                                        <div class="table-responsive text-nowrap">
                                            <table class="table table">
                                                <thead>
                                                <tr class="text-nowrap">
                                                    <th>정보</th>
                                                    <th>내용</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr>
                                                    <th scope="row">금리</th>
                                                    <td>${installmentDto.interestRate}%</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">기간</th>
                                                    <td>${installmentDto.period}개월</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">가입일</th>
                                                    <td>${installmentDto.insertDate}</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">만기일</th>
                                                    <td>${installmentDto.maturityDate}</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">현재까지</th>
                                                    <td>${installmentDto.totalAmount}원</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">납입일</th>
                                                    <td>매월 ${installmentDto.savingDate}일</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">납입금</th>
                                                    <td> ${installmentDto.savingAmount}원</td>
                                                </tr>
                                                </tbody>
                                            </table>
                                            <div class="d-grid gap-2 col-lg-1 mx-auto">
                                                <c:choose>
                                                    <c:when test="${isChairman}">
                                                        <%--                                                                <a href="${pageContext.request.contextPath}/group-wallet/${groupWallet.groupWalletId}/saving" id="cancelSaving" class="btn btn-primary">적금 해지</a>--%>
                                                        <%----%>
                                                        <button type="submit" class="btn btn-primary" id="cancelSaving">
                                                            적금 해지
                                                        </button>
                                                        <script>
                                                            // 적금 해지 버튼 클릭 시 알림창 띄우기
                                                            document.getElementById("cancelSaving").addEventListener("click", function (event) {
                                                                event.preventDefault();

                                                                var confirmation = confirm("적금을 해지하시겠습니까? 해지시 이자도 함께 소멸됩니다.");

                                                                if (confirmation) {
                                                                    // 확인 버튼을 눌렀을 때, 적금 해지를 서버에 요청
                                                                    var groupWalletId = "${groupWallet.groupWalletId}"; // 그룹 월렛 아이디 변수로 설정
                                                                    var xhr = new XMLHttpRequest();
                                                                    xhr.open("DELETE", "${pageContext.request.contextPath}/group-wallet/" + groupWalletId + "/saving", true);
                                                                    xhr.onreadystatechange = function () {
                                                                        if (xhr.readyState === 4) {
                                                                            if (xhr.status === 200) {
                                                                                // 적금 해지가 성공적으로 처리되었을 때 알림 메시지 띄우기
                                                                                alert("적금이 해지되었습니다.");
                                                                                // 페이지 리로드 또는 다른 동작 수행
                                                                                window.location.reload(); // 페이지 리로드 예시
                                                                            } else {
                                                                                // 적금 해지가 실패했을 때 알림 메시지 띄우기
                                                                                var errorMessage = xhr.responseText;
                                                                                alert(errorMessage);
                                                                            }
                                                                        }
                                                                    };
                                                                    xhr.send();
                                                                }
                                                            });
                                                        </script>


                                                        <%----%>
                                                    </c:when>
                                                </c:choose>
                                            </div>
                                        </div>

                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <%--                                </div>--%>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="navs-top-card" role="tabpanel">

                        <div class="card">

                            <div class="card-body">

                                <div class="row" id="tab5">
                                    <c:set var="cardExists" value="false"/>

                                    <c:forEach var="card" items="${cardIssuanceDtoList}" varStatus="status">
                                        <c:if test="${card.member.memberId == sessionScope.member.memberId}">
                                            <c:set var="cardExists" value="true"/>
                                        </c:if>
                                        <div class="col-md-6 col-xl-4">
                                            <div class="card shadow-none bg-transparent border border-secondary mb-3">

                                                <div class="card-body">
                                                    <h5 class="card-title">${card.member.name}</h5>
                                                    <img src="${pageContext.request.contextPath}/assets/img/card/card${fn:substring(card.cardNumber, fn:length(card.cardNumber)-1, fn:length(card.cardNumber))}.png"
                                                         alt="Card Image" style="width: 100%">

                                                </div>


                                            </div>
                                        </div>

                                    </c:forEach>

                                    <c:if test="${not cardExists}">
                                        <div class="col-md-6 col-xl-4">
                                            <div class="card shadow-none bg-transparent border border-secondary mb-3">

                                                <div class="card-body">
                                                    <h5 class="card-title">카드 연결</h5>
                                                    <div style="width: 100%; text-align: center">
                                                        <img src="${pageContext.request.contextPath}/assets/img/icons/squre_plus.png"
                                                             alt="Card Image" style="width: 60%;"
                                                             onclick="location.href='${pageContext.request.contextPath}/group-wallet/${id}/card_2'"
                                                             id="cardChange">
                                                    </div>
                                                </div>


                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                            </div>

                        </div>


                    </div>
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
                       class="btn btn-primary" onclick="deleteWallet(event)">모임 지갑 삭제</button>
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
</div>
<!--/ Striped Rows -->


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



<p></p>
<br>
<br>
<br>
<br>
<br>
<br>

<footer>

</footer>
</body>
</html>