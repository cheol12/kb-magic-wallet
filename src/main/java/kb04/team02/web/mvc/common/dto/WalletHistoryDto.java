package kb04.team02.web.mvc.common.dto;

import kb04.team02.web.mvc.common.entity.CurrencyCode;
import lombok.*;

import java.sql.Date;
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
    private Date date;

    private CurrencyCode currencyCode;
}
