package kb04.team02.web.mvc.exchange.dto;

import io.swagger.annotations.ApiModelProperty;
import kb04.team02.web.mvc.exchange.entity.Bank;
import kb04.team02.web.mvc.exchange.entity.OfflineReceipt;
import kb04.team02.web.mvc.exchange.entity.ReceiptState;
import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.member.entity.Address;
import kb04.team02.web.mvc.common.entity.WalletType;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OfflineReceiptDto {
    @ApiModelProperty(example = "오프라인 환전 id")
    private Long offlineReceiptId;
    @ApiModelProperty(example = "수령일")
    private LocalDateTime receiptDate;
    @ApiModelProperty(example = "통화 코드")
    private CurrencyCode currencyCode;
    @ApiModelProperty(example = "매입량")
    private Long amount;
    @ApiModelProperty(example = "주소")
    private Address address;
    @ApiModelProperty(example = "은행명")
    private String bankName;
    @ApiModelProperty(example = "수령 상태")
    private ReceiptState receiptState;
    @ApiModelProperty(example = "지갑 타입")
    private WalletType walletType;
    @ApiModelProperty(example = "은행 id")
    private Long bankId;
    @ApiModelProperty(example = "지갑 id")
    private Long walletId;

    public static OfflineReceiptDto toPersonalOfflineReceiptDto(OfflineReceipt offlineReceipt){
        return OfflineReceiptDto.builder()
                .offlineReceiptId(offlineReceipt.getOfflineReceiptId())
                .receiptDate(offlineReceipt.getReceiptDate())
                .currencyCode(offlineReceipt.getCurrencyCode())
                .amount(offlineReceipt.getAmount())
                .address(offlineReceipt.getBank().getAddress())
                .bankName(offlineReceipt.getBank().getName())
                .receiptState(offlineReceipt.getReceiptState())
                .walletType(WalletType.PERSONAL_WALLET)
                .build();
    }

    public static OfflineReceiptDto toGroupOfflineReceiptDto(OfflineReceipt offlineReceipt){
        return OfflineReceiptDto.builder()
                .offlineReceiptId(offlineReceipt.getOfflineReceiptId())
                .receiptDate(offlineReceipt.getReceiptDate())
                .currencyCode(offlineReceipt.getCurrencyCode())
                .amount(offlineReceipt.getAmount())
                .address(offlineReceipt.getBank().getAddress())
                .bankName(offlineReceipt.getBank().getName())
                .receiptState(offlineReceipt.getReceiptState())
                .walletType(WalletType.GROUP_WALLET)
                .build();
    }

    // 임시
    public LocalDateTime getReceiptDate() {
        return receiptDate != null ? receiptDate : LocalDateTime.MIN; // null인 경우 LocalDateTime.MIN을 반환
    }

}
