package kb04.team02.web.mvc.exchange.dto;

import io.swagger.annotations.ApiModelProperty;
import kb04.team02.web.mvc.exchange.entity.Bank;
import kb04.team02.web.mvc.member.entity.Address;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BankDto {
    @ApiModelProperty(example = "은행 아이디")
    private Long bankId;
    @ApiModelProperty(example = "은행 지점명")
    private String name;
    @ApiModelProperty(example = "은행 주소")
    private Address address;

    public static BankDto toBankDto(Bank bank){
        return BankDto.builder()
                .bankId(bank.getBankId())
                .name(bank.getName())
                .address(bank.getAddress())
                .build();
    }



}
