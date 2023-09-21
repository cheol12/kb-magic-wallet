package kb04.team02.web.mvc.personal.service;

;
import kb04.team02.web.mvc.common.dto.LoginMemberDto;
import kb04.team02.web.mvc.personal.dto.PersonalWalletTransferDto;
import kb04.team02.web.mvc.common.dto.WalletDetailDto;

public interface PersonalWalletService {

    /**
     * 개인지갑 메인페이지 서비스
     * PersonalWalletDto
     * - Long Balance
     * - List<PersonalWalletTransferDto> list1
     * - List<PersonalWalletPaymentDto> list2
     * - List<PersonalWalletExchangeDto> list3
     * - List<PersonalWalletForeignCurrencyBalanceDto> list4
     *
     * @param member 조회할 개인지갑 id
     * @return 개인지갑에 필요한 DTO
     */
    WalletDetailDto personalWallet(LoginMemberDto member);

    /**
     * 개인지갑 충전 서비스
     * personalWalletTransferDto
     * => PersonalWalletTransfer Domain 과 유사
     *
     * @param personalWalletTransferDto 사용자가 입력한 폼 데이터
     * @return 개인지갑 이체내역에 넣은 이체 내역 DTO
     * 개인지갑메인으로 돌아가니까 필요 없지 않나?
     */
    void personalWalletDeposit(PersonalWalletTransferDto personalWalletTransferDto);

    /**
     * 개인지갑 환불 서비스
     * personalWalletTransferDto
     * => PersonalWalletTransfer Domain 과 유사
     *
     * @param personalWalletTransferDto 사용자가 입력한 폼 데이터
     * @return 개인지갑 이체내역에 넣은 이체 내역 DTO
     * 오류 캐치 안되면 정상 작동.
     */
    void personalWalletWithdraw(PersonalWalletTransferDto personalWalletTransferDto);

    Long personalWalletCalSurplus(LoginMemberDto member);
}
