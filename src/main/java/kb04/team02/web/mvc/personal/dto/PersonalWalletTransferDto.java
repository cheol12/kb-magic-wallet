package kb04.team02.web.mvc.personal.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonalWalletTransferDto {
    @ApiModelProperty(example = "회원 id")
    private Long MemberId;
    @ApiModelProperty(example = "개인지갑 거래 금액")
    private Long amount;
}
