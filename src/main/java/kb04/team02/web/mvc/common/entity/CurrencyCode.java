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

    public static CurrencyCode findByValue(int valueToFind) {
        for (CurrencyCode currency : CurrencyCode.values()) {
            if (currency.value == valueToFind) {
                return currency;
            }
        }
        throw new IllegalArgumentException("해당 값을 가진 Currency Enum을 찾을 수 없습니다.");
    }
}
