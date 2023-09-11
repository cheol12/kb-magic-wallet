package kb04.team02.web.mvc.domain.wallet.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WalletType {
    PERSONAL_WALLET(0),
    GROUP_WALLET(1);

    private int value;
}
