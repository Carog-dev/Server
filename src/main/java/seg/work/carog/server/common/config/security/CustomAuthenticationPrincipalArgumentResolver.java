package seg.work.carog.server.common.config.security;

import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import seg.work.carog.server.auth.dto.UserInfo;
import seg.work.carog.server.common.constant.Message;
import seg.work.carog.server.common.exception.BaseException;
import seg.work.carog.server.common.util.RedisUtil;

@Component
public class CustomAuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == UserInfo.class;
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserInfo userInfo) {
            if (RedisUtil.has(userInfo.getKey())) {
                userInfo.setId(RedisUtil.getLongValue(userInfo.getKey()));
            } else {
                throw new BaseException(Message.USER_NOT_FOUND);
            }
            return userInfo;
        }
        return null;
    }
}