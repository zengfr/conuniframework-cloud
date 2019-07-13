
package com.github.zengfr.conuniframework.cloud.auth.config.integration.authenticator;

import com.github.zengfr.conuniframework.cloud.auth.config.integration.IntegrationAuthenticationEntity;
import com.github.zengfr.conuniframework.cloud.auth.domain.OAuth2User;

/**
 * @Description: 集成认证-认证器接口
 */
public interface IntegrationAuthenticator {

    /**
     * 处理集成认证
     * @param entity    集成认证实体
     * @return 用户表实体
     */
    OAuth2User authenticate(IntegrationAuthenticationEntity entity);

    /**
     * 预处理
     * @param entity    集成认证实体
     */
    void prepare(IntegrationAuthenticationEntity entity);

    /**
     * 判断是否支持集成认证类型
     * @param entity    集成认证实体
     */
    boolean support(IntegrationAuthenticationEntity entity);

    /**
     * 认证结束后执行
     * @param entity    集成认证实体
     */
    void complete(IntegrationAuthenticationEntity entity);
}