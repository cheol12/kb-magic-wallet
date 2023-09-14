package kb04.team02.web.mvc.repository.bank;

import kb04.team02.web.mvc.domain.bank.ExchangeRate;
import kb04.team02.web.mvc.domain.common.CurrencyCode;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    @Override
    <S extends ExchangeRate> Optional<S> findOne(Example<S> example);

    /*
            ROWNUM: 56

            SQL
            select * from exchange_rate where currency_code = ?
         */
    Optional<ExchangeRate> findExchangeRateByCurrencyCode(CurrencyCode currencyCode);

}
