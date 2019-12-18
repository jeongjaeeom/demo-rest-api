package io.jeongjaeeom.demo.api.index;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import io.jeongjaeeom.demo.api.accounts.controller.AccountRestController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eomjeongjae
 * @since 2019-08-26
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api", produces = MediaTypes.HAL_JSON_VALUE)
public class IndexRestController {

  @GetMapping
  public ResourceSupport index() {
    ResourceSupport index = new ResourceSupport();
    index.add(linkTo(AccountRestController.class).withRel("accounts"));
    return index;
  }

}
