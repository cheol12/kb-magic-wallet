package kb04.team02.web.mvc;

import kb04.team02.web.mvc.exchange.entity.Bank;
import kb04.team02.web.mvc.exchange.entity.ExchangeRate;
import kb04.team02.web.mvc.exchange.entity.OfflineReceipt;
import kb04.team02.web.mvc.exchange.entity.ReceiptState;
import kb04.team02.web.mvc.exchange.repository.ExchangeRateRepository;
import kb04.team02.web.mvc.group.entity.*;
import kb04.team02.web.mvc.group.repository.*;
import kb04.team02.web.mvc.mypage.entity.CardIssuance;
import kb04.team02.web.mvc.mypage.entity.CardState;
import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.member.entity.Address;
import kb04.team02.web.mvc.member.entity.Member;
import kb04.team02.web.mvc.member.entity.Role;
import kb04.team02.web.mvc.personal.entity.PersonalWalletTransfer;
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
    @Autowired
    private ExchangeRateRepository repository;

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
                .payPassword("1234")
                .bankAccount("110-232-532XXX")
                .build();

        Member member2 = Member.builder()
                .id("asdf")
                .password("asdf")
                .name("최예빈")
                .address(address)
                .phoneNumber("010-2222-2222")
                .email("asdf@example.com")
                .payPassword("1234")
                .bankAccount("110-232-531XXX")
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


        PersonalWallet personalWallet1 = PersonalWallet.builder().balance(764000L).member(member1).build();
        PersonalWallet personalWallet2 = PersonalWallet.builder().balance(1302800L).member(member2).build();
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

        GroupWallet groupWallet1 = GroupWallet.builder()
                .nickname("깨비깨비 모임지갑")
                .balance(0L) // 초기 잔액 설정
                .dueCondition(false)
                .dueAccumulation(0L)
                .dueDate(0)
                .due(0L)
                .member(member1)
                .build();

        GroupWallet groupWallet2 = GroupWallet.builder()
                .nickname("캐비캐비 모임지갑")
                .balance(0L) // 초기 잔액 설정
                .dueCondition(true)
                .dueAccumulation(0L)
                .dueDate(1)
                .due(50000L)
                .member(member2)
                .build();

        groupWalletRespository.save(groupWallet1);
        groupWalletRespository.save(groupWallet2);

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
                .role(Role.CO_CHAIRMAN).memberId(member3.getMemberId()).build());
        participationRepository.save(Participation.builder().groupWallet(groupWallet2).participationState(ParticipationState.PARTICIPATED)
                .role(Role.GENERAL).memberId(member4.getMemberId()).build());

