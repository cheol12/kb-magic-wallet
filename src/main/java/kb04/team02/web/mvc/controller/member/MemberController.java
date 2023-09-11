package kb04.team02.web.mvc.controller.member;

import kb04.team02.web.mvc.dto.MemberLoginDto;
import kb04.team02.web.mvc.dto.MemberRegisterDto;
import kb04.team02.web.mvc.dto.LoginMemberDto;
import kb04.team02.web.mvc.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String login(@RequestBody MemberLoginDto memberLoginDto, HttpSession session) {
        LoginMemberDto loginMember = memberService.login(memberLoginDto);

        if (loginMember == null) {
            return "index";
        }

        session.setAttribute("member", loginMember);
        return "forward:/";
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
    public void registerForm() {
    }

    /**
     * 회원가입
     *
     * @return
     */
    @PostMapping("/register")
    public String register(@RequestBody MemberRegisterDto memberRegisterDto) {

        memberService.register(memberRegisterDto);

        return "redirect:/";
    }
}
