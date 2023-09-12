package kb04.team02.web.mvc.service.groupwallet;

import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import kb04.team02.web.mvc.domain.wallet.group.Participation;
import kb04.team02.web.mvc.dto.TransferDto;
import kb04.team02.web.mvc.dto.WalletDetailDto;
import kb04.team02.web.mvc.dto.WalletDto;
import kb04.team02.web.mvc.dto.WalletHistoryDto;
import kb04.team02.web.mvc.repository.wallet.group.GroupWalletRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupWalletServiceImpl implements GroupWalletService {

    private final GroupWalletRespository groupWalletRep;

    @Override
    public List<WalletDto> selectAllMyGroupWallet(Long memberId) {
        return groupWalletRep.findAllByMemberOrderByGroupWalletId(memberId);
    }

    @Override
    public int createGroupWallet(WalletDto walletDto) {
        return 0;
    }

    @Override
    public List<WalletHistoryDto> getGroupWalletDetail(Long groupWalletId) {

        return groupWalletRep.findAllByGroupWalletIdOrderByInsertDate(groupWalletId);
    }

    @Override
    public int deleteGroupWallet(Long groupWalletId) {
        // 모임장이 모임지갑을 삭제하는 것.
        // 모임장인지 확인하는 것은 어디서할지?

        int result = groupWalletRep.deleteGroupWalletByGroupWalletId(groupWalletId);
        return result;
    }

    @Override
    public String inviteMember(Long groupWalletId) {

        // 미완

        return null;
    }

    @Override
    public int groupWalletMemberOut(Long groupWalletId, Long memberId) {

        // 모임원 내보내기 or 탈퇴

        int result = groupWalletRep.deleteByGroupWalletIdAndMember(groupWalletId, memberId);
        return result;
    }

    @Override
    public GroupWallet getGroupWalletDueRule(Long groupWalletId, int dueDate, Long due) {
        GroupWallet groupWallet = groupWalletRep.findByGroupWalletId(groupWalletId);

        return groupWallet;
    }

    @Override
    public GroupWallet setGroupWalletDueRule(Long groupWalletId, GroupWallet groupWallet) {
        // 모임 회비 규칙 생성은 모임장만!
        // 모임장 권한 확인은 어디서 할지 생각

        GroupWallet groupWallet01 = groupWalletRep.findByGroupWalletId(groupWalletId);
        groupWallet01.setDueCondition(true);
        groupWallet01.setDueDate(groupWallet.getDueDate());
        groupWallet01.setDue(groupWallet.getDue());
        return groupWallet01;
    }

    @Override
    public GroupWallet deleteGroupWalletDueRule(Long groupWalletId) {
        // 모임지갑 회비 규칙 삭제하기

        GroupWallet groupWallet = groupWalletRep.findByGroupWalletId(groupWalletId);
        groupWallet.setDueCondition(false);
        groupWallet.setDueDate(0);
        groupWallet.setDue(null);
        return groupWallet;
    }


    @Override
    public int groupWalletWithdraw(TransferDto transferDto) {


        return 0;
    }

    @Override
    public int settle(TransferDto transferDto) {
        return 0;
    }

    @Override
    public int groupWalletDeposit(TransferDto transferDto) {
        return 0;
    }
}
