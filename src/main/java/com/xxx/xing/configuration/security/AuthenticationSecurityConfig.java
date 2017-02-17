package com.xxx.xing.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

/**
 * @author loocao
 * @since 2017/1/11
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class AuthenticationSecurityConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private AdminDetailsService adminDetailsService;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setSaltSource(user -> {
            AdminUser adminUser = (AdminUser) user;
            return adminUser.getSalt();
        });
        provider.setUserDetailsService(adminDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        auth.authenticationProvider(provider);
    }
}
