package kb04.team02.web.mvc.controller.groupwallet;

import kb04.team02.web.mvc.domain.card.CardIssuance;
import kb04.team02.web.mvc.domain.member.Member;
import kb04.team02.web.mvc.domain.saving.Saving;
import kb04.team02.web.mvc.dto.*;
import kb04.team02.web.mvc.service.groupwallet.GroupWalletTabService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("/group-wallet")
@RequiredArgsConstructor
public class GroupWalletTabController {

    private final GroupWalletTabService groupWalletTabService;


    // 페이지당 항목 수
    private final static int PAGE_SIZE = 10;
    private final static int BLOCK_SIZE = 5;


    //== 모임원 조회 탭 START ==//

    /**
     * 모임지갑 모임원 리스트 조회 요청
     *
     * @param id 모임원 리스트를 조회할 모임지갑 id
     */

    // 1. 하나의 페이지에서 다 표현하는 방식
/*    public Page<Member> groupwalletMemberList(@PathVariable String id, Pageable pageable) {
        // 정렬 조건을 추가하여 이름 증가 순으로 정렬
        Sort sort = Sort.by(Sort.Order.asc("name")); // Member.java에 이름 필드를 어떻게 저장했는지 확인 필요
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return groupWalletService.getMembersByGroupId(id, pageable);
    }*/
    // 2. 여러 개의 페이지로 나누는 방식
    @ResponseBody
    @GetMapping("/{id}/member-list")
    public HashMap<String, Object> groupWalletMemberList(@PathVariable String id, @RequestParam(defaultValue = "1") int nowPage) {
        // 페이징 처리, 회원 이름순, Member.java에 이름 필드를 어떻게 저장했는지 확인 필요
        Pageable page = PageRequest.of((nowPage - 1), PAGE_SIZE, Sort.by(Sort.Order.asc("name")));
        Page<GroupMemberDto> memberPageList = (Page<GroupMemberDto>) groupWalletTabService.getMembersByGroupId(Long.parseLong(id), page);

        int temp = (nowPage - 1) % BLOCK_SIZE;
        int startPage = nowPage - temp;

        List<GroupMemberDto> groupMemberDtoList = groupWalletTabService.getMembersByGroupId(Long.parseLong(id), page);

        HashMap<String, Object> memberMap = new HashMap<String, Object>();
        memberMap.put("memberPageList", memberPageList); // 뷰에서 ${memberPageList.content}
        memberMap.put("blockCount", BLOCK_SIZE); // [1][2].. 몇개 사용
        memberMap.put("startPage", startPage);
        memberMap.put("nowPage", nowPage);

        return memberMap;

    }


    /**
     * 모임지갑 모임원 강퇴 요청
     * API 명세서 ROWNUM:18
     *
     * @param id     모임지갑 id
     * @param member 강퇴할 모임원 id
     */
    @ResponseBody
    @DeleteMapping("/{id}/{member}")
    public HashMap<String, Object> groupWalletMemberKick(@PathVariable String id, @PathVariable String member, @RequestParam(defaultValue = "1") int nowPage) {
        // 1. 멤버 삭제
        boolean isMemberDeleted = groupWalletTabService.deleteMember(Long.parseLong(id), Long.parseLong(member));

        // 2. 멤버가 성공적으로 삭제되었을 경우 멤버를 삭제한 후의 페이지 리턴
        if (isMemberDeleted) {
            Pageable page = PageRequest.of((nowPage - 1), PAGE_SIZE, Sort.by(Sort.Order.asc("name")));
            Page<GroupMemberDto> memberPageList = (Page<GroupMemberDto>) groupWalletTabService.getMembersByGroupId(Long.parseLong(id), page);

            int temp = (nowPage - 1) % BLOCK_SIZE;
            int startPage = nowPage - temp;

            List<GroupMemberDto> groupMemberDtoList = groupWalletTabService.getMembersByGroupId(Long.parseLong(id), page);

            HashMap<String, Object> memberMap = new HashMap<String, Object>();
            memberMap.put("memberPageList", memberPageList); // 뷰에서 ${memberPageList.content}
            memberMap.put("blockCount", BLOCK_SIZE); // [1][2].. 몇개 사용
            memberMap.put("startPage", startPage);
            memberMap.put("nowPage", nowPage);

            return memberMap;
            // 멤버가 없거나 삭제에 실패했을 경우 에러페이지로 이동
        } else {
            return null;
//            return "redirect:/error/error-message"; // 에러페이지 만들면 좋을 것 같음
        }

    }

