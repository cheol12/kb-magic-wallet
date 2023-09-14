package kb04.team02.web.mvc.controller.personalwallet;

import kb04.team02.web.mvc.domain.member.Member;
import kb04.team02.web.mvc.dto.LoginMemberDto;
import kb04.team02.web.mvc.dto.PersonalWalletTransferDto;
import kb04.team02.web.mvc.dto.WalletDetailDto;
import kb04.team02.web.mvc.exception.InsufficientBalanceException;
import kb04.team02.web.mvc.service.personalwallet.PersonalWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/personalwallet")
@RequiredArgsConstructor
public class PersonalWalletController {

    private final PersonalWalletService personalWalletService;

    /**
     * 개인지갑 메인 페이지
     * API 명세서 ROWNUM:5
     */
    @GetMapping("/main")
    public ModelAndView personalwalletIndex(HttpSession session) {
        LoginMemberDto member = (LoginMemberDto) session.getAttribute("member");
        WalletDetailDto walletDetailDto = personalWalletService.personalWallet(member);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("personalwallet/personal-wallet");
        modelAndView.addObject("walletDetailDto", walletDetailDto);

        return modelAndView;
    }

    /**
     * 개인지갑 충전 폼
     * API 명세서 ROWNUM:6
     */
    @GetMapping("/depositForm")
    public void personalwalletDepositForm() {
    }

    /**
     * 개인지갑 충전 요청
     * API 명세서 ROWNUM:7
     */
    @PostMapping("/deposit")
    public String personalwalletDeposit(PersonalWalletTransferDto transferDto, HttpSession session) {
        LoginMemberDto member = (LoginMemberDto) session.getAttribute("member");
        transferDto.setMemberId(member.getMemberId());

        try {
            personalWalletService.personalWalletDeposit(transferDto);
        } catch (NoSuchElementException e) {
            // TODO 개인지갑 충전 실패시 어디가지?
        }

        return "redirect:/personalwallet/main";
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
    public String personalwalletWithdraw(PersonalWalletTransferDto transferDto) {

        try {
            personalWalletService.personalWalletWithdraw(transferDto);
        } catch (NoSuchElementException e) {
        } catch (InsufficientBalanceException e) {
            // TODO 개인지갑 환불 실패시 어디가지?
        }

        return "personalwallet/main";
    }
}
