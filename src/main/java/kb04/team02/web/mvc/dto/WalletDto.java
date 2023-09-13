package kb04.team02.web.mvc.dto;

import kb04.team02.web.mvc.domain.member.Role;
import kb04.team02.web.mvc.domain.wallet.common.WalletType;
import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import kb04.team02.web.mvc.domain.wallet.group.ParticipationState;
import kb04.team02.web.mvc.domain.wallet.personal.PersonalWallet;
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
    private Long WalletId;
    private String nickname;
    private Role role;
    private WalletType walletType;

    public static WalletDto toPersoanlDto(PersonalWallet personalWallet){
        return WalletDto.builder()
                .WalletId(personalWallet.getPersonalWalletId())
                .nickname("개인지갑")
                .role(Role.CHAIRMAN)
                .walletType(WalletType.PERSONAL_WALLET)
                .build();
    }

    public static WalletDto toGroupDto(GroupWallet groupWallet){
        return WalletDto.builder()
                .WalletId(groupWallet.getGroupWalletId())
                .nickname(groupWallet.getNickname())
                .role(null)
                .walletType(WalletType.GROUP_WALLET)
                .build();
    }

}
