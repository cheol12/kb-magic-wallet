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
    <title>깨비의 요술 지갑 - 모임초대</title>

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

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
    <script type="text/javascript">


    </script>

    <script type="text/javascript">
        // Ajax 요청을 보내고 반환된 값을 처리합니다.
        $(document).ready(function () {

            <%--$("#inviteButton").click(function () {--%>
            <%--    let phone = $("#phoneInput").val(); // 폼 입력값 가져오기--%>
            <%--    let id = ${id}; // JSP 변수 'id' 가져오기--%>
            <%--    console.log(phone);--%>
            <%--    console.log(id);--%>
            <%--    $.ajax({--%>
            <%--        type: "POST",--%>
            <%--        url: "${pageContext.request.contextPath}/group-wallet/" + id +"/invite-request",--%>
            <%--        data : {phone : phone},--%>
            <%--        success: function (data) {--%>
            <%--            console.log("data = " + data + this.data)--%>
            <%--            // 서버에서 반환된 데이터 처리--%>
            <%--            if (data === 1) {--%>
            <%--                // 반환된 값이 1인 경우 성공 메시지 표시--%>
            <%--                $("#resultMessage").text("초대 링크가 생성되었습니다.");--%>
            <%--            } else {--%>
            <%--                // 반환된 값이 1이 아닌 경우 실패 메시지 표시--%>
            <%--                $("#resultMessage").text("초대 링크 생성에 실패했습니다.");--%>
            <%--            }--%>
            <%--        }--%>
            <%--    });--%>
            <%--});--%>

            $("#inviteForm").submit(function (event) {
                event.preventDefault(); // 폼의 기본 동작(페이지 리로딩)을 막습니다.

                var phone = $("#phoneInput").val(); // 폼 데이터를 직렬화하여 가져옵니다.
                var id = ${id}; // JSP 변수 'id' 가져오기

                console.log(phone)
                console.log(id)

                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/group-wallet/" + id + "/invite-request",
                    data: {phone:phone},
                    success: function (data) {
                        if (data === 1) {
                            $("#resultMessage").text(phone + "에게 초대 요청을 보냈어요!");
                        } else {
                            $("#resultMessage").text("이미 모임 지갑에 있나봐요!");
                        }
                    }
                });
            });


        });
    </script>

</head>
<body>
<jsp:include page="/WEB-INF/views/common/navbar.jsp"/>

<div class="pageWrap">
    <div class="center">
        <div class="row">
            <h2>${groupWallet.nickname}에 모임원을 새로 초대해보세요!</h2>
        </div>

        <div class="row">

            <div class="col-md-14 col-lg-14 col-xl-14 mb-4 h-100">

                <h4 class="text-muted"></h4>
                <div class="card h-20">
                    <div class="card-header d-flex align-items-center justify-content-between pb-0">
                        <div class="card-title mb-0">
                            <h5 class="m-0 me-2">초대하고 싶은 사람의 전화번호를 입력해요</h5>

                        </div>
                    </div>
                    <div class="card-body">
                        <form id="inviteForm">
                            <div class="row g-3 align-items-center">
                                <div class="col-auto">
                                    <label for="phoneInput" class="col-form-label"></label>
                                </div>
                                <div class="col-auto">

                                    <input type="text" id="phoneInput" class="form-control" aria-describedby="passwordHelpInline" name="phone">
                                </div>
                                <div class="col-auto">
                                    <button id="inviteButton" type="submit" class="btn btn-primary">초대 요청</button>
                                </div>
                            </div>
                        </form>

                        <div id="resultMessage"></div>
                        <a href="${pageContext.request.contextPath}/group-wallet/${id}" id="pageMove" class="btn btn-primary">모임 지갑으로 돌아가기</a>

                    </div>
                </div>
            </div>


        </div>


        <br>
        <br>
        <br>
        <br>


    </div>


</div>



<p></p>
<br>
<br>
<br>
<br>
<br>
<br>

<footer>

</footer>
</body>
</html>