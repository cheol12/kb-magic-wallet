package kb04.team02.web.mvc.repository.wallet.group;

import kb04.team02.web.mvc.domain.member.Address;
import kb04.team02.web.mvc.domain.member.Member;
import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import kb04.team02.web.mvc.repository.member.MemberRepository;
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
public class GroupWalletRepositoryTest {

    @Autowired
    private GroupWalletRespository groupWalletRep;

    @Autowired
    private MemberRepository memberRep;

    @AfterEach
    public void afterEach(){
        groupWalletRep.deleteAll();
    }

    @Test
    @DisplayName("createGroupWallet")
    void createGroupWallet() throws Exception{

        // given
        Address address = new Address("서울시", "국회대로 54길 10", "07246");

        //1.
        Member member = Member.builder()
                .address(address)
                .email("cheol@naver.com")
                .bankAccount("111-111-1111-12")
                .name("김철")
                .payPassword("123123")
                .id("cheol")
                .password("1231123")
                .phoneNumber("010-1111-1112").build();
        Member save = memberRep.save(member);

        //2.
//        Member save = MemberRepository.save(Member.builder()
//                .address(address)
//                .email("cheol@naver.com")
//                .bankAccount("111-111-1111-12")
//                .name("김철")
//                .payPassword("123123")
//                .id("jang")
//                .password("1231123")
//                .phoneNumber("010-1111-1112").build());

        String nickname = "내 모암";

        GroupWallet groupWallet;

        groupWallet = GroupWallet.builder()
                .dueCondition(false)
                .member(save)
                .nickname(nickname)
                .build();  //new GroupWallet();
        groupWalletRep.save(groupWallet);

        System.out.println("save의 nickname : " + groupWallet.getNickname());

        // when
        List<GroupWallet> findByMember = groupWalletRep.findByMember(save);

        System.out.println("rep의 nickname : " + findByMember.get(0).getNickname());

        // then
        assertThat(findByMember.get(0).getNickname()).isEqualTo(groupWallet.getNickname());

    }


    @Test
    @DisplayName("findAllByMemberOrderByGroupWalletId")
    public void findAllByMemberOrderByGroupWalletId() throws Exception{

        // given
        Address address = new Address("서울시", "국회대로 54길 10", "07246");

        //1.
        Member member = Member.builder()
                .address(address)
                .email("cheol01@naver.com")
                .bankAccount("111-111-1111-01")
                .name("이이잉")
                .payPassword("123123")
                .id("cheol01")
                .password("123123")
                .phoneNumber("010-1111-1101").build();
        Member save = memberRep.save(member);

        String nickname = "내 모";

        GroupWallet groupWallet;

        groupWallet = GroupWallet.builder()
                .dueCondition(false)
                .member(save)
                .nickname(nickname)
                .build();  //new GroupWallet();
        groupWalletRep.save(groupWallet);

        nickname = "내 내";
        groupWallet = GroupWallet.builder()
                .dueCondition(false)
                .member(save)
                .nickname(nickname)
                .build();
        groupWalletRep.save(groupWallet);

        // when
        List<GroupWallet> list = groupWalletRep.findAllByMemberOrderByGroupWalletId(member);

        // then
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("getGroupWalletDetail")
    public void getGroupWalletDetail() throws Exception{


    }






}
