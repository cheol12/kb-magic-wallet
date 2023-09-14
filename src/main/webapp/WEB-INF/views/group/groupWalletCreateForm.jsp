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
</head>
<body>
<header>
    <jsp:include page="../common/navbar.jsp"/>
</header>


<main>
    <div class="pageWrap">
        <div class="center">
<%-- 닉네임 받고 전송하도록 form --%>
<form id="createForm" method="post" action="${pageContext.request.contextPath}/group-wallet/new">
    <input type="text" name="nickname" id="nickname">
    <input type="submit" value="생성">
</form>
        </div>

    </div>
</main>


<footer>

</footer>
<!-- jQuery를 사용하여 폼 제출 처리 -->
<script>
    $(document).ready(function () {
        $("#createForm").submit(function (event) {
            let nickname = $("#nickname").val();
            event.preventDefault(); // 기본 제출 동작을 막음

            // 폼 데이터를 비동기로 서버로 전송
            $.ajax({
                type: 'POST',
                url: $(this).attr("action"),
                data:{nickname : nickname},
                // data: $(this).serialize(), // 폼 데이터 직렬화
                success: function (data) {
                    if(data != null){
                        // 성공 시 처리 (data에 서버에서의 응답이 포함됨)
                        alert("폼 데이터 전송 성공!");
                    }
                    else{
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
</body>
</html>
