package kb04.team02.web.mvc.exchange.dto;

import kb04.team02.web.mvc.member.entity.Role;
import kb04.team02.web.mvc.common.entity.WalletType;
import kb04.team02.web.mvc.group.entity.GroupWallet;
import kb04.team02.web.mvc.personal.entity.PersonalWallet;
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
    private Long walletId;
    private String nickname;
    private Role role;
    private WalletType walletType;

    public static WalletDto toPersoanlDto(PersonalWallet personalWallet){
        return WalletDto.builder()
                .walletId(personalWallet.getPersonalWalletId())
                .nickname("개인지갑")
                .role(Role.CHAIRMAN)
                .walletType(WalletType.PERSONAL_WALLET)
                .build();
    }

    public static WalletDto toGroupDto(GroupWallet groupWallet){
        return WalletDto.builder()
                .walletId(groupWallet.getGroupWalletId())
                .nickname(groupWallet.getNickname())
                .role(null)
                .walletType(WalletType.GROUP_WALLET)
                .build();
    }

}
