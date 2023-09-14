package kb04.team02.web.mvc.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransferType {
    DEPOSIT(0),
    WITHDRAW(1);

    private int value;
}
