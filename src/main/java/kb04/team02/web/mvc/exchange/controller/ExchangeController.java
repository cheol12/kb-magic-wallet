package kb04.team02.web.mvc.exchange.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kb04.team02.web.mvc.common.dto.LoginMemberDto;
import kb04.team02.web.mvc.common.dto.WalletDetailDto;
import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.exchange.dto.*;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/exchange")
@RequiredArgsConstructor
@Api(tags = {"환전 API"})
public class ExchangeController {

    private final ExchangeService exchangeService;

    /**
     * 환전 메인 페이지
     * API 명세서 ROWNUM:41
     */
    @GetMapping("/")
    @ApiOperation(value = "환전 메인 페이지", notes="환전 메인 페이지이며 환율 정보를 반환합니다.")
    public ModelAndView exchangeIndex() {
        ModelAndView modelAndView = new ModelAndView();
        List<String> usdRate = exchangeService.selectExchangeRateByCurrencyCode(CurrencyCode.USD);
        List<String> jpyRate = exchangeService.selectExchangeRateByCurrencyCode(CurrencyCode.JPY);
        List<Double> predictedExchangeRates = exchangeService.getPredictedExchangeRates();
        List<Double> predictedExchangeRatesJPY = exchangeService.getPredictedExchangeRatesJPY();
        modelAndView.setViewName("exchange/exchange");
        //request.setAttribute("usdRate", usdRate);
        // request.setAttribute("jpyRate", jpyRate);

        modelAndView.addObject("usdRate", usdRate);
        modelAndView.addObject("jpyRate", jpyRate);
        modelAndView.addObject("predict", predictedExchangeRates);
        modelAndView.addObject("predictJPY", predictedExchangeRatesJPY);
        return modelAndView;
    }

    /**
     * 오프라인 환전 메인 페이지
     * API 명세서 ROWNUM:42
     */
    @GetMapping("/offline")
    @ApiOperation(value = "오프라인 환전 메인 페이지", notes="오프라인 환전 메인 페이지로 이동하며 오프라인 환전 내역을 함께 반환합니다.")
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
    @ApiOperation(value = "오프라인 환전 폼", notes="오프라인 환전 폼으로 이동합니다.")
    public String exchangeOfflineForm(HttpSession session, Model model) {
        LoginMemberDto loggedIn = (LoginMemberDto) session.getAttribute("member");
        Long memberId = loggedIn.getMemberId();
        List<BankDto> bankList = exchangeService.bankList();
        List<WalletDto> walletList = exchangeService.WalletList(memberId);

        model.addAttribute("bankList", bankList);
        model.addAttribute("walletList", walletList);
        return "exchange/exchangeOfflineForm";
    }

    /**
     * 오프라인 환전 요청
     * API 명세서 ROWNUM:44
     */
    @PostMapping("/offline/form")
    @ApiOperation(value = "오프라인 환전", notes="오프라인 환전을 신청하여 신청 내역에 반영됩니다.")
    public String exchangeOffline(OfflineReceiptRequestDto offlineReceiptRequestDto) {
        System.out.println("ExchangeController.exchangeOffline");
        int result = exchangeService.requestOfflineReceipt(offlineReceiptRequestDto);
        return "redirect:/exchange/offline";
    }

    /**
     * 오프라인 환전 취소 요청
     * API 명세서 ROWNUM:45
     */
    @DeleteMapping("/offline/form")
    @ResponseBody
    @ApiOperation(value = "오프라인 환전 취소", notes="오프라인 환전을 취소합니다.")
    public String exchangeOfflineCancel(@RequestParam String offlineReceiptId) {
        int res = exchangeService.cancelOfflineReceipt(Long.parseLong(offlineReceiptId));
        return "success";
    }


    /**
     * 온라인 환전 폼
     * API 명세서 ROWNUM:47
     */
    @GetMapping("/online/form")
    @ApiOperation(value = "온라인 환전 폼", notes="온라인 환전 폼으로 이동합니다.")
    public String exchangeOnlineForm(HttpSession session, Model model) {
        LoginMemberDto loggedIn = (LoginMemberDto) session.getAttribute("member");
        Long memberId = loggedIn.getMemberId();
        List<WalletDto> walletList = exchangeService.WalletList(memberId);
        model.addAttribute("walletList", walletList);
        return "exchange/exchangeOnlineForm";
    }

