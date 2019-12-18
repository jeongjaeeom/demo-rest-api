package io.jeongjaeeom.demo.api.accounts.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author eomjeongjae
 * @since 2019/12/18
 */
public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {

  Optional<Account> findByUsername(String username);

}
