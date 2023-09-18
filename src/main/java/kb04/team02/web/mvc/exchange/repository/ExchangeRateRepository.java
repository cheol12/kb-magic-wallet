package kb04.team02.web.mvc.exchange.repository;

import kb04.team02.web.mvc.exchange.entity.ExchangeRate;
import kb04.team02.web.mvc.common.entity.CurrencyCode;
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
    Optional<ExchangeRate> findFirstByCurrencyCodeOrderByInsertDateDesc(CurrencyCode currencyCode);

}
