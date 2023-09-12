package kb04.team02.web.mvc.service.exchange;

import kb04.team02.web.mvc.domain.bank.Bank;
import kb04.team02.web.mvc.domain.bank.OfflineReceipt;
import kb04.team02.web.mvc.dto.BankDto;
import kb04.team02.web.mvc.dto.OfflineReceiptDto;
import kb04.team02.web.mvc.dto.WalletDto;

import java.util.List;

public interface ExchangeService {

    /**
     * 환전 수령 은행 리스트
     * ROWNUM: 43
     * @return bankList
     * 
     * 오프라인 은행 지점 목륵을 반환한다
     * BankDto 
     *  - Bank 도메인과 동일 (연관관계 제외)
     */
    List<BankDto> bankList();

    /**
     * 사용자의 출금/결제 권한이 있는 모임지갑 리스트
     * @return
     *
     * 지갑 리스트를 보여줄 때 권한이 있는 애들만 View로 전송
     * => 비즈니스 로직 상으로 권한을 필터링
     *
     * WalletDto
     *  - wallet seq
     *  - 권한
     *  - 지갑 이름
     *  - 지갑 구분
     */
    List<WalletDto> chairManWalletList();

    /**
     * 선택한 지갑의 잔액
     * 
     * 드롭다운 이벤트 감지로 REST API 전송
     * (API 명세 추가 필요)
     */
    Long selectedWalletBalance();

    /**
     * 오프라인 환전 신청 내역
     * ROWNUM: 42
     * @return offlineReceiptHistoryList
     *
     * 오프라인 환전 페이지에 들어갔을 때, 보여질 자신의 환전 신청 내역 리스트 반환
     * 
     * OfflineReceiptDto
     *  - 수령 지점명
     *  - 금액
     *  - 통화
     *  - 주소
     *  - 수령일자
     *  - 상태코드(수령여부)
     */
    List<OfflineReceiptDto> offlineReceiptHistory();

    /**
     * 오프라인 환전 요청
     * ROWNUM: 44
     *
     * @return 1 OK, 0 FAIL
     */
    int requestOfflineReceipt();

    /**
     * 오프라인 환전 취소
     * ROWNUM: 45
     * 
     * 환전 취소 시, rest로 자신의 환전 신청 내역을 리스트로 전송하여 환전 메인페이지를 리프레시한다
     * OfflineReceiptDto 리스트 반환
     */
    List<OfflineReceiptDto> cancelOfflineReceipt();
}
