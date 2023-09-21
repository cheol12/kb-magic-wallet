<%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-14
  Time: 오후 6:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>깨비의 요술 지갑 - 모임 지갑</title>

    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>



</head>
<body>

<jsp:include page="../common/navbar.jsp"></jsp:include>

<div class="pageWrap">
    <div class="center">


        <form id="withdrawForm" action="${pageContext.request.contextPath}/group-wallet/${id}/withdraw" method="post">
            <div class="row g-3 align-items-center" >
                <div class="col-auto">
                    <label for="amount" class="col-form-label">환불하실 금액</label>
                </div>
                <div class="col-auto" >

                    <input type="text" id="amount" class="form-control" aria-describedby="passwordHelpInline"
                           name="amount">


                </div>

            </div>
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-primary" id="withdrawButton" data-bs-toggle="modal"
                    data-bs-target="#exampleModal">
                꺼내기
            </button>

            <!-- Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1"
                 aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">결제 비밀번호 확인</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close">
                            </button>
                        </div>
                        <div class="modal-body">
                            <jsp:include page="../common/virtualKeyboard.jsp"/>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                닫기
                            </button>
                            <button type="button" id="saveChangesButton" class="btn btn-primary">비밀번호 확인</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>


    </div>
</div>
<script>
    $(document).ready(function () {
        $("#saveChangesButton").click(function () {
            handleEnter()
        });
    });

    function summitForm() {
        $("#withdrawForm").submit();
    }
</script>
</body>
</html>
