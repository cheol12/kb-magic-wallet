package kb04.team02.web.mvc.dto;

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

}
