package kb04.team02.web.mvc.group.service;

import kb04.team02.web.mvc.common.dto.LoginMemberDto;
import kb04.team02.web.mvc.common.dto.WalletDetailDto;
import kb04.team02.web.mvc.common.dto.WalletHistoryDto;
import kb04.team02.web.mvc.common.entity.*;
import kb04.team02.web.mvc.exchange.dto.RuleDto;
import kb04.team02.web.mvc.exchange.dto.WalletDto;
import kb04.team02.web.mvc.group.dto.*;
import kb04.team02.web.mvc.group.entity.*;
import kb04.team02.web.mvc.group.repository.*;
import kb04.team02.web.mvc.member.entity.Member;
import kb04.team02.web.mvc.member.entity.Role;
import kb04.team02.web.mvc.mypage.entity.CardIssuance;
import kb04.team02.web.mvc.mypage.entity.CardState;
import kb04.team02.web.mvc.mypage.repository.CardIssuanceRepository;
import kb04.team02.web.mvc.personal.entity.PersonalWallet;
import kb04.team02.web.mvc.personal.entity.PersonalWalletForeignCurrencyBalance;
import kb04.team02.web.mvc.personal.entity.PersonalWalletTransfer;
import kb04.team02.web.mvc.common.exception.InsertException;
import kb04.team02.web.mvc.member.repository.MemberRepository;
import kb04.team02.web.mvc.group.exception.NotEnoughBalanceException;
import kb04.team02.web.mvc.group.exception.WalletDeleteException;
import kb04.team02.web.mvc.personal.repository.PersonalWalletForeignCurrencyBalanceRepository;
import kb04.team02.web.mvc.personal.repository.PersonalWalletRepository;
import kb04.team02.web.mvc.personal.repository.PersonalWalletTransferRepository;
import kb04.team02.web.mvc.personal.service.PersonalWalletService;
import kb04.team02.web.mvc.saving.entity.InstallmentSaving;
import kb04.team02.web.mvc.saving.entity.Saving;
import kb04.team02.web.mvc.saving.repository.InstallmentSavingRepository;
import kb04.team02.web.mvc.saving.repository.SavingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GroupWalletServiceImpl implements GroupWalletService {

    private final GroupWalletRespository groupWalletRep;
    private final GroupWalletExchangeRepository groupExchangeRep;
    private final GroupWalletForeignCurrencyBalanceRepository groupForeignBalanceRep;
    private final PersonalWalletForeignCurrencyBalanceRepository personalForeignBalanceRep;
    private final GroupWalletPaymentRepository groupPaymentRep;
    private final GroupWalletTransferRepository groupTransferRep;
    private final ParticipationRepository participationRep;
    private final PersonalWalletRepository personalWalletRep;
    private final MemberRepository memberRep;
    private final PersonalWalletTransferRepository personalTransferRep;
    private final InstallmentSavingRepository installmentSavingRep;
    private final SavingRepository savingRep;
    private final CardIssuanceRepository cardIssuanceRep;


    @Override
    public List<GroupWallet> selectAllMyGroupWallet(LoginMemberDto loginMemberDto) {

        Long memberId = loginMemberDto.getMemberId();
//        List<Participation> participationList = groupWalletRep.findByMember_MemberId(memberId);
        List<Participation> participationList = participationRep.findParticipationByMemberIdAndParticipationStateEquals(memberId, ParticipationState.PARTICIPATED);

        System.out.println("participationList : " + participationList);

        // 참가자 리스트 테이블에서 현재 memberid 에 맞는 데이터만 불러오기
        List<GroupWallet> groupWalletList = new ArrayList<>();

        for(Participation participation : participationList){
            GroupWallet groupWallet = groupWalletRep.findByGroupWalletId(participation.getGroupWallet().getGroupWalletId());
            groupWalletList.add(groupWallet);
        }
        System.out.println(groupWalletList);

        return groupWalletList;
    }

    @Override
    public int createGroupWallet(Long memberId, String nickname) {

//        GroupWallet groupWallet = groupWalletRep.findByMember(member);

        GroupWallet groupWalletSave;
        List<Member> member = memberRep.findByMemberId(memberId);

        groupWalletSave = groupWalletRep.save(
                GroupWallet.builder()
                        .balance(0L)
                        .due(0L)
                        .dueAccumulation(0L)
                        .dueCondition(false)
                        .dueDate(0)
                        .nickname(nickname)
                        .member(member.get(0))
                        .build()

        );

        // participation 테이블에도 모임장 데이터를 추가해야하지 않나

        Participation participation;

        participation = participationRep.save(
                Participation.builder()
                        .participationState(ParticipationState.PARTICIPATED)
                        .memberId(member.get(0).getMemberId())
                        .role(Role.CHAIRMAN)
                        .groupWallet(groupWalletSave)
                        .build()
        );

        if (groupWalletSave == null || participation == null) {
            throw new InsertException("모임 지갑 생성에 실패했습니다");
        }
        return 1;
    }

    @Override
    public WalletDetailDto getGroupWalletDetail(Long groupWalletId) {
        WalletDetailDto dto = new WalletDetailDto();

        GroupWallet groupWallet = groupWalletRep.findById(groupWalletId).orElseThrow(()->new NoSuchElementException("모임 지갑 조회 실패"));

        List<GroupWalletExchange> exchangeList = groupExchangeRep.searchAllByGroupWallet(groupWallet);

        List<GroupWalletForeignCurrencyBalance> foreignCurrencyBalanceList = groupForeignBalanceRep.findByGroupWallet(groupWallet);

        List<GroupWalletPayment> paymentList = groupPaymentRep.searchAllByGroupWallet(groupWallet);

        List<GroupWalletTransfer> transferList = groupTransferRep.searchAllByGroupWallet(groupWallet);

        dto.setBalance(new HashMap<>());


        // 외화 잔액 내역 설정
        for (GroupWalletForeignCurrencyBalance foreignCurrencyBalance : foreignCurrencyBalanceList) {
            dto.getBalance().put(foreignCurrencyBalance.getCurrencyCode().name(),
                    foreignCurrencyBalance.getBalance());
        }

        dto.getBalance().put("KRW", groupWallet.getBalance());

        dto.setList(new ArrayList<>());

        // 환전 내역 설정
        for(GroupWalletExchange exchange : exchangeList){
            WalletHistoryDto walletHistoryDto = new WalletHistoryDto();

            walletHistoryDto.setDateTime(exchange.getInsertDate());

            if(exchange.getSellCurrencyCode() == CurrencyCode.KRW){
                walletHistoryDto.setType("재환전");
            }
            else{
                walletHistoryDto.setType("환전");
            }

            String detail = exchange.getSellCurrencyCode() + " > " + exchange.getBuyCurrencyCode();

            walletHistoryDto.setDetail(detail);
            walletHistoryDto.setAmount(exchange.getSellAmount().toString());
            walletHistoryDto.setBalance(exchange.getAfterSellBalance().toString() + ":" + exchange.getAfterBuyBalance());

            dto.getList().add(walletHistoryDto);
        }

        // 결제 내역 설정
        for(GroupWalletPayment payment : paymentList){
            WalletHistoryDto walletHistoryDto = new WalletHistoryDto();

            walletHistoryDto.setDateTime(payment.getInsertDate());

            if(payment.getPaymentType() == PaymentType.OK){
                walletHistoryDto.setType("결제");
            }
            else{
                walletHistoryDto.setType("취소");
            }

            String detail = payment.getAmount() + payment.getCurrencyCode().name();

            walletHistoryDto.setDetail(detail);
            walletHistoryDto.setAmount(payment.getAmount().toString());
            walletHistoryDto.setBalance(payment.getAfterPayBalance().toString());

            dto.getList().add(walletHistoryDto);
        }

        // 이체 내역 설정
        for(Transfer transfer : transferList){
            WalletHistoryDto walletHistoryDto = new WalletHistoryDto();

            walletHistoryDto.setDateTime(transfer.getInsertDate());

            if(transfer.getTransferType() == TransferType.DEPOSIT){
                walletHistoryDto.setType("입금");
            }
            else{
                walletHistoryDto.setType("출금");
            }

            String detail = transfer.getSrc() + " > " + transfer.getDest();
            walletHistoryDto.setDetail(detail);
            walletHistoryDto.setAmount(transfer.getAmount().toString());
            walletHistoryDto.setBalance(transfer.getAfterBalance().toString());

            dto.getList().add(walletHistoryDto);
        }

        return dto;
    }

    @Override
    public int deleteGroupWallet(Long groupWalletId) throws WalletDeleteException {
        // 모임장이 모임지갑을 삭제하는 것.
        // 모임장인지 확인하는 것은 어디서할지?
        GroupWallet groupWallet = groupWalletRep.findById(groupWalletId).orElseThrow(()-> new NoSuchElementException("모임 지갑 조회 실패"));
        Member member = groupWallet.getMember();
        Participation participation = participationRep.findByGroupWalletAndMemberId(groupWallet, member.getMemberId());
        List<Participation> participations = participationRep.findByGroupWalletAndParticipationState(groupWallet, ParticipationState.PARTICIPATED);
        if (participations.size() > 1 && participation.getRole() == Role.CHAIRMAN) {
            throw new WalletDeleteException("모임원이 있는 상태에서 삭제할 수 없습니다");
        }

        return groupWalletRep.deleteGroupWalletByGroupWalletId(groupWalletId);
    }

    @Override
    public String inviteMember(Long groupWalletId) {
        return "http://초대링크";
    }

    @Override
    public int groupWalletMemberOut(Long groupWalletId, Member member) {

        // 모임원 내보내기 or 탈퇴
//        int result = groupWalletRep.deleteByGroupWalletIdAndMember(groupWalletId, memberId);
        GroupWallet groupWallet = groupWalletRep.findById(groupWalletId).orElseThrow(()->new NoSuchElementException("멤버 조회 실패"));
        Participation participation = participationRep.findByGroupWalletAndMemberId(groupWallet, member.getMemberId());
        participationRep.delete(participation);
        return 1;
    }

    @Transactional
    @Override
    public GroupWallet setGroupWalletDueRule(Long groupWalletId, int dueDate, Long due) {
        GroupWallet groupWallet = groupWalletRep.findById(groupWalletId).orElseThrow(()->new NoSuchElementException("모임 지갑 조회 실패"));


//        groupWallet = groupWalletRep.save(
//                GroupWallet.builder()
//                        .groupWalletId(groupWalletId)
//                        .dueCondition(true)
//                        .dueDate(dueDate)
//                        .due(due)
//                        .build()
//        );
        groupWallet.setDueCondition(true);
        groupWallet.setDueDate(dueDate);
        groupWallet.setDue(due);
        log.info("ServiceImpl단 : setGroupWalletDueRule = " + groupWallet.getDueDate());
        if (groupWallet == null) {
            throw new InsertException("모임 지갑 회비 규칙 생성에 실패했습니다");
        }
        return groupWallet;
    }

    @Transactional
    @Override
    public RuleDto getGroupWalletDueRule(Long groupWalletId) {
        // 모임 회비 규칙 생성은 모임장만!
        GroupWallet groupWallet = groupWalletRep.findByGroupWalletId(groupWalletId);
        RuleDto ruleDto = new RuleDto();
        if (groupWallet.isDueCondition()) {
            ruleDto.setDueDate(groupWallet.getDueDate());
            ruleDto.setDuePrice(groupWallet.getDue());
        }
        return ruleDto;
    }

    @Override
    public GroupWallet deleteGroupWalletDueRule(Long groupWalletId) {
        // 모임지갑 회비 규칙 삭제하기

        GroupWallet groupWallet = groupWalletRep.findByGroupWalletId(groupWalletId);
        groupWallet.setDueCondition(false);
        groupWallet.setDueDate(0);
        groupWallet.setDue(null);
        return groupWallet;
    }

    @Transactional
    @Override
    public int groupWalletWithdraw(WithDrawDto withDrawDto) throws NotEnoughBalanceException {
        Long walletId = withDrawDto.getSrcWalletId();
        GroupWallet groupWallet = groupWalletRep.findById(walletId).orElseThrow(()->new NoSuchElementException("모임 지갑 조회 실패"));
        Member member = memberRep.findById(withDrawDto.getDestMemberId()).orElseThrow(()->new NoSuchElementException("멤버 조회 실패"));

        PersonalWallet personalWallet = personalWalletRep.findByMember(member);

        List<GroupWalletForeignCurrencyBalance> foreignCurrencyBalances
                = groupForeignBalanceRep.findByGroupWallet(groupWallet);

        List<PersonalWalletForeignCurrencyBalance> personalWalletForeignCurrencyBalances
                = personalForeignBalanceRep.searchAllByPersonalWallet(personalWallet);

        CurrencyCode code = CurrencyCode.KRW;
        GroupWalletForeignCurrencyBalance currGroupForiegnBalance = new GroupWalletForeignCurrencyBalance();
        PersonalWalletForeignCurrencyBalance currPersonalForeignBalance = new PersonalWalletForeignCurrencyBalance();

        for (GroupWalletForeignCurrencyBalance balance : foreignCurrencyBalances) {
            if (balance.getCurrencyCode() == withDrawDto.getCurrencyCode()) {
                code = balance.getCurrencyCode();
                currGroupForiegnBalance = balance;

            }
        }

        for (PersonalWalletForeignCurrencyBalance balance : personalWalletForeignCurrencyBalances) {
            if (balance.getCurrencyCode() == withDrawDto.getCurrencyCode()) {
                code = balance.getCurrencyCode();
                currPersonalForeignBalance = balance;
            }
        }

        switch (code) {
            case KRW:
                if (withDrawDto.getAmount() > groupWallet.getBalance()) {
                    // 잔액초과
                    throw new NotEnoughBalanceException("잔액이 부족합니다");
                }
                groupWallet.setBalance(groupWallet.getBalance() - withDrawDto.getAmount());
                personalWallet.setBalance(personalWallet.getBalance() + withDrawDto.getAmount());

                groupTransferRep.save(GroupWalletTransfer.builder()
                        .currencyCode(code)
                        .groupWallet(groupWallet)
                        .src(groupWallet.getNickname())
                        .transferType(TransferType.WITHDRAW)
                        .fromType(TargetType.GROUP_WALLET)
                        .toType(TargetType.PERSONAL_WALLET)
                        .dest(member.getName())
                        .afterBalance(groupWallet.getBalance())
                        .amount(withDrawDto.getAmount())
                        .build());

                personalTransferRep.save(PersonalWalletTransfer.builder()
                        .currencyCode(code)
                        .personalWallet(personalWallet)
                        .src(groupWallet.getNickname())
                        .transferType(TransferType.WITHDRAW)
                        .fromType(TargetType.GROUP_WALLET)
                        .toType(TargetType.PERSONAL_WALLET)
                        .dest(member.getName())
                        .afterBalance(personalWallet.getBalance())
                        .amount(withDrawDto.getAmount())
                        .build());

                break;
            case JPY:
            case USD:
                if (withDrawDto.getAmount() > currGroupForiegnBalance.getBalance()) {
                    // 잔액초과
                    throw new NotEnoughBalanceException();
                }
                currGroupForiegnBalance.setBalance(currGroupForiegnBalance.getBalance()
                        - withDrawDto.getAmount());
                currPersonalForeignBalance.setBalance(currPersonalForeignBalance.getBalance()
                        + withDrawDto.getAmount());

                groupTransferRep.save(GroupWalletTransfer.builder()
                        .currencyCode(code)
                        .groupWallet(groupWallet)
                        .src(groupWallet.getNickname())
                        .transferType(TransferType.WITHDRAW)
                        .fromType(TargetType.GROUP_WALLET)
                        .toType(TargetType.PERSONAL_WALLET)
                        .dest(member.getName())
                        .afterBalance(currGroupForiegnBalance.getBalance())
                        .amount(withDrawDto.getAmount())
                        .build());

                personalTransferRep.save(PersonalWalletTransfer.builder()
                        .currencyCode(code)
                        .personalWallet(personalWallet)
                        .src(groupWallet.getNickname())
                        .transferType(TransferType.DEPOSIT)
                        .fromType(TargetType.GROUP_WALLET)
                        .toType(TargetType.PERSONAL_WALLET)
                        .dest(member.getName())
                        .afterBalance(currPersonalForeignBalance.getBalance())
                        .amount(withDrawDto.getAmount())
                        .build());

                break;
        }
        return 1;
    }

    @Transactional
    @Override
    public int settle(SettleDto settleDto) throws NotEnoughBalanceException {
        Long groupWalletId = settleDto.getGroupWalletId();
        GroupWallet groupWallet = groupWalletRep.findById(groupWalletId).orElseThrow(()->new NoSuchElementException("모임 지갑 조회 실패"));
        List<Participation> memberList = participationRep.findByGroupWalletAndParticipationState(groupWallet, ParticipationState.PARTICIPATED);

        // 원화 정산
        switch (settleDto.getCurrencyCode()) {
            case KRW:
                switch (settleDto.getSettleType()) {
                    case NBBANG:
                        Long divideAmount = settleDto.getTotalAmout() / memberList.size();
                             Long chairmanDivideAmount = divideAmount + settleDto.getTotalAmout() % memberList.size();
                        System.out.println("divideAmount = " + divideAmount);
                        System.out.println("chairmanDivideAmount = " + chairmanDivideAmount);
                        for (Participation participation : memberList) {
                            Member mem = memberRep.findById(participation.getMemberId()).orElseThrow(()->new NoSuchElementException("회원 조회 실패"));

                            if(participation.getRole()==Role.CHAIRMAN)
                                this.groupWalletToPersonalWallet(groupWallet, mem, chairmanDivideAmount, CurrencyCode.KRW);
                            else
                                this.groupWalletToPersonalWallet(groupWallet, mem, divideAmount, CurrencyCode.KRW);
                        }
                        break;
                    case RATIO_SETTLE:
                    case RANDOM_SETTLE:
                        // 미구현
                        break;
                }
                break;
            case USD:
            case JPY:
                switch (settleDto.getSettleType()) {
                    case NBBANG:
//                        List<GroupWalletForeignCurrencyBalance> currencyBalanceList
//                                = groupForeignBalanceRep.findByGroupWallet(groupWallet);
//                        Long balance = 0L;
//
//                        for (GroupWalletForeignCurrencyBalance currency : currencyBalanceList) {
//                            if (currency.getCurrencyCode() == settleDto.getCurrencyCode()) {
//                                balance = currency.getBalance();
//                            }
//                        }

                        Long divideAmount = settleDto.getTotalAmout() / memberList.size();
                        Long chairmanDivideAmount = divideAmount + settleDto.getTotalAmout() % memberList.size();

                        for (Participation participation : memberList) {
                            Member mem = memberRep.findById(participation.getMemberId()).orElseThrow(()->new NoSuchElementException("회원 조회 실패"));

                            if(participation.getRole()==Role.CHAIRMAN)
                                this.groupWalletToPersonalWallet(groupWallet, mem, chairmanDivideAmount, settleDto.getCurrencyCode());
                            else
                                this.groupWalletToPersonalWallet(groupWallet, mem, divideAmount, settleDto.getCurrencyCode());
                        }
                        break;
                    case RATIO_SETTLE:
                    case RANDOM_SETTLE:
                        // 미구현
                        break;
                }
                break;
        }
        return 1;
    }

    @Transactional
    @Override
    public int groupWalletDeposit(DepositDto depositDto) throws NotEnoughBalanceException {

        Long walletId = depositDto.getDestWalletId();
        GroupWallet groupWallet = groupWalletRep.findById(walletId).orElseThrow(()->new NoSuchElementException("모임 지갑 조회 실패"));
        Member member = memberRep.findById(depositDto.getSrcMemberId()).orElseThrow(()->new NoSuchElementException("회원 조회 실패"));
        PersonalWallet personalWallet = personalWalletRep.findByMember(member);

        List<GroupWalletForeignCurrencyBalance> foreignCurrencyBalances
                = groupForeignBalanceRep.findByGroupWallet(groupWallet);

        List<PersonalWalletForeignCurrencyBalance> personalWalletForeignCurrencyBalances
                = personalForeignBalanceRep.searchAllByPersonalWallet(personalWallet);

        CurrencyCode code = CurrencyCode.KRW;
        GroupWalletForeignCurrencyBalance currGroupForiegnBalance = new GroupWalletForeignCurrencyBalance();
        PersonalWalletForeignCurrencyBalance currPersonalForeignBalance = new PersonalWalletForeignCurrencyBalance();

        for (GroupWalletForeignCurrencyBalance balance : foreignCurrencyBalances) {
            if (balance.getCurrencyCode() == depositDto.getCurrencyCode()) {
                code = balance.getCurrencyCode();
                currGroupForiegnBalance = balance;

            }
        }

        for (PersonalWalletForeignCurrencyBalance balance : personalWalletForeignCurrencyBalances) {
            if (balance.getCurrencyCode() == depositDto.getCurrencyCode()) {
                code = balance.getCurrencyCode();
                currPersonalForeignBalance = balance;
            }
        }

        switch (code) {
            case KRW:
                if (depositDto.getAmount() > personalWallet.getBalance()) {
                    // 잔액부족
                    throw new NotEnoughBalanceException("개인지갑 잔액 부족");
                }
                personalWallet.setBalance(personalWallet.getBalance() - depositDto.getAmount());
                groupWallet.setBalance(groupWallet.getBalance() + depositDto.getAmount());

                groupTransferRep.save(GroupWalletTransfer.builder()
                        .currencyCode(code)
                        .groupWallet(groupWallet)
                        .src(member.getName())
                        .transferType(TransferType.DEPOSIT)
                        .fromType(TargetType.PERSONAL_WALLET)
                        .toType(TargetType.GROUP_WALLET)
                        .dest(groupWallet.getNickname())
                        .afterBalance(groupWallet.getBalance())
                        .amount(depositDto.getAmount())
                        .build());

                personalTransferRep.save(PersonalWalletTransfer.builder()
                        .currencyCode(code)
                        .personalWallet(personalWallet)
                        .src(member.getName())
                        .transferType(TransferType.DEPOSIT)
                        .fromType(TargetType.PERSONAL_WALLET)
                        .toType(TargetType.GROUP_WALLET)
                        .dest(groupWallet.getNickname())
                        .afterBalance(personalWallet.getBalance())
                        .amount(depositDto.getAmount())
                        .build());

                break;
            case JPY:
            case USD:
                if (depositDto.getAmount() > currPersonalForeignBalance.getBalance()) {
                    // 잔액부족
                    throw new NotEnoughBalanceException("개인지갑 잔액 부족");
                }
                currPersonalForeignBalance.setBalance(currPersonalForeignBalance.getBalance()
                        - depositDto.getAmount());
                currGroupForiegnBalance.setBalance(currGroupForiegnBalance.getBalance()
                        + depositDto.getAmount());

                groupTransferRep.save(GroupWalletTransfer.builder()
                        .currencyCode(code)
                        .groupWallet(groupWallet)
                        .src(member.getName())
                        .transferType(TransferType.WITHDRAW)
                        .fromType(TargetType.PERSONAL_WALLET)
                        .toType(TargetType.GROUP_WALLET)
                        .dest(groupWallet.getNickname())
                        .afterBalance(currGroupForiegnBalance.getBalance())
                        .amount(depositDto.getAmount())
                        .build());

                personalTransferRep.save(PersonalWalletTransfer.builder()
                        .currencyCode(code)
                        .personalWallet(personalWallet)
                        .src(member.getName())
                        .transferType(TransferType.DEPOSIT)
                        .fromType(TargetType.PERSONAL_WALLET)
                        .toType(TargetType.GROUP_WALLET)
                        .dest(groupWallet.getNickname())
                        .afterBalance(currPersonalForeignBalance.getBalance())
                        .amount(depositDto.getAmount())
                        .build());

                break;
        }
        return 1;
    }

    @Transactional
    @Override
    public int groupWalletToPersonalWallet(GroupWallet groupWallet, Member member, Long amount, CurrencyCode currencyCode) throws NotEnoughBalanceException {

        PersonalWallet personalWallet = personalWalletRep.findByMember(member);

        List<GroupWalletForeignCurrencyBalance> foreignCurrencyBalances
                = groupForeignBalanceRep.findByGroupWallet(groupWallet);

        List<PersonalWalletForeignCurrencyBalance> personalWalletForeignCurrencyBalances
                = personalForeignBalanceRep.searchAllByPersonalWallet(personalWallet);

        CurrencyCode code = CurrencyCode.KRW;
        GroupWalletForeignCurrencyBalance currGroupForiegnBalance = new GroupWalletForeignCurrencyBalance();
        PersonalWalletForeignCurrencyBalance currPersonalForeignBalance = new PersonalWalletForeignCurrencyBalance();

        for (GroupWalletForeignCurrencyBalance balance : foreignCurrencyBalances) {
            if (balance.getCurrencyCode() == currencyCode) {
                code = balance.getCurrencyCode();
                currGroupForiegnBalance = balance;

            }
        }

        for (PersonalWalletForeignCurrencyBalance balance : personalWalletForeignCurrencyBalances) {
            if (balance.getCurrencyCode() == currencyCode) {
                code = balance.getCurrencyCode();
                currPersonalForeignBalance = balance;
            }
        }

        switch (code) {
            case KRW:
                if (amount > groupWallet.getBalance()) {
                    // 잔액초과
                    throw new NotEnoughBalanceException();
                }
                groupWallet.setBalance(groupWallet.getBalance() - amount);
                personalWallet.setBalance(personalWallet.getBalance() + amount);

                groupTransferRep.save(GroupWalletTransfer.builder()
                        .currencyCode(code)
                        .groupWallet(groupWallet)
                        .src(member.getName())
                        .transferType(TransferType.DEPOSIT)
                        .fromType(TargetType.GROUP_WALLET)
                        .toType(TargetType.PERSONAL_WALLET)
                        .dest(groupWallet.getNickname())
                        .afterBalance(groupWallet.getBalance())
                        .amount(amount)
                        .build());

                personalTransferRep.save(PersonalWalletTransfer.builder()
                        .currencyCode(code)
                        .personalWallet(personalWallet)
                        .src(groupWallet.getNickname())
                        .transferType(TransferType.DEPOSIT)
                        .fromType(TargetType.GROUP_WALLET)
                        .toType(TargetType.PERSONAL_WALLET)
                        .dest(member.getName())
                        .afterBalance(personalWallet.getBalance())
                        .amount(amount)
                        .build());

                break;
            case JPY:
            case USD:
                if (amount > currGroupForiegnBalance.getBalance()) {
                    // 잔액초과
                    throw new NotEnoughBalanceException();
                }
                currGroupForiegnBalance.setBalance(currGroupForiegnBalance.getBalance()
                        - amount);
                currPersonalForeignBalance.setBalance(currPersonalForeignBalance.getBalance()
                        + amount);
                groupTransferRep.save(GroupWalletTransfer.builder()
                        .currencyCode(code)
                        .groupWallet(groupWallet)
                        .src(groupWallet.getNickname())
                        .transferType(TransferType.WITHDRAW)
                        .fromType(TargetType.GROUP_WALLET)
                        .toType(TargetType.PERSONAL_WALLET)
                        .dest(member.getName())
                        .afterBalance(currGroupForiegnBalance.getBalance())
                        .amount(amount)
                        .build());

                personalTransferRep.save(PersonalWalletTransfer.builder()
                        .currencyCode(code)
                        .personalWallet(personalWallet)
                        .src(groupWallet.getNickname())
                        .transferType(TransferType.DEPOSIT)
                        .fromType(TargetType.GROUP_WALLET)
                        .toType(TargetType.PERSONAL_WALLET)
                        .dest(member.getName())
                        .afterBalance(currPersonalForeignBalance.getBalance())
                        .amount(amount)
                        .build());
                break;
        }

        return 1;
    }

    @Override
    @Transactional
    public GroupWallet getGroupWallet(Long groupWalletId){
        GroupWallet groupWallet = groupWalletRep.findByGroupWalletId(groupWalletId);

        return groupWallet;
    }

    @Override
    @Transactional
    public List<GroupMemberDto> getGroupMemberList(Long groupWalletId) {
        List<GroupMemberDto> groupMemberDtoList = new ArrayList<>();

        List<Participation> participationList = participationRep
                .findParticipationByGroupWallet_GroupWalletIdAndParticipationState(groupWalletId, ParticipationState.PARTICIPATED);

        log.info("participationList.size = " + participationList.size());

        GroupMemberDto groupMemberDto;
        for(int i=0; i<participationList.size(); i++){
            Participation p = participationList.get(i);
            Member member = memberRep.findByMemberId(p.getMemberId()).get(0);

            log.info("member = " + member);

            groupMemberDto = new GroupMemberDto();
            groupMemberDto.setName(member.getName());
            groupMemberDto.setRole(p.getRole());

            String roleName = String.valueOf(p.getRole());

            if(roleName.equals("CHAIRMAN")) groupMemberDto.setRoleToString("모임장");
            else if(roleName.equals("CO_CHAIRMAN")) groupMemberDto.setRoleToString("공동모임장");
            else groupMemberDto.setRoleToString("모임원");

            groupMemberDtoList.add(groupMemberDto);
        }

        log.info("groupMemberDtoList.size" + groupMemberDtoList.size());

        return groupMemberDtoList;
    }

    @Transactional
    @Override
    public boolean groupMemberIsChairman(Long groupWalletId, Long memberId) {
        Participation participation = participationRep.findByMemberIdAndRoleAndGroupWallet_GroupWalletId(memberId, Role.CHAIRMAN, groupWalletId);
        if(participation != null)
            return true;

        return false;
    }

    @Override
    public InstallmentDto getInstallmentDtoSaving(GroupWallet groupWallet) {
        InstallmentSaving installmentSaving = installmentSavingRep.findByGroupWalletAndDone(groupWallet, false);
        Saving saving = savingRep.findBySavingId(installmentSaving.getInstallmentId());

        InstallmentDto installmentDto = new InstallmentDto();

        // dto로 변환
        installmentDto.setSavingName(saving.getName());
        installmentDto.setInterestRate(saving.getInterestRate());
        installmentDto.setPeriod(saving.getPeriod());

        installmentDto.setInsertDate(installmentSaving.getInsertDate());
        installmentDto.setMaturityDate(installmentSaving.getMaturityDate());
        installmentDto.setTotalAmount(installmentSaving.getTotalAmount());
        installmentDto.setSavingDate(installmentSaving.getSavingDate());
        installmentDto.setSavingAmount(installmentSaving.getSavingAmount());

        return installmentDto;
    }

    @Override
    public List<CardIssuanceDto> getCardIssuanceDto(Long walletId) {
        List<CardIssuanceDto> cardIssuanceDtoList = new ArrayList<>();
        List<CardIssuance> cardIssuanceList = cardIssuanceRep.findByWalletIdAndWalletTypeAndCardState(walletId, WalletType.GROUP_WALLET, CardState.OK);

        int i = 0;
        for(CardIssuance c : cardIssuanceList){
            CardIssuanceDto cdto = new CardIssuanceDto();
            cdto.setCardNumber(c.getCardNumber());
            cdto.setCardState(c.getCardState());
            cdto.setWalletId(c.getWalletId());
            cdto.setWalletType(c.getWalletType());
            cdto.setMember(c.getMember());
            cardIssuanceDtoList.add(cdto);
        }

        return cardIssuanceDtoList;
    }
}
