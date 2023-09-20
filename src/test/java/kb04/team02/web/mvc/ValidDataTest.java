package kb04.team02.web.mvc;

import kb04.team02.web.mvc.common.entity.*;
import kb04.team02.web.mvc.exchange.entity.Bank;
import kb04.team02.web.mvc.exchange.entity.ExchangeRate;
import kb04.team02.web.mvc.group.entity.*;
import kb04.team02.web.mvc.member.entity.*;

import kb04.team02.web.mvc.exchange.repository.BankRepository;
import kb04.team02.web.mvc.exchange.repository.ExchangeRateRepository;
import kb04.team02.web.mvc.exchange.repository.OfflineReceiptRepository;
import kb04.team02.web.mvc.group.repository.*;
import kb04.team02.web.mvc.member.repository.MemberRepository;
import kb04.team02.web.mvc.mypage.entity.CardIssuance;
import kb04.team02.web.mvc.mypage.entity.CardState;
import kb04.team02.web.mvc.mypage.repository.CardIssuanceRepository;
import kb04.team02.web.mvc.personal.entity.*;
import kb04.team02.web.mvc.personal.repository.*;
import kb04.team02.web.mvc.saving.entity.InstallmentSaving;
import kb04.team02.web.mvc.saving.entity.Saving;
import kb04.team02.web.mvc.saving.entity.SavingHistory;
import kb04.team02.web.mvc.saving.repository.InstallmentSavingRepository;
import kb04.team02.web.mvc.saving.repository.SavingHistoryRepository;
import kb04.team02.web.mvc.saving.repository.SavingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
@Commit
public class ValidDataTest {

    // Service Dependency


    // Repository Dependency
    // 회원 관련 의존성
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CardIssuanceRepository cardIssuanceRepository;

    // 모임지갑 관련 의존성
    @Autowired
    private ParticipationRepository participationRepository;
    @Autowired
    private GroupWalletRespository groupWalletRespository;
    @Autowired
    private GroupWalletTransferRepository groupWalletTransferRepository;
    @Autowired
    private GroupWalletExchangeRepository groupWalletExchangeRepository;
    @Autowired
    private GroupWalletPaymentRepository groupWalletPaymentRepository;
    @Autowired
    private GroupWalletForeignCurrencyBalanceRepository groupWalletForeignCurrencyBalanceRepository;
    @Autowired
    private DuePaymentRepository duePaymentRepository;

    // 개인지갑 관련 의존성
    @Autowired
    private PersonalWalletRepository personalWalletRepository;
    @Autowired
    private PersonalWalletTransferRepository personalWalletTransferRepository;
    @Autowired
    private PersonalWalletExchangeRepository personalWalletExchangeRepository;
    @Autowired
    private PersonalWalletPaymentRepository personalWalletPaymentRepository;
    @Autowired
    private PersonalWalletForeignCurrencyBalanceRepository personalForeignCurrencyBalanceRepository;


    // 적금 관련 의존성
    @Autowired
    private SavingRepository savingRepository;
    @Autowired
    private InstallmentSavingRepository installmentSavingRepository;
    @Autowired
    private SavingHistoryRepository savingHistoryRepository;

