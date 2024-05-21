package com.example.springMVCApp.securityWeb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class Security {
	@Bean
	InMemoryUserDetailsManager usersManagers( ) {
		InMemoryUserDetailsManager mudm= new InMemoryUserDetailsManager(
				User.withUsername("mehdi").password("{noop}123").roles("admin").build(),
				User.withUsername("zniber").password("{noop}123").roles("user").build()
		);
		return mudm;
	}

}
