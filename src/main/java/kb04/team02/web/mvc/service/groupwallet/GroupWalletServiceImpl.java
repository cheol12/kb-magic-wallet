package kb04.team02.web.mvc.service.groupwallet;

import kb04.team02.web.mvc.domain.common.CurrencyCode;
import kb04.team02.web.mvc.domain.member.Member;
import kb04.team02.web.mvc.domain.member.Role;
import kb04.team02.web.mvc.domain.wallet.common.PaymentType;
import kb04.team02.web.mvc.domain.wallet.common.Transfer;
import kb04.team02.web.mvc.domain.wallet.common.TransferType;
import kb04.team02.web.mvc.domain.wallet.common.WalletExchange;
import kb04.team02.web.mvc.domain.wallet.group.*;
import kb04.team02.web.mvc.domain.wallet.personal.PersonalWalletForeignCurrencyBalance;
import kb04.team02.web.mvc.dto.TransferDto;
import kb04.team02.web.mvc.dto.WalletDetailDto;
import kb04.team02.web.mvc.dto.WalletDto;
import kb04.team02.web.mvc.dto.WalletHistoryDto;
import kb04.team02.web.mvc.exception.InsertException;
import kb04.team02.web.mvc.repository.wallet.group.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EnumType;
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
    private final GroupWalletPaymentRepository groupPaymentRep;
    private final GroupWalletTransferRepository groupTransferRep;
    private final ParticipationRepository participationRep;

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

        Participation partici;

        partici = participationRep.save(
                Participation.builder()
                        .participationState(ParticipationState.PARTICIPATED)
                        .memberId(member.getMemberId())
                        .role(Role.CHAIRMAN)
                        .groupWallet(groupWalletSave)
                        .build()
        );

        System.out.println("memberId : " + groupWalletSave.getMember().getMemberId());

        if (groupWalletSave == null || partici == null) {
            throw new InsertException("모임 지갑 생성에 실패했습니다");
        }
        return 1;
    }

    @Override
    public WalletDetailDto getGroupWalletDetail(Long groupWalletId) {
        WalletDetailDto dto = new WalletDetailDto();

        List<GroupWalletExchange> exchangeList = groupExchangeRep.findByGroupWallet(groupWalletId);

        List<GroupWalletForeignCurrencyBalance> foreignCurrencyBalanceList = groupForeignBalanceRep.findByGroupWallet(groupWalletId);

        List<GroupWalletPayment> paymentList = groupPaymentRep.findByGroupWallet(groupWalletId);

        List<GroupWalletTransfer> transferList = groupTransferRep.findByGroupWallet(groupWalletId);

        dto.setBalance(new HashMap<>());

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
    public int deleteGroupWallet(Long groupWalletId) {
        // 모임장이 모임지갑을 삭제하는 것.
        // 모임장인지 확인하는 것은 어디서할지?

        int result = groupWalletRep.deleteGroupWalletByGroupWalletId(groupWalletId);
        return result;
    }

    @Override
    public String inviteMember(Long groupWalletId) {

        // 미완

        return null;
    }

    @Override
    public int groupWalletMemberOut(Long groupWalletId, Member member) {

        // 모임원 내보내기 or 탈퇴

        int result = groupWalletRep.deleteByGroupWalletIdAndMember(groupWalletId, member);
        return result;
    }

    @Override
    public GroupWallet getGroupWalletDueRule(Long groupWalletId, int dueDate, Long due) {
        GroupWallet groupWallet = groupWalletRep.findByGroupWalletId(groupWalletId);

        return groupWallet;
    }

    @Override
    public GroupWallet setGroupWalletDueRule(Long groupWalletId, GroupWallet groupWallet) {
        // 모임 회비 규칙 생성은 모임장만!
        // 모임장 권한 확인은 어디서 할지 생각

        GroupWallet groupWallet01 = groupWalletRep.findByGroupWalletId(groupWalletId);
        groupWallet01.setDueCondition(true);
        groupWallet01.setDueDate(groupWallet.getDueDate());
        groupWallet01.setDue(groupWallet.getDue());
        return groupWallet01;
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


    @Override
    public int groupWalletWithdraw(TransferDto transferDto) {


        return 0;
    }

    @Override
    public int settle(TransferDto transferDto) {
        return 0;
    }

    @Override
    public int groupWalletDeposit(TransferDto transferDto) {
        return 0;
    }
}
