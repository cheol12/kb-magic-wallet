package kb04.team02.web.mvc.domain.bank;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReceiptState {
    WAITING(0),
    COMPLETE(1);

    private int value;
}
