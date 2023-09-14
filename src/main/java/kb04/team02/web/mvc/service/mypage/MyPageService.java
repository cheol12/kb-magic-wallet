package kb04.team02.web.mvc.service.mypage;

import kb04.team02.web.mvc.dto.CardNumberDto;
import kb04.team02.web.mvc.dto.LoginMemberDto;

public interface MyPageService {

    /**
     * 카드 새로 신청
     * <p>
     * 0) 멤버 존재하는지 memberId로 확인
     * 1) 멤버의 카드가 모두 정지되어 있는지 확인
     * 2) 안되었으면 정지 update
     * 3) 새 카드 번호 생성하고 카드 발급 내역에 insert
     */
    void createCard(LoginMemberDto loginMember);

    /**
     * 카드 정지
     * <p>
     * 0) 멤버 존재하는지 memberId로 확인
     * 1) 멤버에 사용 상태 카드 하나만 있는지 확인
     * 2) 카드 정지
     */
    void invalidateCard(LoginMemberDto loginMember);

    /**
     * 카드 일시 정지
     * <p>
     * 0) 멤버 존재하는지 memberId로 확인
     * 1) 멤버에 사용 상태 카드 하나만 있는지 확인
     * 2) 카드 일시 정지
     */
    void pauseCard(LoginMemberDto loginMember);

    /**
     * 은행 계좌 변경
     * <p>
     * 0) 멤버 존재하는지 memberId로 확인
     * 1) 은행 계좌 연결 update
     */
    void linkAccount(LoginMemberDto loginMember, String newAccount);

    /**
     * 카드 다시 시작
     */
    void resumeCard(LoginMemberDto loginMember);

    /**
     * 카드번호 요청
     */
    CardNumberDto getCardNumber(LoginMemberDto loginMember);

    /**
     * 계좌번호 요청
     */
    String getBankAccount(LoginMemberDto loginMember);
}
