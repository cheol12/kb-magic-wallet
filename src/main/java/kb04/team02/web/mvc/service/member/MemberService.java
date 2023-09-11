package kb04.team02.web.mvc.service.member;

import kb04.team02.web.mvc.dto.UserLoginDto;
import kb04.team02.web.mvc.dto.UserRegisterDto;
import kb04.team02.web.mvc.dto.UserSession;

public interface MemberService {


    /**
     * 회원가입 정보를 받아서 회원가입
     * 일단 성공, 실패 나누지 않음
     *
     * @param userRegisterDto
     */
    void register(UserRegisterDto userRegisterDto);

    /**
     * 로그인
     *
     * @param userLoginDto id, password
     * @return 로그인 되면 UserSession 정보 채워서 반환, 안되면 null 반환
     */
    UserSession login(UserLoginDto userLoginDto);
}
