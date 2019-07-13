package com.github.zengfr.conuniframework.cloud.auth.config;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

/** Created by zengfr on 2020/6/8. */
@Data
public class CustomUserDetail extends User implements Serializable {
  private static final long serialVersionUID = -1117421834288947992L;
  private Long userId;
  private String mobile;
  private String params;
  private String permissions;

  public CustomUserDetail() {
    this("", "", false, false, null);
  }

  public CustomUserDetail(
      String username,
      String password,
      boolean enabled,
      boolean accountNonLocked,
      Collection<? extends GrantedAuthority> authorities) {
    this(username, password, enabled, accountNonLocked, authorities, null, 0l, null, null);
  }

  public CustomUserDetail(
      String username,
      String password,
      boolean enabled,
      boolean accountNonLocked,
      Collection<? extends GrantedAuthority> authorities,
      String permissions,
      Long userId,
      String mobile,
      String params) {
    super(username, password, enabled, true, true, accountNonLocked, authorities);
    this.permissions = permissions;
    this.userId = userId;
    this.mobile = mobile;
    this.params = params;
  }
}
