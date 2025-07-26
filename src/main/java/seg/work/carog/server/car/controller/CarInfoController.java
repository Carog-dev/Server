package seg.work.carog.server.car.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seg.work.carog.server.auth.dto.TokenUserInfo;
import seg.work.carog.server.car.dto.CarInfoChangeRepresentRequest;
import seg.work.carog.server.car.dto.CarInfoSaveRequest;
import seg.work.carog.server.car.dto.CarInfoResponse;
import seg.work.carog.server.car.dto.CarInfoUpdateRequest;
import seg.work.carog.server.car.service.CarInfoService;
import seg.work.carog.server.common.dto.BaseResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/car")
public class CarInfoController {

    private final CarInfoService carInfoService;

    @GetMapping("/represent")
    public ResponseEntity<?> getRepresentCarInfo(TokenUserInfo tokenUserInfo) {
        CarInfoResponse carInfoResponse = carInfoService.getRepresentCarInfo(tokenUserInfo);

        return ResponseEntity.ok(BaseResponse.success(carInfoResponse));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getListCarInfo(TokenUserInfo tokenUserInfo) {
        List<CarInfoResponse> carInfoResponseList = carInfoService.getListCarInfo(tokenUserInfo);

        return ResponseEntity.ok(BaseResponse.success(carInfoResponseList));
    }

    @PostMapping
    public ResponseEntity<?> saveCarInfo(TokenUserInfo tokenUserInfo, CarInfoSaveRequest carInfoSaveRequest) {
        carInfoService.saveCarInfo(tokenUserInfo, carInfoSaveRequest);

        return ResponseEntity.ok(BaseResponse.success());
    }

    @PutMapping
    public ResponseEntity<?> updateCarInfo(TokenUserInfo tokenUserInfo, CarInfoUpdateRequest carInfoUpdateRequest) {
        carInfoService.updateCarInfo(tokenUserInfo, carInfoUpdateRequest);

        return ResponseEntity.ok(BaseResponse.success());
    }

    @PutMapping("/represent")
    public ResponseEntity<?> updateRepresentCarInfo(TokenUserInfo tokenUserInfo, CarInfoChangeRepresentRequest carInfoChangeRepresentRequest) {
        carInfoService.updateRepresentCarInfo(tokenUserInfo, carInfoChangeRepresentRequest);

        return ResponseEntity.ok(BaseResponse.success());
    }
}
