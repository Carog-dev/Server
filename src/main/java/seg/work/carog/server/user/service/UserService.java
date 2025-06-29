package seg.work.carog.server.user.service;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seg.work.carog.server.common.util.JwtUtil;
import seg.work.carog.server.constant.Constant;
import seg.work.carog.server.user.dto.UserInfoDTO;
import seg.work.carog.server.user.entity.UserInfoEntity;
import seg.work.carog.server.user.repository.UserInfoRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserInfoRepository userInfoRepository;
    private final JwtUtil jwtUtil;

    public UserInfoDTO getUserInfo(Cookie[] cookies) {
        String accessToken = null;
        for (Cookie cookie : cookies) {
            if (Constant.TOKEN_NAME.equals(cookie.getName())) {
                accessToken = cookie.getValue();
                break;
            }
        }

        if (accessToken == null) {
            throw new IllegalArgumentException("Access token not found in cookies");
        } else if (jwtUtil.validateToken(accessToken)) {
            String oauthId = jwtUtil.getOauthIdFromToken(accessToken);
            UserInfoEntity userInfoEntity = userInfoRepository.findByOauthId(oauthId).orElseThrow(() -> new IllegalArgumentException("User not found"));
            return UserInfoDTO.fromEntity(userInfoEntity);
        } else {
            throw new IllegalArgumentException("Invalid access token");
        }
    }
}
