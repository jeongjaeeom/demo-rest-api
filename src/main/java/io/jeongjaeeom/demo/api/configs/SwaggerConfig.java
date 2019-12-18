package io.jeongjaeeom.demo.api.configs;

import static java.util.Collections.singletonList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

import com.fasterxml.classmate.TypeResolver;
import com.trabricks.security.properties.WebSecurityProperties;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author eomjeongjae
 * @since 2019/11/18
 */
@EnableSwagger2WebMvc
@RequiredArgsConstructor
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

  private final TypeResolver typeResolver;
  private final WebSecurityProperties webSecurityProperties;

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.ant("/api/**"))
        .build()
        .directModelSubstitute(LocalDate.class, String.class)
        .genericModelSubstitutes(ResponseEntity.class)
        .alternateTypeRules(
            newRule(typeResolver.resolve(DeferredResult.class,
                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                typeResolver.resolve(WildcardType.class)
            ))
        .useDefaultResponseMessages(false)
        .ignoredParameterTypes(Pageable.class, PagedResourcesAssembler.class,
            AuthenticationPrincipal.class)
        /*.globalResponseMessage(RequestMethod.GET,
            singletonList(new ResponseMessageBuilder()
                .code(500)
                .message("500 message")
                .responseModel(new ModelRef("Error"))
                .build()))*/
        .securitySchemes(singletonList(securityScheme()))
        .securityContexts(singletonList(securityContext()))
        //.enableUrlTemplating(true)
        .apiInfo(getApiInfo());
        /*.globalOperationParameters(
            singletonList(new ParameterBuilder()
                .name("someGlobalParameter")
                .description("Description of someGlobalParameter")
                .modelRef(new ModelRef("string"))
                .parameterType("query")
                .required(true)
                .build()))*/
    //.tags(new Tag("Trabricks", "All apis relating to trabricks"));
  }

  @Bean
  public SecurityConfiguration securityConfiguration() {
    return SecurityConfigurationBuilder.builder()
        .clientId(webSecurityProperties.getOauth().getClientId())
        .clientSecret(webSecurityProperties.getOauth().getClientSecret())
        .scopeSeparator("password refresh_token client_credentials")
        .useBasicAuthenticationWithAccessCodeGrant(true)
        .build();
  }

  private ApiInfo getApiInfo() {
    return new ApiInfo(
        "Demo Application",
        "Demo Rest API",
        "",
        "",
        new Contact("trabricks", "https://trabricks.com", "service@trabricks.com"),
        "",
        "",
        Collections.emptyList()
    );
  }

  private SecurityScheme securityScheme() {
    GrantType grantType = new ResourceOwnerPasswordCredentialsGrant("/oauth/token");

    SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
        .grantTypes(Arrays.asList(grantType))
        .scopes(Arrays.asList(scopes()))
        .build();

    return oauth;
  }

  private springfox.documentation.spi.service.contexts.SecurityContext securityContext() {
    return springfox.documentation.spi.service.contexts.SecurityContext.builder()
        .securityReferences(
            Arrays.asList(new SecurityReference("spring_oauth", scopes())))
        .forPaths(PathSelectors.ant("/api/**"))
        .build();
  }

  private AuthorizationScope[] scopes() {
    AuthorizationScope[] scopes = {
        new AuthorizationScope("read", "for read operations"),
        new AuthorizationScope("write", "for write operations")};
    return scopes;
  }

}
