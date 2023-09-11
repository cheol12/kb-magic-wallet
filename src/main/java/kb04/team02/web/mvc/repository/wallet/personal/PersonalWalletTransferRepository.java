package kb04.team02.web.mvc.repository.wallet.personal;

import kb04.team02.web.mvc.domain.wallet.personal.PersonalWallet;
import kb04.team02.web.mvc.domain.wallet.personal.PersonalWalletTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalWalletTransferRepository extends JpaRepository<PersonalWalletTransfer, Long> {

    /**
     * row 5-5
     *
     * select * personal_wallet_transfer where personal_wallet_id = [personal_wallet_id];
     */

    List<PersonalWalletTransfer> searchAllByPersonalWallet(PersonalWallet pw);


    /**
     * row7-2
     *
     * INSERT INTO PERSONAL_WALLET_TRANSFER(INSERT_DATE, TYPE, FROM, TO, AMOUNT, BALANCE)
     * VALUES(SYSDATE, [입금], [MEMBER.BANK_AMOUNT], [PERSONAL_WALLET_ID], [입력 금액], [P.BALANCE]
     *
     * JPA : PersonalWalletTransferRepository.save(PersonalWalletTransfer pwTransfer);
     */
}
