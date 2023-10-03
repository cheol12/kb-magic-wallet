package kb04.team02.web.mvc.member.dto;


import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(example = "회원 아이디")
    private String id;
    @ApiModelProperty(example = "회원 비밀번호")
    private String password;
    @ApiModelProperty(example = "이름")
    private String name;
    @ApiModelProperty(example = "도시")
    private String city;
    @ApiModelProperty(example = "도로명")
    private String street;
    @ApiModelProperty(example = "우편번호")
    private String zipcode;
    @ApiModelProperty(example = "핸드폰 번호")
    private String phoneNumber;
    @ApiModelProperty(example = "이메일")
    private String email;
    @ApiModelProperty(example = "결제 비밀번호")
    private String payPassword;
    @ApiModelProperty(example = "계좌번호")
    private String bankAccount;
}
