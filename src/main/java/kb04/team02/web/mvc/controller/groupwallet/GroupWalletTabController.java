package kb04.team02.web.mvc.controller.groupwallet;

import kb04.team02.web.mvc.service.groupwallet.GroupWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/group-wallet")
@RequiredArgsConstructor
public class GroupWalletTabController {

    private final GroupWalletService groupWalletService;

    //== 모임원 조회 탭 START ==//
    /**
     * 모임지갑 모임원 리스트 조회 요청
     * API 명세서 ROWNUM:16
     *
     * @param id 모임원 리스트를 조회할 모임지갑 id
     */
    @ResponseBody
    @GetMapping("/{id}/member-list")
    public void groupwalletMemberList(@PathVariable String id) {
    }

    /**
     * 모임지갑 모임원 강퇴 요청
     * API 명세서 ROWNUM:18
     *
     * @param id 강퇴할 모임원 id
     * @param member 강퇴할 모임원 id
     */
    @ResponseBody
    @DeleteMapping("/{id}/{member}")
    public void groupwalletMemberKick(@PathVariable String id, @PathVariable String member) {
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
    public void groupwalletAuthRequest(@PathVariable String id, @PathVariable String member) {
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
    public void groupwalletAuthRevoke(@PathVariable String id, @PathVariable String member) {
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
    public void groupwalletRule(@PathVariable String id) {
    }

    /**
     * 모임지갑 회비 규칙 생성 요청
     * API 명세서 ROWNUM:20
     *
     * @param id 회비 규칙을 생성할 모임지갑 id
     */
    @PostMapping("{id}/rule")
    public void groupWalletCreateRule(@PathVariable String id) {
    }

    /**
     * 모임지갑 회비 납부 요청
     * API 명세서 ROWNUM:21
     *
     * @param id     회비 납부를 요청하는 모임지갑 id
     * @param member 회비 납부를 요청받을 회원 id
     */
    @GetMapping("/{id}/rule/{member}")
    public void groupwalletRuleAlert(@PathVariable String id, @PathVariable String member) {
    }

    /**
     * 모임지갑 회비 규칙 삭제 요청
     * API 명세서 ROWNUM:22
     *
     * @param id 회비 규칙을 삭제할 모임지갑 id
     */
    @DeleteMapping("/{id}/rule")
    public void groupwalletDeleteRule(@PathVariable String id) {
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
    public void groupwalletSavingInfo(@PathVariable String id) {
    }

    /**
     * 모임지갑 가입 적금상품 해지
     * API 명세서 ROWNUM:26
     *
     * @param id 가입 중인 적금 상품 해지를 요청하는 모임지갑 id
     */
    @DeleteMapping("/{id}/saving")
    public void groupwalletCancelSaving(@PathVariable String id) {
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
    public void groupwalletCardList(@PathVariable String id) {
    }

    /**
     * 모임지갑 카드 연결 요청
     * API 명세서 ROWNUM:28
     *
     * @param id 카드 연결을 요청할 모임지갑 id
     */
    @ResponseBody
    @GetMapping("/{id}/card")
    public void groupwalletCardLink(@PathVariable String id) {
    }
    //== 카드 탭 END ==//

    //== 내역 탭 START ==//
    /**
     * 모임지갑 내역 조회
     * API 명세서 ROWNUM:35
     *
     * @param id 내역 조회할 모임지갑 id
     */
    @ResponseBody
    @GetMapping("/{id}/history")
    public void groupwalletHistoryList(@PathVariable String id) {
    }

    /**
     * 모임지갑 상세 내역 조회
     * API 명세서 ROWNUM:36
     *
     * @param id 상세 내역 조회할 모임지갑 id
     * @param historyid 상세 내역 조회할 내역 id
     */
    @ResponseBody
    @GetMapping("/{id}/{historyid}")
    public void groupwalletHistoryDetail(@PathVariable String id, @PathVariable String historyid) {
    }
    //== 내역 탭 END ==//
}
