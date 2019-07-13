package com.github.zengfr.conuniframework.cloud.service.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
  @Value("${security.oauth2.client.client-id}")
  private String clientId;

  @Value("${security.oauth2.client.client-secret}")
  private String secret;

  @Value("${security.oauth2.authorization.check-token-access}")
  private String checkTokenEndpointUrl;
  //@Bean
  public RemoteTokenServices tokenServices() {
    final RemoteTokenServices tokenService = new RemoteTokenServices();
    tokenService.setCheckTokenEndpointUrl(checkTokenEndpointUrl);
    tokenService.setClientId(clientId);
    tokenService.setClientSecret(secret);
    return tokenService;
  }
  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().anyRequest().authenticated();
  }
  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.tokenServices(tokenServices());
  }
}
