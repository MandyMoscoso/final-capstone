package com.mandy.capstone.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
//this is specifically for secure method. works with @Secure ({"ROLE_ADMIN","ROLE_STAFF"})
public class MethodSecurityConfiguration  extends GlobalMethodSecurityConfiguration {
}
