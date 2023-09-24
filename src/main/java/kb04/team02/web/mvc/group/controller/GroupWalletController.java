package kb04.team02.web.mvc.group.controller;

import kb04.team02.web.mvc.common.dto.LoginMemberDto;
import kb04.team02.web.mvc.common.dto.WalletHistoryDto;
import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.common.entity.SettleType;
import kb04.team02.web.mvc.exchange.dto.ExchangeCalDto;
import kb04.team02.web.mvc.exchange.dto.ExchangeRateDto;
import kb04.team02.web.mvc.exchange.service.ExchangeService;
import kb04.team02.web.mvc.group.dto.*;
import kb04.team02.web.mvc.group.entity.Participation;
import kb04.team02.web.mvc.group.exception.NotEnoughBalanceException;
import kb04.team02.web.mvc.group.service.GroupWalletTabService;
import kb04.team02.web.mvc.member.entity.Member;
import kb04.team02.web.mvc.group.entity.GroupWallet;
import kb04.team02.web.mvc.common.dto.WalletDetailDto;
import kb04.team02.web.mvc.group.exception.WalletDeleteException;
import kb04.team02.web.mvc.group.service.GroupWalletService;
import kb04.team02.web.mvc.mypage.service.CardIssuanceService;
import kb04.team02.web.mvc.personal.service.PersonalWalletService;
import kb04.team02.web.mvc.saving.dto.SavingInstallmentDto;
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
    // 작성자: 김진형
    private final CardIssuanceService cardIssuanceService;
    private final ExchangeService exchangeService;

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

        // 수정자: 김진형
        ExchangeCalDto usdExchangeRateDto = exchangeService.expectedExchangeAmount(CurrencyCode.USD, walletDetailDto.getBalance().get("USD"));
        ExchangeCalDto jpyExchangeRateDto = exchangeService.expectedExchangeAmount(CurrencyCode.JPY, walletDetailDto.getBalance().get("JPY"));
        model.addAttribute("usdDto", usdExchangeRateDto);
        model.addAttribute("jpyDto", jpyExchangeRateDto);

