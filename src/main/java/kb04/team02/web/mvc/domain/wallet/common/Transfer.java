package kb04.team02.web.mvc.domain.wallet.common;

import kb04.team02.web.mvc.domain.common.CurrencyCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_transfer_seq")
    @SequenceGenerator(name = "wallet_transfer_seq", allocationSize = 1, sequenceName = "wallet_transfer_seq")
    @Column(name = "wallet_transfer_id")
    private Long groupWalletTransferId;

    @CreationTimestamp
    private LocalDateTime insertDate;

    @Enumerated(value = EnumType.ORDINAL)
    private TransferType transferType;

    @Enumerated(value = EnumType.ORDINAL)
    private TargetType formType;

    @Enumerated(value = EnumType.ORDINAL)
    private TargetType toType;

    private String src;
    private String dest;
    private Long amount;
    private Long afterBalance;

    @Enumerated(value = EnumType.ORDINAL)
    private CurrencyCode currencyCode;
}
