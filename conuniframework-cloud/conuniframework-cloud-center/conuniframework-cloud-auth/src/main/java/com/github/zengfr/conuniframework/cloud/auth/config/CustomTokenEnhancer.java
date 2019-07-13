package com.github.zengfr.conuniframework.cloud.auth.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import java.util.HashMap;
import java.util.Map;
/** token生成携带的信息 */
public class CustomTokenEnhancer implements TokenEnhancer {

  @Override
  public OAuth2AccessToken enhance(
      OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    final Map<String, Object> additionalInfo = new HashMap<>();
    additionalInfo.put("client_id", authentication.getOAuth2Request().getClientId());
    if (authentication.getUserAuthentication() != null) {

      CustomUserDetail user =
          (CustomUserDetail) authentication.getUserAuthentication().getPrincipal();
      additionalInfo.put("enhance", "");
      if (user != null) {
        additionalInfo.put("uid", user.getUserId());
        additionalInfo.put("username", user.getUsername());
        additionalInfo.put("mobile", "" + user.getMobile());
        additionalInfo.put("permissions", "" + user.getPermissions());
        additionalInfo.put("params", "" + user.getParams());
      } //
    }
    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
    return accessToken;
  }
}
