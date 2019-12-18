package io.jeongjaeeom.demo.api.configs.security;

import com.trabricks.security.configs.AbstractWebSecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author eomjeongjae
 * @since 2019-09-24
 */
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends AbstractWebSecurityConfig {

  @Override
  protected void configureHttpSecurity(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/oauth/**", "/oauth2/callback", "/h2-console/*").permitAll()
        .antMatchers("/admin/**").hasAnyRole("ADMIN")
        .antMatchers("/error").permitAll()
        //.anyRequest().authenticated()
        .anyRequest().permitAll()
        .and()
        .cors().configurationSource(corsConfigurationSource())
        .and()
        .csrf().disable();
  }

  CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }

}
