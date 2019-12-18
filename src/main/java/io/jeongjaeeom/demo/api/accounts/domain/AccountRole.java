package io.jeongjaeeom.demo.api.accounts.domain;

import com.trabricks.data.jpa.domain.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author eomjeongjae
 * @since 2019/11/06
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"account_id", "role_id"})
})
public class AccountRole extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY)
  private Role role;

  public void setAccount(Account account) {
    this.account = account;
    this.account.getAccountRoles().add(this);
  }

}
