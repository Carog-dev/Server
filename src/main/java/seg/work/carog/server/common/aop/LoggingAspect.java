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
                    + " || execution(* seg.work.carog.server.*.*.controller..*Controller.*(..))"
                    + " || execution(* seg.work.carog.server.*.service..*Service.*(..))"
                    + " || execution(* seg.work.carog.server.*.*.service..*Service.*(..))"
                    + " || execution(* seg.work.carog.server.*.mapper..*Mapper.*(..))"
                    + " || execution(* seg.work.carog.server.*.*.mapper..*Mapper.*(..))"
                    + " || execution(* seg.work.carog.server.*.repository..*Repository.*(..)))"
                    + " || execution(* seg.work.carog.server.*.*.repository..*Repository.*(..)))"
            )
    )
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        String type;
        String classNm = pjp.getSignature().getDeclaringTypeName();
        String methodNm = pjp.getSignature().getName();

        if (classNm.contains("Controller")) {
            type = "Controller";
        } else if (classNm.contains("Service")) {
            type = "Service";
        } else if (classNm.contains("Repository")) {
            type = "Repository";
        } else if (classNm.contains("Mapper")) {
            type = "Mapper";
        } else {
            type = "Etc";
        }

        Object res = pjp.proceed();
        if (pjp.proceed() instanceof ResponseEntity<?>) {
            res = ((ResponseEntity<?>) pjp.proceed()).getBody();
        }

        log.info("{} : '{}.{}()', Data={}", type, classNm, methodNm, RestUtil.convertObjectToString(res));
        return pjp.proceed();
    }
}
