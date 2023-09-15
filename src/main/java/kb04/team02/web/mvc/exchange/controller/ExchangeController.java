package kb04.team02.web.mvc.exchange.controller;

import kb04.team02.web.mvc.common.dto.LoginMemberDto;
import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.exchange.dto.WalletDto;
import kb04.team02.web.mvc.exchange.dto.BankDto;
import kb04.team02.web.mvc.exchange.dto.ExchangeCalDto;
import kb04.team02.web.mvc.exchange.dto.ExchangeDto;
import kb04.team02.web.mvc.exchange.dto.OfflineReceiptDto;
import kb04.team02.web.mvc.exchange.entity.OfflineReceipt;
import kb04.team02.web.mvc.member.entity.Role;
import kb04.team02.web.mvc.common.entity.WalletType;
import kb04.team02.web.mvc.exchange.exception.ExchangeException;
import kb04.team02.web.mvc.common.exception.InsertException;
import kb04.team02.web.mvc.exchange.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    /**
     * 환전 메인 페이지
     * API 명세서 ROWNUM:41
     */
    @GetMapping("/")
    public String exchangeIndex() {
        return "exchange/exchange";
    }

    /**
     * 오프라인 환전 메인 페이지
     * API 명세서 ROWNUM:42
     */
    @GetMapping("/offline")
    public String exchangeOfflineIndex(HttpSession session, Model model) {
        LoginMemberDto loggedIn = (LoginMemberDto) session.getAttribute("member");
        Map<Long, Role> map = loggedIn.getGroupWalletIdList();
        Long personalWalletId = loggedIn.getPersonalWalletId();
        List<OfflineReceiptDto> offlineExchangeHistoryList = exchangeService.offlineReceiptHistory(personalWalletId, map);
        model.addAttribute("offlineExchangeHistoryList", offlineExchangeHistoryList);
        return "exchange/offlineExchange";
    }

    /**
     * 오프라인 환전 폼
     * API 명세서 ROWNUM:43
     */
    @GetMapping("/offline/form")
    public String exchangeOfflineForm(HttpSession session, Model model) {
        LoginMemberDto loggedIn = (LoginMemberDto) session.getAttribute("member");
        Long memberId = loggedIn.getMemberId();
        List<BankDto> bankList = exchangeService.bankList();
        List<WalletDto> WalletList = exchangeService.WalletList(memberId); // 지갑 리스트 - 전달인수 세션으로 수정할 것

        model.addAttribute("bankList", bankList);
        model.addAttribute("WalletList", WalletList);
        return "exchange/exchangeOfflineForm";
    }

    /**
     * 오프라인 환전 요청
     * API 명세서 ROWNUM:44
     */
    @PostMapping("/offline/form")
    public String exchangeOffline(OfflineReceiptDto offlineReceiptDto) {
        int result = exchangeService.requestOfflineReceipt(offlineReceiptDto);
        return "redirect:/exchange/offline";
    }

    /**
     * 오프라인 환전 취소 요청
     * API 명세서 ROWNUM:45
     */
    @DeleteMapping("/offline/form")
    @ResponseBody
    public String exchangeOfflineCancel(@RequestParam String offlineReceiptId) {
        int res = exchangeService.cancelOfflineReceipt(Long.parseLong(offlineReceiptId));
        return "success";
    }

    /**
     * 온라인 환전 메인 페이지
     * API 명세서 ROWNUM:46
     */
    @GetMapping("/onlineExchange")
    public void exchangeOnlineIndex() {
    }

    /**
     * 온라인 환전 폼
     * API 명세서 ROWNUM:47
     */
    @GetMapping("/online/form")
    public String exchangeOnlineForm(HttpSession session, Model model) {
        List<WalletDto> walletList = exchangeService.WalletList(0L);
        model.addAttribute("walletList", walletList);
        return "exchange/exchangeOnlineForm";
    }

    /**
     * 온라인 환전 요청
     * API 명세서 ROWNUM:48
     */
    @PostMapping("/online/form")
    public String exchangeOnline(ExchangeDto exchangeDto) {
        exchangeService.requestExchangeOnline(exchangeDto);
        // 어디로 가야 하죠...
        return null;
    }

    /**
     * 선택한 지갑의 잔액 요청
     * API 명세서 ROWNUM: 55
     * @param walletId
     * @return
     */
    @ResponseBody
    @PostMapping("/walletBalance")
    public Long selectedWalletBalance(Long walletId, WalletType walletType){
        return exchangeService.selectedWalletBalance(walletId, walletType);
    }

    /**
     * 선택한 통화와 입력 금액에 대한 환전 예상 금액 요청
     * API 명세서: ROWNUM 56
     */
    @ResponseBody
    @PostMapping("/expectedAmount")
    public ExchangeCalDto expectedAmount(){
        return exchangeService.expectedExchangeAmount(CurrencyCode.USD, 1000L);
    }

    //== 예외 처리 ==/
    @ExceptionHandler({ExchangeException.class, InsertException.class, NoSuchElementException.class})
    public String exchangeException(Exception e) {
        System.out.println(e.getMessage());
        return "error";
    }
}
