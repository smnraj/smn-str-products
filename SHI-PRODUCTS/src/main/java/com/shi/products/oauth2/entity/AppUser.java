package com.shi.products.oauth2.entity;

import lombok.Data;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SHI_PRODUCTS_QUOTE_USER")
@Data
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String username;
    private String password;
    private String role;
    private Timestamp createdTime;
    private Timestamp modifiedTime;
	private String isActive;
	private String flag;

}