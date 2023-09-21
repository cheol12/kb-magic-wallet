package kb04.team02.web.mvc.group.repository;

import kb04.team02.web.mvc.group.entity.DuePayment;
import kb04.team02.web.mvc.group.entity.GroupWallet;
import kb04.team02.web.mvc.group.entity.Participation;
import kb04.team02.web.mvc.group.entity.ParticipationState;
import kb04.team02.web.mvc.member.entity.Member;
import kb04.team02.web.mvc.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Commit
class DuePaymentRepositoryTest {

    @Autowired
    private DuePaymentRepository duePaymentRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private GroupWalletRespository groupWalletRepository;
    @Autowired
    private ParticipationRepository participationRepository;

    @Test
    void insertData() {

        Member member = memberRepository.findById(2L).get();
        GroupWallet groupWallet = groupWalletRepository.findById(8L).get();

        duePaymentRepository.save(DuePayment.builder()
                .member(member)
                .groupWallet(groupWallet)
                .build());
    }

    //        @Test
    void thisMonthDuePayment() {
        GroupWallet groupWallet = groupWalletRepository.findById(8L).get();

        List<Participation> participationList = participationRepository.findByGroupWalletAndParticipationState(groupWallet, ParticipationState.PARTICIPATED);

        for (Participation p :
                participationList) {
            Member member = memberRepository.findById(p.getMemberId()).get();
            DuePayment duePayment = duePaymentRepository.findByGroupWalletAndMemberAndInsertDateAfter(
                    groupWallet, member, firstDayThisMonth()
            ).orElse(null);
            System.out.print(member.getName());
            if (duePayment == null) {
                System.out.println(" 이번달 안냄");
            } else {
                System.out.println(duePayment.getInsertDate());
            }
        }
    }

    private LocalDateTime firstDayThisMonth() {
        // 현재 날짜를 가져옵니다.
        LocalDate currentDate = LocalDate.now();

        // 현재 월의 첫 번째 날짜를 가져옵니다.
        LocalDate firstDayOfMonth = currentDate.with(TemporalAdjusters.firstDayOfMonth());

        // 자정으로 시간을 설정합니다.
        LocalTime midnight = LocalTime.MIDNIGHT;

        // 현재 월의 첫 번째 날짜와 자정 시간을 결합하여 LocalDateTime을 생성합니다.
        return LocalDateTime.of(firstDayOfMonth, midnight);
//        return LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIDNIGHT);
    }
}