package com.shi.products.oauth2.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shi.products.config.BatchConfig;
import com.shi.products.oauth2.entity.AppUser;
import com.shi.products.oauth2.repository.AppUserRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
public class DefaultAuthenticationProvider implements AuthenticationProvider,UserDetailsService {
	private static final Logger log = LoggerFactory.getLogger(DefaultAuthenticationProvider.class);
    private AppUserRepository appUserRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	
    	log.info("called...");
    	
        Optional<AppUser> appUser = appUserRepository.findByusername(authentication.getName());
      
        if(appUser.isPresent()) {
            AppUser user = appUser.get();
            String username = authentication.getName();
            String password = (String)authentication.getCredentials();
            
            if(username.equalsIgnoreCase(user.getUsername()) &&
               password.equalsIgnoreCase(user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(
                		user.getUsername(),
                		user.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
                );
            }
        }

        throw new UsernameNotFoundException("User not found");
    }
    
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    	Optional<AppUser> appUser = appUserRepository.findByusername(userId);
		if(appUser == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		AppUser user = appUser.get();
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
	}
    
    private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}
    
    
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

