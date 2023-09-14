package kb04.team02.web.mvc.service.groupwallet;

import kb04.team02.web.mvc.domain.common.CurrencyCode;
import kb04.team02.web.mvc.domain.member.Address;
import kb04.team02.web.mvc.domain.member.Member;
import kb04.team02.web.mvc.domain.member.Role;
import kb04.team02.web.mvc.domain.wallet.common.*;
import kb04.team02.web.mvc.domain.wallet.group.*;
import kb04.team02.web.mvc.domain.wallet.personal.PersonalWallet;
import kb04.team02.web.mvc.domain.wallet.personal.PersonalWalletForeignCurrencyBalance;
import kb04.team02.web.mvc.dto.*;
import kb04.team02.web.mvc.exception.InsertException;
import kb04.team02.web.mvc.exception.NotEnoughBalanceException;
import kb04.team02.web.mvc.exception.WalletDeleteException;
import kb04.team02.web.mvc.repository.card.CardIssuanceRepository;
import kb04.team02.web.mvc.repository.member.MemberRepository;
import kb04.team02.web.mvc.repository.saving.InstallmentSavingRepository;
import kb04.team02.web.mvc.repository.saving.SavingRepository;
import kb04.team02.web.mvc.repository.wallet.group.*;
import kb04.team02.web.mvc.repository.wallet.personal.PersonalWalletForeignCurrencyBalanceRepository;
import kb04.team02.web.mvc.repository.wallet.personal.PersonalWalletRepository;
import kb04.team02.web.mvc.repository.wallet.personal.PersonalWalletTransferRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
public class GroupWalletServiceImplTest {

    @Autowired
    private GroupWalletService service;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SavingRepository savingRepository;
    @Autowired
    private GroupWalletRespository groupWalletRespository;
    @Autowired
    private ParticipationRepository participationRepository;
    @Autowired
    private InstallmentSavingRepository installmentSavingRepository;
    @Autowired
    private CardIssuanceRepository cardIssuanceRepository;
    @Autowired
    private GroupWalletTransferRepository transferRepository;
    @Autowired
    private GroupWalletExchangeRepository exchangeRepository;
    @Autowired
    private GroupWalletPaymentRepository paymentRepository;
    @Autowired
    private GroupWalletForeignCurrencyBalanceRepository groupForeignCurrencyRepository;
    @Autowired
    private PersonalWalletForeignCurrencyBalanceRepository personalForeignCurrencyBalanceRepository;
    @Autowired
    private PersonalWalletRepository personalWalletRepository;
    @Autowired
    private PersonalWalletTransferRepository personalWalletTransferRepository;

