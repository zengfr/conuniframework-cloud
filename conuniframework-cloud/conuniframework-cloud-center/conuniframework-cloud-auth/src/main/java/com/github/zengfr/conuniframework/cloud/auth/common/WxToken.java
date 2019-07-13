package com.github.zengfr.conuniframework.cloud.auth.common;

import lombok.Data;

import java.io.Serializable;

/** Created by zengfr on 2020/9/22. */
@Data
public class WxToken implements Serializable {

  private String access_token;
  private String expires_in;
  private String refresh_token;
  private String openid;
  private String scope;
  private String unionid;
}
