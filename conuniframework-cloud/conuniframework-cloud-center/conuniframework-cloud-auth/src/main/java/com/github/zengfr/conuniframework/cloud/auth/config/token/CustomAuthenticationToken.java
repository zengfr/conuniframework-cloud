package com.github.zengfr.conuniframework.cloud.auth.config.token;



import org.springframework.security.authentication.AbstractAuthenticationToken;
        import org.springframework.security.core.GrantedAuthority;

        import java.util.Collection;

/**
 *
 *  * Created by zengfr on 2020/6/8.
 *
 * 自定义AbstractAuthenticationToken，
 */
public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 110L;
    protected final Object principal;
    protected Object credentials;
    protected String params;


    public CustomAuthenticationToken(String params,
    Object principal, Object credentials) {
        super(null);
        this.params=params;
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }


    public CustomAuthenticationToken(String params,Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.params=params;
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
    public String getParams() {
        return this.params;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if(isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
