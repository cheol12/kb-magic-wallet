package kb04.team02.web.mvc.service.personalwallet;

import kb04.team02.web.mvc.domain.common.CurrencyCode;
import kb04.team02.web.mvc.domain.member.Member;
import kb04.team02.web.mvc.domain.wallet.personal.*;
import kb04.team02.web.mvc.dto.PersonalWalletDto;
import kb04.team02.web.mvc.dto.WalletDetailDto;
import kb04.team02.web.mvc.dto.WalletHistoryDto;
import kb04.team02.web.mvc.repository.wallet.personal.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalWalletServiceImpl implements PersonalWalletService {

    private final PersonalWalletRepository personalWalletRepository;
    private final PersonalWalletTransferRepository personalWalletTransferRepository;
    private final PersonalWalletExchangeRepository personalWalletExchangeRepository;
    private final PersonalWalletPaymentRepository personalWalletPaymentRepository;
    private final PersonalWalletForeignCurrencyBalanceRepository personalWalletForeignCurrencyBalanceRepository;

    @Override
    public WalletDetailDto personalWallet(Member member) {
        WalletDetailDto dto = new WalletDetailDto();

        PersonalWallet personalWallet = personalWalletRepository.findByMember(member);
        List<PersonalWalletForeignCurrencyBalance> personalWalletForeignCurrencyBalances
                = personalWalletForeignCurrencyBalanceRepository.searchAllByPersonalWallet(personalWallet);
        List<PersonalWalletPayment> personalWalletPayments
                = personalWalletPaymentRepository.searchAllByPersonalWallet(personalWallet);
        List<PersonalWalletExchange> personalWalletExchanges
                = personalWalletExchangeRepository.searchAllByPersonalWallet(personalWallet);
        List<PersonalWalletTransfer> personalWalletTransfers
                = personalWalletTransferRepository.searchAllByPersonalWallet(personalWallet);

//        // 잔액 dto 변환 완료
//        dto.setKRWBalance(personalWallet.getBalance());
//        for (PersonalWalletForeignCurrencyBalance balance : personalWalletForeignCurrencyBalances) {
//            switch (balance.getCurrencyCode()) {
//                case USD:
//                    dto.setUSDBalance(balance.getBalance());
//                    break;
//                case JPY:
//                    dto.setJPYBalance(balance.getBalance());
//                    break;
//            }
//        }

        //== 미완성
//        for (PersonalWalletTransfer transfer : personalWalletTransfers) {
//            dto.getList().add(new WalletHistoryDto(transfer.getInsertDate(),
//                    transfer.getDest(),
//                    transfer.getToType().name(),
//                    transfer.getCurrencyCode().name(),
//                    transfer.getAmount(),
//                    transfer.getAfterBalance()));
//        }
//
//        for (PersonalWalletExchange exchange : personalWalletExchanges) {
//            dto.getList().add(new WalletHistoryDto(exchange.getInsertDate(),
//                    exchange.getSellCurrencyCode().name(),
//                    "환전",
//                    exchange.getBuyCurrencyCode().name(),
//                    exchange.getBuyAmount(),
//                    exchange.getAfterBuyBalance()));
//        }
//
//        for (PersonalWalletTransfer transfer : personalWalletTransfers) {
//            dto.getList().add(new WalletHistoryDto(transfer.getInsertDate(),
//                    transfer.getDest(),
//                    transfer.getToType().name(),
//                    transfer.getCurrencyCode().name(),
//                    transfer.getAmount(),
//                    transfer.getAfterBalance()));
//        }
        return dto;
    }

//    @Override
//    public PersonalWalletTransferDto personalWalletDeposit(PersonalWalletTransferDto personalWalletTransferDto) {
//        return null;
//    }

//    @Override
//    public PersonalWalletTransferDto personalWalletWithdraw(PersonalWalletTransferDto personalWalletTransferDto) {
//        return null;
//    }
}
