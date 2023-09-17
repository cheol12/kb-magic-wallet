package kb04.team02.web.mvc.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WalletType {
    PERSONAL_WALLET(0),
    GROUP_WALLET(1);

    private int value;

    public static WalletType findByValue(int valueToFind) {
        for (WalletType walletType : WalletType.values()) {
            if (walletType.value == valueToFind) {
                return walletType;
            }
        }
        throw new IllegalArgumentException("해당 값을 가진 WalletType Enum을 찾을 수 없습니다.");
    }
}
