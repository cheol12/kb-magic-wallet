package kb04.team02.web.mvc.repository.saving;

import kb04.team02.web.mvc.domain.saving.InstallmentSaving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstallmentSavingRepository extends JpaRepository<InstallmentSaving, Long> {

}
