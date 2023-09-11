package kb04.team02.web.mvc.repository.saving;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.annotation.Commit;

import static org.junit.jupiter.api.Assertions.*;

@Commit
@SpringBootTest
class InstallmentSavingRepositoryTest {

    @Autowired
    private InstallmentSavingRepository installmentSavingRepository;

    @AfterEach
    public void afterEach() {
        installmentSavingRepository.deleteAll();
    }

}