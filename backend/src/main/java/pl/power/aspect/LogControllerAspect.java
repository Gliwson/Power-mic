package pl.power.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static org.slf4j.LoggerFactory.getLogger;

@Aspect
@Component
public class LogControllerAspect {

    @Around("@annotation(LogController)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        Logger log = getLogger(joinPoint.getTarget().getClass());

        log.info("Start Controller " + joinPoint.getKind() + " " + joinPoint.getSignature().getName());
        log.info("args= " + Arrays.toString(joinPoint.getArgs()));

        Object proceed = joinPoint.proceed();

        if (proceed != null) {
            log.info("result= " + proceed.toString());
        }

        log.info("Stop Controller " + joinPoint.getKind() + " " + joinPoint.getSignature().getName());

        return proceed;
    }

}
