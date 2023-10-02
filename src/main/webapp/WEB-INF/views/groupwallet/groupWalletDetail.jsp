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
    <style>
        @font-face {
            font-family: 'NanumSquareNeo-Variable';
            src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_11-01@1.0/NanumSquareNeo-Variable.woff2') format('woff2');
            font-weight: normal;
            font-style: normal;
        }
    </style>
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
    <script src="../../../assets/js/common.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script type="text/javascript">
        // 모임장이면 모임원 관리
        function displayMemberList() {
            let isChairman =
            ${isChairman}

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
                        let dateTime = new Date(result[i].dateTime);
                        let detailString = typeof result[i].detail === 'object' ? JSON.stringify(result[i].detail) : result[i].detail;
                        // 날짜와 시간을 따로 추출
                        let date = dateTime.toLocaleDateString(); // 날짜 형식으로 변환
                        let time = dateTime.toLocaleTimeString(); // 시간 형식으로 변환

                        console.log(detailString);
                        str += '<TR class="searchDateResult" data-id="' + detailString + '">';
                        // 날짜 시간 처리
                        str += '<TD><h5 id="date" class="text-center" style="margin-bottom: 0">' + date + '</h5></TD>';
                        str += '<TD><h5 id="date" class="text-center" style="margin-bottom: 0">' + time + '</h5></TD>';
                        // 입금액 출금액 처리
                        if (result[i].type === '입금') {
                            str += '<TD><h5 id="depositAmount" class="text-center" style="margin-bottom: 0">' + formatNumberWithCommas(result[i].amount) + ' </h5></TD><TD><h5 class="text-center" style="margin-bottom: 0">-</h5></TD>';
                        } else if(result[i].type === '적금 입금') {
                            str += '<TD><h5 id="depositAmount" class="text-center" style="margin-bottom: 0">' + formatNumberWithCommas(result[i].amount) + ' KRW' +'</h5></TD><TD><h5 class="text-center" style="margin-bottom: 0">-</h5></TD>';
                        } else if (result[i].type === '적금 출금') {
                            str += '<TD><h5 id="withdrawAmount" class="text-center" style="margin-bottom: 0">-</h5></TD>' + '<TD><h5 class="text-center" style="margin-bottom: 0">' + formatNumberWithCommas(result[i].amount) + ' KRW'+ '</h5></TD>';
                        }
                        else {
                            str += '<TD><h5 id="withdrawAmount" class="text-center" style="margin-bottom: 0">-</h5></TD>' + '<TD><h5 class="text-center" style="margin-bottom: 0">' + formatNumberWithCommas(result[i].amount) + ' </h5></TD>';
                        }
                        str += '<TD><h5 id="type" class="text-center" style="margin-bottom: 0">' + result[i].type + '</TD>';
                        if (result[i].type === '환전' || result[i].type === '재환전') {
                            str += '<TD><h5 id="afterBalance" class="text-center" style="margin-bottom: 0">' + formatNumberWithCommas(result[i].balance) +  ' </TD>';
                        } else if (result[i].type == "적금 입금" || result[i].type == "적금 출금") {
                            str += '<TD><h5 id="afterBalance" class="text-center" style="margin-bottom: 0">' + formatNumberWithCommas(result[i].balance) + ' KRW' + '</TD>';
                        }
                        else {
                            str += '<TD><h5 id="afterBalance" class="text-center" style="margin-bottom: 0">' + formatNumberWithCommas(result[i].balance) + ' </TD>';
                        }
                        str += '</TR>';
                    });
                    $("#dateSelectHistory").append(str);
                },
                error: function (result, status) {

                },
            })
        }

        // ajax 로 적금 표시 + 포맷 형식 지정
        function savingCall() {
            $.ajax({
                url: "${pageContext.request.contextPath}/group-wallet/${id}/saving-check", // 컨트롤러에서 데이터를 반환하는 엔드포인트를 지정하세요.
                type: "get", // HTTP GET 요청을 사용합니다.
                dataType: "json",
                success: function (data) {
                    var insertDate = new Date(data.insertDate);
                    var maturityDate = new Date(data.maturityDate); // 날짜를 원하는 형식으로 포맷팅
                    var insertDateFormatted = insertDate.toLocaleDateString(); // 날짜 형식으로 변환
                    var maturityDateFormatted = maturityDate.toLocaleDateString(); // 날짜 형식으로 변환

                    // 데이터를 가져와서 화면에 표시합니다.
                    $("#interestRate").text(data.interestRate + "%");
                    $("#period").text(data.period + "개월");
                    $("#insertDate").text(insertDateFormatted);
                    $("#maturityDate").text(maturityDateFormatted);
                    $("#totalAmount").text(formatNumberWithCommas(data.totalAmount) + "원");
                    $("#savingDate").text("매월 " + data.savingDate + "일");
                    $("#savingAmount").text(formatNumberWithCommas(data.savingAmount) + "원");
                },
                error: function (xhr, status, error) {
                    console.error("데이터를 가져오는 중 오류 발생: " + error);
                }
            });
        }

        // AJAX READY

        $(document).ready(function () {
            $(document).on("click", ".searchDateResult", function () {
                $('#detailModal').modal('show');

                var id = $(this).closest("tr").data("id");
                var row = $(this).closest("tr");

                $("#detail-date").text(row.find("td:eq(0)").text());
                $("#detail-time").text(row.find("td:eq(1)").text());

                var deposit = row.find("td:eq(2)").text();
                var withdrawal = row.find("td:eq(3)").text();

                if (deposit === "-") {
                    $("#detail-amount").text(withdrawal);
                } else {
                    $("#detail-amount").text(deposit);
                }
                $("#detail-type").text(row.find("td:eq(4)").text());
                $("#detail-content").text(id);
                console.log(id);
                $("#detail-balance").text(row.find("td:eq(5)").text());

            });

            memberCall();
            historyCall();
            displayMemberList();
            savingCall();
            initTest("${pageContext.request.contextPath}/group-wallet/load-card-data");

            // $(document).on("click", , function(){ }) 형식을 쓰는 이유
            // = 동적 요소에 대한 이벤트 처리: 이 방식을 사용하면 페이지가 로드된 이후에
            // 동적으로 생성되는 요소에 대해서도 이벤트 처리를 할 수 있다
            // 모임지갑에서 강퇴 버튼 클릭
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
                                initTest("${pageContext.request.contextPath}/group-wallet/load-card-data");
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


            <%--document.getElementById("deleteButton").addEventListener("click", function (event) {--%>
            <%--    if (${countMember}>--%>
            <%--    1--%>
            <%--)--%>
            //     {
            //         event.preventDefault();
            //         alert("모임원이 없을 때 모임 지갑을 삭제할 수 있습니다.");
            //     }
            // });

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
                                alert(memberName + "님이 공동모임장에서 모임원이 되었어요!")
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

            // 꺼내기 할 때 모임원은 안되게 권한 판단하기
            // 서버에서 권한 정보를 JavaScript 변수로 전달합니다.
            var userRole = "${groupMemberDto.roleToString}";

            // 버튼 클릭 이벤트 핸들러
            $("#withdrawButton").click(function (event) {
                // 권한이 ADMIN인 경우에만 꺼내기 동작
                if (userRole === '모임장' || userRole === '공동모임장') {
                    // 꺼내기 동작 구현
                } else {
                    // 권한이 없는 경우 alert 메시지 표시
                    alert("꺼내기는 모임장이나 공동모임장이 할 수 있어요!");
                    // 이벤트 기본 동작 취소
                    event.preventDefault();
                }
            });
        });

        // 모임지갑 연결 카드 부르기
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

        // 모임지갑 삭제
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

        // 카드 연결상태 확인
        function initTest(urlPath, data) {
            $.ajax({
                url: urlPath,
                type: "get",
                dataType: "json",
                data: "id=" +${groupWalletId},
                success: function (result, status) {
                    $("#table").empty();
                    // 화면에 갱신
                    var str = "";
                    $.each(result, function (i) {
                        str += '<tr data-id=' + result[i].memberId + '>';
                        str += '<TD><i class="fab fa-angular fa-lg text-danger me-3"></i><h5 class="text-break text-center" style="margin-bottom: 0">' + result[i].name + '</h5></TD>';
                        if (result[i].roleToString == '모임장' || result[i].roleToString == '공동모임장') {
                            str += '<TD><i class="fab fa-angular fa-lg text-danger me-3"></i><h5 class="text-break text-center" style="margin-bottom: 0">' + result[i].roleToString + '👑' + '</h5></TD>';
                        } else {
                            str += '<TD><i class="fab fa-angular fa-lg text-danger me-3"></i><h5 class="text-break text-center" style="margin-bottom: 0">' + result[i].roleToString + '</TD>';
                        }
                        if (result[i].cardIsConnect) {
                            str += '<td class="open-modal text-center"><h5 class="text-break" style="margin-bottom: 0">연결 중&nbsp&nbsp&nbsp&nbsp<i class="material-icons" style="color: green">credit_card</i></h5></td>';
                        } else {
                            if (result[i].role == "GENERAL") {
                                str += '<td id="cant-connect-card" class="open-modal text-center"><h5 class="text-break text-center" style="margin-bottom: 0"> 연결 불가 <i class="material-icons" style="color:red;">credit_card</i></h5></td>';
                            } else {
                                str += `<td id="can-connect-card" class="open-modal text-center"><h5 class="text-break text-center" style="margin-bottom: 0"> 연결 가능 <i class="material-icons">credit_card</i></h5></td>`
                            }
                        }
                        str += '</TR>';
                    });
                    $("#table").append(str);
                },
                error: function (result, status) {
                },
            });
        }

    </script>

</head>
<body style="font-family: NanumSquareNeo-Variable,serif">
<jsp:include page="/WEB-INF/views/common/navbar.jsp"/>

<div class="pageWrap">
    <div class="center">
        <div class="row">
            <i class="fab fa-angular fa-lg text-danger me-3"></i>
            <h1 class="text-center text-break" style="margin-bottom: 0">
                ${member.name}님은 ${groupWallet.nickname}의 ${groupMemberDto.roleToString}이에요!
            </h1>
        </div>

        <br>

        <div class="row">
            <div class="col-md-6 col-lg-6 col-xl-6 mb-4 h-100">
                <i class="fab fa-angular fa-lg text-danger me-3"></i>
                <h6 class="text-muted">
                    지갑 정보
                </h6>
                <!--지갑 통화 현황 차트-->
                <jsp:include page="/WEB-INF/views/common/walletChart.jsp"/>

            </div>

            <!-- 차트->멤버 목록 변경 완료
                 수정자: 김진형 -->
            <div class="col-md-6 col-lg-6 col-xl-6 mb-4 h-100" align="center">
                <i class="fab fa-angular fa-lg text-danger me-3"></i>
                <h6 class="text-muted">
                    ${groupWallet.nickname}의 카드 현황
                </h6>
                <div class="card h-20" style="margin-bottom: 10px">
                    <i class="fab fa-angular fa-lg text-danger me-3"></i>
                    <jsp:include page="groupWalletMemberAndCard.jsp"/>
                </div>

                <div class="card">
                    <img src="../images/깨비비행기.jpg" height="240" style="border-radius: 0px">
                </div>

            </div>
            <!-- 차트->멤버 목록 변경 완료
                 수정자: 김진형 -->



        </div>


        <div class="col-xl-12" style="padding: 0px">
            <div class="" style="padding: 0px;">
                <!--탭 리스트-->
                <ul class="nav nav-tabs flex-fill" role="tablist" style="padding: 0px">
                    <li class="nav-item" style="padding: 0px">
                        <button
                                type="button"
                                class="nav-link active"
                                role="tab"
                                data-bs-toggle="tab"
                                data-bs-target="#navs-top-home"
                                aria-controls="navs-top-home"
                                aria-selected="true"
                        >
                            <i class="fab fa-angular fa-lg text-danger me-3"></i>
                            <h4 class="text-break" style="margin: 0px; padding: 0px">
                                모임 거래 내역
                            </h4>
                        </button>
                        <!-- Button trigger modal -->
                        <%--                        <button--%>
                        <%--                                type="button"--%>
                        <%--                                class="btn btn-primary"--%>
                        <%--                                data-bs-toggle="modal"--%>
                        <%--                                data-bs-target="#basicModal"--%>
                        <%--                        >--%>
                        <%--                            조회 기간 설정--%>
                        <%--                        </button>--%>
                    </li>
                    <li class="nav-item" style="padding: 0px">
                        <button
                                type="button"
                                class="nav-link"
                                role="tab"
                                data-bs-toggle="tab"
                                data-bs-target="#navs-top-rule"
                                aria-controls="navs-top-rule"
                                aria-selected="false"
                        >
                            <i class="fab fa-angular fa-lg text-danger me-3"></i>
                            <h4 class="text-break" style="margin: 0px; padding: 0px">
                                모임 회비 규칙
                            </h4>
                        </button>
                    </li>
                    <li class="nav-item" id="">
                        <button
                                type="button"
                                class="nav-link"
                                role="tab"
                                data-bs-toggle="tab"
                                data-bs-target="#navs-top-save"
                                aria-controls="navs-top-save"
                                aria-selected="false"
                        >
                            <i class="fab fa-angular fa-lg text-danger me-3"></i>
                            <h4 class="text-break" style="margin: 0px; padding: 0px">
                                모임 적금 조회
                            </h4>
                        </button>
                    </li>
                    <%-- 모임 카드 탭 삭제 --%>
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
                            <i class="fab fa-angular fa-lg text-danger me-3"></i>
                            <h4 class="text-break" style="margin: 0px; padding: 0px">
                                모임 멤버 관리
                            </h4>
                        </button>
                    </li>
                </ul>


                <div class="tab-content flex-fill" class="card" style="margin-bottom: 0; padding: 0px">

                    <!--모임 거래내역 START-->
                    <jsp:include page="tab/groupTabTranserHistory.jsp"/>
                    <!--모임 거래내역 END-->

                    <!-- 회비 규칙 START -->
                    <jsp:include page="tab/groupTabDueRule.jsp"/>
                    <!-- 회비 규칙 END -->

                    <!-- 모임적금 조회 START -->
                    <jsp:include page="tab/groupTabSaving.jsp"/>
                    <!-- 모임적금 조회 END -->

                    <!-- 모임 카드 탭 삭제 -->

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
<div class="modal fade" id="detailModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h2 class="modal-title" id="exampleModalLabel11">거래상세내역</h2>
            </div>
            <div class="modal-body" style="margin: 20px">
                <div class="row">
                    <div class="row g-2" style="margin-bottom: 20px">
                        <div class="col mb-3">
                            <h3 style="margin-bottom: 0">거래 날짜</h3>
                            <br>
                            <h4 id="detail-date"></h4>
                        </div>

                        <div class="col mb-3">
                            <h3 style="margin-bottom: 0">거래 시간</h3>
                            <br>
                            <h4 id="detail-time"></h4>
                        </div>
                    </div>

                    <div class="row g-2" style="margin-bottom: 20px">
                        <div class="col mb-3">
                            <h3 style="margin-bottom: 0">거래종류</h3>
                            <br>
                            <h4 id="detail-type"></h4>
                        </div>
                    </div>

                    <div class="row g-2" style="margin-bottom: 20px">
                        <div class="col mb-3">
                            <h3 style="margin-bottom: 0">상세내용</h3>
                            <br>
                            <h4 id="detail-content"></h4>
                        </div>
                    </div>


                    <div class="row g-2" style="margin-bottom: 20px">
                        <div class="col mb-0">
                            <h3 style="margin-bottom: 0">금액</h3>
                            <br>
                            <div class="col mb-3">
                                <h4 id="detail-amount"></h4>
                            </div>
                        </div>
                        <div class="col mb-0">
                            <h3 style="margin-bottom: 0">거래후 잔액</h3>
                            <br>
                            <div class="col mb-3">
                                <h4 id="detail-balance"></h4>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-bs-dismiss="modal">
                    확인
                </button>
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