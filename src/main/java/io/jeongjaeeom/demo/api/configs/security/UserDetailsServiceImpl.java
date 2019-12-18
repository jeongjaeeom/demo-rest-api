package io.jeongjaeeom.demo.api.configs.security;

import io.jeongjaeeom.demo.api.accounts.domain.Account;
import io.jeongjaeeom.demo.api.accounts.domain.AccountAdapter;
import io.jeongjaeeom.demo.api.accounts.domain.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author eomjeongjae
 * @since 2019/12/06
 */
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = accountRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));

    return new AccountAdapter(account);
  }
}
