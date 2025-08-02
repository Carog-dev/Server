package seg.work.carog.server.oil.service;

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
import seg.work.carog.server.oil.dto.OilCostInfoResponse;
import seg.work.carog.server.oil.dto.OilCostInfoSaveRequest;
import seg.work.carog.server.oil.dto.OilCostInfoUpdateRequest;
import seg.work.carog.server.oil.entity.OilCostInfoEntity;
import seg.work.carog.server.oil.repository.OilCostInfoEntityRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OilCostInfoService {

    private final CarInfoRepository carInfoRepository;
    private final OilCostInfoEntityRepository oilCostInfoEntityRepository;

    private CarInfoEntity getCarInfoById(Long carInfoId) {
        return carInfoRepository.findById(carInfoId).orElseThrow(() -> new BaseException(Message.NO_CAR_INFO));
    }

    private CarInfoEntity getRepresentCarInfo(TokenUserInfo tokenUserInfo) {
        return carInfoRepository.findByUserIdAndRepresent(tokenUserInfo.getId(), Constant.FLAG_TRUE).orElseThrow(() -> new BaseException(Message.NO_REPRESENT_CAR_INFO));
    }

    private CarInfoEntity getCarInfo(TokenUserInfo tokenUserInfo, Long carInfoId) {
        return (carInfoId != null && carInfoId > 0) ? this.getCarInfoById(carInfoId) : this.getRepresentCarInfo(tokenUserInfo);
    }

    public Slice<OilCostInfoResponse> getOilCostInfoList(TokenUserInfo tokenUserInfo, Long carInfoId) {
        CarInfoEntity carInfoEntity = this.getCarInfo(tokenUserInfo, carInfoId);

        Optional<Slice<OilCostInfoEntity>> optionalOilCostInfoEntityList = oilCostInfoEntityRepository.findByCarInfoId(carInfoEntity.getId());
        return optionalOilCostInfoEntityList.map(oilCostInfoEntityList -> oilCostInfoEntityList.stream().map(OilCostInfoResponse::new).toList()).<Slice<OilCostInfoResponse>>map(PageImpl::new).orElse(new PageImpl<>(Collections.emptyList()));
    }

    @Transactional
    public void saveOilCostInfo(TokenUserInfo tokenUserInfo, OilCostInfoSaveRequest oilCostInfoSaveRequest) {
        CarInfoEntity carInfoEntity = this.getCarInfo(tokenUserInfo, oilCostInfoSaveRequest.getCarInfoId());

        OilCostInfoEntity oilCostInfoEntity = oilCostInfoSaveRequest.toEntity(carInfoEntity.getId());
        oilCostInfoEntityRepository.save(oilCostInfoEntity);
    }

    @Transactional
    public void updateOilCostInfo(TokenUserInfo tokenUserInfo, OilCostInfoUpdateRequest oilCostInfoUpdateRequest) {
        OilCostInfoEntity oilCostInfoEntity = oilCostInfoEntityRepository.findById(oilCostInfoUpdateRequest.getId()).orElseThrow(() -> new BaseException(Message.NO_OIL_COST_INFO));
        oilCostInfoEntity.updateOilCostInfo(oilCostInfoUpdateRequest);
        oilCostInfoEntityRepository.save(oilCostInfoEntity);
    }

}
