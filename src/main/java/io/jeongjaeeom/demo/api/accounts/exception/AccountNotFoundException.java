package io.jeongjaeeom.demo.api.accounts.exception;

import com.trabricks.web.common.HttpStatusException;
import org.springframework.http.HttpStatus;

/**
 * @author eomjeongjae
 * @since 2019-08-23
 */
public class AccountNotFoundException extends HttpStatusException {

  public AccountNotFoundException(Long id) {
    super(HttpStatus.NOT_FOUND, "Could not find account " + id, "account.notFound");
  }

  public AccountNotFoundException(String email) {
    super(HttpStatus.NOT_FOUND, "Could not find account " + email, "account.notFound");
  }
}
