//package kb04.team02.web.mvc.service.groupwallet;
//
//import kb04.team02.web.mvc.common.dto.WalletHistoryDto;
//import kb04.team02.web.mvc.common.entity.*;
//import kb04.team02.web.mvc.exchange.dto.RuleDto;
//import kb04.team02.web.mvc.group.dto.CardIssuanceDto;
//import kb04.team02.web.mvc.group.dto.GroupMemberDto;
//import kb04.team02.web.mvc.group.dto.InstallmentDto;
//import kb04.team02.web.mvc.group.entity.*;
//import kb04.team02.web.mvc.group.repository.*;
//import kb04.team02.web.mvc.group.service.GroupWalletTabService;
//import kb04.team02.web.mvc.mypage.entity.CardIssuance;
//import kb04.team02.web.mvc.mypage.entity.CardState;
//import kb04.team02.web.mvc.member.entity.Address;
//import kb04.team02.web.mvc.member.entity.Member;
//import kb04.team02.web.mvc.member.entity.Role;
//import kb04.team02.web.mvc.saving.entity.InstallmentSaving;
//import kb04.team02.web.mvc.saving.entity.Saving;
//import kb04.team02.web.mvc.mypage.repository.CardIssuanceRepository;
//import kb04.team02.web.mvc.member.repository.MemberRepository;
//import kb04.team02.web.mvc.saving.repository.InstallmentSavingRepository;
//import kb04.team02.web.mvc.saving.repository.SavingRepository;
//import kb04.team02.web.mvc.personal.repository.PersonalWalletForeignCurrencyBalanceRepository;
//import kb04.team02.web.mvc.personal.repository.PersonalWalletRepository;
//import kb04.team02.web.mvc.personal.repository.PersonalWalletTransferRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.*;
//import org.springframework.test.annotation.Commit;
//import org.springframework.test.annotation.Rollback;
//
//import javax.transaction.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@SpringBootTest
//@Transactional
//@Rollback
//class GroupWalletTabServiceImplTest {
//
//    @Autowired
//    private GroupWalletTabService service;
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private SavingRepository savingRepository;
//    @Autowired
//    private GroupWalletRespository groupWalletRespository;
//    @Autowired
//    private ParticipationRepository participationRepository;
//    @Autowired
//    private InstallmentSavingRepository installmentSavingRepository;
//    @Autowired
//    private CardIssuanceRepository cardIssuanceRepository;
//    @Autowired
//    private GroupWalletTransferRepository transferRepository;
//    @Autowired
//    private GroupWalletExchangeRepository exchangeRepository;
//    @Autowired
//    private GroupWalletPaymentRepository paymentRepository;
//    @Autowired
//    private GroupWalletForeignCurrencyBalanceRepository groupForeignCurrencyRepository;
//    @Autowired
//    private PersonalWalletForeignCurrencyBalanceRepository personalForeignCurrencyBalanceRepository;
//    @Autowired
//    private PersonalWalletRepository personalWalletRepository;
//    @Autowired
//    private PersonalWalletTransferRepository personalWalletTransferRepository;
//
//
//    @AfterEach
//    public void afterEach() {
//        participationRepository.deleteAll();
//        installmentSavingRepository.deleteAll();
//        savingRepository.deleteAll();
//        cardIssuanceRepository.deleteAll();
//        transferRepository.deleteAll();
//        exchangeRepository.deleteAll();
//        paymentRepository.deleteAll();
//        groupForeignCurrencyRepository.deleteAll();
//        personalForeignCurrencyBalanceRepository.deleteAll();
//        personalWalletTransferRepository.deleteAll();
//        personalWalletRepository.deleteAll();
//        groupWalletRespository.deleteAll();
//        memberRepository.deleteAll();
//    }
//
//    @Test
//    void getMembersByGroupId() {
//        Address address = new Address("서울시", "국회대로 54길 10", "07246");
//        Member member1 = Member.builder()
//                .address(address)
//                .email("@naver1.com")
//                .bankAccount("111-111-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe1")
//                .password("1231123")
//                .phoneNumber("010-1111-1111").build();
//        memberRepository.save(member1);
//
//        Member member2 = Member.builder()
//                .address(address)
//                .email("@naver2.com")
//                .bankAccount("222-21212-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe2")
//                .password("1231123")
//                .phoneNumber("010-2222-2222").build();
//        memberRepository.save(member2);
//
//        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
//                .nickname("alias")
//                .dueCondition(false)
//                .member(member1)
//                .build());
//
//        participationRepository.save(Participation.builder()
//                .groupWallet(wallet)
//                .memberId(member1.getMemberId())
//                .participationState(ParticipationState.PARTICIPATED)
//                .role(Role.CHAIRMAN)
//                .build());
//
//        participationRepository.save(Participation.builder()
//                .groupWallet(wallet)
//                .memberId(member2.getMemberId())
//                .participationState(ParticipationState.PARTICIPATED)
//                .role(Role.GENERAL)
//                .build());
//
//        Pageable page = PageRequest.of((1 - 1), 10, Sort.by(Sort.Order.asc("name")));
//        List<GroupMemberDto> membersByGroupId = service.getMembersByGroupId(wallet.getGroupWalletId(), page);
//
//        System.out.println(membersByGroupId.size());
//
//        for (GroupMemberDto groupMemberDto : membersByGroupId) {
//            System.out.println("groupMemberDto.getMemberId() = " + groupMemberDto.getMemberId());
//            System.out.println("groupMemberDto.getName() = " + groupMemberDto.getName());
//        }
//    }
//
//    @Test
//    void deleteMember() {
//        Address address = new Address("서울시", "국회대로 54길 10", "07246");
//        Member member1 = Member.builder()
//                .address(address)
//                .email("@naver1.com")
//                .bankAccount("111-111-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe1")
//                .password("1231123")
//                .phoneNumber("010-1111-1111").build();
//        memberRepository.save(member1);
//
//        Member member2 = Member.builder()
//                .address(address)
//                .email("@naver2.com")
//                .bankAccount("222-21212-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe2")
//                .password("1231123")
//                .phoneNumber("010-2222-2222").build();
//        memberRepository.save(member2);
//
//        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
//                .nickname("alias")
//                .dueCondition(false)
//                .member(member1)
//                .build());
//
//        participationRepository.save(Participation.builder()
//                .groupWallet(wallet)
//                .memberId(member1.getMemberId())
//                .participationState(ParticipationState.PARTICIPATED)
//                .role(Role.CHAIRMAN)
//                .build());
//
//        participationRepository.save(Participation.builder()
//                .groupWallet(wallet)
//                .memberId(member2.getMemberId())
//                .participationState(ParticipationState.PARTICIPATED)
//                .role(Role.GENERAL)
//                .build());
//
//        service.deleteMember(wallet.getGroupWalletId(), member1.getMemberId());
//
//
//        Pageable page = PageRequest.of((1 - 1), 10, Sort.by(Sort.Order.asc("name")));
//        List<GroupMemberDto> membersByGroupId = service.getMembersByGroupId(wallet.getGroupWalletId(), page);
//
//        System.out.println(membersByGroupId.size());
//
//        for (GroupMemberDto groupMemberDto : membersByGroupId) {
//            System.out.println("groupMemberDto.getMemberId() = " + groupMemberDto.getMemberId());
//            System.out.println("groupMemberDto.getName() = " + groupMemberDto.getName());
//        }
//    }
//
//    @Test
//    void grantMemberAuth() {
//        Address address = new Address("서울시", "국회대로 54길 10", "07246");
//        Member member1 = Member.builder()
//                .address(address)
//                .email("@naver1.com")
//                .bankAccount("111-111-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe1")
//                .password("1231123")
//                .phoneNumber("010-1111-1111").build();
//        memberRepository.save(member1);
//
//        Member member2 = Member.builder()
//                .address(address)
//                .email("@naver2.com")
//                .bankAccount("222-21212-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe2")
//                .password("1231123")
//                .phoneNumber("010-2222-2222").build();
//        memberRepository.save(member2);
//
//        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
//                .nickname("alias")
//                .dueCondition(false)
//                .member(member1)
//                .build());
//
//        participationRepository.save(Participation.builder()
//                .groupWallet(wallet)
//                .memberId(member1.getMemberId())
//                .participationState(ParticipationState.PARTICIPATED)
//                .role(Role.CHAIRMAN)
//                .build());
//
//        participationRepository.save(Participation.builder()
//                .groupWallet(wallet)
//                .memberId(member2.getMemberId())
//                .participationState(ParticipationState.PARTICIPATED)
//                .role(Role.GENERAL)
//                .build());
//
//        List<Participation> participations = participationRepository.findByGroupWalletAndParticipationState(wallet, ParticipationState.PARTICIPATED);
//
//        for (Participation participation : participations) {
//            System.out.println("participation.getMemberId() = " + participation.getMemberId());
//            System.out.println("participation.getParticipationState() = " + participation.getParticipationState());
//            System.out.println("participation.getRole() = " + participation.getRole());
//        }
//        Pageable page = PageRequest.of((1 - 1), 10, Sort.by(Sort.Order.asc("name")));
//        service.grantMemberAuth(wallet.getGroupWalletId(), member2.getMemberId());
//
//        participations = participationRepository.findByGroupWalletAndParticipationState(wallet, ParticipationState.PARTICIPATED);
//
//        for (Participation participation : participations) {
//            System.out.println("participation.getMemberId() = " + participation.getMemberId());
//            System.out.println("participation.getParticipationState() = " + participation.getParticipationState());
//            System.out.println("participation.getRole() = " + participation.getRole());
//        }
//    }
//
//    @Test
//    void revokeMemberAuth() {
//        Address address = new Address("서울시", "국회대로 54길 10", "07246");
//        Member member1 = Member.builder()
//                .address(address)
//                .email("@naver1.com")
//                .bankAccount("111-111-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe1")
//                .password("1231123")
//                .phoneNumber("010-1111-1111").build();
//        memberRepository.save(member1);
//
//        Member member2 = Member.builder()
//                .address(address)
//                .email("@naver2.com")
//                .bankAccount("222-21212-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe2")
//                .password("1231123")
//                .phoneNumber("010-2222-2222").build();
//        memberRepository.save(member2);
//
//        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
//                .nickname("alias")
//                .dueCondition(false)
//                .member(member1)
//                .build());
//
//        participationRepository.save(Participation.builder()
//                .groupWallet(wallet)
//                .memberId(member1.getMemberId())
//                .participationState(ParticipationState.PARTICIPATED)
//                .role(Role.CHAIRMAN)
//                .build());
//
//        participationRepository.save(Participation.builder()
//                .groupWallet(wallet)
//                .memberId(member2.getMemberId())
//                .participationState(ParticipationState.PARTICIPATED)
//                .role(Role.GENERAL)
//                .build());
//
//        service.grantMemberAuth(wallet.getGroupWalletId(), member2.getMemberId());
//        List<Participation> participations = participationRepository.findByGroupWalletAndParticipationState(wallet, ParticipationState.PARTICIPATED);
//
//        for (Participation participation : participations) {
//            System.out.println("participation.getMemberId() = " + participation.getMemberId());
//            System.out.println("participation.getParticipationState() = " + participation.getParticipationState());
//            System.out.println("participation.getRole() = " + participation.getRole());
//        }
//
//        service.revokeMemberAuth(wallet.getGroupWalletId(), member2.getMemberId());
//
//        participations = participationRepository.findByGroupWalletAndParticipationState(wallet, ParticipationState.PARTICIPATED);
//
//        for (Participation participation : participations) {
//            System.out.println("participation.getMemberId() = " + participation.getMemberId());
//            System.out.println("participation.getParticipationState() = " + participation.getParticipationState());
//            System.out.println("participation.getRole() = " + participation.getRole());
//        }
//    }
//
//    @Test
//    void getRuleById() {
//        Address address = new Address("서울시", "국회대로 54길 10", "07246");
//        Member member1 = Member.builder()
//                .address(address)
//                .email("@naver1.com")
//                .bankAccount("111-111-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe1")
//                .password("1231123")
//                .phoneNumber("010-1111-1111").build();
//        memberRepository.save(member1);
//
//        Member member2 = Member.builder()
//                .address(address)
//                .email("@naver2.com")
//                .bankAccount("222-21212-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe2")
//                .password("1231123")
//                .phoneNumber("010-2222-2222").build();
//        memberRepository.save(member2);
//
//        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
//                .nickname("alias")
//                .dueCondition(true)
//                .member(member1)
//                .dueAccumulation(10000L)
//                .dueDate(1)
//                .due(100L)
//                .build());
//
//        RuleDto rule = service.getRuleById(wallet.getGroupWalletId());
//        System.out.println("rule = " + rule);
//    }
//
//    @Test
//    void createRule() {
//        Address address = new Address("서울시", "국회대로 54길 10", "07246");
//        Member member1 = Member.builder()
//                .address(address)
//                .email("@naver1.com")
//                .bankAccount("111-111-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe1")
//                .password("1231123")
//                .phoneNumber("010-1111-1111").build();
//        memberRepository.save(member1);
//
//        Member member2 = Member.builder()
//                .address(address)
//                .email("@naver2.com")
//                .bankAccount("222-21212-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe2")
//                .password("1231123")
//                .phoneNumber("010-2222-2222").build();
//        memberRepository.save(member2);
//
//        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
//                .nickname("alias")
//                .dueCondition(false)
//                .member(member1)
//                .build());
//        System.out.println("wallet.getDue() = " + wallet.getDue());
//        System.out.println("wallet.getDueDate() = " + wallet.getDueDate());
//        System.out.println("=============================");
//        service.createRule(wallet.getGroupWalletId(), RuleDto.builder().duePrice(100L).dueDate(1).build());
//        System.out.println("wallet.getDue() = " + wallet.getDue());
//        System.out.println("wallet.getDueDate() = " + wallet.getDueDate());
//
//    }
//
//    @Test
//    void alertMember() {
//    }
//
//    @Test
//    void deleteRule() {
//        Address address = new Address("서울시", "국회대로 54길 10", "07246");
//        Member member1 = Member.builder()
//                .address(address)
//                .email("@naver1.com")
//                .bankAccount("111-111-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe1")
//                .password("1231123")
//                .phoneNumber("010-1111-1111").build();
//        memberRepository.save(member1);
//
//        Member member2 = Member.builder()
//                .address(address)
//                .email("@naver2.com")
//                .bankAccount("222-21212-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe2")
//                .password("1231123")
//                .phoneNumber("010-2222-2222").build();
//        memberRepository.save(member2);
//
//        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
//                .nickname("alias")
//                .dueCondition(true)
//                .member(member1)
//                .dueAccumulation(10000L)
//                .dueDate(1)
//                .due(100L)
//                .build());
//
//        System.out.println("wallet.isDueCondition() = " + wallet.isDueCondition());
//        System.out.println("wallet.getDue() = " + wallet.getDue());
//        System.out.println("wallet.getDueDate() = " + wallet.getDueDate());
//        System.out.println("=============================");
//        service.deleteRule(wallet.getGroupWalletId());
//        System.out.println("wallet.isDueCondition() = " + wallet.isDueCondition());
//        System.out.println("wallet.getDue() = " + wallet.getDue());
//        System.out.println("wallet.getDueDate() = " + wallet.getDueDate());
//    }
//
//    @Test
//    void getSavingById() {
//        Address address = new Address("서울시", "국회대로 54길 10", "07246");
//        Member member1 = Member.builder()
//                .address(address)
//                .email("@naver1.com")
//                .bankAccount("111-111-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe1")
//                .password("1231123")
//                .phoneNumber("010-1111-1111").build();
//        memberRepository.save(member1);
//
//        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
//                .nickname("alias")
//                .dueCondition(true)
//                .member(member1)
//                .build());
//
//        Saving save = savingRepository.save(Saving.builder()
//                .name("saving1")
//                .savingComment("ccccccccccccccccccomment")
//                .interestRate(1.5)
//                .period(6)
//                .amountLimit(10000000L)
//                .build());
//
//        installmentSavingRepository.save(InstallmentSaving.builder()
//                .maturityDate(LocalDateTime.now())
//                .done(false)
//                .totalAmount(1000000L)
//                .savingDate(1)
//                .savingAmount(10000L)
//                .groupWallet(wallet)
//                .saving(save)
//                .build());
//
//        InstallmentDto saving = service.getSavingById(wallet.getGroupWalletId());
//        System.out.println("saving = " + saving);
//    }
//
//    @Test
//    void cancelSaving() {
//        Address address = new Address("서울시", "국회대로 54길 10", "07246");
//        Member member1 = Member.builder()
//                .address(address)
//                .email("@naver1.com")
//                .bankAccount("111-111-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe1")
//                .password("1231123")
//                .phoneNumber("010-1111-1111").build();
//        memberRepository.save(member1);
//
//        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
//                .nickname("alias")
//                .dueCondition(true)
//                .member(member1)
//                .balance(0L)
//                .build());
//
//        Saving save = savingRepository.save(Saving.builder()
//                .name("saving1")
//                .savingComment("ccccccccccccccccccomment")
//                .interestRate(1.5)
//                .period(6)
//                .amountLimit(10000000L)
//                .build());
//
//        InstallmentSaving installmentSaving = installmentSavingRepository.save(InstallmentSaving.builder()
//                .maturityDate(LocalDateTime.now())
//                .done(false)
//                .totalAmount(1000000L)
//                .savingDate(1)
//                .savingAmount(10000L)
//                .groupWallet(wallet)
//                .saving(save)
//                .build());
//
//        System.out.println("wallet.getBalance() = " + wallet.getBalance());
//        System.out.println("installmentSaving.isDone() = " + installmentSaving.isDone());
//        service.cancelSaving(wallet.getGroupWalletId());
//        System.out.println("wallet.getBalance() = " + wallet.getBalance());
//        System.out.println("installmentSaving.isDone() = " + installmentSaving.isDone());
//    }
//
//    @Test
//    void getCard() {
//        Address address = new Address("서울시", "국회대로 54길 10", "07246");
//        Member member1 = Member.builder()
//                .address(address)
//                .email("@naver1.com")
//                .bankAccount("111-111-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe1")
//                .password("1231123")
//                .phoneNumber("010-1111-1111").build();
//        memberRepository.save(member1);
//
//        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
//                .nickname("alias")
//                .dueCondition(true)
//                .member(member1)
//                .balance(0L)
//                .build());
//        cardIssuanceRepository.save(CardIssuance.builder()
//                .cardNumber("1111-111111")
//                .cardState(CardState.OK).walletId(wallet.getGroupWalletId())
//                .walletType(WalletType.GROUP_WALLET)
//                .member(member1)
//                .build());
//
//        List<CardIssuanceDto> card = service.getCard(wallet.getGroupWalletId());
//        for (CardIssuanceDto ca : card) {
//            System.out.println("ca = " + ca);
//        }
//    }
//
//    @Test
//    void linkCard() {
//        Address address = new Address("서울시", "국회대로 54길 10", "07246");
//        Member member1 = Member.builder()
//                .address(address)
//                .email("@naver1.com")
//                .bankAccount("111-111-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe1")
//                .password("1231123")
//                .phoneNumber("010-1111-1111").build();
//        memberRepository.save(member1);
//
//        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
//                .nickname("alias")
//                .dueCondition(true)
//                .member(member1)
//                .balance(0L)
//                .build());
//
//        CardIssuance save = cardIssuanceRepository.save(CardIssuance.builder()
//                .cardNumber("1111-111111")
//                .cardState(CardState.OK).walletId(null)
//                .walletType(WalletType.PERSONAL_WALLET)
//                .member(member1)
//                .build());
//
//        System.out.println("save.getCardNumber() = " + save.getCardNumber());
//        System.out.println("save.getCardState() = " + save.getCardState());
//        System.out.println("save.getWalletId() = " + save.getWalletId());
//        System.out.println("=============================================");
//        service.linkCard(wallet.getGroupWalletId(), member1.getMemberId());
//        System.out.println("save.getCardNumber() = " + save.getCardNumber());
//        System.out.println("save.getCardState() = " + save.getCardState());
//        System.out.println("save.getWalletId() = " + save.getWalletId());
//    }
//
//    @Test
//    void getHistoryByGroupId() throws InterruptedException {
//        Address address = new Address("서울시", "국회대로 54길 10", "07246");
//        Member member1 = Member.builder()
//                .address(address)
//                .email("@naver1.com")
//                .bankAccount("111-111-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe1")
//                .password("1231123")
//                .phoneNumber("010-1111-1111").build();
//        memberRepository.save(member1);
//
//        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
//                .nickname("alias")
//                .dueCondition(true)
//                .member(member1)
//                .balance(0L)
//                .build());
//
//
//        transferRepository.save(GroupWalletTransfer.builder()
//                .groupWallet(wallet)
//                .transferType(TransferType.DEPOSIT)
//                .fromType(TargetType.PERSONAL_WALLET)
//                .toType(TargetType.GROUP_WALLET)
//                .src("출발지")
//                .dest("목적지")
//                .amount(10000L)
//                .afterBalance(990000L)
//                .currencyCode(CurrencyCode.KRW)
//                .build());
//
//        transferRepository.flush();
//
//        Thread.sleep(20000);
//        exchangeRepository.save(GroupWalletExchange.builder()
//                .sellCurrencyCode(CurrencyCode.USD)
//                .sellAmount(10L)
//                .afterSellBalance(990L)
//                .buyCurrencyCode(CurrencyCode.KRW)
//                .buyAmount(13000L)
//                .afterBuyBalance(13000L)
//                .exchangeRate(1300.0)
//                .groupWallet(wallet)
//                .build());
//
//        exchangeRepository.flush();
//
//        Thread.sleep(20000);
//
//        paymentRepository.save(GroupWalletPayment.builder()
//                .currencyCode(CurrencyCode.KRW)
//                .paymentType(PaymentType.OK)
//                .paymentCategory(PaymentCategoryType.ENTERTAINMENT)
//                .amount(110000L)
//                .afterPayBalance(0L)
//                .groupWallet(wallet)
//                .build());
//
//        Pageable page = PageRequest.of((0), 10, Sort.by(Sort.Order.desc("insertDate")));
//        Page<WalletHistoryDto> historyPageList = service.getHistoryByGroupId(wallet.getGroupWalletId(), page);
//        for (WalletHistoryDto historyDto : historyPageList) {
//            System.out.println("historyDto = " + historyDto);
//        }
//    }
//
//    @Test
//    void getHistory() throws InterruptedException {
//        Address address = new Address("서울시", "국회대로 54길 10", "07246");
//        Member member1 = Member.builder()
//                .address(address)
//                .email("@naver1.com")
//                .bankAccount("111-111-1111-11")
//                .name("김진형")
//                .payPassword("123123")
//                .id("qweqwe1")
//                .password("1231123")
//                .phoneNumber("010-1111-1111").build();
//        memberRepository.save(member1);
//
//        GroupWallet wallet = groupWalletRespository.save(GroupWallet.builder()
//                .nickname("alias")
//                .dueCondition(true)
//                .member(member1)
//                .balance(0L)
//                .build());
//
//
//        GroupWalletTransfer transfer = transferRepository.save(GroupWalletTransfer.builder()
//                .groupWallet(wallet)
//                .transferType(TransferType.DEPOSIT)
//                .fromType(TargetType.PERSONAL_WALLET)
//                .toType(TargetType.GROUP_WALLET)
//                .src("출발지")
//                .dest("목적지")
//                .amount(10000L)
//                .afterBalance(990000L)
//                .currencyCode(CurrencyCode.KRW)
//                .build());
//
//        GroupWalletExchange exchange = exchangeRepository.save(GroupWalletExchange.builder()
//                .sellCurrencyCode(CurrencyCode.USD)
//                .sellAmount(10L)
//                .afterSellBalance(990L)
//                .buyCurrencyCode(CurrencyCode.KRW)
//                .buyAmount(13000L)
//                .afterBuyBalance(13000L)
//                .exchangeRate(1300.0)
//                .groupWallet(wallet)
//                .build());
//
//        GroupWalletPayment payment = paymentRepository.save(GroupWalletPayment.builder()
//                .currencyCode(CurrencyCode.KRW)
//                .paymentType(PaymentType.OK)
//                .paymentCategory(PaymentCategoryType.ENTERTAINMENT)
//                .amount(110000L)
//                .afterPayBalance(0L)
//                .groupWallet(wallet)
//                .build());
//
//
//        WalletHistoryDto 출금 = service.getHistory(wallet.getGroupWalletId(), transfer.getGroupWalletTransferId(), "출금");
//        System.out.println("출금 = " + 출금);
//        WalletHistoryDto 재환전 = service.getHistory(wallet.getGroupWalletId(), exchange.getGroupWalletExchangeId(), "재환전");
//        System.out.println("재환전 = " + 재환전);
//        WalletHistoryDto 결제 = service.getHistory(wallet.getGroupWalletId(), payment.getGroupWalletExchangeId(), "결제");
//        System.out.println("결제 = " + 결제);
//    }
//}