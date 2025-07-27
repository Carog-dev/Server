package seg.work.carog.server.car.service;

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
import seg.work.carog.server.car.dto.CarInfoChangeRepresentRequest;
import seg.work.carog.server.car.dto.CarInfoResponse;
import seg.work.carog.server.car.dto.CarInfoSaveRequest;
import seg.work.carog.server.car.dto.CarInfoUpdateRequest;
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

    public CarInfoResponse getRepresentCarInfo(TokenUserInfo tokenUserInfo) {
        Optional<CarInfoEntity> optionalCarInfoEntity = carInfoRepository.findByUserIdAndRepresent(tokenUserInfo.getId(), Constant.FLAG_TRUE);
        return optionalCarInfoEntity.map(CarInfoResponse::new).orElse(null);
    }

    public Slice<CarInfoResponse> getListCarInfo(TokenUserInfo tokenUserInfo, Pageable pageable) {
        Optional<Slice<CarInfoEntity>> optionalCarInfoEntityList = carInfoRepository.findByUserId(tokenUserInfo.getId());
        return optionalCarInfoEntityList.map(carInfoEntityList -> carInfoEntityList.stream().map(CarInfoResponse::new).toList()).<Slice<CarInfoResponse>>map(PageImpl::new).orElse(new PageImpl<>(Collections.emptyList()));
    }

    public void saveCarInfo(TokenUserInfo tokenUserInfo, CarInfoSaveRequest carInfoSaveRequest) {
        boolean existsNumber = carInfoRepository.existsByUserIdAndNumber(tokenUserInfo.getId(), carInfoSaveRequest.getNumber());
        if (existsNumber) {
            throw new BaseException(Message.ALREADY_EXISTS_CAR_NUMBER);
        } else {
            CarInfoEntity carInfoEntity = carInfoSaveRequest.toEntity(tokenUserInfo.getId());
            carInfoRepository.save(carInfoEntity);
        }
    }

    public void updateCarInfo(TokenUserInfo tokenUserInfo, CarInfoUpdateRequest carInfoUpdateRequest) {
        CarInfoEntity carInfoEntity = carInfoRepository.findByUserIdAndId(tokenUserInfo.getId(), carInfoUpdateRequest.getId()).orElseThrow(() -> new BaseException(Message.NOT_FOUND));
        carInfoEntity.update(carInfoUpdateRequest);

        carInfoRepository.save(carInfoEntity);
    }

    public void updateRepresentCarInfo(TokenUserInfo tokenUserInfo, CarInfoChangeRepresentRequest carInfoChangeRepresentRequest) {
        CarInfoEntity newRepresentcarInfoEntity = carInfoRepository.findByUserIdAndId(tokenUserInfo.getId(), carInfoChangeRepresentRequest.getId()).orElseThrow(() -> new BaseException(Message.NOT_FOUND));

        Optional<CarInfoEntity> optionalCarInfoOldRepresentEntity = carInfoRepository.findByUserIdAndRepresent(tokenUserInfo.getId(), Constant.FLAG_TRUE);
        optionalCarInfoOldRepresentEntity.ifPresent(carInfoEntity -> {
            carInfoEntity.updateRepresent(Constant.FLAG_FALSE);
            carInfoRepository.save(carInfoEntity);
        });

        newRepresentcarInfoEntity.updateRepresent(Constant.FLAG_TRUE);
        carInfoRepository.save(newRepresentcarInfoEntity);
    }
}
