package kb04.team02.web.mvc.personal.entity;

import kb04.team02.web.mvc.common.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@DiscriminatorValue("PersonalWalletPayment")
public class PersonalWalletPayment extends Payment {
    //== 연관관계 설정 START==//
    @ManyToOne
    @JoinColumn(name = "personal_wallet_id")
    private PersonalWallet personalWallet;
    //== 연관관계 설정 END==//
}
