package kb04.team02.web.mvc.repository.wallet.group;

import kb04.team02.web.mvc.domain.wallet.group.GroupWalletPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupWalletPaymentRepository extends JpaRepository<GroupWalletPayment, Long> {
    /**
     * 모임지갑 결제내역
     * ROWNUM 35?
     * 
     * SQL
     * INSERT INTO GROUP_WALLET_PAYMENT
     * (CURRENCY_CODE, INSERT_DATE, TYPE, PAYMENT_PLACE, PAYMENT_CATEGORY, AMOUNT, BALANCE, GROUP_WALLET_ID)
     * VALUES
     * (CURRENCY_CODE, SYSDATE, TYPE, PAYMENT_PLACE, PAYMENT_CATEGORY, AMOUNT, BALANCE, GROUP_WALLET_ID);
     * 
     * JPA: GroupWalletPayment.save(GroupWalletPayment, groupWalletPayment);
     */

}
