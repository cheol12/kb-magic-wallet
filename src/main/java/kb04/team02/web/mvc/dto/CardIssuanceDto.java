package kb04.team02.web.mvc.dto;

import kb04.team02.web.mvc.domain.card.CardState;
import kb04.team02.web.mvc.domain.wallet.common.WalletType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CardIssuanceDto {
    private Long cardIssuanceId;
    private String cardNumber;
    private CardState cardState;
    private Long walletId;
    private WalletType walletType;

}
