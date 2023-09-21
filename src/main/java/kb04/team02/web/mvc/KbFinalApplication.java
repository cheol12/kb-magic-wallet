package kb04.team02.web.mvc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KbFinalApplication implements CommandLineRunner {

    private final DataLoader dataLoader;

    public KbFinalApplication(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public static void main(String[] args) {
        SpringApplication.run(KbFinalApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // 애플리케이션 시작 시 DataLoader를 호출하여 초기 데이터를 로드합니다.
        dataLoader.loadInitialData();
    }
}
