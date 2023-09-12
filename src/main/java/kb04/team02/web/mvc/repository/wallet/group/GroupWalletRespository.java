package kb04.team02.web.mvc.repository.wallet.group;

import kb04.team02.web.mvc.domain.member.Member;
import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupWalletRespository extends JpaRepository<GroupWallet, Long> {

    // duepayment = 회비납부내역

    /**
     * ROWNUM 12
     *
     * SQL
     *
     * (모임지갑 생성시 사용)
     * insert into group_wallet
     * (group_wallet_id, nickname, insert_date, balance, due_condition, due_accumulation, member_id, due_accumulation, due_onoff, due_period, due)
     * values
     * (wallet_seq_nextval, nickname, sysdate, 0, “모임장 세션값”, 0, “off”, null, null) ;
     *
     * JPA : GroupWallet.save(GroupWallet groupWallet);
     * */


    /**
     * ROWNUM 10
     * 모임지갑 메인화면
     * 나의 모임지갑 전부 검색
     * */
    List<GroupWallet> findAllByOrderByGroupWalletId(Long memberId);


    /**
     * ROWNUM 13
     * 모임지갑 상세화면
     * 선택한 나의 모임지갑의 모든 거래내역(외화내역, 이체내역, 환전내역)
     */
//    List<GroupWallet>
//    여러 종류의 거래내역을 오버라이딩해서 한 번에 불러오는 게 헷갈림다


    /**
     * ROWNUM 14
     * 모임지갑 삭제
     * 선택한 나의 모임지갑을 삭제한다. (모임장만 가능)
     * */
    GroupWallet deleteGroupWalletByGroupWalletId(Long groupWalletId);


    // ROWNUM 15번 : 나의 모임지갑에 새로운 모임원 초대링크 보내기 (모임장만 가능) = ? : 모르겠슴다


    // ROWNUM 16번 모임지갑 모임원 리스트 요청은 ParticipationRepository에 있음


    /**
     * ROWNUM 18
     * 내 모임지갑에서 선택한 모임원을 강퇴하기 (모임장만 가능)
     *
     * SQL
     *
     * update participation
     * set state = ‘참여안함’
     * where member_id = ‘선택한 모임원 id’
     *
     * JPA : ParticipationRepository.save(int memberId);
     * */

    /**
     * ROWNUM 19
     * 내 모임지갑의 회비 규칙 불러오기
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


    // ROWNUM 21 : 나의 모임지갑에 회비 납부 메시지를 보내기 = ? : 모르겠슴다


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
     * 내가 모임장인 모임지갑
     */
    List<GroupWallet> findByMember(Member member);
}
