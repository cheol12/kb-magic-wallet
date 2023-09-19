package kb04.team02.web.mvc;

import kb04.team02.web.mvc.exchange.entity.Bank;
import kb04.team02.web.mvc.exchange.entity.OfflineReceipt;
import kb04.team02.web.mvc.exchange.entity.ReceiptState;
import kb04.team02.web.mvc.group.entity.*;
import kb04.team02.web.mvc.group.repository.*;
import kb04.team02.web.mvc.mypage.entity.CardIssuance;
import kb04.team02.web.mvc.mypage.entity.CardState;
import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.member.entity.Address;
import kb04.team02.web.mvc.member.entity.Member;
import kb04.team02.web.mvc.member.entity.Role;
import kb04.team02.web.mvc.saving.entity.InstallmentSaving;
import kb04.team02.web.mvc.saving.entity.Saving;
import kb04.team02.web.mvc.common.entity.TargetType;
import kb04.team02.web.mvc.common.entity.TransferType;
import kb04.team02.web.mvc.common.entity.WalletType;
import kb04.team02.web.mvc.personal.entity.PersonalWallet;
import kb04.team02.web.mvc.personal.entity.PersonalWalletForeignCurrencyBalance;
import kb04.team02.web.mvc.exchange.repository.BankRepository;
import kb04.team02.web.mvc.exchange.repository.OfflineReceiptRepository;
import kb04.team02.web.mvc.mypage.repository.CardIssuanceRepository;
import kb04.team02.web.mvc.member.repository.MemberRepository;
import kb04.team02.web.mvc.saving.repository.InstallmentSavingRepository;
import kb04.team02.web.mvc.saving.repository.SavingRepository;
import kb04.team02.web.mvc.personal.repository.PersonalWalletForeignCurrencyBalanceRepository;
import kb04.team02.web.mvc.personal.repository.PersonalWalletRepository;
import kb04.team02.web.mvc.personal.repository.PersonalWalletTransferRepository;
import kb04.team02.web.mvc.group.service.GroupWalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@Commit
public class InitialSettingTest {

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
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private OfflineReceiptRepository offlineReceiptRepository;

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


    @Test
    public void createSampleMembers() {
        List<Member> members = new ArrayList<>();
        Address address = new Address("서울특별시", "국회대로54길 10", "07246");
        Member member1 = Member.builder()
                .id("qwer")
                .password("qwer")
                .name("김진형")
                .address(address)
                .phoneNumber("010-1111-1111")
                .email("qwer@example.com")
                .payPassword("qwer")
                .bankAccount("111-111-111111")
                .build();

        Member member2 = Member.builder()
                .id("asdf")
                .password("asdf")
                .name("최예빈")
                .address(address)
                .phoneNumber("010-2222-2222")
                .email("asdf@example.com")
                .payPassword("asdf")
                .bankAccount("222-222-222222")
                .build();

        Member member3 = Member.builder()
                .id("zxcv")
                .password("zxcv")
                .name("김철")
                .address(address)
                .phoneNumber("010-3333-3333")
                .email("zxcv@example.com")
                .payPassword("zxcv")
                .bankAccount("333-333-333333")
                .build();

        Member member4 = Member.builder()
                .id("wert")
                .password("wert")
                .name("김현지")
                .address(address)
                .phoneNumber("010-4444-4444")
                .email("wert@example.com")
                .payPassword("wert")
                .bankAccount("444-444-4444")
                .build();

        Member member5 = Member.builder()
                .id("sdfg")
                .password("sdfg")
                .name("염혜정")
                .address(address)
                .phoneNumber("010-5555-5555")
                .email("sdfg@example.com")
                .payPassword("sdfg")
                .bankAccount("555-555-555555")
                .build();

        Member member6 = Member.builder()
                .id("xcvb")
                .password("xcvb")
                .name("정지원")
                .address(address)
                .phoneNumber("010-6666-6666")
                .email("xcvb@example.com")
                .payPassword("xcvb")
                .bankAccount("666-666-666666")
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);
        memberRepository.save(member6);


