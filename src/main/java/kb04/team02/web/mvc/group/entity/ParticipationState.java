package kb04.team02.web.mvc.group.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ParticipationState {
//    INVITED(0),
    WAITING(1),
    PARTICIPATED(2);

    private int value;
}
