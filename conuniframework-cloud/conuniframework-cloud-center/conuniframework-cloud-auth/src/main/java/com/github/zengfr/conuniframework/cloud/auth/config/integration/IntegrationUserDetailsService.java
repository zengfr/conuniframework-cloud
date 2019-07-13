package com.github.zengfr.conuniframework.cloud.auth.config.integration;
import com.github.zengfr.conuniframework.cloud.auth.config.integration.authenticator.IntegrationAuthenticator;
import com.github.zengfr.conuniframework.cloud.auth.domain.OAuth2User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 集成认证-用户细节服务
 */
@Service
public class IntegrationUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private List<IntegrationAuthenticator> authenticators;

    @Autowired(required = false)
    public void setIntegrationAuthenticators(List<IntegrationAuthenticator> authenticators) {
        this.authenticators = authenticators;
    }

    @Override
    public UserDetails loadUserByUsername(String str) throws UsernameNotFoundException {
        IntegrationAuthenticationEntity entity = IntegrationAuthenticationContext.get();
        if (entity == null){
            entity = new IntegrationAuthenticationEntity();
        }
        OAuth2User pojo = this.authenticate(entity);
        if (pojo == null){
            throw new OAuth2Exception("此账号不存在！");
        }
        User user = new User(pojo.getUsername(),passwordEncoder.encode(entity.getAuthParameter("password")), AuthorityUtils.commaSeparatedStringToAuthorityList("ROOT_USER"));
        return user;
    }

    private OAuth2User authenticate(IntegrationAuthenticationEntity entity) {
        if (this.authenticators != null) {
            for (IntegrationAuthenticator authenticator : authenticators) {
                if (authenticator.support(entity)) {
                    return authenticator.authenticate(entity);
                }
            }
        }
        throw new OAuth2Exception("无效的auth_type参数值！");
    }
}