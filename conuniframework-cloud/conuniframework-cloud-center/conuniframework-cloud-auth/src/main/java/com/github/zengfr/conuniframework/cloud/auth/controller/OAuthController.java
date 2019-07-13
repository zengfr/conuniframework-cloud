package com.github.zengfr.conuniframework.cloud.auth.controller;

import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/** Created by zengfr on 2020/9/9. */
@Controller
@RequestMapping("/oauth")
@SessionAttributes("authorizationRequest")
public class OAuthController {

  @RequestMapping("/confirm_access")
  public ModelAndView getAccessConfirmation(Map<String, Object> model) {

    AuthorizationRequest authorizationRequest =
        (AuthorizationRequest) model.get("authorizationRequest");

    ModelAndView view = new ModelAndView("base-grant");
    view.addObject("clientId", authorizationRequest.getClientId());
    view.addObject("scope", authorizationRequest.getScope().toArray()[0]);

    return view;
  }
}
