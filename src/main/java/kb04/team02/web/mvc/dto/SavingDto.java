package kb04.team02.web.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SavingDto {
    private Long savingId;
    private String savingName;
    private String savingComment;
    private Double interestRate;
    private Integer period;
    private Long amountLimit;
}
