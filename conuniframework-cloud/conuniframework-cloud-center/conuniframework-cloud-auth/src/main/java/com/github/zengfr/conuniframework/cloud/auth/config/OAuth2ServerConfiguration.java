package com.github.zengfr.conuniframework.cloud.auth.config;

import com.github.zengfr.conuniframework.cloud.auth.config.detailservice.UsernameUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfiguration extends AuthorizationServerConfigurerAdapter {

  @Autowired private DataSource dataSource;
  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private UsernameUserDetailService usernameUserDetailService;

  @Bean
  public JwtTokenStore tokenStore() {
    return new JwtTokenStore(jwtAccessTokenConverter());
  }

  @Bean
  public ApprovalStore approvalStore() {
    return new JdbcApprovalStore(dataSource);
  }

  @Bean
  protected AuthorizationCodeServices authorizationCodeServices() {
    return new JdbcAuthorizationCodeServices(dataSource);
  }
  /* @Bean
  public UserAuthenticationConverter userAuthenticationConverter() {
    DefaultUserAuthenticationConverter defaultUserAuthenticationConverter = new DefaultUserAuthenticationConverter();
    defaultUserAuthenticationConverter.setUserDetailsService(usernameUserDetailService);
    return defaultUserAuthenticationConverter;
  }*/
  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();

    accessTokenConverter.setSigningKey("dev1");
    accessTokenConverter.setVerifierKey("dev1");

    return accessTokenConverter;
  }

  @Bean
  public TokenEnhancer tokenEnhancer() {
    return new CustomTokenEnhancer();
  }
  @Bean
  public ClientDetailsService clientDetails() {
    JdbcClientDetailsService s = new JdbcClientDetailsService(dataSource);
    s.setPasswordEncoder(passwordEncoder);
    return s;
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.approvalStore(approvalStore());
    endpoints.authorizationCodeServices(authorizationCodeServices());
    endpoints.setClientDetailsService(clientDetails());

    TokenEnhancerChain enhancerChain = new TokenEnhancerChain();

    List<TokenEnhancer> enhancerList = new ArrayList<>();
    enhancerList.add(tokenEnhancer());
    enhancerList.add(jwtAccessTokenConverter());

    enhancerChain.setTokenEnhancers(enhancerList);

    endpoints
        .tokenStore(tokenStore())
        .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
        .authenticationManager(authenticationManager)
        .userDetailsService(usernameUserDetailService)
        .tokenEnhancer(enhancerChain);

    DefaultTokenServices tokenServices = new DefaultTokenServices();
    tokenServices.setTokenStore(endpoints.getTokenStore());
    tokenServices.setSupportRefreshToken(true);
    tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
    tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
    tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30)); // 30天

    endpoints.tokenServices(tokenServices);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security
        .passwordEncoder(passwordEncoder)
        .tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()")
        .allowFormAuthenticationForClients();
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    String finalSecret = passwordEncoder.encode("12345678");
    ClientDetailsServiceBuilder builder = clients.inMemory();
    boolean add = true;
    build(builder, passwordEncoder, "authServer", add);
    build(builder, passwordEncoder, "client1", add);
    build(builder, passwordEncoder, "client2", add);
    build(builder, passwordEncoder, "client3", add);
    build(builder, passwordEncoder, "client4", add);
    build(builder, passwordEncoder, "client5", add);
    clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
  }

  private static ClientDetailsServiceBuilder build(
      ClientDetailsServiceBuilder builder,
      PasswordEncoder passwordEncoder,
      String clientId,
      boolean add) {
    if (add) {
      String finalSecret = passwordEncoder.encode("12345678");
      return builder
          .withClient(clientId)
          .authorizedGrantTypes(
              "authorization_code", "client_credentials", "password", "refresh_token")
          .scopes("r", "d", "c", "u", "all")
          .secret(finalSecret)
          .accessTokenValiditySeconds(36000)
          .autoApprove("all")
          .redirectUris("http://localhost:8080/login")
          .and();
    }
    return builder;
  }


}
