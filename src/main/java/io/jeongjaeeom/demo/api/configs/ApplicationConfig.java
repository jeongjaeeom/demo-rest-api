package io.jeongjaeeom.demo.api.configs;

import io.jeongjaeeom.demo.api.accounts.domain.AccountAdapter;
import java.util.Optional;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.client.RestTemplate;

/**
 * @author eomjeongjae
 * @since 2019-07-18
 */
@Configuration
public class ApplicationConfig {

  @Bean
  public AuditorAware<String> auditorProvider() {
    return new SecurityAuditorAware();
  }

  public static class SecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
      return Optional.ofNullable(SecurityContextHolder.getContext())
          .map(SecurityContext::getAuthentication)
          .filter(Authentication::isAuthenticated)
          .filter(authentication -> !AnonymousAuthenticationToken.class
              .isAssignableFrom(authentication.getClass()))
          .map(authentication -> {
            if (OAuth2Authentication.class
                .isAssignableFrom(authentication.getClass())) {
              return authentication.getPrincipal().toString();
            }
            return ((AccountAdapter) authentication.getPrincipal()).getUsername();
          });
    }
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

}