    /**
     * 모임지갑 권한 부여 요청
     * API 명세서 ROWNUM:23
     *
     * @param id     모임지갑에 대한 권한을 요청하는 모임지갑 id
     * @param member 모임지갑에 대한 권한을 요청받는 회원 id
     */

    @ResponseBody
    @GetMapping("/{id}/{member}/auth")
    public HashMap<String, Object> groupWalletAuthRequest(@PathVariable String id, @PathVariable String member, @RequestParam(defaultValue = "1") int nowPage) {
        // 1. 멤버 권한 부여
        boolean isAuthGranted = groupWalletTabService.grantMemberAuth(Long.parseLong(id), Long.parseLong(member));

        // 2. 멤버 권한이 성공적으로 삭제되었을 경우 멤버 페이지 리턴
        if (isAuthGranted) {
            Pageable page = PageRequest.of((nowPage - 1), PAGE_SIZE, Sort.by(Sort.Order.asc("name")));
            Page<GroupMemberDto> memberPageList = (Page<GroupMemberDto>) groupWalletTabService.getMembersByGroupId(Long.parseLong(id), page);

            int temp = (nowPage - 1) % BLOCK_SIZE;
            int startPage = nowPage - temp;

            List<GroupMemberDto> groupMemberDtoList = groupWalletTabService.getMembersByGroupId(Long.parseLong(id), page);

            HashMap<String, Object> memberMap = new HashMap<String, Object>();
            memberMap.put("memberPageList", memberPageList); // 뷰에서 ${memberPageList.content}
            memberMap.put("blockCount", BLOCK_SIZE); // [1][2].. 몇개 사용
            memberMap.put("startPage", startPage);
            memberMap.put("nowPage", nowPage);

            return memberMap;
            // 멤버가 없거나 권한 삭제에 실패했을 경우 에러페이지로 이동
        } else {
            return null;
//            return "redirect:/error/error-message"; // 에러페이지 만들면 좋을 것 같음
        }
    }


    /**
     * 모임지갑 권한 박탈 요청
     * API 명세서 ROWNUM:24
     *
     * @param id     모임지갑에 대한 권한을 박탈하는 모임지갑 id
     * @param member 모임지갑에 대한 권한을 박탈당할 회원 id
     */

    @ResponseBody
    @DeleteMapping("/{id}/{member}/revoke")
    public HashMap<String, Object> groupwalletAuthRevoke(@PathVariable String id, @PathVariable String member, @RequestParam(defaultValue = "1") int nowPage) {
        // 1. 멤버 권한 박탈
        boolean isAuthRevoked = groupWalletTabService.revokeMemberAuth(Long.parseLong(id), Long.parseLong(member));


        // 2. 멤버 권한이 성공적으로 삭제되었을 경우 멤버 페이지 리턴
        if (isAuthRevoked) {
            Pageable page = PageRequest.of((nowPage - 1), PAGE_SIZE, Sort.by(Sort.Order.asc("name")));
            Page<GroupMemberDto> memberPageList = (Page<GroupMemberDto>) groupWalletTabService.getMembersByGroupId(Long.parseLong(id), page);

            int temp = (nowPage - 1) % BLOCK_SIZE;
            int startPage = nowPage - temp;

            List<GroupMemberDto> groupMemberDtoList = groupWalletTabService.getMembersByGroupId(Long.parseLong(id), page);

            HashMap<String, Object> memberMap = new HashMap<String, Object>();
            memberMap.put("memberPageList", memberPageList); // 뷰에서 ${memberPageList.content}
            memberMap.put("blockCount", BLOCK_SIZE); // [1][2].. 몇개 사용
            memberMap.put("startPage", startPage);
            memberMap.put("nowPage", nowPage);

            return memberMap;
            // 멤버가 없거나 권한 삭제에 실패했을 경우 에러페이지로 이동
        } else {
            return null;
//            return "redirect:/error/error-message"; // 에러페이지 만들면 좋을 것 같음
        }
    }


    //== 모임원 조회 탭 END ==//

    //== 규칙 탭 START ==//

