package kb04.team02.web.mvc.group.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kb04.team02.web.mvc.exchange.entity.OfflineReceipt;
import kb04.team02.web.mvc.common.module.BooleanToYNConverter;
import kb04.team02.web.mvc.member.entity.Member;
import kb04.team02.web.mvc.saving.entity.InstallmentSaving;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GroupWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_seq")
    @SequenceGenerator(name = "wallet_seq", allocationSize = 1, sequenceName = "wallet_seq")
    @Column(name = "group_wallet_id")
    private Long groupWalletId;

    @Column(nullable = false, length = 20)
    private String nickname;

    @CreationTimestamp
    private LocalDateTime insertDate;

    @ColumnDefault("0")
    private Long balance;

    @Convert(converter = BooleanToYNConverter.class)
    private boolean dueCondition;

    private Long dueAccumulation;
    private int dueDate;
    private Long due;

    //== 연관관계 설정 START==//
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "groupWallet", cascade = CascadeType.REMOVE)
    private List<DuePayment> duePayments = new ArrayList<>();

    @OneToMany(mappedBy = "groupWallet", cascade = CascadeType.REMOVE)
    private List<Participation> participations = new ArrayList<>();

    @OneToMany(mappedBy = "groupWallet", cascade = CascadeType.REMOVE)
    private List<GroupWalletTransfer> groupWalletTransfers = new ArrayList<>();

    @OneToMany(mappedBy = "groupWallet", cascade = CascadeType.REMOVE)
    private List<GroupWalletExchange> groupWalletExchanges = new ArrayList<>();

    @OneToMany(mappedBy = "groupWallet", cascade = CascadeType.REMOVE)
    private List<GroupWalletPayment> groupWalletPayments = new ArrayList<>();

    @OneToMany(mappedBy = "groupWallet", cascade = CascadeType.REMOVE)
    private List<GroupWalletForeignCurrencyBalance> foreignCurrencyBalances = new ArrayList<>();

    @OneToMany(mappedBy = "groupWallet", cascade = CascadeType.REMOVE)
    private List<InstallmentSaving> installmentSavings = new ArrayList<>();

    @OneToMany(mappedBy = "groupWallet", cascade = CascadeType.REMOVE)
    private List<OfflineReceipt> offlineReceipts = new ArrayList<>();
    //== 연관관계 설정 END==//
}