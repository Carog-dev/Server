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
        Optional<CarInfoEntity> optionalCarInfoEntity = carInfoRepository.findByUserIdAndRepresentAndDeleteYn(tokenUserInfo.getId(), Constant.FLAG_TRUE, Constant.FLAG_N);
        return optionalCarInfoEntity.map(CarInfoResponse::new).orElse(null);
    }

    public Slice<CarInfoResponse> getListCarInfo(TokenUserInfo tokenUserInfo, Pageable pageable) {
        Optional<Slice<CarInfoEntity>> optionalCarInfoEntityList = carInfoRepository.findByUserIdAndDeleteYn(tokenUserInfo.getId(), Constant.FLAG_N);
        return optionalCarInfoEntityList.map(carInfoEntityList -> carInfoEntityList.stream().map(CarInfoResponse::new).toList()).<Slice<CarInfoResponse>>map(PageImpl::new).orElse(new PageImpl<>(Collections.emptyList()));
    }

    @Transactional
    public void saveCarInfo(TokenUserInfo tokenUserInfo, CarInfoSaveRequest carInfoSaveRequest) {
        boolean existsNumber = carInfoRepository.existsByUserIdAndNumberAndDeleteYn(tokenUserInfo.getId(), carInfoSaveRequest.getNumber(), Constant.FLAG_N);
        if (existsNumber) {
            throw new BaseException(Message.ALREADY_EXISTS_CAR_NUMBER);
        } else {
            CarInfoEntity carInfoEntity = carInfoSaveRequest.toEntity(tokenUserInfo.getId());
            carInfoRepository.save(carInfoEntity);
        }
    }

    @Transactional
    public void updateCarInfo(TokenUserInfo tokenUserInfo, CarInfoUpdateRequest carInfoUpdateRequest) {
        CarInfoEntity carInfoEntity = carInfoRepository.findByUserIdAndIdAndDeleteYn(tokenUserInfo.getId(), carInfoUpdateRequest.getId(), Constant.FLAG_N).orElseThrow(() -> new BaseException(Message.NOT_FOUND));
        carInfoEntity.update(carInfoUpdateRequest);

        carInfoRepository.save(carInfoEntity);
    }

    @Transactional
    public void updateRepresentCarInfo(TokenUserInfo tokenUserInfo, Long carInfoId) {
        CarInfoEntity newRepresentcarInfoEntity = carInfoRepository.findByUserIdAndIdAndDeleteYn(tokenUserInfo.getId(), carInfoId, Constant.FLAG_N).orElseThrow(() -> new BaseException(Message.NOT_FOUND));

        Optional<CarInfoEntity> optionalCarInfoOldRepresentEntity = carInfoRepository.findByUserIdAndRepresentAndDeleteYn(tokenUserInfo.getId(), Constant.FLAG_TRUE, Constant.FLAG_N);
        optionalCarInfoOldRepresentEntity.ifPresent(carInfoEntity -> {
            carInfoEntity.updateRepresent(Constant.FLAG_FALSE);
            carInfoRepository.save(carInfoEntity);
        });

        newRepresentcarInfoEntity.updateRepresent(Constant.FLAG_TRUE);
        carInfoRepository.save(newRepresentcarInfoEntity);
    }

    @Transactional
    public void updateUnRepresentCarInfo(TokenUserInfo tokenUserInfo, Long carInfoId) {
        CarInfoEntity unRepresentcarInfoEntity = carInfoRepository.findByUserIdAndIdAndRepresentAndDeleteYn(tokenUserInfo.getId(), carInfoId, Constant.FLAG_TRUE, Constant.FLAG_N).orElseThrow(() -> new BaseException(Message.NOT_FOUND));
        unRepresentcarInfoEntity.updateRepresent(Constant.FLAG_FALSE);
        carInfoRepository.save(unRepresentcarInfoEntity);
    }

    @Transactional
    public void deleteCarInfo(TokenUserInfo tokenUserInfo, Long carInfoId) {
        CarInfoEntity carInfoEntity = carInfoRepository.findByUserIdAndIdAndDeleteYn(tokenUserInfo.getId(), carInfoId, Constant.FLAG_N).orElseThrow(() -> new BaseException(Message.NOT_FOUND));
        carInfoEntity.delete();
        carInfoRepository.save(carInfoEntity);
    }
}
