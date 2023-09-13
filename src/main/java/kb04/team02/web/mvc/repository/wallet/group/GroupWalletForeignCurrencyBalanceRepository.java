package kb04.team02.web.mvc.repository.wallet.group;

import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import kb04.team02.web.mvc.domain.wallet.group.GroupWalletForeignCurrencyBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupWalletForeignCurrencyBalanceRepository extends JpaRepository<GroupWalletForeignCurrencyBalance, Long> {

    /**
     * 모임지갑 외화잔액 내역
     *
     */
    List<GroupWalletForeignCurrencyBalance> findByGroupWallet(Long groupWalletId);
}
