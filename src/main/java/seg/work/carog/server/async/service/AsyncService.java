package seg.work.carog.server.async.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seg.work.carog.server.accident.repository.AccidentCostInfoRepository;
import seg.work.carog.server.common.constant.Constant;
import seg.work.carog.server.installment.repository.InstallmentCostInfoRepository;
import seg.work.carog.server.insurance.repository.InsuranceCostInfoRepository;
import seg.work.carog.server.maintenance.repository.MaintenanceCostInfoRepository;
import seg.work.carog.server.oil.repository.OilCostInfoEntityRepository;

@Slf4j
@Async
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AsyncService {

    private final AccidentCostInfoRepository accidentCostInfoRepository;
    private final InstallmentCostInfoRepository installmentCostInfoRepository;
    private final InsuranceCostInfoRepository insuranceCostInfoRepository;
    private final MaintenanceCostInfoRepository maintenanceCostInfoRepository;
    private final OilCostInfoEntityRepository oilCostInfoEntityRepository;

    public void deleteAllByCarInfoId(Long carInfoId) {
        this.deleteAllAccidentByCarInfoId(carInfoId);
        this.deleteAllInstallmentByCarInfoId(carInfoId);
        this.deleteAllInsuranceByCarInfoId(carInfoId);
        this.deleteAllMaintenanceByCarInfoId(carInfoId);
        this.deleteAllOilByCarInfoId(carInfoId);
    }

    public void deleteAllAccidentByCarInfoId(Long carInfoId) {
        accidentCostInfoRepository.findAllByCarInfoIdAndDeleteYn(carInfoId, Constant.FLAG_N).ifPresent(accidents -> accidents.forEach(accident -> {
            accident.delete();
            accidentCostInfoRepository.save(accident);
        }));
    }

    public void deleteAllInstallmentByCarInfoId(Long carInfoId) {
        installmentCostInfoRepository.findAllByCarInfoIdAndDeleteYn(carInfoId, Constant.FLAG_N).ifPresent(installments -> installments.forEach(installment -> {
            installment.delete();
            installmentCostInfoRepository.save(installment);
        }));
    }

    public void deleteAllInsuranceByCarInfoId(Long carInfoId) {
        insuranceCostInfoRepository.findAllByCarInfoIdAndDeleteYn(carInfoId, Constant.FLAG_N).ifPresent(insurances -> insurances.forEach(insurance -> {
            insurance.delete();
            insuranceCostInfoRepository.save(insurance);
        }));
    }

    public void deleteAllMaintenanceByCarInfoId(Long carInfoId) {
        maintenanceCostInfoRepository.findAllByCarInfoIdAndDeleteYn(carInfoId, Constant.FLAG_N).ifPresent(maintenances -> maintenances.forEach(maintenance -> {
            maintenance.delete();
            maintenanceCostInfoRepository.save(maintenance);
        }));
    }

    public void deleteAllOilByCarInfoId(Long carInfoId) {
        oilCostInfoEntityRepository.findAllByCarInfoIdAndDeleteYn(carInfoId, Constant.FLAG_N).ifPresent(oils -> oils.forEach(oil -> {
            oil.delete();
            oilCostInfoEntityRepository.save(oil);
        }));
    }
}
