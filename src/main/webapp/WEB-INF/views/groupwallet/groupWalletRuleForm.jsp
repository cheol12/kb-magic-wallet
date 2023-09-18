<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: cheol
  Date: 2023-09-17
  Time: 오후 6:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회비 규칙 생성</title>

    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>

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

    <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
    <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
    <script src="../../../assets/js/config.js"></script>

    <!-- jQuery 라이브러리 추가 -->
    <script src="../../../js/jquery-3.6.1.js"></script>

    <script>
        // $(document).ready(function() {
        //     $("#createRuleButton").click(function() {
        //         let dueDate = $("#dueDate").val();
        //         let due = $("#due").val();
        //
        //         console.log(due);
        //         console.log(dueDate)
        //         console.log(!due);
        //
        //         if (!dueDate || !due) {
        //             alert("납부일과 납부금을 모두 입력해야 합니다.");
        //             return;
        //         }
        //
        //         let confirmation = confirm("이제부터 모임원들이 매월 " + dueDate + "일에 " + due + "원을 낼 거예요!");
        //
        //         if (confirmation) {
        //             // 확인을 누른 경우 폼을 제출
        //             $("#dueRuleForm").submit();
        //         }
        //         else{
        //             alert("취소하였습니다.")
        //         }
        //     });
        // });

        $(document).ready(function() {
            let contextPath = "${pageContext.request.contextPath}"
            $("#createRuleButton").click(function() {
                let dueDate = $("#dueDate").val();
                let due = $("#due").val();

                if (!dueDate || !due) {
                    alert("납부일과 납부금을 모두 입력해야 합니다.");
                    return;
                }

                $.ajax({
                    type: "POST",
                    url: contextPath + "/group-wallet/${id}/rule/create",
                    data: {
                        dueDate: dueDate,
                        due: due
                    },
                    success: function(data, response) {
                        // 서버로부터의 응답을 처리
                        $("#resultMessage").text(response);
                        alert("이제부터 모임원들이 매월 " + dueDate + "일에 " + due + "원을 낼 거예요!");
                        location.href(contextPath + "/group-wallet/${id}");
                    },
                    error: function() {
                        alert("회비 규칙 생성에 실패했습니다.");
                    }
                });
            });
        });
    </script>

</head>
<body>

<jsp:include page="../common/navbar.jsp"></jsp:include>

<div class="pageWrap">
    <div class="center">


        <div class="content-wrapper">
            <!-- Content -->
            <div class="container-xxl flex-grow-1 container-p-y">
                <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">모임지갑/</span> ${groupWallet.nickname}의 회비 규칙 설정</h4>
                <!-- Basic Layout -->
                <form>
                    <div class="row">
                        <div class="col-xl">
                            <div class="card mb-4">
                                <div class="card-header d-flex justify-content-between align-items-center">
                                    <h5 class="mb-0">회비 규칙</h5>
                                    <small class="text-muted float-end"></small>
                                </div>
                                <div class="card-body">

                                    <div class=" align-items-center">
                                        <form action="${pageContext.request.contextPath}/group-wallet/${id}/rule/create" method="post" id="dueRuleForm">
                                            <label for="dueDate">매월 납부일(일수) : </label>
                                            <input type="number" id="dueDate" name="dueDate" min="1" max="31" required>
                                            <span>일</span>
                                            <p></p>
                                            <label for="due">납부금 : </label>
                                            <input type="number" id="due" name="due" min="1" max="1000000" required>
                                            <span>원</span>
                                            <p></p>
                                            <br>
                                            <br>
                                            <br>
                                            <!-- 다른 입력 필드들 추가 가능 -->

                                            <button type="submit" id="createRuleButton">규칙 생성</button>
                                        </form>
                                    </div>

                                </div>
                            </div>
                        </div>

                    </div>
                </form>
            </div>
        </div>
        <!-- / Content -->

    </div>
</div>


</div>
</body>
</html>
