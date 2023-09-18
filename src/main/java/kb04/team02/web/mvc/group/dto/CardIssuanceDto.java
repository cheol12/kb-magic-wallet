package kb04.team02.web.mvc.group.dto;

import kb04.team02.web.mvc.member.entity.Member;
import kb04.team02.web.mvc.mypage.entity.CardState;
import kb04.team02.web.mvc.common.entity.WalletType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@Setter
public class CardIssuanceDto {
    private Long cardIssuanceId;
    private String cardNumber;
    private CardState cardState;
    private Long walletId;
    private WalletType walletType;
    private Member member;

}
