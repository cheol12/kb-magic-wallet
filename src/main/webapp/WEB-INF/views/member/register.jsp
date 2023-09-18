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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/fonts/boxicons.css"/>

    <!-- Core CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/css/core.css" class="template-customizer-core-css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/css/theme-default.css" class="template-customizer-theme-css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/demo.css"/>

    <!-- Vendors CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css"/>

    <!-- Page CSS -->

    <!-- Helpers -->
    <script src="${pageContext.request.contextPath}/assets/vendor/js/helpers.js"></script>

    <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
    <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
    <script src="${pageContext.request.contextPath}/assets/js/config.js"></script>
</head>
<body>
    <jsp:include page="../common/navbar.jsp"/>


    <div class="pageWrap">

        <div class="center">
            <div class="col-xxl">
                <div class="card mb-4">
                    <div class="card-header d-flex align-items-center justify-content-between">
                        <h5 class="mb-0">회원가입</h5>
                    </div>
                    <div class="card-body">
                        <form method="post" action="/register">
                            <div class="row mb-3">
                                <label class="col-sm-2 col-form-label" for="register_id">아이디</label>
                                <div class="col-sm-10">
                                    <input name="id" type="text" class="form-control" id="register_id" placeholder="gildong1234" />
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label class="col-sm-2 col-form-label" for="basic-default-company">비밀번호</label>
                                <div class="col-sm-10">
                                    <input
                                            name="password"
                                            type="password"
                                            class="form-control"
                                            id="basic-default-company"
                                    />
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label class="col-sm-2 col-form-label" for="register_name">이름</label>
                                <div class="col-sm-10">
                                    <input name="name" type="text" class="form-control" id="register_name" placeholder="홍길동" />
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label class="col-sm-2 col-form-label" for="basic-default-email">이메일</label>
                                <div class="col-sm-10">
                                    <div class="input-group input-group-merge">
                                        <input
                                                name="email"
                                                type="text"
                                                id="basic-default-email"
                                                class="form-control"
                                                placeholder="gildong@gmail.com"
                                                aria-describedby="basic-default-email2"
                                        />
                                    </div>
                                    <div class="form-text">You can use letters, numbers & periods</div>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label class="col-sm-2 col-form-label" for="basic-default-phone">연락처</label>
                                <div class="col-sm-10">
                                    <input
                                            name="phoneNumber"
                                            type="text"
                                            id="basic-default-phone"
                                            class="form-control phone-mask"
                                            placeholder="010-41234-5678"
                                            aria-describedby="basic-default-phone"
                                    />
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label class="col-sm-2 col-form-label" for="register_address">주소</label>
                                <div class="col-sm-3">
                                    <input
                                            name="city"
                                            type="text"
                                            id="register_address"
                                            class="form-control phone-mask"
                                            placeholder="시/도/군"
                                            aria-describedby="basic-default-phone"
                                    />
                                </div>
                                <div class="col-sm-7">
                                    <input
                                            name="zipcode"
                                            type="text"
                                            id="register_zipcode"
                                            class="form-control phone-mask"
                                            placeholder="우편번호"
                                            aria-describedby="basic-default-phone"
                                    />
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label class="col-sm-2 col-form-label"></label>
                                <div class="col-sm-10">
                                    <input
                                            name="street"
                                            type="text"
                                            id="register_address2"
                                            class="form-control phone-mask"
                                            placeholder="상세주소"
                                            aria-describedby="basic-default-phone"
                                    />
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label class="col-sm-2 col-form-label" for="pay_password">결제 비밀번호</label>
                                <div class="col-sm-10">
                                    <input
                                            name="payPassword"
                                            type="password"
                                            id="pay_password"
                                            class="form-control phone-mask"
                                            aria-describedby="basic-default-phone"
                                    />
                                </div>
                            </div>
                            <div class="row mb-3">
                                <label class="col-sm-2 col-form-label" for="register_account">계좌번호</label>
                                <div class="col-sm-10">
                                    <input
                                            name="bankAccount"
                                            type="text"
                                            id="register_account"
                                            class="form-control phone-mask"
                                            placeholder="942902-00-291438"
                                            aria-describedby="basic-default-phone"
                                    />
                                </div>
                            </div>
                            <div class="row justify-content-end">
                                <div class="col-sm-10">
                                    <button type="submit" class="btn btn-primary">회원 가입</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <!--/회원가입 폼-->
        </div>
    </div>
</body>
</html>
