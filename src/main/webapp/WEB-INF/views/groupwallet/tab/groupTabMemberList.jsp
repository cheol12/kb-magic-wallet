<%--
  Created by IntelliJ IDEA.
  User: 김진형
  Date: 2023-09-20
  Time: 오후 7:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>모임 멤버 조회</title>
    <script type="text/javascript">
        // 페이지 로딩 시
        $(document).ready(function () {

            // 모임지갑에서 강퇴 버튼 클릭
            // $(document).on("click", , function(){ }) 형식을 쓰는 이유
            // = 동적 요소에 대한 이벤트 처리: 이 방식을 사용하면 페이지가 로드된 이후에
            // 동적으로 생성되는 요소에 대해서도 이벤트 처리를 할 수 있다
            <%--$(document).on("click", '.alert-warning', function () {--%>
            <%--    let memberId = $(this).data("member-id");--%>
            <%--    let memberName = $(this).data("member-name")--%>

            <%--    var confirmation = confirm(memberName + "님을 강퇴하시겠습니까?");--%>

            <%--    if (confirmation) {--%>
            <%--        $.ajax({--%>
            <%--            url: "${pageContext.request.contextPath}/group-wallet/${id}/out",--%>
            <%--            type: "post",--%>
            <%--            data: {memberId: memberId},--%>
            <%--            success: function (result, response) {--%>
            <%--                console.log(result);--%>
            <%--                if (result > 0) {--%>
            <%--                    // 강퇴 성공 시 필요한 작업 수행--%>
            <%--                    alert(memberName + "님을 강퇴했어요")--%>
            <%--                    memberCall();--%>
            <%--                } else {--%>
            <%--                    alert("강퇴를 실패했어요");--%>
            <%--                }--%>
            <%--            },--%>
            <%--            error: function () {--%>
            <%--                // 강퇴 실패 시 필요한 작업 수행--%>
            <%--            }--%>
            <%--        });--%>
            <%--    } else {--%>
            <%--        alert("강퇴를 취소했습니다.");--%>
            <%--    }--%>

            <%--});--%>

            // 모임지갑 권한 부여 버튼 클릭
            <%--$(document).on("click", '.alert-primary', function () {--%>
            <%--    let memberId = $(this).data("member-id");--%>
            <%--    let memberName = $(this).data("member-name")--%>

            <%--    var confirmation = confirm(memberName + memberId + "님에게 공동모임장 권한을 부여하시겠습니까?");--%>

            <%--    if (confirmation) {--%>
            <%--        $.ajax({--%>
            <%--            url: "${pageContext.request.contextPath}/group-wallet/${id}/grant",--%>
            <%--            type: "post",--%>
            <%--            data: {memberId: memberId},--%>
            <%--            success: function (data, result, response) {--%>
            <%--                console.log(result);--%>
            <%--                console.log(data);--%>
            <%--                if (data > 0) {--%>
            <%--                    // 강퇴 성공 시 필요한 작업 수행--%>
            <%--                    alert(memberName + "님이 공동모임장이 되었어요!")--%>
            <%--                    memberCall();--%>
            <%--                } else {--%>
            <%--                    alert("권한 부여를 실패했어요");--%>
            <%--                }--%>
            <%--            },--%>
            <%--            error: function () {--%>
            <%--                // 강퇴 실패 시 필요한 작업 수행--%>
            <%--            }--%>
            <%--        });--%>
            <%--    } else {--%>
            <%--        alert("권한 부여를 취소했습니다.");--%>
            <%--    }--%>

            <%--});--%>
            // 모임지갑 권한 철회 버튼 클릭
            <%--$(document).on("click", '.alert-secondary', function () {--%>
            <%--    let memberId = $(this).data("member-id");--%>
            <%--    let memberName = $(this).data("member-name")--%>

            <%--    var confirmation = confirm(memberName + "님의 공동모임장 권한을 철회하시겠습니까?");--%>

            <%--    if (confirmation) {--%>
            <%--        $.ajax({--%>
            <%--            url: "${pageContext.request.contextPath}/group-wallet/${id}/revoke",--%>
            <%--            type: "post",--%>
            <%--            data: {memberId: memberId},--%>
            <%--            success: function (data, result, response) {--%>
            <%--                console.log(result);--%>
            <%--                console.log(data);--%>
            <%--                if (data > 0) {--%>
            <%--                    // 강퇴 성공 시 필요한 작업 수행--%>
            <%--                    // alert(memberName + "님이 모임원이 되었어요!")--%>
            <%--                    memberCall();--%>
            <%--                } else {--%>
            <%--                    // alert("권한 철회를 실패했어요");--%>
            <%--                }--%>
            <%--            },--%>
            <%--            error: function () {--%>
            <%--                // 강퇴 실패 시 필요한 작업 수행--%>
            <%--            }--%>
            <%--        });--%>
            <%--    } else {--%>
            <%--        // alert("권한 철회를 취소했습니다.");--%>
            <%--    }--%>

            <%--});--%>
        });

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
                    console.log("멤버 리스트 비동기 통신")
                    $.each(result, function (i) {
                        str += '<tr id="searchMemberResult">'
                        str += '<td class="text-center"><i class="fab fa-angular fa-lg text-danger me-3"></i><h5 class="text-break text-center" style="margin-bottom: 0">' + result[i].name + '</h5></td>';
                        str += '<td class="text-center"><i class="fab fa-angular fa-lg text-danger me-3"></i><h5 class="text-break text-center" style="margin-bottom: 0">' + result[i].roleToString + '</h5></td>';

                        if (result[i].roleToString === "모임장") {
                            str += '<td class="text-center"></td>';
                            str += '<td class="text-center"></td>';
                        } else if (result[i].roleToString === "공동모임장") {
                            str += '<td class="text-center"><button class="alert-secondary" data-member-id="' + result[i].memberId + '" data-member-name="' + result[i].name + '">권한 철회</button></td>';
                            str += '<td class="text-center"><button class="alert-warning" data-member-id="' + result[i].memberId + '" data-member-name="' + result[i].name + '">강퇴</button>';
                        } else {
                            str += '<td class="text-center"><button class="alert-primary" data-member-id="' + result[i].memberId + '" data-member-name="' + result[i].name + '">권한 부여</button></td>';
                            str += '<td class="text-center"><button class="alert-warning" data-member-id="' + result[i].memberId + '" data-member-name="' + result[i].name + '">강퇴</button></td>';
                        }

                        str += '</tr>';
                    });
                    $("#getMemberList").empty();
                    $("#getMemberList").append(str);

                },
                error: function (result, status) {
                    // 오류 처리
                },
            });
        }


    </script>
</head>
<body>
<div class="tab-pane fade show" id="navs-top-member" role="tabpanel">
    <div class="card " style="margin-top: 0px; padding-top: 0px">
        <h3 class="card-header">
            모임원 목록
        </h3>
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th class="text-center"><i class="fab fa-angular fa-lg text-danger me-3"></i><h4 class="text-break text-center" style="margin-bottom: 0">이름</h4></th>
                    <th class="text-center"><i class="fab fa-angular fa-lg text-danger me-3"></i><h4 class="text-break text-center" style="margin-bottom: 0">역할</h4></th>
                    <th class="text-center"><i class="fab fa-angular fa-lg text-danger me-3"></i><h4 class="text-break text-center" style="margin-bottom: 0">출금/결제 권한</h4></th>
                    <th class="text-center"><i class="fab fa-angular fa-lg text-danger me-3"></i><h4 class="text-break text-center" style="margin-bottom: 0">모임에서 내보내기</h4></th>
                </tr>
                </thead>
                <tbody id="getMemberList">
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>
