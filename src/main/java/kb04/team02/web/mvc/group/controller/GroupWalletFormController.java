package kb04.team02.web.mvc.group.controller;

import io.swagger.annotations.Api;
import kb04.team02.web.mvc.common.dto.LoginMemberDto;
import kb04.team02.web.mvc.group.entity.GroupWallet;
import kb04.team02.web.mvc.group.service.GroupWalletService;
import kb04.team02.web.mvc.personal.service.PersonalWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/group-wallet")
@RequiredArgsConstructor
@Api(tags = {"모임 지갑 폼 API"})
public class GroupWalletFormController {

    private final GroupWalletService groupWalletService;
    private final PersonalWalletService personalWalletService;

    /**
     * 모임지갑 생성 폼
     * API 명세서 ROWNUM:11
     */
    @GetMapping("/new")
    public String groupWalletCreateForm() {
	
        return "groupwallet/groupWalletCreateForm";
    }

    /**
     * 모임지갑 꺼내기 폼
     * API 명세서 ROWNUM:29
     *
     * @param id 모임지갑에서 꺼내기 할 모임지갑 id
     */
    @GetMapping("/{id}/withdraw")
    public String groupWalletWithdrawForm(@PathVariable Long id, HttpSession session, Model model) {
        LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute("member");
        GroupWallet groupWallet = groupWalletService.getGroupWallet(id);
        model.addAttribute("groupWallet", groupWallet);
        model.addAttribute("member", loginMemberDto);
        return "groupwallet/groupWalletWithdrawForm";
    }

    /**
     * 모임지갑 정산 폼
     * API 명세서 ROWNUM:31
     *
     * @param id 정산을 진행할 모임지갑 id
     */
    @GetMapping("/{id}/settle")
    public String groupWalletSettleForm(@PathVariable Long id) {
        return "groupwallet/groupWalletSettleForm";
    }

    /**
     * 모임지갑 입금 폼
     * API 명세서 ROWNUM:33
     *
     * @param id 입금할 모임지갑 id
     */
    @GetMapping("/{id}/deposit")
    public String groupWalletDepositForm(@PathVariable Long id, HttpSession session, Model model) {
        LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute("member");
        GroupWallet groupWallet = groupWalletService.getGroupWallet(id);
        model.addAttribute("groupWallet", groupWallet);
        model.addAttribute("member", loginMemberDto);
        Long krwBalance = personalWalletService.personalWallet(loginMemberDto).getBalance().get("KRW");
        model.addAttribute("personalWalletBalance", krwBalance);

        return "groupwallet/depositForm";
    }

    /**
     * @author:hyun
     * 회비 규칙 만들기 폼으로 이동 or 폼에서 입력받음?
     * 폼으로 이동이면 @GetMapping, 폼에서 입력받으면 @PostMapping
     * API 명세서에 없음
     * @param id 회비 규칙을 만들 모임지갑 id
     */
//    @GetMapping("/{id}/rule")
    public String groupWalletRuleForm(@PathVariable Long id, Model model) {
        GroupWallet groupWallet = groupWalletService.getGroupWallet(id);
        model.addAttribute("groupWallet", groupWallet);
        return "groupwallet/groupWalletRuleForm";
    }


}
