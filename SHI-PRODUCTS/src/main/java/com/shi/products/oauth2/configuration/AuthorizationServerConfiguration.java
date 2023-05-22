package com.shi.products.oauth2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private String grantTypePassword = "password";
    private String authorizationCode = "authorization_code";
    private String refreshToken = "refresh_token";
    private String scopeRead = "read";
    private String scopeWrite = "write";
    private int tokenValidForever = 1200;
    private int refreshTokenValidForever = 3600;

    private String implicit = "implicit";
    private String trust = "trust";
    
    @Value("${basic.auth.client.id}")
    private String clientId;
    @Value("${basic.auth.client.pass}")
    private String clientPass;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientId)
                .secret(passwordEncoder().encode(clientPass))
                .authorizedGrantTypes(grantTypePassword, authorizationCode, refreshToken,implicit)
                .scopes(scopeRead, scopeWrite,trust)
                .accessTokenValiditySeconds(tokenValidForever)
                .refreshTokenValiditySeconds(refreshTokenValidForever);

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager);
    }

    private PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
