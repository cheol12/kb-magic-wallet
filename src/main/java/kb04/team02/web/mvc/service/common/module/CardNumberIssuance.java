package kb04.team02.web.mvc.service.common.module;

import java.util.Random;

public class CardNumberIssuance {
    /**
     * 16자리의 랜덤 카드 번호 생성
     *
     * @return 4xxx-xxxx-xxxx-xxxx
     */
    public static String generateRandomCardNumber() {
        Random rand = new Random();
        StringBuilder cardNumber = new StringBuilder();

        // 첫 번째 숫자는 4로 시작 (마스터카드의 대표적인 시작 번호)
        cardNumber.append("4");

        // 나머지 15자리를 랜덤하게 생성
        for (int i = 1; i < 16; i++) {
            int digit = rand.nextInt(10); // 0부터 9까지의 난수 생성
            cardNumber.append(digit);
            if (i % 4 == 3) {
                cardNumber.append('-');
            }
        }

        return cardNumber.toString();
    }
}
