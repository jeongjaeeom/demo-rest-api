package io.jeongjaeeom.demo.api.accounts.controller;

import io.jeongjaeeom.demo.api.accounts.dto.AccountDto;
import io.jeongjaeeom.demo.api.accounts.dto.AccountDto.Response;
import io.jeongjaeeom.demo.api.accounts.service.AccountService;
import io.jeongjaeeom.demo.api.commons.ApiPageable;
import io.swagger.annotations.ApiParam;
import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eomjeongjae
 * @since 2019/12/18
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/accounts", produces = MediaTypes.HAL_JSON_VALUE)
public class AccountRestController {

  private final AccountService accountService;
  private final AccountResourceAssembler accountResourceAssembler;

  @PostMapping
  ResponseEntity<Resource<Response>> createAccount(
      @ApiParam(name = "AccountDto.Create", value = "Create an account", required = true) @RequestBody @Valid AccountDto.Create dto)
      throws URISyntaxException {
    Response savedAccount = accountService.createAccount(dto);
    Resource<Response> resource = accountResourceAssembler.toResource(savedAccount);

    return ResponseEntity
        .created(new URI(resource.getLink(Link.REL_SELF).getHref()))
        .body(resource);
  }

  @GetMapping
  @ApiPageable
  Resources<Resource<Response>> getAccounts(
      AccountDto.Search search, @NonNull Pageable pageable,
      PagedResourcesAssembler<Response> pagedResourcesAssembler) {
    Page<Response> page = accountService.getAccounts(search, pageable);
    return pagedResourcesAssembler.toResource(page, accountResourceAssembler);
  }

  @GetMapping("/{id}")
  Resource<AccountDto.Response> getAccount(
      @ApiParam(required = true, example = "1") @PathVariable final Long id) {
    return accountResourceAssembler.toResource(accountService.getAccount(id));
  }

  @PutMapping("/{id}")
  ResponseEntity updateAccount(
      @ApiParam(required = true, example = "1") @PathVariable final Long id,
      @RequestBody @Valid AccountDto.Update dto, @AuthenticationPrincipal String username)
      throws URISyntaxException {
    Response savedAccount = accountService.updateAccount(id, dto, username);
    Resource<Response> resource = accountResourceAssembler.toResource(savedAccount);

    return ResponseEntity
        .created(new URI(resource.getLink(Link.REL_SELF).getHref()))
        .body(resource);
  }

  @DeleteMapping("/{id}")
  ResponseEntity<?> deleteAccount(
      @ApiParam(required = true, example = "1") @PathVariable final Long id,
      @AuthenticationPrincipal String username) {
    accountService.deleteAccount(id, username);
    return ResponseEntity
        .noContent()
        .build();
  }

}
