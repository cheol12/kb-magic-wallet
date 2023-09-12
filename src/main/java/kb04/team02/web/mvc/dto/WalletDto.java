package kb04.team02.web.mvc.dto;

import kb04.team02.web.mvc.domain.member.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WalletDto {
    /* WalletDto
     *  - wallet seq
     *  - 권한
     *  - 지갑 이름
     *  - 지갑 구분?
     */
    private Long groupWalletId;
    private String nickname;
    private Role role;
    private String walletType;

}