    // 은행 관련 의존성
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private OfflineReceiptRepository offlineReceiptRepository;
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Test
    @DisplayName("init")
    public void init() throws Exception {

        //======================== 회원 관련 Data 생성 ========================//
        
        /**
         * 회원 Data 생성
         * 총 회원: 6명 (김진형, 최예빈, 김철, 정지원, 김현지, 염혜정)
         * 유효회원: 2명 (김진형, 최예빈)
         * 아이디: 한글 이름 그대로 영문 타자
         *      (김진형 => rla{김}wls{진}gud{형})
         *      (최예빈 => chl{최}dp{에}qls{빈})
         * 로그인 비밀번호: qwer 통일
         * 결재 비밀번호: 1234 통일
         * 전화번호, 계좌는 임의생성
         */
        Address address = new Address("서울특별시", "강남구 선릉로 428", "06159");

        Member member1 = Member.builder()
                .id("rlawlsgud")
                .password("qwer")
                .name("김진형")
                .address(address)
                .phoneNumber("010-3525-1253")
                .email("rlawlsgud@example.com")
                .payPassword("1234")
                .bankAccount("110-232-532123")
                .build();

        Member member2 = Member.builder()
                .id("chldpqls")
                .password("qwer")
                .name("최예빈")
                .address(address)
                .phoneNumber("010-1353-9085")
                .email("chldpqls@example.com")
                .payPassword("1234")
                .bankAccount("110-232-532124")
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

        /**
         * 카드 Data 생성
         * 유효카드: 2개 (김진형, 최예빈)
         * 카드번호: 유효카드 2개에 대해 랜덤으로 생성 (그 외의 데이터는 동일한 숫자 나열)
         */
        CardIssuance cardIssuance1 = CardIssuance.builder().cardNumber("4311-5214-2351-5232").cardState(CardState.OK).member(member1).build();
        CardIssuance cardIssuance2 = CardIssuance.builder().cardNumber("4312-5114-2151-5532").cardState(CardState.OK).member(member2).build();
        CardIssuance cardIssuance3 = CardIssuance.builder().cardNumber("3333-3333-3333-3333").cardState(CardState.OK).member(member2).build();
        CardIssuance cardIssuance4 = CardIssuance.builder().cardNumber("4444-4444-4444-4444").cardState(CardState.OK).member(member2).build();
        CardIssuance cardIssuance5 = CardIssuance.builder().cardNumber("5555-5555-5555-5555").cardState(CardState.OK).member(member2).build();
        CardIssuance cardIssuance6 = CardIssuance.builder().cardNumber("6666-6666-6666-6666").cardState(CardState.OK).member(member2).build();
        cardIssuanceRepository.save(cardIssuance1);
        cardIssuanceRepository.save(cardIssuance2);
        cardIssuanceRepository.save(cardIssuance3);
        cardIssuanceRepository.save(cardIssuance4);
        cardIssuanceRepository.save(cardIssuance5);
        cardIssuanceRepository.save(cardIssuance6);

        //======================== 개인지갑 Data 생성 ========================//
        
        /**
         * 개인지갑 Data 생성
         * 유효개인지갑: 2개 (김진형, 최예빈)
         * 초기 원화: 937,100원 동일 (그 외의 데이터는 0원)
         */
        PersonalWallet personalWallet1 = PersonalWallet.builder().balance(937100L).member(member1).build();
        PersonalWallet personalWallet2 = PersonalWallet.builder().balance(937100L).member(member2).build();
        PersonalWallet personalWallet3 = PersonalWallet.builder().balance(0L).member(member3).build();
        PersonalWallet personalWallet4 = PersonalWallet.builder().balance(0L).member(member4).build();
        PersonalWallet personalWallet5 = PersonalWallet.builder().balance(0L).member(member5).build();
        PersonalWallet personalWallet6 = PersonalWallet.builder().balance(0L).member(member6).build();
        personalWalletRepository.save(personalWallet1);
        personalWalletRepository.save(personalWallet2);
        personalWalletRepository.save(personalWallet3);
        personalWalletRepository.save(personalWallet4);
        personalWalletRepository.save(personalWallet5);
        personalWalletRepository.save(personalWallet6);



        /**
         * 개인지갑 환전내역 Data 생성 1 (김진형)
         * 환전 내역: 2개 (KRW=>USD, KRW=>JPY로 각 한번)
         * 이체 후 최종 잔액:
         *      KRW: 987,1000
         *      USD: 38
         *      JPY: 6988
         */
        PersonalWalletExchange personalWalletExchange1
                = PersonalWalletExchange.builder()
                .personalWallet(personalWallet1)
                .sellCurrencyCode(CurrencyCode.KRW)
                .sellAmount(50000L)
                .afterSellBalance(1050000L)
                .buyCurrencyCode(CurrencyCode.USD)
                .buyAmount(38L)
                .afterBuyBalance(38L)
                .exchangeRate(1300.0)
                .build();
        PersonalWalletExchange personalWalletExchange2
                = PersonalWalletExchange.builder()
                .personalWallet(personalWallet1)
                .sellCurrencyCode(CurrencyCode.KRW)
                .sellAmount(62900L)
                .afterSellBalance(987100L)
                .buyCurrencyCode(CurrencyCode.JPY)
                .buyAmount(6988L)
                .afterBuyBalance(6988L)
                .exchangeRate(9.0)
                .build();

        /**
         * 개인지갑 환전내역 Data 생성 2 (최예빈)
         * 환전 내역: 2개 (KRW=>USD, KRW=>JPY로 각 한번)
         * 이체 후 최종 잔액:
         *      KRW: 987,1000
         *      USD: 61
         *      JPY: 3655
         */
        PersonalWalletExchange personalWalletExchange3
                = PersonalWalletExchange.builder()
                .personalWallet(personalWallet2)
                .sellCurrencyCode(CurrencyCode.KRW)
                .sellAmount(80000L)
                .afterSellBalance(1020000L)
                .buyCurrencyCode(CurrencyCode.USD)
                .buyAmount(61L)
                .afterBuyBalance(61L)
                .exchangeRate(1300.0)
                .build();
        PersonalWalletExchange personalWalletExchange4
                = PersonalWalletExchange.builder()
                .personalWallet(personalWallet2)
                .sellCurrencyCode(CurrencyCode.KRW)
                .sellAmount(32900L)
                .afterSellBalance(987100L)
                .buyCurrencyCode(CurrencyCode.JPY)
                .buyAmount(3655L)
                .afterBuyBalance(3655L)
                .exchangeRate(9.0)
                .build();
        personalWalletExchangeRepository.save(personalWalletExchange1);
        personalWalletExchangeRepository.save(personalWalletExchange2);
        personalWalletExchangeRepository.save(personalWalletExchange3);
        personalWalletExchangeRepository.save(personalWalletExchange4);

        /**
         * 결제 Data 생성
         * 결제 내역: 각 1개 (음식점 결제 50,000원)
         * 결제 후 잔액: 937,100원
         */
        // 김진형 결제 내역
        PersonalWalletPayment personalWalletPayment1 =
                PersonalWalletPayment.builder()
                        .personalWallet(personalWallet1)
                        .currencyCode(CurrencyCode.KRW)
                        .paymentType(PaymentType.OK)
                        .paymentPlace("소공 순대국")
                        .paymentCategory(PaymentCategoryType.RESTAURANT)
                        .amount(50000L)
                        .afterPayBalance(937100L)
                        .build();
        // 최예빈 결제 내역
        PersonalWalletPayment personalWalletPayment2 =
                PersonalWalletPayment.builder()
                        .personalWallet(personalWallet2)
                        .currencyCode(CurrencyCode.KRW)
                        .paymentType(PaymentType.OK)
                        .paymentPlace("맛자랑")
                        .paymentCategory(PaymentCategoryType.RESTAURANT)
                        .amount(50000L)
                        .afterPayBalance(937100L)
                        .build();
        personalWalletPaymentRepository.save(personalWalletPayment1);
        personalWalletPaymentRepository.save(personalWalletPayment2);

        /**
         * 외화 잔액 Data 생성
         * 기존 잔액과 동일
         */
        PersonalWalletForeignCurrencyBalance personalWalletForeignCurrencyBalance1
                = PersonalWalletForeignCurrencyBalance.builder()
                .personalWallet(personalWallet1)
                .currencyCode(CurrencyCode.USD)
                .balance(38L)
                .build();

        PersonalWalletForeignCurrencyBalance personalWalletForeignCurrencyBalance2
                = PersonalWalletForeignCurrencyBalance.builder()
                .personalWallet(personalWallet1)
                .currencyCode(CurrencyCode.JPY)
                .balance(6988L)
                .build();

        PersonalWalletForeignCurrencyBalance personalWalletForeignCurrencyBalance3
                = PersonalWalletForeignCurrencyBalance.builder()
                .personalWallet(personalWallet1)
                .currencyCode(CurrencyCode.USD)
                .balance(61L)
                .build();

        PersonalWalletForeignCurrencyBalance personalWalletForeignCurrencyBalance4
                = PersonalWalletForeignCurrencyBalance.builder()
                .personalWallet(personalWallet1)
                .currencyCode(CurrencyCode.JPY)
                .balance(3655L)
                .build();
        personalForeignCurrencyBalanceRepository.save(personalWalletForeignCurrencyBalance1);
        personalForeignCurrencyBalanceRepository.save(personalWalletForeignCurrencyBalance2);
        personalForeignCurrencyBalanceRepository.save(personalWalletForeignCurrencyBalance3);
        personalForeignCurrencyBalanceRepository.save(personalWalletForeignCurrencyBalance4);

        //======================== 모임지갑 Data 생성 ========================//

        /**
         * 모임지갑 Data생성
         * 모임지갑: 2개 (미국, 일본 여행)
         * 모임장은 김진형, 최예빈
         *
         * 모임지갑 1 => 규칙 O
         * 모임지갑 2 => 규칙 X
         */
        GroupWallet groupWallet1 = GroupWallet.builder()
                .nickname("취업기념 미국여행")
                .dueCondition(true)
                .balance(250000L)
                .dueAccumulation(800000L)
                .dueDate(1)
                .due(200000L)
                .member(member1)
                .build();

        GroupWallet groupWallet2 = GroupWallet.builder()
                .nickname("일본 라면 투어")
                .dueCondition(false)
                .balance(500000L)
                .dueAccumulation(0L)
                .dueDate(0)
                .due(0L)
                .member(member2)
                .build();
        groupWalletRespository.save(groupWallet1);
        groupWalletRespository.save(groupWallet2);

        /**
         * 참여 Data 생성
         * 모임지갑 1(미국): 6명
         *      - 전원 참여 중
         *      - 모임장: 김진형
         *      - 공동모임장: 최예빈
         *
         * 모임지갑 2(일본): 4명
         *      - 김진형, 최예빈, 김철, 정지원
         *      - 모임장: 최예빈
         *      - 공동모임장: X
         */
        // 모임지갑 1(미국 여행)
        Participation participation1 = Participation.builder()
                .participationState(ParticipationState.PARTICIPATED)
                .role(Role.CHAIRMAN)
                .groupWallet(groupWallet1)
                .memberId(member1.getMemberId())
                .build();
        Participation participation2 = Participation.builder()
                .participationState(ParticipationState.PARTICIPATED)
                .role(Role.CO_CHAIRMAN)
                .groupWallet(groupWallet1)
                .memberId(member2.getMemberId())
                .build();
        Participation participation3 = Participation.builder()
                .participationState(ParticipationState.PARTICIPATED)
                .role(Role.GENERAL)
                .groupWallet(groupWallet1)
                .memberId(member3.getMemberId())
                .build();
        Participation participation4 = Participation.builder()
                .participationState(ParticipationState.PARTICIPATED)
                .role(Role.GENERAL)
                .groupWallet(groupWallet1)
                .memberId(member4.getMemberId())
                .build();
        Participation participation5 = Participation.builder()
                .participationState(ParticipationState.PARTICIPATED)
                .role(Role.GENERAL)
                .groupWallet(groupWallet1)
                .memberId(member5.getMemberId())
                .build();
        Participation participation6 = Participation.builder()
                .participationState(ParticipationState.PARTICIPATED)
                .role(Role.GENERAL)
                .groupWallet(groupWallet1)
                .memberId(member6.getMemberId())
                .build();

        // 모임지갑 2(일본 여행)
        Participation participation7 = Participation.builder()
                .participationState(ParticipationState.PARTICIPATED)
                .role(Role.CHAIRMAN)
                .groupWallet(groupWallet2)
                .memberId(member2.getMemberId())
                .build();
        Participation participation8 = Participation.builder()
                .participationState(ParticipationState.PARTICIPATED)
                .role(Role.GENERAL)
                .groupWallet(groupWallet2)
                .memberId(member1.getMemberId())
                .build();
        Participation participation9 = Participation.builder()
                .participationState(ParticipationState.PARTICIPATED)
                .role(Role.GENERAL)
                .groupWallet(groupWallet2)
                .memberId(member3.getMemberId())
                .build();
        Participation participation10 = Participation.builder()
                .participationState(ParticipationState.PARTICIPATED)
                .role(Role.GENERAL)
                .groupWallet(groupWallet2)
                .memberId(member4.getMemberId())
                .build();
        participationRepository.save(participation1);
        participationRepository.save(participation2);
        participationRepository.save(participation3);
        participationRepository.save(participation4);
        participationRepository.save(participation5);
        participationRepository.save(participation6);
        participationRepository.save(participation7);
        participationRepository.save(participation8);
        participationRepository.save(participation9);
        participationRepository.save(participation10);

        /**
         * 회비 납부내역 Data 추가
         * 회비가 존재하는 모임지갑 1에 대해서 생성
         * 이번 달 총 6명의 모임원 중 4명이 납부 완료, 2명은 미납
         */
        DuePayment duePayment1 = DuePayment.builder()
                .member(member1)
                .groupWallet(groupWallet1)
                .build();
        DuePayment duePayment2 = DuePayment.builder()
                .member(member2)
                .groupWallet(groupWallet1)
                .build();
        DuePayment duePayment3 = DuePayment.builder()
                .member(member3)
                .groupWallet(groupWallet1)
                .build();
        DuePayment duePayment4 = DuePayment.builder()
                .member(member4)
                .groupWallet(groupWallet1)
                .build();
        duePaymentRepository.save(duePayment1);
        duePaymentRepository.save(duePayment2);
        duePaymentRepository.save(duePayment3);
        duePaymentRepository.save(duePayment4);

        /**
         * 모임지갑 이체 내역 Data 생성
         * 4명의 회비 이체 내역
         */
        GroupWalletTransfer groupWalletTransfer1 = GroupWalletTransfer.builder()
                .groupWallet(groupWallet1)
                .transferType(TransferType.DEPOSIT)
                .fromType(TargetType.PERSONAL_WALLET)
                .toType(TargetType.GROUP_WALLET)
                .src(member1.getName())
                .dest(groupWallet1.getNickname())
                .amount(200000L)
                .afterBalance(200000L)
                .build();
        GroupWalletTransfer groupWalletTransfer2 = GroupWalletTransfer.builder()
                .groupWallet(groupWallet1)
                .transferType(TransferType.DEPOSIT)
                .fromType(TargetType.PERSONAL_WALLET)
                .toType(TargetType.GROUP_WALLET)
                .src(member2.getName())
                .dest(groupWallet1.getNickname())
                .amount(200000L)
                .afterBalance(400000L)
                .build();
        GroupWalletTransfer groupWalletTransfer3 = GroupWalletTransfer.builder()
                .groupWallet(groupWallet1)
                .transferType(TransferType.DEPOSIT)
                .fromType(TargetType.PERSONAL_WALLET)
                .toType(TargetType.GROUP_WALLET)
                .src(member3.getName())
                .dest(groupWallet1.getNickname())
                .amount(200000L)
                .afterBalance(600000L)
                .build();
        GroupWalletTransfer groupWalletTransfer4 = GroupWalletTransfer.builder()
                .groupWallet(groupWallet1)
                .transferType(TransferType.DEPOSIT)
                .fromType(TargetType.PERSONAL_WALLET)
                .toType(TargetType.GROUP_WALLET)
                .src(member4.getName())
                .dest(groupWallet1.getNickname())
                .amount(200000L)
                .afterBalance(800000L)
                .build();

        GroupWalletTransfer groupWalletTransfer6 = GroupWalletTransfer.builder()
                .groupWallet(groupWallet1)
                .transferType(TransferType.WITHDRAW)
                .fromType(TargetType.GROUP_WALLET)
                .toType(TargetType.ACCOUNT)
                .src(groupWallet1.getNickname())
                .dest("KB적금")
                .amount(100000L)
                .afterBalance(700000L)
                .build();
        
        GroupWalletTransfer groupWalletTransfer5 = GroupWalletTransfer.builder()
                .groupWallet(groupWallet2)
                .transferType(TransferType.DEPOSIT)
                .fromType(TargetType.PERSONAL_WALLET)
                .toType(TargetType.GROUP_WALLET)
                .src(member3.getName())
                .dest(groupWallet1.getNickname())
                .amount(1000000L)
                .afterBalance(1000000L)
                .build();

        groupWalletTransferRepository.save(groupWalletTransfer1);
        groupWalletTransferRepository.save(groupWalletTransfer2);
        groupWalletTransferRepository.save(groupWalletTransfer3);
        groupWalletTransferRepository.save(groupWalletTransfer4);
        groupWalletTransferRepository.save(groupWalletTransfer5);




        /**
         * 개인지갑 이체내역 Data 생성
         * 이체 내역: 3개 (각 채우기 2번, 꺼내기 1번)
         * 이체 후 최종 잔액: 1,100,000원
         */
        // 김진형의 개인지갑
        PersonalWalletTransfer personalWalletTransfer1
                = PersonalWalletTransfer.builder()
                .personalWallet(personalWallet1)
                .transferType(TransferType.DEPOSIT)
                .fromType(TargetType.ACCOUNT)
                .toType(TargetType.PERSONAL_WALLET)
                .src(personalWallet1.getMember().getBankAccount())
                .dest(personalWallet1.getMember().getName())
                .amount(500000L)
                .afterBalance(500000L)
                .currencyCode(CurrencyCode.KRW)
                .build();

        PersonalWalletTransfer personalWalletTransfer2
                = PersonalWalletTransfer.builder()
                .personalWallet(personalWallet1)
                .transferType(TransferType.WITHDRAW)
                .fromType(TargetType.PERSONAL_WALLET)
                .toType(TargetType.ACCOUNT)
                .src(personalWallet1.getMember().getName())
                .dest(personalWallet1.getMember().getBankAccount())
                .amount(150000L)
                .afterBalance(350000L)
                .currencyCode(CurrencyCode.KRW)
                .build();

        PersonalWalletTransfer personalWalletTransfer3
                = PersonalWalletTransfer.builder()
                .personalWallet(personalWallet1)
                .transferType(TransferType.DEPOSIT)
                .fromType(TargetType.ACCOUNT)
                .toType(TargetType.PERSONAL_WALLET)
                .src(personalWallet1.getMember().getBankAccount())
                .dest(personalWallet1.getMember().getName())
                .amount(950000L)
                .afterBalance(1300000L)
                .currencyCode(CurrencyCode.KRW)
                .build();

        PersonalWalletTransfer personalWalletTransfer7
                = PersonalWalletTransfer.builder()
                .personalWallet(personalWallet1)
                .transferType(TransferType.WITHDRAW)
                .fromType(TargetType.PERSONAL_WALLET)
                .toType(TargetType.GROUP_WALLET)
                .src(personalWallet1.getMember().getName())
                .dest(groupWallet1.getNickname())
                .amount(200000L)
                .afterBalance(1100000L)
                .currencyCode(CurrencyCode.KRW)
                .build();

        // 최예빈의 개인지갑
        PersonalWalletTransfer personalWalletTransfer4
                = PersonalWalletTransfer.builder()
                .personalWallet(personalWallet2)
                .transferType(TransferType.DEPOSIT)
                .fromType(TargetType.ACCOUNT)
                .toType(TargetType.PERSONAL_WALLET)
                .src(personalWallet2.getMember().getBankAccount())
                .dest(personalWallet2.getMember().getName())
                .amount(500000L)
                .afterBalance(500000L)
                .currencyCode(CurrencyCode.KRW)
                .build();

        PersonalWalletTransfer personalWalletTransfer5
                = PersonalWalletTransfer.builder()
                .personalWallet(personalWallet2)
                .transferType(TransferType.WITHDRAW)
                .fromType(TargetType.PERSONAL_WALLET)
                .toType(TargetType.ACCOUNT)
                .src(personalWallet2.getMember().getName())
                .dest(personalWallet2.getMember().getBankAccount())
                .amount(150000L)
                .afterBalance(350000L)
                .currencyCode(CurrencyCode.KRW)
                .build();

        PersonalWalletTransfer personalWalletTransfer6
                = PersonalWalletTransfer.builder()
                .personalWallet(personalWallet2)
                .transferType(TransferType.DEPOSIT)
                .fromType(TargetType.ACCOUNT)
                .toType(TargetType.PERSONAL_WALLET)
                .src(personalWallet2.getMember().getBankAccount())
                .dest(personalWallet2.getMember().getName())
                .amount(950000L)
                .afterBalance(1300000L)
                .currencyCode(CurrencyCode.KRW)
                .build();

        PersonalWalletTransfer personalWalletTransfer8
                = PersonalWalletTransfer.builder()
                .personalWallet(personalWallet2)
                .transferType(TransferType.WITHDRAW)
                .fromType(TargetType.PERSONAL_WALLET)
                .toType(TargetType.GROUP_WALLET)
                .src(personalWallet2.getMember().getName())
                .dest(groupWallet1.getNickname())
                .amount(200000L)
                .afterBalance(1100000L)
                .currencyCode(CurrencyCode.KRW)
                .build();
        personalWalletTransferRepository.save(personalWalletTransfer1);
        personalWalletTransferRepository.save(personalWalletTransfer2);
        personalWalletTransferRepository.save(personalWalletTransfer3);
        personalWalletTransferRepository.save(personalWalletTransfer4);
        personalWalletTransferRepository.save(personalWalletTransfer5);
        personalWalletTransferRepository.save(personalWalletTransfer6);
        personalWalletTransferRepository.save(personalWalletTransfer7);
        personalWalletTransferRepository.save(personalWalletTransfer8);

        GroupWalletExchange groupWalletExchange1 = GroupWalletExchange.builder()
                .groupWallet(groupWallet1)
                .sellCurrencyCode(CurrencyCode.KRW)
                .sellAmount(400000L)
                .afterSellBalance(400000L)
                .buyCurrencyCode(CurrencyCode.USD)
                .buyAmount(320L)
                .afterBuyBalance(320L)
                .exchangeRate(1250.0)
                .build();
        GroupWalletExchange groupWalletExchange2 = GroupWalletExchange.builder()
                .groupWallet(groupWallet1)
                .sellCurrencyCode(CurrencyCode.KRW)
                .sellAmount(500000L)
                .afterSellBalance(500000L)
                .buyCurrencyCode(CurrencyCode.JPY)
                .buyAmount(58823L)
                .afterBuyBalance(58823L)
                .exchangeRate(8.5)
                .build();
        groupWalletExchangeRepository.save(groupWalletExchange1);
        groupWalletExchangeRepository.save(groupWalletExchange2);

        GroupWalletPayment groupWalletPayment1 = GroupWalletPayment.builder()
                .currencyCode(CurrencyCode.KRW)
                .paymentType(PaymentType.OK)
                .paymentPlace("바나프레소 선릉 위워크점")
                .paymentCategory(PaymentCategoryType.RESTAURANT)
                .amount(50000L)
                .afterPayBalance(250000L)
                .build();
        groupWalletPaymentRepository.save(groupWalletPayment1);

        GroupWalletForeignCurrencyBalance groupWalletForeignCurrencyBalance1 =
                GroupWalletForeignCurrencyBalance.builder()
                        .groupWallet(groupWallet1)
                        .currencyCode(CurrencyCode.USD)
                        .balance(320L)
                        .build();

        GroupWalletForeignCurrencyBalance groupWalletForeignCurrencyBalance2 =
                GroupWalletForeignCurrencyBalance.builder()
                        .groupWallet(groupWallet1)
                        .currencyCode(CurrencyCode.JPY)
                        .balance(0L)
                        .build();

        GroupWalletForeignCurrencyBalance groupWalletForeignCurrencyBalance3 =
                GroupWalletForeignCurrencyBalance.builder()
                        .groupWallet(groupWallet2)
                        .currencyCode(CurrencyCode.USD)
                        .balance(0L)
                        .build();

        GroupWalletForeignCurrencyBalance groupWalletForeignCurrencyBalance4 =
                GroupWalletForeignCurrencyBalance.builder()
                        .groupWallet(groupWallet2)
                        .currencyCode(CurrencyCode.JPY)
                        .balance(58823L)
                        .build();
        groupWalletForeignCurrencyBalanceRepository.save(groupWalletForeignCurrencyBalance1);
        groupWalletForeignCurrencyBalanceRepository.save(groupWalletForeignCurrencyBalance2);
        groupWalletForeignCurrencyBalanceRepository.save(groupWalletForeignCurrencyBalance3);
        groupWalletForeignCurrencyBalanceRepository.save(groupWalletForeignCurrencyBalance4);


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
                .interestRate(6.0) // Example: Increment interest rate for each saving
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
                .interestRate(10.0) // Example: Increment interest rate for each saving
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

        InstallmentSaving installmentSaving1 = InstallmentSaving.builder()
                .maturityDate(LocalDateTime.of(2023, 12, 7, 0, 5))
                .done(false)
                .totalAmount(600_0000L) // Example: Set a fixed total amount
                .savingDate(10) // Example: Set a fixed saving date
                .savingAmount(100_000L) // Example: Set a fixed saving amount
                .saving(save7)
                .groupWallet(groupWallet1)
                .build();
        installmentSavingRepository.save(installmentSaving1);

        SavingHistory savingHistory1 = SavingHistory.builder()
                .insertDate(LocalDateTime.of(2023, 9, 10, 0, 5))
                .amount(100000L)
                .accumulatedAmount(100_000L)
                .installmentSaving(installmentSaving1)
                .build();
        savingHistoryRepository.save(savingHistory1);

        Address bankAddr1 = new Address("서울특별시", "영등포로 194", "07301");
        Address bankAddr2 = new Address("경기 고양시", "중앙로 1167", "10414");
        Address bankAddr3 = new Address("서울특별시", "신림로 137", "08812");
        Address bankAddr4 = new Address("경기 수원시", "덕영대로 924", "16622");
        Address bankAddr5 = new Address("경기 시흥시", "장현능곡로 178", "14995");
        Address bankAddr6 = new Address("경기 안양시", "시민대로 196", "14072");

        Bank bank1 = bankRepository.save(Bank.builder()
                .name("KB 국민은행 영등포")
                .address(bankAddr1)
                .build());

        Bank bank2 = bankRepository.save(Bank.builder()
                .name("KB국민은행 마두역종합금융센터")
                .address(bankAddr2)
                .build());

        Bank bank3 = bankRepository.save(Bank.builder()
                .name("KB국민은행 신림남부")
                .address(bankAddr3)
                .build());

        Bank bank4 = bankRepository.save(Bank.builder()
                .name("KB국민은행 수원역")
                .address(bankAddr4)
                .build());

        Bank bank5 = bankRepository.save(Bank.builder()
                .name("KB국민은행 시흥능곡")
                .address(bankAddr5)
                .build());

        Bank bank6 = bankRepository.save(Bank.builder()
                .name("KB국민은행 평촌범계종합금융센터")
                .address(bankAddr6)
                .build());
    }

