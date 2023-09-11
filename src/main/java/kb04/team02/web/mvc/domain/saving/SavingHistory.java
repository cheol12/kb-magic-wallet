package kb04.team02.web.mvc.domain.saving;

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
public class SavingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "saving_history_seq")
    @SequenceGenerator(name = "saving_history_seq", allocationSize = 1, sequenceName = "saving_history_seq")
    @Column(name = "saving_history_id")
    private Long savingHistoryId;

    private LocalDateTime insertDate;
    private Long amount;
    private Long accumulatedAmount;
    //== 연관관계 설정 START==//
    @ManyToOne
    @JoinColumn(name = "installment_id")
    private InstallmentSaving installmentSaving;
    //== 연관관계 설정 END==//
}
