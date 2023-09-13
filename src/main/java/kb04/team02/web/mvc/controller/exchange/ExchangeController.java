package kb04.team02.web.mvc.controller.exchange;

import kb04.team02.web.mvc.domain.bank.Bank;
import kb04.team02.web.mvc.domain.common.CurrencyCode;
import kb04.team02.web.mvc.domain.member.Role;
import kb04.team02.web.mvc.domain.wallet.common.WalletType;
import kb04.team02.web.mvc.dto.BankDto;
import kb04.team02.web.mvc.dto.ExchangeDto;
import kb04.team02.web.mvc.dto.OfflineReceiptDto;
import kb04.team02.web.mvc.dto.WalletDto;
import kb04.team02.web.mvc.service.exchange.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    /**
     * 환전 메인 페이지
     * API 명세서 ROWNUM:41
     */
    @GetMapping("/exchange/exchange")
    public void exchangeIndex() {
    }

    /**
     * 오프라인 환전 메인 페이지
     * API 명세서 ROWNUM:42
     */
    @GetMapping("exchange/offline")
    public String exchangeOfflineIndex(HttpSession session, Model model) {
        // session에서 모임지갑 seq, 개인지갑 seq 가져와야 함
        // Map<Long, Role> groupWalletIdList
        Map<Long, Role> map = new HashMap<>(); // getSession
        Long personalWalletId = 0L; // getSession

        exchangeService.offlineReceiptHistory(personalWalletId, map);
        return "exchange/offlineExchange";
    }

    /**
     * 오프라인 환전 폼
     * API 명세서 ROWNUM:43
     */
    @GetMapping("/offline/form")
    public String exchangeOfflineForm(HttpSession session, Model model) {
        List<BankDto> bankList = exchangeService.bankList();
        List<WalletDto> WalletList = exchangeService.WalletList(0L); // 지갑 리스트 - 전달인수 세션으로 수정할 것

        model.addAttribute("bankList", bankList);
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
    public String exchangeOfflineCancel(Long receipt_id) {
        int res = exchangeService.cancelOfflineReceipt(receipt_id);
        return "redirect:/exchange/offline";
    }

    /**
     * 온라인 환전 메인 페이지
     * API 명세서 ROWNUM:46
     */
    @GetMapping("/exchange/onlineExchange")
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
    public ExchangeDto expectedAmount(){
        return exchangeService.expectedExchangeAmount(CurrencyCode.USD, 1000L);
    }

}
