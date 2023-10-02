package kb04.team02.web.mvc.exchange.dto;

import io.swagger.annotations.ApiModelProperty;
import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.common.entity.WalletType;
import kb04.team02.web.mvc.exchange.entity.ReceiptState;
import kb04.team02.web.mvc.member.entity.Address;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OfflineReceiptRequestDto {
    @ApiModelProperty(example = "통화 코드")
    private int currencyCode;

    @ApiModelProperty(example = "매입량")
    private Long amount;

    @ApiModelProperty(example = "지갑 타입")
    private int walletType;

    @ApiModelProperty(example = "지갑 id")
    private Long walletId;

    @ApiModelProperty(example = "은행 id")
    private Long bankId;

    @ApiModelProperty(example = "수령 신청일")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime receiptDate;
}
