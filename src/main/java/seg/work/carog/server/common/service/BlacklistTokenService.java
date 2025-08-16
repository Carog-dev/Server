package seg.work.carog.server.common.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seg.work.carog.server.common.constant.Constant;
import seg.work.carog.server.common.util.RedisUtil;

@Service
@Transactional(readOnly = true)
public class BlacklistTokenService {

    @Transactional
    public void addTokenToBlacklist(String accessToken) {
        RedisUtil.setWithExpiryDay(accessToken + Constant.BLACKLIST_TOKEN_SUFFIX, accessToken, 7);
    }

    public boolean isBlockedToken(String accessToken) {
        return RedisUtil.has(accessToken + Constant.BLACKLIST_TOKEN_SUFFIX);
    }
}