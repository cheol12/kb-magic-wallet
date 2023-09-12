package kb04.team02.web.mvc.domain.bank;

import kb04.team02.web.mvc.domain.common.CurrencyCode;
import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import kb04.team02.web.mvc.domain.wallet.personal.PersonalWallet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor()
@Getter
@AllArgsConstructor
@Builder
public class OfflineReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offline_receipt_seq")
    @SequenceGenerator(name = "offline_receipt_seq", allocationSize = 1, sequenceName = "offline_receipt_seq")
    @Column(name = "offline_receipt_id")
    private Long offlineReceiptId;

    @CreationTimestamp
    private LocalDateTime insertDate;

    private LocalDateTime receiptDate;
    private CurrencyCode currencyCode;
    private Long amount;

    @Enumerated(EnumType.ORDINAL)
    private ReceiptState receiptState;
    //== 연관관계 설정 START==//
    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "personal_wallet_id")
    private PersonalWallet personalWallet;

    @ManyToOne
    @JoinColumn(name = "group_wallet_id")
    private GroupWallet groupWallet;
    //== 연관관계 설정 END==//
}
