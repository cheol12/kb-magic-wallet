package kb04.team02.web.mvc.group.controller;

import kb04.team02.web.mvc.common.dto.LoginMemberDto;
import kb04.team02.web.mvc.common.dto.WalletHistoryDto;
import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.exchange.dto.ExchangeRateDto;
import kb04.team02.web.mvc.group.dto.CardIssuanceDto;
import kb04.team02.web.mvc.group.dto.GroupMemberDto;
import kb04.team02.web.mvc.group.dto.InstallmentDto;
import kb04.team02.web.mvc.group.service.GroupWalletTabService;
import kb04.team02.web.mvc.member.entity.Member;
import kb04.team02.web.mvc.group.entity.GroupWallet;
import kb04.team02.web.mvc.common.dto.WalletDetailDto;
import kb04.team02.web.mvc.group.exception.WalletDeleteException;
import kb04.team02.web.mvc.group.service.GroupWalletService;
import kb04.team02.web.mvc.personal.service.PersonalWalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/group-wallet")
@RequiredArgsConstructor
public class GroupWalletController {

    private final GroupWalletService groupWalletService;
    private final GroupWalletTabService groupWalletTabService;
    private final PersonalWalletService personalWalletService;

    /**
     * 모임지갑 메인 페이지
     * API 명세서 ROWNUM:10
     */
    @GetMapping("/")
    public String groupWalletIndex(Model model, HttpSession session) {

        LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute("member");
        List<GroupWallet> gWalletList = groupWalletService.selectAllMyGroupWallet(loginMemberDto);
        model.addAttribute("gWalletList", gWalletList);

        return "groupwallet/groupWalletIndex";
    }

    /**
     * 모임지갑 생성 요청 (=생성 버튼 누를시)
     * API 명세서 ROWNUM:12
     */
    @PostMapping("/new")
    public String groupWalletCreate(GroupWallet gWallet, Model model, HttpSession session, @RequestParam String nickname) {
	    // 모임장의 회원 식별번호 불러오기,
	    // 뷰에서 입력한 별칭 필요

        LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute("member");

        groupWalletService.createGroupWallet(loginMemberDto.getMemberId(), nickname);

        // 생성 실행 후 컨트롤러에서 /group-wallet/ 을 호출하는 메소드 실행
        return "redirect:/group-wallet/";
    }

    /**
     * 개별 모임지갑 상세 페이지
     * API 명세서 ROWNUM:13
     *
     * @param id 조회할 모임지갑 id
     */
//    @ResponseBody
    @GetMapping("/{id}")
    public String getGroupWalletDetail(@PathVariable Long id, ModelAndView mv, Model model, HttpSession session) {
	// id = 내 모임지갑의 id중 하나임.

        LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute("member");
        model.addAttribute("loginMemberDto", loginMemberDto);

        // 내 모임지갑 내역 조회
        WalletDetailDto walletDetailDto = groupWalletService.getGroupWalletDetail(id);
        model.addAttribute("walletDetailDto", walletDetailDto);
        ExchangeRateDto usdExchangeRateDto = ExchangeRateDto.builder()
                .currencyCode(CurrencyCode.USD)
                .tradingBaseRate(1324.0)
                .build();
        ExchangeRateDto jpyExchangeRateDto = ExchangeRateDto.builder()
                .currencyCode(CurrencyCode.JPY)
                .tradingBaseRate(9.08).build();
        model.addAttribute("usdExchangeRateDto", usdExchangeRateDto);
        model.addAttribute("jpyExchangeRateDto", jpyExchangeRateDto);

        // 내 모임지갑 모임원 리스트
        List<GroupMemberDto> groupMemberDtoList = groupWalletService.getGroupMemberList(id);
        model.addAttribute("groupMemberDtoList", groupMemberDtoList);
        int countMember = groupWalletService.countGroupWalletMember(id);
        model.addAttribute("countMember", countMember);
        // 모임지갑 내에서 내 권한 확인
        GroupMemberDto groupMemberDto = null;
        for(GroupMemberDto dto : groupMemberDtoList){
            if(dto.getMemberId() == loginMemberDto.getMemberId()){
                groupMemberDto = dto;
                break;
            }
        }
        model.addAttribute("groupMemberDto", groupMemberDto);
        System.out.println("groupMember.Role = " + groupMemberDto.getRoleToString());

        // 회비규칙 조회하기
        GroupWallet groupWallet = groupWalletService.getGroupWallet(id);
        model.addAttribute("groupWallet", groupWallet);

        // 회비 규칙 없을 경우, 모임장 권한을 판단하고 생성하기 버튼 보여주기
        boolean isChairman = groupWalletService.groupMemberIsChairman(groupWallet.getGroupWalletId(), loginMemberDto.getMemberId());
        model.addAttribute("isChairman", isChairman);


        // 회비 규칙에서 누적 회비 미구현 : 모임원들이 모임지갑에 이체한 내역 전부 더하기

        try {
            // 적금 조회하기
            InstallmentDto installmentDto = groupWalletService.getInstallmentDtoSaving(groupWallet);
            model.addAttribute("installmentDto", installmentDto);

            // 연결 카드 조회하기
            List<CardIssuanceDto> cardIssuanceDtoList = groupWalletService.getCardIssuanceDto(id);
            model.addAttribute("cardIssuanceDtoList", cardIssuanceDtoList);

            return "groupwallet/groupWalletDetail01";
        } catch (RuntimeException e) {
            model.addAttribute("installmentDto", null);
            model.addAttribute("cardIssuanceDtoList", null);


            return "groupwallet/groupWalletDetail01";
        }

    }



