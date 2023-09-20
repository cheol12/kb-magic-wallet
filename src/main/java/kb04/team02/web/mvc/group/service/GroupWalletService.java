package kb04.team02.web.mvc.group.service;

import kb04.team02.web.mvc.common.dto.LoginMemberDto;
import kb04.team02.web.mvc.common.dto.WalletDetailDto;
import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.group.dto.*;
import kb04.team02.web.mvc.group.entity.Participation;
import kb04.team02.web.mvc.member.entity.Member;
import kb04.team02.web.mvc.group.entity.GroupWallet;
import kb04.team02.web.mvc.group.exception.NotEnoughBalanceException;
import kb04.team02.web.mvc.group.exception.WalletDeleteException;

import java.util.List;

public interface GroupWalletService {

    /**
     * 모임지갑 메인 페이지
     * API 명세서 ROWNUM:10
     * 나의 모든 모임지갑 목록 불러오기
     *
     * @param loginMemberDto 세션에서 불러옴
     * @return List<WalletDto>
     * <p>
     * * WalletDto
     * *  - wallet seq
     * *  - 권한
     * *  - 지갑 이름
     * *  - 지갑 구분
     */
    List<GroupWallet> selectAllMyGroupWallet(LoginMemberDto loginMemberDto);


    /**
     * 모임지갑 생성하기
     * API 명세서 ROWNUM:12
     *
     * @param memberId,nickname 모임지갑 객체를 폼에 입력해서 생성
     * @return int 1: OK 0:FAIL
     */
    int createGroupWallet(Long memberId, String nickname);


    /**
     * 개별 모임지갑 상세 보기
     * API 명세서 ROWNUM:13
     *
     * @param groupWalletId 조회할 모임지갑 id
     * @return WalletHistoryDto = 해당 GroupWallet의 모든 내역
     */
    WalletDetailDto getGroupWalletDetail(Long groupWalletId);


    /**
     * 모임지갑 삭제 요청
     * API 명세서 ROWNUM:14
     *
     * @param groupWalletId 삭제할 모임지갑 id
     * @return int
     */
    int deleteGroupWallet(Long groupWalletId) throws WalletDeleteException;


    /**
     * 모임지갑 초대 링크 생성 요청
     * API 명세서 ROWNUM:15
     *
     * @param groupWalletId 초대링크를 생성할 모임지갑 id
     * @return String
     */
    int inviteMember(String phone, Long groupWalletId);


    /**
     * 모임지갑 탈퇴 요청
     * API 명세서 ROWNUM:17
     *
     * @param groupWalletId,memberId 탈퇴 요청 모임지갑 id, 탈퇴할 사람의 식별번호
     * @return int
     */
    int groupWalletMemberOut(Long groupWalletId, Long memberId);


    /**
     * 모임지갑 회비 규칙 설정하기
     * API 명세서 ROWNUM:19
     *
     * @param groupWalletId,dueDate,due
     * @return GroupWallet
     * */
    GroupWallet setGroupWalletDueRule(Long groupWalletId, int dueDate, Long due);


    /**
     * 모임지갑 회비 규칙 불러오기
     * API 명세서 ROWNUM:20
     *
     * @param groupWalletId
     * @return
     * */
    RuleDto getGroupWalletDueRule(Long groupWalletId);


    /**
     * 모임지갑 회비 규칙 삭제하기
     * API 명세서 ROWNUM:22
     *
     * @param groupWalletId
     * @return GroupWallet
     * */
    GroupWallet deleteGroupWalletDueRule(Long groupWalletId);


    /**
     * 모임지갑 꺼내기 요청
     * API 명세서 ROWNUM:30
     * <p>
     * 모임지갑의 잔액 update + 모임지갑 이체내역 insert + 개인지갑의 잔액 update + 개인지갑 이체내역 insert
     * => 트랜잭션 처리
     *
     * @param withDrawDto 이체내역을 insert할 transferDto객체.
     *                    Q) 개인과 모임 이체내역 둘 다 하나의 객체로 불러오는 것은 어디에?
     *                    <p>
     *                    TransferDto
     *                    - 통화
     *                    - 금액
     *                    - from id(group wallet id 자동)
     *                    - to id(내내 개인지갑 자동)
     */
    int groupWalletWithdraw(WithDrawDto withDrawDto) throws NotEnoughBalanceException;


