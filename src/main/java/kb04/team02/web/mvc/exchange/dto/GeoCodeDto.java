package kb04.team02.web.mvc.exchange.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GeoCodeDto {
    @ApiModelProperty(example = "경도")
    private String lng;
    @ApiModelProperty(example = "위도")
    private String lat;
}
