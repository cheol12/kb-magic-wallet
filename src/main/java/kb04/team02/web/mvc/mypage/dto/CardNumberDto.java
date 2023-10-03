package kb04.team02.web.mvc.mypage.dto;

import io.swagger.annotations.ApiModelProperty;
import kb04.team02.web.mvc.mypage.entity.CardState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CardNumberDto {
    @ApiModelProperty(example = "카드 번호")
    private String cardNumber;
    @ApiModelProperty(example = "카드 상태")
    private CardState cardState;
}
