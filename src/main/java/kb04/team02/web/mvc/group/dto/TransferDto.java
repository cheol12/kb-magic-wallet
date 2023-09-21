package kb04.team02.web.mvc.group.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransferDto {
    private Long memberId;
    private Long groupWalletId;
    private Long amount;
}
