package io.jeongjaeeom.demo.api.accounts.exception;

import com.trabricks.web.common.HttpStatusException;
import org.springframework.http.HttpStatus;

/**
 * @author eomjeongjae
 * @since 2019/10/14
 */
public class UserDuplicatedException extends HttpStatusException {

  public UserDuplicatedException(String username) {
    super(HttpStatus.BAD_REQUEST, "User is a duplicated " + username);
  }
}
