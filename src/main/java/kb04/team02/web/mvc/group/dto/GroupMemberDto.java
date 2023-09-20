package kb04.team02.web.mvc.group.dto;

import kb04.team02.web.mvc.member.entity.Role;
import kb04.team02.web.mvc.group.entity.ParticipationState;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GroupMemberDto {

    private Long memberId;
    private String id;
    private String name;
    private Role role;
    private ParticipationState participationState;
    private String roleToString;

    /**
     * 카드 연결 여부를 위한 필드 추가
     * 작성자 : 김진형
     */
    private boolean cardIsConnect;
}
