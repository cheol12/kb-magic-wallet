package kb04.team02.web.mvc.dto;

import kb04.team02.web.mvc.domain.member.Role;
import kb04.team02.web.mvc.domain.wallet.group.ParticipationState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GroupMemberDto {

    private Long memberId;
    private String id;
    private String name;
    private Role role;
    private ParticipationState participationState;

}
