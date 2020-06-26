package portal.education.Monolit.aspect;


import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;

@Component
@Aspect
@Log4j2(topic = "ASYNC__JSON__FILE__APPENDER_APP_STATISTIC")
public class LoggingAspect {

    @Around(value = "@annotation(LogExecutionTime)", argNames = "LogExecutionTime")
    public Object loggingInfoAdvice(ProceedingJoinPoint jp, LogExecutionTime logExecutionTimeAnnotation) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = jp.proceed();
        long executionTime = System.currentTimeMillis() - start;

        String message = jp.getSignature() + String.format("\tExecution time:%d ms", executionTime);

        if (logExecutionTimeAnnotation.logAnyway())
            log.info(message);

        if (executionTime > logExecutionTimeAnnotation.writeErrorIfTimeLess())
            log.error(message);

        return proceed;
    }


    @AfterThrowing("@annotation(LogException)")
    public void loggingErrorAdvice(JoinPoint jp) {
        log.error(jp.getSignature());
    }

}
