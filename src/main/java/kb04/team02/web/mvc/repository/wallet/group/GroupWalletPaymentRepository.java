package kb04.team02.web.mvc.repository.wallet.group;

import kb04.team02.web.mvc.domain.wallet.group.GroupWalletExchange;
import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import kb04.team02.web.mvc.domain.wallet.group.GroupWalletPayment;
import kb04.team02.web.mvc.domain.wallet.group.GroupWalletTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupWalletPaymentRepository extends JpaRepository<GroupWalletPayment, Long> {
    /**
     * 모임지갑 결제내역 insert
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


    /**
     * 모임지갑 결제내역 내역 불러오기
     * */
    List<GroupWalletPayment> findByGroupWallet(Long groupWalletId);

    List<GroupWalletPayment> searchAllByGroupWallet(GroupWallet groupWallet);

}
