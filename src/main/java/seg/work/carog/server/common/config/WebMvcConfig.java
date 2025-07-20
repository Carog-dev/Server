package seg.work.carog.server.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import seg.work.carog.server.common.config.security.CustomAuthenticationPrincipalArgumentResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final CustomAuthenticationPrincipalArgumentResolver customAuthenticationPrincipalArgumentResolver;

    public WebMvcConfig(CustomAuthenticationPrincipalArgumentResolver customAuthenticationPrincipalArgumentResolver) {
        this.customAuthenticationPrincipalArgumentResolver = customAuthenticationPrincipalArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(customAuthenticationPrincipalArgumentResolver);
    }
}