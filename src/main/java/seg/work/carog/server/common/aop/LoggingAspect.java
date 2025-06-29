package seg.work.carog.server.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import seg.work.carog.server.common.util.RestUtil;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around(
            ("(execution(* seg.work.carog.server.*.controller..*Controller.*(..))"
                    + " || execution(* seg.work.carog.server.*.service..*Service.*(..))"
                    + " || execution(* seg.work.carog.server.*.mapper..*Mapper.*(..))"
                    + " || execution(* seg.work.carog.server.*.repository..*Repository.*(..)))"
            )
    )
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        String classNm = pjp.getSignature().getDeclaringTypeName();
        String methodNm = pjp.getSignature().getName();

        Object result = pjp.proceed();
        Object res = result;
        if (result instanceof ResponseEntity<?>) {
            res = ((ResponseEntity<?>) result).getBody();
        }

        if (classNm.contains("Controller")) {
            log.info("Controller : '{}.{}()', Data={}", classNm, methodNm, RestUtil.convertObjectToString(res));
        } else if (classNm.contains("Service")) {
            log.info("Service : '{}.{}()', Data={}", classNm, methodNm, RestUtil.convertObjectToString(res));
        } else if (classNm.contains("Repository")) {
            log.info("Repository : '{}.{}()', Data={}", classNm, methodNm, RestUtil.convertObjectToString(res));
        } else if (classNm.contains("Mapper")) {
            log.info("Mapper : '{}.{}()', Data={}", classNm, methodNm, RestUtil.convertObjectToString(res));
        }

        return result;
    }
}
