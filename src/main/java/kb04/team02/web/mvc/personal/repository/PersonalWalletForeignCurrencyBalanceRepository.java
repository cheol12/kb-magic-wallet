package kb04.team02.web.mvc.personal.repository;

import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.personal.entity.PersonalWallet;
import kb04.team02.web.mvc.personal.entity.PersonalWalletForeignCurrencyBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonalWalletForeignCurrencyBalanceRepository extends JpaRepository<PersonalWalletForeignCurrencyBalance, Long> {
    /**
     * row 5-2
     *
     * SELECT CURRENCY_CODE, BALANCE FROM PERSONAL_WALLET_BALANCE WHERE PERSONAL_WALLET_ID=[위에서 찾은 PERSONAL_WALLET_ID]
     */
    List<PersonalWalletForeignCurrencyBalance> searchAllByPersonalWallet(PersonalWallet pw);

    /**
     * ROWNUM: 48
     * @param code
     * @param pw
     * @return
     */
    Optional<PersonalWalletForeignCurrencyBalance> findPersonalWalletForeignCurrencyBalanceByCurrencyCodeAndPersonalWallet(CurrencyCode code, PersonalWallet pw);
}
