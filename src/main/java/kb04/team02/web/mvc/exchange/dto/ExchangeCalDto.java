package kb04.team02.web.mvc.exchange.dto;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(example = "환율 적용 금액")
    private Long expectedAmount;
    @ApiModelProperty(example = "현재 고시 환율")
    private Double tradingBaseRate;
    @ApiModelProperty(example = "적용 환율")
    private Double applicableExchangeRate;
}
