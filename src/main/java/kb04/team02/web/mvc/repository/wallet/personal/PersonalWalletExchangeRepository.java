package kb04.team02.web.mvc.repository.wallet.personal;

import kb04.team02.web.mvc.domain.wallet.personal.PersonalWallet;
import kb04.team02.web.mvc.domain.wallet.personal.PersonalWalletExchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalWalletExchangeRepository extends JpaRepository<PersonalWalletExchange, Long> {

    /**
     * row 5-3
     *
     * select * personal_wallet_exchange where personal_wallet_id = [personal_wallet_id]
     */
    List<PersonalWalletExchange> searchAllByPersonalWallet(PersonalWallet pw);
}
