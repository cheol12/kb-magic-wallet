package kb04.team02.web.mvc.exchange.dto;

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
    private int currencyCode;
    private Long amount;
    private int walletType;
    private Long walletId;
    private Long bankId;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime receiptDate;
}
