package com.github.zengfr.conuniframework.cloud.auth.config.token;


import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by zengfr on 2020/6/8.
 *
 * 二维码Token
 */
public class QrCodeAuthenticationToken extends CustomAuthenticationToken {
    public QrCodeAuthenticationToken(String params, Object principal, Object credentials) {
        super(  params,principal, credentials);
    }

    public QrCodeAuthenticationToken(String params, Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(  params,principal, credentials, authorities);
    }
}
