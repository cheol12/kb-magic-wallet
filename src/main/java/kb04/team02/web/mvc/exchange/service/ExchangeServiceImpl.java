package kb04.team02.web.mvc.exchange.service;

import kb04.team02.web.mvc.common.dto.WalletDetailDto;
import kb04.team02.web.mvc.exchange.dto.*;
import kb04.team02.web.mvc.exchange.entity.Bank;
import kb04.team02.web.mvc.exchange.entity.ExchangeRate;
import kb04.team02.web.mvc.exchange.entity.OfflineReceipt;
import kb04.team02.web.mvc.exchange.entity.ReceiptState;
import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.member.entity.Member;
import kb04.team02.web.mvc.member.entity.Role;
import kb04.team02.web.mvc.common.entity.WalletType;
import kb04.team02.web.mvc.group.entity.GroupWallet;
import kb04.team02.web.mvc.group.entity.GroupWalletExchange;
import kb04.team02.web.mvc.group.entity.GroupWalletForeignCurrencyBalance;
import kb04.team02.web.mvc.personal.entity.PersonalWallet;
import kb04.team02.web.mvc.personal.entity.PersonalWalletExchange;
import kb04.team02.web.mvc.personal.entity.PersonalWalletForeignCurrencyBalance;
import kb04.team02.web.mvc.exchange.exception.ExchangeException;
import kb04.team02.web.mvc.exchange.repository.BankRepository;
import kb04.team02.web.mvc.exchange.repository.ExchangeRateRepository;
import kb04.team02.web.mvc.exchange.repository.OfflineReceiptRepository;
import kb04.team02.web.mvc.member.repository.MemberRepository;
import kb04.team02.web.mvc.group.repository.GroupWalletExchangeRepository;
import kb04.team02.web.mvc.group.repository.GroupWalletForeignCurrencyBalanceRepository;
import kb04.team02.web.mvc.group.repository.GroupWalletRespository;
import kb04.team02.web.mvc.personal.repository.PersonalWalletExchangeRepository;
import kb04.team02.web.mvc.personal.repository.PersonalWalletForeignCurrencyBalanceRepository;
import kb04.team02.web.mvc.personal.repository.PersonalWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ExchangeServiceImpl implements ExchangeService {


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
        WalletDto pwDto = WalletDto.toPersoanlDto(pw);
        pwDto.setNickname("개인 지갑");
        pwDto.setWalletType(WalletType.PERSONAL_WALLET);
        pWalletList.add(pwDto);
        // 모임 지갑
        List<GroupWallet> groupWallet = groupWalletRespository.findByMember(member);
        if (groupWallet != null) {
            List<WalletDto> gWalletList = groupWallet.stream()
                    .map(WalletDto::toGroupDto)
                    .collect(Collectors.toList());

            resList.addAll(gWalletList);
        }

        resList.addAll(pWalletList);
        return resList;
    }

    @Override
    public Long selectedWalletBalance(Long walletId, WalletType walletType) {
        // 원화 잔액
        Long balance = 0L;

        if (walletType.equals(WalletType.PERSONAL_WALLET)) {
            // 선택한 지갑이 개인지갑
            PersonalWallet personalWallet = personalWalletRepository.findById(walletId)
                    .orElseThrow(() -> new NoSuchElementException("개인 지갑 조회 실패"));
            balance = personalWallet.getBalance();
        } else if (walletType.equals(WalletType.GROUP_WALLET)) {
            // 선택한 지갑이 모임지갑
            GroupWallet groupWallet = groupWalletRespository.findById(walletId)
                    .orElseThrow(() -> new NoSuchElementException("모임 지갑 조회 실패"));
            balance = groupWallet.getBalance();
        }
        return balance;
    }

    @Override
    public List<OfflineReceiptDto> offlineReceiptHistory(Long personalWalletId, Map<Long, Role> map) {
        // 개인지갑 -> 환전 내역
        PersonalWallet personalWallet = personalWalletRepository.findById(personalWalletId).orElseThrow(() -> new NoSuchElementException("개인 지갑 조회 실패"));
        List<OfflineReceiptDto> pwReceiptHistory = offlineReceiptRepository.findAllByPersonalWallet(personalWallet).stream()
                .map(OfflineReceiptDto::toPersonalOfflineReceiptDto)
                .collect(Collectors.toList());

        // 모임지갑 -> 환전 내역
        List<OfflineReceipt> gwOfflineReceiptList = new ArrayList<>();
        for (Long gwId : map.keySet()) {
            GroupWallet groupWallet = groupWalletRespository.findById(gwId).orElseThrow(() -> new NoSuchElementException("모임 지갑 조회 실패"));
            gwOfflineReceiptList.addAll(offlineReceiptRepository.findAllByGroupWallet(groupWallet));
        }

        List<OfflineReceiptDto> gwReceiptHistory = gwOfflineReceiptList.stream()
                .map(OfflineReceiptDto::toGroupOfflineReceiptDto)
                .collect(Collectors.toList());


        // 개인지갑 내역 + 모임지갑 내역
        List<OfflineReceiptDto> resList = new ArrayList<>();
        resList.addAll(pwReceiptHistory);
        resList.addAll(gwReceiptHistory);
        Collections.sort(resList, Comparator.comparing(OfflineReceiptDto::getReceiptDate).reversed());

        return resList;
    }

    @Override
    public int requestOfflineReceipt(OfflineReceiptRequestDto offlineReceiptRequestDto) {

        WalletType type = WalletType.findByValue(offlineReceiptRequestDto.getWalletType());
        Long walletId = offlineReceiptRequestDto.getWalletId();
        CurrencyCode currencyCode = CurrencyCode.findByValue(offlineReceiptRequestDto.getCurrencyCode());
        Long amount = offlineReceiptRequestDto.getAmount();

        // 선택한 지갑의 balance
        Long balance = selectedWalletBalance(walletId, type);

        // balance보다 높은 금액을 신청한 경우 예외 발생
        Long expectedAmount = expectedExchangeAmount(currencyCode, amount).getExpectedAmount();
        if (expectedAmount > balance) throw new ExchangeException("잔액이 부족합니다.");

        Bank bank = bankRepository.findById(offlineReceiptRequestDto.getBankId()).orElseThrow(() -> new NoSuchElementException("은행 조회 실패"));
        GroupWallet groupWallet = null;
        PersonalWallet personalWallet = null;

        if(type.equals(WalletType.PERSONAL_WALLET)){
            personalWallet = personalWalletRepository.findById(walletId).orElseThrow(() -> new NoSuchElementException("개인지갑 조회 실패"));
        } else if (type.equals(WalletType.GROUP_WALLET)) {
            groupWallet = groupWalletRespository.findById(walletId).orElseThrow(() -> new NoSuchElementException("모임지갑 조회 실패"));
        }

        offlineReceiptRepository.save(
                OfflineReceipt.builder()
                        .receiptDate(offlineReceiptRequestDto.getReceiptDate())
                        .currencyCode(currencyCode)
                        .amount(amount)
                        .receiptState(ReceiptState.WAITING)
                        .bank(bank)
                        .personalWallet(personalWallet)
                        .groupWallet(groupWallet)
                        .build()
        );

        // 선택한 지갑의 balance update
        Long minus = expectedExchangeAmount(currencyCode, amount).getExpectedAmount();

        if (type.equals(WalletType.PERSONAL_WALLET)) personalWallet.setBalance(balance - minus);
        else if (type.equals(WalletType.GROUP_WALLET)) groupWallet.setBalance(balance - minus);

        return 1;
    }

    @Override
    public int cancelOfflineReceipt(Long receipt_id) {
        OfflineReceipt offlineReceipt = offlineReceiptRepository.findById(receipt_id)
                .orElseThrow(() -> new NoSuchElementException("오프라인 환전 내역 조회 실패"));

        if(! offlineReceipt.getReceiptState().equals(ReceiptState.WAITING)){
            throw new ExchangeException("취소할 수 없습니다.");
        }
        offlineReceipt.setReceiptState(ReceiptState.CANCEL);

        // 환불
        Long exchangeAmount
                = expectedExchangeAmount(offlineReceipt.getCurrencyCode(), offlineReceipt.getAmount()).getExpectedAmount();
        if(offlineReceipt.getPersonalWallet() != null){
            PersonalWallet pw = personalWalletRepository.findById(offlineReceipt.getPersonalWallet().getPersonalWalletId())
                    .orElseThrow(() -> new NoSuchElementException("개인 지갑 내역 조회 실패"));
            pw.setBalance(pw.getBalance() + exchangeAmount);
        } else if (offlineReceipt.getGroupWallet() != null) {
            GroupWallet gw = groupWalletRespository.findById(offlineReceipt.getGroupWallet().getGroupWalletId())
                    .orElseThrow(() -> new NoSuchElementException("모임 지갑 내역 조회 실패"));
            gw.setBalance(gw.getBalance() + exchangeAmount);
        }

        return 1;
    }

    @Override
    public int requestExchangeOnline(ExchangeDto exchangeDto) {
        // 원화 -> 외화일 경우만
        exchangeDto.setSellCurrencyCode(CurrencyCode.KRW.getValue());

        Long walletId = exchangeDto.getWalletId();
        CurrencyCode buyCode = CurrencyCode.findByValue(exchangeDto.getBuyCurrencyCode());
        WalletType type = WalletType.findByValue(exchangeDto.getWalletType());
        Long buyAmount = exchangeDto.getBuyAmount();

        // 선택한 지갑의 balance
        Long balance = selectedWalletBalance(walletId, type);

        // 환전 정보 반환
        ExchangeCalDto exchangeCalDto = expectedExchangeAmount(buyCode, buyAmount);
        // 환율 적용
        Long sellAmount = exchangeCalDto.getExpectedAmount();
        exchangeDto.setSellAmount(sellAmount); // 매도 금액 설정

        // balance보다 높은 금액을 신청한 경우 예외 발생
        if (sellAmount > balance) throw new ExchangeException("잔액이 부족합니다.");

        // 환율 설정
        exchangeDto.setExchangeRate(exchangeCalDto.getApplicableExchangeRate());

        if (type.equals(WalletType.PERSONAL_WALLET)) {
            // 개인지갑 -> 환전일 경우
            PersonalWallet personalWallet = personalWalletRepository.findById(walletId)
                    .orElseThrow(() -> new NoSuchElementException("개인 지갑 조회 실패"));
            Long kwBalance = personalWallet.getBalance(); // 원화 balance

            // 해당 코드 환전 내역 조회
            PersonalWalletForeignCurrencyBalance pwfcb = pFCBalanceRepository.findPersonalWalletForeignCurrencyBalanceByCurrencyCodeAndPersonalWallet(buyCode, personalWallet).orElse(null);

            Long fBalance = 0L;
            // 해당코드 환전 내역이 있을 때 - update
            if (pwfcb != null) {
                fBalance = pwfcb.getBalance();
                pwfcb.setBalance(fBalance + buyAmount);
            }

            // 없을 때 insert
            if (pwfcb == null) {
                pFCBalanceRepository.save(PersonalWalletForeignCurrencyBalance.builder()
                        .currencyCode(buyCode)
                        .balance(fBalance + buyAmount)
                        .personalWallet(personalWallet)
                        .build());
            }
            // exchangeDto setter
            exchangeDto.setAfterSellBalance(kwBalance - sellAmount); // 지갑 balance - 매도 금액
            exchangeDto.setAfterBuyBalance(fBalance + buyAmount); // 외화 지갑 balance + 매수 금액
            PersonalWalletExchange personalWalletExchange = ExchangeDto.toPersonalEntity(exchangeDto, personalWallet);
            personalWalletExchangeRepository.save(personalWalletExchange);
            personalWallet.setBalance(kwBalance - sellAmount);


        } else if (type.equals(WalletType.GROUP_WALLET)) {
            // 모임지갑 -> 환전일 경우
            GroupWallet groupWallet = groupWalletRespository.findById(walletId)
                    .orElseThrow(() -> new NoSuchElementException("모임 지갑 조회 실패"));
            ;
            Long kwBalance = groupWallet.getBalance(); // 원화 balance

            // 해당 코드 환전 내역 조회
            GroupWalletForeignCurrencyBalance bwfcb = gFCBalanceRepository.findGroupWalletForeignCurrencyBalanceByCurrencyCodeAndGroupWallet(buyCode, groupWallet).orElse(null);

            Long fBalance = 0L;
            // 해당코드 환전 내역이 있을 때
            if (bwfcb != null) fBalance = bwfcb.getBalance();

            // 외화 지갑 balance insert / update
            if (bwfcb == null) {
                gFCBalanceRepository.save(GroupWalletForeignCurrencyBalance.builder()
                        .currencyCode(buyCode)
                        .balance(fBalance + buyAmount)
                        .groupWallet(groupWallet)
                        .build());
            }

            // exchangeDto setter
            exchangeDto.setAfterSellBalance(kwBalance - sellAmount); // 지갑 balance - 매도 금액
            exchangeDto.setAfterBuyBalance(fBalance + buyAmount); // 외화 지갑 balance + 매수 금액
            GroupWalletExchange groupWalletExchange = ExchangeDto.toGroupEntity(exchangeDto, groupWallet);
            groupWalletExchangeRepository.save(groupWalletExchange);
            groupWallet.setBalance(kwBalance - sellAmount);
        }

        return 1;
    }

    @Override
    public ExchangeCalDto expectedExchangeAmount(CurrencyCode currencyCode, Long amount) {

        // 적용 환율 구하기
        Double applicableExchangeRate = 0.0;
        ExchangeRate exchangeRate = exchangeRateRepository.findFirstByCurrencyCodeOrderByInsertDateDesc(currencyCode)
                .orElseThrow(() -> new NoSuchElementException("환율 조회 실패"));
        if (!currencyCode.equals(CurrencyCode.KRW)) {
            // 원화 -> 외화
            applicableExchangeRate = exchangeRate.getTelegraphicTransferBuyingRate();
        } else {
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

    @Override
    public WalletDetailDto selectedWalletFCBalance(Long walletId, WalletType walletType) {
        // 외화 잔액
        WalletDetailDto dto = new WalletDetailDto();

        if (walletType.equals(WalletType.PERSONAL_WALLET)) {
            // 선택한 지갑이 개인지갑
            PersonalWallet personalWallet = personalWalletRepository.findById(walletId)
                    .orElseThrow(() -> new NoSuchElementException("개인 지갑 조회 실패"));

            List<PersonalWalletForeignCurrencyBalance> personalForeignBalance
                    = pFCBalanceRepository.searchAllByPersonalWallet(personalWallet);

            dto.setBalance(new HashMap<>());
            for (PersonalWalletForeignCurrencyBalance balance : personalForeignBalance) {
                dto.getBalance().put(balance.getCurrencyCode().name(), balance.getBalance());
            }

        } else if (walletType.equals(WalletType.GROUP_WALLET)) {
            // 선택한 지갑이 모임지갑
            GroupWallet groupWallet = groupWalletRespository.findById(walletId)
                    .orElseThrow(() -> new NoSuchElementException("모임 지갑 조회 실패"));
            List<GroupWalletForeignCurrencyBalance> groupForeignBalance =
            gFCBalanceRepository.findByGroupWallet(groupWallet);

            dto.setBalance(new HashMap<>());
            for (GroupWalletForeignCurrencyBalance balance : groupForeignBalance) {
                dto.getBalance().put(balance.getCurrencyCode().name(), balance.getBalance());
            }
        }
        return dto;
    }

    @Override
    public int requestReExchangeOnline(ExchangeDto exchangeDto) {
        // 재환전
        exchangeDto.setBuyCurrencyCode(CurrencyCode.KRW.getValue());

        Long walletId = exchangeDto.getWalletId();
        CurrencyCode sellCode = CurrencyCode.findByValue(exchangeDto.getSellCurrencyCode());
        WalletType type = WalletType.findByValue(exchangeDto.getWalletType());
        Long sellAmount = exchangeDto.getSellAmount();
        Long fcBalance = selectedWalletFCBalance(walletId, type).getBalance().get(sellCode.toString()); // 외화 잔액

        // fcBalance보다 높은 금액을 파는 경우 예외 발생
        if (sellAmount > fcBalance) throw new ExchangeException("외화 잔액이 부족합니다.");

        // 환전 정보 반환
        ExchangeCalDto exchangeCalDto = expectedExchangeAmount(sellCode, sellAmount);
        Long buyAmount = exchangeCalDto.getExpectedAmount();
        Double exchangeRate = exchangeCalDto.getApplicableExchangeRate();

        exchangeDto.setBuyAmount(buyAmount);
        exchangeDto.setExchangeRate(exchangeRate);

        if (type.equals(WalletType.PERSONAL_WALLET)) {
            PersonalWallet personalWallet = personalWalletRepository.findById(walletId)
                    .orElseThrow(() -> new NoSuchElementException("개인 지갑 조회 실패"));
            Long kwBalance = personalWallet.getBalance(); // 원화 balance

            exchangeDto.setAfterBuyBalance(kwBalance + buyAmount);
            exchangeDto.setAfterSellBalance(fcBalance - sellAmount);
            PersonalWalletExchange personalWalletExchange = ExchangeDto.toPersonalEntity(exchangeDto, personalWallet);
            personalWalletExchangeRepository.save(personalWalletExchange);

            PersonalWalletForeignCurrencyBalance pwfcb
                    = pFCBalanceRepository.findPersonalWalletForeignCurrencyBalanceByCurrencyCodeAndPersonalWallet(sellCode, personalWallet).orElseThrow(() -> new NoSuchElementException("해당 통화 내역 조회 실패"));
            pwfcb.setBalance(fcBalance - sellAmount);
            personalWallet.setBalance(kwBalance + buyAmount);

        } else if (type.equals(WalletType.GROUP_WALLET)) {
            // 모임지갑 -> 환전일 경우
            GroupWallet groupWallet = groupWalletRespository.findById(walletId)
                    .orElseThrow(() -> new NoSuchElementException("모임 지갑 조회 실패"));
            ;
            Long kwBalance = groupWallet.getBalance(); // 원화 balance
            exchangeDto.setAfterBuyBalance(kwBalance + buyAmount);
            exchangeDto.setAfterSellBalance(fcBalance - sellAmount);
            GroupWalletExchange groupWalletExchange = ExchangeDto.toGroupEntity(exchangeDto, groupWallet);
            groupWalletExchangeRepository.save(groupWalletExchange);

            // 해당 코드 환전 내역 조회
            GroupWalletForeignCurrencyBalance bwfcb
                    = gFCBalanceRepository.findGroupWalletForeignCurrencyBalanceByCurrencyCodeAndGroupWallet(sellCode, groupWallet).orElseThrow(() -> new NoSuchElementException("해당 통화 내역 조회 실패"));
            bwfcb.setBalance(fcBalance - sellAmount);
            groupWallet.setBalance(kwBalance + buyAmount);
        }

        return 1;
    }
}
