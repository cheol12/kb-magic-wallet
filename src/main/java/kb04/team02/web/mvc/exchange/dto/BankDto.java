package kb04.team02.web.mvc.exchange.dto;

import kb04.team02.web.mvc.exchange.entity.Bank;
import kb04.team02.web.mvc.member.entity.Address;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BankDto {
    private Long bankId;
    private String name;
    private Address address;

    public static BankDto toBankDto(Bank bank){
        return BankDto.builder()
                .bankId(bank.getBankId())
                .name(bank.getName())
                .address(bank.getAddress())
                .build();
    }



}
