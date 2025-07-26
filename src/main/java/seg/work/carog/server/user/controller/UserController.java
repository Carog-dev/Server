package seg.work.carog.server.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seg.work.carog.server.auth.dto.TokenUserInfo;
import seg.work.carog.server.user.dto.UserInfoResponse;
import seg.work.carog.server.common.dto.BaseResponse;
import seg.work.carog.server.user.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(TokenUserInfo tokenUserInfo) {
        UserInfoResponse userInfoResponse = userService.getProfile(tokenUserInfo);

        return ResponseEntity.ok(BaseResponse.success(userInfoResponse));
    }
}
