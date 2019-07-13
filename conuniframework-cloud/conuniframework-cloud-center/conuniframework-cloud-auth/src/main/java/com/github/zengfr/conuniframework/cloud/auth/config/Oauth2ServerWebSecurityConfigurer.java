package com.github.zengfr.conuniframework.cloud.auth.config;

import com.github.zengfr.conuniframework.cloud.auth.config.filter.QrCodeLoginAuthenticationFilter;
import com.github.zengfr.conuniframework.cloud.auth.config.filter.SmsLoginAuthenticationFilter;
import com.github.zengfr.conuniframework.cloud.auth.config.handler.CustomLoginAuthSuccessHandler;
import com.github.zengfr.conuniframework.cloud.auth.config.provider.SmsAuthenticationProvider;
import com.github.zengfr.conuniframework.cloud.auth.config.provider.QrCodeAuthenticationProvider;
import com.github.zengfr.conuniframework.cloud.auth.config.detailservice.SmsUserDetailService;
import com.github.zengfr.conuniframework.cloud.auth.config.detailservice.QrCodeUserDetailService;
import com.github.zengfr.conuniframework.cloud.auth.config.detailservice.UsernameUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@Order(1)
public class Oauth2ServerWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

  @Autowired private UsernameUserDetailService usernameUserDetailService;

  @Autowired private SmsUserDetailService phoneUserDetailService;

  @Autowired private QrCodeUserDetailService qrUserDetailService;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();//必须
  }
  /**
   * 用户验证
   *
   * @param auth
   */
  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(usernameUserDetailService);

    auth.authenticationProvider(daoAuthenticationProvider());
    auth.authenticationProvider(qrAuthenticationProvider());
    auth.authenticationProvider(phoneAuthenticationProvider());
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    // 设置userDetailsService
    provider.setUserDetailsService(usernameUserDetailService);
    // 禁止隐藏用户未找到异常
    provider.setHideUserNotFoundExceptions(false);
    // 使用BCrypt进行密码的hash
    provider.setPasswordEncoder(new BCryptPasswordEncoder(6));

    return provider;
  }

  @Bean
  public SmsAuthenticationProvider phoneAuthenticationProvider() {
    SmsAuthenticationProvider provider = new SmsAuthenticationProvider();
    // 设置userDetailsService
    provider.setUserDetailsService(phoneUserDetailService);
    // 禁止隐藏用户未找到异常
    provider.setHideUserNotFoundExceptions(false);
    return provider;
  }

  @Bean
  public QrCodeAuthenticationProvider qrAuthenticationProvider() {
    QrCodeAuthenticationProvider provider = new QrCodeAuthenticationProvider();
    // 设置userDetailsService
    provider.setUserDetailsService(qrUserDetailService);
    // 禁止隐藏用户未找到异常
    provider.setHideUserNotFoundExceptions(false);
    return provider;
  }
  /**
   * 手机验证码登陆过滤器
   *
   * @return
   */
  @Bean
  public SmsLoginAuthenticationFilter smsLoginAuthenticationFilter() {
    SmsLoginAuthenticationFilter filter = new SmsLoginAuthenticationFilter();
    try {
      filter.setAuthenticationManager(this.authenticationManagerBean());
    } catch (Exception e) {
      e.printStackTrace();
    }
    filter.setAuthenticationSuccessHandler(new CustomLoginAuthSuccessHandler());
    filter.setAuthenticationFailureHandler(
        new SimpleUrlAuthenticationFailureHandler("/login?error"));
    return filter;
  }

  @Bean
  public QrCodeLoginAuthenticationFilter qrCodeLoginAuthenticationFilter() {
    QrCodeLoginAuthenticationFilter filter = new QrCodeLoginAuthenticationFilter();
    try {
      filter.setAuthenticationManager(this.authenticationManagerBean());
    } catch (Exception e) {
      e.printStackTrace();
    }
    filter.setAuthenticationSuccessHandler(new CustomLoginAuthSuccessHandler());
    filter.setAuthenticationFailureHandler(
        new SimpleUrlAuthenticationFailureHandler("/login?error"));
    return filter;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http // .addFilterBefore(smsLoginAuthenticationFilter(),
         // UsernamePasswordAuthenticationFilter.class)
        // .addFilterBefore(
        //    qrCodeLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers("/", "/login**", "/oauth/**", "/oauth/authorize", "/oauth/logout")
        .permitAll()
        .antMatchers(HttpMethod.OPTIONS)
        .permitAll()
        .anyRequest()
        .authenticated()

        .and()
        .formLogin()
        .and()
        .logout()
        .logoutSuccessUrl("/login?logout")
        .and()
        .exceptionHandling()
        .and()
        .httpBasic()
        .and()
        .csrf()
        .disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
  }
}
