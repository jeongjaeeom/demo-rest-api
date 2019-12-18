package io.jeongjaeeom.demo.api.accounts.domain;

import com.google.common.collect.Sets;
import com.trabricks.data.jpa.domain.BaseEntity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author eomjeongjae
 * @since 2019/12/18
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
public class Account extends BaseEntity {

  @Default
  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<AccountRole> accountRoles = Sets.newHashSet();

  @Column(nullable = false, unique = true)
  private String username;

  @Setter
  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String name;

  public void addRole(AccountRole accountRole) {
    accountRole.setAccount(this);
  }

}
