package kb04.team02.web.mvc.service.exchange;

import kb04.team02.web.mvc.dto.BankDto;
import kb04.team02.web.mvc.dto.OfflineReceiptDto;
import kb04.team02.web.mvc.dto.WalletDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ExchangeServiceImpl implements ExchangeService{

    @Override
    public List<BankDto> bankList() {
        return null;
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
    public int requestOfflineReceipt() {
        return 0;
    }

    @Override
    public List<OfflineReceiptDto> cancelOfflineReceipt() {
        return null;
    }
}
