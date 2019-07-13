package com.github.zengfr.conuniframework.cloud.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/** Created by zengfr on 2020/9/22. */
@Configuration
public class OAuth2AuthManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {

  @Autowired private DataSource dataSource;
  @Autowired private PasswordEncoder passwordEncoder;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return   new BCryptPasswordEncoder();
  }

  @Override
  public void init(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery("select username,password,enabled from oauth_user WHERE username=?")
        .authoritiesByUsernameQuery(
            "select u.username,r.name as authority from oauth_user u "
                + " join oauth_userrole ur on u.id=ur.userid"
                + " join oauth_role r on r.id=ur.roleid"
                + " where u.username=?")
        .passwordEncoder(passwordEncoder);
    ;
    ;
  }

  public static void main(String[] args) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String password = "12345678";
    String pwd = passwordEncoder.encode(password);
    System.out.println("\r\npwd:" + pwd);
    boolean flag = passwordEncoder.matches(password, pwd);
    System.out.println(flag);
    boolean flag2 = passwordEncoder.matches(password, pwd);
    System.out.println(flag2);
  }
}
