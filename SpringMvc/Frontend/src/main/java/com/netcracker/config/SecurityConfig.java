package com.netcracker.config;

import com.netcracker.service.impl.MySocialUserDetailsService;
import com.netcracker.service.impl.MyUserDetailsService;
import com.netcracker.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialAuthenticationProvider;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsServiceImpl;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private ApplicationContextConfig context;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(encoder);
    }
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private MySocialUserDetailsService mySocialUserDetailsService;

    @Bean
    public AuthenticationProvider getAuthenticationProvider() {
        return new SocialAuthenticationProvider(usersConnectionRepository, mySocialUserDetailsService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/").authenticated()
                .antMatchers("/home").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/profile").authenticated()
                .antMatchers("/profile").hasAnyRole("USER", "ADMIN", "COURIER")
                .antMatchers("/basket").authenticated()
                .antMatchers("/basket").hasAnyRole("USER","ADMIN")
                .antMatchers("/free-orders").hasAnyRole("COURIER","ADMIN")
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/my-orders/**").authenticated()
                .antMatchers("/my-orders/**").hasAnyRole("USER", "ADMIN", "COURIER")
                .antMatchers("/current-orders").authenticated()
                .antMatchers("/current-orders").hasAnyRole("COURIER","ADMIN")
                .antMatchers("/drunk").authenticated()
                .antMatchers("/drunk").hasAnyRole("USER", "ADMIN", "COURIER")
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
                        .tokenValiditySeconds(86400)
                .and()
                    .apply(new SpringSocialConfigurer());


        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter,CsrfFilter.class);
    }



}
