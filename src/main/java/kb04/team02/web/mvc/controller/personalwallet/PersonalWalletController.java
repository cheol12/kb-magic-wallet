package kb04.team02.web.mvc.controller.personalwallet;

import kb04.team02.web.mvc.dto.LoginMemberDto;
import kb04.team02.web.mvc.service.personalwallet.PersonalWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/personal-wallet")
@RequiredArgsConstructor
public class PersonalWalletController {

    private final PersonalWalletService userService;

    /**
     * 개인지갑 메인 페이지
     * API 명세서 ROWNUM:5
     */
    @GetMapping("/")
    public ModelAndView personalwalletIndex(HttpSession session) {
        LoginMemberDto member = (LoginMemberDto) session.getAttribute("member");

        return new ModelAndView("personal-wallet");
    }

    /**
     * 개인지갑 충전 폼
     * API 명세서 ROWNUM:6
     */
    @GetMapping("/deposit")
    public void personalwalletDepositForm() {
    }

    /**
     * 개인지갑 충전 요청
     * API 명세서 ROWNUM:7
     */
    @PostMapping("/deposit")
    public String personalwalletDeposit() {
        //userService.depositPersonalWallet();

        return "personal-wallet";
    }

    /**
     * 개인지갑 환불 폼
     * API 명세서 ROWNUM:8
     */
    @GetMapping("/withdraw")
    public void personalwalletWithdrawForm() {
    }

    /**
     * 개인지갑 환불 요청
     * API 명세서 ROWNUM:9
     */
    @PostMapping("/withdraw")
    public String personalwalletWithdraw() {
        //userService.cancleWithDraw();
        return "personal-wallet";
    }
}
