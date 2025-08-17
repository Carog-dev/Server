package seg.work.carog.server.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seg.work.carog.server.admin.service.AdminService;
import seg.work.carog.server.auth.dto.TokenUserInfo;
import seg.work.carog.server.common.dto.BaseApiResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/revoke/token")
    public ResponseEntity<?> revokeToken(TokenUserInfo tokenUserInfo) {
        adminService.revokeToken(tokenUserInfo);

        return ResponseEntity.ok(BaseApiResponse.success());
    }

    @PostMapping("/revoke/token/{userKey}")
    public ResponseEntity<?> revokeToken(TokenUserInfo tokenUserInfo, @PathVariable String userKey) {
        adminService.revokeToken(tokenUserInfo, userKey);

        return ResponseEntity.ok(BaseApiResponse.success());
    }

}
