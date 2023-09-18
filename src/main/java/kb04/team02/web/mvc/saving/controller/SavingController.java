package kb04.team02.web.mvc.saving.controller;

import kb04.team02.web.mvc.common.dto.LoginMemberDto;
import kb04.team02.web.mvc.exchange.dto.WalletDto;
import kb04.team02.web.mvc.group.entity.GroupWallet;
import kb04.team02.web.mvc.group.service.GroupWalletService;
import kb04.team02.web.mvc.saving.dto.SavingDto;
import kb04.team02.web.mvc.saving.dto.SavingInstallmentDto;
import kb04.team02.web.mvc.common.exception.InsertException;
import kb04.team02.web.mvc.saving.exception.NoSavingDetailException;
import kb04.team02.web.mvc.saving.service.SavingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/saving")
@RequiredArgsConstructor
public class SavingController {

    private final GroupWalletService groupWalletService;
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
//    @GetMapping("/{id}/form")
//    public void savingJoinForm(@PathVariable String id) {}

//    @GetMapping("/{id}/form")
//    public ModelAndView savingJoinForm(@PathVariable String id) {
//        return new ModelAndView("saving/savingForm", "id", id);
//    }

    @GetMapping("/{id}/form")
    public String savingJoinForm(@PathVariable String id, HttpSession session, Model model) {
        System.out.println("GetMapping 실행.............");
        LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute("member");
        List<GroupWallet> gWalletList = groupWalletService.selectAllMyGroupWallet(loginMemberDto);
        model.addAttribute("gWalletList", gWalletList);
        model.addAttribute("userId", id);
        return "saving/savingForm";
    }



    /**
     * 적금 상품 가입
     * API 명세서 ROWNUM:40
     *
     * @param id 가입 할 적금 상품 id
     */
//    @PostMapping("/{id}/form")
    @PostMapping("/{id}/form")
//    public String savingJoin(@PathVariable String id, SavingInstallmentDto installmentDto) {
    public String savingJoin(@PathVariable String id, SavingInstallmentDto installmentDto) {
        System.out.println("PostMapping 실행.............");
        int result = savingService.insertInstallmentSaving(installmentDto);
        System.out.println("result = " + result);

        if (result == 1) {
            return "redirect:/mypage/main";
        } else {
            return "redirect:/saving/";
        }
    }

    //== 예외 처리 ==/
    @ExceptionHandler({NoSavingDetailException.class, InsertException.class})
    public String noSavingDetailException(Exception e) {
        System.out.println(e.getMessage());
        return "error";
    }
}
