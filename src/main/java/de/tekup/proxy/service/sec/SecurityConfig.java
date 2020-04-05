package de.tekup.proxy.service.sec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


   
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/categories/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/products/**").permitAll();
        http.authorizeRequests().antMatchers("/auth-service/login/**","/auth-service/registerUser/**","/auth-service/registerFour/**").permitAll();
        http.authorizeRequests().antMatchers("/mongo-service/categories/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/mongo-service/products/**").hasAuthority("USER");
       
       http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }


}
