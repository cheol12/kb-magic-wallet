package kb04.team02.web.mvc.dto;

import kb04.team02.web.mvc.domain.bank.OfflineReceipt;
import kb04.team02.web.mvc.domain.bank.ReceiptState;
import kb04.team02.web.mvc.domain.common.CurrencyCode;
import kb04.team02.web.mvc.domain.member.Address;
import kb04.team02.web.mvc.domain.wallet.common.WalletType;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OfflineReceiptDto {

    private Long offlineReceiptId;
    private LocalDateTime receiptDate;
    private CurrencyCode currencyCode;
    private Long amount;
    private Address address;
    private String bankName;
    private ReceiptState receiptState;
    private WalletType walletType;

    private Long bankId;
    private Long groupWalletId;
    private Long personalWalletId;

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

}