    @AfterEach
    public void afterEach() {
        participationRepository.deleteAll();
        installmentSavingRepository.deleteAll();
        savingRepository.deleteAll();
        cardIssuanceRepository.deleteAll();
        transferRepository.deleteAll();
        exchangeRepository.deleteAll();
        paymentRepository.deleteAll();
        groupForeignCurrencyRepository.deleteAll();
        personalForeignCurrencyBalanceRepository.deleteAll();
        personalWalletTransferRepository.deleteAll();
        personalWalletRepository.deleteAll();
        groupWalletRespository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("selectAllMyGroupWallet")
    void selectAllMyGroupWallet() {

        // given
        Address address = new Address("서울시", "국회대로 54길 10", "07246");

        //1.
        Member member = Member.builder()
                .address(address)
                .email("cheol02@naver.com")
                .bankAccount("111-111-1111-02")
                .name("이이잉")
                .payPassword("123123")
                .id("cheol02")
                .password("123123")
                .phoneNumber("010-1111-1102").build();
        Member save = memberRepository.save(member);

        String nickname = "내 모";

        GroupWallet groupWallet;

        groupWallet = GroupWallet.builder()
                .dueCondition(false)
                .member(member)
                .nickname(nickname)
                .build();  //new GroupWallet();
        groupWalletRespository.save(groupWallet);

        nickname = "내 내";
        groupWallet = GroupWallet.builder()
                .dueCondition(false)
                .member(member)
                .nickname(nickname)
                .build();
        groupWalletRespository.save(groupWallet);

        // when
        List<GroupWallet> list = service.selectAllMyGroupWallet(member);

        // then
        assertThat(list.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("createGroupWallet")
    void createGroupWallet() {
        Address address = new Address("서울시", "국회대로 54길 10", "07246");
        Member member1 = Member.builder()
                .address(address)
                .email("@naver1.com")
                .bankAccount("111-111-1111-11")
                .name("김진형")
                .payPassword("123123")
                .id("qweqwe1")
                .password("1231123")
                .phoneNumber("010-1111-1111").build();
        memberRepository.save(member1);

        String nickname = "이아";

        int result = service.createGroupWallet(member1, nickname);

        List<GroupWallet> list = groupWalletRespository.findAllByMemberOrderByGroupWalletId(member1);
        // participation 테이블에도 모임장 데이터를 추가해야하지 않나

        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    void getGroupWalletDetail() throws InterruptedException {
        Address address = new Address("서울시", "국회대로 54길 10", "07246");
        Member member1 = Member.builder()
                .address(address)
                .email("@naver1.com")
                .bankAccount("111-111-1111-11")
                .name("김진형")
                .payPassword("123123")
                .id("qweqwe1")
                .password("1231123")
                .phoneNumber("010-1111-1111").build();
        memberRepository.save(member1);

        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
                .nickname("alias")
                .dueCondition(true)
                .member(member1)
                .balance(0L)
                .build());

        transferRepository.save(GroupWalletTransfer.builder()
                .groupWallet(wallet)
                .transferType(TransferType.DEPOSIT)
                .fromType(TargetType.PERSONAL_WALLET)
                .toType(TargetType.GROUP_WALLET)
                .src("출발지")
                .dest("목적지")
                .amount(10000L)
                .afterBalance(990000L)
                .currencyCode(CurrencyCode.KRW)
                .build());

        transferRepository.flush();

        Thread.sleep(5000);
        exchangeRepository.save(GroupWalletExchange.builder()
                .sellCurrencyCode(CurrencyCode.USD)
                .sellAmount(10L)
                .afterSellBalance(990L)
                .buyCurrencyCode(CurrencyCode.KRW)
                .buyAmount(13000L)
                .afterBuyBalance(13000L)
                .exchangeRate(1300.0)
                .groupWallet(wallet)
                .build());

        exchangeRepository.flush();

        Thread.sleep(5000);

        paymentRepository.save(GroupWalletPayment.builder()
                .currencyCode(CurrencyCode.KRW)
                .paymentType(PaymentType.OK)
                .paymentCategory(PaymentCategoryType.ENTERTAINMENT)
                .amount(110000L)
                .afterPayBalance(0L)
                .groupWallet(wallet)
                .build());

        groupForeignCurrencyRepository.save(GroupWalletForeignCurrencyBalance.builder()
                .groupWallet(wallet)
                .currencyCode(CurrencyCode.USD)
                .balance(10000L)
                .build());

        WalletDetailDto groupWalletDetail = service.getGroupWalletDetail(wallet.getGroupWalletId());

        System.out.println("groupWalletDetail.getBalance().get(\"USD\") = " + groupWalletDetail.getBalance().get("USD"));

        assertThat(groupWalletDetail.getList().size()).isEqualTo(3);
        assertThat(groupWalletDetail.getBalance().get("USD")).isEqualTo(10000L);
    }

    @Test
    void deleteGroupWallet() throws WalletDeleteException {
        Address address = new Address("서울시", "국회대로 54길 10", "07246");
        Member member1 = Member.builder()
                .address(address)
                .email("@naver1.com")
                .bankAccount("111-111-1111-11")
                .name("김진형")
                .payPassword("123123")
                .id("qweqwe1")
                .password("1231123")
                .phoneNumber("010-1111-1111").build();
        memberRepository.save(member1);

        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
                .nickname("alias")
                .dueCondition(true)
                .member(member1)
                .balance(0L)
                .build());

        participationRepository.save(Participation.builder()
                .groupWallet(wallet)
                .role(Role.CHAIRMAN)
                .memberId(member1.getMemberId())
                .participationState(ParticipationState.PARTICIPATED)
                .build()
        );

        int result = service.deleteGroupWallet(wallet.getGroupWalletId());
        assertThat(result).isEqualTo(1);
    }

    @Test
    void inviteMember() {
        Address address = new Address("서울시", "국회대로 54길 10", "07246");
        Member member1 = Member.builder()
                .address(address)
                .email("@naver1.com")
                .bankAccount("111-111-1111-11")
                .name("김진형")
                .payPassword("123123")
                .id("qweqwe1")
                .password("1231123")
                .phoneNumber("010-1111-1111").build();
        memberRepository.save(member1);

        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
                .nickname("alias")
                .dueCondition(true)
                .member(member1)
                .balance(0L)
                .build());

        String s = service.inviteMember(wallet.getGroupWalletId());
        System.out.println("s = " + s);

    }

    @Test
    void groupWalletMemberOut() {
        Address address = new Address("서울시", "국회대로 54길 10", "07246");
        Member member1 = Member.builder()
                .address(address)
                .email("@naver1.com")
                .bankAccount("111-111-1111-11")
                .name("김진형")
                .payPassword("123123")
                .id("qweqwe1")
                .password("1231123")
                .phoneNumber("010-1111-1111").build();
        memberRepository.save(member1);

        Member member2 = Member.builder()
                .address(address)
                .email("@naver2.com")
                .bankAccount("222-21212-1111-11")
                .name("김진형")
                .payPassword("123123")
                .id("qweqwe2")
                .password("1231123")
                .phoneNumber("010-2222-2222").build();
        memberRepository.save(member2);

        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
                .nickname("alias")
                .dueCondition(true)
                .member(member1)
                .balance(0L)
                .build());

        participationRepository.save(Participation.builder()
                .groupWallet(wallet)
                .role(Role.CHAIRMAN)
                .memberId(member1.getMemberId())
                .participationState(ParticipationState.PARTICIPATED)
                .build()
        );
        participationRepository.save(Participation.builder()
                .groupWallet(wallet)
                .role(Role.GENERAL)
                .memberId(member2.getMemberId())
                .participationState(ParticipationState.PARTICIPATED)
                .build()
        );
        List<Participation> participations = participationRepository.findAll();
        assertThat(participations.size()).isEqualTo(2);
        service.groupWalletMemberOut(wallet.getGroupWalletId(), member2.getMemberId());
        participations = participationRepository.findAll();
        assertThat(participations.size()).isEqualTo(1);
    }

