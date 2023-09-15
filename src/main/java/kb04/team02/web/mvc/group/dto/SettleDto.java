package kb04.team02.web.mvc.group.dto;

import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.common.entity.SettleType;
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
