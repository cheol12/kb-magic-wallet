package kb04.team02.web.mvc.controller.member;

import kb04.team02.web.mvc.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 로그인
     * */
    @RequestMapping("/login")
    public void login() {}

    /**
     * 로그아웃
     * */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        //모든 세션의 정보를 삭제한다.
        session.invalidate();
        return "redirect:/";
    }

    /**
     * 회원가입
     */
    @RequestMapping("/register")
    @ResponseBody
    public void register() {}


}
