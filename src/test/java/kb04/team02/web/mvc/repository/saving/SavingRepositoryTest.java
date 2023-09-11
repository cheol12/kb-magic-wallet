package kb04.team02.web.mvc.repository.saving;

import kb04.team02.web.mvc.domain.saving.Saving;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Commit
class SavingRepositoryTest {

    @Autowired
    private SavingRepository repository;

    @AfterEach
    public void afterEach() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("findAllTest")
    public void findAllTest() throws Exception {
        //given
        Saving saving1 = Saving.builder().savingComment("comment").name("이름").interestRate(1.1).period(1).amountLimit(100000L).build();
        Saving saving2 = Saving.builder().savingComment("comment").name("이름").interestRate(1.1).period(1).amountLimit(100000L).build();
        Saving saving3 = Saving.builder().savingComment("comment").name("이름").interestRate(1.1).period(1).amountLimit(100000L).build();
        repository.save(saving1);
        repository.save(saving2);
        repository.save(saving3);

        //when
        List<Saving> savings = repository.findAll();

        //then
        assertThat(savings.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("savingFindByIdTest")
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void savingFindByIdTest() {

        //given
        Saving saving1 = Saving.builder().savingComment("comment").name("이름").interestRate(1.1).period(1).amountLimit(100000L).build();
        Saving saveResult = repository.save(saving1);
//        repository.flush();

        System.out.println(saving1);
        System.out.println(saveResult);

        //when
        Saving savingFind = repository.findById(saveResult.getSavingId()).get();

        System.out.println(savingFind);
        //then
        System.out.println("==============111");

        assertThat(savingFind).isEqualTo(saveResult);

        System.out.println("==============2222");

    }

}