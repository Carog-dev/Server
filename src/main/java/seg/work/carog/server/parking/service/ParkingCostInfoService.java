package seg.work.carog.server.parking.service;

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
import seg.work.carog.server.parking.dto.ParkingCostInfoResponse;
import seg.work.carog.server.parking.dto.ParkingCostInfoSaveRequest;
import seg.work.carog.server.parking.dto.ParkingCostInfoUpdateRequest;
import seg.work.carog.server.parking.entity.ParkingCostInfoEntity;
import seg.work.carog.server.parking.repository.ParkingCostInfoRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParkingCostInfoService {

    private final CarInfoRepository carInfoRepository;
    private final ParkingCostInfoRepository parkingCostInfoRepository;

    private CarInfoEntity getCarInfoById(Long carInfoId) {
        return carInfoRepository.findById(carInfoId).orElseThrow(() -> new BaseException(Message.NO_CAR_INFO));
    }

    private CarInfoEntity getRepresentCarInfo(TokenUserInfo tokenUserInfo) {
        return carInfoRepository.findByUserIdAndRepresentAndDeleteYn(tokenUserInfo.getId(), Constant.FLAG_TRUE, Constant.FLAG_N).orElseThrow(() -> new BaseException(Message.NO_REPRESENT_CAR_INFO));
    }

    private CarInfoEntity getCarInfo(TokenUserInfo tokenUserInfo, Long carInfoId) {
        return (carInfoId != null && carInfoId > 0) ? this.getCarInfoById(carInfoId) : this.getRepresentCarInfo(tokenUserInfo);
    }

    public Slice<ParkingCostInfoResponse> getParkingCostInfoList(TokenUserInfo tokenUserInfo, Long carInfoId, Pageable pageable) {
        CarInfoEntity carInfoEntity = this.getCarInfo(tokenUserInfo, carInfoId);

        Optional<Slice<ParkingCostInfoEntity>> optionalParkingCostInfoEntityList = parkingCostInfoRepository.findByCarInfoIdAndDeleteYn(carInfoEntity.getId(), Constant.FLAG_N, pageable);
        return optionalParkingCostInfoEntityList.map(parkingCostInfoEntityList -> parkingCostInfoEntityList.stream().map(ParkingCostInfoResponse::new).toList()).<Slice<ParkingCostInfoResponse>>map(PageImpl::new).orElse(new PageImpl<>(Collections.emptyList()));
    }

    @Transactional
    public void saveParkingCostInfo(TokenUserInfo tokenUserInfo, ParkingCostInfoSaveRequest parkingCostInfoSaveRequest) {
        CarInfoEntity carInfoEntity = this.getCarInfo(tokenUserInfo, parkingCostInfoSaveRequest.getCarInfoId());

        ParkingCostInfoEntity parkingCostInfoEntity = parkingCostInfoSaveRequest.toEntity(carInfoEntity.getId());
        parkingCostInfoRepository.save(parkingCostInfoEntity);
    }

    @Transactional
    public void updateParkingCostInfo(TokenUserInfo tokenUserInfo, ParkingCostInfoUpdateRequest parkingCostInfoUpdateRequest) {
        ParkingCostInfoEntity parkingCostInfoEntity = parkingCostInfoRepository.findById(parkingCostInfoUpdateRequest.getId()).orElseThrow(() -> new BaseException(Message.NO_PARKING_COST_INFO));
        parkingCostInfoEntity.updateParkingCostInfo(parkingCostInfoUpdateRequest);
        parkingCostInfoRepository.save(parkingCostInfoEntity);
    }

}
