package kb04.team02.web.mvc.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TargetType {
    PERSONAL_WALLET(0),
    GROUP_WALLET(1),
    ACCOUNT(2);

    private int value;
}
