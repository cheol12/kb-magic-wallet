package kb04.team02.web.mvc.dto;

import kb04.team02.web.mvc.domain.common.CurrencyCode;
import kb04.team02.web.mvc.domain.wallet.common.SettleType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SettleDto {
    private SettleType settleType;
    private CurrencyCode currencyCode;
    private Long totalAmout;
    private Long groupWalletId;
}
