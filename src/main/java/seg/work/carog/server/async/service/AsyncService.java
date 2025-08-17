package seg.work.carog.server.async.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seg.work.carog.server.accident.repository.AccidentCostInfoRepository;
import seg.work.carog.server.common.constant.Constant;
import seg.work.carog.server.common.entity.BaseEntity;
import seg.work.carog.server.common.repository.BaseRepository;
import seg.work.carog.server.etc.repository.EtcCostInfoRepository;
import seg.work.carog.server.installment.repository.InstallmentCostInfoRepository;
import seg.work.carog.server.insurance.repository.InsuranceCostInfoRepository;
import seg.work.carog.server.maintenance.repository.MaintenanceCostInfoRepository;
import seg.work.carog.server.oil.repository.OilCostInfoRepository;
import seg.work.carog.server.parking.repository.ParkingCostInfoRepository;

@Slf4j
@Async
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AsyncService {

    private final AccidentCostInfoRepository accidentCostInfoRepository;
    private final EtcCostInfoRepository etcCostInfoRepository;
    private final InstallmentCostInfoRepository installmentCostInfoRepository;
    private final InsuranceCostInfoRepository insuranceCostInfoRepository;
    private final MaintenanceCostInfoRepository maintenanceCostInfoRepository;
    private final OilCostInfoRepository oilCostInfoRepository;
    private final ParkingCostInfoRepository parkingCostInfoRepository;

    private <T extends BaseEntity, R extends BaseRepository<T>> void deleteAllCostInfoByCarInfoId(Long carInfoId, R repository, FindAllByCarInfoIdAndDeleteYn<T> finder) {

        Optional<List<T>> optionalCostInfos = finder.findAllByCarInfoIdAndDeleteYn(carInfoId, Constant.FLAG_N);

        if (optionalCostInfos.isPresent()) {
            List<T> costInfos = optionalCostInfos.get();
            for (T costInfo : costInfos) {
                costInfo.delete();
            }

            repository.saveAll(costInfos);
        }
    }

    @FunctionalInterface
    private interface FindAllByCarInfoIdAndDeleteYn<T> {

        Optional<List<T>> findAllByCarInfoIdAndDeleteYn(Long carInfoId, String deleteYn);
    }

    @Transactional
    public void deleteAllByCarInfoId(Long carInfoId) {
        this.deleteAllAccidentByCarInfoId(carInfoId);
        this.deleteAllEtcByCarInfoId(carInfoId);
        this.deleteAllInstallmentByCarInfoId(carInfoId);
        this.deleteAllInsuranceByCarInfoId(carInfoId);
        this.deleteAllMaintenanceByCarInfoId(carInfoId);
        this.deleteAllOilByCarInfoId(carInfoId);
        this.deleteAllParkingByCarInfoId(carInfoId);
    }

    @Transactional
    public void deleteAllAccidentByCarInfoId(Long carInfoId) {
        log.info("deleteAllAccidentByCarInfoId async run by {}", carInfoId);
        deleteAllCostInfoByCarInfoId(carInfoId, accidentCostInfoRepository, accidentCostInfoRepository::findAllByCarInfoIdAndDeleteYn);
    }

    @Transactional
    public void deleteAllEtcByCarInfoId(Long carInfoId) {
        log.info("deleteAllEtcByCarInfoId async run by {}", carInfoId);
        deleteAllCostInfoByCarInfoId(carInfoId, etcCostInfoRepository, etcCostInfoRepository::findAllByCarInfoIdAndDeleteYn);
    }

    @Transactional
    public void deleteAllInstallmentByCarInfoId(Long carInfoId) {
        log.info("deleteAllInstallmentByCarInfoId async run by {}", carInfoId);
        deleteAllCostInfoByCarInfoId(carInfoId, installmentCostInfoRepository, installmentCostInfoRepository::findAllByCarInfoIdAndDeleteYn);
    }

    @Transactional
    public void deleteAllInsuranceByCarInfoId(Long carInfoId) {
        log.info("deleteAllInsuranceByCarInfoId async run by {}", carInfoId);
        deleteAllCostInfoByCarInfoId(carInfoId, insuranceCostInfoRepository, insuranceCostInfoRepository::findAllByCarInfoIdAndDeleteYn);
    }

    @Transactional
    public void deleteAllMaintenanceByCarInfoId(Long carInfoId) {
        log.info("deleteAllMaintenanceByCarInfoId async run by {}", carInfoId);
        deleteAllCostInfoByCarInfoId(carInfoId, maintenanceCostInfoRepository, maintenanceCostInfoRepository::findAllByCarInfoIdAndDeleteYn);
    }

    @Transactional
    public void deleteAllOilByCarInfoId(Long carInfoId) {
        log.info("deleteAllOilByCarInfoId async run by {}", carInfoId);
        deleteAllCostInfoByCarInfoId(carInfoId, oilCostInfoRepository, oilCostInfoRepository::findAllByCarInfoIdAndDeleteYn);
    }

    @Transactional
    public void deleteAllParkingByCarInfoId(Long carInfoId) {
        log.info("deleteAllParkingByCarInfoId async run by {}", carInfoId);
        deleteAllCostInfoByCarInfoId(carInfoId, parkingCostInfoRepository, parkingCostInfoRepository::findAllByCarInfoIdAndDeleteYn);
    }
}
