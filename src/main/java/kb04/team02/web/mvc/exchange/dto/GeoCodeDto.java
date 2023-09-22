package kb04.team02.web.mvc.exchange.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GeoCodeDto {
    private String lng;
    private String lat;
}
