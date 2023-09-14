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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class PersonalWalletServiceImplTest {

    @Autowired
    private PersonalWalletService personalWalletService;

    static LoginMemberDto loggedIn;

    @BeforeAll
    static void beforeAll() {
//        loggedIn = LoginMemberDto.builder()
//                .memberId()
//                .id()
//                .name()
//                .personalWalletId()
//                .groupWalletIdList()
//                .build();
    }

    @Order(1)
    @Name("개인지갑 메인 정보")
    @Test
    void personalWallet() {

        WalletDetailDto walletDetailDto = personalWalletService.personalWallet(loggedIn);

        // TODO 검증
    }

    @Order(2)
    @Name("은행계좌 -> 개인지갑 충전 성공 ")
    @Test
    void personalWalletDeposit() {
        PersonalWalletTransferDto dto = new PersonalWalletTransferDto();
        dto.setMemberId(loggedIn.getMemberId());
        dto.setAmount(30000L);


    }

    @Order(3)
    @Name("개인지갑 -> 은행계좌 환불 성공 ")
    @Test
    void personalWalletWithdraw() {
        PersonalWalletTransferDto dto = new PersonalWalletTransferDto();
        dto.setMemberId(loggedIn.getMemberId());
        dto.setAmount(30000L);

    }

    @Order(4)
    @Name("개인지갑 -> 은행계좌 환불 실패 ")
    @Test
    void personalWalletWithdrawFailed() {
        PersonalWalletTransferDto dto = new PersonalWalletTransferDto();
        dto.setMemberId(loggedIn.getMemberId());
        dto.setAmount(30000L);

    }
}