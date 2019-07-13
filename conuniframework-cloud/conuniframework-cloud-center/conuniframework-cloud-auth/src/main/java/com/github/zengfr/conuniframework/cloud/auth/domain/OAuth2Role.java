package com.github.zengfr.conuniframework.cloud.auth.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/** Created by zengfr on 2020/7/2. */
@Entity
@Getter
@Setter
@Table(name = "oauth_role")
public class OAuth2Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;
}
