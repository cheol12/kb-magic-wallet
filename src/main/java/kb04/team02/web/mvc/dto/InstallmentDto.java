package kb04.team02.web.mvc.dto;

import lombok.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class InstallmentDto {
    private String savingName;
    private Double interestRate;
    private int period;
    private LocalDateTime insertDate;
    private LocalDateTime maturityDate;
    private Long totalAmount;
    private int savingDate;
    private Long savingAmount;
}
