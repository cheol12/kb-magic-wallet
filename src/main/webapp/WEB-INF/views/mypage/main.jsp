<%@ page import="kb04.team02.web.mvc.common.dto.LoginMemberDto" %><%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-11
  Time: 오후 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

    <script>
        // 페이지 로드 시에 successMessage가 있으면 알림 창을 띄움
        window.onload = function() {
            const successMessage = "${successMessage}"; // JSP에서 전달된 메시지를 JavaScript 변수로 가져옴
            const failMessage = "${failMessage}";

            if (failMessage) {
                alert(failMessage);
            }

            if (successMessage) {
                alert(successMessage); // 알림 창에 메시지 표시
            }
        }
    </script>

</head>
<body>
<%
    Object obj = session.getAttribute("member");
    LoginMemberDto member = (LoginMemberDto) obj;


%>
<jsp:include page="../common/navbar.jsp"/>
<div class="pageWrap">
    <div class="center">

        <div class="card mb-3">
            <div class="row g-0">
                <div class="col-md-4">
                    <img class="card-img card-img-left" src="../assets/img/icons/people.png" alt="Card image" style="background: darkgrey;" />
                </div>
                <div class="col-md-8">
                    <div class="card-body">
                        <div class="col-lg">
                            <div class="card mb-4 mb-lg-0">
                                <h5 class="card-header" style="padding-bottom: 0px">개인 정보</h5>
                                <div class="card-body" style="padding-bottom: 0px" >
                                    <blockquote class="blockquote mt-3">
                                        <p class="mb-0"><%out.print(member.getName());%></p>
                                    </blockquote>
                                </div>
                                <hr class="m-0" />
                                <h5 class="card-header" style="padding-bottom: 0px">지갑 정보</h5>
                                <div class="card-body" style="padding-bottom: 0px">
                                    <blockquote class="blockquote mt-3">
                                        <p class="mb-0">개인 지갑</p>
                                        <p class="mb-0">${walletDetailDto.getBalance().get("USD")} 달러</p>
                                        <p class="mb-0">${walletDetailDto.getBalance().get("JPY")} 엔</p>
                                        <p class="mb-0">모임 지갑  ${gWalletList.size()}개</p>
                                    </blockquote>
                                </div>
                                <hr class="m-0" />


                            </div>
                        </div>


                    </div>
                </div>
            </div>
        </div>

        <div class="row mb-5">
            <div class="col-md-6 col-lg-6">
                <div class="card mb-3">
                    <div class="row g-0">
                        <div class="col-md-4">
                            <img src="${pageContext.request.contextPath}/assets/img/icons/people.png" alt="Card image" class="card-img">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title">카드 관리</h5>
                                <p class="card-text">개인 카드 관리 서비스</p>
                                <a href="${pageContext.request.contextPath}/mypage/cardForm" class="btn btn-primary">카드 신청 페이지</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-lg-6">
                <div class="card mb-3">
                    <div class="row g-0">
                        <div class="col-md-4">
                            <img src="${pageContext.request.contextPath}/assets/img/icons/people.png" alt="Card image" class="card-img">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title">계좌 연결</h5>
                                <p class="card-text">기존 계좌를 새로운 계좌로 변경</p>
                                <a href="/mypage/bankForm" class="btn btn-primary">계좌 연결 하기</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>
