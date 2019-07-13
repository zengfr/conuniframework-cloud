package com.github.zengfr.conuniframework.cloud.auth.config.provider;

import com.github.zengfr.conuniframework.cloud.auth.config.CustomUserDetail;
import com.github.zengfr.conuniframework.cloud.auth.config.token.SmsAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/** Created by zengfr on 2020/6/8. 手机验证码登陆 */
public class SmsAuthenticationProvider extends AbstractAuthenticationProvider {

  private UserDetailsService userDetailsService;

  @Override
  protected void additionalAuthenticationChecks(UserDetails var1, Authentication authentication)
      throws AuthenticationException {

    if (authentication.getCredentials() == null) {
      this.logger.debug("Authentication failed: no credentials provided");
      throw new BadCredentialsException(
          this.messages.getMessage(
              "PhoneAuthenticationProvider.badCredentials", "Bad credentials"));
    } else {
      String presentedPassword = authentication.getCredentials().toString();

      // 验证码验证，调用公共服务查询 key 为authentication.getPrincipal()的value， 并判断其与验证码是否匹配
      if (!"1000".equals(presentedPassword)) {
        this.logger.debug("Authentication failed: verifyCode does not match stored value");
        throw new BadCredentialsException(
            this.messages.getMessage(
                "PhoneAuthenticationProvider.badCredentials", "Bad verifyCode"));
      }
    }
  }

  @Override
  protected Authentication createSuccessAuthentication(
      String params, Object principal, Authentication authentication, UserDetails user) {
    CustomUserDetail userd = (CustomUserDetail) user;
    SmsAuthenticationToken result =
        new SmsAuthenticationToken(
            params, principal, authentication.getCredentials(), user.getAuthorities());
    result.setDetails(authentication.getDetails());
    return result;
  }

  @Override
  protected UserDetails retrieveUser(String phone, Authentication authentication)
      throws AuthenticationException {
    UserDetails loadedUser;
    try {
      loadedUser = this.getUserDetailsService().loadUserByUsername(phone);
    } catch (UsernameNotFoundException var6) {
      throw var6;
    } catch (Exception var7) {
      throw new InternalAuthenticationServiceException(var7.getMessage(), var7);
    }

    if (loadedUser == null) {
      throw new InternalAuthenticationServiceException(
          "UserDetailsService returned null, which is an interface contract violation");
    } else {
      return loadedUser;
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return SmsAuthenticationToken.class.isAssignableFrom(authentication);
  }

  public UserDetailsService getUserDetailsService() {
    return userDetailsService;
  }

  public void setUserDetailsService(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }
}
