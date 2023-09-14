package kb04.team02.web.mvc.exchange.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReceiptState {
    WAITING(0),
    COMPLETE(1),
    CANCEL(2);

    private int value;
}
