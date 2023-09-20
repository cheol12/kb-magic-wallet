<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회비 규칙 생성 모달</title>
    <script type="text/javascript">
        //== 회비 규칙 START ==//
        let contextPath = "${pageContext.request.contextPath}"
        $("#createRuleButton").click(function () {
            console.log("규칙 생성 버튼 누름")
            let dueDate = $("#dueRuleFormModalSelectDueDate").val();
            let due = $("#dueRuleFormModalInputDue").val();
            console.log(dueDate)
            console.log(due)

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
                success: function (data, response) {
                    // 서버로부터의 응답을 처리
                    $("#resultMessage").text(response);
                    alert("이제부터 모임원들이 매월 " + dueDate + "일에 " + due + "원을 낼 거예요!");
                    location.href(contextPath + "/group-wallet/${id}");
                },
                error: function () {
                    alert("회비 규칙 생성에 실패했습니다.");
                }
            });
        });
        //== 회비 규칙 END ==//
    </script>
</head>
<body>

<div class="modal fade" id="dueModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel3">회비 규칙 정하기</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="mb-3">
                        <label for="dueRuleFormModalSelectDueDate" class="form-label">언제</label>
                        <select class="form-select" id="dueRuleFormModalSelectDueDate"
                                aria-label="Default select example">
                            <option selected>납부일을 선택해주세요!</option>
                            <c:forEach var="day" begin="1" end="31">
                                <option value="${day}">${day}일</option>
                            </c:forEach>
                            <option value="32">말일</option>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <label for="dueRuleFormModalInputDue" class="form-label">얼마를</label>
                    <div class="input-group input-group-merge">
                        <span class="input-group-text">₩</span>
                        <input type="number" class="form-control" placeholder="10000"
                               id="dueRuleFormModalInputDue"
                               aria-label="Amount (to the nearest dollar)"/>
                        <span class="input-group-text">원</span>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-label-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="createRuleButton">Save changes</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
