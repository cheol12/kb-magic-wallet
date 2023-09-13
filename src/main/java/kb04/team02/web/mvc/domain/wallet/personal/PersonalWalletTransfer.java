package kb04.team02.web.mvc.domain.wallet.personal;

import kb04.team02.web.mvc.domain.wallet.common.Transfer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@DiscriminatorValue("PersonalWalletTransfer")
public class PersonalWalletTransfer extends Transfer {
    //== 연관관계 설정 START==//
    @ManyToOne
    @JoinColumn(name = "personal_wallet_id")
    private PersonalWallet personalWallet;
    //== 연관관계 설정 END==//
}
