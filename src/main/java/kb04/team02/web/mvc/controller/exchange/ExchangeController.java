package kb04.team02.web.mvc.controller.exchange;

import kb04.team02.web.mvc.service.exchange.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public void exchangeOfflineIndex() {
    }

    /**
     * 오프라인 환전 폼
     * API 명세서 ROWNUM:43
     */
    @GetMapping("/offline/form")
    public void exchangeOfflineForm() {
    }

    /**
     * 오프라인 환전 요청
     * API 명세서 ROWNUM:44
     */
    @PostMapping("/offline/form")
    public void exchangeOffline() {
    }

    /**
     * 오프라인 환전 취소 요청
     * API 명세서 ROWNUM:45
     */
    @ResponseBody
    @DeleteMapping("/offline/form")
    public void exchangeOfflineCancel() {
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
    }

    /**
     * 오프라인 환전 요청
     * API 명세서 ROWNUM:48
     */
    @PostMapping("/online/form")
    public void exchangeOnline() {
    }

}
