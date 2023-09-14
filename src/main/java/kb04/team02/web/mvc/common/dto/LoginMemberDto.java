package kb04.team02.web.mvc.common.dto;

import kb04.team02.web.mvc.member.entity.Role;
import lombok.*;

import java.util.Map;

/**
 * 세션 스토리지에 저장할 객체
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginMemberDto {
    private Long memberId;
    private String id;
    private String name;
    private Long personalWalletId;
    Map<Long, Role> groupWalletIdList;
}
