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
@Table(name = "oauth_userrole")
public class OAuth2UserRole implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private Long userid;
  @Column private Long roleid;
}
