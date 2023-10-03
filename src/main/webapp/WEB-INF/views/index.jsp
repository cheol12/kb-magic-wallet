<%--
  Created by IntelliJ IDEA.
  User: 김진형
  Date: 2023-09-01
  Time: 오후 3:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>깨비의 요술 지갑 - 메인</title>

    <!--사용자 설정 css-->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css"/>

    <!--폰트 css-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/fonts/boxicons.css"/>
    <!-- Core CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/css/core.css"
          class="template-customizer-core-css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/css/theme-default.css"
          class="template-customizer-theme-css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/demo.css"/>
    <!-- Vendors CSS -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css"/>
    <!-- Page CSS -->
    <!-- Helpers -->
    <script src="${pageContext.request.contextPath}/assets/vendor/js/helpers.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/config.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>

    <!-- 모달, 드롭다운, 탭, 슬라이드 등 부트스트랩 구성 요소를 활성화하는 데 사용-->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            //==로그인 모달 띄우기==//
            $(document).on("click", "#loginModalOpen", function () {
                $('#loginModal').modal('show');
            });

            //== 로그인 START ==//
            $("#loginButton").click(function () {
                let contextPath = "${pageContext.request.contextPath}"
                console.log("로그인 버튼 누름")
                let id = $("#login_id").val();
                let pw = $("#login_pw").val();
                console.log(id)
                console.log(pw)

                if (!id || !pw) {
                    alert("올바른 아이디와 비밀번호를 입력하세요");
                    return;
                }

                $.ajax({
                    type: "POST",
                    url: contextPath + `/login`,
                    data: {
                        id: id,
                        password: pw
                    },
                    success: function (data, response) {
                        // 서버로부터의 응답을 처리
                        if(data==='success'){
                            window.location.href="${pageContext.request.contextPath}/"
                        }else {
                            $('#loginFail').modal('show');
                        }
                    },
                    error: function () {
                        console.log(data);
                        alert("에러발생");
                    }
                });
            });
        });
    </script>
</head>
<body>
<!--네비게이션 바-->
<jsp:include page="common/navbar.jsp"/>
<div>
    <div class="pageWrap" style="margin-top: 0">
        <div class="banner-container">
            <div class="above-container">
                <div id="first-box" class="box">
                    <div class="first-contents">
                        <h1 style="margin-top: 48px;">KB
                            <p id="first-text">MAGIC WALLET</p>
                            <p id="second-text">깨비의 요술지갑</p>
                        </h1>
                        <p>다같이 해외여행엔 매직월렛! 지갑 하나로 달러, 엔화 환전하기!</p>
                    </div>
                </div>
                <div id="second-box" class="box">
                    <img src="${pageContext.request.contextPath}/assets/img/icons/4.png">
                </div>
            </div>
        </div>

        <div class="container">
            <div class="text-above-rectangles">
                <h2>카드 한장으로</h2>
                <h2>떠나는 세계여행</h2>
                <div class="explainDiv">
                    <p>깨비의 요술지갑과 함께 세계를 누비며</p>
                    <p>놀랍도록 간편하고 안전한 여행을 누리세요.</p></div>
            </div>
            <!-- 사각형 1 -->
            <div class="rectangles">
                <div class="rectangle">
                    <div class="inner-content">
                        <img src="${pageContext.request.contextPath}/assets/img/icons/1.png">
                    </div>
                    <div class="divider">
                        <div class="title">
                            환전
                        </div>
                        <div class="content">
                            지갑 하나에 다양한 외화 충전 가능 <br> 전세계 어디서든 온/오프라인 환전 지원
                        </div>
                    </div>
                </div>

                <!-- 사각형 2 -->
                <div class="rectangle">
                    <div class="inner-content">
                        <img src="${pageContext.request.contextPath}/assets/img/icons/2.png">
                    </div>
                    <div class="divider">
                        <div class="title">
                            지갑
                        </div>
                        <div class="content">
                            개인지갑과 모임지갑을 제공 <br> 모임원들에게 투명한 입출금 내역 공개
                        </div>
                    </div>
                </div>

                <!-- 사각형 3 -->
                <div class="rectangle">
                    <div class="inner-content">
                        <img src="${pageContext.request.contextPath}/assets/img/icons/3.png">
                    </div>
                    <div class="divider">
                        <div class="title">
                            적금
                        </div>
                        <div class="content">
                            모임원들과 함께 하는 적금 <br> 모임지갑에 보관하는 것보다 최대 4%의 이자
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="login-modal.jsp"/>
</body>
</html>