        PersonalWallet personalWallet1 = PersonalWallet.builder().balance(1000000L).member(member1).build();
        PersonalWallet personalWallet2 = PersonalWallet.builder().balance(1000000L).member(member2).build();
        PersonalWallet personalWallet3 = PersonalWallet.builder().balance(1000000L).member(member3).build();
        PersonalWallet personalWallet4 = PersonalWallet.builder().balance(1000000L).member(member4).build();
        PersonalWallet personalWallet5 = PersonalWallet.builder().balance(1000000L).member(member5).build();
        PersonalWallet personalWallet6 = PersonalWallet.builder().balance(1000000L).member(member6).build();
        personalWalletRepository.save(personalWallet1);
        personalWalletRepository.save(personalWallet2);
        personalWalletRepository.save(personalWallet3);
        personalWalletRepository.save(personalWallet4);
        personalWalletRepository.save(personalWallet5);
        personalWalletRepository.save(personalWallet6);

        GroupWallet groupWallet1 = GroupWallet.builder()
                .nickname("모임지갑 1")
                .balance(100000L) // 초기 잔액 설정
                .dueCondition(false)
                .dueAccumulation(0L)
                .dueDate(0)
                .due(0L)
                .member(member1)
                .build();

        GroupWallet groupWallet2 = GroupWallet.builder()
                .nickname("모임지갑 2")
                .balance(0L) // 초기 잔액 설정
                .dueCondition(true)
                .dueAccumulation(0L)
                .dueDate(1)
                .due(50000L)
                .member(member2)
                .build();

        GroupWallet groupWallet3 = GroupWallet.builder()
                .nickname("모임지갑 3")
                .balance(0L) // 초기 잔액 설정
                .dueCondition(false)
                .dueAccumulation(0L)
                .dueDate(0)
                .due(0L)
                .member(member3)
                .build();

        groupWalletRespository.save(groupWallet1);
        groupWalletRespository.save(groupWallet2);
        groupWalletRespository.save(groupWallet3);

        // 모임지갑 1 참여 (1, 2, 3, 4, 5, 6 전체)
        participationRepository.save(Participation.builder().groupWallet(groupWallet1).participationState(ParticipationState.PARTICIPATED)
                .role(Role.CHAIRMAN).memberId(member1.getMemberId()).build());
        participationRepository.save(Participation.builder().groupWallet(groupWallet1).participationState(ParticipationState.PARTICIPATED)
                .role(Role.CO_CHAIRMAN).memberId(member2.getMemberId()).build());
        participationRepository.save(Participation.builder().groupWallet(groupWallet1).participationState(ParticipationState.PARTICIPATED)
                .role(Role.GENERAL).memberId(member3.getMemberId()).build());
        participationRepository.save(Participation.builder().groupWallet(groupWallet1).participationState(ParticipationState.PARTICIPATED)
                .role(Role.GENERAL).memberId(member4.getMemberId()).build());
        participationRepository.save(Participation.builder().groupWallet(groupWallet1).participationState(ParticipationState.PARTICIPATED)
                .role(Role.GENERAL).memberId(member5.getMemberId()).build());
        participationRepository.save(Participation.builder().groupWallet(groupWallet1).participationState(ParticipationState.PARTICIPATED)
                .role(Role.GENERAL).memberId(member6.getMemberId()).build());

        // 모임지갑 2 참여 (1, 2, 3, 4)
        participationRepository.save(Participation.builder().groupWallet(groupWallet2).participationState(ParticipationState.PARTICIPATED)
                .role(Role.GENERAL).memberId(member1.getMemberId()).build());
        participationRepository.save(Participation.builder().groupWallet(groupWallet2).participationState(ParticipationState.PARTICIPATED)
                .role(Role.CHAIRMAN).memberId(member2.getMemberId()).build());
        participationRepository.save(Participation.builder().groupWallet(groupWallet2).participationState(ParticipationState.PARTICIPATED)
                .role(Role.GENERAL).memberId(member3.getMemberId()).build());
        participationRepository.save(Participation.builder().groupWallet(groupWallet2).participationState(ParticipationState.PARTICIPATED)
                .role(Role.GENERAL).memberId(member4.getMemberId()).build());

        // 모임지갑 3 참여 (3, 4, 5, 6)
        participationRepository.save(Participation.builder().groupWallet(groupWallet3).participationState(ParticipationState.PARTICIPATED)
                .role(Role.GENERAL).memberId(member3.getMemberId()).build());
        participationRepository.save(Participation.builder().groupWallet(groupWallet3).participationState(ParticipationState.PARTICIPATED)
                .role(Role.GENERAL).memberId(member4.getMemberId()).build());
        participationRepository.save(Participation.builder().groupWallet(groupWallet3).participationState(ParticipationState.PARTICIPATED)
                .role(Role.CO_CHAIRMAN).memberId(member5.getMemberId()).build());
        participationRepository.save(Participation.builder().groupWallet(groupWallet3).participationState(ParticipationState.PARTICIPATED)
                .role(Role.CHAIRMAN).memberId(member6.getMemberId()).build());

