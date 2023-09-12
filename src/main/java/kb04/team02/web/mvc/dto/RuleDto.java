package kb04.team02.web.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RuleDto {
    private Long ruleId;
    private String ruleName;
    private Long duePrice;
    private LocalDate dueDate;

}
