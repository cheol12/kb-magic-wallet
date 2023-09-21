package kb04.team02.web.mvc.group.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
//@ToString
public class InvitedDto {
    private Long groupWalletId;
    private String nickname;
    private String chairmanName;
    private int memberCount;
}
