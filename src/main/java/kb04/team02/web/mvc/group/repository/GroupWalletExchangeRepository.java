package kb04.team02.web.mvc.group.repository;

import kb04.team02.web.mvc.group.entity.GroupWallet;
import kb04.team02.web.mvc.group.entity.GroupWalletExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupWalletExchangeRepository  extends JpaRepository<GroupWalletExchange, Long> {
    
    
    /**
     * 모임지갑 환전내역 입력하기
     * ROWNUM 35?
     * 
     * SQL
     * INSERT INTO GROUP_WALLET_EXCHANGE
     * (INSERT_DATE, SELL_CURRENCY_CODE, SELL_AMOUNT, SELL_BALANCE, BUY_CURRENCY_MODE, BUY_AMOUNT, BUY_BALANCE, EXCHANGE_RATE, GROUP_WALLET_ID)
     * VALUES
     * (SYSDATE, SELL_CURRENCY_CODE, SELL_AMOUNT, SELL_BALANCE, BUY_CURRENCY_MODE, BUY_AMOUNT, BUY_BALANCE, EXCHANGE_RATE, GROUP_WALLET_ID)
     * 
     * JPA: GroupWalletExchangeRepository.save(GroupWalletExchange, groupWalletExchange);
     */


    /**
     * 모임지갑 환전내역 불러오기
     * */
    List<GroupWalletExchange> findByGroupWallet(GroupWallet groupWallet);

    List<GroupWalletExchange> searchAllByGroupWallet(GroupWallet groupWallet);

}
