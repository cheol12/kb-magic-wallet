package kb04.team02.web.mvc.repository.wallet.group;

import kb04.team02.web.mvc.domain.common.CurrencyCode;
import kb04.team02.web.mvc.domain.member.Address;
import kb04.team02.web.mvc.domain.member.Member;
import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import kb04.team02.web.mvc.domain.wallet.group.GroupWalletForeignCurrencyBalance;
import kb04.team02.web.mvc.repository.member.MemberRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Commit
class GroupWalletForeignCurrencyBalanceRepositoryTest {

    @Autowired
    private GroupWalletForeignCurrencyBalanceRepository foreignCurrencyRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private GroupWalletRespository groupWalletRespository;

    @AfterEach
    public void afterEach() {
        foreignCurrencyRepository.deleteAll();
        groupWalletRespository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("findByGroupWallet")
    public void findByGroupWallet() throws Exception {
        //given
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
        memberRepository.save(member1);

        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
                .nickname("alias")
                .dueCondition(true)
                .member(member1)
                .balance(0L)
                .build());

        foreignCurrencyRepository.save(GroupWalletForeignCurrencyBalance.builder()
                .groupWallet(wallet)
                .currencyCode(CurrencyCode.USD)
                .balance(10000L)
                .build());
        //when
        List<GroupWalletForeignCurrencyBalance> byGroupWallet =
                foreignCurrencyRepository.findByGroupWallet(wallet);

        //then
        for (GroupWalletForeignCurrencyBalance balance : byGroupWallet) {
            System.out.println(balance.getCurrencyCode());
            System.out.println(balance.getBalance());
        }
    }
}