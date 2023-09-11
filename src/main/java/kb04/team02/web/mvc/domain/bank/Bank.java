package kb04.team02.web.mvc.domain.bank;

import kb04.team02.web.mvc.domain.member.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_seq")
    @SequenceGenerator(name = "bank_seq", allocationSize = 1, sequenceName = "bank_seq")
    @Column(name = "bank_id")
    private Long bankId;

    private String name;

    @Embedded
    private Address address;

    //== 연관관계 설정 START==//
    @OneToMany(mappedBy = "bank")
    private List<OfflineReceipt> receiptList = new ArrayList<>();
    //== 연관관계 설정 END==//
}
