package kb04.team02.web.mvc.saving.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavingInstallmentDto {

    private Long savingId;
    private Long groupWalletId;
    private Long totalAmount;
    private int savingDate;
    private Long savingAmount;
    private LocalDateTime maturityDate;

}
