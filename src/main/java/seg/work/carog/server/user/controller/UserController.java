package seg.work.carog.server.user.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seg.work.carog.server.common.util.JwtUtil;
import seg.work.carog.server.constant.Constant;
import seg.work.carog.server.user.dto.UserInfoDTO;
import seg.work.carog.server.user.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @GetMapping("/get-session")
    public ResponseEntity<Boolean> getUserSession(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Constant.TOKEN_NAME.equals(cookie.getName())) {
                    String accessToken = cookie.getValue();

                    return ResponseEntity.ok(jwtUtil.validateToken(accessToken));
                }
            }
        }

        return ResponseEntity.ok(false);
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfoDTO> getUserInfo(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            UserInfoDTO userInfoDTO = userService.getUserInfo(cookies);
            return ResponseEntity.ok(userInfoDTO);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}
