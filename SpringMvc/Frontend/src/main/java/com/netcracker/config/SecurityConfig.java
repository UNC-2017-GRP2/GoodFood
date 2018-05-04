package com.netcracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ApplicationContextConfig context;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").authenticated()
                .antMatchers("/home").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/profile").authenticated()
                .antMatchers("/basket").authenticated()
                .antMatchers("/basket").hasAnyRole("USER","ADMIN")
                .antMatchers("/free-orders").hasAnyRole("COURIER","ADMIN")
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/my-orders").authenticated()
                .antMatchers("/current-orders").authenticated()
                .antMatchers("/current-orders").hasAnyRole("COURIER","ADMIN")
                .antMatchers("/drunk_receive").permitAll()
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .defaultSuccessUrl("/home")
                        .failureUrl("/login?error")
                        .usernameParameter("username")
                        .passwordParameter("password")
                .and()
                    .logout()
                        .logoutSuccessUrl("/login")
                        .logoutUrl("/logout")
                .and()
                    .rememberMe()
                        .tokenRepository(context.persistentTokenRepository())
                        .key("rem-me-key")
                        .tokenValiditySeconds(86400);

    }

}
