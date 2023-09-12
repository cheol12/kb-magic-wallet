package kb04.team02.web.mvc.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 회원가입 시 입력해야 하는 정보
 */
@Getter
@Setter
@NoArgsConstructor
public class MemberRegisterDto {
    private String id;
    private String password;
    private String name;
    private String city;
    private String street;
    private String zipcode;
    private String phoneNumber;
    private String email;
    private String payPassword;
    private String bankAccount;
}
