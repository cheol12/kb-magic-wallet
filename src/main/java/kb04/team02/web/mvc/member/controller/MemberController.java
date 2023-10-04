package kb04.team02.web.mvc.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kb04.team02.web.mvc.member.dto.MemberLoginDto;
import kb04.team02.web.mvc.member.dto.MemberRegisterDto;
import kb04.team02.web.mvc.common.dto.LoginMemberDto;
import kb04.team02.web.mvc.member.exception.LoginException;
import kb04.team02.web.mvc.member.exception.RegisterException;
import kb04.team02.web.mvc.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Api(tags = {"멤버 API"})
public class MemberController {

    private final MemberService memberService;

    /**
     * 로그인
     */
    @PostMapping("/login")
    @ResponseBody
    @ApiOperation(value = "로그인", notes="아이디와 비밀번호를 입력받아 로그인합니다.")
    public String login(MemberLoginDto memberLoginDto, HttpSession session) {
        System.out.println("memberLoginDto.getId() = " + memberLoginDto.getId());
        System.out.println("memberLoginDto.getPassword() = " + memberLoginDto.getPassword());
        try {
            LoginMemberDto loggedIn = memberService.login(memberLoginDto);
            session.setAttribute("member", loggedIn);
        } catch (LoginException e) {
            return "fail";
        }

        return "success";
    }

    @GetMapping("/login")
    @ApiOperation(value = "로그인 페이지", notes="로그인 페이지로 이동합니다.")
    public String login(){
        return "mypage/loginForm";
    }

    /**
     * 로그아웃
     */
    @GetMapping("/logout")
    @ApiOperation(value = "로그아웃", notes="로그아웃입니다.")
    public String logout(HttpSession session) {
        //모든 세션의 정보를 삭제한다.
        session.invalidate();
        return "redirect:/";
    }

    /**
     * 회원가입 폼
     */
    @GetMapping("/register")
    @ApiOperation(value = "회원가입 페이지", notes="회원가입 페이지로 이동합니다.")
    public String registerForm() {
        return "member/register";
    }

    /**
     * 회원가입
     *
     * @return /로 이동
     */
    @PostMapping("/register")
    @ApiOperation(value = "회원가입", notes="MemberRegisterDto를 입력받아 회원가입을 진행합니다.")
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

    @ResponseBody
    @PostMapping("/verification")
    @ApiOperation(value = "결제 비밀번호 확인", notes="결제 비밀번호를 입력받아 확인합니다.")
    public ResponseEntity<?> PayPasswordVerification(String payPassword, HttpSession session) {
        LoginMemberDto member = (LoginMemberDto) session.getAttribute("member");
        try {
            memberService.verify(member, payPassword);
            return ResponseEntity.ok("비밀 번호 확인 완료");
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("비밀 번호 불일치");
        }
    }
}
