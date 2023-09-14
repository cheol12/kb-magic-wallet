package kb04.team02.web.mvc.service.exchange;

import kb04.team02.web.mvc.domain.bank.Bank;
import kb04.team02.web.mvc.domain.bank.ExchangeRate;
import kb04.team02.web.mvc.domain.bank.OfflineReceipt;
import kb04.team02.web.mvc.domain.bank.ReceiptState;
import kb04.team02.web.mvc.domain.common.CurrencyCode;
import kb04.team02.web.mvc.domain.member.Member;
import kb04.team02.web.mvc.domain.member.Role;
import kb04.team02.web.mvc.domain.wallet.common.WalletExchange;
import kb04.team02.web.mvc.domain.wallet.common.WalletType;
import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import kb04.team02.web.mvc.domain.wallet.group.GroupWalletExchange;
import kb04.team02.web.mvc.domain.wallet.group.GroupWalletForeignCurrencyBalance;
import kb04.team02.web.mvc.domain.wallet.personal.PersonalWallet;
import kb04.team02.web.mvc.domain.wallet.personal.PersonalWalletExchange;
import kb04.team02.web.mvc.domain.wallet.personal.PersonalWalletForeignCurrencyBalance;
import kb04.team02.web.mvc.dto.*;
import kb04.team02.web.mvc.exception.ExchangeException;
import kb04.team02.web.mvc.repository.bank.BankRepository;
import kb04.team02.web.mvc.repository.bank.ExchangeRateRepository;
import kb04.team02.web.mvc.repository.bank.OfflineReceiptRepository;
import kb04.team02.web.mvc.repository.member.MemberRepository;
import kb04.team02.web.mvc.repository.wallet.group.GroupWalletExchangeRepository;
import kb04.team02.web.mvc.repository.wallet.group.GroupWalletForeignCurrencyBalanceRepository;
import kb04.team02.web.mvc.repository.wallet.group.GroupWalletRespository;
import kb04.team02.web.mvc.repository.wallet.personal.PersonalWalletExchangeRepository;
import kb04.team02.web.mvc.repository.wallet.personal.PersonalWalletForeignCurrencyBalanceRepository;
import kb04.team02.web.mvc.repository.wallet.personal.PersonalWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ExchangeServiceImpl implements ExchangeService{

    private final EntityManager em;
    private final BankRepository bankRepository;
    private final OfflineReceiptRepository offlineReceiptRepository;
    private final PersonalWalletRepository personalWalletRepository;
    private final GroupWalletRespository groupWalletRespository;
    private final PersonalWalletExchangeRepository personalWalletExchangeRepository;
    private final GroupWalletExchangeRepository groupWalletExchangeRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final PersonalWalletForeignCurrencyBalanceRepository pFCBalanceRepository;
    private final GroupWalletForeignCurrencyBalanceRepository gFCBalanceRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<BankDto> bankList() {
        List<BankDto> bankList = bankRepository.findAll().stream()
                .map(BankDto::toBankDto)
                .collect(Collectors.toList());

        return bankList;
    }

    @Override
    public List<WalletDto> WalletList(Long memberId) {
        // 일단 지갑리스트 전부 return하고 view에서 선택 못하게

        // 개인 + 모임
        List<WalletDto> resList = new ArrayList<>();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("멤버 조회 실패"));


        // 개인 지갑
        List<WalletDto> pWalletList = new ArrayList<>();
        PersonalWallet pw = personalWalletRepository.findByMember(member);
        pWalletList.add(WalletDto.toPersoanlDto(pw));
        // 모임 지갑
        List<GroupWallet> groupWallet = groupWalletRespository.findByMember(member);
        if(groupWallet != null){
            List<WalletDto> gWalletList = groupWallet.stream()
                    .map(WalletDto::toGroupDto)
                    .collect(Collectors.toList());

            resList.addAll(gWalletList);
        }

        resList.addAll(pWalletList);
        return resList;
    }

    @Override
    public Long selectedWalletBalance(Long WalletId, WalletType walletType) {

        Long balance = 0L;

        if(walletType.equals(WalletType.PERSONAL_WALLET)){
            // 선택한 지갑이 개인지갑
            balance = personalWalletRepository.findById(WalletId).get().getBalance();
        } else if (walletType.equals(WalletType.GROUP_WALLET)) {
            // 선택한 지갑이 모임지갑
            balance = groupWalletRespository.findById(WalletId).get().getBalance();
        }
        return balance;
    }

    @Override
    public List<OfflineReceiptDto> offlineReceiptHistory(Long personalWalletId, Map<Long, Role> map) {

        // 개인지갑 -> 환전 내역
        PersonalWallet personalWallet = personalWalletRepository.findById(personalWalletId).orElse(null);
        List<OfflineReceiptDto> pwReceiptHistory = offlineReceiptRepository.findAllByPersonalWallet(personalWallet).stream()
                .map(OfflineReceiptDto::toPersonalOfflineReceiptDto)
                .collect(Collectors.toList());

        // 모임지갑 -> 환전 내역
        List<OfflineReceipt> gwOfflineReceiptList = new ArrayList<>();
        for(Long gwId : map.keySet()){
            GroupWallet groupWallet = groupWalletRespository.findById(gwId).orElse(null);
            gwOfflineReceiptList.addAll(offlineReceiptRepository.findAllByGroupWallet(groupWallet));
        }

        List<OfflineReceiptDto> gwReceiptHistory = gwOfflineReceiptList.stream()
                .map(OfflineReceiptDto::toGroupOfflineReceiptDto)
                .collect(Collectors.toList());


        // 개인지갑 내역 + 모임지갑 내역
        List<OfflineReceiptDto> resList = new ArrayList<>();
        resList.addAll(pwReceiptHistory);
        resList.addAll(gwReceiptHistory);

        return resList;
    }

    @Override
    public int requestOfflineReceipt(OfflineReceiptDto offlineReceiptDto) {

        WalletType type = offlineReceiptDto.getWalletType();

        // 선택한 지갑의 balance
        Long balance = 0L;
        if(type.equals(WalletType.PERSONAL_WALLET)) {
            balance = selectedWalletBalance(offlineReceiptDto.getPersonalWalletId(), offlineReceiptDto.getWalletType());
        } else if (type.equals(WalletType.GROUP_WALLET)) {
            balance = selectedWalletBalance(offlineReceiptDto.getGroupWalletId(), offlineReceiptDto.getWalletType());
        }

        // balance보다 높은 금액을 신청한 경우 예외 발생
        if(offlineReceiptDto.getAmount() > balance) throw new ExchangeException("잔액이 부족합니다.");

        Bank bank = bankRepository.findById(offlineReceiptDto.getBankId()).get();
        GroupWallet groupWallet = groupWalletRespository.findById(offlineReceiptDto.getOfflineReceiptId()).get();
        PersonalWallet personalWallet = personalWalletRepository.findById(offlineReceiptDto.getPersonalWalletId()).get();

        offlineReceiptRepository.save(
                OfflineReceipt.builder()
                        .receiptDate(offlineReceiptDto.getReceiptDate())
                        .currencyCode(offlineReceiptDto.getCurrencyCode())
                        .amount(offlineReceiptDto.getAmount())
                        .receiptState(offlineReceiptDto.getReceiptState())
                        .bank(bank)
                        .personalWallet(personalWallet)
                        .groupWallet(groupWallet)
                        .build()
        );

        // 선택한 지갑의 balance update
        Long minus = expectedExchangeAmount(offlineReceiptDto.getCurrencyCode(), offlineReceiptDto.getAmount()).getExpectedAmount();

        if(type.equals(WalletType.PERSONAL_WALLET)) personalWallet.setBalance(balance-minus);
        else if (type.equals(WalletType.GROUP_WALLET)) groupWallet.setBalance(balance-minus);

        return 1;
    }
    
    @Override
    public int cancelOfflineReceipt(Long receipt_id) {
        OfflineReceipt offlineReceipt = offlineReceiptRepository.findById(receipt_id)
                .orElseThrow(() -> new NoSuchElementException("오프라인 환전 내역 조회 실패"));

        offlineReceipt.setReceiptState(ReceiptState.CANCEL);
        return 1;
    }

    @Override
    public int requestExchangeOnline(ExchangeDto exchangeDto) {
        // 원화 -> 외화일 경우만
        exchangeDto.setSellCurrencyCode(CurrencyCode.KRW);

        Long walletId = exchangeDto.getWalletId();
        CurrencyCode buyCode = exchangeDto.getBuyCurrencyCode();

        // 선택한 지갑의 balance
        Long balance = 0L;
        if(exchangeDto.getWalletType().equals(WalletType.PERSONAL_WALLET)) {
            balance = selectedWalletBalance(walletId, exchangeDto.getWalletType());
        } else if (exchangeDto.getWalletType().equals(WalletType.GROUP_WALLET)) {
            balance = selectedWalletBalance(walletId, exchangeDto.getWalletType());
        }

        // 환전 정보 반환
        ExchangeCalDto exchangeCalDto = expectedExchangeAmount(buyCode, exchangeDto.getBuyAmount());
        // 환율 적용
        Long expectedAmount = exchangeCalDto.getExpectedAmount();
        exchangeDto.setSellAmount(expectedAmount); // 매도 금액 설정
        // balance보다 높은 금액을 신청한 경우 예외 발생
        if(expectedAmount > balance) throw new ExchangeException("잔액이 부족합니다.");

        // 환율 설정
        exchangeDto.setExchangeRate(exchangeCalDto.getApplicableExchangeRate());

        if(exchangeDto.getWalletType().equals(WalletType.PERSONAL_WALLET)){
            // 개인지갑 -> 환전일 경우
            PersonalWallet personalWallet = personalWalletRepository.findById(walletId).get();
            Long kwBalance = personalWallet.getBalance(); // 원화 balance

            // 해당 코드 환전 내역 조회
            PersonalWalletForeignCurrencyBalance pwfcb = pFCBalanceRepository.findPersonalWalletForeignCurrencyBalanceByCurrencyCodeAndPersonalWallet(buyCode, personalWallet).orElse(null);

            Long fBalance = 0L;
            // 해당코드 환전 내역이 있을 때
            if(pwfcb != null) fBalance = pwfcb.getBalance();

            // 외화 지갑 balance insert / update
            pFCBalanceRepository.save(PersonalWalletForeignCurrencyBalance.builder()
                    .currencyCode(buyCode)
                    .balance(fBalance + exchangeDto.getBuyAmount())
                    .personalWallet(personalWallet)
                    .build());

            // exchangeDto setter
            exchangeDto.setAfterSellBalance(kwBalance - expectedAmount); // 지갑 balance - 매도 금액
            exchangeDto.setAfterBuyBalance(fBalance + exchangeDto.getBuyAmount()); // 외화 지갑 balance + 매수 금액
            PersonalWalletExchange personalWalletExchange = ExchangeDto.toPersonalEntity(exchangeDto, personalWallet);
            personalWalletExchangeRepository.save(personalWalletExchange);


        } else if (exchangeDto.getWalletType().equals(WalletType.GROUP_WALLET)) {
            // 모임지갑 -> 환전일 경우
            GroupWallet groupWallet = groupWalletRespository.findById(walletId).get();
            Long kwBalance = groupWallet.getBalance(); // 원화 balance

            // 해당 코드 환전 내역 조회
            GroupWalletForeignCurrencyBalance bwfcb = gFCBalanceRepository.findGroupWalletForeignCurrencyBalanceByCurrencyCodeAndGroupWallet(buyCode, groupWallet).orElse(null);

            Long fBalance = 0L;
            // 해당코드 환전 내역이 있을 때
            if(bwfcb != null) fBalance = bwfcb.getBalance();

            // 외화 지갑 balance insert / update
            gFCBalanceRepository.save(GroupWalletForeignCurrencyBalance.builder()
                    .currencyCode(buyCode)
                    .balance(fBalance + exchangeDto.getBuyAmount())
                    .groupWallet(groupWallet)
                    .build());

            // exchangeDto setter
            exchangeDto.setAfterSellBalance(kwBalance - expectedAmount); // 지갑 balance - 매도 금액
            exchangeDto.setAfterBuyBalance(fBalance + exchangeDto.getBuyAmount()); // 외화 지갑 balance + 매수 금액


            GroupWalletExchange groupWalletExchange = ExchangeDto.toGroupEntity(exchangeDto, groupWallet);
            groupWalletExchangeRepository.save(groupWalletExchange);

        }

        return 1;
    }

    @Override
    public ExchangeCalDto expectedExchangeAmount(CurrencyCode currencyCode, Long amount) {

        // 적용 환율 구하기
        Double applicableExchangeRate = 0.0;
        ExchangeRate exchangeRate = exchangeRateRepository.findExchangeRateByCurrencyCode(currencyCode);
        if(! currencyCode.equals(CurrencyCode.KRW)){
            // 원화 -> 외화
            applicableExchangeRate = exchangeRate.getTelegraphicTransferBuyingRate();
        }else {
            // 외화 -> 원화
            applicableExchangeRate = exchangeRate.getTelegraphicTransferSellingRate();
        }

        // 환율 적용 계산: 신청 금액 * 적용 환율
        Long expectedAmount = (long) (amount * applicableExchangeRate);

        // dto set
        ExchangeCalDto exchangeCalDto = new ExchangeCalDto();
        exchangeCalDto.setExpectedAmount(expectedAmount);
        exchangeCalDto.setTradingBaseRate(exchangeRate.getTradingBaseRate());
        exchangeCalDto.setApplicableExchangeRate(applicableExchangeRate);

        return exchangeCalDto;
    }
}
