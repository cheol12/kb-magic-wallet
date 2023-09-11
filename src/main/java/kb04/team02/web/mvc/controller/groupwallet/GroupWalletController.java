package kb04.team02.web.mvc.controller.groupwallet;

import kb04.team02.web.mvc.service.groupwallet.GroupWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public void groupwalletIndex() {
    }

    /**
     * 모임지갑 생성 요청
     * API 명세서 ROWNUM:12
     */
    @PostMapping("/new")
    public void groupwalletCreat() {
    }

    /**
     * 개별 모임지갑 상세 페이지
     * API 명세서 ROWNUM:13
     *
     * @param id 조회할 모임지갑 id
     */
    @GetMapping("/{id}")
    public void groupwalletDetail(@PathVariable String id) {
    }

    /**
     * 모임지갑 삭제 요청
     * API 명세서 ROWNUM:14
     *
     * @param id 삭제할 모임지갑 id
     */
    @DeleteMapping("/{id}")
    public void groupwalletDelete(@PathVariable String id) {
    }

    /**
     * 모임지갑 초대 링크 생성 요청
     * API 명세서 ROWNUM:15
     *
     * @param id 초대링크를 생성할 모임지갑 id
     */
    @ResponseBody
    @GetMapping("/{id}/link")
    public void groupwalletCreateInviteLink(@PathVariable String id) {
    }

    /**
     * 모임지갑 자진 탈퇴 요청
     * API 명세서 ROWNUM:17
     *
     * @param id 자진 탈퇴 요청 모임원 id
     */
    @DeleteMapping("/{id}/out")
    public void groupwalletMemberOut(@PathVariable String id) {
    }

    /**
     * 모임지갑 꺼내기 요청
     * API 명세서 ROWNUM:30
     *
     * @param id 모임지갑에서 꺼내기 할 모임지갑 id
     */
    @PostMapping("/{id}/withdraw")
    public void groupwalletWithdraw(@PathVariable String id) {
    }

    /**
     * 모임지갑 정산 요청
     * API 명세서 ROWNUM:32
     *
     * @param id 정산을 진행할 모임지갑 id
     */
    @PostMapping("/{id}/settle")
    public void groupwalletSettle(@PathVariable String id) {
    }

    /**
     * 모임지갑 입금 요청
     * API 명세서 ROWNUM:34
     *
     * @param id 입금할 모임지갑 id
     */
    @PostMapping("/{id}/deposit")
    public void groupwalletDeposit(@PathVariable String id) {
    }

}
