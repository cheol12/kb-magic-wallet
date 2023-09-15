package kb04.team02.web.mvc.saving.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Saving {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "saving_seq")
    @SequenceGenerator(name = "v_seq", allocationSize = 1, sequenceName = "saving_seq")
    @Column(name = "saving_id")
    private Long savingId;

    private String name;
    private String savingComment;
    private Double interestRate;
    private Integer period;
    private Long amountLimit;

    //== 연관관계 설정 START==//
    @OneToMany(mappedBy = "saving")
    private List<InstallmentSaving> installmentSavings = new ArrayList<>();
    //== 연관관계 설정 END==//


}
