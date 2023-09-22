<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 김진형
  Date: 2023-09-20
  Time: 오후 7:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>모임 적금 조회</title>

    <script>

        // 적금 해지 버튼 클릭 시 알림창 띄우기
        document.getElementById("cancelSaving").addEventListener("click", function (event) {
            event.preventDefault();

            var confirmation = confirm("적금을 해지하시겠습니까? 만기일이 지나지 않았으면 해지시 이자도 함께 소멸됩니다.");

            if (confirmation) {
                // 확인 버튼을 눌렀을 때, 적금 해지를 서버에 요청
                var groupWalletId = "${groupWallet.groupWalletId}"; // 그룹 월렛 아이디 변수로 설정
                var xhr = new XMLHttpRequest();
                xhr.open("DELETE", "${pageContext.request.contextPath}/group-wallet/" + groupWalletId + "/saving", true);
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4) {
                        if (xhr.status === 200) {
                            // 적금 해지가 성공적으로 처리되었을 때 알림 메시지 띄우기
                            alert("적금이 해지되었습니다.");
                            // 페이지 리로드 또는 다른 동작 수행
                            window.location.reload(); // 페이지 리로드 예시
                        } else {
                            // 적금 해지가 실패했을 때 알림 메시지 띄우기
                            var errorMessage = xhr.responseText;
                            alert(errorMessage);
                        }
                    }
                };
                xhr.send();
            }
        });
    </script>

</head>
<body>
<div class="tab-pane fade" id="navs-top-save" role="tabpanel">
    <div class="card" style="margin-top: 5px;">
        <c:choose>
            <c:when test="${installmentDto == null}">
                <c:choose>
                    <c:when test="${isChairman}">
                        <p><strong>적금을 가입하지 않으셨습니다.</strong></p>
                        <a href="${pageContext.request.contextPath}/saving/"
                           class="btn btn-primary">적금 보러가기</a>
                    </c:when>
                    <c:otherwise>
                        <p><strong>가입된 적금이 없습니다. 모임장에게 적금 가입을 추천하는건 어떨까요?</strong></p>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <div class="card">
                    <h5 class="card-header">${installmentDto.savingName}</h5>
                    <div class="table-responsive text-nowrap">
                        <table class="table table">
                            <thead>
                            <tr class="text-nowrap">
                                <th>정보</th>
                                <th>내용</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th scope="row">금리</th>
                                <td>${installmentDto.interestRate}%</td>
                            </tr>
                            <tr>
                                <th scope="row">기간</th>
                                <td>${installmentDto.period}개월</td>
                            </tr>
                            <tr>
                                <th scope="row">가입일</th>
                                <td>${installmentDto.insertDate}</td>
                            </tr>
                            <tr>
                                <th scope="row">만기일</th>
                                <td>${installmentDto.maturityDate}</td>
                            </tr>
                            <tr>
                                <th scope="row">현재까지</th>
                                <td>${installmentDto.totalAmount}원</td>
                            </tr>
                            <tr>
                                <th scope="row">납입일</th>
                                <td>매월 ${installmentDto.savingDate}일</td>
                            </tr>
                            <tr>
                                <th scope="row">납입금</th>
                                <td> ${installmentDto.savingAmount}원</td>
                            </tr>
                            </tbody>
                        </table>
                        <div class="d-grid gap-2 col-lg-1 mx-auto">
                            <c:choose>
                                <c:when test="${isChairman}">
                                    <%--                                                                <a href="${pageContext.request.contextPath}/group-wallet/${groupWallet.groupWalletId}/saving" id="cancelSaving" class="btn btn-primary">적금 해지</a>--%>
                                    <%----%>
                                    <button type="submit" class="btn btn-primary" id="cancelSaving">
                                        적금 해지
                                    </button>
                                    <script>
                                        // 적금 해지 버튼 클릭 시 알림창 띄우기
                                        document.getElementById("cancelSaving").addEventListener("click", function(event) {
                                            event.preventDefault();

                                            var confirmation = confirm("적금을 해지하시겠습니까? 해지시 이자도 함께 소멸됩니다.");

                                            if (confirmation) {
                                                // 확인 버튼을 눌렀을 때, 적금 해지를 서버에 요청
                                                var groupWalletId = "${groupWallet.groupWalletId}"; // 그룹 월렛 아이디 변수로 설정
                                                var xhr = new XMLHttpRequest();
                                                xhr.open("DELETE", "${pageContext.request.contextPath}/group-wallet/" + groupWalletId + "/saving", true);
                                                xhr.onreadystatechange = function() {
                                                    if (xhr.readyState === 4) {
                                                        if (xhr.status === 200) {
                                                            // 적금 해지가 성공적으로 처리되었을 때 알림 메시지 띄우기
                                                            alert("적금이 해지되었습니다.");
                                                            // 페이지 리로드 또는 다른 동작 수행
                                                            window.location.reload(); // 페이지 리로드 예시
                                                        } else {
                                                            // 적금 해지가 실패했을 때 알림 메시지 띄우기
                                                            var errorMessage = xhr.responseText;
                                                            alert(errorMessage);
                                                        }
                                                    }
                                                };
                                                xhr.send();
                                            }
                                        });
                                    </script>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
