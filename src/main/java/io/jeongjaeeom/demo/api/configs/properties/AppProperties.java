package io.jeongjaeeom.demo.api.configs.properties;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author eomjeongjae
 * @since 2019-07-22
 */
@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties("app")
public class AppProperties {

  @NotEmpty
  private String name;

  @NotEmpty
  private String clientId;

  @NotEmpty
  private String clientSecret;

  @NotEmpty
  private String adminEmail;

  @NotEmpty
  private String adminPassword;

  @NotEmpty
  private String userEmail;

  @NotEmpty
  private String userPassword;

  @NotEmpty
  private String host;

}
