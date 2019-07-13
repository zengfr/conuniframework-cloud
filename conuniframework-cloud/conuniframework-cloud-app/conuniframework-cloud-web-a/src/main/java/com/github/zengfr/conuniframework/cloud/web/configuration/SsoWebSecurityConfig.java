package com.github.zengfr.conuniframework.cloud.web.configuration;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableOAuth2Sso
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SsoWebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/**")
        .authorizeRequests()
        .antMatchers("/", "/*.ico", "/error", "/login**") // 不用改
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .logout()
        .logoutSuccessUrl("http://auth:5000/auth/user/logout");
    // .oauth2Login().successHandler(customAuthenticationSuccessHandler);
    http.csrf().disable();
    http.headers().frameOptions().sameOrigin();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/static/**", "/upload/**");
  }
}
