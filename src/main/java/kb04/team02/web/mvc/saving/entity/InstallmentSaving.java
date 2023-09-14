package kb04.team02.web.mvc.saving.entity;

import kb04.team02.web.mvc.common.module.BooleanToYNConverter;
import kb04.team02.web.mvc.group.entity.GroupWallet;
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
@Setter
public class InstallmentSaving {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "installment_seq")
    @SequenceGenerator(name = "installment_seq", allocationSize = 1, sequenceName = "installment_seq")
    @Column(name = "installment_id")
    private Long installmentId;

    @CreationTimestamp
    private LocalDateTime insertDate;

    private LocalDateTime maturityDate;

    @Convert(converter = BooleanToYNConverter.class)
    private boolean done;

    private Long totalAmount;
    private int savingDate;
    private Long savingAmount;
    //== 연관관계 설정 START==//
    @ManyToOne
    @JoinColumn(name = "saving_id")
    private Saving saving;

    @OneToMany(mappedBy = "installmentSaving")
    private List<SavingHistory> savingHistories = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "group_wallet_id")
    private GroupWallet groupWallet;
    //== 연관관계 설정 END==//

}
