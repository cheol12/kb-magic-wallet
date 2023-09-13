package kb04.team02.web.mvc.repository.saving;

import kb04.team02.web.mvc.domain.saving.InstallmentSaving;
import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstallmentSavingRepository extends JpaRepository<InstallmentSaving, Long> {


    // 모임지갑으로 적금 가입 내용 조회
    InstallmentSaving findByGroupWalletAndDone(GroupWallet groupWallet, boolean done);

}
