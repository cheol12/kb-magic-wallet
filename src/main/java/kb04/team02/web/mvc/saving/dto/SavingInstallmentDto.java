package kb04.team02.web.mvc.saving.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavingInstallmentDto {

    private Long savingId;
    private Long groupWalletId;
    private Long totalAmount = 0L;
    private int savingDate;
    private Long savingAmount;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime maturityDate;
}
