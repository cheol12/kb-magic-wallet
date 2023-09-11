package kb04.team02.web.mvc.repository.wallet.group;

@Repository
public interface GroupWalletTransferRepository extends JpaRepository <GroupWalletTransfer, Long>{
    /**
     * 모임지갑 이체내역
     * ROWNUM 35?
     * 
     * SQL
     * INSERT INTO GROUP_WALLET_TRANSFER
     * (INSERT_DATE, TYPE, FROM_TYPE, FROM, TO, TO_TYPE, AMOUNT, BALANCE, GROUP_WALLET_ID, CURRENCY_CODE)
     * VALUES
     * (SYSDATE, TYPE, FROM_TYPE, FROM, TO, TO_TYPE, AMOUNT, BALANCE, GROUP_WALLET_ID, CURRENCY_CODE);
     * 
     * JPA: GroupWalletTransferRepository.save(GroupWalletTransfer, groupWalletTransfer);
     */    
}
