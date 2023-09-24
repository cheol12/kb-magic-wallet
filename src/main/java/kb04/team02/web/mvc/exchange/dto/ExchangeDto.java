package kb04.team02.web.mvc.exchange.dto;

import io.swagger.annotations.ApiModelProperty;
import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.common.entity.WalletType;
import kb04.team02.web.mvc.group.entity.GroupWallet;
import kb04.team02.web.mvc.group.entity.GroupWalletExchange;
import kb04.team02.web.mvc.personal.entity.PersonalWallet;
import kb04.team02.web.mvc.personal.entity.PersonalWalletExchange;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ExchangeDto {
    @ApiModelProperty(example = "매도 통화 코드")
    private int sellCurrencyCode;
    @ApiModelProperty(example = "매도 금액")
    private Long sellAmount;
    @ApiModelProperty(example = "매도 후 금액")
    private Long afterSellBalance;
    @ApiModelProperty(example = "매수 통화 코드")
    private int buyCurrencyCode;
    @ApiModelProperty(example = "매수 금액")
    private Long buyAmount;
    @ApiModelProperty(example = "매수 후 금액")
    private Long afterBuyBalance;
    @ApiModelProperty(example = "환율")
    private Double exchangeRate;
    @ApiModelProperty(example = "지갑 id")
    private Long walletId;
    @ApiModelProperty(example = "지갑 type")
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
