package kb04.team02.web.mvc.exchange.dto;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(example = "지갑 id")
    private Long walletId;
    @ApiModelProperty(example = "지갑 별칭")
    private String nickname;
    @ApiModelProperty(example = "권한")
    private Role role;
    @ApiModelProperty(example = "지갑 타입")
    private WalletType walletType;

    public static WalletDto toPersoanlDto(PersonalWallet personalWallet){
        return WalletDto.builder()
                .walletId(personalWallet.getPersonalWalletId())
                .nickname("개인지갑")
                .role(Role.CHAIRMAN)
                .walletType(WalletType.PERSONAL_WALLET)
                .build();
    }

//    public static WalletDto toGroupDto(GroupWallet groupWallet, Role role){
//        return WalletDto.builder()
//                .walletId(groupWallet.getGroupWalletId())
//                .nickname(groupWallet.getNickname())
//                .role(role)
//                .walletType(WalletType.GROUP_WALLET)
//                .build();
//    }

}
