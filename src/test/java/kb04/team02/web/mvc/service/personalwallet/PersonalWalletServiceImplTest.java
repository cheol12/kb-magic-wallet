package kb04.team02.web.mvc.service.personalwallet;

import jdk.jfr.Name;
import kb04.team02.web.mvc.domain.member.Member;
import kb04.team02.web.mvc.dto.LoginMemberDto;
import kb04.team02.web.mvc.dto.PersonalWalletTransferDto;
import kb04.team02.web.mvc.dto.WalletDetailDto;
import kb04.team02.web.mvc.repository.member.MemberRepository;
import kb04.team02.web.mvc.repository.wallet.personal.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class PersonalWalletServiceImplTest {

    @Autowired
    private PersonalWalletService personalWalletService;

    static LoginMemberDto loggedIn;

    @BeforeEach
    void setUp() {
        loggedIn = LoginMemberDto.builder()
                .memberId(1L)
                .id("id1")
                .name("User 1")
                .personalWalletId(1L)
                .groupWalletIdList(new HashMap<>())
                .build();
    }

    @Order(1)
    @Test
    void personalWallet() {

        WalletDetailDto walletDetailDto = personalWalletService.personalWallet(loggedIn);

        System.out.println(walletDetailDto);
    }

    @Order(2)
    @Test
    void personalWalletDeposit() {
        PersonalWalletTransferDto dto = new PersonalWalletTransferDto();
        dto.setMemberId(loggedIn.getMemberId());
        dto.setAmount(30000L);


    }

    @Order(3)
    @Test
    void personalWalletWithdraw() {
        PersonalWalletTransferDto dto = new PersonalWalletTransferDto();
        dto.setMemberId(loggedIn.getMemberId());
        dto.setAmount(30000L);

    }

    @Order(4)
    @Test
    void personalWalletWithdrawFailed() {
        PersonalWalletTransferDto dto = new PersonalWalletTransferDto();
        dto.setMemberId(loggedIn.getMemberId());
        dto.setAmount(30000L);

    }
}