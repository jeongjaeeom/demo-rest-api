package io.jeongjaeeom.demo.api.accounts.exception;

import com.trabricks.web.common.HttpStatusException;
import org.springframework.http.HttpStatus;

/**
 * @author eomjeongjae
 * @since 2019-08-12
 */
public class SocialEmailNotFoundException extends HttpStatusException {

  public SocialEmailNotFoundException(HttpStatus status, String msg) {
    super(status, msg);
  }

}
