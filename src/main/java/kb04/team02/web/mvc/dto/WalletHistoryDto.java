package kb04.team02.web.mvc.dto;

import kb04.team02.web.mvc.domain.wallet.common.Payment;
import kb04.team02.web.mvc.domain.wallet.common.Transfer;
import kb04.team02.web.mvc.domain.wallet.common.WalletExchange;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WalletHistoryDto {

    //==common==/
    private LocalDateTime dateTime;
    private String type;
    private String detail;
    private String amount;
    private String balance;
}