    /**
     * 모임지갑 회비 규칙 조회
     * API 명세서 ROWNUM:19
     *
     * @param id 회비 규칙을 조회할 모임지갑 id
     */
    @ResponseBody
    @GetMapping("/{id}/rule")
    // 회비 테이블과 객체 필요해 보임, 회비 객체를 Rule.java로 가정함
    public RuleDto groupWalletRule(@PathVariable String id) {
        RuleDto ruleDto = groupWalletTabService.getRuleById(Long.parseLong(id));


        if (ruleDto != null) {
            return ruleDto;
        } else {
            return null;
//            return "redirect:/error/error-message"; // 에러페이지 만들면 좋을 것 같음
        }
    }


    /**
     * 모임지갑 회비 규칙 생성 요청
     * API 명세서 ROWNUM:20
     *
     * @param id 회비 규칙을 생성할 모임지갑 id
     */
    @PostMapping("{id}/rule")
    public String groupWalletCreateRule(@PathVariable String id, RuleDto ruleDto) {
        boolean isRuleCreated = groupWalletTabService.createRule(Long.parseLong(id), ruleDto);

        if (isRuleCreated) {
            return "redirect:/group-wallet/{id}/rule";
        } else {
            return "redirect:/error/error-message"; // 에러페이지 만들면 좋을 것 같음
        }

        /////////////////////////////////////
        // 규칙 만들기 폼으로 이동하는 api 만들어줘야 할듯 GroupWalletFormController.java에 추가
    }


    /**
     * 모임지갑 회비 납부 요청
     * API 명세서 ROWNUM:21
     *
     * @param id     회비 납부를 요청하는 모임지갑 id
     * @param member 회비 납부를 요청받을 회원 id
     */
    @GetMapping("/{id}/rule/{member}")
    public String groupWalletRuleAlert(@PathVariable String id, @PathVariable String member) {
        boolean isMemberAlerted = groupWalletTabService.alertMember(Long.parseLong(id), Long.parseLong(member));

        if (isMemberAlerted) {
            return "redirect:/group-wallet/{id}/rule";
        } else {
            return "redirect:/error/error-message"; // 에러페이지 만들면 좋을 것 같음
        }
    }


    /**
     * 모임지갑 회비 규칙 삭제 요청
     * API 명세서 ROWNUM:22
     *
     * @param id 회비 규칙을 삭제할 모임지갑 id
     */

    @DeleteMapping("/{id}/rule")
    public String groupWalletDeleteRule(@PathVariable String id) {
        boolean isRuleDeleted = groupWalletTabService.deleteRule(Long.parseLong(id));

        if (isRuleDeleted) {
            return "redirect:/group-wallet/{id}/rule";
        } else {
            return "redirect:/error/error-message"; // 에러페이지 만들면 좋을 것 같음
        }
    }

    //== 규칙 탭 END ==//

    //== 적금 탭 START ==//

    /**
     * 모임지갑 가입 적금상품 조회
     * API 명세서 ROWNUM:25
     *
     * @param id 가입 중인 적금 상품 조회를 요청하는 모임지갑 id
     */
    @ResponseBody
    @GetMapping("/{id}/saving")
    // Saving 테이블과 대응되는 객체를 Saving.java라고 가정한 코드
    public InstallmentDto groupWalletSavingInfo(@PathVariable String id) {
        InstallmentDto installmentDto = groupWalletTabService.getSavingById(Long.parseLong(id));

        if (installmentDto != null) {
            return installmentDto;
        } else {
            return null;
//            return "redirect:/error/error-message"; // 에러페이지 만들면 좋을 것 같음
        }
    }


    /**
     * 모임지갑 가입 적금상품 해지
     * API 명세서 ROWNUM:26
     *
     * @param id 가입 중인 적금 상품 해지를 요청하는 모임지갑 id
     */
    @DeleteMapping("/{id}/saving")
    public String groupWalletCancelSaving(@PathVariable String id) {
        boolean isSavingCanceled = groupWalletTabService.cancelSaving(Long.parseLong(id));

        if (isSavingCanceled) {
            return "redirect:/group-wallet/{id}/history";
        } else {
            return "redirect:/error/error-message"; // 에러페이지 만들면 좋을 것 같음
        }
    }

    //== 적금 탭 END ==//

    //== 카드 탭 START ==//

