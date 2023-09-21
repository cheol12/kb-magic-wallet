package kb04.team02.web.mvc.personal.controller;

import kb04.team02.web.mvc.common.dto.LoginMemberDto;
import kb04.team02.web.mvc.common.dto.WalletHistoryDto;
import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.exchange.dto.ExchangeCalDto;
import kb04.team02.web.mvc.exchange.dto.ExchangeRateDto;
import kb04.team02.web.mvc.exchange.service.ExchangeService;
import kb04.team02.web.mvc.group.dto.GroupMemberDto;
import kb04.team02.web.mvc.mypage.dto.CardNumberDto;
import kb04.team02.web.mvc.mypage.service.CardIssuanceService;
import kb04.team02.web.mvc.mypage.service.MyPageService;
import kb04.team02.web.mvc.personal.dto.PersonalWalletTransferDto;
import kb04.team02.web.mvc.common.dto.WalletDetailDto;
import kb04.team02.web.mvc.common.exception.InsufficientBalanceException;
import kb04.team02.web.mvc.personal.service.PersonalWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/personalwallet")
@RequiredArgsConstructor
public class PersonalWalletController {

    private final PersonalWalletService personalWalletService;
    private final ExchangeService exchangeService;
    private final MyPageService myPageService;
    private final CardIssuanceService cardIssuanceService;


    /**
     * 개인지갑 메인 페이지
     * API 명세서 ROWNUM:5
     */
    @GetMapping("/main")
    public ModelAndView personalwalletIndex(HttpSession session) {
        LoginMemberDto member = (LoginMemberDto) session.getAttribute("member");
        WalletDetailDto walletDetailDto = personalWalletService.personalWallet(member);
        ExchangeCalDto usd = exchangeService.expectedExchangeAmount(CurrencyCode.USD, walletDetailDto.getBalance().get("USD"));
        ExchangeCalDto jpy = exchangeService.expectedExchangeAmount(CurrencyCode.JPY, walletDetailDto.getBalance().get("JPY"));
        Long surplus = personalWalletService.personalWalletCalSurplus(member);
        CardNumberDto cardNumber = myPageService.getCardNumber(member);

        boolean connectToWallet = cardIssuanceService.isConnectToPersonalWallet(member.getMemberId());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("personalwallet/personal-wallet");
        modelAndView.addObject("walletDetailDto", walletDetailDto);
        modelAndView.addObject("connected", connectToWallet);
        modelAndView.addObject("usdDto", usd);
        modelAndView.addObject("jpyDto", jpy);

        modelAndView.addObject("cardNumber", cardNumber);

        modelAndView.addObject("surplus", surplus);

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
    @GetMapping("/withdrawForm")
    public void personalwalletWithdrawForm(Model model, HttpSession session) {
        LoginMemberDto member = (LoginMemberDto) session.getAttribute("member");
        WalletDetailDto walletDetailDto = personalWalletService.personalWallet(member);

        model.addAttribute("balance", walletDetailDto.getBalance());
    }

    /**
     * 개인지갑 환불 요청
     * API 명세서 ROWNUM:9
     */
    @PostMapping("/withdraw")
    public String personalwalletWithdraw(PersonalWalletTransferDto transferDto, HttpSession session) {
        LoginMemberDto member = (LoginMemberDto) session.getAttribute("member");
        transferDto.setMemberId(member.getMemberId());
        try {
            personalWalletService.personalWalletWithdraw(transferDto);
        } catch (NoSuchElementException e) {
        } catch (InsufficientBalanceException e) {
            // TODO 개인지갑 환불 실패시 어디가지?
        }

        return "redirect:/personalwallet/main";
    }

    @ResponseBody
    @PostMapping("/selectDate")
    public List<WalletHistoryDto> selectDate(HttpSession session, Model model) {
        LoginMemberDto member = (LoginMemberDto) session.getAttribute("member");
        WalletDetailDto walletDetailDto = personalWalletService.personalWallet(member);
        model.addAttribute("walletDetailDto", walletDetailDto);
        System.out.println(model.getAttribute("startDate"));
        System.out.println(model.getAttribute("endDate"));
        System.out.println("===============");
        return walletDetailDto.getList();
    }

    @ResponseBody
    @GetMapping("/cardStatusUpdate")
    public boolean changeCardConnection(Model model, HttpSession session) {
        System.out.println("qqqqqqqqqqqqqqqqqqqqqqqq");
        LoginMemberDto member = (LoginMemberDto) session.getAttribute("member");
        cardIssuanceService.createPersonalWalletCardConnection(member.getMemberId());
        return true;
    }
}