    @ResponseBody
    @PostMapping("{id}/history")
    public List<WalletHistoryDto> getHistory(@PathVariable Long id, HttpSession session, Model model) {
        WalletDetailDto walletDetailDto = groupWalletService.getGroupWalletDetail(id);
        model.addAttribute("walletDetailDto", walletDetailDto);
        System.out.println(walletDetailDto.getList().get(0).getDate()); // 이건 null값
        System.out.println(walletDetailDto.getList().get(0).getDateTime());     // 이건 진짜 값
        System.out.println("===============");
        return walletDetailDto.getList();
    }

    @ResponseBody
    @PostMapping("/{id}/member-list")
    public List<GroupMemberDto> getGroupWalletMembers(@PathVariable Long id, ModelAndView mv, HttpSession session) {
        // id = 내 모임지갑의 id중 하나임.

        List<GroupMemberDto> groupMemberDtoList = groupWalletService.getGroupMemberList(id);
        GroupWallet groupWallet = groupWalletService.getGroupWallet(id);
        log.info("groupMemberDtoListSize = " + groupMemberDtoList.size());

        return groupMemberDtoList;
    }

    /**
     * @author 김철
     * 모임지갑에서 모임장이 모임원을 강퇴한다.
     * */
    @ResponseBody
    @PostMapping("{id}/out")
    public int groupWalletMemberKick(@PathVariable Long id, @RequestParam Long memberId){
        int result = groupWalletService.groupWalletMemberOut(id, memberId);

        return result;
    }

    /**
     * 모임지갑 삭제 요청
     * API 명세서 ROWNUM:14
     *
     * @param id 삭제할 모임지갑 id
     */
    @DeleteMapping("/{id}") // 매핑값이 /{id} 가 맞는지?
    public String groupWalletDelete(@PathVariable Long id) throws WalletDeleteException {
	    groupWalletService.deleteGroupWallet(id);

	    return "redirect:/group-wallet/";
    }

    /**
     * 모임지갑 초대 링크 생성 폼
     * API 명세서 ROWNUM:15
     *
     * @param id 초대링크를 생성할 모임지갑 id
     */
    @GetMapping("/{id}/invite-form")
    public String groupWalletInviteForm(@PathVariable Long id, Model model) {
        GroupWallet groupWallet = groupWalletService.getGroupWallet(id);
        model.addAttribute("groupWallet", groupWallet);
	    return "groupwallet/groupWalletInviteForm";
    }

    /**
     * 모임지갑 초대 링크 생성 요청
     * API 명세서 ROWNUM:15
     *
     * @param id 초대링크를 생성할 모임지갑 id
     */
    @ResponseBody
    @GetMapping("/{id}/invite-request")
    public int groupWalletCreateInviteLink(@PathVariable Long id, @RequestParam String phone) {
	// 메시지 api 불러오기
	    int value = groupWalletService.inviteMember(phone, id);
	//json으로 데이터 전달하기
        return value;
//	    return "redirect:/group-wallet/" + id + "/member-list";
    }

