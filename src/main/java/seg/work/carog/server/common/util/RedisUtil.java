package seg.work.carog.server.common.util;

import java.util.concurrent.TimeUnit;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import seg.work.carog.server.common.constant.Message;
import seg.work.carog.server.common.exception.BaseException;

@Component
public class RedisUtil {

    @Setter
    private static RedisTemplate<String, Object> redisTemplate;

    // 데이터 저장
    public static void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 만료시간이 있는 데이터 저장
    public static void setWithExpiryMs(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    public static void setWithExpirySec(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public static void setWithExpiryMin(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MINUTES);
    }

    public static void setWithExpiryHour(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.HOURS);
    }

    public static void setWithExpiryDay(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.DAYS);
    }

    /**
     * TimeUnit이 달은 지원하지 않으므로 timeout * 30으로 계산
     */
    public static void setWithExpiryMonth(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout * 30, TimeUnit.DAYS);
    }

    /**
     * TimeUnit이 년은 지원하지 않으므로 timeout * 365으로 계산
     */
    public static void setWithExpiryYear(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout * 365, TimeUnit.DAYS);
    }

    // 데이터 조회
    public static Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 데이터 조회 후 타입 변환
    public static String getStringValue(String key) {
        Object value = get(key);
        if (value == null) {
            throw new BaseException(Message.NO_DATA);
        }
        return value.toString();
    }

    public static Long getLongValue(String key) {
        Object value = get(key);
        if (value == null) {
            throw new BaseException(Message.NO_DATA);
        }
        return Long.valueOf(value.toString());
    }

    // 키 존재 확인
    public static boolean has(String key) {
        return redisTemplate.hasKey(key);
    }

    // 데이터 삭제
    public static boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    // ======================== hash ========================
    // hash table 조회
    public static Object getHash(String hash, String key) {
        return redisTemplate.opsForHash().get(hash, key);
    }

    public static Long getHashLongValue(String hash, String key) {
        Object value = getHash(hash, key);
        if (value == null) {
            throw new BaseException(Message.NO_DATA);
        }
        return Long.valueOf(value.toString());
    }

    // hash table 추가
    public static void putHash(String hash, String key, Object value) {
        redisTemplate.opsForHash().put(hash, key, value);
    }

    // hash table 만료시간 지정
    public static void setExpireHash(String hash, long timeout, TimeUnit unit) {
        redisTemplate.expire(hash, timeout, unit);
    }

    // hash table 키 존재 확인
    public static boolean hashHasKey(String hash, Object key) {
        return redisTemplate.opsForHash().hasKey(hash, key);
    }

    // hash table 삭제
    public static void deleteHash(String hash, String key) {
        redisTemplate.opsForHash().delete(hash, key);
    }

    // ======================== set ========================
    // set 추가
    public static Boolean isSetMember(String set, Object value) {
        return redisTemplate.opsForSet().isMember(set, value);
    }

    // set 추가
    public static void putSet(String set, Object value) {
        redisTemplate.opsForSet().add(set, value);
    }

    // set 만료시간 지정
    public static void setExpireSet(String set, long timeout, TimeUnit unit) {
        redisTemplate.expire(set, timeout, unit);
    }
}