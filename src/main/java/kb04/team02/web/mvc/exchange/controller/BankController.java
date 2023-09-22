package kb04.team02.web.mvc.exchange.controller;

import kb04.team02.web.mvc.exchange.dto.GeoCodeDto;
import kb04.team02.web.mvc.exchange.service.StaticMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bank")
@RequiredArgsConstructor
public class BankController {

    private final StaticMapService mapService;

    @GetMapping("/{id}")
    @ResponseBody
    public GeoCodeDto geoCode(@PathVariable("id") String bankId) {
        GeoCodeDto geoCodeDto = mapService.staticMapImage(Long.valueOf(bankId));
        System.out.println("geoCodeDto = " + geoCodeDto);
        return geoCodeDto;
    }
}
