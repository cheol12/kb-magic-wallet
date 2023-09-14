package kb04.team02.web.mvc.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentCategoryType {
    RESTAURANT(0),
    ENTERTAINMENT(1),
    ETC(2);

    private int value;
}
