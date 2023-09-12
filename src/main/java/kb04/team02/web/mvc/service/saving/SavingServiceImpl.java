package kb04.team02.web.mvc.service.saving;

import kb04.team02.web.mvc.domain.saving.InstallmentSaving;
import kb04.team02.web.mvc.domain.saving.Saving;
import kb04.team02.web.mvc.domain.wallet.group.GroupWallet;
import kb04.team02.web.mvc.dto.SavingDto;
import kb04.team02.web.mvc.dto.SavingInstallmentDto;
import kb04.team02.web.mvc.exception.InsertException;
import kb04.team02.web.mvc.exception.NoSavingDetailException;
import kb04.team02.web.mvc.repository.saving.InstallmentSavingRepository;
import kb04.team02.web.mvc.repository.saving.SavingRepository;
import kb04.team02.web.mvc.repository.wallet.group.GroupWalletRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SavingServiceImpl implements SavingService {

    private final InstallmentSavingRepository installmentSavingRepository;
    private final SavingRepository savingRepository;
    private final GroupWalletRespository groupWalletRespository;

    @Override
    public List<SavingDto> selectSavings() {
        List<Saving> savings = savingRepository.findAll();

        // Mapper 미사용
        List<SavingDto> list = new ArrayList<>();
        for (Saving saving : savings) {
            SavingDto savingDto = SavingDto.builder()
                    .savingId(saving.getSavingId())
                    .name(saving.getName())
                    .interestRate(saving.getInterestRate())
                    .period(saving.getPeriod())
                    .amountLimit(saving.getAmountLimit())
                    .build();
            list.add(savingDto);
        }
        return list;
    }

    @Override
    public SavingDto selectSavingDetail(Long savingId){
        Saving saving = savingRepository.findById(savingId).orElse(null);

        SavingDto dto = SavingDto.builder()
                .savingId(saving.getSavingId())
                .name(saving.getName())
                .interestRate(saving.getInterestRate())
                .period(saving.getPeriod())
                .amountLimit(saving.getAmountLimit())
                .build();
        if (dto == null) {
            throw new NoSavingDetailException("해당 적금 상품이 없습니다");
        }
        return dto;
    }

    @Override
    public int insertInstallmentSaving(SavingInstallmentDto installmentDto) {
        Saving saving =
                savingRepository.findById(installmentDto.getSavingId()).get();
        GroupWallet groupWallet =
                groupWalletRespository.findById(installmentDto.getGroupWalletId()).get();

        InstallmentSaving save = installmentSavingRepository.save(
                InstallmentSaving.builder()
                        .groupWallet(groupWallet)
                        .saving(saving)
                        .savingAmount(installmentDto.getSavingAmount())
                        .savingDate(installmentDto.getSavingDate())
                        .maturityDate(installmentDto.getMaturityDate())
                        .build()
        );
        if (save == null) {
            throw new InsertException("적금 상품에 가입되지 않았습니다");
        }
        return 1;
    }
}
