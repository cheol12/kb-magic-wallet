package kb04.team02.web.mvc.saving.repository;

import kb04.team02.web.mvc.saving.entity.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingRepository extends JpaRepository<Saving, Long> {
    /*
      ROWNUM 41

      SQL
      SELECT *
      FROM SAVING

      JPA: InstallmentSavingRepository.findAll()
     */

    /*
      ROWNUM 42

      SQL
      SELECT *
      FROM SAVING
      WHERE SAVING.ID = ID

      JPA: InstallmentSavingRepository.findById(id)
     */
}
