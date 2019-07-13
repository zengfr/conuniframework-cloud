package com.github.zengfr.conuniframework.cloud.auth.config.detailservice;

import com.github.zengfr.conuniframework.cloud.auth.config.CustomUserDetail;
import com.github.zengfr.conuniframework.cloud.auth.domain.OAuth2User;
import com.github.zengfr.conuniframework.cloud.auth.domain.OAuth2Role;
import com.github.zengfr.conuniframework.cloud.auth.service.OAuth2UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/** Created by zengfr on 2020/6/8. */
public abstract class AbsUserDetailService implements UserDetailsService {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired protected OAuth2UserService oAuth2UserService;

  protected abstract Optional<OAuth2User> getUser(String var1);

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    // 调用FeignClient查询用户
    Optional<OAuth2User> userOpt = oAuth2UserService.getByUserName(username);
    if (userOpt == null || !userOpt.isPresent()) {
      logger.error("找不到该用户，用户名：" + username);
      throw new UsernameNotFoundException("找不到该用户，用户名：" + username);
    }
    OAuth2User user = userOpt.get();

    // 调用FeignClient查询角色
    Collection<OAuth2Role> roles = user.getAuthorities();
    if (roles == null || roles.isEmpty()) {
      logger.error("查询角色失败！");
      roles = new ArrayList<>();
    }

    // 获取用户权限列表
    List<GrantedAuthority> authorities = new ArrayList();
    roles.forEach(
        e -> {
          // 存储用户、角色信息到GrantedAuthority，并放到GrantedAuthority列表
          GrantedAuthority authority = new SimpleGrantedAuthority(e.getName());
          authorities.add(authority);
        });

    // 返回带有用户权限信息的User
    CustomUserDetail user2 =
        new CustomUserDetail(
            user.getUsername(),
            user.getPassword(),
            user.getEnabled() > 0,
            isActive(user.getStatus()),
            authorities,"",user.getId(),user.getMobile(),"");
    return user2;
  }

  private boolean isActive(int active) {
    return active >= 1 ? true : false;
  }

  public static void createUser(String name, String finalPassword) {
    UserDetails u = User.withUsername(name).password(finalPassword).authorities("USER").build();
  }
}
