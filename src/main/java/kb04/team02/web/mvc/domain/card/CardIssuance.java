package kb04.team02.web.mvc.domain.card;

import kb04.team02.web.mvc.domain.member.Member;
import kb04.team02.web.mvc.domain.wallet.common.WalletType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CardIssuance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_issuance_seq")
    @SequenceGenerator(name = "card_issuance_seq", allocationSize = 1, sequenceName = "card_issuance_seq")
    @Column(name = "card_issuance_id")
    private Long cardIssuanceId;

    @CreationTimestamp
    private LocalDateTime insertDate;

    @Column(unique = true, length = 19)
    private String cardNumber;

    @Enumerated(EnumType.ORDINAL)
    private CardState cardState;

    private Long walletId;

    @Enumerated(EnumType.ORDINAL)
    private WalletType walletType;

    //== 연관관계 설정 START==//
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    //== 연관관계 설정 END==//
}
