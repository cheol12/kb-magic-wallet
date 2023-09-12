package kb04.team02.web.mvc.service.groupwallet;

import kb04.team02.web.mvc.domain.wallet.common.Transfer;
import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import kb04.team02.web.mvc.domain.wallet.group.GroupWalletTransfer;
import kb04.team02.web.mvc.dto.TransferDto;
import kb04.team02.web.mvc.dto.WalletDetailDto;
import kb04.team02.web.mvc.dto.WalletDto;

import java.util.List;

public interface GroupWalletService {

    /**
     * 모임지갑 메인 페이지
     * API 명세서 ROWNUM:10
     * 나의 모든 모임지갑 목록 불러오기
     *
     * @param memberId 세션에서 불러옴
     * @return List<WalletDto>
     * <p>
     * * WalletDto
     * *  - wallet seq
     * *  - 권한
     * *  - 지갑 이름
     * *  - 지갑 구분
     */
    List<WalletDto> selectAllMyGroupWallet(int memberId);

    /**
     * 모임지갑 생성하기
     * API 명세서 ROWNUM:12
     *
     * @param walletDto 모임지갑 객체를 폼에 입력해서 생성
     * @return int 1: OK 0:FAIL
     */
    int createGroupWallet(WalletDto walletDto);

    /**
     * 개별 모임지갑 상세 보기
     * API 명세서 ROWNUM:13
     *
     * @param groupWalletId 조회할 모임지갑 id
     * @return GroupWallet 객체 보여주기
     */
    WalletDetailDto getGroupWalletDetail(Long groupWalletId);

    /**
     * 모임지갑 삭제 요청
     * API 명세서 ROWNUM:14
     *
     * @param groupWalletId 삭제할 모임지갑 id
     * @return void
     */
    int deleteGroupWallet(Long groupWalletId);

    /**
     * 모임지갑 초대 링크 생성 요청
     * API 명세서 ROWNUM:15
     *
     * @param groupWalletId 초대링크를 생성할 모임지갑 id
     * @return void
     */
    String inviteMember(Long groupWalletId);

    /**
     * 모임지갑 자진 탈퇴 요청
     * API 명세서 ROWNUM:17
     *
     * @param groupWalletId,memberId 자진 탈퇴 요청 모임지갑 id, 탈퇴할 사람의 식별번호
     * @return GroupWallet
     */
    int groupWalletMemberOut(Long groupWalletId, Long memberId);

    /**
     * 모임지갑 꺼내기 요청
     * API 명세서 ROWNUM:30
     * <p>
     * 모임지갑의 잔액 update + 모임지갑 이체내역 insert + 개인지갑의 잔액 update + 개인지갑 이체내역 insert
     * => 트랜잭션 처리
     *
     * @param transferDto 이체내역을 insert할 transferDto객체.
     *                    Q) 개인과 모임 이체내역 둘 다 하나의 객체로 불러오는 것은 어디에?
     *                    <p>
     *                    TransferDto
     *                    - 통화
     *                    - 금액
     *                    - from id(group wallet id 자동)
     *                    - to id(내내 개인지갑 자동)
     */
    int groupWalletWithdraw(TransferDto transferDto);

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
     * @param transferDto 이체내역을 insert할 transferDto객체
     *                    TransferDto
     *                    - 통화
     *                    - 금액
     *                    - from id(group wallet id 자동)
     *                    - to id(내내 개인지갑 자동)
     */
    int settle(TransferDto transferDto);

    /**
     * 모임지갑 채우기
     * API 명세서 ROWNUM:34
     * <p>
     * 모임지갑 잔액 update + 모임지갑 이체내역 insert + 개인지갑 잔액 update + 개인지갑 이체내역 insert
     * => 트랜잭션 처리
     *
     * @param transferDto 이체내역을 insert할 transferDto객체
     *                    TransferDto
     *                    - 통화
     *                    - 금액
     *                    - from id(group wallet id 자동)
     *                    - to id(내내 개인지갑 자동)
     */
    int groupWalletDeposit(TransferDto transferDto);
}
