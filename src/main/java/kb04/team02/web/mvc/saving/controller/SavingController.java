package kb04.team02.web.mvc.saving.controller;

import kb04.team02.web.mvc.saving.dto.SavingDto;
import kb04.team02.web.mvc.saving.dto.SavingInstallmentDto;
import kb04.team02.web.mvc.common.exception.InsertException;
import kb04.team02.web.mvc.saving.exception.NoSavingDetailException;
import kb04.team02.web.mvc.saving.service.SavingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/saving")
@RequiredArgsConstructor
public class SavingController {

    private final SavingService savingService;

    /**
     * 적금 상품 메인 페이지 (상품조회)
     * API 명세서 ROWNUM:37
     */
    @GetMapping("/")
    public ModelAndView savingIndex() {
        List<SavingDto> savingList = savingService.selectSavings();
        return new ModelAndView("saving/saving", "savingList", savingList);
    }

    /**
     * 적금 상품 상세 조회
     * API 명세서 ROWNUM:38
     *
     * @param id 상세 조회 할 적금 상품 id
     */
    @GetMapping("/{id}")
    public ModelAndView savingDetail(@PathVariable String id) throws Exception {
        SavingDto saving = savingService.selectSavingDetail(Long.parseLong(id));

        return new ModelAndView("saving/savingDetail", "saving", saving);
    }

    /**
     * 적금 상품 가입 폼
     * API 명세서 ROWNUM:39
     *
     * @param id 가입 할 적금 상품 id
     */
    @GetMapping("/{id}/form")
    public void savingJoinForm(@PathVariable String id) {
    }

    /**
     * 적금 상품 가입
     * API 명세서 ROWNUM:40
     *
     * @param id 가입 할 적금 상품 id
     */
    @PostMapping("/{id}/form")
    public String savingJoin(@PathVariable String id, SavingInstallmentDto installmentDto) {
        int result = savingService.insertInstallmentSaving(installmentDto);
        return "redirect:/saving/";
    }

    //== 예외 처리 ==/
    @ExceptionHandler({NoSavingDetailException.class, InsertException.class})
    public String noSavingDetailException(Exception e) {
        System.out.println(e.getMessage());
        return "error";
    }
}
