package com.github.zengfr.conuniframework.cloud.auth.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Created by zengfr on 2020/9/21. 登陆成功处理，移动端登陆成功后还需做绑定操作 */
public class CustomLoginAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
  private RequestCache requestCache = new HttpSessionRequestCache();

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    System.out.println("onAuthenticationSuccess");
    super.onAuthenticationSuccess(request, response, authentication);
  }
}
