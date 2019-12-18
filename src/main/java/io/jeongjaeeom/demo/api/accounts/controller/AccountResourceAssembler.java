package io.jeongjaeeom.demo.api.accounts.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import io.jeongjaeeom.demo.api.accounts.dto.AccountDto.Response;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

/**
 * @author eomjeongjae
 * @since 2019-08-23
 */
@Component
class AccountResourceAssembler implements ResourceAssembler<Response, Resource<Response>> {

  @Override
  public Resource<Response> toResource(Response account) {

    return new Resource<>(account,
        linkTo(AccountRestController.class).slash(account.getId()).withSelfRel(),
        linkTo(methodOn(AccountRestController.class)
            .getAccounts(null, null, null))
            .withRel("accounts")
    );
  }
}
