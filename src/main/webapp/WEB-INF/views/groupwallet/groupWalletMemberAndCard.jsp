<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: 김진형
  Date: 2023-09-19
  Time: 오후 7:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
        <script type="text/javascript">

            /*function initTest(urlPath, data) {
                $.ajax({
                    url: urlPath,
                    type: "get",
                    dataType: "json",
                    data: "id="+${groupWalletId},

                    success: function (result, status) {
                        $("#table").empty();
                        // 화면에 갱신
                        var str = "";
                        $.each(result, function (i) {
                            str += '<tr data-id=' + result[i].memberId + '>';
                            str += '<TD><i class="fab fa-angular fa-lg text-danger me-3"></i><h5 class="text-break text-center" style="margin-bottom: 0">' + result[i].name + '</h5></TD>';
                            if (result[i].roleToString == '모임장' || result[i].roleToString == '공동모임장') {
                                str += '<TD><i class="fab fa-angular fa-lg text-danger me-3"></i><h5 class="text-break text-center" style="margin-bottom: 0">' +  result[i].roleToString + '👑' + '</h5></TD>';
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
            */

            //initTest() end

            <%--initTest("${pageContext.request.contextPath}/group-wallet/load-card-data");--%>

            // 연결 불가 선택 시 연결 불가를 알려주는 모달 창 출력
            $(document).on("click", "#cant-connect-card", function () {
                $("#cantConnectCard").modal('show');
            })
            // 연결 가능 선택 시 연결 가능을 알려주는 모달 창 출력
            $(document).on("click", "#can-connect-card", function () {
                var memberId = $(this).closest("tr").data("id");
                if (${sessionScope.member.memberId} == memberId) {
                    $("input[name='connect-memberId']").val(memberId);
                    $("#changeWallet").modal('show');
                }
            else
                {
                    $("#cantConnectCardByLogin").modal('show');
                }
            });
            // 변경 버튼을 누릴 시 컨트롤러로의 전송
            $(document).on("click", "#change-confirm-button", function () {
                var memberId = $("input[name='connect-memberId']").val();
                initTest("${pageContext.request.contextPath}/group-wallet/change-card-connection", memberId);
            });

    </script>
</head>
<body>

<%--카드 변경 모달창--%>
<div class="modal fade" id="changeWallet" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="changeWalletLabel">카드 변경</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="changeWalletBody">
                카드 연결을 변경하시겠습니까?
            </div>
            <div class="modal-footer">
                <input type="hidden" name="connect-memberId">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                <button type="button" class="btn btn-primary" id="change-confirm-button" data-bs-dismiss="modal">변경
                </button>
            </div>
        </div>
    </div>
</div>

<%--카드 변경 불가(권한 X) 모달창--%>
<div class="modal fade" id="cantConnectCard" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="cantConnectCardLabel">모달 제목</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="cantConnectCardBody">
                모임원은 카드를 연결할 수 없습니다
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="confirm-button" data-bs-dismiss="modal">확인</button>
            </div>
        </div>
    </div>
</div>

<%--카드 변경 불가(로그인한 멤버와 다름) 모달창--%>
<div class="modal fade" id="cantConnectCardByLogin" tabindex="-1" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="cantConnectCardByLoginLabel">모달 제목</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="cantConnectCardByLoginBody">
                카드 소유자만 변경할 수 있습니다
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="confirm-button2" data-bs-dismiss="modal">확인</button>
            </div>
        </div>
    </div>
</div>
<div class="card-header d-flex align-items-center justify-content-between pb-0">
    <div class="card-title mb-0">
        <h2 class="m-0 me-2">카드 연결내역</h2>
        <small class="text-muted">모임원</small>
    </div>
</div>
<table class="table table">
    <thead>
    <tr>
        <th class="text-center">
            <i class="fab fa-angular fa-lg text-danger me-3"></i>
            <h5 class="text-break" style="margin-bottom: 0">이름</h5>
        </th>
        <th class="text-center">
            <i class="fab fa-angular fa-lg text-danger me-3"></i>
            <h5 class="text-break" style="margin-bottom: 0">권한</h5>
        </th>
        <th class="text-center">
            <i class="fab fa-angular fa-lg text-danger me-3"></i>
            <h5 class="text-break" style="margin-bottom: 0">카드연결</h5>
        </th>
    </tr>
    </thead>
    <tbody class="table-border-bottom-0" id="table">
    </tbody>
</table>
</body>
</html>