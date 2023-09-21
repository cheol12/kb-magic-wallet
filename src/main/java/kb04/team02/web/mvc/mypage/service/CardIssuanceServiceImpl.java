package kb04.team02.web.mvc.mypage.service;

import kb04.team02.web.mvc.common.entity.WalletType;
import kb04.team02.web.mvc.member.entity.Member;
import kb04.team02.web.mvc.member.repository.MemberRepository;
import kb04.team02.web.mvc.mypage.entity.CardIssuance;
import kb04.team02.web.mvc.mypage.entity.CardState;
import kb04.team02.web.mvc.mypage.repository.CardIssuanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardIssuanceServiceImpl implements CardIssuanceService {

    private final MemberRepository memberRepository;
    private final CardIssuanceRepository cardIssuanceRepository;

    @Override
    public boolean isConnectToWallet(Long memberId, Long walletId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException("회원조회 실패"));
        CardIssuance cardIssuance = cardIssuanceRepository.findByMemberAndCardStateAndWalletId(member, CardState.OK, walletId);
        if (cardIssuance != null) {
            // 해당 지갑에 해당 인원의 카드가 연결되어있다
            return true;
        }
        return false;
    }

    @Override
    public void createCardConnection(Long memberId, Long walletId) {
        // 모임지갑에 연결

        Optional<Member> byId = memberRepository.findById(memberId);
        Member member = byId.get();

        CardIssuance cardIssuance = cardIssuanceRepository.findByMemberAndCardState(member, CardState.OK);
        cardIssuance.setWalletType(WalletType.GROUP_WALLET);
        cardIssuance.setWalletId(walletId);
    }

    @Override
    public void deleteCardConnection() {

    }

    @Override
    public boolean isConnectToPersonalWallet(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException("회원조회 실패"));
        CardIssuance cardIssuance = cardIssuanceRepository.findByMemberAndCardStateAndWalletId(member, CardState.OK, member.getPersonalWallet().getPersonalWalletId());
        if (cardIssuance != null) {
            // 해당 지갑에 해당 인원의 카드가 연결되어있다
            return true;
        }
        return false;
    }

    @Override
    public void createPersonalWalletCardConnection(Long memberId) {
        Optional<Member> byId = memberRepository.findById(memberId);
        Member member = byId.get();

        CardIssuance cardIssuance = cardIssuanceRepository.findByMemberAndCardState(member, CardState.OK);
        cardIssuance.setWalletType(WalletType.PERSONAL_WALLET);
        cardIssuance.setWalletId(member.getPersonalWallet().getPersonalWalletId());
    }
}
