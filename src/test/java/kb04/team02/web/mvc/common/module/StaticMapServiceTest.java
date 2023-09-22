package kb04.team02.web.mvc.common.module;

import kb04.team02.web.mvc.exchange.dto.GeoCodeDto;
import kb04.team02.web.mvc.exchange.service.StaticMapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StaticMapServiceTest {

    @Autowired
    StaticMapService staticMapService;

    @Test
    void getImageWithMarker() {
        GeoCodeDto geoCode = staticMapService.staticMapImage(1L);
        System.out.println(geoCode);
    }
}