        // 모임지갑 이체 내역
        transferRepository.save(GroupWalletTransfer.builder()
                .transferType(TransferType.WITHDRAW)
                .fromType(TargetType.GROUP_WALLET)
                .toType(TargetType.PERSONAL_WALLET)
                .src(groupWallet1.getNickname())
                .dest("Receiver's Name") // 수신자 이름 또는 정보
                .amount(10000L)
                .afterBalance(100000L) // 이체 후 잔액 계산
                .currencyCode(CurrencyCode.KRW) // 통화 코드 설정
                .groupWallet(groupWallet1)
                .build());

        transferRepository.save(GroupWalletTransfer.builder()
                .transferType(TransferType.WITHDRAW)
                .fromType(TargetType.GROUP_WALLET)
                .toType(TargetType.PERSONAL_WALLET)
                .src(groupWallet2.getNickname())
                .dest("Receiver's Name") // 수신자 이름 또는 정보
                .amount(10000L)
                .afterBalance(0L) // 이체 후 잔액 계산
                .currencyCode(CurrencyCode.KRW) // 통화 코드 설정
                .groupWallet(groupWallet2)
                .build());

        transferRepository.save(GroupWalletTransfer.builder()
                .transferType(TransferType.DEPOSIT)
                .fromType(TargetType.PERSONAL_WALLET)
                .toType(TargetType.GROUP_WALLET)
                .src("Receiver's Name")
                .dest(groupWallet3.getNickname()) // 수신자 이름 또는 정보
                .amount(50000L)
                .afterBalance(50000L) // 이체 후 잔액 계산
                .currencyCode(CurrencyCode.KRW) // 통화 코드 설정
                .groupWallet(groupWallet3)
                .build());

