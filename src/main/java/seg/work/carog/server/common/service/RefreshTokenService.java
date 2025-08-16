package seg.work.carog.server.common.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seg.work.carog.server.common.constant.Constant;
import seg.work.carog.server.common.util.RedisUtil;

@Service
@Transactional(readOnly = true)
public class RefreshTokenService {

    @Transactional
    public void addTokenToRefresh(String accessToken, String refreshToken) {
        RedisUtil.setWithExpiryMonth(accessToken + Constant.REFRESH_TOKEN_KEY_SUFFIX, refreshToken, 6);
    }

    @Transactional
    public void renameTokenToRefresh(String oldKey, String accessToken) {
        RedisUtil.renameKeyIfAbsent(oldKey + Constant.REFRESH_TOKEN_KEY_SUFFIX, accessToken + Constant.REFRESH_TOKEN_KEY_SUFFIX);
    }

    public String getRefreshToken(String accessToken) {
        return RedisUtil.getStringValue(accessToken + Constant.REFRESH_TOKEN_KEY_SUFFIX);
    }
}