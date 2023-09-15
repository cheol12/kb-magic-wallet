package kb04.team02.web.mvc.group.repository;

import kb04.team02.web.mvc.group.entity.GroupWallet;
import kb04.team02.web.mvc.group.entity.GroupWalletTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupWalletTransferRepository extends JpaRepository<GroupWalletTransfer, Long> {


    /**
     * ROWNUM 30, 32, 34 함께 사용
     *
     * SQL
     *
     * (모임지갑 꺼내기 시 모임지갑의 출금으로 사용)
     * insert into
     * group_wallet_transfer(transfer_seq, insert_date, type, from, to, amount, balance, group_wallet_id)
     * values(transfer_seq.nextval, sysdate, “출금”, “현재모임지갑”, “내내 개지갑”, “입력값”, balance, “현재모임지갑”);
     * (balance=이체 후 잔액은 컨트롤러에서 처리하기)
     *
     * JPA : GroupWalletTransferRepository.save(GroupWalletTransfer groupWalletTransfer);
     * */



    /**
     * ROWNUM 32
     *
     * SQL
     *
     * (모임지갑 정산 시 모임지갑의 출금으로 사용)
     * insert into group_wallet_transfer
     * (transfer_seq, insert_date, type, from, to, amount, balance, group_wallet_id)
     * values
     * (transfer_seq.nextval, sysdate, “출금정산”, “모임지갑”, “각자 개인지갑”, 정산금/N, balance, “현재 모임지갑 식별번호”)
     *
     * JPA : GroupWalletTransferRepository.save(GroupWalletTransfer groupWalletTransfer);
     * */

    /**
     * ROWNUM 34
     * <p>
     * SQL
     * <p>
     * (모임지갑 입금 시 입금으로 사용)
     * insert into group_wallet_transfer
     * (transfer_seq, insert_date, type, from, to, amount, balance, group_wallet_id)
     * values
     * (transfer_seq.nextval, sysdate, “입금”, “모임지갑”, “회비 낸 사람 지갑”, 회비, balance + 회비, “현재 모임지갑 식별번호”)
     * <p>
     * JPA : GroupWalletTransferRepository.save(GroupWalletTransfer groupWalletTransfer);
     */

    List<GroupWalletTransfer> findByGroupWallet(Long groupWalletId);

    List<GroupWalletTransfer> searchAllByGroupWallet(GroupWallet groupWallet);
}
