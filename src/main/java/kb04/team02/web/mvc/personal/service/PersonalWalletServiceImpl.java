package kb04.team02.web.mvc.personal.service;

import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.member.entity.Member;
import kb04.team02.web.mvc.common.entity.PaymentType;
import kb04.team02.web.mvc.common.entity.TargetType;
import kb04.team02.web.mvc.common.entity.Transfer;
import kb04.team02.web.mvc.common.entity.TransferType;
import kb04.team02.web.mvc.common.dto.LoginMemberDto;
import kb04.team02.web.mvc.personal.dto.PersonalWalletTransferDto;
import kb04.team02.web.mvc.common.dto.WalletDetailDto;
import kb04.team02.web.mvc.common.dto.WalletHistoryDto;
import kb04.team02.web.mvc.common.exception.InsufficientBalanceException;
import kb04.team02.web.mvc.personal.entity.*;
import kb04.team02.web.mvc.member.repository.MemberRepository;
import kb04.team02.web.mvc.personal.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PersonalWalletServiceImpl implements PersonalWalletService {

    private final PersonalWalletRepository walletRepository;
    private final PersonalWalletTransferRepository transferRepository;
    private final PersonalWalletExchangeRepository exchangeRepository;
    private final PersonalWalletPaymentRepository paymentRepository;
    private final PersonalWalletForeignCurrencyBalanceRepository foreignBalanceRepository;
    private final MemberRepository memberRepository;
    private final PersonalWalletRepository personalWalletRepository;

    @Override
    public WalletDetailDto personalWallet(LoginMemberDto loginMemberDto) {
        WalletDetailDto dto = new WalletDetailDto();

        Member member = memberRepository.findById(loginMemberDto.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("멤버 조회 오류"));

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
    public void personalWalletDeposit(PersonalWalletTransferDto personalWalletTransferDto) {
        Member member = memberRepository.findById(personalWalletTransferDto.getMemberId())
                .orElseThrow(() -> new NoSuchElementException());

        String bankAccount = member.getBankAccount();

        PersonalWallet personalWallet = personalWalletRepository.findByMember(member);
        Long walletId = personalWallet.getPersonalWalletId();
        Long KRW = personalWallet.getBalance();
        Long amount = personalWalletTransferDto.getAmount();
        Long afterBalance = KRW + amount;

        PersonalWalletTransfer deposit = PersonalWalletTransfer.builder()
                .personalWallet(personalWallet)
                .transferType(TransferType.DEPOSIT)
                .fromType(TargetType.ACCOUNT)
                .toType(TargetType.PERSONAL_WALLET)
                .src(bankAccount)
                .dest(String.valueOf(walletId))
                .amount(amount)
                .afterBalance(afterBalance)
                .currencyCode(CurrencyCode.KRW)
                .build();

        transferRepository.save(deposit);
    }

    @Override
    public void personalWalletWithdraw(PersonalWalletTransferDto personalWalletTransferDto) {
        Member member = memberRepository.findById(personalWalletTransferDto.getMemberId())
                .orElseThrow(() -> new NoSuchElementException());

        String bankAccount = member.getBankAccount();

        PersonalWallet personalWallet = personalWalletRepository.findByMember(member);
        Long walletId = personalWallet.getPersonalWalletId();
        Long KRW = personalWallet.getBalance();
        Long amount = personalWalletTransferDto.getAmount();
        Long afterBalance = KRW - amount;
        if (afterBalance < 0) {
            throw new InsufficientBalanceException("개인지갑의 잔액이 부족합니다.");
        }

        PersonalWalletTransfer withdraw = PersonalWalletTransfer.builder()
                .personalWallet(personalWallet)
                .transferType(TransferType.WITHDRAW)
                .fromType(TargetType.PERSONAL_WALLET)
                .toType(TargetType.ACCOUNT)
                .src(String.valueOf(walletId))
                .dest(bankAccount)
                .amount(amount)
                .afterBalance(afterBalance)
                .currencyCode(CurrencyCode.KRW)
                .build();

        transferRepository.save(withdraw);
    }
}
