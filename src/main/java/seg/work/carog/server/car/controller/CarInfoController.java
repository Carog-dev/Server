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
import seg.work.carog.server.car.dto.CarInfoRequest;
import seg.work.carog.server.car.dto.CarInfoResponse;
import seg.work.carog.server.car.dto.CarInfoSaveOrUpdateRequest;
import seg.work.carog.server.car.service.CarInfoService;
import seg.work.carog.server.common.dto.BaseResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/car")
public class CarInfoController {

    private final CarInfoService carInfoService;

    @GetMapping("/represent")
    public ResponseEntity<?> getCarRepresent(TokenUserInfo tokenUserInfo) {
        CarInfoResponse carInfoResponse = carInfoService.getCarRepresent(tokenUserInfo);

        return ResponseEntity.ok(BaseResponse.success(carInfoResponse));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getCarList(TokenUserInfo tokenUserInfo) {
        List<CarInfoResponse> carInfoResponseList = carInfoService.getCarList(tokenUserInfo);

        return ResponseEntity.ok(BaseResponse.success(carInfoResponseList));
    }

    @PostMapping
    public ResponseEntity<?> saveCarInfo(TokenUserInfo tokenUserInfo, CarInfoRequest carInfoRequest) {
        carInfoService.saveCarInfo(tokenUserInfo, carInfoRequest);

        return ResponseEntity.ok(BaseResponse.success());
    }

    @PutMapping
    public ResponseEntity<?> updateCarInfo(TokenUserInfo tokenUserInfo, CarInfoSaveOrUpdateRequest carInfoSaveOrUpdateRequest) {
        carInfoService.updateCarInfo(tokenUserInfo, carInfoSaveOrUpdateRequest);

        return ResponseEntity.ok(BaseResponse.success());
    }
}
