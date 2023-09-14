package kb04.team02.web.mvc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonalWalletTransferDto {
    private Long MemberId;
    private Long amount;
}
