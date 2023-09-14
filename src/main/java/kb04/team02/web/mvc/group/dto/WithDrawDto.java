package kb04.team02.web.mvc.group.dto;

import kb04.team02.web.mvc.common.entity.CurrencyCode;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class WithDrawDto {
    private CurrencyCode currencyCode;
    private Long amount;
    private Long srcWalletId;
    private Long destMemberId;
}
