package kb04.team02.web.mvc.service.groupwallet;

import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import kb04.team02.web.mvc.domain.wallet.group.Participation;
import kb04.team02.web.mvc.dto.*;
import kb04.team02.web.mvc.repository.member.MemberRepository;
import kb04.team02.web.mvc.repository.wallet.group.GroupWalletRespository;
import kb04.team02.web.mvc.repository.wallet.group.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupWalletTabServiceImpl implements GroupWalletTabService {

    private final GroupWalletRespository groupWalletRespository;
    private final ParticipationRepository participationRepository;
    @Override

    public List<GroupMemberDto> getMembersByGroupId(Long id, Pageable pageable) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).get();
        List<Participation> list = participationRepository.findByParticipationStateIsTrue(groupWallet.getGroupWalletId());
        for (Participation participation : list) {
            participation.
        }
//        return GroupMemberRepository.findall();
    }


    @Override
    public boolean deleteMember(Long id, Long member) {
        return false;
    }

    @Override
    public boolean GrantMemberAuth(Long id, Long member) {
        return false;
    }

    @Override
    public boolean RevokeMemberAuth(Long id, Long member) {
        return false;
    }

    @Override
    public RuleDto getRuleById(Long id) {
        return null;
    }

    @Override
    public boolean createRule(Long id, RuleDto ruleDto) {
        return false;
    }

    @Override
    public boolean alertMember(Long id, Long member) {
        return false;
    }

    @Override
    public boolean deleteRule(Long id) {
        return false;
    }

    @Override
    public SavingDto getSavingById(Long id) {
        return null;
    }

    @Override
    public boolean cancelSaving(Long id) {
        return false;
    }

    @Override
    public List<CardIssuanceDto> getCard(Long id) {
        return null;
    }

    @Override
    public boolean linkCard(Long id) {
        return false;
    }

    @Override
    public Page<WalletHistoryDto> getHistoryByGroupId(Long id, Pageable page) {
        return null;
    }

    @Override
    public WalletHistoryDto getHistory(Long id) {
        return null;
    }
}
