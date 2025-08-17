package seg.work.carog.server.parking.controller;

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
import seg.work.carog.server.parking.dto.ParkingCostInfoResponse;
import seg.work.carog.server.parking.dto.ParkingCostInfoSaveRequest;
import seg.work.carog.server.parking.dto.ParkingCostInfoUpdateRequest;
import seg.work.carog.server.parking.service.ParkingCostInfoService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/parking")
public class ParkingCostInfoController {

    private final ParkingCostInfoService parkingCostInfoService;

    @GetMapping({"/list", "/list/{carInfoId}"})
    public ResponseEntity<?> getParkingCostInfoList(TokenUserInfo tokenUserInfo, @PathVariable(required = false) Long carInfoId, Pageable pageable) {
        Slice<ParkingCostInfoResponse> parkingCostInfoResponseList = parkingCostInfoService.getParkingCostInfoList(tokenUserInfo, carInfoId, pageable);

        return ResponseEntity.ok(BaseApiResponse.success(parkingCostInfoResponseList));
    }

    @PostMapping
    public ResponseEntity<?> saveParkingCostInfo(TokenUserInfo tokenUserInfo, @Validated @RequestBody ParkingCostInfoSaveRequest parkingCostInfoSaveRequest) {
        parkingCostInfoService.saveParkingCostInfo(tokenUserInfo, parkingCostInfoSaveRequest);

        return ResponseEntity.ok(BaseApiResponse.success());
    }

    @PutMapping
    public ResponseEntity<?> updateParkingCostInfo(TokenUserInfo tokenUserInfo, @Validated @RequestBody ParkingCostInfoUpdateRequest parkingCostInfoUpdateRequest) {
        parkingCostInfoService.updateParkingCostInfo(tokenUserInfo, parkingCostInfoUpdateRequest);

        return ResponseEntity.ok(BaseApiResponse.success());
    }
}
