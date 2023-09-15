package kb04.team02.web.mvc.mypage.controller;

import kb04.team02.web.mvc.mypage.dto.CardNumberDto;
import kb04.team02.web.mvc.common.dto.LoginMemberDto;
import kb04.team02.web.mvc.mypage.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    /**
     * 마이 페이지
     * API 명세서 ROWNUM:49
     */
    @GetMapping("/main")
    public void mypageIndex() {
    }

    /**
     * 마이 페이지 - 카드 신청 폼
     * API 명세서 ROWNUM:50
     */
    @GetMapping("/cardForm")
    public void cardForm() {
    }

    /**
     * 마이 페이지 - 카드 신청
     * API 명세서 ROWNUM:51
     */
    @PostMapping("/card")
    public String cardCreate(HttpSession session) {
        LoginMemberDto loggedIn = (LoginMemberDto) session.getAttribute("member");
        myPageService.createCard(loggedIn);
        return "mypage/main";
    }

    /**
     * 마이 페이지 - 카드 분실
     * API 명세서 ROWNUM:52
     */
    @DeleteMapping("/card")
    public String cardInvalidate(HttpSession session) {
        LoginMemberDto loggedIn = (LoginMemberDto) session.getAttribute("member");
        myPageService.invalidateCard(loggedIn);
        return "mypage/main";
    }

    /**
     * 마이 페이지 - 은행 계좌 연결 폼
     * API 명세서 ROWNUM:53
     */
    @GetMapping("/bankForm")
    public void bankLinkForm() {
    }

    /**
     * 마이 페이지 - 은행 계좌 연결 요청
     * API 명세서 ROWNUM:54
     */
    @PostMapping("/bank")
    public String bankLink(@RequestBody String account, HttpSession session) {
        LoginMemberDto loggedIn = (LoginMemberDto) session.getAttribute("member");
        myPageService.linkAccount(loggedIn, account);
        return "mypage/main";
    }

    /**
     * 마이페이지 - 카드 일시 정지, 재개
     * TODO API 명세서 추가
     */
    @PatchMapping("/card/stop")
    public String cardPause(HttpSession session) {
        LoginMemberDto loggedIn = (LoginMemberDto) session.getAttribute("member");
        myPageService.pauseCard(loggedIn);
        return "mypage/main";
    }

    /**
     * 마이페이지 - 카드 다시 시작
     * TODO API 명세서 추가
     */
    @PatchMapping("/card/restart")
    public String cardResume(HttpSession session) {
        LoginMemberDto loggedIn = (LoginMemberDto) session.getAttribute("member");
        myPageService.resumeCard(loggedIn);
        return "mypage/main";
    }

    /**
     * 마이 페이지 - 카드 번호, 상태
     * TODO API 명세서 추가
     */
    @GetMapping("/mycard")
    @ResponseBody
    public CardNumberDto cardNumber(HttpSession session) {
        LoginMemberDto member = (LoginMemberDto) session.getAttribute("member");
        return myPageService.getCardNumber(member);
    }

    /**
     * 마이 페이지 - 은행 계좌 조회
     * TODO API 명세서 추가
     */
    @GetMapping("/mybank")
    @ResponseBody
    public String bankAccount(HttpSession session) {
        LoginMemberDto member = (LoginMemberDto) session.getAttribute("member");
        return myPageService.getBankAccount(member);
    }
}
