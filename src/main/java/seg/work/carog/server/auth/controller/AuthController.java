package seg.work.carog.server.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seg.work.carog.server.auth.dto.KakaoLoginRequest;
import seg.work.carog.server.auth.dto.LoginResponse;
import seg.work.carog.server.auth.dto.UserInfo;
import seg.work.carog.server.auth.service.AuthService;
import seg.work.carog.server.common.dto.BaseResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/kakao")
    public ResponseEntity<?> kakaoLogin(@Valid @RequestBody KakaoLoginRequest request) {
        LoginResponse response = authService.loginWithKakao(request.getCode());

        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @PostMapping("/logout/kakao")
    public ResponseEntity<?> kakaoLogout(UserInfo userInfo) {
        authService.logoutWithKakao(userInfo);

        return ResponseEntity.ok(BaseResponse.success());
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(UserInfo userInfo) {
        authService.getProfile(userInfo);
        return ResponseEntity.ok(BaseResponse.success());
    }
}