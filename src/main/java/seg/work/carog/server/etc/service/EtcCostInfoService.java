package seg.work.carog.server.etc.service;

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
import seg.work.carog.server.etc.dto.EtcCostInfoResponse;
import seg.work.carog.server.etc.dto.EtcCostInfoSaveRequest;
import seg.work.carog.server.etc.dto.EtcCostInfoUpdateRequest;
import seg.work.carog.server.etc.entity.EtcCostInfoEntity;
import seg.work.carog.server.etc.repository.EtcCostInfoRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class EtcCostInfoService {

    private final CarInfoRepository carInfoRepository;
    private final EtcCostInfoRepository etcCostInfoRepository;

    private CarInfoEntity getCarInfoById(Long carInfoId) {
        return carInfoRepository.findById(carInfoId).orElseThrow(() -> new BaseException(Message.NO_CAR_INFO));
    }

    private CarInfoEntity getRepresentCarInfo(TokenUserInfo tokenUserInfo) {
        return carInfoRepository.findByUserIdAndRepresentAndDeleteYn(tokenUserInfo.getId(), Constant.FLAG_TRUE, Constant.FLAG_N).orElseThrow(() -> new BaseException(Message.NO_REPRESENT_CAR_INFO));
    }

    private CarInfoEntity getCarInfo(TokenUserInfo tokenUserInfo, Long carInfoId) {
        return (carInfoId != null && carInfoId > 0) ? this.getCarInfoById(carInfoId) : this.getRepresentCarInfo(tokenUserInfo);
    }

    public Slice<EtcCostInfoResponse> getEtcCostInfoList(TokenUserInfo tokenUserInfo, Long carInfoId, Pageable pageable) {
        CarInfoEntity carInfoEntity = this.getCarInfo(tokenUserInfo, carInfoId);

        Optional<Slice<EtcCostInfoEntity>> optionalEtcCostInfoEntityList = etcCostInfoRepository.findByCarInfoIdAndDeleteYn(carInfoEntity.getId(), Constant.FLAG_N, pageable);
        return optionalEtcCostInfoEntityList.map(etcCostInfoEntityList -> etcCostInfoEntityList.stream().map(EtcCostInfoResponse::new).toList()).<Slice<EtcCostInfoResponse>>map(PageImpl::new).orElse(new PageImpl<>(Collections.emptyList()));
    }

    @Transactional
    public void saveEtcCostInfo(TokenUserInfo tokenUserInfo, EtcCostInfoSaveRequest etcCostInfoSaveRequest) {
        CarInfoEntity carInfoEntity = this.getCarInfo(tokenUserInfo, etcCostInfoSaveRequest.getCarInfoId());

        EtcCostInfoEntity etcCostInfoEntity = etcCostInfoSaveRequest.toEntity(carInfoEntity.getId());
        etcCostInfoRepository.save(etcCostInfoEntity);
    }

    @Transactional
    public void updateEtcCostInfo(TokenUserInfo tokenUserInfo, EtcCostInfoUpdateRequest etcCostInfoUpdateRequest) {
        EtcCostInfoEntity etcCostInfoEntity = etcCostInfoRepository.findById(etcCostInfoUpdateRequest.getId()).orElseThrow(() -> new BaseException(Message.NO_ETC_COST_INFO));
        etcCostInfoEntity.updateEtcCostInfo(etcCostInfoUpdateRequest);
        etcCostInfoRepository.save(etcCostInfoEntity);
    }

}
