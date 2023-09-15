package kb04.team02.web.mvc.group.repository;

import kb04.team02.web.mvc.group.entity.DuePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DuePaymentRepository extends JpaRepository<DuePayment, Long> {

    /**
     * 회비 납부 내역
     *  ROWNUM 19
     * 
     * SQL
     * INSERT INTO DUE_PAYMENT
     * (INSERT_DATE, MEMBER_ID, GROUP_WALLET_ID)
     * VALUES
     * (SYSDATE, MEMBER_ID, GROUP_WALLET_ID)
     * 
     * JPA: DuePaymentRepository.save(DuePayment duePayment);
     */
    
}
