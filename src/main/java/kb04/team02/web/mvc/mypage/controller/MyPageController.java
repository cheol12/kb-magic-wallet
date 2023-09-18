package kb04.team02.web.mvc.mypage.controller;

import kb04.team02.web.mvc.common.dto.WalletDetailDto;
import kb04.team02.web.mvc.group.entity.GroupWallet;
import kb04.team02.web.mvc.group.service.GroupWalletService;
import kb04.team02.web.mvc.mypage.dto.CardNumberDto;
import kb04.team02.web.mvc.common.dto.LoginMemberDto;
import kb04.team02.web.mvc.mypage.service.MyPageService;
import kb04.team02.web.mvc.personal.service.PersonalWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    private final PersonalWalletService personalWalletService;
    private final GroupWalletService groupWalletService;



    /**
     * 마이 페이지
     * API 명세서 ROWNUM:49
     */
    @GetMapping("/main")
    public ModelAndView mypageIndex(HttpSession session) {

        LoginMemberDto member = (LoginMemberDto) session.getAttribute("member");
        WalletDetailDto walletDetailDto = personalWalletService.personalWallet(member);

        List<GroupWallet> gWalletList = groupWalletService.selectAllMyGroupWallet(member);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mypage/main");
        modelAndView.addObject("walletDetailDto", walletDetailDto);
        modelAndView.addObject("gWalletList", gWalletList);

        return modelAndView;
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
    @GetMapping("/card/new")
    public String cardCreate(HttpSession session) {
        LoginMemberDto loggedIn = (LoginMemberDto) session.getAttribute("member");
        System.out.println("================");
        myPageService.createCard(loggedIn);
        return "mypage/main";
    }

    /**
     * 마이 페이지 - 카드 분실
     * API 명세서 ROWNUM:52
     */
    @GetMapping("/card/delete")
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
    public String bankLink(@RequestParam("account") String account, HttpSession session) {
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
    @GetMapping("/card/restart")
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
    public ModelAndView cardNumber(HttpSession session) {
        LoginMemberDto member = (LoginMemberDto) session.getAttribute("member");
        CardNumberDto cardNumber = myPageService.getCardNumber(member);
        return new ModelAndView("mypage/cardForm", "cardNumber", cardNumber);
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
