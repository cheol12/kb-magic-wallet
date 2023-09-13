package kb04.team02.web.mvc.repository.bank;

import kb04.team02.web.mvc.domain.bank.ExchangeRate;
import kb04.team02.web.mvc.domain.common.CurrencyCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    /*
        ROWNUM: 56

        SQL
        select * from exchange_rate where currency_code = ?
     */
    ExchangeRate findExchangeRateByCurrencyCode(CurrencyCode currencyCode);

}
