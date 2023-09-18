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

}
