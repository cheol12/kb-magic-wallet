<%--
  Created by IntelliJ IDEA.
  User: jiwon
  Date: 2023-09-11
  Time: 오후 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" type="text/css" href="/css/common.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</head>
<body>

<script>
    function validateForm() {
        const agreement1 = document.querySelector("#agreement1").checked;
        const agreement2 = document.querySelector("#agreement2").checked;
        var check = document.getElementById("account").value;
        if (!agreement1 || !agreement2) {
            alert("두 동의 모두 체크해주세요.");
            return false; // 폼 제출 중지
        }

        if (!check) { // checkValue가 null 또는 빈 문자열인 경우
            alert("계좌를 입력해주세요.");
            return false; // 폼 제출 중지
        }
        return true;
    }
</script>
<jsp:include page="../common/navbar.jsp"/>
<div class="pageWrap">
    <div class="center">
        <form action="${pageContext.request.contextPath}/mypage/bank" method="post" onsubmit="return validateForm()">


            <div class="card mb-4">
                <div class="card-body">
                    <div class="mb-3 row">
                        <label for="account" class="col-md-2 col-form-label">계좌 번호</label>
                        <div class="col-md-10">
                            <input class="form-control" type="text" id="account" placeholder="111111-11-111111"/>
                        </div>
                    </div>

                </div>
            </div>


            <div class="accordion mt-3" id="accordionExample">
                <div class="card accordion-item active">
                    <h2 class="accordion-header" id="headingOne">
                        <button
                                type="button"
                                class="accordion-button collapsed"
                                data-bs-toggle="collapse"
                                data-bs-target="#accordionOne"
                                aria-expanded="false"
                                aria-controls="accordionOne"
                        >
                            약관 동의
                        </button>
                    </h2>

                    <div
                            id="accordionOne"
                            class="accordion-collapse collapse"
                    >
                        <div class="accordion-body">
                            <p>제 1 장 총 칙



                            <hr>
                            <p>제 1 조 (목적)

                            <p>이 약관은 KB 모임통장(이하 "사이트"라 합니다)에서 제공하는 인터넷서비스(이하 "서비스"라 합니다)의 이용 조건 및 절차에 관한 기본적인 사항을 규정함을 목적으로 합니다.


                            <hr>
                            <p>제 2 조 (약관의 효력 및 변경)
                            <p>① 이 약관은 서비스 화면이나 기타의 방법으로 이용고객에게 공지함으로써 효력을 발생합니다.
                            <p>② 사이트는 이 약관의 내용을 변경할 수 있으며, 변경된 약관은 제1항과 같은 방법으로 공지 또는 통지함으로써 효력을 발생합니다.


                            <hr>
                            <p>제 3 조 (용어의 정의)
                            <p>이 약관에서 사용하는 용어의 정의는 다음과 같습니다.
                            <p>① 회원 : 사이트와 서비스 이용계약을 체결하거나 이용자 아이디(ID)를 부여받은 개인 또는 단체를 말합니다.
                            <p>② 신청자 : 회원가입을 신청하는 개인 또는 단체를 말합니다.
                            <p>③ 아이디(ID) : 회원의 식별과 서비스 이용을 위하여 회원이 정하고 사이트가 승인하는 문자와 숫자의 조합을 말합니다.
                            <p>④ 비밀번호 : 회원이 부여 받은 아이디(ID)와 일치된 회원임을 확인하고, 회원 자신의 비밀을 보호하기 위하여 회원이 정한 문자와 숫자의 조합을 말합니다.
                            <p>⑤ 해지 : 사이트 또는 회원이 서비스 이용계약을 취소하는 것을 말합니다.


                            <hr>
                            <p>제 2 장 서비스 이용계약



                            <hr>
                            <p>제 4 조 (이용계약의 성립)
                            <p>① 이용약관 하단의 동의 버튼을 누르면 이 약관에 동의하는 것으로 간주됩니다.
                            <p>② 이용계약은 서비스 이용희망자의 이용약관 동의 후 이용 신청에 대하여 사이트가 승낙함으로써 성립합니다.


                            <hr>
                            <p>제 5 조 (이용신청)
                            <p>① 신청자가 본 서비스를 이용하기 위해서는 사이트 소정의 가입신청 양식에서 요구하는 이용자 정보를 기록하여 제출해야 합니다.
                            <p>② 가입신청 양식에 기재하는 모든 이용자 정보는 모두 실제 데이터인 것으로 간주됩니다. 실명이나 실제 정보를 입력하지 않은 사용자는 법적인 보호를 받을 수 없으며, 서비스의 제한을 받을 수 있습니다.


                            <hr>
                            <p>제 6 조 (이용신청의 승낙)
                            <p>① 사이트는 신청자에 대하여 제2항, 제3항의 경우를 예외로 하여 서비스 이용신청을 승낙합니다.
                            <p>② 사이트는 다음에 해당하는 경우에 그 신청에 대한 승낙 제한사유가 해소될 때까지 승낙을 유보할 수 있습니다.
                            <p> 가. 서비스 관련 설비에 여유가 없는 경우
                            <p>나. 기술상 지장이 있는 경우
                            <p>다. 기타 사이트가 필요하다고 인정되는 경우
                            <p>③ 사이트는 신청자가 다음에 해당하는 경우에는 승낙을 거부할 수 있습니다.
                            <p>가. 다른 개인(사이트)의 명의를 사용하여 신청한 경우
                            <p>나. 이용자 정보를 허위로 기재하여 신청한 경우
                            <p>다. 사회의 안녕질서 또는 미풍양속을 저해할 목적으로 신청한 경우
                            <p>라. 기타 사이트 소정의 이용신청요건을 충족하지 못하는 경우


                            <hr>
                            <p>제 7 조 (이용자정보의 변경)
                            <p> 회원은 이용 신청시에 기재했던 회원정보가 변경되었을 경우에는, 온라인으로 수정하여야 하며 변경하지 않음으로 인하여 발생되는 모든 문제의 책임은 회원에게 있습니다.


                            <hr>
                            <p>제 3 장 계약 당사자의 의무



                            <hr>
                            <p>제 8 조 (사이트의 의무)
                            <p> ① 사이트는 회원에게 각 호의 서비스를 제공합니다.
                            <p>가. 신규서비스와 도메인 정보에 대한 뉴스레터 발송
                            <p>나. 추가 도메인 등록시 개인정보 자동 입력
                            <p>다. 도메인 등록, 관리를 위한 각종 부가서비스
                            <p>② 사이트는 서비스 제공과 관련하여 취득한 회원의 개인정보를 회원의 동의없이 타인에게 누설, 공개 또는 배포할 수 없으며, 서비스관련 업무 이외의 상업적 목적으로 사용할 수 없습니다. 단, 다음 각 호의 1에 해당하는 경우는 예외입니다.
                            <p>가. 전기통신기본법 등 법률의 규정에 의해 국가기관의 요구가 있는 경우
                            <p>나. 범죄에 대한 수사상의 목적이 있거나 정보통신윤리 위원회의 요청이 있는 경우
                            <p> 다. 기타 관계법령에서 정한 절차에 따른 요청이 있는 경우
                            <p> ③ 사이트는 이 약관에서 정한 바에 따라 지속적, 안정적으로 서비스를 제공할 의무가 있습니다.


                            <hr>
                            <p>제 9 조 (회원의 의무)
                            <p>① 회원은 서비스 이용 시 다음 각 호의 행위를 하지 않아야 합니다.
                            <p>가. 다른 회원의 ID를 부정하게 사용하는 행위
                            <p>나. 서비스에서 얻은 정보를 사이트의 사전승낙 없이 회원의 이용 이외의 목적으로 복제하거나 이를 변경, 출판 및 방송 등에 사용하거나 타인에게 제공하는 행위
                            <p>다. 사이트의 저작권, 타인의 저작권 등 기타 권리를 침해하는 행위
                            <p>라. 공공질서 및 미풍양속에 위반되는 내용의 정보, 문장, 도형 등을 타인에게 유포하는 행위
                            <p>마. 범죄와 결부된다고 객관적으로 판단되는 행위
                            <p>바. 기타 관계법령에 위배되는 행위
                            <p>② 회원은 관계법령, 이 약관에서 규정하는 사항, 서비스 이용 안내 및 주의 사항을 준수하여야 합니다.
                            <p>③ 회원은 내용별로 사이트가 서비스 공지사항에 게시하거나 별도로 공지한 이용 제한 사항을 준수하여야 합니다.


                            <hr>
                            <p>제 4 장 서비스 제공 및 이용



                            <hr>
                            <p>제 10 조 (회원 아이디(ID)와 비밀번호 관리에 대한 회원의 의무)
                            <p>① 아이디(ID)와 비밀번호에 대한 모든 관리는 회원에게 책임이 있습니다. 회원에게 부여된 아이디(ID)와 비밀번호의 관리소홀, 부정사용에 의하여 발생하는 모든 결과에 대한 전적인 책임은 회원에게 있습니다.
                            <p>② 자신의 아이디(ID)가 부정하게 사용된 경우 또는 기타 보안 위반에 대하여, 회원은 반드시 사이트에 그 사실을 통보해야 합니다.


                            <hr>
                            <p>제 11 조 (서비스 제한 및 정지)
                            <p>① 사이트는 전시, 사변, 천재지변 또는 이에 준하는 국가비상사태가 발생하거나 발생할 우려가 있는 경우와 전기통신사업법에 의한 기간통신 사업자가 전기통신서비스를 중지하는 등 기타 불가항력적 사유가 있는 경우에는 서비스의 전부 또는 일부를 제한하거나 정지할 수 있습니다.
                            <p>② 사이트는 제1항의 규정에 의하여 서비스의 이용을 제한하거나 정지할 때에는 그 사유 및 제한기간 등을 지체없이 회원에게 알려야 합니다.
                            <hr>

                                <input type="radio" id="agreement1">동의

                        </div>
                    </div>
                </div>
                <div class="card accordion-item">
                    <h2 class="accordion-header" id="headingTwo">
                        <button
                                type="button"
                                class="accordion-button collapsed"
                                data-bs-toggle="collapse"
                                data-bs-target="#accordionTwo"
                                aria-expanded="false"
                                aria-controls="accordionTwo"
                        >
                            기존 계좌 연결 해지 동의
                        </button>
                    </h2>
                    <div
                            id="accordionTwo"
                            class="accordion-collapse collapse"
                            aria-labelledby="headingTwo"
                    >
                        <div class="accordion-body">
                            본 사이트에서 제공하는 서비스에 의거하여 사용자는 단 하나의 계정만을 본인의 개인 지갑에 연결시킬 수 있습니다.
                            따라서 기존에 개인지갑과 연결되어있던 계정은 연결이 즉시 해지됨을 알립니다.
                            <hr>
                                <input type="radio" id="agreement2">동의

                        </div>
                    </div>
                </div>

            </div>
            <input type="submit" class="btn btn-primary" style="margin-top: 10px" value="계좌 연결">
        </form>










    </div>
</div>


</body>
</html>
