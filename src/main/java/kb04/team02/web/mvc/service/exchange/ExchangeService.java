package kb04.team02.web.mvc.service.exchange;

import kb04.team02.web.mvc.domain.bank.Bank;
import kb04.team02.web.mvc.domain.bank.OfflineReceipt;
import kb04.team02.web.mvc.domain.common.CurrencyCode;
import kb04.team02.web.mvc.domain.member.Role;
import kb04.team02.web.mvc.domain.wallet.common.WalletType;
import kb04.team02.web.mvc.dto.BankDto;
import kb04.team02.web.mvc.dto.ExchangeDto;
import kb04.team02.web.mvc.dto.OfflineReceiptDto;
import kb04.team02.web.mvc.dto.WalletDto;

import java.util.List;
import java.util.Map;

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
     * 사용자의 지갑 리스트
     * @return
     *
     * 지갑 리스트를 전부 반환하고 view에서 권한 없는 지갑은 선택 못하게
     * => 비즈니스 로직 상으로 권한을 필터링
     *
     * WalletDto
     *  - wallet seq
     *  - 권한
     *  - 지갑 이름
     *  - 지갑 구분
     */
    List<WalletDto> WalletList(Long memberId);

    /**
     * 선택한 지갑의 잔액
     * 
     * 드롭다운 이벤트 감지로 REST API 전송
     * (API 명세 추가 필요)
     */
    Long selectedWalletBalance(Long WalletId, WalletType walletType);

    /**
     * 오프라인 환전 신청 내역
     * ROWNUM: 42
     * @return offlineReceiptHistoryList
     *
     * 오프라인 환전 페이지에 들어갔을 때, 보여질 자신의 환전 신청 내역 리스트 반환
     * 
     * OfflineReceiptDto
     *  - 수령 내역 Seq
     *  - 수령 지점명
     *  - 금액
     *  - 통화
     *  - 주소
     *  - 수령일자
     *  - 상태코드(수령여부)
     */
    List<OfflineReceiptDto> offlineReceiptHistory(Long personalWalletId, Map<Long, Role> map);

    /**
     * 오프라인 환전 요청
     * ROWNUM: 44
     *
     * @return 1 OK, 0 FAIL
     *
     * - 수령일시
     * - 통화
     * - 금액
     * - 은행 식별번호
     * - 개인지갑 식별번호 or 모임지갑 식별변호
     */
    int requestOfflineReceipt(OfflineReceiptDto offlineReceiptDto);

    /**
     * 오프라인 환전 취소
     * ROWNUM: 45
     * 
     * 환전 취소 시, rest로 자신의 환전 신청 내역을 리스트로 전송하여 환전 메인페이지를 리프레시한다
     * OfflineReceiptDto 리스트 반환
     */
    int cancelOfflineReceipt(Long receipt_id);

    /**
     * 온라인 환전 신청
     * ROWNUM: 48
     *
     * @return 1: OK, 0: FAIL
     *
     * ExchangeDto
     * - 매도 통화 코드
     * - 매도 금액
     * - 매도 후 잔액
     * - 매수 통화 코드
     * - 매수 금액
     * - 매수 후 잔액
     * - 환율
     * - 개인지갑 / 모임지갑..?
     */
    int requestExchangeOnline(ExchangeDto exchangeDto);

    /**
     * 선택한 통화와 입력 금액에 대한 환전 예상 금액을 반환
     * @param currencyCode
     * @param amount
     * @return ExchangeDto
     */
    ExchangeDto expectedExchangeAmount(CurrencyCode currencyCode, Long amount);
}
