package seg.work.carog.server.accident.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import seg.work.carog.server.accident.dto.AccidentCostInfoResponse;
import seg.work.carog.server.accident.dto.AccidentCostInfoSaveRequest;
import seg.work.carog.server.accident.dto.AccidentCostInfoUpdateRequest;
import seg.work.carog.server.accident.service.AccidentCostInfoService;
import seg.work.carog.server.auth.dto.TokenUserInfo;
import seg.work.carog.server.common.dto.BaseResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accident")
@Tag(name = "사고")
public class AccidentController {

    private final AccidentCostInfoService accidentCostInfoService;

    @GetMapping({"/list", "/list/{carInfoId}"})
    @Parameter(name = "carInfoId", description = "대표챠랑 또는 선택한 차량 아이디")
    public ResponseEntity<?> getAccidentCostInfoList(TokenUserInfo tokenUserInfo, @PathVariable(required = false) Long carInfoId) {
        Slice<AccidentCostInfoResponse> maintenanceCostInfoResponseList = accidentCostInfoService.getAccidentCostInfoList(tokenUserInfo, carInfoId);

        return ResponseEntity.ok(BaseResponse.success(maintenanceCostInfoResponseList));
    }

    @PostMapping
    public ResponseEntity<?> saveAccidentCostInfo(TokenUserInfo tokenUserInfo, @RequestBody AccidentCostInfoSaveRequest accidentCostInfoSaveRequest) {
        accidentCostInfoService.saveAccidentCostInfo(tokenUserInfo, accidentCostInfoSaveRequest);

        return ResponseEntity.ok(BaseResponse.success());
    }

    @PutMapping
    public ResponseEntity<?> updateAccidentCostInfo(TokenUserInfo tokenUserInfo, @RequestBody AccidentCostInfoUpdateRequest accidentCostInfoUpdateRequest) {
        accidentCostInfoService.updateAccidentCostInfo(tokenUserInfo, accidentCostInfoUpdateRequest);

        return ResponseEntity.ok(BaseResponse.success());
    }

}
