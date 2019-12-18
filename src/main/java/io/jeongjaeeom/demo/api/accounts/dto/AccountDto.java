package io.jeongjaeeom.demo.api.accounts.dto;

import com.trabricks.data.jpa.support.Restrictions;
import com.trabricks.data.jpa.support.SearchDto;
import io.jeongjaeeom.demo.api.accounts.domain.Account;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author eomjeongjae
 * @since 2019/12/18
 */
@Slf4j
public class AccountDto {

  @Setter
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  @ApiModel(value = "AccountDto.Create")
  public static class Create {

    @NotEmpty
    @ApiModelProperty(value = "유저ID", required = true)
    private String username;

    @NotEmpty
    @ApiModelProperty(value = "비밀번호", required = true)
    private String password;

    @NotEmpty
    @ApiModelProperty(value = "이름", example = "Queen", required = true)
    private String name;

  }

  @Setter
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  @ApiModel(value = "AccountDto.Update")
  public static class Update {

    @NotEmpty
    @ApiModelProperty(value = "이름", example = "Queen", required = true)
    private String name;

  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @ToString
  @ApiModel(value = "AccountDto.Response")
  public static class Response {

    private Long id;

    private String username;

    private String name;

    private LocalDateTime createdAt;
  }

  @Getter
  @Setter
  @ToString(callSuper = true)
  public static class Search extends SearchDto<Account> {

    @ApiModelProperty(value = "검색어")
    private String keyword;

    @Override
    protected Restrictions generateRestrictions() {
      final Restrictions restrictions = new Restrictions();

      if (StringUtils.isNotEmpty(this.keyword)) {
        final Restrictions restrictionsByKeyword = new Restrictions(Restrictions.Conn.OR);
        List<String> keywords = Arrays.asList(this.keyword.trim().split("\\s+"));
        keywords.stream()
            .forEach(keyword -> {
              log.debug("keyword: {}", keyword);
              restrictionsByKeyword.like("username", "%" + keyword + "%");
              restrictionsByKeyword.like("name", "%" + keyword + "%");
            });
        restrictions.addChild(restrictionsByKeyword);
      }

      return restrictions;
    }
  }

}
