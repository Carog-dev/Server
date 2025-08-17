package seg.work.carog.server.oil.controller;

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
import seg.work.carog.server.oil.dto.OilCostInfoResponse;
import seg.work.carog.server.oil.dto.OilCostInfoSaveRequest;
import seg.work.carog.server.oil.dto.OilCostInfoUpdateRequest;
import seg.work.carog.server.oil.service.OilCostInfoService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oil")
public class OilCostInfoController {

    private final OilCostInfoService oilCostInfoService;

    @GetMapping({"/list", "/list/{carInfoId}"})
    public ResponseEntity<?> getOilCostInfoList(TokenUserInfo tokenUserInfo, @PathVariable(required = false) Long carInfoId, Pageable pageable) {
        Slice<OilCostInfoResponse> oilCostInfoResponseList = oilCostInfoService.getOilCostInfoList(tokenUserInfo, carInfoId, pageable);

        return ResponseEntity.ok(BaseApiResponse.success(oilCostInfoResponseList));
    }

    @PostMapping
    public ResponseEntity<?> saveOilCostInfo(TokenUserInfo tokenUserInfo, @Validated @RequestBody OilCostInfoSaveRequest oilCostInfoSaveRequest) {
        oilCostInfoService.saveOilCostInfo(tokenUserInfo, oilCostInfoSaveRequest);

        return ResponseEntity.ok(BaseApiResponse.success());
    }

    @PutMapping
    public ResponseEntity<?> updateOilCostInfo(TokenUserInfo tokenUserInfo, @Validated @RequestBody OilCostInfoUpdateRequest oilCostInfoUpdateRequest) {
        oilCostInfoService.updateOilCostInfo(tokenUserInfo, oilCostInfoUpdateRequest);

        return ResponseEntity.ok(BaseApiResponse.success());
    }
}
