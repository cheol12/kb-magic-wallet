<%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-11
  Time: 오후 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원 가입</title>
    <link rel="stylesheet" type="text/css" href="../../css/loginform.css" /><!--로그인 폼 css-->
    <link rel="stylesheet"type="text/css" href="/css/common.css">
</head>
<body>
    <jsp:include page="../common/navbar.jsp"/>


    <div class="pageWrap">
        <h1 class="titDep1">회원가입 - 개인</h1>
<%--            <div class="contentArea">--%>
<%--                <div class="stepType stepCol4"></div>--%>
<%--                    <div class="inner">--%>
<%--                        <strong class="step01">--%>
<%--                            ::before--%>
<%--                            <span>--%>
<%--                                ::before--%>
<%--                                "현재"--%>
<%--                                ::after--%>
<%--                            </span>--%>
<%--                            "1. KBPay 인증"--%>
<%--                        </strong>--%>
<%--                        <span class="step02">--%>
<%--                            ::before--%>
<%--                            <span>--%>
<%--                                ::before--%>
<%--                                "현재"--%>
<%--                                ::after--%>
<%--                            </span>--%>
<%--                            "2. 약관동의"--%>
<%--                        </span>--%>
<%--                        <span class="step03">--%>
<%--                            ::before--%>
<%--                            <span>--%>
<%--                                ::before--%>
<%--                                "현재"--%>
<%--                                ::after--%>
<%--                            </span>--%>
<%--                            "3. 약관동의"--%>
<%--                        </span>--%>
<%--                        <span class="step04">--%>
<%--                            ::before--%>
<%--                            <span>--%>
<%--                                ::before--%>
<%--                                "현재"--%>
<%--                                ::after--%>
<%--                            </span>--%>
<%--                            "4. 완료"--%>
<%--                        </span>--%>
<%--                    </div>--%>

<%--            </div>--%>
        <div class="center">
            <!--회원가입 폼-->
            <form method="post" action="/register">
                <div class="group">
                    <input type="text" name="id"><span class="highlight"></span><span class="bar"></span>
                    <label>아이디</label>
                </div>
                <div class="group">
                    <input type="password" name="password"><span class="highlight"></span><span class="bar"></span>
                    <label>비밀번호</label>
                </div>
                <div class="group">
                    <input type="text" name="name"><span class="highlight"></span><span class="bar"></span>
                    <label>이름</label>
                </div>
                <div class="group">
                    <input type="text" name="city"><span class="highlight"></span><span class="bar"></span>
                    <label>시/도/군</label>
                </div>
                <div class="group">
                    <input type="text" name="street"><span class="highlight"></span><span class="bar"></span>
                    <label>도로명</label>
                </div>
                <div class="group">
                    <input type="text" name="zipcode"><span class="highlight"></span><span class="bar"></span>
                    <label>우편번호</label>
                </div>
                <div class="group">
                    <input type="text" name="phoneNumber"><span class="highlight"></span><span class="bar"></span>
                    <label>전화번호</label>
                </div>
                <div class="group">
                    <input type="text" name="email"><span class="highlight"></span><span class="bar"></span>
                    <label>이메일</label>
                </div>
                <div class="group">
                    <input type="text" name="payPassword"><span class="highlight"></span><span class="bar"></span>
                    <label>kb페이 비밀번호</label>
                </div>
                <div class="group">
                    <input type="text" name="bankAccount"><span class="highlight"></span><span class="bar"></span>
                    <label>은행 계좌번호</label>
                </div>
                <button type="ripples buttonRipples" class="button buttonBlue">회원 가입
                    <div class="ripplesCircle"><span class="bar"></span></div>
                </button>
            </form>
            <!--/회원가입 폼-->
        </div>
    </div>
</body>
</html>
