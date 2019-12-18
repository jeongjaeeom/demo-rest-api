package io.jeongjaeeom.demo.api.accounts.domain;

import com.trabricks.data.jpa.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class Role extends BaseEntity {

  public enum Authority {
    ADMIN, USER
  }

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Authority authority;

}
