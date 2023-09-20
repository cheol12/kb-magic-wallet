package kb04.team02.web.mvc.group.repository;

import kb04.team02.web.mvc.group.entity.DuePayment;
import kb04.team02.web.mvc.group.entity.GroupWallet;
import kb04.team02.web.mvc.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DuePaymentRepository extends JpaRepository<DuePayment, Long> {

    /**
     * 회비 납부 내역
     * ROWNUM 19
     * <p>
     * SQL
     * INSERT INTO DUE_PAYMENT
     * (INSERT_DATE, MEMBER_ID, GROUP_WALLET_ID)
     * VALUES
     * (SYSDATE, MEMBER_ID, GROUP_WALLET_ID)
     * <p>
     * JPA: DuePaymentRepository.save(DuePayment duePayment);
     */

    /**
     * 이번달에 납부 했는지
     */
    Optional<DuePayment> findByGroupWalletAndMemberAndInsertDateAfter(GroupWallet groupWallet, Member member, LocalDateTime firstDayOfThisMonth);

    /**
     * 총 납부 일수
     */
    int countByGroupWalletAndMember(GroupWallet groupWallet, Member member);
}
