package kb04.team02.web.mvc.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* kb04.team02.web.mvc.*.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        // 메소드 실행 전에 출력 되는 로그
        logger.info("Before executing: " + joinPoint.getSignature());

        // 전달된 파라미터 출력. toString() 필요.
        Object[] methodArgs = joinPoint.getArgs();
        if (methodArgs != null && methodArgs.length > 0) {
            logger.info("Method arguments:");
            for (Object arg : methodArgs) {
                logger.info("  " + arg.toString());
            }
        }
    }

    @After("execution(* kb04.team02.web.mvc.*.controller.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        // 메소드 실행 후에 출력 되는 로그
        logger.info("After executing: " + joinPoint.getSignature());
    }
}
