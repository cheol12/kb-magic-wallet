//
//package kb04.team02.web.mvc;
//
//import kb04.team02.web.mvc.exchange.entity.Bank;
//import kb04.team02.web.mvc.exchange.entity.OfflineReceipt;
//import kb04.team02.web.mvc.exchange.entity.ReceiptState;
//import kb04.team02.web.mvc.mypage.entity.CardIssuance;
//import kb04.team02.web.mvc.mypage.entity.CardState;
//import kb04.team02.web.mvc.common.entity.CurrencyCode;
//import kb04.team02.web.mvc.member.entity.Address;
//import kb04.team02.web.mvc.member.entity.Member;
//import kb04.team02.web.mvc.member.entity.Role;
//import kb04.team02.web.mvc.saving.entity.InstallmentSaving;
//import kb04.team02.web.mvc.saving.entity.Saving;
//import kb04.team02.web.mvc.saving.entity.SavingHistory;
//import kb04.team02.web.mvc.domain.wallet.common.*;
//import kb04.team02.web.mvc.domain.wallet.group.*;
//import kb04.team02.web.mvc.domain.wallet.personal.*;
//import kb04.team02.web.mvc.exchange.repository.BankRepository;
//import kb04.team02.web.mvc.exchange.repository.OfflineReceiptRepository;
//import kb04.team02.web.mvc.mypage.repository.CardIssuanceRepository;
//import kb04.team02.web.mvc.member.repository.MemberRepository;
//import kb04.team02.web.mvc.saving.repository.InstallmentSavingRepository;
//import kb04.team02.web.mvc.saving.repository.SavingHistoryRepository;
//import kb04.team02.web.mvc.saving.repository.SavingRepository;
//import kb04.team02.web.mvc.repository.wallet.group.*;
//import kb04.team02.web.mvc.repository.wallet.personal.*;
//import kb04.team02.web.mvc.group.service.GroupWalletTabService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//@SpringBootTest
//class KbFinalApplicationTests {
//
//    private static final int MEMBER_NUM = 10;
//    private static final int GROUP_WALLET_NUM = 3;
//    private static final int SAVING_NUM = 5;
//    private static final int TRANSFER_NUM = 5;
//    private static final int EXCHANGE_NUM = 5;
//    private static final int PAYMENT_NUM = 5;
//
//    // 회원 관련 의존성
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private CardIssuanceRepository cardIssuanceRepository;
//
//    // 개인지갑 관련 의존성
//    @Autowired
//    private PersonalWalletRepository personalWalletRepository;
//    @Autowired
//    private PersonalWalletTransferRepository personalWalletTransferRepository;
//    @Autowired
//    private PersonalWalletExchangeRepository personalWalletExchangeRepository;
//    @Autowired
//    private PersonalWalletPaymentRepository personalWalletPaymentRepository;
//    @Autowired
//    private PersonalWalletForeignCurrencyBalanceRepository personalWalletForeignCurrencyBalanceRepository;
//
//    // 모임지갑 관련 의존성
//    @Autowired
//    private GroupWalletRespository groupWalletRespository;
//    @Autowired
//    private ParticipationRepository participationRepository;
//    @Autowired
//    private DuePaymentRepository duePaymentRepository;
//    @Autowired
//    private GroupWalletForeignCurrencyBalanceRepository groupWalletForeignCurrencyBalanceRepository;
//    @Autowired
//    private GroupWalletTransferRepository groupWalletTransferRepository;
//    @Autowired
//    private GroupWalletExchangeRepository groupWalletExchangeRepository;
//    @Autowired
//    private GroupWalletPaymentRepository groupWalletPaymentRepository;
//
//    // 적금 관련 의존성
//    @Autowired
//    private InstallmentSavingRepository installmentSavingRepository;
//    @Autowired
//    private SavingHistoryRepository savingHistoryRepository;
//    @Autowired
//    private SavingRepository savingRepository;
//
//    // 오프라인 환전 관련 의존성
//    @Autowired
//    private BankRepository bankRepository;
//    @Autowired
//    private OfflineReceiptRepository offlineReceiptRepository;
//
//
//    @Test
//    void contextLoads() {
//        int bankCnt = 1;
//        List<Member> members = createSampleMembers();
//        Bank bank = createBanks(bankCnt);
//        bankRepository.save(bank);
//
//        int cnt = 0;
//        List<Member> chairman = new ArrayList<>();
//        for (Member member : members) {
//            // 회원 저장
//            memberRepository.save(member);
//            List<PersonalWallet> samplePersonalWallets = createSamplePersonalWallets(member);
//
//            for (PersonalWallet personalWallet : samplePersonalWallets) {
//                // 개인지갑 저장
//                personalWalletRepository.save(personalWallet);
//                List<PersonalWalletTransfer> personalWalletTransfers = createPersonalWalletTransfers(personalWallet, TRANSFER_NUM);
//                // 개인지갑 이체 내역 저장
//                personalWalletTransferRepository.saveAll(personalWalletTransfers);
//                for (int i = 0; i < EXCHANGE_NUM; ++i) {
//                    PersonalWalletExchange personalWalletExchange = createRandomPersonalWalletExchange(personalWallet);
//                    // 개인지갑 환전 내역 저장
//                    personalWalletExchangeRepository.save(personalWalletExchange);
//                }
//                for (int i = 0; i < PAYMENT_NUM; ++i) {
//                    PersonalWalletPayment personalWalletPayment = createRandomPersonalWalletPayment(personalWallet);
//                    // 개인지갑 결제내역 저장
//                    personalWalletPaymentRepository.save(personalWalletPayment);
//                }
//
//                PersonalWalletForeignCurrencyBalance foreignCurrencyBalance = createRandomPersonalWalletForeignCurrencyBalance(personalWallet);
//                personalWalletForeignCurrencyBalanceRepository.save(foreignCurrencyBalance);
//
//                OfflineReceipt offlineReceipt = createOfflineReceipt(bank, personalWallet, null);
//                offlineReceiptRepository.save(offlineReceipt);
//            }
//            if (cnt++ < 3) {
//                // 모임장이 될 회원
//                chairman.add(member);
//            }
//        }
//
//        int savingCnt = 1;
//
//        for (Member member : chairman) {
//            GroupWallet groupWallet = createSampleGroupWallets(member);
//            // 모임지갑 저장
//            groupWalletRespository.save(groupWallet);
//            Participation participation = createParticipation(member, groupWallet, ParticipationState.PARTICIPATED, Role.CHAIRMAN);
//            // 모임장 참여 정보 저장
//            participationRepository.save(participation);
//            // 모임지갑 이체내역 저장
//            List<GroupWalletTransfer> transfers = createGroupWalletTransfers(groupWallet, TRANSFER_NUM);
//            groupWalletTransferRepository.saveAll(transfers);
//            for (int i = 0; i < EXCHANGE_NUM; ++i) {
//                GroupWalletExchange groupWalletExchange = createRandomGroupWalletExchange(groupWallet);
//                // 모임지갑 환전 내역 저장
//                groupWalletExchangeRepository.save(groupWalletExchange);
//            }
//            for (int i = 0; i < PAYMENT_NUM; ++i) {
//                GroupWalletPayment groupWalletPayment = createRandomGroupWalletPayment(groupWallet);
//                // 모임지갑 결제내역 저장
//                groupWalletPaymentRepository.save(groupWalletPayment);
//            }
//            GroupWalletForeignCurrencyBalance foreignCurrencyBalance = createRandomGroupWalletForeignCurrencyBalance(groupWallet);
//            groupWalletForeignCurrencyBalanceRepository.save(foreignCurrencyBalance);
//
//            int i = 1;
//            int chairmanIdx = members.indexOf(member);
//            while (chairmanIdx + i < members.size()) {
//                Member general = members.get(chairmanIdx + i);
//                Participation generalParticipation = createParticipation(general, groupWallet, ParticipationState.PARTICIPATED, Role.GENERAL);
//                // 모임원 참여 정보 저장
//                participationRepository.save(generalParticipation);
//                DuePayment duePayment = createRandomDuePayment(general, groupWallet);
//                // 회비 납부 내역 정보 저장
//                duePaymentRepository.save(duePayment);
//                ++i;
//            }
//
//
//            Saving saving = createSavings(savingCnt++);
//            savingRepository.save(saving);
//            InstallmentSaving installmentSaving = createInstallmentSavings(saving, groupWallet);
//            installmentSavingRepository.save(installmentSaving);
//
//            List<SavingHistory> savingHistories = createSavingHistories(installmentSaving);
//            savingHistoryRepository.saveAll(savingHistories);
//
//            OfflineReceipt offlineReceipt = createOfflineReceipt(bank, null, groupWallet);
//            offlineReceiptRepository.save(offlineReceipt);
//        }
//    }
//
//
////    // 회원 생성
////    public static List<Member> createSampleMembers() {
////        List<Member> members = new ArrayList<>();
////
////        Member member = Member.builder()
////                .id("id" + i)
////                .password("password" + i)
////                .name("User " + i)
////                .address(address)
////                .phoneNumber("123-456-789" + i)
////                .email("user" + i + "@example.com")
////                .payPassword("pay" + i)
////                .bankAccount("123-456-789" + i)
////                .build();
////
////        members.add(member);
////        return members;
////    }
//
//    // 모임지갑 생성
//    // 매개변수 = 모임장
//    public static GroupWallet createSampleGroupWallets(Member member) {
//        return GroupWallet.builder()
//                .nickname("GroupWallet " + member.getMemberId())
//                .balance(10000L) // 초기 잔액 설정
//                .dueCondition(false)
//                .dueAccumulation(0L)
//                .dueDate(0)
//                .due(0L)
//                .member(member)
//                .build();
//    }
//
//    // 개인지갑 생성
//    // 매개변수 = 카드 주인
//    public static List<PersonalWallet> createSamplePersonalWallets(Member member) {
//        List<PersonalWallet> personalWallets = new ArrayList<>();
//        Random random = new Random();
//
//        long randomBalance = random.nextLong() % 10000;
//        if (randomBalance < 0) {
//            randomBalance = -randomBalance; // 음수 값 방지
//        }
//
//        for (int i = 1; i <= MEMBER_NUM; i++) {
//            PersonalWallet personalWallet = PersonalWallet.builder()
//                    .balance(10000L) // 초기 잔액 설정
//                    .member(member)
//                    .build();
//
//            personalWallets.add(personalWallet);
//        }
//
//        return personalWallets;
//    }
//
//    // 참여
//    public static Participation createParticipation(Member member, GroupWallet groupWallet, ParticipationState state, Role role) {
//        return Participation.builder()
//                .groupWallet(groupWallet)
//                .participationState(state)
//                .role(role)
//                .memberId(member.getMemberId())
//                .build();
//    }
//
//    // 모임지갑 이체 내역
//    public static List<GroupWalletTransfer> createGroupWalletTransfers(GroupWallet groupWallet, int numberOfTransfers) {
//        List<GroupWalletTransfer> groupWalletTransfers = new ArrayList<>();
//        Random random = new Random();
//
//        for (int i = 0; i < numberOfTransfers; i++) {
//            // 임의의 이체 금액 생성 (예: 1000부터 5000 사이의 임의의 값)
//            long randomAmount = (random.nextInt(4000) + 1000L);
//
//            GroupWalletTransfer groupWalletTransfer = GroupWalletTransfer.builder()
//                    .transferType(TransferType.WITHDRAW)
//                    .fromType(TargetType.GROUP_WALLET)
//                    .toType(TargetType.PERSONAL_WALLET)
//                    .src(groupWallet.getNickname())
//                    .dest("Receiver's Name") // 수신자 이름 또는 정보
//                    .amount(randomAmount)
//                    .afterBalance(groupWallet.getBalance() - randomAmount) // 이체 후 잔액 계산
//                    .currencyCode(CurrencyCode.KRW) // 통화 코드 설정
//                    .groupWallet(groupWallet)
//                    .build();
//
//            groupWalletTransfers.add(groupWalletTransfer);
//        }
//
//        return groupWalletTransfers;
//    }
//
//    // 개인지갑 이체 내역
//    public static List<PersonalWalletTransfer> createPersonalWalletTransfers(PersonalWallet personalWallet, int numberOfTransfers) {
//        List<PersonalWalletTransfer> personalWalletTransfers = new ArrayList<>();
//        Random random = new Random();
//
//        for (int i = 0; i < numberOfTransfers; i++) {
//            // 임의의 이체 금액 생성 (예: 1000부터 5000 사이의 임의의 값)
//            long randomAmount = (random.nextInt(4000) + 1000L);
//
//            PersonalWalletTransfer personalWalletTransfer = PersonalWalletTransfer.builder()
//                    .transferType(TransferType.WITHDRAW)
//                    .fromType(TargetType.ACCOUNT) // Personal Wallet로 변경
//                    .toType(TargetType.PERSONAL_WALLET) // Personal Wallet로 변경
//                    .src("국민은행 실제 계좌")
//                    .dest(personalWallet.getMember().getName()) // 수신자 이름 또는 정보
//                    .amount(randomAmount)
//                    .afterBalance(personalWallet.getBalance() - randomAmount) // 이체 후 잔액 계산
//                    .currencyCode(CurrencyCode.KRW) // 통화 코드 설정
//                    .personalWallet(personalWallet)
//                    .build();
//            personalWalletTransfers.add(personalWalletTransfer);
//        }
//        return personalWalletTransfers;
//    }
//
//    // 모임지갑 환전 내역
//    public static GroupWalletExchange createRandomGroupWalletExchange(GroupWallet groupWallet) {
//        Random random = new Random();
//
//        // 임의의 환전 정보 생성
//        CurrencyCode sellCurrencyCode = CurrencyCode.values()[random.nextInt(CurrencyCode.values().length)];
//        long sellAmount = (random.nextInt(900) + 100) * 1000L; // 100,000부터 1,000,000 사이의 임의의 값
//        CurrencyCode buyCurrencyCode = CurrencyCode.values()[random.nextInt(CurrencyCode.values().length)];
//        long buyAmount = (random.nextInt(900) + 100) * 1000L; // 100,000부터 1,000,000 사이의 임의의 값
//        double exchangeRate = random.nextDouble() * 10; // 0부터 10 사이의 임의의 환율 값
//
//        return GroupWalletExchange.builder()
//                .sellCurrencyCode(sellCurrencyCode)
//                .sellAmount(sellAmount)
//                .afterSellBalance(groupWallet.getBalance() - sellAmount)
//                .buyCurrencyCode(buyCurrencyCode)
//                .buyAmount(buyAmount)
//                .afterBuyBalance(groupWallet.getBalance() + buyAmount)
//                .exchangeRate(exchangeRate)
//                .groupWallet(groupWallet)
//                .build();
//    }
//
//    // 개인지갑 환전 내역
//    public static PersonalWalletExchange createRandomPersonalWalletExchange(PersonalWallet personalWallet) {
//        Random random = new Random();
//
//        // 임의의 환전 정보 생성
//        CurrencyCode sellCurrencyCode = CurrencyCode.values()[random.nextInt(CurrencyCode.values().length)];
//        long sellAmount = (random.nextInt(900) + 100) * 1000L; // 100,000부터 1,000,000 사이의 임의의 값
//        CurrencyCode buyCurrencyCode = CurrencyCode.values()[random.nextInt(CurrencyCode.values().length)];
//        long buyAmount = (random.nextInt(900) + 100) * 1000L; // 100,000부터 1,000,000 사이의 임의의 값
//        double exchangeRate = random.nextDouble() * 10; // 0부터 10 사이의 임의의 환율 값
//
//        return PersonalWalletExchange.builder()
//                .sellCurrencyCode(sellCurrencyCode)
//                .sellAmount(sellAmount)
//                .afterSellBalance(personalWallet.getBalance() - sellAmount)
//                .buyCurrencyCode(buyCurrencyCode)
//                .buyAmount(buyAmount)
//                .afterBuyBalance(personalWallet.getBalance() + buyAmount)
//                .exchangeRate(exchangeRate)
//                .personalWallet(personalWallet)
//                .build();
//    }
//
//    // 모임지갑 결제 내역
//    public static GroupWalletPayment createRandomGroupWalletPayment(GroupWallet groupWallet) {
//        Random random = new Random();
//
//        // 임의의 결제 정보 생성
//        CurrencyCode currencyCode = CurrencyCode.values()[random.nextInt(CurrencyCode.values().length)];
//        PaymentType paymentType = PaymentType.values()[random.nextInt(PaymentType.values().length)];
//        String paymentPlace = "Payment Place " + random.nextInt(100); // 임의의 결제 장소 이름 생성
//        PaymentCategoryType paymentCategory = PaymentCategoryType.values()[random.nextInt(PaymentCategoryType.values().length)];
//        long amount = (random.nextInt(900) + 100) * 1000L; // 100,000부터 1,000,000 사이의 임의의 결제 금액
//
//        return GroupWalletPayment.builder()
//                .currencyCode(currencyCode)
//                .paymentType(paymentType)
//                .paymentPlace(paymentPlace)
//                .paymentCategory(paymentCategory)
//                .amount(amount)
//                .afterPayBalance(groupWallet.getBalance() - amount)
//                .groupWallet(groupWallet)
//                .build();
//    }
//
//    // 개인지갑 결제 내역
//    public static PersonalWalletPayment createRandomPersonalWalletPayment(PersonalWallet personalWallet) {
//        Random random = new Random();
//
//        // 임의의 결제 정보 생성
//        CurrencyCode currencyCode = CurrencyCode.values()[random.nextInt(CurrencyCode.values().length)];
//        PaymentType paymentType = PaymentType.values()[random.nextInt(PaymentType.values().length)];
//        String paymentPlace = "Payment Place " + random.nextInt(100); // 임의의 결제 장소 이름 생성
//        PaymentCategoryType paymentCategory = PaymentCategoryType.values()[random.nextInt(PaymentCategoryType.values().length)];
//        long amount = (random.nextInt(900) + 100) * 1000L; // 100,000부터 1,000,000 사이의 임의의 결제 금액
//
//        return PersonalWalletPayment.builder()
//                .currencyCode(currencyCode)
//                .paymentType(paymentType)
//                .paymentPlace(paymentPlace)
//                .paymentCategory(paymentCategory)
//                .amount(amount)
//                .afterPayBalance(personalWallet.getBalance() - amount)
//                .personalWallet(personalWallet)
//                .build();
//    }
//
//    // 모임지갑 외화 잔액
//    public static GroupWalletForeignCurrencyBalance createRandomGroupWalletForeignCurrencyBalance(GroupWallet groupWallet) {
//        Random random = new Random();
//
//        // 임의의 외화 잔액 정보 생성
//        CurrencyCode currencyCode = CurrencyCode.values()[random.nextInt(CurrencyCode.values().length)];
//        long balance = (random.nextInt(900) + 100) * 1000L; // 100,000부터 1,000,000 사이의 임의의 외화 잔액
//
//        return GroupWalletForeignCurrencyBalance.builder()
//                .currencyCode(currencyCode)
//                .balance(balance)
//                .groupWallet(groupWallet)
//                .build();
//    }
//
//    // 개인지갑 외화 잔액
//    public static PersonalWalletForeignCurrencyBalance createRandomPersonalWalletForeignCurrencyBalance(PersonalWallet personalWallet) {
//        Random random = new Random();
//
//        // 임의의 외화 잔액 정보 생성
//        CurrencyCode currencyCode = CurrencyCode.values()[random.nextInt(CurrencyCode.values().length)];
//        long balance = (random.nextInt(900) + 100) * 1000L; // 100,000부터 1,000,000 사이의 임의의 외화 잔액
//
//        return PersonalWalletForeignCurrencyBalance.builder()
//                .currencyCode(currencyCode)
//                .balance(balance)
//                .personalWallet(personalWallet)
//                .build();
//    }
//
//    // 카드 발급 내역
//    public static CardIssuance createRandomCardIssuance(Member member, Long walletId) {
//        Random random = new Random();
//
//        // 임의의 카드 정보 생성
//        String cardNumber = generateRandomCardNumber();
//        CardState cardState = CardState.values()[random.nextInt(CardState.values().length)];
//        WalletType walletType = WalletType.values()[random.nextInt(WalletType.values().length)];
//
//        return CardIssuance.builder()
//                .cardNumber(cardNumber)
//                .cardState(cardState)
//                .walletId(walletId)
//                .walletType(walletType)
//                .member(member)
//                .build();
//    }
//
//    private static String generateRandomCardNumber() {
//        StringBuilder cardNumber = new StringBuilder();
//        Random random = new Random();
//
//        for (int i = 0; i < 16; i++) {
//            int digit = random.nextInt(10);
//            cardNumber.append(digit);
//            if ((i + 1) % 4 == 0 && i != 15) {
//                cardNumber.append(" "); // 각 네 자리마다 공백 추가
//            }
//        }
//        return cardNumber.toString();
//    }
//
//    // 회비 납부 내역
//    public static DuePayment createRandomDuePayment(Member member, GroupWallet groupWallet) {
//        // 납부 날짜를 현재 시각으로 설정
//        LocalDateTime insertDate = LocalDateTime.now();
//
//        return DuePayment.builder()
//                .member(member)
//                .groupWallet(groupWallet)
//                .build();
//    }
//
//    // 적금 생성
//    public static Saving createSavings(int i) {
//
//
//        return Saving.builder()
//                .name("Saving " + (i + 1))
//                .savingComment("Description of Saving " + (i + 1))
//                .interestRate(3.0 + i) // Example: Increment interest rate for each saving
//                .period(12 + i) // Example: Increment period for each saving
//                .amountLimit(10000000L + (i * 1000000)) // Example: Increment amount limit for each saving
//                .build();
//    }
//
//    // 적금 가입 생성
//    public static InstallmentSaving createInstallmentSavings(Saving saving, GroupWallet groupWallet) {
//
//
//        InstallmentSaving installmentSaving = InstallmentSaving.builder()
//                .maturityDate(LocalDateTime.now().plusMonths(saving.getPeriod()))
//                .done(false)
//                .totalAmount(1000000L) // Example: Set a fixed total amount
//                .savingDate(5) // Example: Set a fixed saving date
//                .savingAmount(200000L) // Example: Set a fixed saving amount
//                .saving(saving)
//                .groupWallet(groupWallet)
//                .build();
//
//
//        return installmentSaving;
//    }
//
//    // 적금 납부 내역 생성
//    public static List<SavingHistory> createSavingHistories(InstallmentSaving installmentSaving) {
//        List<SavingHistory> savingHistories = new ArrayList<>();
//
//        for (int i = 0; i < installmentSaving.getSaving().getPeriod(); i++) {
//            LocalDateTime insertDate = LocalDateTime.now().plusMonths(i).withDayOfMonth(5);
//            SavingHistory savingHistory = SavingHistory.builder()
//                    .amount(installmentSaving.getSavingAmount())
//                    .accumulatedAmount((i + 1) * installmentSaving.getSavingAmount())
//                    .installmentSaving(installmentSaving)
//                    .build();
//            savingHistories.add(savingHistory);
//        }
//
//        return savingHistories;
//    }
//
//    // 은행
//    public static Bank createBanks(int i) {
//        Address address = new Address("Street " + i, "City " + i, "Zip " + i);
//        Bank bank = Bank.builder()
//                .name("국민은행 " + (i + 1))
//                .address(address)
//                .build();
//
//
//        return bank;
//    }
//
//    // 오프라인 수령내역
//    public static OfflineReceipt createOfflineReceipt(Bank bank, PersonalWallet personalWallet, GroupWallet groupWallet) {
//        Random random = new Random();
//
//        OfflineReceipt offlineReceipt = OfflineReceipt.builder()
//                .currencyCode(CurrencyCode.values()[random.nextInt(CurrencyCode.values().length)])
//                .amount((long) (random.nextDouble() * 1000 + 1))
//                .receiptState(ReceiptState.values()[random.nextInt(ReceiptState.values().length)])
//                .bank(bank)
//                .personalWallet(personalWallet)
//                .groupWallet(groupWallet)
//                .build();
//
//        return offlineReceipt;
//    }
//}
//
