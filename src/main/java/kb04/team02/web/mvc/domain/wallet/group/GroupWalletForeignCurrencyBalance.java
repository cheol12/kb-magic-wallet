package kb04.team02.web.mvc.domain.wallet.group;

import kb04.team02.web.mvc.domain.wallet.common.ForeignCurrencyBalance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@DiscriminatorValue("GroupWalletForeignCurrencyBalance")
public class GroupWalletForeignCurrencyBalance extends ForeignCurrencyBalance {
    //== 연관관계 설정 START==//
    @ManyToOne
    @JoinColumn(name = "group_wallet_id")
    private GroupWallet groupWallet;
    //== 연관관계 설정 END==//
}
