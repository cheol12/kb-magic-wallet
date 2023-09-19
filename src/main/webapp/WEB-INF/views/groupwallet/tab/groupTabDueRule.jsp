<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회비 규칙 탭</title>
    <script type="text/javascript">
        let pageContext = "${pageContext.request.contextPath}";

        $(document).ready(function () {
            // 함수 선언
            function dueRule() {
                console.log("회비 규칙 비동기 통신");

                $.ajax({
                    url: pageContext + "/group-wallet/${id}/rule",
                    type: "GET",
                    dataType: "json",
                    success: function (data, textStatus, jqXHR) {
                        console.log(data.dueCondition);

                        let str = "";
                        // 성공적으로 응답을 받았을 때 실행되는 함수
                        // data: 서버에서 받은 응답 데이터
                        if (data.dueCondition === true) { // dueCondition이 true일 때
                            str += '<h5 style="text-align: center">' + data.nickname + ' 회비 규칙</h5>';
                            str += '<h5 style="text-align: center">' +
                                '매월 ' + data.dueDate + '일에 모임원들이 ' + data.due + '원 씩 회비를 납부해요!' +
                                '</h5>';
                            if (data.isChairman) {
                                str += '<div class="text-end">';
                                str += '<button type="button" class="btn btn-outline-danger btn-sm" style="align-self: center">회비 규칙 삭제</button>'
                                str += '</div>'
                            }
                        } else { // dueCondition이 false일 때
                            str += '<h5 style="text-align: center">회비 규칙이 없습니다.&nbsp;';
                            if (data.isChairman) {
                                str += '회비를 생성해 볼까요?&nbsp;';
                                str += '<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#dueModal">';
                                str += '회비 규칙 생성';
                                str += '</button>';
                            }
                            str += '</h4>'
                        }

                        $("#resultTabRule").empty();
                        $("#resultTabRule").append(str);
                        // textStatus: HTTP 상태 메시지 (예: "success", "notmodified", "error", "timeout", "abort", "parsererror" 등)
                        // jqXHR: jQuery XMLHttpRequest 객체
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        // 요청 중에 오류가 발생했을 때 실행되는 함수
                        // jqXHR: jQuery XMLHttpRequest 객체
                        // textStatus: HTTP 상태 메시지 (예: "error", "timeout", "abort", "parsererror" 등)
                        // errorThrown: 예외 객체 (실제 오류 정보)
                    }
                });
            }

            function dueMemberList() {
                console.log("회비 납부 내역 비동기 통신");

                $.ajax({

                });
            }

            // 함수 사용
            dueRule();
        });


    </script>
</head>
<body>
<!-- 회비 규칙 START -->
<div class="tab-pane fade" id="navs-top-rule" role="tabpanel" >
    <div class="card" style="margin-top: 5px;">
        <div class="card-body" id="resultTabRule">
            <h1>회비 규칙</h1>
        </div>
    </div>
<%--    회비 납부 멤버 리스트 --%>
</div>
<!-- 회비 규칙 END -->
</body>
</html>
