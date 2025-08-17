package seg.work.carog.server.accident.controller;

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
import seg.work.carog.server.accident.dto.AccidentCostInfoResponse;
import seg.work.carog.server.accident.dto.AccidentCostInfoSaveRequest;
import seg.work.carog.server.accident.dto.AccidentCostInfoUpdateRequest;
import seg.work.carog.server.accident.service.AccidentCostInfoService;
import seg.work.carog.server.auth.dto.TokenUserInfo;
import seg.work.carog.server.common.dto.BaseApiResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accident")
public class AccidentController {

    private final AccidentCostInfoService accidentCostInfoService;

    @GetMapping({"/list", "/list/{carInfoId}"})
    public ResponseEntity<?> getAccidentCostInfoList(TokenUserInfo tokenUserInfo, @PathVariable(required = false) Long carInfoId, Pageable pageable) {
        Slice<AccidentCostInfoResponse> maintenanceCostInfoResponseList = accidentCostInfoService.getAccidentCostInfoList(tokenUserInfo, carInfoId, pageable);

        return ResponseEntity.ok(BaseApiResponse.success(maintenanceCostInfoResponseList));
    }

    @PostMapping
    public ResponseEntity<?> saveAccidentCostInfo(TokenUserInfo tokenUserInfo, @Validated @RequestBody AccidentCostInfoSaveRequest accidentCostInfoSaveRequest) {
        accidentCostInfoService.saveAccidentCostInfo(tokenUserInfo, accidentCostInfoSaveRequest);

        return ResponseEntity.ok(BaseApiResponse.success());
    }

    @PutMapping
    public ResponseEntity<?> updateAccidentCostInfo(TokenUserInfo tokenUserInfo, @Validated @RequestBody AccidentCostInfoUpdateRequest accidentCostInfoUpdateRequest) {
        accidentCostInfoService.updateAccidentCostInfo(tokenUserInfo, accidentCostInfoUpdateRequest);

        return ResponseEntity.ok(BaseApiResponse.success());
    }

}
