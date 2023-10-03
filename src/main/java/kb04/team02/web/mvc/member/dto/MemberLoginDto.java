package kb04.team02.web.mvc.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberLoginDto {
    @ApiModelProperty(example = "회원 아이디")
    private String id;
    @ApiModelProperty(example = "회원 비밀번호")
    private String password;
}
