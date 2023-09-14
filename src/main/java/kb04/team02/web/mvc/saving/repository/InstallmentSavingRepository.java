package kb04.team02.web.mvc.saving.repository;

import kb04.team02.web.mvc.saving.entity.InstallmentSaving;
import kb04.team02.web.mvc.group.entity.GroupWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstallmentSavingRepository extends JpaRepository<InstallmentSaving, Long> {


    // 모임지갑으로 적금 가입 내용 조회
    InstallmentSaving findByGroupWalletAndDone(GroupWallet groupWallet, boolean done);

}
