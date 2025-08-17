package seg.work.carog.server.etc.controller;

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
import seg.work.carog.server.etc.dto.EtcCostInfoResponse;
import seg.work.carog.server.etc.dto.EtcCostInfoSaveRequest;
import seg.work.carog.server.etc.dto.EtcCostInfoUpdateRequest;
import seg.work.carog.server.etc.service.EtcCostInfoService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/etc")
public class EtcCostInfoController {

    private final EtcCostInfoService etcCostInfoService;

    @GetMapping({"/list", "/list/{carInfoId}"})
    public ResponseEntity<?> getEtcCostInfoList(TokenUserInfo tokenUserInfo, @PathVariable(required = false) Long carInfoId, Pageable pageable) {
        Slice<EtcCostInfoResponse> etcCostInfoResponseList = etcCostInfoService.getEtcCostInfoList(tokenUserInfo, carInfoId, pageable);

        return ResponseEntity.ok(BaseApiResponse.success(etcCostInfoResponseList));
    }

    @PostMapping
    public ResponseEntity<?> saveEtcCostInfo(TokenUserInfo tokenUserInfo, @Validated @RequestBody EtcCostInfoSaveRequest etcCostInfoSaveRequest) {
        etcCostInfoService.saveEtcCostInfo(tokenUserInfo, etcCostInfoSaveRequest);

        return ResponseEntity.ok(BaseApiResponse.success());
    }

    @PutMapping
    public ResponseEntity<?> updateEtcCostInfo(TokenUserInfo tokenUserInfo, @Validated @RequestBody EtcCostInfoUpdateRequest etcCostInfoUpdateRequest) {
        etcCostInfoService.updateEtcCostInfo(tokenUserInfo, etcCostInfoUpdateRequest);

        return ResponseEntity.ok(BaseApiResponse.success());
    }
}
