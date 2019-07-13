package com.github.zengfr.conuniframework.cloud.auth.repository;

import com.github.zengfr.conuniframework.cloud.auth.domain.OAuth2AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/** Created by zengfr on 2020/6/9. */
public interface Oauth2AccessTokenRepository
    extends JpaRepository<OAuth2AccessToken, Long>, JpaSpecificationExecutor<OAuth2AccessToken> {
 Optional<OAuth2AccessToken> findByAuthenticationId(String auth);

  Optional<OAuth2AccessToken> findByTokenId(String tokenValue);
}
