package kb04.team02.web.mvc.exchange.dto;

import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.common.entity.WalletType;
import kb04.team02.web.mvc.group.entity.GroupWallet;
import kb04.team02.web.mvc.group.entity.GroupWalletExchange;
import kb04.team02.web.mvc.personal.entity.PersonalWallet;
import kb04.team02.web.mvc.personal.entity.PersonalWalletExchange;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExchangeDto {

    private int sellCurrencyCode;
    private Long sellAmount;
    private Long afterSellBalance;
    private int buyCurrencyCode;
    private Long buyAmount;
    private Long afterBuyBalance;
    private Double exchangeRate;
    private Long walletId;
    private int walletType;

    public static PersonalWalletExchange toPersonalEntity(ExchangeDto exchangeDto, PersonalWallet personalWallet){
        return PersonalWalletExchange.builder()
                .sellCurrencyCode(CurrencyCode.findByValue(exchangeDto.getSellCurrencyCode()))
                .sellAmount(exchangeDto.getSellAmount())
                .afterSellBalance(exchangeDto.getAfterSellBalance())
                .buyCurrencyCode(CurrencyCode.findByValue(exchangeDto.getBuyCurrencyCode()))
                .buyAmount(exchangeDto.getBuyAmount())
                .afterBuyBalance(exchangeDto.getAfterBuyBalance())
                .exchangeRate(exchangeDto.getExchangeRate())
                .personalWallet(personalWallet)
                .build();
    }

    public static GroupWalletExchange toGroupEntity(ExchangeDto exchangeDto, GroupWallet groupWallet){
        return GroupWalletExchange.builder()
                .sellCurrencyCode(CurrencyCode.findByValue(exchangeDto.getSellCurrencyCode()))
                .sellAmount(exchangeDto.getSellAmount())
                .afterSellBalance(exchangeDto.getAfterSellBalance())
                .buyCurrencyCode(CurrencyCode.findByValue(exchangeDto.getBuyCurrencyCode()))
                .buyAmount(exchangeDto.getBuyAmount())
                .afterBuyBalance(exchangeDto.getAfterBuyBalance())
                .exchangeRate(exchangeDto.getExchangeRate())
                .groupWallet(groupWallet)
                .build();
    }
}
