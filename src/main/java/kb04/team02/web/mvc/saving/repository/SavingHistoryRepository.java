package kb04.team02.web.mvc.saving.repository;

import kb04.team02.web.mvc.saving.entity.SavingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingHistoryRepository extends JpaRepository<SavingHistory, Long> {
}
