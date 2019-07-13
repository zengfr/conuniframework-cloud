package com.github.zengfr.conuniframework.cloud.auth.config.detailservice;

import com.github.zengfr.conuniframework.cloud.auth.domain.OAuth2User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** Created by zengfr on 2020/6/8. */
@Service
public class UsernameUserDetailService extends AbsUserDetailService {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  protected Optional<OAuth2User> getUser(String name) {
    // 手机验证码调用FeignClient根据电话号码查询用户
    Optional<OAuth2User> baseUser = oAuth2UserService.getByUserName(name);

    return baseUser;
  }
}
