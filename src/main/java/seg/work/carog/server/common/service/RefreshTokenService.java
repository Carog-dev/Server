package seg.work.carog.server.common.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seg.work.carog.server.common.constant.Constant;
import seg.work.carog.server.common.util.RedisUtil;

@Service
@Transactional(readOnly = true)
public class RefreshTokenService {

    @Transactional
    public void addRefreshToken(String accessToken, String refreshToken) {
        RedisUtil.setWithExpiryMonth(accessToken + Constant.REFRESH_TOKEN_KEY_SUFFIX, refreshToken, 6);
    }

    @Transactional
    public void renameRefreshToken(String oldKey, String accessToken) {
        RedisUtil.renameKeyIfAbsent(oldKey + Constant.REFRESH_TOKEN_KEY_SUFFIX, accessToken + Constant.REFRESH_TOKEN_KEY_SUFFIX);
    }

    public String getRefreshToken(String accessToken) {
        return RedisUtil.getStringValue(accessToken + Constant.REFRESH_TOKEN_KEY_SUFFIX);
    }

    public void deleteRefreshToken(String accessToken) {
        RedisUtil.delete(accessToken + Constant.REFRESH_TOKEN_KEY_SUFFIX);
    }
}