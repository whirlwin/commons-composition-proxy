package org.whirlwin.commons_composition_proxy.spring.timeout;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.stereotype.Component;
import org.whirlwin.CompositionProxyEndpoint;

import java.lang.reflect.Method;
import java.util.concurrent.*;

@Component
public class TimeoutAdvisor extends AbstractPointcutAdvisor {

    private static final MethodInterceptor TIMEOUT_INTERCEPTOR = methodInvocation -> {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Future<Void> future = executorService.submit(() -> {
            try {
                methodInvocation.proceed();
            } catch (final Throwable throwable) {
                System.err.println("Failed to execute method " + methodInvocation.getMethod().getName());
            }
            return null;
        });
        try {
            future.get(getTimeout(methodInvocation), TimeUnit.MILLISECONDS);
        } catch(final TimeoutException e) {
            System.out.println("Method took more time: " + methodInvocation.getMethod().getName());
        }
        executorService.shutdownNow();
        return null;
    };

    private static long getTimeout(final MethodInvocation methodInvocation) {
        return methodInvocation.getMethod().getAnnotation(CompositionProxyEndpoint.class).timeout();
    }

    @Override
    public Pointcut getPointcut() {
        return new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(final Method method, final Class<?> aClass) {
                return method.isAnnotationPresent(CompositionProxyEndpoint.class);
            }
        };
    }

    @Override
    public Advice getAdvice() {
        return TIMEOUT_INTERCEPTOR;
    }
}
