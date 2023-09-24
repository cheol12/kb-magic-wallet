package kb04.team02.web.mvc.exchange.service;

import kb04.team02.web.mvc.common.dto.WalletDetailDto;
import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.exchange.dto.*;
import kb04.team02.web.mvc.member.entity.Role;
import kb04.team02.web.mvc.common.entity.WalletType;

import java.util.List;
import java.util.Map;

public interface ExchangeService {

    /**
     * 환전 수령 은행 리스트
     * ROWNUM: 43
     *
     * @return bankList
     * <p>
     * 오프라인 은행 지점 목륵을 반환한다
     * BankDto
     * - Bank 도메인과 동일 (연관관계 제외)
     */
    List<BankDto> bankList();

    /**
     * 사용자의 지갑 리스트
     *
     * @return 지갑 리스트를 전부 반환하고 view에서 권한 없는 지갑은 선택 못하게
     * => 비즈니스 로직 상으로 권한을 필터링
     * <p>
     * WalletDto
     * - wallet seq
     * - 권한
     * - 지갑 이름
     * - 지갑 구분
     */
    List<WalletDto> WalletList(Long memberId);

    /**
     * 선택한 지갑의 잔액
     * <p>
     * 드롭다운 이벤트 감지로 REST API 전송
     * (API 명세 추가 필요)
     */
    Long selectedWalletBalance(Long WalletId, WalletType walletType);

    /**
     * 오프라인 환전 신청 내역
     * ROWNUM: 42
     *
     * @return offlineReceiptHistoryList
     * <p>
     * 오프라인 환전 페이지에 들어갔을 때, 보여질 자신의 환전 신청 내역 리스트 반환
     * <p>
     * OfflineReceiptDto
     * - 수령 내역 Seq
     * - 수령 지점명
     * - 금액
     * - 통화
     * - 주소
     * - 수령일자
     * - 상태코드(수령여부)
     */
    List<OfflineReceiptDto> offlineReceiptHistory(Long personalWalletId, Map<Long, Role> map);

    /**
     * 오프라인 환전 요청
     * ROWNUM: 44
     *
     * @return 1 OK, 0 FAIL
     * <p>
     * - 수령일시
     * - 통화
     * - 금액
     * - 은행 식별번호
     * - 개인지갑 식별번호 or 모임지갑 식별변호
     */
    int requestOfflineReceipt(OfflineReceiptRequestDto offlineReceiptRequestDto);

    /**
     * 오프라인 환전 취소
     * ROWNUM: 45
     * <p>
     * 환전 취소 시, rest로 자신의 환전 신청 내역을 리스트로 전송하여 환전 메인페이지를 리프레시한다
     * OfflineReceiptDto 리스트 반환
     */
    int cancelOfflineReceipt(Long receipt_id);

    /**
     * 온라인 환전 신청
     * ROWNUM: 48
     *
     * @return 1: OK, 0: FAIL
     * <p>
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
     *
     * @param currencyCode
     * @param amount
     * @return ExchangeDto
     */
    ExchangeCalDto expectedExchangeAmount(CurrencyCode currencyCode, Long amount);

    /**
     * 선택한 지갑의 외화 잔액을 리스트로 반환
     *
     * @param walletId
     * @param walletType
     * @return
     */
    WalletDetailDto selectedWalletFCBalance(Long walletId, WalletType walletType);

    /**
     * 온라인 재환전 요청
     *
     * @param exchangeDto
     * @return
     */
    int requestReExchangeOnline(ExchangeDto exchangeDto);

    /**
     * 환율정보 모두 긁어오기
     */
    List<String> selectExchangeRateByCurrencyCode(CurrencyCode currencyCode);


    /**
     * 환율 정보 예측
     */
    public List<Double> getPredictedExchangeRates();

    public List<Double> getPredictedExchangeRatesJPY();
}
