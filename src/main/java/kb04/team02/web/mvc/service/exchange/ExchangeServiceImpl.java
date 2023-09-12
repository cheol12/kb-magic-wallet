package kb04.team02.web.mvc.service.exchange;

import kb04.team02.web.mvc.dto.BankDto;
import kb04.team02.web.mvc.dto.ExchangeDto;
import kb04.team02.web.mvc.dto.OfflineReceiptDto;
import kb04.team02.web.mvc.dto.WalletDto;
import kb04.team02.web.mvc.repository.bank.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService{

    private final BankRepository bankRepository;

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
    public List<OfflineReceiptDto> offlineReceiptHistory() {
        return null;
    }

    @Override
    public int requestOfflineReceipt(OfflineReceiptDto offlineReceiptDto) {
        return 0;
    }
    
    @Override
    public List<OfflineReceiptDto> cancelOfflineReceipt() {
        return null;
    }

    @Override
    public int requestExchangeOnline(ExchangeDto exchangeDto) {
        return 0;
    }
}
