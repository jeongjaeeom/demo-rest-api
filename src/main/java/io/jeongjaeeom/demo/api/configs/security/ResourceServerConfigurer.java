package io.jeongjaeeom.demo.api.configs.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * @author eomjeongjae
 * @since 29/09/2019
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        .antMatcher("/api/**")
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/api/photos/*/validation").permitAll()
        .antMatchers(HttpMethod.POST, "/api/photos/*").permitAll()
        .antMatchers(HttpMethod.POST, "/api/accounts").permitAll()
        .antMatchers("/api/login-with-social").permitAll()
        .antMatchers("/api/check-email").permitAll()
        //.antMatchers("/api/**").hasAnyRole("USER")
        //.anyRequest().authenticated()
        .anyRequest().permitAll()
        .and()
        .exceptionHandling()
        .accessDeniedHandler(new OAuth2AccessDeniedHandler());
  }
}
