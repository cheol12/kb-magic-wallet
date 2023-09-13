package kb04.team02.web.mvc.service.member;

import kb04.team02.web.mvc.domain.card.CardIssuance;
import kb04.team02.web.mvc.domain.card.CardState;
import kb04.team02.web.mvc.domain.member.Address;
import kb04.team02.web.mvc.domain.member.Member;
import kb04.team02.web.mvc.domain.member.Role;
import kb04.team02.web.mvc.domain.wallet.common.WalletType;
import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import kb04.team02.web.mvc.domain.wallet.group.Participation;
import kb04.team02.web.mvc.domain.wallet.group.ParticipationState;
import kb04.team02.web.mvc.domain.wallet.personal.PersonalWallet;
import kb04.team02.web.mvc.dto.LoginMemberDto;
import kb04.team02.web.mvc.dto.MemberLoginDto;
import kb04.team02.web.mvc.dto.MemberRegisterDto;
import kb04.team02.web.mvc.exception.LoginException;
import kb04.team02.web.mvc.exception.RegisterException;
import kb04.team02.web.mvc.repository.card.CardIssuanceRepository;
import kb04.team02.web.mvc.repository.member.MemberRepository;
import kb04.team02.web.mvc.repository.wallet.group.GroupWalletRespository;
import kb04.team02.web.mvc.repository.wallet.group.ParticipationRepository;
import kb04.team02.web.mvc.repository.wallet.personal.PersonalWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

import static kb04.team02.web.mvc.common.module.CardNumberIssuance.generateRandomCardNumber;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PersonalWalletRepository personalWalletRepository;
    private final GroupWalletRespository groupWalletRespository;
    private final ParticipationRepository participationRepository;
    private final CardIssuanceRepository cardIssuanceRepository;

    @Override
    @Transactional
    public void register(MemberRegisterDto memberRegisterDto) throws RegisterException {
        // 아이디 중복 체크
        memberRepository.findById(memberRegisterDto.getId()).orElseThrow(
                () -> new RegisterException("아이디는 중복될 수 없습니다.")
        );

        // 회원 가입
        Member member = Member.builder()
                .id(memberRegisterDto.getId())
                .password(memberRegisterDto.getPassword())
                .name(memberRegisterDto.getName())
                .address(new Address(memberRegisterDto.getCity(), memberRegisterDto.getStreet(), memberRegisterDto.getZipcode()))
                .phoneNumber(memberRegisterDto.getPhoneNumber())
                .email(memberRegisterDto.getEmail())
                .payPassword(memberRegisterDto.getPayPassword())
                .bankAccount(memberRegisterDto.getBankAccount())
                .build();

        Member saved = memberRepository.save(member);

        // 지갑 생성
        PersonalWallet personalWallet = PersonalWallet.builder()
                .member(saved)
                .build();

        PersonalWallet savedWallet = personalWalletRepository.save(personalWallet);

        // 카드 발급
        CardIssuance card = CardIssuance.builder()
                .cardNumber(generateRandomCardNumber())
                .cardState(CardState.OK)
                .walletId(savedWallet.getPersonalWalletId())
                .walletType(WalletType.PERSONAL_WALLET)
                .build();

        CardIssuance savedCard = cardIssuanceRepository.save(card);
    }

    @Override
    @Transactional
    public LoginMemberDto login(MemberLoginDto memberLoginDto) throws LoginException {
        // 아이디 비밀번호 확인
        Member member = memberRepository.findByIdAndPassword(memberLoginDto.getId(), memberLoginDto.getPassword())
                .orElseThrow(() -> new LoginException("아이디, 비밀번호를 확인해주세요."));

        // 로그인한 사용자 지갑들
        PersonalWallet personalWallet = personalWalletRepository.findByMember(member);
        List<GroupWallet> groupWalletList = groupWalletRespository.findByMember(member);
        List<Participation> participationList = participationRepository.findByMemberId(member.getMemberId());

        // 로그인한 사용자 정보 감싸서 보내기
        LoginMemberDto loggedIn = LoginMemberDto.builder()
                .memberId(member.getMemberId())
                .id(member.getId())
                .name(member.getName())
                .build();

        // 지갑 정보
        loggedIn.setPersonalWalletId(
                member.getPersonalWallet().getPersonalWalletId()
        );

        groupWalletList.forEach(wallet -> loggedIn.getGroupWalletIdList()
                .put(wallet.getGroupWalletId(), Role.CHAIRMAN));

        participationList.stream()
                .filter(wallet -> wallet.getParticipationState() == ParticipationState.PARTICIPATED)
                .forEach(wallet -> loggedIn.getGroupWalletIdList()
                        .put(wallet.getGroupWallet().getGroupWalletId(), wallet.getRole()));

        return loggedIn;
    }
}