        // 모임지갑 1 외화
        groupForeignCurrencyRepository.save(GroupWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.USD)
                .balance(1000L)
                .groupWallet(groupWallet1)
                .build());
        groupForeignCurrencyRepository.save(GroupWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.JPY)
                .balance(0L)
                .groupWallet(groupWallet1)
                .build());

        // 모임지갑 2 외화
        groupForeignCurrencyRepository.save(GroupWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.USD)
                .balance(10L)
                .groupWallet(groupWallet2)
                .build());
        groupForeignCurrencyRepository.save(GroupWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.JPY)
                .balance(500L)
                .groupWallet(groupWallet2)
                .build());

        // 모임지갑 3 외화
        groupForeignCurrencyRepository.save(GroupWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.USD)
                .balance(0L)
                .groupWallet(groupWallet3)
                .build());
        groupForeignCurrencyRepository.save(GroupWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.JPY)
                .balance(0L)
                .groupWallet(groupWallet3)
                .build());

        //====================================================================================
        personalForeignCurrencyBalanceRepository.save(PersonalWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.USD)
                .balance(0L)
                .personalWallet(personalWallet1)
                .build());
        personalForeignCurrencyBalanceRepository.save(PersonalWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.JPY)
                .balance(0L)
                .personalWallet(personalWallet1)
                .build());

        personalForeignCurrencyBalanceRepository.save(PersonalWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.USD)
                .balance(100L)
                .personalWallet(personalWallet2)
                .build());
        personalForeignCurrencyBalanceRepository.save(PersonalWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.JPY)
                .balance(10000L)
                .personalWallet(personalWallet2)
                .build());

        personalForeignCurrencyBalanceRepository.save(PersonalWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.USD)
                .balance(1L)
                .personalWallet(personalWallet3)
                .build());
        personalForeignCurrencyBalanceRepository.save(PersonalWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.JPY)
                .balance(1L)
                .personalWallet(personalWallet3)
                .build());

        personalForeignCurrencyBalanceRepository.save(PersonalWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.USD)
                .balance(900L)
                .personalWallet(personalWallet4)
                .build());
        personalForeignCurrencyBalanceRepository.save(PersonalWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.JPY)
                .balance(10000L)
                .personalWallet(personalWallet4)
                .build());

        personalForeignCurrencyBalanceRepository.save(PersonalWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.USD)
                .balance(100000L)
                .personalWallet(personalWallet5)
                .build());
        personalForeignCurrencyBalanceRepository.save(PersonalWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.JPY)
                .balance(99990000L)
                .personalWallet(personalWallet5)
                .build());

        personalForeignCurrencyBalanceRepository.save(PersonalWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.USD)
                .balance(70000L)
                .personalWallet(personalWallet6)
                .build());
        personalForeignCurrencyBalanceRepository.save(PersonalWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.JPY)
                .balance(87000000L)
                .personalWallet(personalWallet6)
                .build());
        //====================================================================================

        cardIssuanceRepository.save(CardIssuance.builder()
                .cardNumber("카드번호1")
                .cardState(CardState.OK)
                .walletId(groupWallet1.getGroupWalletId())
                .walletType(WalletType.GROUP_WALLET)
                .member(member1)
                .build());

        cardIssuanceRepository.save(CardIssuance.builder()
                .cardNumber("카드번호2")
                .cardState(CardState.OK)
                .walletId(groupWallet1.getGroupWalletId())
                .walletType(WalletType.GROUP_WALLET)
                .member(member2)
                .build());

        cardIssuanceRepository.save(CardIssuance.builder()
                .cardNumber("카드번호3")
                .cardState(CardState.OK)
                .walletId(groupWallet2.getGroupWalletId())
                .walletType(WalletType.GROUP_WALLET)
                .member(member3)
                .build());

        cardIssuanceRepository.save(CardIssuance.builder()
                .cardNumber("카드번호5")
                .cardState(CardState.OK)
                .walletId(groupWallet3.getGroupWalletId())
                .walletType(WalletType.GROUP_WALLET)
                .member(member6)
                .build());

        cardIssuanceRepository.save(CardIssuance.builder()
                .cardNumber("카드번호6")
                .cardState(CardState.OK)
                .walletId(groupWallet3.getGroupWalletId())
                .walletType(WalletType.GROUP_WALLET)
                .member(member5)
                .build());

        Saving save1 = savingRepository.save(Saving.builder()
                .name("KB내맘대로적금")
                .savingComment("\n" +
                        "상품에 관한 다양한 옵션(우대이율, 부가서비스 등)을 제공함으로써 고객이 직접 상품 요건을 설계하여 가입할 수 있는 비대면채널 전용 DIY(Do-It-Yourself)형 상품")
                .interestRate(3.75) // Example: Increment interest rate for each saving
                .period(36) // Example: Increment period for each saving
                .amountLimit(3_000_000L) // Example: Increment amount limit for each saving
                .build());
        Saving save2 = savingRepository.save(Saving.builder()
                .name("직장인우대적금")
                .savingComment("직장인의 재테크 스타일을 반영하여 급여이체를 하거나 보너스 등의 부정기적인 자금을 추가로 적립하는 경우 우대이율로 목돈마련을 지원하고 결혼, 출산, 이사 등 이벤트를 위한 중도해지시 기본이율을 제공 하는 적립식 예금")
                .interestRate(4.05) // Example: Increment interest rate for each saving
                .period(36) // Example: Increment period for each saving
                .amountLimit(3_000_000L) // Example: Increment amount limit for each saving
                .build());
        Saving save3 = savingRepository.save(Saving.builder()
                .name("KB 특★한 적금")
                .savingComment("내가 원하는 특별한 날을 만기일로 지정하고 변경할 수 있는 단기 적금")
                .interestRate(6.0) // Example: Increment interest rate for each saving
                .period(1) // Example: Increment period for each saving
                .amountLimit(300_000L) // Example: Increment amount limit for each saving
                .build());
        Saving save4 = savingRepository.save(Saving.builder()
                .name("KB장병내일준비적금")
                .savingComment("병사봉급 한도 내에서 복무기간 동안 목돈마련을 원하는 병역 의무복무자 맞춤형 상품으로, 은행 거래실적에 따라 추가 우대이율 제공")
                .interestRate(5.5) // Example: Increment interest rate for each saving
                .period(24) // Example: Increment period for each saving
                .amountLimit(200_000L) // Example: Increment amount limit for each saving
                .build());
        Saving save5 = savingRepository.save(Saving.builder()
                .name("KB청년도약계좌")
                .savingComment("힘찬 미래 높은 도약")
                .interestRate(6) // Example: Increment interest rate for each saving
                .period(3) // Example: Increment period for each saving
                .amountLimit(700_000L) // Example: Increment amount limit for each saving
                .build());
        Saving save6 = savingRepository.save(Saving.builder()
                .name("KB맑은하늘적금")
                .savingComment("맑은하늘을 위한 생활 속 작은 실천에 대해 우대금리를 제공하고, 대중교통/자전거상해 관련 무료 보험서비스(최대 2억원 보장)를 제공하는 친환경 특화 상품")
                .interestRate(3.35) // Example: Increment interest rate for each saving
                .period(36) // Example: Increment period for each saving
                .amountLimit(1_000_000L) // Example: Increment amount limit for each saving
                .build());
        Saving save7 = savingRepository.save(Saving.builder()
                .name("온국민 건강적금-골든라이프")
                .savingComment("시니어 고객의 건강관리와 금융 혜택을 결합한 앱테크형 상품으로, 저소득층 대상 특별 우대이율을 제공하는 적금")
                .interestRate(10) // Example: Increment interest rate for each saving
                .period(6) // Example: Increment period for each saving
                .amountLimit(200_000L) // Example: Increment amount limit for each saving
                .build());
        Saving save8 = savingRepository.save(Saving.builder()
                .name("KB맑은바다적금")
                .savingComment("해양쓰레기 줄이기 활동에 동참할 경우 친환경 실천 우대이율을 제공하고,\n" +
                        "맑은바다의 소중함에 대한 공감대를 형성하는 친환경 특화 상품")
                .interestRate(3.05) // Example: Increment interest rate for each saving
                .period(12) // Example: Increment period for each saving
                .amountLimit(1_000_000L) // Example: Increment amount limit for each saving
                .build());
        Saving save9 = savingRepository.save(Saving.builder()
                .name("KB Young Youth 적금")
                .savingComment("자녀가 성년이 될 때까지 장기거래가 가능하며, 어린이/청소년을 위한 무료 보험가입서비스를 제공하는 적금")
                .interestRate(3.65) // Example: Increment interest rate for each saving
                .period(12) // Example: Increment period for each saving
                .amountLimit(3_000_000L) // Example: Increment amount limit for each saving
                .build());
        Saving save10 = savingRepository.save(Saving.builder()
                .name("KB상호부금(자유적립식)")
                .savingComment("목돈을 마련하는 국민은행의 대표 적립식예금")
                .interestRate(3.55) // Example: Increment interest rate for each saving
                .period(36) // Example: Increment period for each saving
                .amountLimit(5_000_000L) // Example: Increment amount limit for each saving
                .build());

        installmentSavingRepository.save(InstallmentSaving.builder()
                .maturityDate(LocalDateTime.now().plusMonths(save1.getPeriod()))
                .done(false)
                .totalAmount(0L) // Example: Set a fixed total amount
                .savingDate(5) // Example: Set a fixed saving date
                .savingAmount(100000L) // Example: Set a fixed saving amount
                .saving(save1)
                .groupWallet(groupWallet1)
                .build());

        installmentSavingRepository.save(InstallmentSaving.builder()
                .maturityDate(LocalDateTime.now().plusMonths(save2.getPeriod()))
                .done(false)
                .totalAmount(0L) // Example: Set a fixed total amount
                .savingDate(1) // Example: Set a fixed saving date
                .savingAmount(200000L) // Example: Set a fixed saving amount
                .saving(save2)
                .groupWallet(groupWallet2)
                .build());

        Address bankAddr1 = new Address("서울특별시", "어쩌구저쩌구1길 99", "11111");
        Address bankAddr2 = new Address("서울특별시", "어쩌구저쩌구2길 99", "22222");
        Address bankAddr3 = new Address("서울특별시", "어쩌구저쩌구2길 99", "33333");

        Bank bank1 = bankRepository.save(Bank.builder()
                .name("국민은행 시청점")
                .address(bankAddr1)
                .build());

        Bank bank2 = bankRepository.save(Bank.builder()
                .name("국민은행 강남점")
                .address(bankAddr2)
                .build());

        Bank bank3 = bankRepository.save(Bank.builder()
                .name("국민은행 시흥점")
                .address(bankAddr3)
                .build());

        offlineReceiptRepository.save(OfflineReceipt.builder()
                .currencyCode(CurrencyCode.USD)
                .amount(500L)
                .receiptState(ReceiptState.WAITING)
                .bank(bank1)
                .personalWallet(null)
                .groupWallet(groupWallet1)
                .build());

        offlineReceiptRepository.save(OfflineReceipt.builder()
                .currencyCode(CurrencyCode.JPY)
                .amount(5000L)
                .receiptState(ReceiptState.COMPLETE)
                .bank(bank2)
                .personalWallet(null)
                .groupWallet(groupWallet1)
                .build());
    }

}
