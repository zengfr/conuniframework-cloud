package com.github.zengfr.conuniframework.cloud.auth.service;

import com.github.zengfr.conuniframework.cloud.auth.domain.OAuth2User;
import com.github.zengfr.conuniframework.cloud.auth.repository.OAuth2UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** Created by zengfr on 2020/7/2. */
@Service
public class OAuth2UserService {
  @Autowired OAuth2UserRepository oAuth2UserRepository;

  public OAuth2User save(String username, String password) {
    Optional<OAuth2User> userOpt = getByUserName(username);
    OAuth2User user = userOpt.isPresent() ? userOpt.get() : new OAuth2User();
    user.setUsername(username);
    user.setUsername(password);
    return oAuth2UserRepository.save(user);
  }

  public Optional<OAuth2User> getByUserName(String username) {
    OAuth2User user = new OAuth2User();
    user.setUsername(username);
    Example<OAuth2User> userExample = Example.of(user);
    return oAuth2UserRepository.findOne(userExample);
  }

  public Optional<OAuth2User> getByMobile(String mobile) {
    OAuth2User user = new OAuth2User();
    user.setMobile(mobile);
    Example<OAuth2User> userExample = Example.of(user);
    return oAuth2UserRepository.findOne(userExample);
  }
}
