package kb04.team02.web.mvc.dto;

import kb04.team02.web.mvc.domain.wallet.common.Transfer;
import kb04.team02.web.mvc.domain.wallet.personal.PersonalWalletTransfer;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalWalletDto {
    private Long KRWBalance;
    private Long USDBalance;
    private Long JPYBalance;

    private List<WalletHistoryDto> list;

}
