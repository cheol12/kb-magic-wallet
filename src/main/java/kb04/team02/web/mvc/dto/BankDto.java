package kb04.team02.web.mvc.dto;

import kb04.team02.web.mvc.domain.member.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BankDto {
    private Long bankId;
    private String name;
    private Address address;

}
