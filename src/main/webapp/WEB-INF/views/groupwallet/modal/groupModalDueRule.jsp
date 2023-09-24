<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회비 규칙 생성 모달</title>
    <script type="text/javascript">
        $(document).ready(function () {
            //== 회비 규칙 START ==//
            $("#createRuleButton").click(function () {
                let contextPath = "${pageContext.request.contextPath}"
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
                    url: contextPath + `/group-wallet/${id}/rule/create`,
                    data: {
                        dueDate: dueDate,
                        due: due * 10000
                    },
                    success: function (data, response) {
                        // 서버로부터의 응답을 처리
                        // $("#resultMessage").text(response);
                        alert("이제부터 모임원들이 매 월 " + dueDate + "일에 " + due + "만 원을 낼 거예요!");
                        let redirectUrl = contextPath + '/group-wallet/' + data;
                        console.log(redirectUrl)
                        window.location.href = redirectUrl;
                    },
                    error: function () {
                        alert("회비 규칙 생성에 실패했습니다.");
                    }
                });
            });
            //== 회비 규칙 END ==//
            //
            // document.getElementById('dueRuleFormModalInputDue').addEventListener('input', function () {
            //     let input = formatNumberWithCommas(this.value);
            //     this.value = input;
            // });
        });
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
                        <input type="number" class="form-control text-end" placeholder="1"
                               id="dueRuleFormModalInputDue"
                               aria-label="Amount (to the nearest dollar)"/>
                        <span class="input-group-text">만원</span>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-label-secondary" data-bs-dismiss="modal">취소</button>
                <button type="button" class="btn btn-primary" id="createRuleButton">회비 규칙 생성</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
