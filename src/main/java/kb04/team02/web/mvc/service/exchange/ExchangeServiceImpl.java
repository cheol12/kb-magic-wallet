package kb04.team02.web.mvc.service.exchange;

import kb04.team02.web.mvc.domain.bank.Bank;
import kb04.team02.web.mvc.domain.bank.OfflineReceipt;
import kb04.team02.web.mvc.domain.bank.ReceiptState;
import kb04.team02.web.mvc.domain.member.Role;
import kb04.team02.web.mvc.domain.wallet.common.WalletExchange;
import kb04.team02.web.mvc.domain.wallet.common.WalletType;
import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import kb04.team02.web.mvc.domain.wallet.group.GroupWalletExchange;
import kb04.team02.web.mvc.domain.wallet.personal.PersonalWallet;
import kb04.team02.web.mvc.domain.wallet.personal.PersonalWalletExchange;
import kb04.team02.web.mvc.dto.BankDto;
import kb04.team02.web.mvc.dto.ExchangeDto;
import kb04.team02.web.mvc.dto.OfflineReceiptDto;
import kb04.team02.web.mvc.dto.WalletDto;
import kb04.team02.web.mvc.repository.bank.BankRepository;
import kb04.team02.web.mvc.repository.bank.OfflineReceiptRepository;
import kb04.team02.web.mvc.repository.wallet.group.GroupWalletExchangeRepository;
import kb04.team02.web.mvc.repository.wallet.group.GroupWalletRespository;
import kb04.team02.web.mvc.repository.wallet.personal.PersonalWalletExchangeRepository;
import kb04.team02.web.mvc.repository.wallet.personal.PersonalWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService{

    private final EntityManager em;
    private final BankRepository bankRepository;
    private final OfflineReceiptRepository offlineReceiptRepository;
    private final PersonalWalletRepository personalWalletRepository;
    private final GroupWalletRespository groupWalletRespository;
    private final PersonalWalletExchangeRepository personalWalletExchangeRepository;
    private final GroupWalletExchangeRepository groupWalletExchangeRepository;

    @Override
    public List<BankDto> bankList() {
        List<BankDto> bankList = bankRepository.findAll().stream()
                .map(BankDto::toBankDto)
                .collect(Collectors.toList());

        return bankList;
    }

    @Override
    public List<WalletDto> chairManWalletList() {

        return null;
    }

    @Override
    public Long selectedWalletBalance() {

        return null;
    }

    @Override
    public List<OfflineReceiptDto> offlineReceiptHistory(Long personalWalletId, Map<Long, Role> map) {

        // 개인지갑 -> 환전 내역
        PersonalWallet personalWallet = personalWalletRepository.findById(personalWalletId).orElse(null);
        List<OfflineReceiptDto> pwReceiptHistory = offlineReceiptRepository.findAllByPersonalWallet(personalWallet).stream()
                .map(OfflineReceiptDto::toPersonalOfflineReceiptDto)
                .collect(Collectors.toList());

        // 모임지갑 -> 환전 내역
        List<OfflineReceipt> gwOfflineReceiptList = new ArrayList<>();
        for(Long gwId : map.keySet()){
            GroupWallet groupWallet = groupWalletRespository.findById(gwId).orElse(null);
            gwOfflineReceiptList.addAll(offlineReceiptRepository.findAllByGroupWallet(groupWallet));
        }

        List<OfflineReceiptDto> gwReceiptHistory = gwOfflineReceiptList.stream()
                .map(OfflineReceiptDto::toGroupOfflineReceiptDto)
                .collect(Collectors.toList());


        // 개인지갑 내역 + 모임지갑 내역
        List<OfflineReceiptDto> resList = new ArrayList<>();
        resList.addAll(pwReceiptHistory);
        resList.addAll(gwReceiptHistory);

        return resList;
    }

    @Override
    public int requestOfflineReceipt(OfflineReceiptDto offlineReceiptDto) {

        // balance보다 높은 금액을 신청한 경우 예외 발생

        Bank bank = bankRepository.findById(offlineReceiptDto.getBankId()).get();
        GroupWallet groupWallet = groupWalletRespository.findById(offlineReceiptDto.getOfflineReceiptId()).get();
        PersonalWallet personalWallet = personalWalletRepository.findById(offlineReceiptDto.getPersonalWalletId()).get();

        OfflineReceipt offlineReceipt = offlineReceiptRepository.save(
                OfflineReceipt.builder()
                        .receiptDate(offlineReceiptDto.getReceiptDate())
                        .currencyCode(offlineReceiptDto.getCurrencyCode())
                        .amount(offlineReceiptDto.getAmount())
                        .receiptState(offlineReceiptDto.getReceiptState())
                        .bank(bank)
                        .personalWallet(personalWallet)
                        .groupWallet(groupWallet)
                        .build()
        );

        return 1;
    }
    
    @Override
    public int cancelOfflineReceipt(Long receipt_id) {
        OfflineReceipt offlineReceipt = em.find(OfflineReceipt.class, receipt_id);

        // 예외처리 offlineReceipt == null

        offlineReceipt.setReceiptState(ReceiptState.CANCEL);
        return 1;
    }

    @Override
    public int requestExchangeOnline(ExchangeDto exchangeDto) {

        // balance보다 높은 금액을 신청한 경우 예외 발생
        // 일단 원화 -> 외화 환전일 경우만..


        if(exchangeDto.getWalletType().equals(WalletType.PERSONAL_WALLET)){
            // 개인지갑 -> 환전일 경우
            PersonalWallet personalWallet = personalWalletRepository.findById(exchangeDto.getWalletId()).get();
            PersonalWalletExchange personalWalletExchange = ExchangeDto.toPersonalEntity(exchangeDto, personalWallet);
            personalWalletExchangeRepository.save(personalWalletExchange);
        } else if (exchangeDto.getWalletType().equals(WalletType.GROUP_WALLET)) {
            // 모임지갑 -> 환전일 경우
            GroupWallet groupWallet = groupWalletRespository.findById(exchangeDto.getWalletId()).get();
            GroupWalletExchange groupWalletExchange = ExchangeDto.toGroupEntity(exchangeDto, groupWallet);
            groupWalletExchangeRepository.save(groupWalletExchange);
        }

        // 예외 처리...

        return 1;
    }
}