//        // 내 모임지갑 모임원 리스트
        List<GroupMemberDto> groupMemberDtoList = groupWalletService.getGroupMemberList(id);
        model.addAttribute("groupMemberDtoList", groupMemberDtoList);
        int countMember = groupWalletService.countGroupWalletMember(id);
        model.addAttribute("countMember", countMember);

        // 모임지갑 내에서 내 권한 확인
        GroupMemberDto groupMemberDto = null;
        for (GroupMemberDto dto : groupMemberDtoList) {
            if (dto.getMemberId() == loginMemberDto.getMemberId()) {
                groupMemberDto = dto;
                break;
            }
        }
        model.addAttribute("groupWalletId", id);

        // 회비규칙 조회하기
        GroupWallet groupWallet = groupWalletService.getGroupWallet(id);
        model.addAttribute("groupWallet", groupWallet);

        // 회비 규칙 없을 경우, 모임장 권한을 판단하고 생성하기 버튼 보여주기
        boolean isChairman = groupWalletService.groupMemberIsChairman(groupWallet.getGroupWalletId(), loginMemberDto.getMemberId());
        model.addAttribute("isChairman", isChairman);


        model.addAttribute("groupMemberDto", groupMemberDto);
        // 회비 규칙에서 누적 회비 미구현 : 모임원들이 모임지갑에 이체한 내역 전부 더하기

        // 수정자: 김진형
        Long krwBalance = personalWalletService.personalWallet(loginMemberDto).getBalance().get("KRW");
        model.addAttribute("personalWalletBalance", krwBalance);

        try {
            // 적금 조회하기
            InstallmentDto installmentDto = groupWalletTabService.getSavingById(groupWallet);
            model.addAttribute("installmentDto", installmentDto);

            // 연결 카드 조회하기
            List<CardIssuanceDto> cardIssuanceDtoList = groupWalletService.getCardIssuanceDto(id);
            model.addAttribute("cardIssuanceDtoList", cardIssuanceDtoList);

            return "groupwallet/groupWalletDetail";
        } catch (RuntimeException e) {
            model.addAttribute("installmentDto", null);
            model.addAttribute("cardIssuanceDtoList", null);


            return "groupwallet/groupWalletDetail";
        }

    }


    @ResponseBody
    @PostMapping("{id}/history")
    public List<WalletHistoryDto> getHistory(@PathVariable Long id, HttpSession session, Model model) {
        WalletDetailDto walletDetailDto = groupWalletService.getGroupWalletDetail(id);
        model.addAttribute("walletDetailDto", walletDetailDto);
        return walletDetailDto.getList();
    }

    @ResponseBody
    @PostMapping("/{id}/member-list")
    public List<GroupMemberDto> getGroupWalletMembers(@PathVariable Long id, ModelAndView mv, HttpSession session) {
        List<GroupMemberDto> groupMemberDtoList = groupWalletService.getGroupMemberList(id);
        log.info("groupMemberDtoListSize = " + groupMemberDtoList.size());

        return groupMemberDtoList;
    }

    @ResponseBody
    @GetMapping("/{id}/saving-check")
    public InstallmentDto getGroupWalletInstallmentDto(@PathVariable Long id, HttpSession session){
        GroupWallet groupWallet = groupWalletService.getGroupWallet(id);

        try{
            InstallmentDto installmentDto = groupWalletTabService.getSavingById(groupWallet);
            return installmentDto;
        }
        catch (NullPointerException e){
            return null;
        }
    }
    /**
     * @author 김철
     * 모임지갑에서 모임장이 모임원을 강퇴한다.
     */
    @ResponseBody
    @PostMapping("{id}/out")
    public int groupWalletMemberKick(@PathVariable Long id, @RequestParam Long memberId) {
        int result = groupWalletService.groupWalletMemberOut(id, memberId);

        return result;
    }

    /**
     * 모임지갑 삭제 요청
     * API 명세서 ROWNUM:14
     *
     * @param id 삭제할 모임지갑 id
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public String groupWalletDelete(@PathVariable Long id) throws WalletDeleteException {
        groupWalletService.deleteGroupWallet(id);

        return "success";
    }

    /**
     * 모임지갑 초대 요청 폼
     * API 명세서 ROWNUM:15
     *
     * @param id 초대를 요청할 모임지갑 id
     */
    @GetMapping("/{id}/invite-form")
    public String groupWalletInviteForm(@PathVariable Long id, Model model) {
        GroupWallet groupWallet = groupWalletService.getGroupWallet(id);
        model.addAttribute("groupWallet", groupWallet);
        return "groupwallet/groupWalletInviteForm";
    }

    /**
     * 모임지갑 초대 요청
     * API 명세서 ROWNUM:15
     *
     * @param id 초대를 요청할 모임지갑 id
     */
    @ResponseBody
    @PostMapping("/{id}/invite-request")
    public int groupWalletCreateInviteLink(@PathVariable Long id, @RequestParam String phone) {
        int value = groupWalletService.inviteMember(phone, id);
        if (value != 1) {
            return 0;
        }
        return 1;
    }

    /**
     * 나를 초대한 모임지갑들 부르기
     */
    @ResponseBody
    @PostMapping("/invited-list")
    public List<InvitedDto> groupWalletInvitedList(HttpSession session) {

        LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute("member");
        try {
            List<InvitedDto> invitedDtoList = groupWalletService.getGroupListInvitedMe(loginMemberDto.getMemberId());
            return invitedDtoList;
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * 모임지갑 초대 수락
     * API 명세서
     *
     * @param id 모임지갑 id
     */
    @ResponseBody
    @PostMapping("/{id}/invite-accept")
    public int groupWalletInviteAccept(@PathVariable Long id, HttpSession session) {
        LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute("member");
        return groupWalletService.invitedAccept(id, loginMemberDto.getMemberId());
    }

    /**
     * 모임지갑 초대 거절
     */
    @ResponseBody
    @PostMapping("/{id}/invite-refuse")
    public int groupWalletInviteRefuse(@PathVariable Long id, HttpSession session) {
        LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute("member");
        return groupWalletService.invitedRefuse(id, loginMemberDto.getMemberId());
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
    public String groupWalletWithdraw(@PathVariable Long id, @RequestParam Long amount, HttpSession session) {
        LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute("member");
        TransferDto transferDto = new TransferDto();
        transferDto.setMemberId(loginMemberDto.getMemberId());
        transferDto.setGroupWalletId(id);
        transferDto.setAmount(amount);
        try {
            groupWalletService.groupWalletWithdraw(transferDto);
            return "redirect:/group-wallet/" + id;
        } catch (NotEnoughBalanceException e) {
            return null;
        }

    }

    /**
     * 모임지갑 정산 요청
     * API 명세서 ROWNUM:32
     *
     * @param id 정산을 진행할 모임지갑 id
     */
    @PostMapping("/{id}/settle")
    public String groupWalletSettle(@PathVariable Long id) throws NotEnoughBalanceException {
        // {id} 로 현재 모임지갑 부르고, 폼 입력값을 amount로 부른다.
        GroupWallet groupWallet = groupWalletService.getGroupWallet(id);
        SettleDto settleDto = SettleDto.builder()
                .groupWalletId(id)
                .settleType(SettleType.NBBANG)
                .currencyCode(CurrencyCode.KRW)
                .totalAmout(groupWallet.getBalance()).build();

	    groupWalletService.settle(settleDto);

        return "redirect:/group-wallet/" + id;
    }

    /**
     * 모임지갑 입금 = 채우기 요청
     * API 명세서 ROWNUM:34
     *
     * @param id 입금할 모임지갑 id
     */
    @PostMapping("/{id}/deposit")
    public String groupWalletDeposit(@PathVariable Long id, @RequestParam Long amount, HttpSession session) {
        LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute("member");
        TransferDto transferDto = new TransferDto();
        transferDto.setMemberId(loginMemberDto.getMemberId());
        transferDto.setGroupWalletId(id);
        transferDto.setAmount(amount);
        try {
            groupWalletService.groupWalletDeposit(transferDto);
            return "redirect:/group-wallet/" + id;
        } catch (NotEnoughBalanceException e) {
            return null;
        }

        // 모임지갑 '채우기' 를 하기 위해 개인지갑을 '꺼내기' 한다.
//	    PersonalWallet pWallet = personalWalletService.personalWalletWithdraw();

        // 입금했을 경우,
        // GroupWallet 의 잔액을 update : id, amount 필요
        // 모임지갑 이체내역 데이터 insert,
        // PersonalWallet 의 잔액을 update,
        // 개인지갑 이체내역 데이터 insert
        // 이를 service.deposit 에서 한 번에 트랜잭션 처리
//	groupWalletService.groupWalletDeposit(id, amount, memberId );

    }

    @ResponseBody
    @PostMapping("/{id}/rule/create")
    public Long groupWalletRuleCreate(@PathVariable Long id, @RequestParam int dueDate, @RequestParam Long due, Model model, ModelAndView mv){
        GroupWallet groupWallet = groupWalletService.setGroupWalletDueRule(id, dueDate, due);

        log.info("groupWallet result = " + groupWallet.getGroupWalletId());
        
        return groupWallet.getGroupWalletId();
    }


    /**
     * 모임지갑 상세 내에서 멤버 리스트와 카드 상태를 보여주는 기능을 위한 메소드
     * 작성자: 김진형
     */
    @ResponseBody
    @GetMapping("/load-card-data")
    public List<GroupMemberDto> getGroupWalletDetail(Long id, Model model, HttpSession session) {
        System.out.println("=====================");
        System.out.println("id = " + id);
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

        return groupMemberDtoList;
    }

    /**
     * 모임지갑 상세 내에서 카드 연결 기능을 위한 메소드
     * 작성자: 김진형
     */
    @ResponseBody
    @GetMapping("/change-card-connection")
    public List<GroupMemberDto> changeCardConnection(Long id, Model model, HttpSession session) {
        LoginMemberDto member = (LoginMemberDto) session.getAttribute("member");
        cardIssuanceService.createCardConnection(member.getMemberId(), id);

        // id = 내 모임지갑의 id중 하나임.
        LoginMemberDto loginMemberDto = (LoginMemberDto) member;
        model.addAttribute("loginMemberDto", loginMemberDto);

        // 내 모임지갑 모임원 리스트
        List<GroupMemberDto> groupMemberDtoList = groupWalletService.getGroupMemberList(id);
        for (GroupMemberDto groupMemberDto : groupMemberDtoList) {
            boolean connectToWallet = cardIssuanceService.isConnectToWallet(groupMemberDto.getMemberId(), id);
            groupMemberDto.setCardIsConnect(connectToWallet);
        }
        model.addAttribute("groupMemberDtoList", groupMemberDtoList);
        return groupMemberDtoList;
    }


}
