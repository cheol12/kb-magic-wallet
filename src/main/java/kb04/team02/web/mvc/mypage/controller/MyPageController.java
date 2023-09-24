package kb04.team02.web.mvc.mypage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {"마이페이지 API"})
public class MyPageController {

    private final MyPageService myPageService;

    private final PersonalWalletService personalWalletService;
    private final GroupWalletService groupWalletService;



    /**
     * 마이 페이지
     * API 명세서 ROWNUM:49
     */
    @GetMapping("/main")
    @ApiOperation(value = "마이페이지", notes="마이페이지입니다.")
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
    @ApiOperation(value = "카드 신청 폼", notes="카드 신청 폼입니다.")
    public void cardForm() {
    }

    /**
     * 마이 페이지 - 카드 신청
     * API 명세서 ROWNUM:51
     */
    @GetMapping("/card/new")
    @ApiOperation(value = "카드 신청", notes="카드를 신청합니다.")
    public String cardCreate(HttpSession session) {
        LoginMemberDto loggedIn = (LoginMemberDto) session.getAttribute("member");
        myPageService.createCard(loggedIn);
        session.setAttribute("alertMessage", "카드 신청이 완료 되었습니다");

        return "redirect:/mypage/mycard";
    }

    /**
     * 마이 페이지 - 카드 분실
     * API 명세서 ROWNUM:52
     */
    @GetMapping("/card/delete")
    @ApiOperation(value = "카드 정지 요청", notes="카드를 정지합니다.")
    public String cardInvalidate(HttpSession session) {
        LoginMemberDto loggedIn = (LoginMemberDto) session.getAttribute("member");
        myPageService.invalidateCard(loggedIn);
        session.setAttribute("alertMessage", "카드 정지 신청이 완료되었습니다");

        return "redirect:/mypage/mycard";
    }

    /**
     * 마이 페이지 - 은행 계좌 연결 폼
     * API 명세서 ROWNUM:53
     */
    @GetMapping("/bankForm")
    @ApiOperation(value = "은행 계좌 연결 폼", notes="은행 계좌 연결 폼으로 이동합니다.")
    public void bankLinkForm() {
    }

    /**
     * 마이 페이지 - 은행 계좌 연결 요청
     * API 명세서 ROWNUM:54
     */
    @PostMapping("/bank")
    @ApiOperation(value = "은행 계좌 연결 요청", notes="은행 계좌 연결을 요청합니다.")
    public String bankLink(@RequestParam("account") String account, HttpSession session) {
        LoginMemberDto loggedIn = (LoginMemberDto) session.getAttribute("member");
        myPageService.linkAccount(loggedIn, account);
        return "mypage/main";
    }

    /**
     * 마이페이지 - 카드 일시 정지, 재개
     * TODO API 명세서 추가
     */
    @GetMapping("/card/stop")
    @ApiOperation(value = "카드 일시정지 요청", notes="카드 일시정지를 요청합니다.")
    public String cardPause(HttpSession session) {
        LoginMemberDto loggedIn = (LoginMemberDto) session.getAttribute("member");
        myPageService.pauseCard(loggedIn);
        session.setAttribute("alertMessage", "카드 일시정지 신청이 완료 되었습니다");

        return "redirect:/mypage/mycard";
    }

    /**
     * 마이페이지 - 카드 다시 시작
     * TODO API 명세서 추가
     */
    @GetMapping("/card/restart")
    @ApiOperation(value = "카드 재시작", notes="카드 재시작을 요청합니다.")
    public String cardResume(HttpSession session) {
        LoginMemberDto loggedIn = (LoginMemberDto) session.getAttribute("member");
        myPageService.resumeCard(loggedIn);
        session.setAttribute("alertMessage", "카드 사용이 재개 되었습니다");

        return "redirect:/mypage/mycard";
    }

    /**
     * 마이 페이지 - 카드 번호, 상태
     * TODO API 명세서 추가
     */
    @GetMapping("/mycard")
    @ResponseBody
    @ApiOperation(value = "카드 번호 요청", notes="카드 번호를 요청합니다.")
    public ModelAndView cardNumber(HttpSession session) {
        LoginMemberDto member = (LoginMemberDto) session.getAttribute("member");
        CardNumberDto cardNumber = myPageService.getCardNumber(member);
        char cardDesign = cardNumber.getCardNumber().charAt(18);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forward:/mypage/cardForm");
        modelAndView.addObject("cardNumber", cardNumber);
        modelAndView.addObject("cardDesign", cardDesign);

        return modelAndView;
    }

    /**
     * 마이 페이지 - 은행 계좌 조회
     * TODO API 명세서 추가
     */
    @GetMapping("/mybank")
    @ResponseBody
    @ApiOperation(value = "은행 계좌 조회 요청", notes="은행 계좌 조회를 요청합니다.")
    public String bankAccount(HttpSession session) {
        LoginMemberDto member = (LoginMemberDto) session.getAttribute("member");
        return myPageService.getBankAccount(member);
    }
}
