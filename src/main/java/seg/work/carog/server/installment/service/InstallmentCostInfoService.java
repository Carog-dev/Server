package seg.work.carog.server.installment.service;

import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seg.work.carog.server.auth.dto.TokenUserInfo;
import seg.work.carog.server.car.entity.CarInfoEntity;
import seg.work.carog.server.car.repository.CarInfoRepository;
import seg.work.carog.server.common.constant.Constant;
import seg.work.carog.server.common.constant.Message;
import seg.work.carog.server.common.exception.BaseException;
import seg.work.carog.server.installment.dto.InstallmentCostInfoResponse;
import seg.work.carog.server.installment.dto.InstallmentCostInfoSaveRequest;
import seg.work.carog.server.installment.dto.InstallmentCostInfoUpdateRequest;
import seg.work.carog.server.installment.entity.InstallmentCostInfoEntity;
import seg.work.carog.server.installment.repository.InstallmentCostInfoRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InstallmentCostInfoService {

    private final CarInfoRepository carInfoRepository;
    private final InstallmentCostInfoRepository installmentCostInfoRepository;

    private CarInfoEntity getCarInfoById(Long carInfoId) {
        return carInfoRepository.findById(carInfoId).orElseThrow(() -> new BaseException(Message.NO_CAR_INFO));
    }

    private CarInfoEntity getRepresentCarInfo(TokenUserInfo tokenUserInfo) {
        return carInfoRepository.findByUserIdAndRepresentAndDeleteYn(tokenUserInfo.getId(), Constant.FLAG_TRUE, Constant.FLAG_N).orElseThrow(() -> new BaseException(Message.NO_REPRESENT_CAR_INFO));
    }

    private CarInfoEntity getCarInfo(TokenUserInfo tokenUserInfo, Long carInfoId) {
        return (carInfoId != null && carInfoId > 0) ? this.getCarInfoById(carInfoId) : this.getRepresentCarInfo(tokenUserInfo);
    }

    public Slice<InstallmentCostInfoResponse> getInstallmentCostInfoList(TokenUserInfo tokenUserInfo, Long carInfoId, Pageable pageable) {
        CarInfoEntity carInfoEntity = this.getCarInfo(tokenUserInfo, carInfoId);

        Optional<Slice<InstallmentCostInfoEntity>> optionalInstallmentCostInfoEntityList = installmentCostInfoRepository.findByCarInfoIdAndDeleteYn(carInfoEntity.getId(), Constant.FLAG_N, pageable);
        return optionalInstallmentCostInfoEntityList.map(installmentCostInfoEntityList -> installmentCostInfoEntityList.stream().map(InstallmentCostInfoResponse::new).toList()).<Slice<InstallmentCostInfoResponse>>map(PageImpl::new).orElse(new PageImpl<>(Collections.emptyList()));
    }

    @Transactional
    public void saveInstallmentCostInfo(TokenUserInfo tokenUserInfo, InstallmentCostInfoSaveRequest installmentCostInfoSaveRequest) {
        CarInfoEntity carInfoEntity = this.getCarInfo(tokenUserInfo, installmentCostInfoSaveRequest.getCarInfoId());

        InstallmentCostInfoEntity installmentCostInfoEntity = installmentCostInfoSaveRequest.toEntity(carInfoEntity.getId());
        installmentCostInfoRepository.save(installmentCostInfoEntity);
    }

    @Transactional
    public void updateInstallmentCostInfo(TokenUserInfo tokenUserInfo, InstallmentCostInfoUpdateRequest installmentCostInfoUpdateRequest) {
        InstallmentCostInfoEntity installmentCostInfoEntity = installmentCostInfoRepository.findById(installmentCostInfoUpdateRequest.getId()).orElseThrow(() -> new BaseException(Message.NO_INSTALLMENT_COST_INFO));
        installmentCostInfoEntity.updateInstallmentCostInfo(installmentCostInfoUpdateRequest);
        installmentCostInfoRepository.save(installmentCostInfoEntity);
    }
}
