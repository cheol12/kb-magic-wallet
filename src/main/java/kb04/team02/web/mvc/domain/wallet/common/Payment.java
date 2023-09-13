package kb04.team02.web.mvc.domain.wallet.common;

import kb04.team02.web.mvc.domain.common.CurrencyCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
@NoArgsConstructor
@SuperBuilder
public abstract class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_payment_seq")
    @SequenceGenerator(name = "wallet_payment_seq", allocationSize = 1, sequenceName = "wallet_payment_seq")
    @Column(name = "wallet_payment_id")
    private Long groupWalletExchangeId;

    @CreationTimestamp
    private LocalDateTime insertDate;

    @Enumerated(value = EnumType.ORDINAL)
    private CurrencyCode currencyCode;

    @Enumerated(value = EnumType.ORDINAL)
    private PaymentType paymentType;

    private String paymentPlace;

    @Enumerated(value = EnumType.ORDINAL)
    private PaymentCategoryType paymentCategory;

    private Long amount;
    private Long afterPayBalance;
}
