<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--로그인 모달 창-->
<div class="modal fade" id="loginModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header" style="text-align: center; margin: 0 auto;">
                <h2 class="modal-title" id="exampleModalLabel3">LOGIN</h2>
            </div>

            <div class="modal-body" style="text-align: center; margin: 0 auto;">
                <div class="row">
                    <div class="mb-3">
                        <label class="col-form-label" for="login_id">
                            <h5>
                                아이디
                            </h5>
                        </label>
                        <input name="id" type="text" class="form-control" id="login_id" placeholder="id"/>
                    </div>
                </div>
                <div class="row">
                    <div class="mb-3">
                        <label class="col-form-label" for="login_pw">
                            <h5>
                                비밀번호
                            </h5>
                        </label>
                        <input
                                name="password"
                                type="password"
                                class="form-control"
                                id="login_pw"
                                placeholder="password"
                        />
                    </div>
                </div>
            </div>
            <div class="modal-footer" style="text-align: center; margin: 0 auto;">
                <button type="button" class="btn btn-primary" id="loginButton">
                    로그인
                </button>
            </div>
            <div class="text-center" style="margin-bottom: 15px">
                회원이 아니신가요? <a href="${pageContext.request.contextPath}/register">회원가입</a>
            </div>
        </div>
    </div>
</div>

<!--로그인 실패 창-->
<div class="modal fade" id="loginFail" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title">로그인 실패</h3>
            </div>
            <div class="modal-body">
                <div class="row text-center">
                    <h4>
                        아이디, 비밀번호를 확인해주세요.
                    </h4>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">확인</button>
            </div>
        </div>
    </div>
</div>