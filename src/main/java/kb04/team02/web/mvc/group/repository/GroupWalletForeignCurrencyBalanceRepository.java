package kb04.team02.web.mvc.group.repository;

import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.group.entity.GroupWallet;
import kb04.team02.web.mvc.group.entity.GroupWalletForeignCurrencyBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupWalletForeignCurrencyBalanceRepository extends JpaRepository<GroupWalletForeignCurrencyBalance, Long> {

    /**
     * 모임지갑 외화잔액 내역
     *
     */

    List<GroupWalletForeignCurrencyBalance> findByGroupWallet(Long groupWalletId);

    /**
     * 외화코드에 해당하는 잔액 조회
     * @param code
     * @param gw
     * @return
     */
    Optional<GroupWalletForeignCurrencyBalance> findGroupWalletForeignCurrencyBalanceByCurrencyCodeAndGroupWallet(CurrencyCode code, GroupWallet gw);

    List<GroupWalletForeignCurrencyBalance> findByGroupWallet(GroupWallet groupWallet);

}
