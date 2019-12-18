package io.jeongjaeeom.demo.api.accounts.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author eomjeongjae
 * @since 2019/11/06
 */
public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {

}
