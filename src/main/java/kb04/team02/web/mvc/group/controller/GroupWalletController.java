package kb04.team02.web.mvc.group.controller;

import kb04.team02.web.mvc.member.entity.Member;
import kb04.team02.web.mvc.group.entity.GroupWallet;
import kb04.team02.web.mvc.common.dto.WalletDetailDto;
import kb04.team02.web.mvc.group.exception.WalletDeleteException;
import kb04.team02.web.mvc.group.service.GroupWalletService;
import kb04.team02.web.mvc.personal.service.PersonalWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/group-wallet")
@RequiredArgsConstructor
public class GroupWalletController {

    private final GroupWalletService groupWalletService;
    private final PersonalWalletService personalWalletService;

//    private final GroupWalletRespository groupWalletRep;  -- 테스트용 임시코드
//    private final MemberRepository memberRep;             -- 테스트용 임시코드

    /**
     * 모임지갑 메인 페이지
     * API 명세서 ROWNUM:10
     */
    @GetMapping("/")
    public String groupWalletIndex(Model model, HttpSession session) {

        Member member = (Member) session.getAttribute("member_id");


        System.out.println("------------------------");
//        Member member = memberRep.findById(1L).get();     // 테스트용 임시코드
        System.out.println("--------------------------");

        List<GroupWallet> gWalletList = groupWalletService.selectAllMyGroupWallet(member);

        member.setGroupWallets(gWalletList);

        model.addAttribute("gWalletList", gWalletList);
//        model.addAttribute("member", member);             // 테스트용 임시코드

//        session.setAttribute("member", member);           // 테스트용 임시코드
        System.out.println("member" + member);

        return "group/groupIndex";
    }

    /**
     * 모임지갑 생성 요청 (=생성 버튼 누를시)
     * API 명세서 ROWNUM:12
     */
    @PostMapping("/new")
    public String groupWalletCreate(GroupWallet gWallet, Model model, HttpSession session, @RequestParam String nickname) {
	    // 모임장의 회원 식별번호 불러오기,
	    // 뷰에서 입력한 별칭 필요
        // 멤버 컬럼 : memberid, id, password, name, address, phonenumber, email, insertDate, payPassword, bankaccount

        Member member = (Member) session.getAttribute("member_id");

//        Member member = memberRep.findById(1L).get();     // 테스트용 임시코드
//        model.addAttribute("member", member);             // 테스트용 임시코드

	    groupWalletService.createGroupWallet(member, nickname);

	    // 생성되었습니다 알림 띄우고 group-wallet으로 이동
	    return "redirect:/group-wallet";		// "redirect:/"; 인가?
    }

    /**
     * 개별 모임지갑 상세 페이지
     * API 명세서 ROWNUM:13
     *
     * @param id 조회할 모임지갑 id
     */
    @GetMapping("/{id}")
    public String getGroupWalletDetail(@PathVariable Long id, Model model) {
	// id = 내 모임지갑의 id중 하나임.
        WalletDetailDto walletDetailDto = groupWalletService.getGroupWalletDetail(id);
        model.addAttribute("walletDetailDto", walletDetailDto);
        return "group/groupWalletDetail";
    }

    /**
     * 모임지갑 삭제 요청
     * API 명세서 ROWNUM:14
     *
     * @param id 삭제할 모임지갑 id
     */
    @DeleteMapping("/{id}") // 매핑값이 /{id} 가 맞는지?
    public String groupWalletDelete(@PathVariable Long id) throws WalletDeleteException {
	// 삭제할때 카드비밀번호 입력해서 삭제하기?
	    groupWalletService.deleteGroupWallet(id);
	    return "redirect:/group-wallet";
    }

    /**
     * 모임지갑 초대 링크 생성 요청
     * API 명세서 ROWNUM:15
     *
     * @param id 초대링크를 생성할 모임지갑 id
     */
    @ResponseBody
    @GetMapping("/{id}/link")
    public String groupWalletCreateInviteLink(@PathVariable Long id) {
	// 메시지 api 불러오기
	    groupWalletService.inviteMember(id);
	//json으로 데이터 전달하기
	    return "redirect:/group-wallet/{id}/member-list";
    }

    /**
     * 모임지갑 자진 탈퇴 요청
     * API 명세서 ROWNUM:17
     *
     * @param id 자진 탈퇴 요청 모임지갑 id
     */
    @DeleteMapping("/{id}/out")
    public String groupWalletMemberOut(@PathVariable Long id, HttpSession session) {
        // 모임지갑 id
	    Member member = (Member) session.getAttribute("member_id");

	    // id=모임지갑에서 memberId=내가 탈퇴한다.
	    groupWalletService.groupWalletMemberOut(id, member);
	    return "redirect:/group-wallet";
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
	return "redirect:/group-wallet/{id}";
    }

    /**
     * 모임지갑 정산 요청
     * API 명세서 ROWNUM:32
     *
     * @param id 정산을 진행할 모임지갑 id
     */
    @PostMapping("/{id}/settle")
    public String groupWalletSettle(@PathVariable String id, int amount) {
	// {id} 로 현재 모임지갑 부르고, 폼 입력값을 amount로 부른다.
//	groupWalletService.settle(id, amount);
	return "redirect:/group-wallet/{id}";
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

	return "redirect:/group-wallet/{id}";
    }

}
