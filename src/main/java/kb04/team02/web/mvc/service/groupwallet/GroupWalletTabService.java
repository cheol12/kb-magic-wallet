package kb04.team02.web.mvc.service.groupwallet;

import kb04.team02.web.mvc.domain.card.CardIssuance;
import kb04.team02.web.mvc.domain.member.Member;
import kb04.team02.web.mvc.domain.saving.Saving;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupWalletTabService {

    /**
     * 멤버 전체 검색 - Page 처리
     * @param id
     * @param pageable
     * @return
     */
    List<Member> getMembersByGroupId(String id, Pageable pageable);

    /**
     * 모임지갑 모임원 삭제
     * @param id
     * @param member
     * @return
     */
    boolean deleteMember(String id, String member);

    /**
     * 모임지갑 권한 부여
     * @param id
     * @param member
     * @return
     */
    boolean GrantMemberAuth(String id, String member);

    /**
     * 모임지갑 권한 박탈
     * @param id
     * @param member
     * @return
     */
    boolean RevokeMemberAuth(String id, String member);

    /**
     * 모임지갑 회비 규칙 조회
     * @param id
     * @return
     */
    Rule getRuleById(String id);

    /**
     * 모임지갑 회비 규칙 생성
     * @param id
     * @return
     */
    boolean createRule(String id);

    /**
     * 모임지갑 회비 납부 요청 요청
     * @param id
     * @param member
     * @return
     */
    boolean alertMember(String id, String member);

    /**
     * 모임지갑 회비 규칙 삭제 요청
     * @param id
     * @return
     */
    boolean deleteRule(String id);

    /**
     * 모임지갑 가입 적금상품 조회
     *
     * @param id
     * @return
     */
    Saving getSavingById(String id);

    /**
     * 모임지갑 가입 적금상품 해지
     * @param id
     * @return
     */
    boolean cancelSaving(String id);

    /**
     * 모임지갑 카드 연결 현황 조회 요청
     * @param id
     * @return
     */
    CardIssuance getCard(String id);

    /**
     * 모임지갑 카드 연결 요청
     * @param id
     * @return
     */
    boolean linkCard(String id);

    /**
     * 모임지갑 내역 조회
     * @param id
     * @param page
     * @return
     */
    Page<History> getHistoryByGroupId(String id, Pageable page);

    /**
     * 모임지갑 상세 내역 조회
     * @param id
     * @return
     */
    HistoryDetail getHistory(String id);
}
