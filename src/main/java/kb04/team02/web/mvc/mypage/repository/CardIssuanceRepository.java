package kb04.team02.web.mvc.mypage.repository;

import kb04.team02.web.mvc.common.entity.WalletType;
import kb04.team02.web.mvc.mypage.entity.CardIssuance;
import kb04.team02.web.mvc.mypage.entity.CardState;
import kb04.team02.web.mvc.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardIssuanceRepository extends JpaRepository<CardIssuance, Long> {

    /**
     * ROWNUM 51
     *
     * 카드 신청
     *
     * SQL
     * insert into card_ issuance(card_number, insert_date, member_id)
     * values([생성 규칙에 따른 카드 번호], [sysdate], [session.member_id])
     *
     * JPA: CardIssuanceRepository.save(CardIssuance cardIssuance)
     */

    /**
     * ROWNUM 52
     *
     * 카드 분실 method
     *
     * SQL
     * update card_ issuance set status=’사용 정지’ where card_number=[기존 card_number])
     *
     * @Param card_number
     */
    Optional<CardIssuance> findCardIssuanceByCardNumber(String card_number);

    /**
     * 멤버의 최신 카드 하나만 가져오기
     */
    Optional<CardIssuance> findFirstByMemberOrderByInsertDateDesc(Member member);

    List<CardIssuance> findAllByWalletId(Long walletId);

    CardIssuance findByMemberAndCardState(Member member, CardState cardState);

    /**
     * @author 김철
     * 모임지갑 연결 카드 조회
     * */
    List<CardIssuance> findByWalletIdAndWalletTypeAndCardState(Long walletId, WalletType type, CardState state);
}
