<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Title</title>

    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

    <script type="text/javascript">
        $(document).ready(function () {

            function inviteResponse() {
                let myMemberId = ${loginMemberDto.memberId};

                // 이후 JavaScript 코드에서 myMemberId 변수를 사용할 수 있음

                $.ajax({
                    url: "${pageContext.request.contextPath}/group-wallet/",
                    type: "post",
                    dataType: "json",
                    success: function (result, status) {
                        // 화면에 갱신
                        var str = "";
                        $.each(result, function (i) {
                            str += '<tr id="searchGroupWalletList">'
                            str += '<td>' + result[i].groupWallet.nickname + '</td>';
                            str += '<td>' + result[i].groupWallet.member.name + '</td>';
                            str += '<td><button class="alert-warning" data-member-id="' + result[i].groupWallet.groupWalletId + '" data-member-name="' + result[i].name + '">초대 응답</button>';

                            str += '</tr>';
                        });
                        $("#invitedMeList").empty();
                        $("#invitedMeList").append(str);

                        // 강퇴 버튼 클릭 이벤트 핸들러
                        //    모임장 권한 아직
                    },
                    error: function (result, status) {
                        // 오류 처리
                    },
                });
            }

            inviteResponse();

            // 모임지갑에서 강퇴 버튼 클릭

            // $(document).on("click", , function(){ }) 형식을 쓰는 이유
            // = 동적 요소에 대한 이벤트 처리: 이 방식을 사용하면 페이지가 로드된 이후에
            // 동적으로 생성되는 요소에 대해서도 이벤트 처리를 할 수 있다
            $(document).on("click", '.alert-warning', function () {
                let groupId = $(this).data("member-id");
                let memberName = $(this).data("member-name")

                var confirmation = confirm(groupId + "의 초대를 수락하시겠습니까?");

                if (confirmation) {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/group-wallet/" + groupId + "/invite-response",
                        type: "post",
                        data: {groupId: groupId},
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
        })

    </script>
</head>
<body>
<header>
    <jsp:include page="../common/navbar.jsp"/>
</header>



<main>
    <div class="pageWrap">


        <div class="center">
            <div class="d-flex align-items-end row">
                <div class="col-sm-7">
                    <div class="card-body">
                        <h1 class="card-title text-break" >${member.name}님이 참여 중인 모임지갑이에요 🎉</h1>
                        <p class="mb-4"></p>

                    </div>
                </div>
                <div class="col-sm-5 text-center text-sm-left">
                    <div class="card-body pb-0 px-0 px-md-4">
                        <img src="../assets/img/illustrations/man-with-laptop-light.png" height="140" alt="View Badge User" data-app-dark-img="illustrations/man-with-laptop-dark.png" data-app-light-img="illustrations/man-with-laptop-light.png">
                    </div>
                </div>
            </div>

            <div style="margin-top: 5px">
                <h2 class="card-title text-break"> 잠깐! 모임지갑으로부터 초대가 왔어요!</h2>
                <div class="table-responsive text-nowrap">
                    <table class="table table">
                        <thead>
                        <tr>

                        </tr>
                        </thead>
                        <tbody class="table-border-bottom-0" id="invitedMeList">

                        </tbody>
                    </table>


                </div>
            </div>

            <div>

                <c:forEach var="list" varStatus="status" items="${gWalletList}">


                    <div style="margin-top: 5px">
                        <div class="card">
                            <div class="card-header">
                                    ${list.getNickname()}
                            </div>
                            <div class="card-body">
                                <h5 class="card-title">원화 잔액 : ${list.getBalance()}</h5>
                                <a href="${pageContext.request.contextPath}/group-wallet/${list.getGroupWalletId()}" class="btn btn-primary">상세보기</a>
                            </div>
                        </div>
                    </div>

                </c:forEach>
                <section>
                    <div style="margin-top: 5px">
                        <div class="card">
                            <div class="card-header">
                                새로운 모임 지갑 생성
                            </div>
                            <div class="card-body">
                                <h5 class="card-title"></h5>
                                <a href="${pageContext.request.contextPath}/group-wallet/new" class="btn btn-primary">생성하기</a>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div>

    </div>
</main>


<footer>

</footer>
</body>
</html>
