package kb04.team02.web.mvc.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    GENERAL(0),
    CO_CHAIRMAN(1),
    CHAIRMAN(2);

    private int value;
}
