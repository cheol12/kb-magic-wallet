package kb04.team02.web.mvc.service.groupwallet;

import kb04.team02.web.mvc.domain.member.Address;
import kb04.team02.web.mvc.domain.member.Member;
import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import kb04.team02.web.mvc.exception.InsertException;
import kb04.team02.web.mvc.repository.member.MemberRepository;
import kb04.team02.web.mvc.repository.wallet.group.GroupWalletRespository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Commit
public class GroupWalletServiceImplTest {

    @Autowired
    private GroupWalletRespository groupWalletRep;

    @Autowired
    private GroupWalletService groupWalletService;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    public void afterEach() {
        groupWalletRep.deleteAll();
    }

    @Test
    @DisplayName("selectAllMyGroupWallet")
    void selectAllMyGroupWallet(){

        // given
        Address address = new Address("서울시", "국회대로 54길 10", "07246");

        //1.
        Member member = Member.builder()
                .address(address)
                .email("cheol02@naver.com")
                .bankAccount("111-111-1111-02")
                .name("이이잉")
                .payPassword("123123")
                .id("cheol02")
                .password("123123")
                .phoneNumber("010-1111-1102").build();
        Member save = memberRepository.save(member);

        String nickname = "내 모";

        GroupWallet groupWallet;

        groupWallet = GroupWallet.builder()
                .dueCondition(false)
                .member(member)
                .nickname(nickname)
                .build();  //new GroupWallet();
        groupWalletRep.save(groupWallet);

        nickname = "내 내";
        groupWallet = GroupWallet.builder()
                .dueCondition(false)
                .member(member)
                .nickname(nickname)
                .build();
        groupWalletRep.save(groupWallet);

        // when
        List<GroupWallet> list = groupWalletService.selectAllMyGroupWallet(member);

        // then
        assertThat(list.size()).isEqualTo(2);

    }

//    @Test
    @DisplayName("createGroupWallet")
    void createGroupWallet() {

//        GroupWallet groupWallet = groupWalletRep.findByMember(member);

        GroupWallet groupWalletSave;
        // given
        Address address = new Address("서울시", "국회대로 54길 10", "07246");

        Member member = Member.builder()
                .address(address)
                .email("cheol03@naver.com")
                .bankAccount("111-111-1111-03")
                .name("이이잉")
                .payPassword("123123")
                .id("cheol03")
                .password("123123")
                .phoneNumber("010-1111-1103").build();
        Member save = memberRepository.save(member);

        String nickname = "이아";

        int result = groupWalletService.createGroupWallet(save, nickname);

        // participation 테이블에도 모임장 데이터를 추가해야하지 않나

        assertThat(result).isEqualTo(1);
    }
}
