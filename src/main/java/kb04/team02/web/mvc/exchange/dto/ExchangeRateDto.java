package kb04.team02.web.mvc.exchange.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import kb04.team02.web.mvc.common.entity.CurrencyCode;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ExchangeRateDto {
    @ApiModelProperty(example = "통화 코드")
    private CurrencyCode currencyCode;
    @ApiModelProperty(example = "매매 기준율")
    private Double tradingBaseRate;
    @ApiModelProperty(example = "전신환 매입율")
    private Double telegraphicTransferBuyingRate;
    @ApiModelProperty(example = "전신환 매도율")
    private Double telegraphicTransferSellingRate;
    @ApiModelProperty(example = "현찰 매입율")
    private Double buyingRate;
    @ApiModelProperty(example = "현찰 매도율")
    private Double sellingRate;

    @JsonFormat
    @ApiModelProperty(example = "환율 입력일")
    private LocalDateTime insertDate;
}
