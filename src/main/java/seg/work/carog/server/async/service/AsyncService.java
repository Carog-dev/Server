package seg.work.carog.server.async.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seg.work.carog.server.accident.entity.AccidentCostInfoEntity;
import seg.work.carog.server.accident.repository.AccidentCostInfoRepository;
import seg.work.carog.server.common.constant.Constant;
import seg.work.carog.server.installment.entity.InstallmentCostInfoEntity;
import seg.work.carog.server.installment.repository.InstallmentCostInfoRepository;
import seg.work.carog.server.insurance.entity.InsuranceCostInfoEntity;
import seg.work.carog.server.insurance.repository.InsuranceCostInfoRepository;
import seg.work.carog.server.maintenance.entity.MaintenanceCostInfoEntity;
import seg.work.carog.server.maintenance.repository.MaintenanceCostInfoRepository;
import seg.work.carog.server.oil.entity.OilCostInfoEntity;
import seg.work.carog.server.oil.repository.OilCostInfoRepository;

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
    private final OilCostInfoRepository oilCostInfoRepository;

    @Transactional
    public void deleteAllByCarInfoId(Long carInfoId) {
        this.deleteAllAccidentByCarInfoId(carInfoId);
        this.deleteAllInstallmentByCarInfoId(carInfoId);
        this.deleteAllInsuranceByCarInfoId(carInfoId);
        this.deleteAllMaintenanceByCarInfoId(carInfoId);
        this.deleteAllOilByCarInfoId(carInfoId);
    }

    public void deleteAllAccidentByCarInfoId(Long carInfoId) {
        Optional<List<AccidentCostInfoEntity>> optionalAccidentCostInfos = accidentCostInfoRepository.findAllByCarInfoIdAndDeleteYn(carInfoId, Constant.FLAG_N);

        if (optionalAccidentCostInfos.isPresent()) {
            List<AccidentCostInfoEntity> accidentCostInfos = optionalAccidentCostInfos.get();
            for (AccidentCostInfoEntity accidentCostInfo : accidentCostInfos) {
                accidentCostInfo.delete();
            }

            accidentCostInfoRepository.saveAll(accidentCostInfos);
        }
    }

    public void deleteAllInstallmentByCarInfoId(Long carInfoId) {
        Optional<List<InstallmentCostInfoEntity>> optionalInstallmentCostInfos = installmentCostInfoRepository.findAllByCarInfoIdAndDeleteYn(carInfoId, Constant.FLAG_N);

        if (optionalInstallmentCostInfos.isPresent()) {
            List<InstallmentCostInfoEntity> installmentCostInfos = optionalInstallmentCostInfos.get();
            for (InstallmentCostInfoEntity installmentCostInfo : installmentCostInfos) {
                installmentCostInfo.delete();
            }

            installmentCostInfoRepository.saveAll(installmentCostInfos);
        }
    }

    public void deleteAllInsuranceByCarInfoId(Long carInfoId) {
        Optional<List<InsuranceCostInfoEntity>> optionalInsuranceCostInfos = insuranceCostInfoRepository.findAllByCarInfoIdAndDeleteYn(carInfoId, Constant.FLAG_N);

        if (optionalInsuranceCostInfos.isPresent()) {
            List<InsuranceCostInfoEntity> insuranceCostInfos = optionalInsuranceCostInfos.get();
            for (InsuranceCostInfoEntity insuranceCostInfo : insuranceCostInfos) {
                insuranceCostInfo.delete();
            }

            insuranceCostInfoRepository.saveAll(insuranceCostInfos);
        }
    }

    public void deleteAllMaintenanceByCarInfoId(Long carInfoId) {
        Optional<List<MaintenanceCostInfoEntity>> optionalMaintenanceCostInfos = maintenanceCostInfoRepository.findAllByCarInfoIdAndDeleteYn(carInfoId, Constant.FLAG_N);

        if (optionalMaintenanceCostInfos.isPresent()) {
            List<MaintenanceCostInfoEntity> maintenanceCostInfos = optionalMaintenanceCostInfos.get();
            for (MaintenanceCostInfoEntity maintenanceCostInfo : maintenanceCostInfos) {
                maintenanceCostInfo.delete();
            }

            maintenanceCostInfoRepository.saveAll(maintenanceCostInfos);
        }
    }

    public void deleteAllOilByCarInfoId(Long carInfoId) {
        Optional<List<OilCostInfoEntity>> optionalOilCostInfos = oilCostInfoRepository.findAllByCarInfoIdAndDeleteYn(carInfoId, Constant.FLAG_N);

        if (optionalOilCostInfos.isPresent()) {
            List<OilCostInfoEntity> oilCostInfos = optionalOilCostInfos.get();
            for (OilCostInfoEntity oilCostInfo : oilCostInfos) {
                oilCostInfo.delete();
            }

            oilCostInfoRepository.saveAll(oilCostInfos);
        }
    }
}
