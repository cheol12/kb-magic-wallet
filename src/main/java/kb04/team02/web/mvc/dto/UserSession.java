package kb04.team02.web.mvc.dto;

import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * 세션 스토리지에 저장할 객체
 */
public class UserSession {
    private String id;
    private String name;
    private String bankAccount;
    private String cardNumber;
}
