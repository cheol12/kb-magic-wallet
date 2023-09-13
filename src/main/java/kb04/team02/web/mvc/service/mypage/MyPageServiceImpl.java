package kb04.team02.web.mvc.service.mypage;

import kb04.team02.web.mvc.domain.card.CardIssuance;
import kb04.team02.web.mvc.domain.card.CardState;
import kb04.team02.web.mvc.domain.member.Member;
import kb04.team02.web.mvc.domain.wallet.common.WalletType;
import kb04.team02.web.mvc.domain.wallet.personal.PersonalWallet;
import kb04.team02.web.mvc.dto.CardNumberDto;
import kb04.team02.web.mvc.dto.LoginMemberDto;
import kb04.team02.web.mvc.repository.card.CardIssuanceRepository;
import kb04.team02.web.mvc.repository.member.MemberRepository;
import kb04.team02.web.mvc.repository.wallet.personal.PersonalWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

import static kb04.team02.web.mvc.service.common.module.CardNumberIssuance.generateRandomCardNumber;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final MemberRepository memberRepository;
    private final CardIssuanceRepository cardIssuanceRepository;
    private final PersonalWalletRepository personalWalletRepository;

    @Override
    public void createCard(LoginMemberDto loginMember) {
        // 멤버 존재 확인
        Member member = memberRepository.findById(loginMember.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("멤버 조회 실패"));

        // 카드 찾아서 정지
        cardIssuanceRepository.findFirstByMemberOrderByInsertDateDesc(member)
                .orElseThrow(() -> new NoSuchElementException("카드 조회 실패"))
                .changeStateStop();

        // 개인 지갑 존재 확인
        PersonalWallet personalWallet = personalWalletRepository.findById(loginMember.getPersonalWalletId())
                .orElseThrow(NoSuchElementException::new);

        if (personalWallet == null) {
            throw new NoSuchElementException();
        }

        // 카드 생성
        CardIssuance card = CardIssuance.builder()
                .cardNumber(generateRandomCardNumber())
                .cardState(CardState.OK)
                .walletId(personalWallet.getPersonalWalletId())
                .walletType(WalletType.PERSONAL_WALLET)
                .member(member)
                .build();

        cardIssuanceRepository.save(card);
    }

    @Override
    public void invalidateCard(LoginMemberDto loginMember) {
        Member member = memberRepository.findById(loginMember.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("멤버 조회 실패"));

        cardIssuanceRepository.findFirstByMemberOrderByInsertDateDesc(member)
                .orElseThrow(() -> new NoSuchElementException("카드 조회 실패"))
                .changeStateStop();
    }

    @Override
    public void pauseCard(LoginMemberDto loginMember) {
        Member member = memberRepository.findById(loginMember.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("멤버 조회 실패"));

        cardIssuanceRepository.findFirstByMemberOrderByInsertDateDesc(member)
                .orElseThrow(() -> new NoSuchElementException("카드 조회 실패"))
                .changeStateTemporalStop();
    }

    @Override
    public void linkAccount(LoginMemberDto loginMember, String account) {
        Member member = memberRepository.findById(loginMember.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("멤버 조회 실패"));

        member.changeAccount(account);
    }

    @Override
    public void resumeCard(LoginMemberDto loginMember) {
        Member member = memberRepository.findById(loginMember.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("멤버 조회 실패"));

        cardIssuanceRepository.findFirstByMemberOrderByInsertDateDesc(member)
                .orElseThrow(() -> new NoSuchElementException("카드 조회 실패"))
                .changeStateOk();
    }

    @Override
    public CardNumberDto getCardNumber(LoginMemberDto loginMember) {
        Member member = memberRepository.findById(loginMember.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("멤버 조회 실패"));

        CardIssuance card = cardIssuanceRepository.findFirstByMemberOrderByInsertDateDesc(member)
                .orElseThrow(() -> new NoSuchElementException("카드 조회 실패"));

        return CardNumberDto.builder()
                .cardNumber(card.getCardNumber())
                .cardState(card.getCardState())
                .build();
    }

    @Override
    public String getBankAccount(LoginMemberDto loginMember) {
        Member member = memberRepository.findById(loginMember.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("멤버 조회 실패"));

        return member.getBankAccount();
    }
}
