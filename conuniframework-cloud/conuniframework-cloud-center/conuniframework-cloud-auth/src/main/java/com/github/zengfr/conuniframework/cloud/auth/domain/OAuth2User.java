package com.github.zengfr.conuniframework.cloud.auth.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/** Created by zengfr on 2020/6/8. */
@Entity
@Getter
@Setter
@Table(name = "oauth_user")
public class OAuth2User implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "mobile", unique = true)
  private String mobile;

  @Column(name = "status")
  private Byte status;

  @Column(name = "enabled")
  private Byte enabled;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(
      name = "oauth_userrole",
      joinColumns = @JoinColumn(name = "userid", referencedColumnName = "id"),
      inverseJoinColumns = {@JoinColumn(name = "roleid", referencedColumnName = "id")})
  private List<OAuth2Role> authorities;
}
