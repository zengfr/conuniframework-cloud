package com.github.zengfr.conuniframework.cloud.auth.config;

import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/** Created by zengfr on 2020/6/9. */
public class CustomTokenGranter extends AbstractTokenGranter {
  protected CustomTokenGranter(
      AuthorizationServerTokenServices tokenServices,
      ClientDetailsService clientDetailsService,
      OAuth2RequestFactory requestFactory,
      String grantType) {
    super(tokenServices, clientDetailsService, requestFactory, grantType);
  }

  @Override
  protected OAuth2Authentication getOAuth2Authentication(
      ClientDetails client, TokenRequest tokenRequest) {
    OAuth2Authentication authentication = super.getOAuth2Authentication(client, tokenRequest);
    Map<String, String> parameters =
        new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
    authentication.setDetails(parameters);
    return authentication;
  }
}
