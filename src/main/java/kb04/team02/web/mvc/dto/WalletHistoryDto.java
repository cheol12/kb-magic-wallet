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
public class WalletHistoryDto {

    private LocalDateTime dateTime;
    private String dest;
    private String type;
    private String currencyCode;
    private Long amount;
    private Long afterBalance;

//    public static WalletHistoryDto toDto(Transfer transfer) {
//        return new WalletHistoryDto()
//    }
//
//    public static WalletHistoryDto toDto(WalletExchange exchange) {
//        return new WalletHistoryDto()
//    }
//
//    public static WalletHistoryDto toDto(Payment payment) {
//        return new WalletHistoryDto()
//    }
}