    /**
     * 온라인 환전 요청
     * API 명세서 ROWNUM:48
     */
    @PostMapping("/online/form")
    @ApiOperation(value = "온라인 환전", notes="온라인 환전을 요청하면 원화로 외화를 구매합니다.")
    public String exchangeOnline(ExchangeDto exchangeDto) {
        exchangeService.requestExchangeOnline(exchangeDto);
        WalletType type = WalletType.findByValue(exchangeDto.getWalletType());
        if(type.equals(WalletType.PERSONAL_WALLET)) return "redirect:/personalwallet/main";
        else return "redirect:/group-wallet/"+exchangeDto.getWalletId();
    }

    /**
     * 선택한 지갑의 원화 잔액 요청
     * API 명세서 ROWNUM: 55
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/walletBalance")
    @ApiOperation(value = "선택한 지갑의 원화 잔액 조회", notes="사용자가 선택한 지갑의 원화 잔액을 반환합니다.")
    public Long selectedWalletBalance(@RequestBody HashMap<String, Integer> param){
        Long walletId = Long.valueOf(param.get("walletId"));
        WalletType walletType = WalletType.findByValue(param.get("walletType"));
        return exchangeService.selectedWalletBalance(walletId, walletType);
    }

    /**
     * 선택한 통화와 입력 금액에 대한 환전 예상 금액 요청
     * API 명세서: ROWNUM 56
     */
    @ResponseBody
    @PostMapping("/expectedAmount")
    @ApiOperation(value = "선택한 통화와 입력 금액에 대한 환전 예상 금액 조회", notes="현재 적용 환율에 따라 입력한 통화와 금액을 계산하여 반환합니다.")
    public ExchangeCalDto expectedAmount(@RequestBody HashMap<String, Integer> param){
        Long amount = Long.valueOf(param.get("amount"));
        CurrencyCode foundCurrency = CurrencyCode.findByValue(param.get("code"));
        ExchangeCalDto dto = exchangeService.expectedExchangeAmount(foundCurrency, amount);
        return dto;
    }

    //== 예외 처리 ==/
    @ExceptionHandler({ExchangeException.class, InsertException.class, NoSuchElementException.class})
    @ApiOperation(value = "예외", hidden = true)
    public String exchangeException(Exception e) {
        System.out.println(e.getMessage());
        return "error";
    }

    /**
     * 온라인 재환전 폼
     * API 명세서 ROWNUM:47
     */
    @GetMapping("/online/re-form")
    @ApiOperation(value = "온라인 재환전 폼", notes="온라인 재환전 폼으로 이동합니다.")
    public String reExchangeOnlineForm(HttpSession session, Model model) {
        LoginMemberDto loggedIn = (LoginMemberDto) session.getAttribute("member");
        Long memberId = loggedIn.getMemberId();
        List<WalletDto> walletList = exchangeService.WalletList(memberId);
        model.addAttribute("walletList", walletList);
        return "exchange/reExchangeOnlineForm";
    }

    /**
     * 온라인 재환전 요청
     * API 명세서 ROWNUM:48
     */
    @PostMapping("/online/re-form")
    @ApiOperation(value = "온라인 재환전", notes="온라인 재환전을 요청하면 외화로 원화를 구매할 수 있습니다.")
    public String reExchangeOnline(ExchangeDto exchangeDto) {
        exchangeService.requestReExchangeOnline(exchangeDto);
        WalletType type = WalletType.findByValue(exchangeDto.getWalletType());
        if(type.equals(WalletType.PERSONAL_WALLET)) return "redirect:/personalwallet/main";
        else return "redirect:/group-wallet/"+exchangeDto.getWalletId();
    }

    /**
     * 선택한 지갑의 외화 잔액 목록 요청
     * API 명세서 ROWNUM:
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping("/walletFCBalance")
    @ApiOperation(value = "선택한 지갑의 외화 잔액 목록 조회", notes="선택한 지갑의 외화 잔액 목록을 통화별로 묶어서 반환합니다.")
    public WalletDetailDto selectedWalletFCBalance(@RequestBody HashMap<String, Integer> param){
        Long walletId = Long.valueOf(param.get("walletId"));
        WalletType walletType = WalletType.findByValue(param.get("walletType"));
        return exchangeService.selectedWalletFCBalance(walletId, walletType);
    }



    // 환율 예측 테스트

    @GetMapping("/exchangePrediction")
    @ApiOperation(value = "환율 예측 페이지", hidden = true)
    public String showPredictionPage() {
        return "exchange/exchangePredict"; // JSP 파일 이름
    }

    @PostMapping("/exchangePrediction")
    @ApiOperation(value = "환율 예측", notes="지난 환율에 따라 환율 예측치를 반환합니다.")
    public String runPrediction(Model model) {
        List<Double> predictions = exchangeService.getPredictedExchangeRates();
        model.addAttribute("predictions", predictions);
        return "exchange/exchangePredict"; // 결과를 동일한 JSP에 표시
    }




}