package seg.work.carog.server.maintenance.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seg.work.carog.server.auth.dto.TokenUserInfo;
import seg.work.carog.server.common.dto.BaseApiResponse;
import seg.work.carog.server.maintenance.dto.MaintenanceCostInfoResponse;
import seg.work.carog.server.maintenance.dto.MaintenanceCostInfoSaveRequest;
import seg.work.carog.server.maintenance.dto.MaintenanceCostInfoUpdateRequest;
import seg.work.carog.server.maintenance.service.MaintenanceCostInfoService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/maintenance")
public class MaintenanceCostInfoController {

    private final MaintenanceCostInfoService maintenanceCostInfoService;

    @GetMapping({"/list", "/list/{carInfoId}"})
    public ResponseEntity<?> getMaintenanceCostInfoList(TokenUserInfo tokenUserInfo, @PathVariable(required = false) Long carInfoId, Pageable pageable) {
        Slice<MaintenanceCostInfoResponse> maintenanceCostInfoResponseList = maintenanceCostInfoService.getMaintenanceCostInfoList(tokenUserInfo, carInfoId, pageable);

        return ResponseEntity.ok(BaseApiResponse.success(maintenanceCostInfoResponseList));
    }

    @PostMapping
    public ResponseEntity<?> saveMaintenanceCostInfo(TokenUserInfo tokenUserInfo, @Validated @RequestBody MaintenanceCostInfoSaveRequest maintenanceCostInfoSaveRequest) {
        maintenanceCostInfoService.saveMaintenanceCostInfo(tokenUserInfo, maintenanceCostInfoSaveRequest);

        return ResponseEntity.ok(BaseApiResponse.success());
    }

    @PutMapping
    public ResponseEntity<?> updateMaintenanceCostInfo(TokenUserInfo tokenUserInfo, @Validated @RequestBody MaintenanceCostInfoUpdateRequest maintenanceCostInfoUpdateRequest) {
        maintenanceCostInfoService.updateMaintenanceCostInfo(tokenUserInfo, maintenanceCostInfoUpdateRequest);

        return ResponseEntity.ok(BaseApiResponse.success());
    }

}
