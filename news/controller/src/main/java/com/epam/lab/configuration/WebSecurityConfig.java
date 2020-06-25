package com.epam.lab.configuration;

import com.epam.lab.jwt.JwtConfigurer;
import com.epam.lab.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/users/**").permitAll()
                .antMatchers(HttpMethod.GET, "/news/**").permitAll()
                .antMatchers(HttpMethod.GET, "/tags/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/tags/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/tags/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/tags/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/authors/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/authors/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/authors/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/authors/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/news/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/news/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/news/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
