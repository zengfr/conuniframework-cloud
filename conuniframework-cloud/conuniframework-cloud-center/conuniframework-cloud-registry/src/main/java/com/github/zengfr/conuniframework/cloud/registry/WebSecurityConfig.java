package com.github.zengfr.conuniframework.cloud.registry;

/** Created by zengfr on 2020/9/9. */
/*@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests() // 对请求进行鉴权
        .antMatchers("/login") // 登录页面不鉴权
        .permitAll();
    http.formLogin()
        .loginPage("/login") // 登录页面
        .failureUrl("/login?error") // 鉴权失败的页面
        .permitAll();
    http.csrf().disable();
    super.configure(http);
  }
}*/
