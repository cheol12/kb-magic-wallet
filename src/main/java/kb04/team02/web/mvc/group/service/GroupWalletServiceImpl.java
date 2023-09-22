package kb04.team02.web.mvc.group.service;

import kb04.team02.web.mvc.common.dto.LoginMemberDto;
import kb04.team02.web.mvc.common.dto.WalletDetailDto;
import kb04.team02.web.mvc.common.dto.WalletHistoryDto;
import kb04.team02.web.mvc.common.entity.*;
import kb04.team02.web.mvc.common.exception.InsufficientBalanceException;
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

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GroupWalletServiceImpl implements GroupWalletService {
    private final DuePaymentRepository duePaymentRepository;

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

        groupForeignBalanceRep.save(
                GroupWalletForeignCurrencyBalance.builder()
                        .currencyCode(CurrencyCode.USD)
                        .balance(0L)
                        .groupWallet(groupWalletSave)
                        .build()
        );

        groupForeignBalanceRep.save(
                GroupWalletForeignCurrencyBalance.builder()
                        .currencyCode(CurrencyCode.JPY)
                        .balance(0L)
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
            if(exchange.getBuyCurrencyCode() == CurrencyCode.KRW){
                walletHistoryDto.setType("재환전");
            }
            else{
                walletHistoryDto.setType("환전");
            }
            String detail = exchange.getSellCurrencyCode().name() + " ➜ " + exchange.getBuyCurrencyCode().name();

            walletHistoryDto.setCurrencyCode(exchange.getBuyCurrencyCode());
            walletHistoryDto.setDetail(detail);
            walletHistoryDto.setAmount(exchange.getSellAmount() + " " + exchange.getSellCurrencyCode() + " ➜ " + exchange.getBuyAmount() + " " + exchange.getBuyCurrencyCode().name());
            walletHistoryDto.setBalance(exchange.getAfterSellBalance() + " " + exchange.getSellCurrencyCode() + " / " + exchange.getAfterBuyBalance() + " " + exchange.getBuyCurrencyCode().name());

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

            walletHistoryDto.setCurrencyCode(payment.getCurrencyCode());
            walletHistoryDto.setDetail(detail);
            walletHistoryDto.setAmount(payment.getAmount().toString() + " " + payment.getCurrencyCode().name());
            walletHistoryDto.setBalance(payment.getAfterPayBalance().toString() + " " + payment.getCurrencyCode().name());

            dto.getList().add(walletHistoryDto);
        }

        // 이체 내역 설정
        for(Transfer transfer : transferList){
            WalletHistoryDto walletHistoryDto = new WalletHistoryDto();
            walletHistoryDto.setDateTime(transfer.getInsertDate());
            String detail = "";
            if(transfer.getTransferType() == TransferType.DEPOSIT){
                walletHistoryDto.setType("입금");
                detail = transfer.getSrc() + " ➜ " + transfer.getDest();
            }
            else{
                walletHistoryDto.setType("출금");
                detail = "모임지갑 : " + groupWallet.getNickname() + " ➜ " + transfer.getDest();
            }
            walletHistoryDto.setCurrencyCode(CurrencyCode.KRW);
            walletHistoryDto.setDetail(detail);
            walletHistoryDto.setAmount(transfer.getAmount().toString() + transfer.getCurrencyCode().name());
            walletHistoryDto.setBalance(transfer.getAfterBalance().toString() + transfer.getCurrencyCode().name());

            dto.getList().add(walletHistoryDto);
        }
        dto.getList().sort(new Comparator<WalletHistoryDto>() {
            @Override
            public int compare(WalletHistoryDto o1, WalletHistoryDto o2) {
                return o2.getDateTime().compareTo(o1.getDateTime());
            }
        });

        return dto;
    }

    @Override
    public int deleteGroupWallet(Long groupWalletId) throws WalletDeleteException {
        GroupWallet groupWallet = groupWalletRep.findById(groupWalletId).orElseThrow(()-> new NoSuchElementException("모임 지갑 조회 실패"));
        Member member = groupWallet.getMember();
        Participation participation = participationRep.findByGroupWalletAndMemberId(groupWallet, member.getMemberId());
        List<Participation> participations = participationRep.findByGroupWalletAndParticipationState(groupWallet, ParticipationState.PARTICIPATED);
        List<GroupWalletForeignCurrencyBalance> groupWalletForeignCurrencyBalances = groupForeignBalanceRep.findByGroupWallet(groupWallet);
        InstallmentSaving installmentSaving = installmentSavingRep.findByGroupWalletAndDone(groupWallet, false);

        if (participations.size() > 1 && participation.getRole() == Role.CHAIRMAN) throw new WalletDeleteException("모임원이 있는 상태에서 삭제할 수 없습니다");
        if (groupWallet.getBalance() > 0) throw new WalletDeleteException("지갑 잔액이 남아있는 경우 삭제할 수 없습니다.");
        if (installmentSaving != null) throw new WalletDeleteException("생성한 적금이 있는 경우 삭제할 수 없습니다.");
        for(GroupWalletForeignCurrencyBalance g : groupWalletForeignCurrencyBalances){
            if(g.getBalance() > 0) throw new WalletDeleteException("지갑 잔액이 남아있는 경우 삭제할 수 없습니다." + g.getCurrencyCode());
        }

        // 삭제
        groupWalletRep.deleteGroupWalletByGroupWalletId(groupWalletId);


        return 1;
    }

    @Override
    public int groupWalletMemberOut(Long groupWalletId, Long memberId) {

        // 모임원 내보내기 or 탈퇴
//        int result = groupWalletRep.deleteByGroupWalletIdAndMember(groupWalletId, memberId);
        GroupWallet groupWallet = groupWalletRep.findById(groupWalletId).orElseThrow(()->new NoSuchElementException("멤버 조회 실패"));
        Participation participation = participationRep.findByGroupWalletAndMemberId(groupWallet, memberId);
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
            ruleDto.setDue(groupWallet.getDue());
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
    public void groupWalletWithdraw(TransferDto transferDto) throws NotEnoughBalanceException {
        Long walletId = transferDto.getGroupWalletId();
        GroupWallet groupWallet = groupWalletRep.findById(walletId).orElseThrow(()->new NoSuchElementException("모임 지갑 조회 실패"));
        Member member = memberRep.findById(transferDto.getMemberId()).orElseThrow(()->new NoSuchElementException("멤버 조회 실패"));

        PersonalWallet personalWallet = personalWalletRep.findByMember(member);

        Long KRW = groupWallet.getBalance();
        Long amount = transferDto.getAmount();
        System.out.println("KRW = " + KRW);
        System.out.println("amount = " + amount);
        Long afterBalance = KRW - amount;
        System.out.println("afterBalance = " + afterBalance);
        if (afterBalance < 0) {
            throw new InsufficientBalanceException("모임지갑의 잔액이 부족합니다.");
        }

        GroupWalletTransfer withdraw = GroupWalletTransfer.builder()
                .groupWallet(groupWallet)
                .transferType(TransferType.WITHDRAW)
                .fromType(TargetType.GROUP_WALLET)
                .toType(TargetType.PERSONAL_WALLET)
                .src(groupWallet.getNickname())
                .dest(member.getName())
                .amount(amount)
                .afterBalance(afterBalance)
                .currencyCode(CurrencyCode.KRW)
                .build();
        groupWallet.setBalance(afterBalance);
        personalWallet.setBalance(personalWallet.getBalance() + amount);
        groupTransferRep.save(withdraw);
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
    public void groupWalletDeposit(TransferDto transferDto) throws NotEnoughBalanceException {

        Long groupWalletId = transferDto.getGroupWalletId();
        GroupWallet groupWallet = groupWalletRep.findById(groupWalletId).orElseThrow(()->new NoSuchElementException("모임 지갑 조회 실패"));

        Member member = memberRep.findById(transferDto.getMemberId()).orElseThrow(()->new NoSuchElementException("회원 조회 실패"));
        PersonalWallet personalWallet = personalWalletRep.findByMember(member);

        Long balance = personalWallet.getBalance();

        Long KRW = groupWallet.getBalance();
        Long amount = transferDto.getAmount();
        System.out.println("KRW = " + KRW);
        System.out.println("amount = " + amount);

        Long afterBalance = KRW + amount;

        GroupWalletTransfer groupWalletTransfer = GroupWalletTransfer.builder()
                .groupWallet(groupWallet)
                .transferType(TransferType.DEPOSIT)
                .fromType(TargetType.PERSONAL_WALLET)
                .toType(TargetType.GROUP_WALLET)
                .src(member.getName() + "의 개인지갑")
                .dest(groupWallet.getNickname())
                .amount(amount)
                .afterBalance(afterBalance)
                .currencyCode(CurrencyCode.KRW)
                .build();
        groupWallet.setBalance(afterBalance);
        personalWallet.setBalance(personalWallet.getBalance() - amount);
        groupTransferRep.save(groupWalletTransfer);
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
            groupMemberDto.setMemberId(p.getMemberId());
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
        Saving saving = installmentSaving.getSaving();
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

    @Override
    public boolean isChairmanGroupWalletList(LoginMemberDto loginMemberDto) {
        Long memberId = loginMemberDto.getMemberId();
        List<Role> roles = new ArrayList<Role>();
        roles.add(Role.CHAIRMAN);
        roles.add(Role.CO_CHAIRMAN);
        ParticipationState participationState = ParticipationState.PARTICIPATED;

        List<Participation> chairmanList = participationRep.findByMemberIdAndRoleInAndParticipationState(memberId, roles, participationState);
        if (chairmanList.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<GroupWallet> getChairmanGroupWalletList(LoginMemberDto loginMemberDto) {
        Long memberId = loginMemberDto.getMemberId();
        List<Role> roles = new ArrayList<Role>();
        roles.add(Role.CHAIRMAN);
        roles.add(Role.CO_CHAIRMAN);
        ParticipationState participationState = ParticipationState.PARTICIPATED;

        List<Participation> chairmanList = participationRep.findByMemberIdAndRoleInAndParticipationState(memberId, roles, participationState);
        List<GroupWallet> groupWalletList = new ArrayList<GroupWallet>();

        for (Participation participation : chairmanList) {
            GroupWallet groupWallet = groupWalletRep.findByGroupWalletId(participation.getGroupWallet().getGroupWalletId());
            groupWalletList.add(groupWallet);
        }
        System.out.println(groupWalletList);

        return groupWalletList;

    }

    @Override
    public int countGroupWalletMember(Long groupWalletId) {
        int result = participationRep.countByGroupWallet_GroupWalletIdAndParticipationState(groupWalletId, ParticipationState.PARTICIPATED);
        return result;
    }

    // 초대 목록 부르기
    @Override
    public List<InvitedDto> getGroupListInvitedMe(Long memberId) {
        List<InvitedDto> InvitedDtoList = new ArrayList<>();

        // 참여자 테이블에서 내가 대기 상태인 모임지갑을 부른다. .getGroupWalletId
        List<Participation> participationList
                = participationRep.findByMemberIdAndParticipationState(memberId, ParticipationState.WAITING);

        for (Participation participation : participationList) {
            GroupWallet groupWallet = participation.getGroupWallet();
            if (groupWallet != null) {
                InvitedDto invitedDto = new InvitedDto();
                invitedDto.setNickname(groupWallet.getNickname());
                invitedDto.setGroupWalletId(groupWallet.getGroupWalletId());
                invitedDto.setChairmanName(groupWallet.getMember().getName());
                invitedDto.setMemberCount(this.countGroupWalletMember(invitedDto.getGroupWalletId()));

                InvitedDtoList.add(invitedDto);
            }
        }

        return InvitedDtoList;
    }

    // 초대하기
    @Override
    public int inviteMember(String phone, Long groupWalletId) {
        Member member = memberRep.findByPhoneNumber(phone).orElseThrow(()-> new NoSuchElementException("멤버 조회 실패"));

        GroupWallet groupWallet = groupWalletRep.findByGroupWalletId(groupWalletId);
        Participation participation = participationRep.findByGroupWalletAndMemberId(groupWallet, member.getMemberId());

        int countParticipation = participationRep.countByGroupWalletAndMemberId(groupWallet, member.getMemberId());

        if(countParticipation>0){
            return 0;
        }
        participation = participationRep.save(
                Participation.builder()
//                        .participationId(participation.getParticipationId())
                        .participationState(ParticipationState.WAITING)
                        .memberId(member.getMemberId())
                        .role(Role.GENERAL)
                        .groupWallet(groupWallet)
                        .build()
        );
        return 1;
    }

    // 초대 수락하기
    @Override
    public int invitedAccept(Long groupWalletId, Long memberId) {
        GroupWallet groupWallet = groupWalletRep.findByGroupWalletId(groupWalletId);
        Participation participation = participationRep.findByGroupWalletAndMemberId(groupWallet, memberId);

        if(participation != null && participation.getParticipationState().equals(ParticipationState.WAITING)){
            participation.setParticipationState(ParticipationState.PARTICIPATED);
            return 1;
        }
        return 0;
    }

    // 초대 거절하기
    @Override
    public int invitedRefuse(Long groupWalletId, Long memberId) {
        GroupWallet groupWallet = groupWalletRep.findByGroupWalletId(groupWalletId);
        Participation participation = participationRep.findByGroupWalletAndMemberId(groupWallet, memberId);
        if(participation != null){
            participationRep.delete(participation);
            return 1;
        }
        return 0;
    }

    //
    @Override
    public void payDue(Long id, Long memberId) {
        GroupWallet groupWallet = groupWalletRep.findById(id).orElseThrow(
                () -> new NoSuchElementException("모임지갑 조회 실패")
        );
        Long amount = groupWallet.getDue();

        Member member = memberRep.findById(memberId).orElseThrow(
                () -> new NoSuchElementException("멤버 조회 실패")
        );

        duePaymentRepository.save(
                DuePayment.builder()
                        .groupWallet(groupWallet)
                        .member(member)
//                .due(amount)
                        .build()
        );

        DepositDto depositDto = DepositDto.builder()
                .currencyCode(CurrencyCode.KRW)
                .amount(amount)
                .srcMemberId(memberId)
                .destWalletId(id)
                .build();
        TransferDto transferDto = new TransferDto();
        transferDto.setAmount(amount);
        transferDto.setGroupWalletId(id);
        transferDto.setMemberId(memberId);

        try {
            groupWalletDeposit(transferDto);
        } catch (NotEnoughBalanceException e) {
            throw new RuntimeException(e);
        }
    }
}
