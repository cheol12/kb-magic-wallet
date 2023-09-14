package kb04.team02.web.mvc.exchange.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RuleDto {
//    private Long ruleId;
//    private String ruleName;
    private Long duePrice;
    private int dueDate;

}





//== 수정사항 ==//
// RULE NAME 과 RULE ID는 어떤 값을 의미하는가? (현지님)
// dueDate의 수정 LocalDateTime => int(날짜)