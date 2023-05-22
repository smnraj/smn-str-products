package com.shi.products.oauth2.configuration;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.shi.products.oauth2.repository.AppUserRepository;
import com.shi.products.oauth2.service.DefaultAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration{

    @Autowired
    private AppUserRepository appUserRepository;
    
    
    @Autowired
    private AppUserRepository userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new DefaultAuthenticationProvider(appUserRepository));
    }
    
    @Autowired
    public void globalUserDetail(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new DefaultAuthenticationProvider(userDetailsService));
    }	
}