//        // 모임지갑 이체 내역
//        transferRepository.save(GroupWalletTransfer.builder()
//                .transferType(TransferType.WITHDRAW)
//                .fromType(TargetType.GROUP_WALLET)
//                .toType(TargetType.PERSONAL_WALLET)
//                .src(groupWallet1.getNickname())
//                .dest("Receiver's Name") // 수신자 이름 또는 정보
//                .amount(10000L)
//                .afterBalance(100000L) // 이체 후 잔액 계산
//                .currencyCode(CurrencyCode.KRW) // 통화 코드 설정
//                .groupWallet(groupWallet1)
//                .build());
//
//        transferRepository.save(GroupWalletTransfer.builder()
//                .transferType(TransferType.WITHDRAW)
//                .fromType(TargetType.GROUP_WALLET)
//                .toType(TargetType.PERSONAL_WALLET)
//                .src(groupWallet2.getNickname())
//                .dest("Receiver's Name") // 수신자 이름 또는 정보
//                .amount(10000L)
//                .afterBalance(0L) // 이체 후 잔액 계산
//                .currencyCode(CurrencyCode.KRW) // 통화 코드 설정
//                .groupWallet(groupWallet2)
//                .build());
//

        // 모임지갑 1 외화
        groupForeignCurrencyRepository.save(GroupWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.USD)
                .balance(210L)
                .groupWallet(groupWallet1)
                .build());
        groupForeignCurrencyRepository.save(GroupWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.JPY)
                .balance(30000L)
                .groupWallet(groupWallet1)
                .build());

        // 모임지갑 2 외화
        groupForeignCurrencyRepository.save(GroupWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.USD)
                .balance(90L)
                .groupWallet(groupWallet2)
                .build());
        groupForeignCurrencyRepository.save(GroupWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.JPY)
                .balance(80000L)
                .groupWallet(groupWallet2)
                .build());

        //====================================================================================
        personalForeignCurrencyBalanceRepository.save(PersonalWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.USD)
                .balance(100L)
                .personalWallet(personalWallet1)
                .build());
        personalForeignCurrencyBalanceRepository.save(PersonalWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.JPY)
                .balance(20000L)
                .personalWallet(personalWallet1)
                .build());

        personalForeignCurrencyBalanceRepository.save(PersonalWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.USD)
                .balance(87L)
                .personalWallet(personalWallet2)
                .build());
        personalForeignCurrencyBalanceRepository.save(PersonalWalletForeignCurrencyBalance.builder()
                .currencyCode(CurrencyCode.JPY)
                .balance(9800L)
                .personalWallet(personalWallet2)
                .build());

        //====================================================================================

        cardIssuanceRepository.save(CardIssuance.builder()
                .cardNumber("4632-1235-3462-0951")
                .cardState(CardState.OK)
                .walletId(groupWallet1.getGroupWalletId())
                .walletType(WalletType.GROUP_WALLET)
                .member(member1)
                .build());

        cardIssuanceRepository.save(CardIssuance.builder()
                .cardNumber("4654-2356-1235-5734")
                .cardState(CardState.OK)
                .walletId(groupWallet1.getGroupWalletId())
                .walletType(WalletType.GROUP_WALLET)
                .member(member2)
                .build());

        cardIssuanceRepository.save(CardIssuance.builder()
                .cardNumber("4151-4632-1254")
                .cardState(CardState.OK)
                .walletId(groupWallet2.getGroupWalletId())
                .walletType(WalletType.GROUP_WALLET)
                .member(member3)
                .build());

        //==============================================================

        Saving save1 = savingRepository.save(Saving.builder()
                .name("두근두근 외화적금")
                .savingComment("두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근")
                .interestRate(3.0) // Example: Increment interest rate for each saving
                .period(12) // Example: Increment period for each saving
                .amountLimit(10000000L) // Example: Increment amount limit for each saving
                .build());
        Saving save2 = savingRepository.save(Saving.builder()
                .name("쿵쾅쿵쾅 외화적금")
                .savingComment("쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅쿵쾅")
                .interestRate(2.0) // Example: Increment interest rate for each saving
                .period(6) // Example: Increment period for each saving
                .amountLimit(100000L) // Example: Increment amount limit for each saving
                .build());
        Saving save3 = savingRepository.save(Saving.builder()
                .name("근두근두 외화적금")
                .savingComment("근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두근두")
                .interestRate(1.0) // Example: Increment interest rate for each saving
                .period(6) // Example: Increment period for each saving
                .amountLimit(10000000L) // Example: Increment amount limit for each saving
                .build());
        Saving save4 = savingRepository.save(Saving.builder()
                .name("허겁지겁 외화적금")
                .savingComment("허겁지겁허겁지겁허겁지겁허겁지겁허겁지겁허겁지겁허겁지겁허겁지겁허겁지겁허겁지겁허겁지겁허겁지겁허겁지겁허겁지겁허겁지겁허겁지겁허겁지겁허겁지겁허겁지겁허겁지겁허겁지겁허겁지겁허겁지겁")
                .interestRate(0.5) // Example: Increment interest rate for each saving
                .period(3) // Example: Increment period for each saving
                .amountLimit(5000000L) // Example: Increment amount limit for each saving
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
                .groupWallet(groupWallet2)
                .build());

//        ;personalWalletTransferRepository.save(PersonalWalletTransfer.builder()
//                .transferType(TransferType.DEPOSIT)
//                .fromType(TargetType.ACCOUNT)
//                .toType(TargetType.PERSONAL_WALLET)
//                .src("국민은행")
//                .dest(personalWallet2.getMember().getName()) // 수신자 이름 또는 정보
//                .amount(1100000L)
//                .afterBalance(1100000L) // 이체 후 잔액 계산
//                .currencyCode(CurrencyCode.KRW) // 통화 코드 설정
//                .personalWallet(personalWallet2)
//                .build());
//
//        personalWalletTransferRepository.save(PersonalWalletTransfer.builder()
//                .transferType(TransferType.WITHDRAW)
//                .fromType(TargetType.PERSONAL_WALLET)
//                .toType(TargetType.GROUP_WALLET)
//                .src(personalWallet2.getMember().getName())
//                .dest("모임지갑") // 수신자 이름 또는 정보
//                .amount(100000L)
//                .afterBalance(1000000L) // 이체 후 잔액 계산
//                .currencyCode(CurrencyCode.KRW) // 통화 코드 설정
//                .personalWallet(personalWallet2)
//                .build());
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
                    repository.save(ExchangeRate.builder()
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
                    repository.save(ExchangeRate.builder()
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