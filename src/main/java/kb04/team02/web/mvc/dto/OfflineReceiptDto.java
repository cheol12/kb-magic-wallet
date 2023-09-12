package kb04.team02.web.mvc.dto;

import kb04.team02.web.mvc.domain.common.CurrencyCode;

import java.time.LocalDateTime;

public class OfflineReceiptDto {
      /*
      OfflineReceiptDto
        - 수령 내역 Seq
     *  - 수령 지점명
     *  - 금액
     *  - 통화
     *  - 주소
     *  - 수령일자
     *  - 상태코드(수령여부)
     */

    private Long offlineReceiptId;
    private LocalDateTime receiptDate;
    private CurrencyCode currencyCode;
    private Long amount;


}
