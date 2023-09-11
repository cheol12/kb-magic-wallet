package kb04.team02.web.mvc.repository.wallet.personal;

import kb04.team02.web.mvc.domain.wallet.personal.PersonalWallet;
import kb04.team02.web.mvc.domain.wallet.personal.PersonalWalletForeignCurrencyBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalWalletForeignCurrencyBalanceRepository extends JpaRepository<PersonalWalletForeignCurrencyBalance, Long> {
    /**
     * row 5-2
     *
     * SELECT CURRENCY_CODE, BALANCE FROM PERSONAL_WALLET_BALANCE WHERE PERSONAL_WALLET_ID=[위에서 찾은 PERSONAL_WALLET_ID]
     */
    List<PersonalWalletForeignCurrencyBalance> searchAllByPersonalWallet(PersonalWallet pw);

}
