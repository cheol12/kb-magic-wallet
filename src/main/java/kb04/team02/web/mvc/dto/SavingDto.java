package kb04.team02.web.mvc.dto;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SavingDto {
    private Long savingId;
    private String name;
    private String savingComment;
    private Double interestRate;
    private Integer period;
    private Long amountLimit;
}
