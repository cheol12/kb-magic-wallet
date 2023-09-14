package kb04.team02.web.mvc.service.personalwallet;

import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.member.entity.Address;
import kb04.team02.web.mvc.member.entity.Member;
import kb04.team02.web.mvc.personal.entity.PersonalWallet;
import kb04.team02.web.mvc.personal.entity.PersonalWalletForeignCurrencyBalance;
import kb04.team02.web.mvc.common.dto.LoginMemberDto;
import kb04.team02.web.mvc.personal.dto.PersonalWalletTransferDto;
import kb04.team02.web.mvc.common.dto.WalletDetailDto;
import kb04.team02.web.mvc.common.exception.InsufficientBalanceException;
import kb04.team02.web.mvc.member.repository.MemberRepository;
import kb04.team02.web.mvc.personal.repository.PersonalWalletForeignCurrencyBalanceRepository;
import kb04.team02.web.mvc.personal.repository.PersonalWalletRepository;
import kb04.team02.web.mvc.personal.repository.PersonalWalletTransferRepository;
import kb04.team02.web.mvc.personal.service.PersonalWalletService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class PersonalWalletServiceImplTest {

    @Autowired
    private PersonalWalletService personalWalletService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PersonalWalletRepository personalWalletRepository;
    @Autowired
    private PersonalWalletForeignCurrencyBalanceRepository personalWalletForeignCurrencyBalanceRepository;
    @Autowired
    private PersonalWalletTransferRepository personalWalletTransferRepository;

    static LoginMemberDto loggedIn;

    void setUp() {// TODO @BeforeEach 는 왜 안되는거야!!!
        Member member = memberRepository.save(
                Member.builder()
                        .id("member1")
                        .password("1234")
                        .name("name1")
                        .address(new Address(
                                "seoul", "sunrung 428", "20981"
                        ))
                        .phoneNumber("010-0000-0000")
                        .email("email@gmail.com")
                        .payPassword("283748")
                        .bankAccount("3333-1298374-19")
                        .build()
        );

        PersonalWallet personalWallet = personalWalletRepository.save(
                PersonalWallet.builder().member(member).build()
        );
        personalWallet.setBalance(100000L);// 10,000 won

        PersonalWalletForeignCurrencyBalance USD = personalWalletForeignCurrencyBalanceRepository.save(
                PersonalWalletForeignCurrencyBalance.builder()
                        .currencyCode(CurrencyCode.USD)
                        .balance(10L)// 10 dollar
                        .personalWallet(personalWallet)
                        .build()
        );
        PersonalWalletForeignCurrencyBalance JPY = personalWalletForeignCurrencyBalanceRepository.save(
                PersonalWalletForeignCurrencyBalance.builder()
                        .currencyCode(CurrencyCode.JPY)
                        .balance(1000L)// 1000 yen
                        .personalWallet(personalWallet)
                        .build()
        );

        loggedIn = LoginMemberDto.builder()
                .memberId(member.getMemberId())
                .id(member.getId())
                .name(member.getName())
                .personalWalletId(1L)
                .groupWalletIdList(new HashMap<>())
                .build();
    }

    @AfterEach
    void tearDown() {
        personalWalletTransferRepository.deleteAll();
        personalWalletForeignCurrencyBalanceRepository.deleteAll();
        personalWalletRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Order(1)
    @Test
    void personalWallet() {
        setUp();
        WalletDetailDto walletDetailDto = personalWalletService.personalWallet(loggedIn);

        System.out.println(walletDetailDto);
    }

    @Order(2)
    @Test
    void personalWalletDeposit() {
        setUp();

        PersonalWalletTransferDto dto = new PersonalWalletTransferDto();
        dto.setMemberId(loggedIn.getMemberId());
        dto.setAmount(30000L);
        personalWalletService.personalWalletDeposit(dto);

        // 개인지갑 메인에서 확인
        WalletDetailDto walletDetailDto = personalWalletService.personalWallet(loggedIn);

        assertThat(walletDetailDto.getList().size()).isEqualTo(1);
        System.out.println(walletDetailDto);
    }

    @Order(3)
    @Test
    void personalWalletWithdraw() {
        setUp();

        PersonalWalletTransferDto dto = new PersonalWalletTransferDto();
        dto.setMemberId(loggedIn.getMemberId());
        dto.setAmount(30000L);
        personalWalletService.personalWalletWithdraw(dto);

        // 개인지갑 메인에서 확인
        WalletDetailDto walletDetailDto = personalWalletService.personalWallet(loggedIn);

        assertThat(walletDetailDto.getList().size()).isEqualTo(1);
        System.out.println(walletDetailDto);
    }

    @Order(4)
    @Test
    void personalWalletWithdrawFailed() {
        setUp();

        PersonalWalletTransferDto dto = new PersonalWalletTransferDto();
        dto.setMemberId(loggedIn.getMemberId());
        dto.setAmount(300000L);
        assertThatThrownBy(() -> {
            personalWalletService.personalWalletWithdraw(dto);
        }).isInstanceOf(InsufficientBalanceException.class);

        // 개인지갑 메인에서 확인
        WalletDetailDto walletDetailDto = personalWalletService.personalWallet(loggedIn);

        System.out.println(walletDetailDto);
    }
}