package seg.work.carog.server.common.aop;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import seg.work.carog.server.common.annotation.Except;
import seg.work.carog.server.common.util.ObjectUtil;

@Slf4j
@Aspect
@Component
public class ControllerAspect {

    @Pointcut("execution(public * seg.work.carog.server.*.controller..*Controller.*(..)) && !(@annotation(seg.work.carog.server.common.annotation.Except) || @target(seg.work.carog.server.common.annotation.Except))")
    public void loggingControllerPointCut() {
    }

    @Before("loggingControllerPointCut()")
    public void before(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        Method method = getMethodFromJoinPoint(joinPoint);
        if (method != null && !method.isAnnotationPresent(Except.class)) {
            log.info("{} - {} called with args: {}", className, methodName, ObjectUtil.convertObjectToString(args));
        }
    }

    private Method getMethodFromJoinPoint(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();

        if (!(signature instanceof MethodSignature methodSignature)) {
            return null;
        }

        try {
            return joinPoint.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}