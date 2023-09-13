package kb04.team02.web.mvc.repository.bank;

import kb04.team02.web.mvc.domain.bank.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
}
