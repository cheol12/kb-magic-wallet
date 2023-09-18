package kb04.team02.web.mvc.group.repository;

import kb04.team02.web.mvc.group.entity.GroupWallet;
import kb04.team02.web.mvc.group.entity.ParticipationState;
import kb04.team02.web.mvc.member.entity.Role;
import kb04.team02.web.mvc.group.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    /**
     * ROWNUM 16
     * 내 모임지갑의 모임원 리스트 불러오기
     *
     * select m.name, p.authority from member m
     * left outer join participation p
     * on m.member_id = p.member_id where p.group_wallet_id = “현재세션모임지갑”
     **/
    // -> join하는거 보류.
//    List<Participation> findByParticipationStateIsTrue(Long groupWalletId);

    List<Participation> findByGroupWalletAndParticipationState(GroupWallet groupWallet, ParticipationState state);

    /**
     * ROWNUM 17
     * 내 모임지갑에서 본인이 탈퇴하기
     * <p>
     * SQL
     * <p>
     * update participation
     * set state = ‘참여안함’
     * where member_id = ‘현재 나의 세션’
     * <p>
     * JPA : ParticipationRepository.save(int memberId);
     */

    /**
     * 내가 참여중/참여했던 모임지갑
     * @param memberId
     * @return
     */
    List<Participation> findByMemberId(Long memberId);


    /**
     * @author 김진형
     * 
     * 그룹원 참여 삭제
     */
    Participation findByGroupWalletAndMemberId(GroupWallet groupWallet, Long memberId);

    List<Participation> findByMemberIdAndParticipationState(Long memberId, ParticipationState state);
















    // 해당 모임 지갑의 모임장, 공동 모임장 회원 리스트 찾기
    List<Participation> findByGroupWalletAndRoleIn(GroupWallet groupWallet, Collection<Role> role);

    /**
     * @author 김철
     * 매개변수 memberId와 state가 동시에 일치하는 데이터 찾기
     * = 모임지갑 메인화면 : 나의 모임지갑 리스트 조회에 필요
     * */
    List<Participation> findParticipationByMemberIdAndParticipationStateEquals(Long memberId, ParticipationState state);

    /**
     * @author 김철
     * 매개변수 groupWalletId와 state가 동시에 일치하는 데이터 찾기
     * = 모임지갑 상세화면 = 모임지갑의 모임원 리스트 조회에 필요
     * */
    List<Participation> findParticipationByGroupWallet_GroupWalletIdAndParticipationState(Long groupWalletId, ParticipationState state);

    /**
     * @author 김철
     * */
    Participation findByMemberIdAndRoleAndGroupWallet_GroupWalletId(Long memberId, Role role, Long groupWalletId);
}