    /**
     * 모임지갑 카드 연결 현황 조회 요청
     * API 명세서 ROWNUM:27
     *
     * @param id 카드 연결 현황을 조회할 모임지갑 id
     */
    @ResponseBody
    @GetMapping("/{id}/card/list")
    // Card_issuance 테이블과 대응되는 객체를 Card.java라고 가정한 코드
    public List<CardIssuanceDto> groupWalletCardList(@PathVariable String id) {
        List<CardIssuanceDto> cardIssuanceDtoList = groupWalletTabService.getCard(Long.parseLong(id));

        return cardIssuanceDtoList;

//        if (cardIssuanceDtoList != null) {
//            return "redirect:/group-wallet/{id}/card/list";
//        } else {
//            return "redirect:/error/error-message"; // 에러페이지 만들면 좋을 것 같음
//        }
    }


    /**
     * 모임지갑 카드 연결 요청
     * API 명세서 ROWNUM:28
     *
     * @param id 카드 연결을 요청할 모임지갑 id
     */
    @ResponseBody
    @GetMapping("/{id}/card")
    public List<CardIssuanceDto> groupWalletCardLink(@PathVariable String id) {
        boolean isCardLinked = groupWalletTabService.linkCard(Long.parseLong(id), 1L);

        List<CardIssuanceDto> cardIssuanceDtoList = groupWalletTabService.getCard(Long.parseLong(id));

        return cardIssuanceDtoList;

//        if (isCardLinked) {
//            return "redirect:/group-wallet/{id}/card/list";
//        } else {
//            return "redirect:/error/error-message"; // 에러페이지 만들면 좋을 것 같음
//        }
    }

    //== 카드 탭 END ==//

    //== 내역 탭 START ==//

    /**
     * 모임지갑 내역 조회
     * API 명세서 ROWNUM:35
     *
     * @param id 내역 조회할 모임지갑 id
     */
    /* @author: hyun
    // 카드 내역 객체를 History.java라고 가정, 이체내역, 환전내역, 결제내역 중 어느 것을 의미? 
    // 3개 다 합친 것? Map으로 만들어야하나?
    public String groupWalletHistoryList(@PathVariable String id) {
        History history = groupWalletService.getHistory(id);

        if (history != null) {
            return "redirect:/group-wallet/{id}/history";
        } else {
            return "redirect:/error/error-message"; // 에러페이지 만들면 좋을 것 같음
        }
    }
     */
    @ResponseBody
    @GetMapping("/{id}/history")
    public Page<WalletHistoryDto> groupWalletHistoryList(@PathVariable String id, Model model, @RequestParam(defaultValue = "1") int nowPage) {
//        // 페이징 처리, 내역 날짜순, History.java에 날짜 필드를 어떻게 저장했는지 확인 필요
        Pageable page = PageRequest.of((nowPage - 1), PAGE_SIZE, Sort.by(Sort.Order.asc("date")));
//        // GroupWallet에서 쓸 정보들만 가져와서 History
//
//        Page<WalletHistoryDto> historyPageList = groupWalletTabService.getHistoryByGroupId(Long.parseLong(id), page);
//
//        int temp = (nowPage - 1) % BLOCK_SIZE;
//        int startPage = nowPage - temp;
//
//        model.addAttribute("pageList", historyPageList); // 뷰에서 ${pageList.content}
//        model.addAttribute("blockCount", BLOCK_SIZE); // [1][2].. 몇개 사용
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("nowPage", nowPage);

        Page<WalletHistoryDto> walletHistoryDtoList = groupWalletTabService.getHistoryByGroupId(Long.parseLong(id), page);
        return walletHistoryDtoList;

    }


    /**
     * 모임지갑 상세 내역 조회
     * API 명세서 ROWNUM:36
     *
     * @param id        상세 내역 조회할 모임지갑 id
     * @param historyid 상세 내역 조회할 내역 id
     */
    @ResponseBody
    @GetMapping("/{id}/{historyid}")
    // 카드 상세내역 객체를 History.java라고 가정, 이체내역, 환전내역, 결제내역 중 어느 것을 의미? 
    // 3개 다 합친 것? 대응되는 개념?
    public WalletHistoryDto groupWalletHistoryDetail(@PathVariable String id, @PathVariable String historyid, Model model) {
        WalletHistoryDto historyDetail = groupWalletTabService.getHistory(Long.parseLong(id), Long.parseLong(historyid), (String) model.getAttribute("type"));

        return historyDetail;
//        if (historyDetail != null) {
//            return "redirect:/group-wallet/{id}/history";
//        } else {
//            return "redirect:/error/error-message"; // 에러페이지 만들면 좋을 것 같음
//        }
    }

    //== 내역 탭 END ==//
}
