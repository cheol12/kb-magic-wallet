package kb04.team02.web.mvc.dto;

import lombok.*;

/**
 * 세션 스토리지에 저장할 객체
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginMemberDto {
    private Long memberId;
    private String id;
    private String name;
    private String bankAccount;
    private String cardNumber;
}
