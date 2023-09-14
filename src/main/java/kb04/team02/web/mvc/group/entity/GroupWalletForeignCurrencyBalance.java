package kb04.team02.web.mvc.group.entity;

import kb04.team02.web.mvc.common.entity.ForeignCurrencyBalance;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@DiscriminatorValue("GroupWalletForeign")
@Setter
public class GroupWalletForeignCurrencyBalance extends ForeignCurrencyBalance {
    //== 연관관계 설정 START==//
    @ManyToOne
    @JoinColumn(name = "group_wallet_id")
    private GroupWallet groupWallet;
    //== 연관관계 설정 END==//
}