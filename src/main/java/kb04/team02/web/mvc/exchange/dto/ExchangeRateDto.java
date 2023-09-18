package kb04.team02.web.mvc.exchange.dto;

import kb04.team02.web.mvc.common.entity.CurrencyCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeRateDto {
    private CurrencyCode currencyCode;
    private Double tradingBaseRate;
    private Double telegraphicTransferBuyingRate;
    private Double telegraphicTransferSellingRate;
    private Double buyingRate;
    private Double sellingRate;

}
