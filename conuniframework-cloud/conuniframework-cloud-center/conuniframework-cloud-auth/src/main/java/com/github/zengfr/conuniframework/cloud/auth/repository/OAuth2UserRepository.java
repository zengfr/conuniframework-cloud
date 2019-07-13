package com.github.zengfr.conuniframework.cloud.auth.repository;

import com.github.zengfr.conuniframework.cloud.auth.domain.OAuth2User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by zengfr on 2020/6/9.
 */
@Repository
public interface OAuth2UserRepository extends JpaRepository<OAuth2User,Long>,
        JpaSpecificationExecutor<OAuth2User>
{
    Optional<OAuth2User> findByUsername(String userName);
}
