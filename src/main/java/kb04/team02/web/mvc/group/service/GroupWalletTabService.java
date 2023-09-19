package kb04.team02.web.mvc.group.service;

import kb04.team02.web.mvc.common.dto.WalletHistoryDto;
import kb04.team02.web.mvc.group.dto.RuleDto;
import kb04.team02.web.mvc.group.dto.CardIssuanceDto;
import kb04.team02.web.mvc.group.dto.GroupMemberDto;
import kb04.team02.web.mvc.group.dto.InstallmentDto;
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
    List<GroupMemberDto> getMembersByGroupId(Long id, Pageable pageable);

    /**
     * 모임지갑 모임원 삭제
     * @param id
     * @param member
     * @return
     */
    boolean deleteMember(Long id, Long member);

    /**
     * 모임지갑 공동모임장 권한 부여
     * @param id
     * @param member
     * @return
     */
    boolean grantMemberAuth(Long id, Long member);

    /**
     * 모임지갑 공동모임장 권한 박탈
     * @param id
     * @param member
     * @return
     */
    boolean revokeMemberAuth(Long id, Long member);

    /**
     * 모임지갑 회비 규칙 조회
     * @param id
     * @return
     */
    RuleDto getRuleById(Long id, Long memberId);

    /**
     * 모임지갑 회비 규칙 생성
     * @param ruleDto
     * @return
     */
    boolean createRule(Long id, RuleDto ruleDto);

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
    InstallmentDto getSavingById(Long id);

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
    boolean linkCard(Long id, Long memberId);

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
    WalletHistoryDto getHistory(Long id, Long historyId, String type);
}
