package kb04.team02.web.mvc.service.personalwallet;

import kb04.team02.web.mvc.domain.common.CurrencyCode;
import kb04.team02.web.mvc.domain.member.Member;
import kb04.team02.web.mvc.domain.wallet.common.PaymentType;
import kb04.team02.web.mvc.domain.wallet.common.Transfer;
import kb04.team02.web.mvc.domain.wallet.common.TransferType;
import kb04.team02.web.mvc.domain.wallet.personal.*;
import kb04.team02.web.mvc.dto.PersonalWalletTransferDto;
import kb04.team02.web.mvc.dto.WalletDetailDto;
import kb04.team02.web.mvc.dto.WalletHistoryDto;
import kb04.team02.web.mvc.repository.wallet.personal.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalWalletServiceImpl implements PersonalWalletService {

    private final PersonalWalletRepository walletRepository;
    private final PersonalWalletTransferRepository transferRepository;
    private final PersonalWalletExchangeRepository exchangeRepository;
    private final PersonalWalletPaymentRepository paymentRepository;
    private final PersonalWalletForeignCurrencyBalanceRepository foreignBalanceRepository;

    @Override
    public WalletDetailDto personalWallet(Member member) {
        WalletDetailDto dto = new WalletDetailDto();

        PersonalWallet personalWallet = walletRepository.findByMember(member);
        List<PersonalWalletForeignCurrencyBalance> foreignBalance
                = foreignBalanceRepository.searchAllByPersonalWallet(personalWallet);
        List<PersonalWalletPayment> personalWalletPayments
                = paymentRepository.searchAllByPersonalWallet(personalWallet);
        List<PersonalWalletExchange> personalWalletExchanges
                = exchangeRepository.searchAllByPersonalWallet(personalWallet);
        List<PersonalWalletTransfer> personalWalletTransfers
                = transferRepository.searchAllByPersonalWallet(personalWallet);

        dto.setBalance(new HashMap<>());
        for (PersonalWalletForeignCurrencyBalance balance : foreignBalance) {
            dto.getBalance().put(balance.getCurrencyCode().name(), balance.getBalance());
        }

        dto.setList(new ArrayList<>());
        for (Transfer transfer : personalWalletTransfers) {
            WalletHistoryDto historyDto = new WalletHistoryDto();
            historyDto.setDateTime(transfer.getInsertDate());
            if (transfer.getTransferType() == TransferType.DEPOSIT) {
                historyDto.setType("출금");
            } else {
                historyDto.setType("입금");
            }
            String detail = transfer.getSrc() + " > " + transfer.getDest();
            historyDto.setDetail(detail);
            historyDto.setAmount(transfer.getAmount().toString());
            historyDto.setBalance(transfer.getAfterBalance().toString());

            dto.getList().add(historyDto);
        }

        for (PersonalWalletExchange exchange : personalWalletExchanges) {
            WalletHistoryDto historyDto = new WalletHistoryDto();
            historyDto.setDateTime(exchange.getInsertDate());
            if (exchange.getBuyCurrencyCode() == CurrencyCode.KRW) {
                historyDto.setType("재환전");
            } else {
                historyDto.setType("환전");
            }
            String detail = exchange.getSellCurrencyCode().name() + " > " + exchange.getBuyCurrencyCode().name();

            historyDto.setDetail(detail);
            historyDto.setAmount(exchange.getSellAmount()+" > "+exchange.getBuyAmount());
            historyDto.setBalance(exchange.getAfterSellBalance() + " : " + exchange.getAfterBuyBalance());

            dto.getList().add(historyDto);
        }
        
        for (PersonalWalletPayment payment : personalWalletPayments) {
            WalletHistoryDto historyDto = new WalletHistoryDto();
            historyDto.setDateTime(payment.getInsertDate());
            if (payment.getPaymentType() == PaymentType.OK) {
                historyDto.setType("결제");
            } else {
                historyDto.setType("취소");
            }
            String detail = payment.getAmount() + payment.getCurrencyCode().name();

            historyDto.setDetail(detail);
            historyDto.setAmount(payment.getAmount().toString());
            historyDto.setBalance(payment.getAfterPayBalance().toString());

            dto.getList().add(historyDto);
        }
        return dto;
    }

    @Override
    public PersonalWalletTransferDto personalWalletDeposit(PersonalWalletTransferDto personalWalletTransferDto) {
        return null;
    }

    @Override
    public PersonalWalletTransferDto personalWalletWithdraw(PersonalWalletTransferDto personalWalletTransferDto) {
        return null;
    }
}
