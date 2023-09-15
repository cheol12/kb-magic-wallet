package kb04.team02.web.mvc.saving.service;

import kb04.team02.web.mvc.saving.dto.SavingDto;
import kb04.team02.web.mvc.saving.dto.SavingInstallmentDto;

import java.util.List;

public interface SavingService {
    /**
     * 적금 상품 메인 페이지 (상품조회)
     * API 명세서 ROWNUM:37
     * 
     * 서비스 내에 등록되어있는 모든 적금 상품 조회
     */
    List<SavingDto> selectSavings();

    /**
     * 적금 상품 상세 조회
     * API 명세서 ROWNUM:38
     * <p>
     * 선택한 적금 상품 상페 조회
     * <p>
     * SavingDetailDto
     * - 적금명
     * - 금리
     * - 기간
     * - 설명
     * - 금액제한
     * - 기타 내용(약관)
     */
    SavingDto selectSavingDetail(Long savingId);

    /**
     * 적금 상품 가입
     * API 명세서 ROWNUM:40
     * 
     * SavingInstallmentDto
     *  - 가입금액
     *  - 출금지갑 번호
     */
    int insertInstallmentSaving(SavingInstallmentDto installmentDto);

}
