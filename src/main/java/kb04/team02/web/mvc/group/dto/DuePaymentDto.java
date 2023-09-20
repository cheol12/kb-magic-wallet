package kb04.team02.web.mvc.group.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@Setter
public class DuePaymentDto {
    private Long memberId;// 멤버 아이디
    private String name;// 이름
    private Boolean payed;// 이번달 회비 납부
    private Long due;// 이번달 회비
    private Long dueAccumulation;// 누적 회비
}
