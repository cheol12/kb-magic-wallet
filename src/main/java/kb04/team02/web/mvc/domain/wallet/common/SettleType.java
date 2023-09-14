package kb04.team02.web.mvc.domain.wallet.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SettleType {
    NBBANG(0),
    RANDOM_SETTLE(1),
    RATIO_SETTLE(2);

    private int value;
}
