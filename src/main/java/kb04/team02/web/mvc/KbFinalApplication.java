package kb04.team02.web.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class KbFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(KbFinalApplication.class, args);
    }

}
