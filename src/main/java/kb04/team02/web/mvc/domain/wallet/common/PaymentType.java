package kb04.team02.web.mvc.domain.wallet.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentType {
    OK(0),
    CANCEL(1);

    private int value;
}
