package seg.work.carog.server.common.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;
import seg.work.carog.server.common.constant.Constant;
import seg.work.carog.server.common.util.RedisUtil;

@Service
@Transactional(readOnly = true)
public class BlacklistTokenService {

    @Transactional
    public void addTokenToBlacklist(String accessToken) {
        RedisUtil.putSet(Constant.BLACKLIST_TOKEN_KEY, accessToken);
        RedisUtil.setExpireSet(Constant.BLACKLIST_TOKEN_KEY, 7, TimeUnit.DAYS);
    }

    public boolean isBlockedToken(String accessToken) {
        Boolean isMember = RedisUtil.isSetMember(Constant.BLACKLIST_TOKEN_KEY, accessToken);
        return isMember != null ? isMember : false;
    }
}