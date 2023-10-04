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
    <style>
        @font-face {
            font-family: 'NanumSquareNeo-Variable';
            src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_11-01@1.0/NanumSquareNeo-Variable.woff2') format('woff2');
            font-weight: normal;
            font-style: normal;
        }
    </style>
    <title>깨비의 요술 지갑 - 모임지갑</title>

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
    <script src="../../../assets/js/common.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <script>

        <!-- jQuery를 사용하여 폼 제출 처리 -->
        $(document).ready(function () {
            $("#createForm").submit(function (event) {
                let nickname = $("#nickname").val();
                var redirectUrl = "${pageContext.request.contextPath}/group-wallet/";
                event.preventDefault(); // 기본 제출 동작을 막음

                // 폼 데이터를 비동기로 서버로 전송
                $.ajax({
                    type: 'POST',
                    url: $(this).attr("action"),
                    data: {nickname: nickname},
                    // data: $(this).serialize(), // 폼 데이터 직렬화
                    success: function (data) {
                        if (data != null) {
                            // 성공 시 처리 (data에 서버에서의 응답이 포함됨)
                            // alert("폼 데이터 전송 성공!");
                            alert(nickname + " 모임지갑을 생성했어요!");
                            location.href = redirectUrl;

                        } else {
                            alert("실패");
                        }
                        // 여기에서 성공 페이지로 이동하거나 추가 작업을 수행할 수 있습니다.
                    },
                    error: function () {
                        // 실패 시 처리
                        alert("폼 데이터 전송 실패. 에러 메시지를 표시할 수 있습니다.");
                    }
                });
            });
        });
    </script>

</head>
<body>
<header>
    <jsp:include page="../common/navbar.jsp"/>
</header>


<main>
    <div class="pageWrap">
        <div class="center">
            <div>
                <%-- 닉네임 받고 전송하도록 form --%>
                <form id="createForm" method="post" action="${pageContext.request.contextPath}/group-wallet/new">
                    <input type="text" name="nickname" id="nickname">
                    <button type="submit" value="생성" class="btn btn-primary">생성</button>
                    <h5>모임지갑 이름을 정해요!</h5>
                </form>

            </div>
        </div>
    </div>
</main>


<footer>

</footer>

</body>
</html>
