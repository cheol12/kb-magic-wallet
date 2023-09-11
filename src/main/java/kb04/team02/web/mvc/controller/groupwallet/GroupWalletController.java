package kb04.team02.web.mvc.controller.groupwallet;

import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import kb04.team02.web.mvc.service.groupwallet.GroupWalletService;
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

    /**
     * 모임지갑 메인 페이지
     * API 명세서 ROWNUM:10
     */
    @GetMapping("/")
    public void groupWalletIndex(Model model, HttpSession session) {
	Integer memberId = (Integer) session.getAttribute("member_id");
//	List<GroupWallet> gWalletList = groupWalletService.selectAllMyGroupWallet(memberId);
//	model.addAttribute("gWalletList", gWalletList);
    }

    /**
     * 모임지갑 생성 요청 (=생성 버튼 누를시)
     * API 명세서 ROWNUM:12
     */
    @PostMapping("/new")
    public String groupWalletCreate(GroupWallet gWallet, Model model, HttpSession session) {
	// 모임장의 회원 식별번호 불러오기, 
	// 뷰에서 입력한 별칭 필요, 회비 상태 필요
	Integer memberId = (Integer) session.getAttribute("member_id");
//	gWallet.setMemberId(memberId);
//	groupWalletService.createGroupWallet(gWallet);
	
	
	
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
    public void groupWalletDetail(@PathVariable String id, Model model) {
	// id = 내 모임지갑의 id중 하나임.
	// 
//	GroupWallet gWallet = groupWalletService.getGroupWalletDetail(id);
//	model.addAttribute("gWallet", gWallet);
	
    }

    /**
     * 모임지갑 삭제 요청
     * API 명세서 ROWNUM:14
     *
     * @param id 삭제할 모임지갑 id
     */
    @DeleteMapping("/{id}")
    public String groupWalletDelete(@PathVariable String id) {
	// 삭제할때 카드비밀번호 입력해서 삭제하기?
//	groupWalletService.deleteGroupWallet(id);
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
    public String groupWalletCreateInviteLink(@PathVariable String id) {
	// 메시지 api 불러오기
//	groupWalletService.inviteMember(id);
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
    public String groupWalletMemberOut(@PathVariable String id, HttpSession session) {
	Integer memberId = (Integer) session.getAttribute("member_id");
//	groupWalletService.groupWalletMemberOut(id, memberId);
	return "redirect:/group-wallet";
    }

    /**
     * 모임지갑 꺼내기 요청
     * API 명세서 ROWNUM:30
     *
     * @param id 모임지갑에서 꺼내기 할 모임지갑 id
     */
    @PostMapping("/{id}/withdraw")
    public String groupWalletWithdraw(@PathVariable String id, int amount) {
	
//	groupWalletService.groupWalleWithdraw(id, amount);
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
	// 세션모임지갑
//	groupWalletService.settle(id, amount);
	return "redirect:/group-wallet/{id}";
    }

    /**
     * 모임지갑 입금 요청
     * API 명세서 ROWNUM:34
     *
     * @param id 입금할 모임지갑 id
     */
    @PostMapping("/{id}/deposit")
    public String groupWalletDeposit(@PathVariable String id, int amount, HttpSession session) {
	Integer memberId = (Integer) session.getAttribute("member_id");
	//PersonalWallet pWallet = personalWalletService.selectById(memberId);
	
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