    /**
     * 모임지갑 초대 수락
     * API 명세서
     *
     * @param id 모임지갑 id
     */
    @ResponseBody
    @GetMapping("/{id}/invite-response")
    public String groupWalletInviteResponse(@PathVariable Long id, @RequestParam String phone) {
        // 메시지 api 불러오기
        groupWalletService.inviteMember(phone, id);
        //json으로 데이터 전달하기
        return "redirect:/group-wallet/" + id + "/member-list";
    }

    /**
     * 모임지갑 자진 탈퇴 요청
     * API 명세서 ROWNUM:17
     *
     * @param id 자진 탈퇴 요청 모임지갑 id
     */
    @GetMapping("/{id}/leave")
    public String groupWalletMemberOut(@PathVariable Long id, HttpSession session, Model model) {
        // 모임지갑 id
//	    Member member = (Member) session.getAttribute("member_id");
        LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute("member");
	    // id=모임지갑에서 memberId=내가 탈퇴한다.
	    groupWalletService.groupWalletMemberOut(id, loginMemberDto.getMemberId());
	    return "redirect:/group-wallet/";
    }


    /**
     * 모임지갑 꺼내기 요청
     * API 명세서 ROWNUM:30
     *
     * @param id 모임지갑에서 꺼내기 할 모임지갑 id
     */
    @PostMapping("/{id}/withdraw")
    public String groupWalletWithdraw(@PathVariable Long id, int amount, HttpSession session) {
        Member member = (Member) session.getAttribute("member_id");

        // member로 꺼내기하는 사람을 불러오고, {id}로 모임지갑 id를 불러오고, amount로 폼에서의 입력값을 부른다.
//	    groupWalletService.groupWalleWithdraw(member, id, amount);
	return "redirect:/group-wallet/" + id;
    }

    /**
     * 모임지갑 정산 요청
     * API 명세서 ROWNUM:32
     *
     * @param id 정산을 진행할 모임지갑 id
     */
    @PostMapping("/{id}/settle")
    public String groupWalletSettle(@PathVariable Long id, int amount) {
	// {id} 로 현재 모임지갑 부르고, 폼 입력값을 amount로 부른다.
//	groupWalletService.settle(id, amount);
	return "redirect:/group-wallet/" + id;
    }

    /**
     * 모임지갑 입금 = 채우기 요청
     * API 명세서 ROWNUM:34
     *
     * @param id 입금할 모임지갑 id
     */
    @PostMapping("/{id}/deposit")
    public String groupWalletDeposit(@PathVariable Long id, int amount, HttpSession session) {
	    Member member = (Member) session.getAttribute("member_id");

	    // 모임지갑 '채우기' 를 하기 위해 개인지갑을 '꺼내기' 한다.
//	    PersonalWallet pWallet = personalWalletService.personalWalletWithdraw();
	
	// 입금했을 경우,
	// GroupWallet 의 잔액을 update : id, amount 필요
	// 모임지갑 이체내역 데이터 insert, 
	// PersonalWallet 의 잔액을 update,
	// 개인지갑 이체내역 데이터 insert
	// 이를 service.deposit 에서 한 번에 트랜잭션 처리
//	groupWalletService.groupWalletDeposit(id, amount, memberId );

	return "redirect:/group-wallet/" + id;
    }

//    @ResponseBody
    @PostMapping("/{id}/rule/create")
    public ModelAndView groupWalletRuleCreate(@PathVariable Long id, @RequestParam int dueDate, @RequestParam Long due, Model model, ModelAndView mv){
        GroupWallet groupWallet = groupWalletService.setGroupWalletDueRule(id, dueDate, due);

        log.info("groupWallet result = " + groupWallet);
        model.addAttribute("groupWallet", groupWallet);
        mv.setViewName("redirect:/group-wallet/" + id);
        mv.addObject("groupWallet", groupWallet);
//        if(result > 0){
//            return "redirect:/group-wallet/" + id;
//        }

//        return "redirect:/group-wallet/" + id;
        return mv;
    }


}