    /**
     * 모임지갑 정산하기
     * API 명세서 ROWNUM:32
     * <p>
     * 모임지갑 잔액 update + 모임지갑 이체내역 insert + 개인지갑 잔액 update + 개인지갑 이체내역 insert
     * => 트랜잭션 처리
     * <p>
     * 주의) 정산하기 전에 모임지갑의 잔액을 자바 객체로 저장하는 것이 좋다?
     * 모임지갑 잔액은 0원이 된다. (나누어 떨어지지 않으면 어떻게?)
     * 해당 모임 내에 있는 모든 멤버의 개인지갑 잔액을 update 하고, 이체내역 데이터를 insert 한다.
     *
     * @param settleDto 이체내역을 insert할 transferDto객체
     *                    TransferDto
     *                    - 통화
     *                    - 금액
     *                    - from id(group wallet id 자동)
     *                    - to id(내내 개인지갑 자동)
     */
    int settle(SettleDto settleDto) throws NotEnoughBalanceException;


    /**
     * 모임지갑 채우기
     * API 명세서 ROWNUM:34
     * <p>
     * 모임지갑 잔액 update + 모임지갑 이체내역 insert + 개인지갑 잔액 update + 개인지갑 이체내역 insert
     * => 트랜잭션 처리
     *
     * @param depositDto 이체내역을 insert할 transferDto객체
     *                    TransferDto
     *                    - 통화
     *                    - 금액
     *                    - from id(group wallet id 자동)
     *                    - to id(내내 개인지갑 자동)
     */
    int groupWalletDeposit(DepositDto depositDto) throws NotEnoughBalanceException;

    /**
     * 정산 시 모임지갑에서 개인지갑으로의 돈 전송
     *
     * @param groupWallet 모임지갑 ID
     * @param member 개인지갑 소유주
     * @param amount 양
     * @param currencyCode 통화코드
     * @return
     */
    int groupWalletToPersonalWallet(GroupWallet groupWallet, Member member, Long amount, CurrencyCode currencyCode) throws NotEnoughBalanceException;

    /**
     * @author 김철
     * 내 모임지갑의 정보
     * 회비 규칙 불러오는 데에 사용
     * */
    GroupWallet getGroupWallet(Long groupWalletId);

    /**
     * @author 김철
     * 내 모임지갑의 모임원 리스트 조회
     */
    List<GroupMemberDto> getGroupMemberList(Long groupWalletId);

    /**
     * @author 김철
     * 나의 모임지갑 권한 확인
     */
    boolean groupMemberIsChairman(Long groupWalletId, Long memberId);

    /**
     * @author 김철
     * 나의 모임지갑 적금 조회
     * */
    InstallmentDto getInstallmentDtoSaving(GroupWallet groupWallet);

    /**
     * @author 김철
     * 나의 모임지갑에 연결된 카드 리스트 조회
     * */
    List<CardIssuanceDto> getCardIssuanceDto(Long walletId);

    /**
     * 내가 모임장 혹은 공동모임장인 모임지갑을 가지고 있는지 여부
     */
    boolean isChairmanGroupWalletList(LoginMemberDto loginMemberDto);

    /**
     * 내가 모임장 혹은 공동모임장인 모임지갑 조회
     */
    List<GroupWallet> getChairmanGroupWalletList(LoginMemberDto loginMemberDto);

    /**
     * @author 김철
     *
     * */
    int countGroupWalletMember(Long groupWalletId);

    /**
     * @author 김철
     * 내가 초대된 모임지갑 리스트를 보기
     * */
    List<Participation> getGroupListInvitedMe(Long memberId);

    /**
     * @author 김철
     * 모임지갑 초대에 응답하기
     * */
    int invitedResponse(Long groupWalletId, Long memberId, boolean confirm);

    /**
     * 회비 내기
     */
    void payDue(Long id, Long memberId);
}
