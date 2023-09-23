<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회비 규칙 탭</title>

    <style>
        .material-icons {
            vertical-align: middle
        }
    </style>

    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <script type="text/javascript">
        $(document).ready(function () {
            let pageContext = "${pageContext.request.contextPath}";
            let groupWalletDue = ${groupWallet.due};
            let personalWalletBalance = ${personalWalletBalance};

            $(document).on("click", "#payDueBtn", function () {
                if (groupWalletDue > personalWalletBalance) {
                    $("#cantPayModal").modal('show');
                } else {
                    $("#payModal").modal('show');
                }
            });

            $(document).on("click", "#pay-button", function () {
                $.ajax({
                    url: pageContext + "/group-wallet/${id}/rule/pay",
                    type: "PUT",
                    dataType: "text",
                    success: function (result, textStatus, jqXHR) {
                        dueRule()
                        console.log(result);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.log("회비 납부 내역 가져오기 실패");
                    },
                });
            });

            // 회비 규칙 삭제 버튼
            $(document).on("click", "#btnDeleteDueRule", function () {
                console.log("회비 규칙 삭제 비동기 통신");

                $.ajax({
                    url: pageContext + "/group-wallet/${id}/rule",
                    type: "DELETE",
                    success: function(data) {
                        // 서버에서 리다이렉트 완료 후 자동으로 새로운 페이지로 이동됩니다.
                        if (data != null) {
                            alert("회비 규칙이 삭제되었습니다!");
                        }
                        let redirectUrl = pageContext + '/group-wallet/' + data;
                        console.log(redirectUrl)
                        window.location.href = redirectUrl;
                    },
                    error: function(xhr, status, error) {
                        // 오류 처리 코드
                        console.error(error);
                        alert("회비 규칙 삭제에 실패했습니다.");
                    }
                });
            });

            // 함수 선언
            function dueRule() {
                console.log("회비 규칙 비동기 통신");

                $.ajax({
                    url: pageContext + "/group-wallet/${id}/rule",
                    type: "GET",
                    dataType: "json",
                    success: function (data, textStatus, jqXHR) {
                        console.log(data)

                        let str = "";

                        // 성공적으로 응답을 받았을 때 실행되는 함수
                        // data: 서버에서 받은 응답 데이터
                        if (data.dueCondition === true) { // dueCondition이 true일 때
                            str += '<h4 style="text-align: center">✈️ <strong>' + data.nickname + '</strong>의 회비 규칙 ✈️</h4>';
                            str += '<p style="text-align: center"><h4 style="text-align: center">' +
                                '매 월 <strong>' + data.dueDate + '</strong>일에 모임원들이 <strong>' + data.due + '</strong>원씩 회비를 납부해요!' +
                                '</h4></p>';
                            if (data.chairman === true) {
                                str += '<div class="text-end">';
                                str += '<h4 style="margin-bottom: 0">';
                                str += '<button type="button" id="btnDeleteDueRule" class="btn btn-outline-danger btn-sm" style="align-self: center">';
                                str += '회비 규칙 삭제';
                                str += '</button>';
                                str += '</h4>';
                                str += '</div>'
                            }

                            // 이번달 회비 납부 현황
                            dueMemberList();
                        } else { // dueCondition이 false일 때
                            str += '<p>';
                            str += '<h4 style="text-align: center">회비 규칙이 없습니다.&nbsp;';
                            if (data.chairman === true) {
                                str += '회비를 생성해 볼까요?&nbsp;';
                                str += '</p>';
                                str += '<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#dueModal">';
                                str += '회비 규칙 생성';
                                str += '</button>';
                            }
                            str += '</h4>'
                        }
                        $("#resultTabDueRule").empty();
                        $("#resultTabDueRule").append(str);

                        // textStatus: HTTP 상태 메시지 (예: "success", "notmodified", "error", "timeout", "abort", "parsererror" 등)
                        // jqXHR: jQuery XMLHttpRequest 객체
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        // 요청 중에 오류가 발생했을 때 실행되는 함수
                        // jqXHR: jQuery XMLHttpRequest 객체
                        // textStatus: HTTP 상태 메시지 (예: "error", "timeout", "abort", "parsererror" 등)
                        // errorThrown: 예외 객체 (실제 오류 정보)
                    }
                });
            }

            function dueMemberList() {
                console.log("회비 납부 내역 비동기 통신");

                $.ajax({
                    url: pageContext + "/group-wallet/${id}/rule/list",
                    type: "GET",
                    dataType: "json",
                    success: function (result, textStatus, jqXHR) {
                        console.log(result.size);
                        let str = "";

                        $.each(result, function (i) {
                            str += '<tr data-id=' + result[i].memberId + '>';
                            str += '<td class="text-center"><i class="fab fa-angular fa-lg text-danger me-3"></i><h5 class="text-break text-center" style="margin-bottom: 0">' + result[i].name + '</h5></TD>';
                            if (result[i].memberId ==${sessionScope.get("member").memberId}) {
                                if (result[i].payed) {
                                    str += '<td class="text-center"><i class="material-icons" style="color:green;"><h5 class="text-break text-center" style="margin-bottom: 0">check_circle</i></h5></td>'
                                } else {
                                    str += '<td class="text-center"><i class="material-icons" style="color:red;">cancel</i> <button id="payDueBtn" class="btn btn-primary">납부하기</button></td>'
                                }
                            } else {
                                if (result[i].payed) {
                                    str += '<td class="text-center"><i class="material-icons" style="color:green;">check_circle</i></td>'
                                } else {
                                    str += '<td class="text-center"><i class="material-icons" style="color:red;">cancel</i></td>'
                                }
                            }


                            str += '<TD class="text-center"><i class="fab fa-angular fa-lg text-danger me-3"></i><h5 class="text-break text-center" style="margin-bottom: 0">'
                                + formatNumberWithCommas(result[i].due)
                                + '</h5></TD>';
                            str += '<TD class="text-center"><i class="fab fa-angular fa-lg text-danger me-3"></i><h5 class="text-break text-center" style="margin-bottom: 0">'
                                + formatNumberWithCommas(result[i].dueAccumulation)
                                + '</h5></TD>';

                            str += '</TR>';
                        });

                        $("#resultTabDueHeader").show();
                        $("#resultTabDueMember").empty();
                        $("#resultTabDueMember").append(str);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.log("회비 납부 내역 가져오기");

                    },
                });
            }

            // 함수 사용
            dueRule();
        });


    </script>
