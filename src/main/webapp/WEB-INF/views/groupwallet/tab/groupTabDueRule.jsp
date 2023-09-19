<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회비 규칙 탭</title>
    <script type="text/javascript">

    </script>
</head>
<body>
<!-- 회비 규칙 START -->
<div class="tab-pane fade" id="navs-top-rule" role="tabpanel">
    <div class="card" style="margin-top: 5px;">
        <div class="card-header">
            <c:choose>
                <c:when test="${groupWallet.dueCondition}">
                    회비 규칙 ${groupWallet.dueCondition},
                    <p></p>
                    매월 : ${groupWallet.dueDate}일, ${groupWallet.due}원
                    <br>
                    현재 누적 회비 : ${groupWallet.dueAccumulation}원
                    <c:choose>
                        <c:when test="${isChairman == true}">
                            <p>
                            <a href="${pageContext.request.contextPath}/group-wallet/${id}/rule"
                               class="btn btn-primary">회비 규칙 수</a>
                        </c:when>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <h4 style="text-align: center">회비 규칙이 없습니다.&nbsp;
                        <c:choose>
                            <c:when test="${isChairman == true}">
                                <!-- 모임장 일 때만 -->
                                <!-- 회비 규칙 생성 폼으로 넘어가는 버튼 -->
                                회비를 생성 해볼까요?&nbsp;
                                <a href="${pageContext.request.contextPath}/group-wallet/${id}/rule"
                                   class="btn btn-primary">회비 규칙 생성</a>
                                <!-- Button trigger modal -->
                                <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                        data-bs-target="#dueModal">
                                    Launch modal
                                </button>

                            </c:when>
                        </c:choose>
                    </h4>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<!-- 회비 규칙 END -->
</body>
</html>
