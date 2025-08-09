package seg.work.carog.server.common.service;

import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seg.work.carog.server.common.constant.Constant;
import seg.work.carog.server.common.util.RedisUtil;

@Service
@Transactional(readOnly = true)
public class AuthTokenService {

    @Transactional
    public void addTokenToAuthMap(String authToken, Long userId) {
        RedisUtil.putHash(Constant.AUTH_TOKEN_KEY, authToken, userId.toString());
        RedisUtil.setExpireSet(Constant.AUTH_TOKEN_KEY, 10, TimeUnit.MINUTES);
    }

    public Boolean checkHashHasKey(String authToken) {
        return RedisUtil.hashHasKey(Constant.AUTH_TOKEN_KEY, authToken);
    }

    public Long getAuthMapByAuthToken(String authToken) {
        return RedisUtil.getHashLongValue(Constant.AUTH_TOKEN_KEY, authToken);
    }
}