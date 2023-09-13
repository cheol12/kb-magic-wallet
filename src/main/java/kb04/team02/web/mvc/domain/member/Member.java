package kb04.team02.web.mvc.domain.member;

import kb04.team02.web.mvc.domain.card.CardIssuance;
import kb04.team02.web.mvc.domain.member.Address;
import kb04.team02.web.mvc.domain.wallet.group.DuePayment;
import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import kb04.team02.web.mvc.domain.wallet.personal.PersonalWallet;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    @SequenceGenerator(name = "member_seq", allocationSize = 1, sequenceName = "member_seq")
    @Column(name = "member_id")
    private Long memberId;

    @Column(nullable = false, length = 30, unique = true)
    private String id;

    @Column(nullable = false, length = 30)
    private String password;

    @Column(nullable = false, length = 30)
    private String name;

    @Embedded
    @Column(nullable = false)
    private Address address;

    @Column(nullable = false, length = 64, unique = true)
    private String phoneNumber;

    @Column(nullable = true, length = 64, unique = true)
    private String email;

    @CreationTimestamp
    private LocalDateTime insertDate;

    @Column(nullable = false, length = 8)
    private String payPassword;

    @Column(nullable = false, length = 20, unique = true)
    private String bankAccount;

    //== 연관관계 설정 START==//
    @OneToMany(mappedBy = "member")
    private List<CardIssuance> cardIssuances = new ArrayList<>();

    @OneToOne(mappedBy = "member")
    private PersonalWallet personalWallet;

    @OneToMany(mappedBy = "member")
    private List<DuePayment> duePayments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<GroupWallet> groupWallets = new ArrayList<>();

    //== 연관관계 설정 END==//

    //== 메소드 START ==//
    public void changeAccount(String newAccount) {
        this.bankAccount = newAccount;
    }
    //== 메소드 END ==//
}