package kb04.team02.web.mvc.service.exchange;

import kb04.team02.web.mvc.domain.bank.Bank;
import kb04.team02.web.mvc.domain.bank.OfflineReceipt;
import kb04.team02.web.mvc.domain.bank.ReceiptState;
import kb04.team02.web.mvc.domain.common.CurrencyCode;
import kb04.team02.web.mvc.domain.member.Address;
import kb04.team02.web.mvc.dto.BankDto;
import kb04.team02.web.mvc.dto.SavingDto;
import kb04.team02.web.mvc.repository.bank.BankRepository;
import kb04.team02.web.mvc.repository.bank.OfflineReceiptRepository;
import kb04.team02.web.mvc.repository.wallet.group.GroupWalletRespository;
import kb04.team02.web.mvc.repository.wallet.personal.PersonalWalletRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class ExchangeServiceImplTest {

    @Autowired
    private ExchangeService exchangeService;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private OfflineReceiptRepository offlineReceiptRepository;

    @AfterEach
    public void afterEach() {
        bankRepository.deleteAll();
    }

    @Test
    @DisplayName("selectBank")
    void bankList() {
        bankRepository.save(Bank.builder()
                .name("국민은행")
                .address(new Address("서울시 강남구", "삼성로 406","06185"))
                .build());
        bankRepository.save(Bank.builder()
                .name("국민은행")
                .address(new Address("서울시 강남구", "삼성로 407","06185"))
                .build());
        bankRepository.save(Bank.builder()
                .name("국민은행")
                .address(new Address("서울시 강남구", "삼성로 408","06185"))
                .build());
        bankRepository.save(Bank.builder()
                .name("국민은행")
                .address(new Address("서울시 강남구", "삼성로 409","06185"))
                .build());
        List<BankDto> bankDtoList = exchangeService.bankList();
        for (BankDto bankDto : bankDtoList) {
            System.out.println(bankDto.getName());
            System.out.println(bankDto.getAddress().getCity() + bankDto.getAddress().getStreet());
            System.out.println("============================");
        }
    }


    @Test
    @DisplayName("walletList")
    void walletList() {
    }

    @Test
    void selectedWalletBalance() {
    }

    @Test
    @DisplayName("selectOfflineReceiptHistory")
    void testOfflineReceiptHistory() {
    }

    @Test
    void requestOfflineReceipt() {
    }

    @Test
    void cancelOfflineReceipt() {
    }

    @Test
    void requestExchangeOnline() {
    }
}