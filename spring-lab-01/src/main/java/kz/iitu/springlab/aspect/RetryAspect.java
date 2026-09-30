package kz.iitu.springlab.aspect;

import kz.iitu.springlab.audit.RetryOnFailure;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0)
public class RetryAspect {

    @Around("@annotation(retryOnFailure)")
    public Object retry(
            ProceedingJoinPoint pjp,
            RetryOnFailure retryOnFailure) throws Throwable {

        int attempts = retryOnFailure.attempts();

        Throwable lastException = null;

        for (int i = 1; i <= attempts; i++) {
            try {
                return pjp.proceed();
            } catch (Throwable ex) {
                lastException = ex;

                System.out.println(
                        "[RETRY] attempt " + i
                                + "/" + attempts
                                + " failed: "
                                + ex.getMessage()
                );
            }
        }

        throw lastException;
    }
}