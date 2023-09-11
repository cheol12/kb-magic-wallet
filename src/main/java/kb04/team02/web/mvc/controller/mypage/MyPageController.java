package kb04.team02.web.mvc.controller.mypage;

import kb04.team02.web.mvc.service.mypage.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    /**
     * 마이 페이지
     * API 명세서 ROWNUM:49
     */
    @GetMapping("/")
    public void mypageIndex() {
    }

    /**
     * 마이 페이지 - 카드 신청 폼
     * API 명세서 ROWNUM:50
     */
    @GetMapping("/card")
    public void cardForm() {
    }

    /**
     * 마이 페이지 - 카드 신청
     * API 명세서 ROWNUM:51
     */
    @PostMapping("/card")
    public String cardCreate(HttpSession session) {
        return null;
    }

    /**
     * 마이 페이지 - 카드 분실
     * API 명세서 ROWNUM:52
     */
    @DeleteMapping("/card")
    public String cardInvalidate(HttpSession session) {
        return null;
    }

    /**
     * 마이 페이지 - 은행 계좌 연결 폼
     * API 명세서 ROWNUM:53
     */
    @GetMapping("/bank")
    public void bankLinkForm() {
    }

    /**
     * 마이 페이지 - 은행 계좌 연결 요청
     * API 명세서 ROWNUM:54
     */
    @PostMapping("/bank")
    public String bankLink(HttpSession session) {
        return null;
    }
}
