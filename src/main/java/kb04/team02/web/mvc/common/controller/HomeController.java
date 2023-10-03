package kb04.team02.web.mvc.common.controller;

import kb04.team02.web.mvc.common.dto.LoginMemberDto;
import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.exchange.service.ExchangeService;
import kb04.team02.web.mvc.group.dto.GroupMemberDto;
import kb04.team02.web.mvc.group.service.GroupWalletService;
import kb04.team02.web.mvc.group.service.GroupWalletTabService;
import kb04.team02.web.mvc.mypage.service.CardIssuanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@ApiIgnore
public class HomeController {

    private final GroupWalletTabService groupWalletTabService;
    private final GroupWalletService groupWalletService;
    private final ExchangeService exchangeService;
    private final CardIssuanceService cardIssuanceService;


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

    //======================================================================================
    // 카드, 회원 리스트 기능 Test Controller Method
    @GetMapping("/test/newGwalletMain")
    public String newGwalletMain() {
        return "groupwallet/groupWalletMemberAndCard";
    }


    @ResponseBody
    @GetMapping("/test/load-card-data")
    public List<GroupMemberDto> getGroupWalletDetail(ModelAndView mv, Model model, HttpSession session) {
        System.out.println("=====================");
        Long id = 8L;
        // id = 내 모임지갑의 id중 하나임.
        LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute("member");
        model.addAttribute("loginMemberDto", loginMemberDto);

        // 내 모임지갑 모임원 리스트
        List<GroupMemberDto> groupMemberDtoList = groupWalletService.getGroupMemberList(id);
        for (GroupMemberDto groupMemberDto : groupMemberDtoList) {
            boolean connectToWallet = cardIssuanceService.isConnectToWallet(groupMemberDto.getMemberId(), id);
            groupMemberDto.setCardIsConnect(connectToWallet);
        }
        model.addAttribute("groupMemberDtoList", groupMemberDtoList);
        for (GroupMemberDto groupMemberDto : groupMemberDtoList) {
            System.out.println("groupMemberDto.getName() = " + groupMemberDto.getName());
            System.out.println("groupMemberDto.getRole() = " + groupMemberDto.getRole());
            System.out.println("groupMemberDto.isCardIsConnect() = " + groupMemberDto.isCardIsConnect());
        }
        return groupMemberDtoList;
    }

    @ResponseBody
    @GetMapping("/test/change-card-connection")
    public List<GroupMemberDto> changeCardConnection(Long id, Model model, HttpSession session) {
        cardIssuanceService.createCardConnection(id, 8L);

        // id = 내 모임지갑의 id중 하나임.
        LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute("member");
        model.addAttribute("loginMemberDto", loginMemberDto);

        // 내 모임지갑 모임원 리스트
        List<GroupMemberDto> groupMemberDtoList = groupWalletService.getGroupMemberList(8L);
        for (GroupMemberDto groupMemberDto : groupMemberDtoList) {
            boolean connectToWallet = cardIssuanceService.isConnectToWallet(groupMemberDto.getMemberId(), 8L);
            System.out.println("connectToWallet = " + connectToWallet);
            groupMemberDto.setCardIsConnect(connectToWallet);
        }
        model.addAttribute("groupMemberDtoList", groupMemberDtoList);
        for (GroupMemberDto groupMemberDto : groupMemberDtoList) {
            System.out.println("groupMemberDto.getName() = " + groupMemberDto.getName());
            System.out.println("groupMemberDto.getRole() = " + groupMemberDto.getRole());
            System.out.println("groupMemberDto.isCardIsConnect() = " + groupMemberDto.isCardIsConnect());
        }
        return groupMemberDtoList;
    }


    // 회비 납부내역 조회 기능 Test Controller Method
    @GetMapping("test/testDuePayment")
    public String testDuePayment(){
        return "groupwallet/duePaymentCondition";}
}
