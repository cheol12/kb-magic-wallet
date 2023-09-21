package kb04.team02.web.mvc.group.entity;

import kb04.team02.web.mvc.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DuePayment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "due_payment_seq")
    @SequenceGenerator(name = "due_payment_seq", allocationSize = 1, sequenceName = "due_payment_seq")
    @Column(name = "due_payment_id")
    private Long duePaymentId;

    @CreationTimestamp
    private LocalDateTime insertDate;

//    private Long due;

    //== 연관관계 설정 START==//
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "group_wallet_id")
    private GroupWallet groupWallet;
    //== 연관관계 설정 END==//
}
