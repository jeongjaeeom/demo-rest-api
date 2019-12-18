package io.jeongjaeeom.demo.api.configs;

import io.jeongjaeeom.demo.api.accounts.domain.Role;
import io.jeongjaeeom.demo.api.accounts.domain.Role.Authority;
import io.jeongjaeeom.demo.api.accounts.domain.RoleRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author eomjeongjae
 * @since 2019-08-26
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ApplicationInitializer implements ApplicationRunner {

  private final RoleRepository roleRepository;

  @Override
  public void run(ApplicationArguments args) {
    log.info("Application startup complete.");

    for (Authority authority : Role.Authority.values()) {
      Optional<Role> optionalRole = roleRepository.findByAuthority(authority);
      if (!optionalRole.isPresent()) {
        Role role = Role.builder().authority(authority).build();
        roleRepository.save(role);
      }
    }
  }
}
