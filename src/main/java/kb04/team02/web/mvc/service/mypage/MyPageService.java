package kb04.team02.web.mvc.service.mypage;

import kb04.team02.web.mvc.dto.LoginMemberDto;
import org.springframework.stereotype.Service;

public interface MyPageService {

    /**
     * 카드신청
     * <p>
     * 0) 멤버 존재하는지 memberId로 확인
     * 1) 멤버의 카드가 모두 정지되어 있는지 확인
     * 2) 안되었으면 정지 update
     * 3) 새 카드 번호 생성하고 카드 발급 내역에 insert
     *
     * @return 새 카드 번호 dto에 넣어서 반환
     */
    LoginMemberDto createCard(LoginMemberDto loginMember);

    /**
     * 카드 정지
     * <p>
     * 0) 멤버 존재하는지 memberId로 확인
     * 1) 멤버에 사용 상태 카드 하나만 있는지 확인
     * 2) 카드 정지
     *
     * @return dto에 카드번호 지우고 반환
     */
    LoginMemberDto invalidateCard(LoginMemberDto loginMember);

    /**
     * 카드 일시 정지
     * <p>
     * 0) 멤버 존재하는지 memberId로 확인
     * 1) 멤버에 사용 상태 카드 하나만 있는지 확인
     * 2) 카드 일시 정지
     *
     * @return dto에 카드번호 null 하고 반환
     */
    LoginMemberDto pauseCard(LoginMemberDto loginMember);

    /**
     * 은행 계좌 연결
     * <p>
     * 0) 멤버 존재하는지 memberId로 확인
     * 1) 은행 계좌 연결 update
     *
     * @return dto 에 담아서 반환
     */
    LoginMemberDto linkAccount(LoginMemberDto loginMember);
}
