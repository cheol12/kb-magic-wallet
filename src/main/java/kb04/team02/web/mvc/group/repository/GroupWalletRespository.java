package kb04.team02.web.mvc.group.repository;

import kb04.team02.web.mvc.member.entity.Member;
import kb04.team02.web.mvc.common.entity.Payment;
import kb04.team02.web.mvc.group.entity.GroupWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface GroupWalletRespository extends JpaRepository<GroupWallet, Long> {

    // duepayment = 회비납부내역

    /**
     * ROWNUM 12
     *
     * (모임지갑 생성시 사용)
     *
     * JPA : GroupWalletRepository.save(GroupWallet groupWallet);
     * Member 객체로
     * */
//    GroupWallet findByMember(Member member);

    /**
     * ROWNUM 10
     * 모임지갑 메인화면
     * 나의 모임지갑 전부 검색
     * */
    List<GroupWallet> findAllByMember_MemberId(Long memberId);


    /**
     * ROWNUM 13
     * 모임지갑 상세화면
     * 선택한 나의 모임지갑의 모든 거래내역(외화내역, 이체내역, 환전내역)
     */
    Payment findAllByGroupWalletIdOrderByInsertDate(Long groupWalletId);

//    여러 종류의 거래내역을 오버라이딩해서 한 번에 불러오는 게 헷갈림다


    /**
     *
     * */

    /**
     * ROWNUM 14
     * 모임지갑 삭제
     * 선택한 나의 모임지갑을 삭제한다. (모임장만 가능)
     * */
    @Transactional
    int deleteGroupWalletByGroupWalletId(Long groupWalletId);


    // ROWNUM 15번 : 나의 모임지갑에 새로운 모임원 초대링크 보내기 (모임장만 가능) = ? : 모르겠슴다


    // ROWNUM 16번 모임지갑 모임원 리스트 요청은 ParticipationRepository에 있음


    /**
     * ROWNUM 18
     * 내 모임지갑에서 선택한 모임원을 강퇴하기 (모임장만 가능)
     *
     * SQL
     *
     * 그냥 해당 모임원을 삭제하기
     * delete from participation
     * where group_wallet_id = ? and member_id = ?;
     * JPA : ParticipationRepository.save(int memberId);
     * */
    int deleteByGroupWalletIdAndMember(Long groupWalletId, Member memberId);

    /**
     * ROWNUM 19
     * 내 모임지갑의 회비 규칙 불러오기 = 모임지갑 정보 불러오기
     * */
    GroupWallet findByGroupWalletId(Long groupWalletId);

    /**
     * ROWNUM 20
     * 내 모임지갑의 회비 규칙 생성하기 (모임장만 가능)
     *
     * update group_wallet
     * set (due_onoff, due_period, due) = (”ON”, “입력값”, “입력값”);
     * where group_wallet_id = '현재 모임지갑'
     *
     * JPA : GroupWalletRepository.save(GroupWallet groupWallet);
     * */


    /**
     * ROWNUM 21
     * 내 모임지갑에 회비 납부 메시지 보내기
     *
     * 미완성????
     * */


    /**
     * ROWNUM 22
     * 내 모임지갑의 회비 규칙 삭제하기 (모임장만 가능)
     *
     * update group_wallet
     * set (due_onoff, due_period, due) = (”OFF”, null, null);
     * where group_wallet_id = '현재 모임지갑'
     *
     * JPA : GroupWalletRepository.save(GroupWallet groupWallet);
     * */

    /**
     * ROWNUM 4
     * 내가 모임장인 모임지갑 리스트
     */
    List<GroupWallet> findByMember(Member member);

}
