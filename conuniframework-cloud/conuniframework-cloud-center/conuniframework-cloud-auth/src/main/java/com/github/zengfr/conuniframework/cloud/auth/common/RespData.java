package com.github.zengfr.conuniframework.cloud.auth.common;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/** Created by zengfr on 2020/7/2. */
@Data
public class RespData<T> {

  /** 返回码 */
  private Integer code;

  /** 返回描述 */
  private String message;

  private Long total;

  private List<T> items= Collections.emptyList();
}