    @Test
    void getAndSetGroupWalletDueRule() {
        Address address = new Address("서울시", "국회대로 54길 10", "07246");
        Member member1 = Member.builder()
                .address(address)
                .email("@naver1.com")
                .bankAccount("111-111-1111-11")
                .name("김진형")
                .payPassword("123123")
                .id("qweqwe1")
                .password("1231123")
                .phoneNumber("010-1111-1111").build();
        memberRepository.save(member1);

        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
                .nickname("alias")
                .dueCondition(false)
                .member(member1)
                .balance(0L)
                .build());

        assertThat(wallet.isDueCondition()).isEqualTo(false);
        GroupWallet groupWallet = service.setGroupWalletDueRule(wallet.getGroupWalletId(), 10, 10000L);
        assertThat(wallet.isDueCondition()).isEqualTo(true);
        assertThat(wallet.getDueDate()).isEqualTo(10);
        assertThat(wallet.getDue()).isEqualTo(10000L);
        RuleDto ruleDto = service.getGroupWalletDueRule(wallet.getGroupWalletId());
        assertThat(ruleDto.getDueDate()).isEqualTo(10);
        assertThat(ruleDto.getDuePrice()).isEqualTo(10000L);
    }

    @Test
    void deleteGroupWalletDueRule() {
        Address address = new Address("서울시", "국회대로 54길 10", "07246");
        Member member1 = Member.builder()
                .address(address)
                .email("@naver1.com")
                .bankAccount("111-111-1111-11")
                .name("김진형")
                .payPassword("123123")
                .id("qweqwe1")
                .password("1231123")
                .phoneNumber("010-1111-1111").build();
        memberRepository.save(member1);

        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
                .nickname("alias")
                .dueCondition(false)
                .member(member1)
                .balance(0L)
                .build());

        assertThat(wallet.isDueCondition()).isEqualTo(false);
        GroupWallet groupWallet = service.setGroupWalletDueRule(wallet.getGroupWalletId(), 10, 10000L);
        assertThat(wallet.isDueCondition()).isEqualTo(true);
        assertThat(wallet.getDueDate()).isEqualTo(10);
        assertThat(wallet.getDue()).isEqualTo(10000L);
        wallet = service.deleteGroupWalletDueRule(wallet.getGroupWalletId());
        assertThat(wallet.isDueCondition()).isEqualTo(false);
        assertThat(wallet.getDueDate()).isEqualTo(0);
        assertThat(wallet.getDue()).isEqualTo(null);
    }

    @Test
    void groupWalletWithdraw() throws NotEnoughBalanceException {
        Address address = new Address("서울시", "국회대로 54길 10", "07246");
        Member member1 = Member.builder()
                .address(address)
                .email("@naver1.com")
                .bankAccount("111-111-1111-11")
                .name("김진형")
                .payPassword("123123")
                .id("qweqwe1")
                .password("1231123")
                .phoneNumber("010-1111-1111").build();
        memberRepository.save(member1);

        PersonalWallet personalWallet = PersonalWallet.builder()
                .member(member1)
                .balance(0L)
                .build();

        personalWalletRepository.save(personalWallet);

        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
                .nickname("alias")
                .dueCondition(false)
                .member(member1)
                .balance(10000L)
                .build());


        service.groupWalletWithdraw(WithDrawDto.builder()
                .srcWalletId(wallet.getGroupWalletId())
                .destMemberId(member1.getMemberId())
                .amount(10000L)
                .currencyCode(CurrencyCode.KRW)
                .build());

        assertThat(wallet.getBalance()).isEqualTo(0L);
        assertThat(personalWallet.getBalance()).isEqualTo(10000L);
        assertThatThrownBy(() -> service.groupWalletWithdraw(WithDrawDto.builder()
                .srcWalletId(wallet.getGroupWalletId())
                .destMemberId(member1.getMemberId())
                .amount(10000L)
                .currencyCode(CurrencyCode.KRW)
                .build())).isInstanceOf(NotEnoughBalanceException.class);


        // 외화 TEST

        GroupWalletForeignCurrencyBalance groupUSD = groupForeignCurrencyRepository
                .save(GroupWalletForeignCurrencyBalance.builder()
                        .currencyCode(CurrencyCode.USD)
                        .balance(100L)
                        .groupWallet(wallet)
                        .build());

        PersonalWalletForeignCurrencyBalance personalUSD = personalForeignCurrencyBalanceRepository
                .save(PersonalWalletForeignCurrencyBalance.builder()
                        .currencyCode(CurrencyCode.USD)
                        .balance(0L)
                        .personalWallet(personalWallet)
                        .build());

        List<PersonalWalletForeignCurrencyBalance> list = personalForeignCurrencyBalanceRepository.findAll();

        service.groupWalletWithdraw(WithDrawDto.builder()
                .srcWalletId(wallet.getGroupWalletId())
                .destMemberId(member1.getMemberId())
                .amount(100L)
                .currencyCode(CurrencyCode.USD)
                .build());

        assertThat(groupUSD.getBalance()).isEqualTo(0L);
        assertThat(personalUSD.getBalance()).isEqualTo(100L);
        assertThatThrownBy(() -> service.groupWalletWithdraw(WithDrawDto.builder()
                .srcWalletId(wallet.getGroupWalletId())
                .destMemberId(member1.getMemberId())
                .amount(100L)
                .currencyCode(CurrencyCode.USD)
                .build())).isInstanceOf(NotEnoughBalanceException.class);

    }

    @Test
    void settle() throws NotEnoughBalanceException {
        Address address = new Address("서울시", "국회대로 54길 10", "07246");
        Member member1 = Member.builder()
                .address(address)
                .email("@naver1.com")
                .bankAccount("111-111-1111-11")
                .name("김진형1")
                .payPassword("123123")
                .id("qweqwe1")
                .password("1231123")
                .phoneNumber("010-1111-1111").build();
        memberRepository.save(member1);

        Member member2 = Member.builder()
                .address(address)
                .email("@naver2.com")
                .bankAccount("222-21212-1111-11")
                .name("김진형2")
                .payPassword("123123")
                .id("qweqwe2")
                .password("1231123")
                .phoneNumber("010-2222-2222").build();
        memberRepository.save(member2);

        PersonalWallet personalWallet1 = PersonalWallet.builder()
                .member(member1)
                .balance(0L)
                .build();
        personalWalletRepository.save(personalWallet1);

        PersonalWallet personalWallet2 = PersonalWallet.builder()
                .member(member2)
                .balance(0L)
                .build();
        personalWalletRepository.save(personalWallet2);

        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
                .nickname("KB WALLET")
                .dueCondition(false)
                .member(member1)
                .balance(100L)
                .build());

        Participation participation1 = participationRepository.save(Participation.builder()
                .groupWallet(wallet)
                .memberId(member1.getMemberId())
                .participationState(ParticipationState.PARTICIPATED)
                .role(Role.CHAIRMAN)
                .build()
        );

        Participation participation2 = participationRepository.save(Participation.builder()
                .groupWallet(wallet)
                .memberId(member2.getMemberId())
                .participationState(ParticipationState.PARTICIPATED)
                .role(Role.GENERAL)
                .build()
        );

        service.settle(SettleDto.builder()
                .groupWalletId(wallet.getGroupWalletId())
                .settleType(SettleType.NBBANG)
                .totalAmout(99L)
                .currencyCode(CurrencyCode.KRW)
                .build());

        assertThat(wallet.getBalance()).isEqualTo(1L);
        assertThat(personalWallet1.getBalance()).isEqualTo(50L);
        assertThat(personalWallet2.getBalance()).isEqualTo(49L);
        assertThat(transferRepository.searchAllByGroupWallet(wallet).size()).isEqualTo(2);

    }

    @Test
    void groupWalletDeposit() throws NotEnoughBalanceException {
        Address address = new Address("서울시", "국회대로 54길 10", "07246");
        Member member1 = Member.builder()
                .address(address)
                .email("@naver1.com")
                .bankAccount("111-111-1111-11")
                .name("김진형")
                .payPassword("123123")
                .id("qweqwe1")
                .password("1231123")
                .phoneNumber("010-1111-1111").build();
        memberRepository.save(member1);

        PersonalWallet personalWallet = PersonalWallet.builder()
                .member(member1)
                .balance(10000L)
                .build();

        personalWalletRepository.save(personalWallet);

        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
                .nickname("alias")
                .dueCondition(false)
                .member(member1)
                .balance(0L)
                .build());


        service.groupWalletDeposit(DepositDto.builder()
                .destWalletId(wallet.getGroupWalletId())
                .srcMemberId(member1.getMemberId())
                .amount(10000L)
                .currencyCode(CurrencyCode.KRW)
                .build());

        assertThat(personalWallet.getBalance()).isEqualTo(0L);
        assertThat(wallet.getBalance()).isEqualTo(10000L);
        assertThatThrownBy(() -> service.groupWalletDeposit(DepositDto.builder()
                .destWalletId(wallet.getGroupWalletId())
                .srcMemberId(member1.getMemberId())
                .amount(10000L)
                .currencyCode(CurrencyCode.KRW)
                .build())).isInstanceOf(NotEnoughBalanceException.class);


        // 외화 TEST

        GroupWalletForeignCurrencyBalance groupUSD = groupForeignCurrencyRepository
                .save(GroupWalletForeignCurrencyBalance.builder()
                        .currencyCode(CurrencyCode.USD)
                        .balance(0L)
                        .groupWallet(wallet)
                        .build());

        PersonalWalletForeignCurrencyBalance personalUSD = personalForeignCurrencyBalanceRepository
                .save(PersonalWalletForeignCurrencyBalance.builder()
                        .currencyCode(CurrencyCode.USD)
                        .balance(100L)
                        .personalWallet(personalWallet)
                        .build());

        service.groupWalletDeposit(DepositDto.builder()
                .destWalletId(wallet.getGroupWalletId())
                .srcMemberId(member1.getMemberId())
                .amount(100L)
                .currencyCode(CurrencyCode.USD)
                .build());

        assertThat(personalUSD.getBalance()).isEqualTo(0L);
        assertThat(groupUSD.getBalance()).isEqualTo(100L);
        assertThatThrownBy(() -> service.groupWalletDeposit(DepositDto.builder()
                .destWalletId(wallet.getGroupWalletId())
                .srcMemberId(member1.getMemberId())
                .amount(100L)
                .currencyCode(CurrencyCode.USD)
                .build())).isInstanceOf(NotEnoughBalanceException.class);

    }
}
