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
    <script>
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
    </script>
</head>
<body>

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

</body>
</html>
