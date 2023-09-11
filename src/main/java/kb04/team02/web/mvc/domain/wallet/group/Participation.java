package kb04.team02.web.mvc.domain.wallet.group;

import kb04.team02.web.mvc.domain.member.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participation_seq")
    @SequenceGenerator(name = "participation_seq", allocationSize = 1, sequenceName = "participation_seq")
    @Column(name = "participation_id")
    private Long memberId;

    @CreationTimestamp
    private LocalDateTime insetDate;

    @Enumerated(EnumType.ORDINAL)
    private ParticipationState participationState;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    //== 연관관계 설정 START==//
    @ManyToOne
    @JoinColumn(name = "group_wallet_id")
    private GroupWallet groupWallet;
    //== 연관관계 설정 END==//
}
