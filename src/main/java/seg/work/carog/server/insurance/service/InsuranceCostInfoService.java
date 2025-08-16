package seg.work.carog.server.insurance.service;

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
import seg.work.carog.server.insurance.dto.InsuranceCostInfoResponse;
import seg.work.carog.server.insurance.dto.InsuranceCostInfoSaveRequest;
import seg.work.carog.server.insurance.dto.InsuranceCostInfoUpdateRequest;
import seg.work.carog.server.insurance.entity.InsuranceCostInfoEntity;
import seg.work.carog.server.insurance.repository.InsuranceCostInfoRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InsuranceCostInfoService {

    private final CarInfoRepository carInfoRepository;
    private final InsuranceCostInfoRepository insuranceCostInfoRepository;

    private CarInfoEntity getCarInfoById(Long carInfoId) {
        return carInfoRepository.findById(carInfoId).orElseThrow(() -> new BaseException(Message.NO_CAR_INFO));
    }

    private CarInfoEntity getRepresentCarInfo(TokenUserInfo tokenUserInfo) {
        return carInfoRepository.findByUserIdAndRepresentAndDeleteYn(tokenUserInfo.getId(), Constant.FLAG_TRUE, Constant.FLAG_N).orElseThrow(() -> new BaseException(Message.NO_REPRESENT_CAR_INFO));
    }

    private CarInfoEntity getCarInfo(TokenUserInfo tokenUserInfo, Long carInfoId) {
        return (carInfoId != null && carInfoId > 0) ? this.getCarInfoById(carInfoId) : this.getRepresentCarInfo(tokenUserInfo);
    }

    public Slice<InsuranceCostInfoResponse> getInsuranceCostInfoList(TokenUserInfo tokenUserInfo, Long carInfoId, Pageable pageable) {
        CarInfoEntity carInfoEntity = this.getCarInfo(tokenUserInfo, carInfoId);

        Optional<Slice<InsuranceCostInfoEntity>> optionalInsuranceCostInfoEntityList = insuranceCostInfoRepository.findByCarInfoIdAndDeleteYn(carInfoEntity.getId(), Constant.FLAG_N, pageable);
        return optionalInsuranceCostInfoEntityList.map(insuranceCostInfoEntityList -> insuranceCostInfoEntityList.stream().map(InsuranceCostInfoResponse::new).toList()).<Slice<InsuranceCostInfoResponse>>map(PageImpl::new).orElse(new PageImpl<>(Collections.emptyList()));
    }

    @Transactional
    public void saveInsuranceCostInfo(TokenUserInfo tokenUserInfo, InsuranceCostInfoSaveRequest insuranceCostInfoSaveRequest) {
        CarInfoEntity carInfoEntity = this.getCarInfo(tokenUserInfo, insuranceCostInfoSaveRequest.getCarInfoId());

        InsuranceCostInfoEntity insuranceCostInfoEntity = insuranceCostInfoSaveRequest.toEntity(carInfoEntity.getId());
        insuranceCostInfoRepository.save(insuranceCostInfoEntity);
    }

    @Transactional
    public void updateInsuranceCostInfo(TokenUserInfo tokenUserInfo, InsuranceCostInfoUpdateRequest insuranceCostInfoUpdateRequest) {
        InsuranceCostInfoEntity insuranceCostInfoEntity = insuranceCostInfoRepository.findById(insuranceCostInfoUpdateRequest.getId()).orElseThrow(() -> new BaseException(Message.NO_INSURANCE_COST_INFO));
        insuranceCostInfoEntity.updateInsuranceCostInfo(insuranceCostInfoUpdateRequest);
        insuranceCostInfoRepository.save(insuranceCostInfoEntity);
    }

}
