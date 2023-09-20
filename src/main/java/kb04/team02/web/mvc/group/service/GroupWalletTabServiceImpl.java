package kb04.team02.web.mvc.group.service;

import kb04.team02.web.mvc.common.dto.WalletHistoryDto;
import kb04.team02.web.mvc.group.dto.*;
import kb04.team02.web.mvc.group.entity.*;
import kb04.team02.web.mvc.group.repository.*;
import kb04.team02.web.mvc.mypage.entity.CardIssuance;
import kb04.team02.web.mvc.mypage.entity.CardState;
import kb04.team02.web.mvc.common.entity.CurrencyCode;
import kb04.team02.web.mvc.member.entity.Member;
import kb04.team02.web.mvc.member.entity.Role;
import kb04.team02.web.mvc.saving.entity.InstallmentSaving;
import kb04.team02.web.mvc.common.entity.PaymentType;
import kb04.team02.web.mvc.common.entity.Transfer;
import kb04.team02.web.mvc.common.entity.TransferType;
import kb04.team02.web.mvc.common.entity.WalletType;
import kb04.team02.web.mvc.mypage.repository.CardIssuanceRepository;
import kb04.team02.web.mvc.member.repository.MemberRepository;
import kb04.team02.web.mvc.saving.repository.InstallmentSavingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
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
    private final DuePaymentRepository duePaymentRepository;

    @Override

    public List<GroupMemberDto> getMembersByGroupId(Long id, Pageable pageable) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElseThrow(() -> new NoSuchElementException("모임지갑 조회 실패"));
        List<Participation> list = participationRepository.findByGroupWalletAndParticipationState(groupWallet, ParticipationState.PARTICIPATED);
        List<GroupMemberDto> dtoList = new ArrayList<>();
        for (Participation participation : list) {
            Member member = memberRepository.findById(participation.getMemberId()).orElseThrow(() -> new NoSuchElementException("회원 조회 실패"));
            dtoList.add(GroupMemberDto.builder()
                    .memberId(member.getMemberId())
                    .name(member.getName())
                    .build());
        }
        return dtoList;
    }


    @Override
    public boolean deleteMember(Long id, Long member) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElseThrow(() -> new NoSuchElementException("모임지갑 조회 실패"));
        Participation participation = participationRepository.findByGroupWalletAndMemberId(groupWallet, member);
        participationRepository.delete(participation);
        return true;
    }

    @Override
    public boolean grantMemberAuth(Long id, Long member) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElseThrow(() -> new NoSuchElementException("모임지갑 조회 실패"));
        Participation participation = participationRepository.findByGroupWalletAndMemberId(groupWallet, member);
        participation.setRole(Role.CO_CHAIRMAN);
        return true;
    }

    @Override
    public boolean revokeMemberAuth(Long id, Long member) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElseThrow(() -> new NoSuchElementException("모임지갑 조회 실패"));
        Participation participation = participationRepository.findByGroupWalletAndMemberId(groupWallet, member);
        participation.setRole(Role.GENERAL);
        return true;
    }

    @Override
    public RuleDto getRuleById(Long id, Long memberId) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElseThrow(
                () -> new NoSuchElementException("모임지갑 조회 실패")
        );

        if (!groupWallet.isDueCondition()) {
            return RuleDto.builder()
                    .dueCondition(groupWallet.isDueCondition())
                    .build();
        }

        return RuleDto.builder()
                .nickname(groupWallet.getNickname())
                .dueCondition(groupWallet.isDueCondition())
                .dueAccumulation(groupWallet.getDueAccumulation())
                .dueDate(groupWallet.getDueDate())
                .due(groupWallet.getDue())
                .isChairman(memberId.equals(groupWallet.getMember().getMemberId()))
                .build();
    }

    @Override
    public boolean createRule(Long id, RuleDto ruleDto) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElseThrow(() -> new NoSuchElementException("모임지갑 조회 실패"));
        if (groupWallet == null) {
            throw new NoResultException("해당 모임지갑이 존재하지 않습니다");
        } else {
            groupWallet.setDueCondition(true);
            groupWallet.setDueDate(ruleDto.getDueDate());
            groupWallet.setDueAccumulation(0L);
            groupWallet.setDue(ruleDto.getDue());
        }
        return true;
    }

    @Override
    public boolean alertMember(Long id, Long member) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElseThrow(() -> new NoSuchElementException("모임지갑 조회 실패"));
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
    public InstallmentDto getSavingById(GroupWallet groupWallet) {
//    public InstallmentDto getSavingById(Long id) {
//        GroupWallet groupWallet = groupWalletRespository.findById(id).orElseThrow(()->new NoSuchElementException("모임 지갑 조회 실패"));
        if (groupWallet == null) {
            throw new NoResultException("해당 모임지갑이 존재하지 않습니다");
        } else {
            InstallmentSaving saving = installmentRepository.findByGroupWalletAndDone(groupWallet, false);
            System.out.println("**************************************************");
            // 적금이 만료되었지만 아직 정산이 되지 않은 경우
            if (saving == null) {
                saving = installmentRepository.findByGroupWalletAndTotalAmountIsGreaterThanAndDone(groupWallet, 0L, true);
            }
            // 적금이 만료되지 않았거나 적금이 만료되었더라도 정산이 이루어지지 않은 경우 적금을 확인할 수 있도록 함
            if (saving == null) {
                return null;
            }
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
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElseThrow(() -> new NoSuchElementException("모임지갑 조회 실패"));
        if (groupWallet == null) {
            throw new NoResultException("해당 모임지갑이 존재하지 않습니다");
        } else {
            InstallmentSaving installmentSaving = installmentRepository.findByGroupWalletAndDone(groupWallet, false);
            // 적금이 아직 만료되지 않은 경우
            if (installmentSaving != null) {
                installmentSaving.setDone(true);
                groupWallet.setBalance(groupWallet.getBalance() + installmentSaving.getTotalAmount());
                // 적금이 만료되었지만 아직 정산이 되지 않은 경우 이자율도 챙겨줘야 함
            } else {
                // 이자율 계산
                installmentSaving = installmentRepository.findByGroupWalletAndTotalAmountIsGreaterThanAndDone(groupWallet, 0L, true);
                installmentSaving.setTotalAmount(0L);

                double monthlyInterestRate = installmentSaving.getSaving().getInterestRate() / 12; // 월 이자율
                int numberOfMonths = installmentSaving.getSaving().getPeriod(); // 총 월 수
                double monthlyDeposit = installmentSaving.getSavingAmount(); // 월별 납입 금액
                double totalInterest = 0.0; // 총 이자
                double savingsBalance = groupWallet.getBalance(); // 적금 잔액

                for (int month = 1; month <= numberOfMonths; month++) {
                    // 해당 월의 이자 계산
                    double monthlyInterest = (savingsBalance + monthlyDeposit) * monthlyInterestRate;
                    // 총 이자에 월별 이자 추가
                    totalInterest += monthlyInterest;
                    // 적금 잔액 업데이트
                    savingsBalance = savingsBalance + monthlyDeposit + monthlyInterest;
                }

                System.out.println("총 이자: " + totalInterest);

                groupWallet.setBalance((long) (groupWallet.getBalance() + installmentSaving.getTotalAmount() + totalInterest));
            }
        }
        return true;
    }

    @Override
    public List<CardIssuanceDto> getCard(Long id) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElseThrow(() -> new NoSuchElementException("모임지갑 조회 실패"));
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
    @Transactional
    public boolean linkCard(Long id, Long memberId) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElseThrow(() -> new NoSuchElementException("모임지갑 조회 실패"));
        if (groupWallet == null) {
            throw new NoResultException("해당 모임지갑이 존재하지 않습니다");
        } else {
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException("회원 조회 실패"));
            CardIssuance cardIssuance = cardRepository.findByMemberAndCardState(member, CardState.OK);
            cardIssuance.setWalletId(groupWallet.getGroupWalletId());
            cardIssuance.setWalletType(WalletType.GROUP_WALLET);
        }
        return true;
    }

    @Override
    public Page<WalletHistoryDto> getHistoryByGroupId(Long id, Pageable page) {
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElseThrow(() -> new NoSuchElementException("모임지갑 조회 실패"));

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
        GroupWallet groupWallet = groupWalletRespository.findById(id).orElseThrow(() -> new NoSuchElementException("모임지갑 조회 실패"));
        WalletHistoryDto historyDto = new WalletHistoryDto();
        String detail;
        switch (type) {
            case "입금":
            case "출금":
                GroupWalletTransfer transfer = transferRepository.findById(historyId).orElseThrow(() -> new NoSuchElementException("이체 내역 조회 실패"));
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
                GroupWalletExchange exchange = exchangeRepository.findById(historyId).orElseThrow(() -> new NoSuchElementException("환전 내역 조회 실패"));
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
                GroupWalletPayment payment = paymentRepository.findById(historyId).orElseThrow(() -> new NoSuchElementException("결제 내역 조회 실패"));
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

    @Override
    public List<DuePaymentDto> getDuePaymentList(Long id) {
        List<DuePaymentDto> duePaymentDtoList = new ArrayList<>();

        GroupWallet groupWallet = groupWalletRespository.findById(id).orElseThrow(
                () -> new NoSuchElementException("모임 지갑 조회 실패")
        );

        List<Participation> participationList = participationRepository.findByGroupWalletAndParticipationState(groupWallet, ParticipationState.PARTICIPATED);

        for (Participation p :
                participationList) {
            Member member = memberRepository.findById(p.getMemberId()).orElseThrow(
                    () -> new NoSuchElementException("멤버 조회 실패")
            );
            DuePaymentDto duePaymentDto = DuePaymentDto.builder()
                    .memberId(p.getMemberId())
                    .name(member.getName())
                    .due(groupWallet.getDue())
                    .build();
            duePaymentDto.setDueAccumulation(groupWallet.getDue() * duePaymentRepository.countByGroupWalletAndMember(groupWallet, member));
            DuePayment payed = duePaymentRepository.findByGroupWalletAndMemberAndInsertDateAfter(groupWallet, member, LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIDNIGHT))
                    .orElse(null);
            duePaymentDto.setPayed(payed != null);
            duePaymentDtoList.add(duePaymentDto);
        }

        return duePaymentDtoList;
    }
}
