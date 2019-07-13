package com.github.zengfr.conuniframework.cloud.service.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import java.util.Arrays;

/** Created by zengfr on 2020/9/17. */
@Configuration
public class OAuth2ClientConfig {
  @Value("${security.oauth2.client.client-id}")
  private String clientId;

  @Value("${security.oauth2.client.client-secret}")
  private String secret;
  @Value("${security.oauth2.client.access-token-uri}")
  private String accessTokenUri;
  @Value("${security.oauth2.authorization.check-token-access}")
  private String checkTokenEndpointUrl;
  @Bean
  //@ConfigurationProperties(prefix = "security.oauth2.client")
  public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
    ClientCredentialsResourceDetails clientCredentialsResourceDetails= new ClientCredentialsResourceDetails();
    clientCredentialsResourceDetails.setClientId(clientId);
    clientCredentialsResourceDetails.setClientSecret(secret);
    clientCredentialsResourceDetails.setAccessTokenUri(accessTokenUri);
    clientCredentialsResourceDetails.setScope(Arrays.asList("all"));
    clientCredentialsResourceDetails.setGrantType("client_credentials");
    return clientCredentialsResourceDetails;
  }


}
