package seg.work.carog.server.insurance.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seg.work.carog.server.auth.dto.TokenUserInfo;
import seg.work.carog.server.common.dto.BaseResponse;
import seg.work.carog.server.insurance.dto.InsuranceCostInfoResponse;
import seg.work.carog.server.insurance.dto.InsuranceCostInfoSaveRequest;
import seg.work.carog.server.insurance.dto.InsuranceCostInfoUpdateRequest;
import seg.work.carog.server.insurance.service.InsuranceCostInfoService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/insurance")
public class InsuranceCostInfoController {

    private final InsuranceCostInfoService insuranceCostInfoService;

    @GetMapping({"/list", "/list/{carInfoId}"})
    public ResponseEntity<?> getInsuranceCostInfoList(TokenUserInfo tokenUserInfo, @PathVariable(required = false) Long carInfoId) {
        Slice<InsuranceCostInfoResponse> insuranceCostInfoResponseList = insuranceCostInfoService.getInsuranceCostInfoList(tokenUserInfo, carInfoId);

        return ResponseEntity.ok(BaseResponse.success(insuranceCostInfoResponseList));
    }

    @PostMapping
    public ResponseEntity<?> saveInsuranceCostInfo(TokenUserInfo tokenUserInfo, @RequestBody InsuranceCostInfoSaveRequest insuranceCostInfoSaveRequest) {
        insuranceCostInfoService.saveInsuranceCostInfo(tokenUserInfo, insuranceCostInfoSaveRequest);

        return ResponseEntity.ok(BaseResponse.success());
    }

    @PutMapping
    public ResponseEntity<?> updateInsuranceCostInfo(TokenUserInfo tokenUserInfo, @RequestBody InsuranceCostInfoUpdateRequest insuranceCostInfoUpdateRequest) {
        insuranceCostInfoService.updateInsuranceCostInfo(tokenUserInfo, insuranceCostInfoUpdateRequest);

        return ResponseEntity.ok(BaseResponse.success());
    }
}
