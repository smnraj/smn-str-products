package com.shi.products.oauth2.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("resource_id").stateless(false);
    }

	/*
	 * @Override public void configure(HttpSecurity http) throws Exception {
	 * http.anonymous().disable() .authorizeRequests()
	 * .antMatchers("/Quotes/**").authenticated()
	 * .and().exceptionHandling().accessDeniedHandler(new
	 * OAuth2AccessDeniedHandler()); }
	 */
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
    	
        http.csrf().disable().anonymous().disable()
                .authorizeRequests()
                .antMatchers("/Quotes/**").authenticated()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler())
    	        .and(). headers().addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy","default-src 'self'"))
    	        .addHeaderWriter(new StaticHeadersWriter("Strict-Transport-Security","max-age=31536000; includeSubDomains; preload"))
    			.addHeaderWriter(new StaticHeadersWriter("X-WebKit-CSP","default-src 'self'"))
    	            .httpStrictTransportSecurity()
    	                .includeSubDomains(true)
    	                .maxAgeInSeconds(31536000)
    	                .preload(false);
    }
}
