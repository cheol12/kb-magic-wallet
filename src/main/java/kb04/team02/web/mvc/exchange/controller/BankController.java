package kb04.team02.web.mvc.exchange.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {"은행 API"})
public class BankController {

    private final StaticMapService mapService;

    @GetMapping("/{id}")
    @ResponseBody
    @ApiOperation(value = "은행 위치 Map", notes="은행 id에 따라 은행의 위치를 반환합니다.")
    @ApiImplicitParam(name = "id", value = "은행 id", required = true, dataType = "string", paramType = "path", defaultValue = "None")
    public GeoCodeDto geoCode(@PathVariable("id") String bankId) {
        GeoCodeDto geoCodeDto = mapService.staticMapImage(Long.valueOf(bankId));
        System.out.println("geoCodeDto = " + geoCodeDto);
        return geoCodeDto;
    }
}
