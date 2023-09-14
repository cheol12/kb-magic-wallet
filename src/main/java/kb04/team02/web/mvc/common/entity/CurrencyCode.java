package kb04.team02.web.mvc.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CurrencyCode {
    KRW(0),
    USD(1),
    JPY(2);

    private int value;
}
