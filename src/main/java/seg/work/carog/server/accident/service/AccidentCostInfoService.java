package seg.work.carog.server.accident.service;

import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seg.work.carog.server.accident.dto.AccidentCostInfoResponse;
import seg.work.carog.server.accident.dto.AccidentCostInfoSaveRequest;
import seg.work.carog.server.accident.dto.AccidentCostInfoUpdateRequest;
import seg.work.carog.server.accident.entity.AccidentCostInfoEntity;
import seg.work.carog.server.accident.repository.AccidentCostInfoRepository;
import seg.work.carog.server.auth.dto.TokenUserInfo;
import seg.work.carog.server.car.entity.CarInfoEntity;
import seg.work.carog.server.car.repository.CarInfoRepository;
import seg.work.carog.server.common.constant.Constant;
import seg.work.carog.server.common.constant.Message;
import seg.work.carog.server.common.exception.BaseException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccidentCostInfoService {

    private final CarInfoRepository carInfoRepository;
    private final AccidentCostInfoRepository accidentCostInfoRepository;

    private CarInfoEntity getCarInfoById(Long carInfoId) {
        return carInfoRepository.findById(carInfoId).orElseThrow(() -> new BaseException(Message.NO_CAR_INFO));
    }

    private CarInfoEntity getRepresentCarInfo(TokenUserInfo tokenUserInfo) {
        return carInfoRepository.findByUserIdAndRepresentAndDeleteYn(tokenUserInfo.getId(), Constant.FLAG_TRUE, Constant.FLAG_N).orElseThrow(() -> new BaseException(Message.NO_REPRESENT_CAR_INFO));
    }

    private CarInfoEntity getCarInfo(TokenUserInfo tokenUserInfo, Long carInfoId) {
        return (carInfoId != null && carInfoId > 0) ? this.getCarInfoById(carInfoId) : this.getRepresentCarInfo(tokenUserInfo);
    }

    public Slice<AccidentCostInfoResponse> getAccidentCostInfoList(TokenUserInfo tokenUserInfo, Long carInfoId, Pageable pageable) {
        CarInfoEntity carInfoEntity = this.getCarInfo(tokenUserInfo, carInfoId);

        Optional<Slice<AccidentCostInfoEntity>> optionalAccidentCostInfoEntityList = accidentCostInfoRepository.findByCarInfoIdAndDeleteYn(carInfoEntity.getId(), Constant.FLAG_N, pageable);
        return optionalAccidentCostInfoEntityList.map(accidentCostInfoEntityList -> accidentCostInfoEntityList.stream().map(AccidentCostInfoResponse::new).toList()).<Slice<AccidentCostInfoResponse>>map(PageImpl::new).orElse(new PageImpl<>(Collections.emptyList()));
    }

    @Transactional
    public void saveAccidentCostInfo(TokenUserInfo tokenUserInfo, AccidentCostInfoSaveRequest maintenanceCostInfoSaveRequest) {
        CarInfoEntity carInfoEntity = this.getCarInfo(tokenUserInfo, maintenanceCostInfoSaveRequest.getCarInfoId());

        AccidentCostInfoEntity accidentCostInfoEntity = maintenanceCostInfoSaveRequest.toEntity(carInfoEntity.getId());
        accidentCostInfoRepository.save(accidentCostInfoEntity);
    }

    @Transactional
    public void updateAccidentCostInfo(TokenUserInfo tokenUserInfo, AccidentCostInfoUpdateRequest maintenanceCostInfoUpdateRequest) {
        AccidentCostInfoEntity accidentCostInfoEntity = accidentCostInfoRepository.findById(maintenanceCostInfoUpdateRequest.getId()).orElseThrow(() -> new BaseException(Message.NO_ACCIDENT_COST_INFO));
        accidentCostInfoEntity.updateAccidentCostInfo(maintenanceCostInfoUpdateRequest);
        accidentCostInfoRepository.save(accidentCostInfoEntity);
    }
}
