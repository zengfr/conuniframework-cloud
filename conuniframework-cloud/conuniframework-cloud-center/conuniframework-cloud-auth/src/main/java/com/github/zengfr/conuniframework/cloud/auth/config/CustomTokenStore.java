package com.github.zengfr.conuniframework.cloud.auth.config;

import com.github.zengfr.conuniframework.cloud.auth.domain.OAuth2AccessToken;

import com.github.zengfr.conuniframework.cloud.auth.repository.Oauth2AccessTokenRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/** */
@Service
public class CustomTokenStore implements TokenStore {

  private static final Log LOG = LogFactory.getLog(CustomTokenStore.class);
  private AuthenticationKeyGenerator authenticationKeyGenerator =
      new DefaultAuthenticationKeyGenerator();

  @Autowired private Oauth2AccessTokenRepository oauth2AccessTokenRepository;

  @Override
  public OAuth2Authentication readAuthentication(
      org.springframework.security.oauth2.common.OAuth2AccessToken token) {
    OAuth2Authentication authentication = null;

    try {
      Optional<OAuth2AccessToken> at = oauth2AccessTokenRepository.findByTokenId(token.getValue());
      authentication = SerializationUtils.deserialize(at.get().getAuthentication());

    } catch (EmptyResultDataAccessException e) {
      if (LOG.isInfoEnabled()) {
        LOG.info("Failed to find access token for token " + token);
      }
    }

    return authentication;
  }

  @Override
  public OAuth2Authentication readAuthentication(String token) {
    OAuth2Authentication authentication = null;

    try {
      Optional<OAuth2AccessToken> at = oauth2AccessTokenRepository.findByTokenId(token);
      authentication = SerializationUtils.deserialize(at.get().getAuthentication());

    } catch (EmptyResultDataAccessException e) {
      if (LOG.isInfoEnabled()) {
        LOG.info("Failed to find access token for token " + token);
      }
    }

    return authentication;
  }

  @Override
  public void storeAccessToken(
      org.springframework.security.oauth2.common.OAuth2AccessToken token,
      OAuth2Authentication authentication) {
    String refreshToken = null;
    if (token.getRefreshToken() != null) {
      refreshToken = token.getRefreshToken().getValue();
    }
    OAuth2AccessToken at = new OAuth2AccessToken();
    at.setTokenId(token.getValue());
    at.setToken(SerializationUtils.serialize(token));
    at.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
    at.setAuthentication(SerializationUtils.serialize(authentication));
    at.setRefreshToken(refreshToken);

    // 存储
    oauth2AccessTokenRepository.save(at);
  }

  @Override
  public org.springframework.security.oauth2.common.OAuth2AccessToken readAccessToken(
      String tokenValue) {
    org.springframework.security.oauth2.common.OAuth2AccessToken accessToken = null;

    try {
      Optional<OAuth2AccessToken> token = oauth2AccessTokenRepository.findByTokenId(tokenValue);
      if (token.isPresent()) {
        accessToken = SerializationUtils.deserialize(token.get().getToken());
      }
    } catch (EmptyResultDataAccessException e) {
      if (LOG.isInfoEnabled()) {
        LOG.info("Failed to find access token for token " + tokenValue);
      }
    }

    return accessToken;
  }

  @Override
  public void removeAccessToken(
      org.springframework.security.oauth2.common.OAuth2AccessToken token) {
    // oauth2AccessTokenRepository.delete(token);
  }

  @Override
  public void storeRefreshToken(
      OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {}

  @Override
  public OAuth2RefreshToken readRefreshToken(String tokenValue) {
    return null;
  }

  @Override
  public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
    return null;
  }

  @Override
  public void removeRefreshToken(OAuth2RefreshToken token) {}

  @Override
  public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {}

  @Override
  public org.springframework.security.oauth2.common.OAuth2AccessToken getAccessToken(
      OAuth2Authentication authentication) {
    org.springframework.security.oauth2.common.OAuth2AccessToken accessToken = null;

    try {
      String auth = authenticationKeyGenerator.extractKey(authentication);
      Optional<OAuth2AccessToken> at = oauth2AccessTokenRepository.findByAuthenticationId(auth);
      if (null == at || !at.isPresent()) {
        return null;
      } else {
        accessToken = SerializationUtils.deserialize(at.get().getToken());
      }

    } catch (EmptyResultDataAccessException e) {
      if (LOG.isInfoEnabled()) {
        LOG.debug("Failed to find access token for authentication " + authentication);
      }
    }

    return accessToken;
  }

  @Override
  public Collection<org.springframework.security.oauth2.common.OAuth2AccessToken>
      findTokensByClientIdAndUserName(String clientId, String userName) {
    return null;
  }

  @Override
  public Collection<org.springframework.security.oauth2.common.OAuth2AccessToken>
      findTokensByClientId(String clientId) {
    return null;
  }
}
