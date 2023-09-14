package kb04.team02.web.mvc.service.exchange;

import kb04.team02.web.mvc.domain.bank.Bank;
import kb04.team02.web.mvc.domain.bank.OfflineReceipt;
import kb04.team02.web.mvc.domain.bank.ReceiptState;
import kb04.team02.web.mvc.domain.common.CurrencyCode;
import kb04.team02.web.mvc.domain.member.Address;
import kb04.team02.web.mvc.domain.member.Member;
import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import kb04.team02.web.mvc.domain.wallet.personal.PersonalWallet;
import kb04.team02.web.mvc.dto.BankDto;
import kb04.team02.web.mvc.dto.SavingDto;
import kb04.team02.web.mvc.dto.WalletDto;
import kb04.team02.web.mvc.repository.bank.BankRepository;
import kb04.team02.web.mvc.repository.bank.OfflineReceiptRepository;
import kb04.team02.web.mvc.repository.member.MemberRepository;
import kb04.team02.web.mvc.repository.wallet.group.GroupWalletRespository;
import kb04.team02.web.mvc.repository.wallet.personal.PersonalWalletRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@Commit
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

    @AfterEach
    public void afterEach() {
        System.out.println("afterEach.....................................");
        bankRepository.deleteAll();
        offlineReceiptRepository.deleteAll();
        personalWalletRepository.deleteAll();
        groupWalletRespository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("selectBank")
    void bankList() {
        bankRepository.save(Bank.builder()
                .name("국민은행")
                .address(new Address("서울시 강남구", "삼성로 406","06185"))
                .build());
        bankRepository.save(Bank.builder()
                .name("국민은행")
                .address(new Address("서울시 강남구", "삼성로 407","06185"))
                .build());
        bankRepository.save(Bank.builder()
                .name("국민은행")
                .address(new Address("서울시 강남구", "삼성로 408","06185"))
                .build());
        bankRepository.save(Bank.builder()
                .name("국민은행")
                .address(new Address("서울시 강남구", "삼성로 409","06185"))
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
    void selectedWalletBalance() {
    }

    @Test
    @DisplayName("selectOfflineReceiptHistory")
    void testOfflineReceiptHistory() {
    }

    @Test
    void requestOfflineReceipt() {
    }

    @Test
    void cancelOfflineReceipt() {
    }

    @Test
    void requestExchangeOnline() {
    }
}