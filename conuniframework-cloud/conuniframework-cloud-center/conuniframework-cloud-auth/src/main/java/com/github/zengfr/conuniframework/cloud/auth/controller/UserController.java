package com.github.zengfr.conuniframework.cloud.auth.controller;

import com.github.zengfr.conuniframework.cloud.auth.domain.OAuth2User;
import com.github.zengfr.conuniframework.cloud.auth.repository.OAuth2UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

@RestController
@RequestMapping("/auth/user")
public class UserController {
  @Autowired OAuth2UserRepository oAuth2UserRepository;

  @RequestMapping(value = "/me")
  public Principal me(Principal principal) {

    return principal;
  }
  @GetMapping(value = "/me/jwt")
  public Object jwtParser(Authentication authentication){
    authentication.getCredentials();
    OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)authentication.getDetails();
    String jwtToken = details.getTokenValue();
    Claims claims = Jwts.parser()
            .setSigningKey("dev1".getBytes(StandardCharsets.UTF_8))
            .parseClaimsJws(jwtToken)
            .getBody();
    return claims;
  }
  @RequestMapping("/logout")
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    new SecurityContextLogoutHandler().logout(request, null, null);
    try {
      response.sendRedirect(request.getHeader("referer"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping(value = "/reg", method = RequestMethod.POST)
  public OAuth2User createUser(
      @RequestParam("username") String username, @RequestParam("password") String password) {
    if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
      OAuth2User user = new OAuth2User();
      user.setUsername(username);
      user.setUsername(password);
      return oAuth2UserRepository.save(user);
    }

    return null;
  }

  @GetMapping(value = "/test")
  public Authentication test() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return auth;
  }
}
