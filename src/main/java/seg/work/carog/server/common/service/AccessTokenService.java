package seg.work.carog.server.common.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seg.work.carog.server.common.constant.Constant;
import seg.work.carog.server.common.util.RedisUtil;

@Service
public class AccessTokenService {

    @Transactional
    public void addAccessToken(String userKey, String accessToken, Long expireTime) {
        RedisUtil.setWithExpiryHour(userKey + Constant.ACCESS_TOKEN_KEY_SUFFIX, accessToken, expireTime);
    }

    public String getAccessToken(String userKey) {
        return RedisUtil.getStringValue(userKey + Constant.ACCESS_TOKEN_KEY_SUFFIX);
    }

    public void deleteAccessToken(String accessToken) {
        RedisUtil.delete(accessToken + Constant.ACCESS_TOKEN_KEY_SUFFIX);
    }
}