package kb04.team02.web.mvc.controller.exchange;

import kb04.team02.web.mvc.domain.bank.Bank;
import kb04.team02.web.mvc.domain.member.Role;
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
    @GetMapping("/")
    public void exchangeIndex() {
    }

    /**
     * 오프라인 환전 메인 페이지
     * API 명세서 ROWNUM:42
     */
    @GetMapping("/offline")
    public String exchangeOfflineIndex(HttpSession session, Model model) {
        // session에서 모임지갑 seq, 개인지갑 seq 가져와야 함
        // Map<Long, Role> groupWalletIdList
        Map<Long, Role> map = new HashMap<>(); // getSession
        Long personalWalletId = 0L; // getSession

        exchangeService.offlineReceiptHistory(personalWalletId, map);
        return null;
    }

    /**
     * 오프라인 환전 폼
     * API 명세서 ROWNUM:43
     */
    @GetMapping("/offline/form")
    public String exchangeOfflineForm(Model model) {
        List<BankDto> bankList = exchangeService.bankList();
        List<WalletDto> chairManWalletList = exchangeService.chairManWalletList();
        // chairManWalletList 권한 걸러주고 개인지갑 추가해서 view로 넘겨줘야 함

        model.addAttribute("bankList", bankList);
        return "exchange/offline/form";
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
    @GetMapping("/online")
    public void exchangeOnlineIndex() {
    }

    /**
     * 온라인 환전 폼
     * API 명세서 ROWNUM:47
     */
    @GetMapping("/online/form")
    public void exchangeOnlineForm() {
        List<WalletDto> chairManWalletList = exchangeService.chairManWalletList();
        // chairManWalletList 권한 걸러주고 개인지갑 추가해서 view로 넘겨줘야 함
    }

    /**
     * 온라인 환전 요청
     * API 명세서 ROWNUM:48
     */
    @PostMapping("/online/form")
    public String exchangeOnline(ExchangeDto exchangeDto) {
        exchangeService.requestExchangeOnline(exchangeDto);
        return null;
    }

}
