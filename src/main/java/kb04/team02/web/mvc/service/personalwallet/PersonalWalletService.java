package kb04.team02.web.mvc.service.personalwallet;

import kb04.team02.web.mvc.domain.member.Member;;
import kb04.team02.web.mvc.dto.PersonalWalletTransferDto;
import kb04.team02.web.mvc.dto.WalletDetailDto;

public interface PersonalWalletService {

    /**
     * 개인지갑 메인페이지 서비스
     * PersonalWalletDto
     *  - Long Balance
     *  - List<PersonalWalletTransferDto> list1
     *  - List<PersonalWalletPaymentDto> list2
     *  - List<PersonalWalletExchangeDto> list3
     *  - List<PersonalWalletForeignCurrencyBalanceDto> list4
     *
     * @param member 조회할 개인지갑 id
     * @return 개인지갑에 필요한 DTO
     */
    WalletDetailDto personalWallet(Member member);

    /**
     * 개인지갑 충전 서비스
     * personalWalletTransferDto
     * => PersonalWalletTransfer Domain 과 유사
     *
     * @param personalWalletTransferDto 사용자가 입력한 폼 데이터
     * @return 개인지갑 이체내역에 넣은 이체 내역 DTO
     */
    PersonalWalletTransferDto personalWalletDeposit(PersonalWalletTransferDto personalWalletTransferDto);

    /**
     * 개인지갑 환불 서비스
     * personalWalletTransferDto
     * => PersonalWalletTransfer Domain 과 유사
     *
     * @param personalWalletTransferDto 사용자가 입력한 폼 데이터
     * @return 개인지갑 이체내역에 넣은 이체 내역 DTO
     */
    PersonalWalletTransferDto personalWalletWithdraw(PersonalWalletTransferDto personalWalletTransferDto);
}
