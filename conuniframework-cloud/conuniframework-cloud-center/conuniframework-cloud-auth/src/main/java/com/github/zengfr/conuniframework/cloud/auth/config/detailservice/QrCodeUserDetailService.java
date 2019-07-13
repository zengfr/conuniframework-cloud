package com.github.zengfr.conuniframework.cloud.auth.config.detailservice;

import com.github.zengfr.conuniframework.cloud.auth.domain.OAuth2User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** Created by zengfr on 2020/6/8. */
@Service
public class QrCodeUserDetailService extends AbsUserDetailService {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  protected Optional<OAuth2User> getUser(String qrCode) {
    return null;
  }
}
