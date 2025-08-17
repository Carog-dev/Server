package seg.work.carog.server.installment.controller;

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
import seg.work.carog.server.installment.dto.InstallmentCostInfoResponse;
import seg.work.carog.server.installment.dto.InstallmentCostInfoSaveRequest;
import seg.work.carog.server.installment.dto.InstallmentCostInfoUpdateRequest;
import seg.work.carog.server.installment.service.InstallmentCostInfoService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/installment")
public class InstallmentCostInfoController {

    private final InstallmentCostInfoService installmentCostInfoService;

    @GetMapping({"/list", "/list/{carInfoId}"})
    public ResponseEntity<?> getInstallmentCostInfoList(TokenUserInfo tokenUserInfo, @PathVariable(required = false) Long carInfoId, Pageable pageable) {
        Slice<InstallmentCostInfoResponse> installmentCostInfoResponseList = installmentCostInfoService.getInstallmentCostInfoList(tokenUserInfo, carInfoId, pageable);

        return ResponseEntity.ok(BaseApiResponse.success(installmentCostInfoResponseList));
    }

    @PostMapping
    public ResponseEntity<?> saveInstallmentCostInfo(TokenUserInfo tokenUserInfo, @Validated @RequestBody InstallmentCostInfoSaveRequest installmentCostInfoSaveRequest) {
        installmentCostInfoService.saveInstallmentCostInfo(tokenUserInfo, installmentCostInfoSaveRequest);

        return ResponseEntity.ok(BaseApiResponse.success());
    }

    @PutMapping
    public ResponseEntity<?> updateInstallmentCostInfo(TokenUserInfo tokenUserInfo, @Validated @RequestBody InstallmentCostInfoUpdateRequest installmentCostInfoUpdateRequest) {
        installmentCostInfoService.updateInstallmentCostInfo(tokenUserInfo, installmentCostInfoUpdateRequest);

        return ResponseEntity.ok(BaseApiResponse.success());
    }
}
