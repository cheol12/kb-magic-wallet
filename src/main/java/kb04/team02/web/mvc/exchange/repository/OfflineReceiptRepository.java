package kb04.team02.web.mvc.exchange.repository;

import kb04.team02.web.mvc.exchange.entity.OfflineReceipt;
import kb04.team02.web.mvc.group.entity.GroupWallet;
import kb04.team02.web.mvc.personal.entity.PersonalWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfflineReceiptRepository extends JpaRepository<OfflineReceipt, Long> {
    /*
      ROWNUM 42

      SQL
      select *
      from Offline_Receipt
      where personal_wallet_id = personal_wallet_id
      or group_wallet_id = group_wallet_id;

      JPA: X / QueryDSL O
     */
    List<OfflineReceipt> findAllByPersonalWallet(PersonalWallet personalWallet);
    List<OfflineReceipt> findAllByGroupWallet(GroupWallet groupWallet);

    /*
    ROWNUM 44

    SQL
    insert into offline_receipt
    (application_date, receipt_date, currency_code, amount, bank_id, personal_wallet_id, group_wallet_id)
    values
    (sysdate, [수령일], [통화종류], [금액], [session.은행 ID], [개인 지갑 정보 or null] , [그룹지갑 정보 or null])

    JPA: OfflineReceiptRepository.save(OfflineReceipt offlineReceipt)
     */

    /*
    ROWNUM 45

    SQL
    update offline_receipt
    set status ‘요청 취소’
    where receipt_id = [선택한 receipt_id]

    JPA: OfflineReceiptRepository.save(OfflineReceipt offlineReceipt)
     */

    /*
    ROWNUM
     */



}
