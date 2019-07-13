package com.github.zengfr.conuniframework.cloud.auth.controller;

import com.github.zengfr.conuniframework.cloud.auth.common.WxToken;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zengfr on 2020/9/22.
 */

    @Controller
    @RequestMapping("/login")
    @Slf4j
    public class WxLoginController {
        @Autowired
        private RestTemplate restTemplate;

        @GetMapping
        public void login(HttpServletRequest request, HttpServletResponse response) throws ParseException {
            //获取到code值
            String code = request.getParameter("code");
            //判断
            if (code == null) {
                throw new RuntimeException("用户禁止授权");
            }
            //获取到了code值，回调没有问题
            //定义地址
            String token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx7287a60bb700fd21&secret=1ef8755f92bebae8ad7bab432ba29cbf&code="
                    + code + "&grant_type=authorization_code";
            //发送请求
            String json = restTemplate.getForObject(token_url, String.class);
            Gson gson = new Gson();

            WxToken token = gson.fromJson(json, WxToken.class);
            //获取到接口调用凭证
            //获取个人信息
            String user_url = "https://api.weixin.qq.com/sns/userinfo?access_token="+token.getAccess_token()+"&openid="+token.getOpenid();
            String jsonStr1 = restTemplate.getForObject(user_url, String.class);
            JSONObject jsonStr = (JSONObject) new JSONParser().parse(jsonStr1);

 //用户名
            String nickname = (String) jsonStr.get("nickname");
  //用户头像地址
            String headimgurl = (String) jsonStr.get("headimgurl");
            request.setAttribute("nickname",nickname);
            request.setAttribute("headimgurl",headimgurl);
            log.info("微信返回的用户对象:jsonStr1:{}",jsonStr);
            try {
                request.getRequestDispatcher("/main.html").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("微信扫描登陆异常");
            }
        }
    }