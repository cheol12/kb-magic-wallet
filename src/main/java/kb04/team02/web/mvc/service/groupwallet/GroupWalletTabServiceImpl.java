package kb04.team02.web.mvc.service.groupwallet;

import kb04.team02.web.mvc.domain.card.CardIssuance;
import kb04.team02.web.mvc.domain.card.CardState;
import kb04.team02.web.mvc.domain.common.CurrencyCode;
import kb04.team02.web.mvc.domain.member.Member;
import kb04.team02.web.mvc.domain.member.Role;
import kb04.team02.web.mvc.domain.saving.InstallmentSaving;
import kb04.team02.web.mvc.domain.wallet.common.PaymentType;
import kb04.team02.web.mvc.domain.wallet.common.Transfer;
import kb04.team02.web.mvc.domain.wallet.common.TransferType;
import kb04.team02.web.mvc.domain.wallet.common.WalletType;
import kb04.team02.web.mvc.domain.wallet.group.*;
import kb04.team02.web.mvc.dto.*;
import kb04.team02.web.mvc.repository.card.CardIssuanceRepository;
import kb04.team02.web.mvc.repository.member.MemberRepository;
import kb04.team02.web.mvc.repository.saving.InstallmentSavingRepository;
import kb04.team02.web.mvc.repository.wallet.group.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GroupWalletTabServiceImpl implements GroupWalletTabService {

    // 회원 관련 의존성
    private final MemberRepository memberRepository;

    // 모임지갑 관련 의존성
    private final GroupWalletRespository groupWalletRespository;
    private final ParticipationRepository participationRepository;

    // 적금 관련 의존성
    private final InstallmentSavingRepository installmentRepository;

    // 카드 관련 의존성
    private final CardIssuanceRepository cardRepository;

    // 내역 관련 의존성
    private final GroupWalletTransferRepository transferRepository;
    private final GroupWalletExchangeRepository exchangeRepository;
    private final GroupWalletPaymentRepository paymentRepository;

    @Override

    public List<GroupMemberDto> getMembersByGroupId(Long id, Pageable pageable) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElse(null);
        List<Participation> list = participationRepository.findByGroupWalletAndParticipationState(groupWallet, ParticipationState.PARTICIPATED);
        List<GroupMemberDto> dtoList = new ArrayList<>();
        for (Participation participation : list) {
            Member member = memberRepository.findById(participation.getMemberId()).orElse(null);
            dtoList.add(GroupMemberDto.builder()
                    .memberId(member.getMemberId())
                    .name(member.getName())
                    .build());
        }
        return dtoList;
    }


    @Override
    public boolean deleteMember(Long id, Long member) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElse(null);
        Participation participation = participationRepository.findByGroupWalletAndMemberId(groupWallet, member);
        participationRepository.delete(participation);
        return true;
    }

    @Override
    public boolean grantMemberAuth(Long id, Long member) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElse(null);
        Participation participation = participationRepository.findByGroupWalletAndMemberId(groupWallet, member);
        participation.setRole(Role.CO_CHAIRMAN);
        return true;
    }

    @Override
    public boolean revokeMemberAuth(Long id, Long member) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElse(null);
        Participation participation = participationRepository.findByGroupWalletAndMemberId(groupWallet, member);
        participation.setRole(Role.GENERAL);
        return true;
    }

    @Override
    public RuleDto getRuleById(Long id) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElse(null);
        if (groupWallet == null) {
            throw new NoResultException("해당 모임지갑이 존재하지 않습니다");
        } else {
            return RuleDto.builder()
                    .duePrice(groupWallet.getDue())
                    .dueDate(groupWallet.getDueDate())
                    .build();
        }
    }

    @Override
    public boolean createRule(Long id, RuleDto ruleDto) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElse(null);
        if (groupWallet == null) {
            throw new NoResultException("해당 모임지갑이 존재하지 않습니다");
        } else {
            groupWallet.setDueCondition(true);
            groupWallet.setDueDate(ruleDto.getDueDate());
            groupWallet.setDueAccumulation(0L);
            groupWallet.setDue(ruleDto.getDuePrice());
        }
        return true;
    }

    @Override
    public boolean alertMember(Long id, Long member) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElse(null);
        if (groupWallet == null) {
            throw new NoResultException("해당 모임지갑이 존재하지 않습니다");
        } else {
            // 알람?
        }
        return true;
    }

    @Override
    public boolean deleteRule(Long id) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElse(null);
        if (groupWallet == null) {
            throw new NoResultException("해당 모임지갑이 존재하지 않습니다");
        } else {
            groupWallet.setDueCondition(false);
        }
        return true;
    }

    @Override
    public InstallmentDto getSavingById(Long id) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElse(null);
        if (groupWallet == null) {
            throw new NoResultException("해당 모임지갑이 존재하지 않습니다");
        } else {
            InstallmentSaving saving = installmentRepository.findByGroupWalletAndDone(groupWallet, false);

            return InstallmentDto.builder()
                    .savingName(saving.getSaving().getName())
                    .interestRate(saving.getSaving().getInterestRate())
                    .period(saving.getSaving().getPeriod())
                    .insertDate(saving.getInsertDate())
                    .maturityDate(saving.getMaturityDate())
                    .totalAmount(saving.getTotalAmount())
                    .savingDate(saving.getSavingDate())
                    .savingAmount(saving.getSavingAmount())
                    .build();
        }
    }

    @Override
    @Transactional
    public boolean cancelSaving(Long id) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElse(null);
        if (groupWallet == null) {
            throw new NoResultException("해당 모임지갑이 존재하지 않습니다");
        } else {
            InstallmentSaving saving = installmentRepository.findByGroupWalletAndDone(groupWallet, false);
            saving.setDone(true);
            groupWallet.setBalance(groupWallet.getBalance() + saving.getTotalAmount());
        }
        return true;
    }

    @Override
    public List<CardIssuanceDto> getCard(Long id) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElse(null);
        if (groupWallet == null) {
            throw new NoResultException("해당 모임지갑이 존재하지 않습니다");
        } else {
            List<CardIssuance> cardList = cardRepository.findAllByWalletId(groupWallet.getGroupWalletId());
            List<CardIssuanceDto> cardIssuanceDtos
                    = new ArrayList<>();
            for (CardIssuance card : cardList) {
                cardIssuanceDtos.add(CardIssuanceDto.builder()
                        .cardIssuanceId(card.getCardIssuanceId())
                        .cardNumber(card.getCardNumber())
                        .cardState(card.getCardState())
                        .walletId(card.getWalletId())
                        .walletType(card.getWalletType())
                        .build());
            }
            return cardIssuanceDtos;
        }
    }

    @Override
    public boolean linkCard(Long id, Long memberId) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElse(null);
        if (groupWallet == null) {
            throw new NoResultException("해당 모임지갑이 존재하지 않습니다");
        } else {
            Member member = memberRepository.findById(memberId).orElse(null);
            CardIssuance cardIssuance = cardRepository.findByMemberAndCardState(member, CardState.OK);
            cardIssuance.setWalletId(groupWallet.getGroupWalletId());
            cardIssuance.setWalletType(WalletType.GROUP_WALLET);
        }
        return true;
    }

    @Override
    public Page<WalletHistoryDto> getHistoryByGroupId(Long id, Pageable page) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElse(null);

        List<GroupWalletPayment> groupWalletPayments
                = paymentRepository.searchAllByGroupWallet(groupWallet);
        List<GroupWalletExchange> groupWalletExchanges
                = exchangeRepository.searchAllByGroupWallet(groupWallet);
        List<GroupWalletTransfer> groupWalletTransfers
                = transferRepository.searchAllByGroupWallet(groupWallet);

        List<WalletHistoryDto> list = new ArrayList<>();

        for (Transfer transfer : groupWalletTransfers) {
            WalletHistoryDto historyDto = new WalletHistoryDto();
            historyDto.setDateTime(transfer.getInsertDate());
            if (transfer.getTransferType() == TransferType.DEPOSIT) {
                historyDto.setType("출금");
            } else {
                historyDto.setType("입금");
            }
            String detail = transfer.getSrc() + " > " + transfer.getDest();
            historyDto.setDetail(detail);
            historyDto.setAmount(transfer.getAmount().toString());
            historyDto.setBalance(transfer.getAfterBalance().toString());

            list.add(historyDto);
        }

        for (GroupWalletExchange exchange : groupWalletExchanges) {
            WalletHistoryDto historyDto = new WalletHistoryDto();
            historyDto.setDateTime(exchange.getInsertDate());
            if (exchange.getBuyCurrencyCode() == CurrencyCode.KRW) {
                historyDto.setType("재환전");
            } else {
                historyDto.setType("환전");
            }
            String detail = exchange.getSellCurrencyCode().name() + " > " + exchange.getBuyCurrencyCode().name();

            historyDto.setDetail(detail);
            historyDto.setAmount(exchange.getSellAmount() + " > " + exchange.getBuyAmount());
            historyDto.setBalance(exchange.getAfterSellBalance() + " : " + exchange.getAfterBuyBalance());

            list.add(historyDto);
        }

        for (GroupWalletPayment payment : groupWalletPayments) {
            WalletHistoryDto historyDto = new WalletHistoryDto();
            historyDto.setDateTime(payment.getInsertDate());
            if (payment.getPaymentType() == PaymentType.OK) {
                historyDto.setType("결제");
            } else {
                historyDto.setType("취소");
            }
            String detail = payment.getAmount() + payment.getCurrencyCode().name();

            historyDto.setDetail(detail);
            historyDto.setAmount(payment.getAmount().toString());
            historyDto.setBalance(payment.getAfterPayBalance().toString());

            list.add(historyDto);
        }
        list.sort(new Comparator<WalletHistoryDto>() {
            @Override
            public int compare(WalletHistoryDto o1, WalletHistoryDto o2) {
                return o2.getDateTime().compareTo(o1.getDateTime());
            }
        });

        return new PageImpl<>(list);
    }

    @Override
    public WalletHistoryDto getHistory(Long id, Long historyId, String type) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElse(null);
        WalletHistoryDto historyDto = new WalletHistoryDto();
        String detail;
        switch (type) {
            case "입금":
            case "출금":
                GroupWalletTransfer transfer = transferRepository.findById(historyId).orElse(null);
                assert transfer != null;
                historyDto.setDateTime(transfer.getInsertDate());
                if (transfer.getTransferType() == TransferType.DEPOSIT) {
                    historyDto.setType("출금");
                } else {
                    historyDto.setType("입금");
                }
                detail = transfer.getSrc() + " > " + transfer.getDest();
                historyDto.setDetail(detail);
                historyDto.setAmount(transfer.getAmount().toString());
                historyDto.setBalance(transfer.getAfterBalance().toString());
                return historyDto;
            case "환전":
            case "재환전":
                GroupWalletExchange exchange = exchangeRepository.findById(historyId).orElse(null);
                assert exchange != null;
                if (exchange.getBuyCurrencyCode() == CurrencyCode.KRW) {
                    historyDto.setType("재환전");
                } else {
                    historyDto.setType("환전");
                }
                historyDto.setDateTime(exchange.getInsertDate());
                detail = exchange.getSellCurrencyCode().name() + " > " + exchange.getBuyCurrencyCode().name();

                historyDto.setDetail(detail);
                historyDto.setAmount(exchange.getSellAmount() + " > " + exchange.getBuyAmount());
                historyDto.setBalance(exchange.getAfterSellBalance() + " : " + exchange.getAfterBuyBalance());
                return historyDto;
            case "결제":
            case "취소":
                GroupWalletPayment payment = paymentRepository.findById(historyId).orElse(null);
                assert payment != null;
                historyDto.setDateTime(payment.getInsertDate());
                if (payment.getPaymentType() == PaymentType.OK) {
                    historyDto.setType("결제");
                } else {
                    historyDto.setType("취소");
                }
                detail = payment.getAmount() + payment.getCurrencyCode().name();
                historyDto.setDetail(detail);
                historyDto.setAmount(payment.getAmount().toString());
                historyDto.setBalance(payment.getAfterPayBalance().toString());
                return historyDto;
            default:
                return null;
        }
    }
}
