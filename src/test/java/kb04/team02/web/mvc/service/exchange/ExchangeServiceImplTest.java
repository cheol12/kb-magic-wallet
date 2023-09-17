package kb04.team02.web.mvc.service.exchange;

import kb04.team02.web.mvc.exchange.dto.*;
import kb04.team02.web.mvc.exchange.entity.Bank;
import kb04.team02.web.mvc.exchange.entity.OfflineReceipt;
import kb04.team02.web.mvc.exchange.entity.ReceiptState;
import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.exchange.service.ExchangeService;
import kb04.team02.web.mvc.member.entity.Address;
import kb04.team02.web.mvc.member.entity.Member;
import kb04.team02.web.mvc.member.entity.Role;
import kb04.team02.web.mvc.common.entity.WalletType;
import kb04.team02.web.mvc.group.entity.GroupWallet;
import kb04.team02.web.mvc.personal.entity.PersonalWallet;
import kb04.team02.web.mvc.exchange.repository.BankRepository;
import kb04.team02.web.mvc.exchange.repository.OfflineReceiptRepository;
import kb04.team02.web.mvc.member.repository.MemberRepository;
import kb04.team02.web.mvc.group.repository.GroupWalletRespository;
import kb04.team02.web.mvc.personal.entity.PersonalWalletForeignCurrencyBalance;
import kb04.team02.web.mvc.personal.repository.PersonalWalletForeignCurrencyBalanceRepository;
import kb04.team02.web.mvc.personal.repository.PersonalWalletRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class ExchangeServiceImplTest {

    @Autowired
    private ExchangeService exchangeService;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private OfflineReceiptRepository offlineReceiptRepository;
    @Autowired
    private PersonalWalletRepository personalWalletRepository;
    @Autowired
    private GroupWalletRespository groupWalletRespository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PersonalWalletForeignCurrencyBalanceRepository personalForeignCurrencyBalanceRepository;

/*    @AfterEach
    public void afterEach() {
        System.out.println("afterEach.....................................");
        bankRepository.deleteAll();
        offlineReceiptRepository.deleteAll();
        personalWalletRepository.deleteAll();
        groupWalletRespository.deleteAll();
        memberRepository.deleteAll();
    }*/

    @Test
    @DisplayName("selectBank")
    void bankList() {
        bankRepository.save(Bank.builder()
                .name("국민은행")
                .address(new Address("서울시 강남구", "삼성로 406", "06185"))
                .build());
        bankRepository.save(Bank.builder()
                .name("국민은행")
                .address(new Address("서울시 강남구", "삼성로 407", "06185"))
                .build());
        bankRepository.save(Bank.builder()
                .name("국민은행")
                .address(new Address("서울시 강남구", "삼성로 408", "06185"))
                .build());
        bankRepository.save(Bank.builder()
                .name("국민은행")
                .address(new Address("서울시 강남구", "삼성로 409", "06185"))
                .build());

        List<BankDto> bankDtoList = exchangeService.bankList();
        for (BankDto bankDto : bankDtoList) {
            System.out.println(bankDto.getName());
            System.out.println(bankDto.getAddress().getCity() + bankDto.getAddress().getStreet());
            System.out.println("============================");
        }
    }


    @Test
    @DisplayName("walletList")
    void walletList() {
        Address address = new Address("서울시", "국회대로 54길 10", "07246");
        Member member1 = Member.builder()
                .address(address)
                .email("@naver1.com")
                .bankAccount("111-111-1111-11")
                .name("김진형")
                .payPassword("123123")
                .id("qweqwe1")
                .password("1231123")
                .phoneNumber("010-1111-1111").build();
        Member member = memberRepository.save(member1);

        groupWalletRespository.save(GroupWallet.builder()
                .nickname("alias")
                .dueCondition(true)
                .member(member)
                .balance(0L)
                .build());

        groupWalletRespository.save(GroupWallet.builder()
                .nickname("alias2")
                .dueCondition(true)
                .member(member)
                .balance(10L)
                .build());

        personalWalletRepository.save(PersonalWallet.builder()
                .member(member).build());

        System.out.println("===================================");
        List<WalletDto> list = exchangeService.WalletList(member.getMemberId());
        System.out.println("list size: " + list.size());
    }

    @Test
    @DisplayName("walletBalance")
    void selectedWalletBalance() {

        Address address = new Address("서울특별시", "국회대로54길 10", "07246");
        Member member1 = Member.builder()
                .id("qwer")
                .password("qwer")
                .name("김진형")
                .address(address)
                .phoneNumber("010-1111-1111")
                .email("qwer@example.com")
                .payPassword("qwer")
                .bankAccount("111-111-111111")
                .build();

        memberRepository.save(member1);

        PersonalWallet personalWallet1 = PersonalWallet.builder().balance(1000000L).member(member1).build();

        personalWalletRepository.save(personalWallet1);

        GroupWallet groupWallet1 = GroupWallet.builder()
                .nickname("모임지갑 1")
                .balance(100000L) // 초기 잔액 설정
                .dueCondition(false)
                .dueAccumulation(0L)
                .dueDate(0)
                .due(0L)
                .member(member1)
                .build();

        groupWalletRespository.save(groupWallet1);

        assertThat(exchangeService.selectedWalletBalance(groupWallet1.getGroupWalletId(), WalletType.GROUP_WALLET))
                .isEqualTo(groupWallet1.getBalance());
        assertThat(exchangeService.selectedWalletBalance(personalWallet1.getPersonalWalletId(), WalletType.PERSONAL_WALLET))
                .isEqualTo(personalWallet1.getBalance());

    }

    @Test
    @DisplayName("selectOfflineReceiptHistory")
    void testOfflineReceiptHistory() {
        Map<Long, Role> map = new HashMap<>();
        map.put(141L, Role.CHAIRMAN);
        map.put(140L, Role.GENERAL);
        List<OfflineReceiptDto> list = exchangeService.offlineReceiptHistory(41L, map);
        System.out.println("=========================================");
        for (OfflineReceiptDto o : list) {
            System.out.println("지갑 타입: " + o.getWalletType());
            System.out.println("환전 내역: " + o.getAmount());
            System.out.println("은행: " + o.getBankName());
        }
    }

    @Test
    @DisplayName("requestOfflineReceipt")
    void requestOfflineReceipt() {

        Address address = new Address("서울특별시", "국회대로54길 10", "07246");
        Member member1 = Member.builder()
                .id("qwer")
                .password("qwer")
                .name("김진형")
                .address(address)
                .phoneNumber("010-1111-1111")
                .email("qwer@example.com")
                .payPassword("qwer")
                .bankAccount("111-111-111111")
                .build();

        memberRepository.save(member1);

        PersonalWallet personalWallet1 = PersonalWallet.builder().balance(1000000L).member(member1).build();
        personalWalletRepository.save(personalWallet1);


        GroupWallet groupWallet1 = GroupWallet.builder()
                .nickname("모임지갑 1")
                .balance(100000L) // 초기 잔액 설정
                .dueCondition(false)
                .dueAccumulation(0L)
                .dueDate(0)
                .due(0L)
                .member(member1)
                .build();

        groupWalletRespository.save(groupWallet1);
        Address bankAddr1 = new Address("서울특별시", "어쩌구저쩌구1길 99", "11111");

        Bank bank1 = bankRepository.save(Bank.builder()
                .name("국민은행 시청점")
                .address(bankAddr1)
                .build());


        offlineReceiptRepository.save(OfflineReceipt.builder()
                .currencyCode(CurrencyCode.USD)
                .amount(500L)
                .receiptState(ReceiptState.WAITING)
                .bank(bank1)
                .personalWallet(null)
                .groupWallet(groupWallet1)
                .build());

        OfflineReceiptRequestDto offlineReceiptDto = OfflineReceiptRequestDto.builder()
                .receiptDate(LocalDateTime.now())
                .currencyCode(1)
                .amount(1L)
                .bankId(bank1.getBankId())
                .walletId(groupWallet1.getGroupWalletId())
                .walletType(1).build();

        int res = exchangeService.requestOfflineReceipt(offlineReceiptDto);
        System.out.println(res);

        OfflineReceiptRequestDto offlineReceiptDto2 = OfflineReceiptRequestDto.builder()
                .receiptDate(LocalDateTime.now())
                .currencyCode(1)
                .amount(1L)
                .bankId(5L)
                .walletId(41L)
                .walletType(2).build();
        int res2 = exchangeService.requestOfflineReceipt(offlineReceiptDto2);
        System.out.println(res2);

    }

    @Test
    @DisplayName("cancelOfflineReceipt")
    void cancelOfflineReceipt() {
        Address address = new Address("서울특별시", "국회대로54길 10", "07246");
        Member member1 = Member.builder()
                .id("qwer")
                .password("qwer")
                .name("김진형")
                .address(address)
                .phoneNumber("010-1111-1111")
                .email("qwer@example.com")
                .payPassword("qwer")
                .bankAccount("111-111-111111")
                .build();

        memberRepository.save(member1);

        PersonalWallet personalWallet1 = PersonalWallet.builder().balance(1000000L).member(member1).build();
        personalWalletRepository.save(personalWallet1);


        GroupWallet groupWallet1 = GroupWallet.builder()
                .nickname("모임지갑 1")
                .balance(100000L) // 초기 잔액 설정
                .dueCondition(false)
                .dueAccumulation(0L)
                .dueDate(0)
                .due(0L)
                .member(member1)
                .build();

        groupWalletRespository.save(groupWallet1);
        Address bankAddr1 = new Address("서울특별시", "어쩌구저쩌구1길 99", "11111");

        Bank bank1 = bankRepository.save(Bank.builder()
                .name("국민은행 시청점")
                .address(bankAddr1)
                .build());
        OfflineReceipt offlineReceipt = offlineReceiptRepository.save(OfflineReceipt.builder()
                .currencyCode(CurrencyCode.USD)
                .amount(500L)
                .receiptState(ReceiptState.WAITING)
                .bank(bank1)
                .personalWallet(null)
                .groupWallet(groupWallet1)
                .build());
        int res = exchangeService.cancelOfflineReceipt(offlineReceipt.getOfflineReceiptId());
        System.out.println(res);
    }

    @Test
    @DisplayName("requestExchangeOnline")
    void requestExchangeOnline() {
        Address address = new Address("서울특별시", "국회대로54길 10", "07246");
        Member member1 = Member.builder()
                .id("qwer")
                .password("qwer")
                .name("김진형")
                .address(address)
                .phoneNumber("010-1111-1111")
                .email("qwer@example.com")
                .payPassword("qwer")
                .bankAccount("111-111-111111")
                .build();

        memberRepository.save(member1);

        PersonalWallet personalWallet1 = PersonalWallet.builder().balance(1000000L).member(member1).build();
        personalWalletRepository.save(personalWallet1);


        GroupWallet groupWallet1 = GroupWallet.builder()
                .nickname("모임지갑 1")
                .balance(100000L) // 초기 잔액 설정
                .dueCondition(false)
                .dueAccumulation(0L)
                .dueDate(0)
                .due(0L)
                .member(member1)
                .build();

        groupWalletRespository.save(groupWallet1);
        Address bankAddr1 = new Address("서울특별시", "어쩌구저쩌구1길 99", "11111");

        Bank bank1 = bankRepository.save(Bank.builder()
                .name("국민은행 시청점")
                .address(bankAddr1)
                .build());


        offlineReceiptRepository.save(OfflineReceipt.builder()
                .currencyCode(CurrencyCode.USD)
                .amount(500L)
                .receiptState(ReceiptState.WAITING)
                .bank(bank1)
                .personalWallet(null)
                .groupWallet(groupWallet1)
                .build());

        personalForeignCurrencyBalanceRepository.save(PersonalWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.USD)
                .balance(100L)
                .personalWallet(personalWallet1)
                .build());

        ExchangeDto dto = new ExchangeDto();
        dto.setBuyAmount(1L);
        dto.setBuyCurrencyCode(CurrencyCode.USD.getValue());
        dto.setWalletId(personalWallet1.getPersonalWalletId());
        dto.setWalletType(WalletType.PERSONAL_WALLET.getValue());

        int res = exchangeService.requestExchangeOnline(dto);
        System.out.println(res);

        ExchangeDto dto2 = new ExchangeDto();
        dto2.setBuyAmount(1L);
        dto2.setBuyCurrencyCode(CurrencyCode.USD.getValue());
        dto2.setWalletId(140L);
        dto2.setWalletType(WalletType.GROUP_WALLET.getValue());

        int res2 = exchangeService.requestExchangeOnline(dto2);
        System.out.println(res2);
    }

    @Test
    @DisplayName("expectedExchangeAmount")
    void expectedExchangeAmount() {
        System.out.println("==================================");
        System.out.println(exchangeService.expectedExchangeAmount(CurrencyCode.USD, 1L).getApplicableExchangeRate());
        System.out.println(exchangeService.expectedExchangeAmount(CurrencyCode.USD, 1L).getExpectedAmount());

    }
}