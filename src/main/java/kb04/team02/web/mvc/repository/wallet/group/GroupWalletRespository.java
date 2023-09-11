package kb04.team02.web.mvc.repository.wallet.group;

import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupWalletRespository extends JpaRepository<GroupWallet, Long> {
    /**
     * 모임지갑
     * ROWNUM 13
     * 
     * SQL
     * INSERT INTO GROUP_WALLET
     * (NICKNAME, INSERT_DATE, BALANCE, MEMBER_ID, DUE_ONOFF, DUE_ACCUMULATION, DUE_DATE, DUE)
     * VALUES
     * (NICKNAME, SYSDATE, BALANCE, MEMBER_ID, DUE_ONOFF, DUE_ACCUMULATION, DUE_DATE, DUE);
     * 
     * JPA: GroupWalletPayment.save(GroupWalletPayment, groupWalletPayment);
     */    
}
