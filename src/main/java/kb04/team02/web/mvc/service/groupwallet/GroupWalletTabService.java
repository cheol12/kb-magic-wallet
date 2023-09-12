package kb04.team02.web.mvc.service.groupwallet;

import kb04.team02.web.mvc.domain.card.CardIssuance;
import kb04.team02.web.mvc.domain.member.Member;
import kb04.team02.web.mvc.domain.saving.Saving;
import kb04.team02.web.mvc.dto.*;
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
    List<GroupMemberDto> getMembersByGroupId(String id, Pageable pageable);

    /**
     * 모임지갑 모임원 삭제
     * @param id
     * @param member
     * @return
     */
    boolean deleteMember(Long id, Long member);

    /**
     * 모임지갑 권한 부여
     * @param id
     * @param member
     * @return
     */
    boolean GrantMemberAuth(Long id, Long member);

    /**
     * 모임지갑 권한 박탈
     * @param id
     * @param member
     * @return
     */
    boolean RevokeMemberAuth(Long id, Long member);

    /**
     * 모임지갑 회비 규칙 조회
     * @param id
     * @return
     */
    RuleDto getRuleById(Long id);

    /**
     * 모임지갑 회비 규칙 생성
     * @param ruleDto
     * @return
     */
    boolean createRule(RuleDto ruleDto);

    /**
     * 모임지갑 회비 납부 요청 요청
     * @param id
     * @param member
     * @return
     */
    boolean alertMember(Long id, Long member);

    /**
     * 모임지갑 회비 규칙 삭제 요청
     * @param id
     * @return
     */
    boolean deleteRule(Long id);

    /**
     * 모임지갑 가입 적금상품 조회
     *
     * @param id
     * @return
     */
    SavingDto getSavingById(Long id);

    /**
     * 모임지갑 가입 적금상품 해지
     * @param id
     * @return
     */
    boolean cancelSaving(Long id);

    /**
     * 모임지갑 카드 연결 현황 조회 요청
     * @param id
     * @return
     */
    List<CardIssuanceDto> getCard(Long id);

    /**
     * 모임지갑 카드 연결 요청
     * @param id
     * @return
     */
    boolean linkCard(Long id);

    /**
     * 모임지갑 내역 조회
     * @param id
     * @param page
     * @return
     */
    Page<WalletHistoryDto> getHistoryByGroupId(Long id, Pageable page);

    /**
     * 모임지갑 상세 내역 조회
     * @param id
     * @return
     */
    WalletHistoryDto getHistory(Long id);
}
