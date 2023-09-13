package kb04.team02.web.mvc.repository.saving;

import kb04.team02.web.mvc.domain.saving.SavingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingHistoryRepository extends JpaRepository<SavingHistory, Long> {
}
