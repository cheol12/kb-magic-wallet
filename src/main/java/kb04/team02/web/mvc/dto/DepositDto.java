package kb04.team02.web.mvc.dto;

import kb04.team02.web.mvc.domain.common.CurrencyCode;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class DepositDto {
    private CurrencyCode currencyCode;
    private Long amount;
    private Long srcMemberId;
    private Long destWalletId;
}
