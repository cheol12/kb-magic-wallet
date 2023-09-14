package kb04.team02.web.mvc.common.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletDetailDto {
    Map<String, Long> balance;
    private List<WalletHistoryDto> list;
}
