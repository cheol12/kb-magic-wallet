package kb04.team02.web.mvc.repository.bank;

import kb04.team02.web.mvc.domain.bank.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    /*
    ROWNUM 43

    SQL
    SELECT * FROM BANK

    JPA: BankRepository.findAll()
    */

    /*
    ROWNUM
    */
}