</head>
<body>

<!-- 회비 규칙 START -->
<div class="tab-pane fade" id="navs-top-rule" role="tabpanel">
    <div class="card" style="margin-top: 0px; padding-top: 0px">
        <div class="card-body" id="resultTabDueRule">
            <h4>회비 규칙</h4>
        </div>
        <div class="table-responsive">
            <table class="table">
                <thead id="resultTabDueHeader" style="display: none">
                <tr>
                    <th class="text-center"><i class="fab fa-angular fa-lg text-danger me-3"></i><h5
                            class="text-break text-center" style="margin-bottom: 0">이름</h5></th>
                    <th class="text-center"><i class="fab fa-angular fa-lg text-danger me-3"></i><h5
                            class="text-break text-center" style="margin-bottom: 0">납부 상태</h5></th>
                    <th class="text-center"><i class="fab fa-angular fa-lg text-danger me-3"></i><h5
                            class="text-break text-center" style="margin-bottom: 0">회비(원)</h5></th>
                    <th class="text-center"><i class="fab fa-angular fa-lg text-danger me-3"></i><h5
                            class="text-break text-center" style="margin-bottom: 0">누적 회비(원)</h5></th>
                </tr>
                </thead>
                <tbody id="resultTabDueMember">
                </tbody>
            </table>
        </div>
    </div>
</div>
<!-- 회비 규칙 END -->
</body>
</html>
