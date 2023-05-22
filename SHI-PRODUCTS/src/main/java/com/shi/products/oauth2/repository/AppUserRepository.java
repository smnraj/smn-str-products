package com.shi.products.oauth2.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shi.products.oauth2.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	
	Optional<AppUser> findByusername(String quoteId);
}
