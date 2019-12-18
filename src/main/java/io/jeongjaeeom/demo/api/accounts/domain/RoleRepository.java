package io.jeongjaeeom.demo.api.accounts.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author eomjeongjae
 * @since 2019/11/06
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByAuthority(Role.Authority authority);

}
