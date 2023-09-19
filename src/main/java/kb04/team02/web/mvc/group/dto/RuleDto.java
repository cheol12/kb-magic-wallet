package kb04.team02.web.mvc.group.dto;

import com.sun.istack.NotNull;
import kb04.team02.web.mvc.common.module.BooleanToYNConverter;
import lombok.*;

import javax.persistence.Convert;
import javax.persistence.Converter;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RuleDto {
    private String nickname;// 지갑 별칭
    @Convert(converter = BooleanToYNConverter.class)
    private boolean dueCondition;// 회비 상태
    private Long dueAccumulation;// 누적 회비
    private int dueDate;// 회비 납부일
    private Long due;// 회비
}





//== 수정사항 ==//
// RULE NAME 과 RULE ID는 어떤 값을 의미하는가? (현지님)
// dueDate의 수정 LocalDateTime => int(날짜)