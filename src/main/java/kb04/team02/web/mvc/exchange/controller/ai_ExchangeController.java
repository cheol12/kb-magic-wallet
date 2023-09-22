package kb04.team02.web.mvc.exchange.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public class ai_ExchangeController {

    /////////////////////////////////////////////////////////
    // 환율 예측 테스트

    @GetMapping("/exchangePrediction")
    public String showPredictionPage() {
        System.out.println("======================showPredictionPage======================");
        return "exchange/exchangePredict"; // JSP 파일 이름
    }

    @PostMapping("/exchangePrediction")
    public String runPrediction(Model model) {
        System.out.println("======================runPrediction(Model model)======================");
        List<Double> predictions = exchangeService.getPredictedExchangeRates();
        model.addAttribute("predictions", predictions);
        return "exchange/exchangePredict"; // 결과를 동일한 JSP에 표시
    }
    ///////////////////////////////////////////////////////////
}
