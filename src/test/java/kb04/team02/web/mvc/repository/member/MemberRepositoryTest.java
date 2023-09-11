package kb04.team02.web.mvc.repository.member;

import kb04.team02.web.mvc.domain.member.Address;
import kb04.team02.web.mvc.domain.member.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Commit // DML 작업 시 필수
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    public void afterEach() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("memberInsert")
    public void memberInsert () throws Exception {
        //given
        Address address = new Address("서울시", "국회대로 54길 10", "07246");
        Member member = Member.builder()
                .address(address)
                .email("@naver2.com")
                .bankAccount("111-111-1111-11")
                .name("김진형")
                .payPassword("123123")
                .id("qweqwe1")
                .password("1231123")
                .phoneNumber("010-1111-1111").build();
        Member save = memberRepository.save(member);

        //when
        Member findMember = memberRepository.findById(save.getMemberId()).get();

        //then
        assertThat(findMember).isEqualTo(save);
    }
}