package com.github.zengfr.conuniframework.cloud.auth.config.token;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


/**
 * Created by zengfr on 2020/6/8.
 * 手机验证码token
 */
public class SmsAuthenticationToken extends CustomAuthenticationToken {

    public SmsAuthenticationToken(String params, Object principal, Object credentials) {
        super(  params,principal, credentials);
    }

    public SmsAuthenticationToken(String params, Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(  params,principal, credentials, authorities);
    }

}
