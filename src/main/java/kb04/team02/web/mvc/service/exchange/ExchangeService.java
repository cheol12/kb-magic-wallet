package kb04.team02.web.mvc.service.exchange;

import kb04.team02.web.mvc.domain.bank.Bank;
import kb04.team02.web.mvc.domain.bank.OfflineReceipt;

import java.util.List;

public interface ExchangeService {

    /**
     * 환전 수령 은행 리스트
     * ROWNUM: 43
     * @return bankList
     */
    List<Bank> bankList();

    /**
     * 사용자의 출금/결제 권한이 있는 모임지갑 리스트
     * @return
     */
    List<chairManWalletListDto> chairManWalletList();

    /**
     * 선택한 지갑의 잔액
     */
    int selectedWalletBalance();

    /**
     * 오프라인 환전 신청 내역
     * ROWNUM: 42
     * @return offlineReceiptHistoryList
     */
    List<OfflineReceipt> offlineReceiptHistory();

    /**
     * 오프라인 환전 요청
     * ROWNUM: 44
     */
    void requestOfflineReceipt();

    /**
     * 오프라인 환전 취소
     * ROWNUM: 45
     */
    void cancelOfflineReceipt();


}
