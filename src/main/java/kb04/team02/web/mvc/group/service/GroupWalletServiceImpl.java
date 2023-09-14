package kb04.team02.web.mvc.group.service;

import kb04.team02.web.mvc.common.dto.WalletDetailDto;
import kb04.team02.web.mvc.common.dto.WalletHistoryDto;
import kb04.team02.web.mvc.common.entity.*;
import kb04.team02.web.mvc.exchange.dto.RuleDto;
import kb04.team02.web.mvc.group.dto.DepositDto;
import kb04.team02.web.mvc.group.dto.SettleDto;
import kb04.team02.web.mvc.group.dto.WithDrawDto;
import kb04.team02.web.mvc.group.entity.*;
import kb04.team02.web.mvc.group.repository.*;
import kb04.team02.web.mvc.member.entity.Member;
import kb04.team02.web.mvc.member.entity.Role;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private final PersonalWalletRepository personalWalletRepository;
    private final MemberRepository memberRepository;
    private final PersonalWalletTransferRepository personalTransferRep;

    private final PersonalWalletService personalWalletService;

    @Override
    public List<GroupWallet> selectAllMyGroupWallet(Member member) {

        List<GroupWallet> groupWalletList = groupWalletRep.findAllByMemberOrderByGroupWalletId(member);

        //

        //groupWalletList 를 for문으로 돌리면서 하나 하나를 WalletDto에 알맞는 객체로 set or save 해서 리턴하도록 하기

        return groupWalletList;
    }

    @Override
    public int createGroupWallet(Member member, String nickname) {

//        GroupWallet groupWallet = groupWalletRep.findByMember(member);

        GroupWallet groupWalletSave;

        groupWalletSave = groupWalletRep.save(
                GroupWallet.builder()
                        .dueCondition(false)
                        .member(member)
                        .nickname(nickname)
                        .build()

        );

        // participation 테이블에도 모임장 데이터를 추가해야하지 않나

        Participation participation;

        participation = participationRep.save(
                Participation.builder()
                        .participationState(ParticipationState.PARTICIPATED)
                        .memberId(member.getMemberId())
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

        GroupWallet groupWallet = groupWalletRep.findById(groupWalletId).orElse(null);

        List<GroupWalletExchange> exchangeList = groupExchangeRep.searchAllByGroupWallet(groupWallet);

        List<GroupWalletForeignCurrencyBalance> foreignCurrencyBalanceList = groupForeignBalanceRep.findByGroupWallet(groupWallet);

        List<GroupWalletPayment> paymentList = groupPaymentRep.searchAllByGroupWallet(groupWallet);

        List<GroupWalletTransfer> transferList = groupTransferRep.searchAllByGroupWallet(groupWallet);

        dto.setBalance(new HashMap<>());

        System.out.println("foreignCurrencyBalanceList = " + foreignCurrencyBalanceList.size());
        // 외화 잔액 내역 설정
        for (GroupWalletForeignCurrencyBalance foreignCurrencyBalance : foreignCurrencyBalanceList) {
            dto.getBalance().put(foreignCurrencyBalance.getCurrencyCode().name(), foreignCurrencyBalance.getBalance());
        }

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
        GroupWallet groupWallet = groupWalletRep.findById(groupWalletId).orElse(null);
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
        GroupWallet groupWallet = groupWalletRep.findById(groupWalletId).orElse(null);
        Participation participation = participationRep.findByGroupWalletAndMemberId(groupWallet, member.getMemberId());
        participationRep.delete(participation);
        return 1;
    }

    @Override
    public GroupWallet setGroupWalletDueRule(Long groupWalletId, int dueDate, Long due) {
        GroupWallet groupWallet = groupWalletRep.findByGroupWalletId(groupWalletId);
        groupWallet.setDueCondition(true);
        groupWallet.setDueDate(dueDate);
        groupWallet.setDue(due);
        return groupWallet;
    }

    @Override
    public RuleDto getGroupWalletDueRule(Long groupWalletId) {
        // 모임 회비 규칙 생성은 모임장만!
        // 모임장 권한 확인은 어디서 할지 생각
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
        GroupWallet groupWallet = groupWalletRep.findById(walletId).orElse(null);
        Member member = memberRepository.findById(withDrawDto.getDestMemberId()).orElse(null);

        PersonalWallet personalWallet = personalWalletRepository.findByMember(member);

        List<GroupWalletForeignCurrencyBalance> foreignCurrencyBalances
                = groupForeignBalanceRep.findByGroupWallet(groupWallet);

        List<PersonalWalletForeignCurrencyBalance> personalWalletForeignCurrencyBalances
                = personalForeignBalanceRep.searchAllByPersonalWallet(personalWallet);

        CurrencyCode code = CurrencyCode.KRW;
        GroupWalletForeignCurrencyBalance currGroupForiegnBalance = null;
        PersonalWalletForeignCurrencyBalance currPersonalForeignBalance = null;

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
                    throw new NotEnoughBalanceException();
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
        GroupWallet groupWallet = groupWalletRep.findById(groupWalletId).orElse(null);
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
                            Member mem = memberRepository.findById(participation.getMemberId()).orElse(null);

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
                            Member mem = memberRepository.findById(participation.getMemberId()).orElse(null);

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
        GroupWallet groupWallet = groupWalletRep.findById(walletId).orElse(null);
        Member member = memberRepository.findById(depositDto.getSrcMemberId()).orElse(null);
        PersonalWallet personalWallet = personalWalletRepository.findByMember(member);

        List<GroupWalletForeignCurrencyBalance> foreignCurrencyBalances
                = groupForeignBalanceRep.findByGroupWallet(groupWallet);

        List<PersonalWalletForeignCurrencyBalance> personalWalletForeignCurrencyBalances
                = personalForeignBalanceRep.searchAllByPersonalWallet(personalWallet);

        CurrencyCode code = CurrencyCode.KRW;
        GroupWalletForeignCurrencyBalance currGroupForiegnBalance = null;
        PersonalWalletForeignCurrencyBalance currPersonalForeignBalance = null;

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

        PersonalWallet personalWallet = personalWalletRepository.findByMember(member);

        List<GroupWalletForeignCurrencyBalance> foreignCurrencyBalances
                = groupForeignBalanceRep.findByGroupWallet(groupWallet);

        List<PersonalWalletForeignCurrencyBalance> personalWalletForeignCurrencyBalances
                = personalForeignBalanceRep.searchAllByPersonalWallet(personalWallet);

        CurrencyCode code = CurrencyCode.KRW;
        GroupWalletForeignCurrencyBalance currGroupForiegnBalance = null;
        PersonalWalletForeignCurrencyBalance currPersonalForeignBalance = null;

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
}
