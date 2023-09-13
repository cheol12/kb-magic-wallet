package kb04.team02.web.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeCalDto {
    // 계산 금액, 현재 고시 환율, 적용 환율
    private Long expectedAmount;
    private Double tradingBaseRate;
    private Double applicableExchangeRate;
}
