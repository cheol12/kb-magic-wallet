package kb04.team02.web.mvc.member.controller;

import kb04.team02.web.mvc.member.dto.MemberLoginDto;
import kb04.team02.web.mvc.member.dto.MemberRegisterDto;
import kb04.team02.web.mvc.common.dto.LoginMemberDto;
import kb04.team02.web.mvc.member.exception.LoginException;
import kb04.team02.web.mvc.member.exception.RegisterException;
import kb04.team02.web.mvc.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@SessionAttributes("member")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 로그인
     */
    @PostMapping("/login")
    public String login(MemberLoginDto memberLoginDto, HttpSession session) {
        try {
            LoginMemberDto loggedIn = memberService.login(memberLoginDto);
            session.setAttribute("member", loggedIn);
        } catch (LoginException e) {
            return "forward:/";
        }

        return "mypage/main";
    }

    /**
     * 로그아웃
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        //모든 세션의 정보를 삭제한다.
        session.invalidate();
        return "redirect:/";
    }

    /**
     * 회원가입 폼
     */
    @GetMapping("/register")
    public String registerForm() {
        return "member/register";
    }

    /**
     * 회원가입
     *
     * @return /로 이동
     */
    @PostMapping("/register")
//    public String register(@RequestBody MemberRegisterDto memberRegisterDto) {
    public String register(MemberRegisterDto memberRegisterDto) {
        System.out.println("memberRegisterDto = " + memberRegisterDto);
        try {
            memberService.register(memberRegisterDto);
        } catch (RegisterException e) {
            return "forward:/register";
        }

        return "redirect:/";
    }
}
