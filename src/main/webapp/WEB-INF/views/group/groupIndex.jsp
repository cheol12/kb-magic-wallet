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
            <div>
                <article>
                    모임 지갑 설명 img, content
                </article>

            </div>

            <div>
                모임지갑 리스트<br>


                <c:forEach var="list" varStatus="status" items="${gWalletList}">
                    <section>
                        <div class="groupWalletCard" onclick="location.href='/group-wallet/'">
                            지갑 정보들
                        </div>
                    </section>
                </c:forEach>
                <section>
                    <div class="groupWalletCard" onclick="location.href='/group-wallet/new'">
                        생성 폼
                    </div>
                </section>
            </div>
        </div>

    </div>
</main>


<footer>

</footer>
</body>
</html>
