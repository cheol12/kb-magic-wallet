package kb04.team02.web.mvc.common.controller;

import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.exchange.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ExchangeService exchangeService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping("/test/test")
    public ModelAndView test(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        List<String> usdRate = exchangeService.selectExchangeRateByCurrencyCode(CurrencyCode.USD);
        List<String> jpyRate = exchangeService.selectExchangeRateByCurrencyCode(CurrencyCode.JPY);

        modelAndView.setViewName("test");
        //request.setAttribute("usdRate", usdRate);
       // request.setAttribute("jpyRate", jpyRate);

        modelAndView.addObject("usdRate", usdRate);
        modelAndView.addObject("jpyRate", jpyRate);

        return modelAndView;
    }
}
