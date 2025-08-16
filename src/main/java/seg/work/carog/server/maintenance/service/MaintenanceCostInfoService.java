package seg.work.carog.server.maintenance.service;

import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seg.work.carog.server.auth.dto.TokenUserInfo;
import seg.work.carog.server.car.entity.CarInfoEntity;
import seg.work.carog.server.car.repository.CarInfoRepository;
import seg.work.carog.server.common.constant.Constant;
import seg.work.carog.server.common.constant.Message;
import seg.work.carog.server.common.exception.BaseException;
import seg.work.carog.server.maintenance.dto.MaintenanceCostInfoResponse;
import seg.work.carog.server.maintenance.dto.MaintenanceCostInfoSaveRequest;
import seg.work.carog.server.maintenance.dto.MaintenanceCostInfoUpdateRequest;
import seg.work.carog.server.maintenance.entity.MaintenanceCostInfoEntity;
import seg.work.carog.server.maintenance.repository.MaintenanceCostInfoRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MaintenanceCostInfoService {

    private final CarInfoRepository carInfoRepository;
    private final MaintenanceCostInfoRepository maintenanceCostInfoRepository;

    private CarInfoEntity getCarInfoById(Long carInfoId) {
        return carInfoRepository.findById(carInfoId).orElseThrow(() -> new BaseException(Message.NO_CAR_INFO));
    }

    private CarInfoEntity getRepresentCarInfo(TokenUserInfo tokenUserInfo) {
        return carInfoRepository.findByUserIdAndRepresentAndDeleteYn(tokenUserInfo.getId(), Constant.FLAG_TRUE, Constant.FLAG_N).orElseThrow(() -> new BaseException(Message.NO_REPRESENT_CAR_INFO));
    }

    private CarInfoEntity getCarInfo(TokenUserInfo tokenUserInfo, Long carInfoId) {
        return (carInfoId != null && carInfoId > 0) ? this.getCarInfoById(carInfoId) : this.getRepresentCarInfo(tokenUserInfo);
    }

    public Slice<MaintenanceCostInfoResponse> getMaintenanceCostInfoList(TokenUserInfo tokenUserInfo, Long carInfoId) {
        CarInfoEntity carInfoEntity = this.getCarInfo(tokenUserInfo, carInfoId);

        Optional<Slice<MaintenanceCostInfoEntity>> optionalMaintenanceCostInfoEntityList = maintenanceCostInfoRepository.findByCarInfoId(carInfoEntity.getId());
        return optionalMaintenanceCostInfoEntityList.map(maintenanceCostInfoEntityList -> maintenanceCostInfoEntityList.stream().map(MaintenanceCostInfoResponse::new).toList()).<Slice<MaintenanceCostInfoResponse>>map(PageImpl::new).orElse(new PageImpl<>(Collections.emptyList()));
    }

    @Transactional
    public void saveMaintenanceCostInfo(TokenUserInfo tokenUserInfo, MaintenanceCostInfoSaveRequest maintenanceCostInfoSaveRequest) {
        CarInfoEntity carInfoEntity = this.getCarInfo(tokenUserInfo, maintenanceCostInfoSaveRequest.getCarInfoId());

        MaintenanceCostInfoEntity maintenanceCostInfoEntity = maintenanceCostInfoSaveRequest.toEntity(carInfoEntity.getId());
        maintenanceCostInfoRepository.save(maintenanceCostInfoEntity);
    }

    @Transactional
    public void updateMaintenanceCostInfo(TokenUserInfo tokenUserInfo, MaintenanceCostInfoUpdateRequest maintenanceCostInfoUpdateRequest) {
        MaintenanceCostInfoEntity maintenanceCostInfoEntity = maintenanceCostInfoRepository.findById(maintenanceCostInfoUpdateRequest.getId()).orElseThrow(() -> new BaseException(Message.NO_MAINTENANCE_COST_INFO));
        maintenanceCostInfoEntity.updateMaintenanceCostInfo(maintenanceCostInfoUpdateRequest);
        maintenanceCostInfoRepository.save(maintenanceCostInfoEntity);
    }

}
