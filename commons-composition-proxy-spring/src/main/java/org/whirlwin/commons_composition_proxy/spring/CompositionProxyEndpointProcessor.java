package org.whirlwin.commons_composition_proxy.spring;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public final class CompositionProxyEndpointProcessor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {

        System.out.println("I am a processor!");

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
