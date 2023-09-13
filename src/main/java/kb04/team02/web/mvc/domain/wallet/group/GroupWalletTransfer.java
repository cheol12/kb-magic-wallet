package kb04.team02.web.mvc.domain.wallet.group;

import kb04.team02.web.mvc.domain.wallet.common.Transfer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@DiscriminatorValue("GroupWalletTransfer")
@SuperBuilder
public class GroupWalletTransfer extends Transfer {
    //== 연관관계 설정 START==//
    @ManyToOne
    @JoinColumn(name = "group_wallet_id")
    private GroupWallet groupWallet;
    //== 연관관계 설정 END==//
}
