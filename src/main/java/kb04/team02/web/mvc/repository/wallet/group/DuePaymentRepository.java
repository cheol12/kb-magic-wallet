package kb04.team02.web.mvc.repository.wallet.group;

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
