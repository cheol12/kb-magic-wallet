package kb04.team02.web.mvc.domain.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CardState {
    OK(0),
    STOP(1),
    TEMPORAL_STOP(2);

    private int value;
}
