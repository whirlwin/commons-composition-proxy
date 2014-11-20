package org.whirlwin.commons_composition_proxy.spring;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("org.whirlwin.commons_composition_proxy.spring")
public class CommonsCompositionProxySpringConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private CompositionProxyEndpointProcessor compositionProxyEndpointProcessor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(compositionProxyEndpointProcessor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }
}