    @Test
    @DisplayName("insertRate")
    public void insertRate() throws Exception {
        //given
        //반환용 리스트
        List<List<String>> ret = new ArrayList<List<String>>();
        BufferedReader br = null;

        try {
            br = Files.newBufferedReader(Paths.get("src/main/java/kb04/team02/web/mvc/exchange/service/result.csv"));
            //Charset.forName("UTF-8");
            String line = "";
            int cnt = 0;
            LocalDate nowUSD = LocalDate.now();
            LocalDate nowJPY = LocalDate.now();
            System.out.println("nowUSD = " + nowUSD);
            System.out.println("nowJPY = " + nowJPY);
            while ((line = br.readLine()) != null) {
                //CSV 1행을 저장하는 리스트
                List<String> tmpList = new ArrayList<String>();
                String array[] = line.split(",");
                //배열에서 리스트 반환
                tmpList = Arrays.asList(array);

                if (tmpList.get(1).equals("JPY") || tmpList.get(1).equals("USD")) {
                    cnt++;
                    System.out.println(tmpList);
                    ret.add(tmpList);
                }
            }
            System.out.println("cnt/2 = " + cnt / 2);

            for (int i = cnt - 1; i >= 0; --i) {
                List<String> curr = ret.get(i);
                if (curr.get(1).equals("USD")) {
                    System.out.println(curr.get(2));
                    System.out.println(curr.get(2).replaceAll(",", ""));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
                    System.out.println("curr.get(0) = " + curr.get(0));
                    LocalDateTime time = LocalDateTime.parse(curr.get(0), formatter);
                    System.out.println("time = " + time);
                    Double tradingBaseRate = Double.parseDouble(curr.get(2).replaceAll(",", ""));
                    Double telegraphic_transfer_buying_rate = Double.parseDouble(curr.get(3).replaceAll(",", ""));
                    Double telegraphic_transfer_selling_rate = Double.parseDouble(curr.get(4).replaceAll(",", ""));
                    Double buying_rate = Double.parseDouble(curr.get(5).replaceAll(",", ""));
                    Double selling_rate = Double.parseDouble(curr.get(6).replaceAll(",", ""));
                    exchangeRateRepository.save(ExchangeRate.builder()
                            .insertDate(time)
                            .currencyCode(CurrencyCode.USD)
                            .tradingBaseRate(tradingBaseRate)
                            .telegraphicTransferBuyingRate(telegraphic_transfer_buying_rate)
                            .telegraphicTransferSellingRate(telegraphic_transfer_selling_rate)
                            .buyingRate(buying_rate)
                            .sellingRate(selling_rate)
                            .build());
//                    nowUSD = nowUSD.minusDays(1);
                }
                if (curr.get(1).equals("JPY")) {
                    System.out.println(curr.get(2));
                    System.out.println(curr.get(2).replaceAll(",", ""));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
                    System.out.println("curr.get(0) = " + curr.get(0));
                    LocalDateTime time = LocalDateTime.parse(curr.get(0), formatter);
                    Double tradingBaseRate = Double.parseDouble(curr.get(2).replaceAll(",", ""));
                    Double telegraphic_transfer_buying_rate = Double.parseDouble(curr.get(3).replaceAll(",", ""));
                    Double telegraphic_transfer_selling_rate = Double.parseDouble(curr.get(4).replaceAll(",", ""));
                    Double buying_rate = Double.parseDouble(curr.get(5).replaceAll(",", ""));
                    Double selling_rate = Double.parseDouble(curr.get(6).replaceAll(",", ""));
                    exchangeRateRepository.save(ExchangeRate.builder()
                            .insertDate(time)
                            .currencyCode(CurrencyCode.JPY)
                            .tradingBaseRate((tradingBaseRate * 100) / 100.0)
                            .telegraphicTransferBuyingRate((telegraphic_transfer_buying_rate) / 100.0)
                            .telegraphicTransferSellingRate((telegraphic_transfer_selling_rate) / 100.0)
                            .buyingRate(buying_rate)
                            .sellingRate(selling_rate)
                            .build());
//                    nowJPY = nowJPY.minusDays(1);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //when

        //then

    }
}
