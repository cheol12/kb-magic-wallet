package kb04.team02.web.mvc.saving.repository;

import kb04.team02.web.mvc.saving.entity.InstallmentSaving;
import kb04.team02.web.mvc.saving.entity.SavingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingHistoryRepository extends JpaRepository<SavingHistory, Long> {
    //    List<InstallmentSaving> findAllByGroupWallet(GroupWallet groupWallet);
    List<SavingHistory> findSavingHistoriesByInstallmentSaving(InstallmentSaving installmentSaving);
}
