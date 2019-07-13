package com.github.zengfr.conuniframework.cloud.auth.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 */
@Entity
@Getter
@Setter
@Table(name = "oauth_access_token")
public class OAuth2AccessToken implements Serializable {
    private static final long serialVersionUID = -4232065232755289541L;
    @Id
    private String tokenId;
    @Column(name="token")
    private byte[] token;
    @Column(name="authentication_id")
    private String authenticationId;
    @Column(name="authentication")
    private byte[] authentication;
    @Column(name="refresh_token")
    private String refreshToken;
}