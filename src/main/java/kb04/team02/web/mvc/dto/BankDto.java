package kb04.team02.web.mvc.dto;

import kb04.team02.web.mvc.domain.bank.Bank;
import kb04.team02.web.mvc.domain.member.Address;
import lombok.*;

import java.util.List;

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
