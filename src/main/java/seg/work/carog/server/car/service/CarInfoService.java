package seg.work.carog.server.car.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seg.work.carog.server.auth.dto.TokenUserInfo;
import seg.work.carog.server.car.dto.CarInfoRequest;
import seg.work.carog.server.car.dto.CarInfoResponse;
import seg.work.carog.server.car.dto.CarInfoSaveOrUpdateRequest;
import seg.work.carog.server.car.entity.CarInfoEntity;
import seg.work.carog.server.car.repository.CarInfoRepository;
import seg.work.carog.server.common.constant.Constant;
import seg.work.carog.server.common.constant.Message;
import seg.work.carog.server.common.exception.BaseException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarInfoService {

    private final CarInfoRepository carInfoRepository;

    public CarInfoResponse getCarRepresent(TokenUserInfo tokenUserInfo) {
        Optional<CarInfoEntity> optionalCarInfoEntity = carInfoRepository.findByUserIdAndRepresent(tokenUserInfo.getId(), Constant.FLAG_TRUE);
        return optionalCarInfoEntity.map(CarInfoResponse::new).orElse(null);
    }

    public List<CarInfoResponse> getCarList(TokenUserInfo tokenUserInfo) {
        List<CarInfoEntity> carInfoEntityList = carInfoRepository.findByUserId(tokenUserInfo.getId());
        return carInfoEntityList.stream().map(CarInfoResponse::new).toList();
    }

    public void saveCarInfo(TokenUserInfo tokenUserInfo, CarInfoRequest carInfoRequest) {
        boolean existsNumber = carInfoRepository.existsByUserIdAndNumber(tokenUserInfo.getId(), carInfoRequest.getNumber());
        if (existsNumber) {
            throw new BaseException(Message.ALREADY_EXISTS_CAR_NUMBER);
        } else {
            CarInfoEntity carInfoEntity = carInfoRequest.toEntity(tokenUserInfo.getId());
            carInfoRepository.save(carInfoEntity);
        }
    }

    public void updateCarInfo(TokenUserInfo tokenUserInfo, CarInfoSaveOrUpdateRequest carInfoSaveOrUpdateRequest) {
        CarInfoEntity carInfoEntity = carInfoRepository.findByUserIdAndId(tokenUserInfo.getId(), carInfoSaveOrUpdateRequest.getId()).orElseThrow(() -> new BaseException(Message.NOT_FOUND));
        carInfoEntity.update(carInfoSaveOrUpdateRequest);

        carInfoRepository.save(carInfoEntity);
    }
}
