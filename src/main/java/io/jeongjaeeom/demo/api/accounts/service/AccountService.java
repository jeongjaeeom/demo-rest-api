package io.jeongjaeeom.demo.api.accounts.service;

import io.jeongjaeeom.demo.api.accounts.domain.Account;
import io.jeongjaeeom.demo.api.accounts.domain.AccountRepository;
import io.jeongjaeeom.demo.api.accounts.domain.AccountRole;
import io.jeongjaeeom.demo.api.accounts.domain.Role;
import io.jeongjaeeom.demo.api.accounts.domain.Role.Authority;
import io.jeongjaeeom.demo.api.accounts.domain.RoleRepository;
import io.jeongjaeeom.demo.api.accounts.dto.AccountDto;
import io.jeongjaeeom.demo.api.accounts.dto.AccountDto.Response;
import io.jeongjaeeom.demo.api.accounts.exception.AccountNotFoundException;
import io.jeongjaeeom.demo.api.accounts.exception.UserDuplicatedException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author eomjeongjae
 * @since 2019/12/18
 */
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AccountService {

  private final AccountRepository accountRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final ModelMapper modelMapper;

  @Transactional
  public AccountDto.Response createAccount(AccountDto.Create dto) {
    return createAccount(dto,
        roleRepository.findByAuthority(Authority.USER).orElseThrow(IllegalArgumentException::new));
  }

  @Transactional
  public AccountDto.Response createAccount(AccountDto.Create dto, Role role) {
    Account newAccount = modelMapper.map(dto, Account.class);
    Optional<Account> optionalAccount = accountRepository.findByUsername(dto.getUsername());
    if (optionalAccount.isPresent()) {
      throw new UserDuplicatedException(dto.getUsername());
    }

    newAccount.setPassword(passwordEncoder.encode(newAccount.getPassword()));
    newAccount.addRole(AccountRole.builder().role(role).build());

    return modelMapper.map(accountRepository.save(newAccount), AccountDto.Response.class);
  }

  public Page<Response> getAccounts(AccountDto.Search search, Pageable pageable) {
    Page<Account> page = accountRepository.findAll(search.toSpecification(), pageable);
    List<AccountDto.Response> content = page.getContent().stream()
        .map(account -> modelMapper.map(account, AccountDto.Response.class))
        .collect(Collectors.toList());
    return new PageImpl<>(content, pageable, page.getTotalElements());
  }

  public AccountDto.Response getAccount(final Long id) {
    return modelMapper.map(
        accountRepository.findById(id)
            .orElseThrow(() -> new AccountNotFoundException(id)), AccountDto.Response.class);
  }

  @Transactional
  public AccountDto.Response updateAccount(final Long id, AccountDto.Update dto, String username) {
    Account existingAccount = accountRepository.findById(id)
        .orElseThrow(() -> new AccountNotFoundException(id));

    if (!existingAccount.getUsername().equals(username)) {
      throw new AccessDeniedException("Not own account");
    }

    modelMapper.map(dto, existingAccount);

    return modelMapper.map(accountRepository.save(existingAccount), Response.class);
  }

  @Transactional
  public void deleteAccount(final Long id, String username) {
    Account existingAccount = accountRepository.findById(id)
        .orElseThrow(() -> new AccountNotFoundException(id));

    if (!existingAccount.getUsername().equals(username)) {
      throw new AccessDeniedException("Not own account");
    }

    accountRepository.deleteById(id);
  }

}
