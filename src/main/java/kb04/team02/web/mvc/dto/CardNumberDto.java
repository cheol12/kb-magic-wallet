package kb04.team02.web.mvc.dto;

import kb04.team02.web.mvc.domain.card.CardState;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class CardNumberDto {
    private String cardNumber;
    private CardState cardState;
}
