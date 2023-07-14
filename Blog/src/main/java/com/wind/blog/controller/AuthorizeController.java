package com.wind.blog.controller;

import com.wind.blog.entity.RestBean;
import com.wind.blog.service.AuthorizeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/7/14  18:07
 */
@Validated
@RestController
@RequestMapping("/auth")
public class AuthorizeController {
    private final String EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

    @Resource
    AuthorizeService service;
    @PostMapping("/valid-email")
    public RestBean<String>  validateEmail(@RequestParam("email") String email,
                                           HttpSession session){
        if (service.sendValidateEmail(email,session.getId()))
            return RestBean.success("邮件发送成功,请注意查收");
        else
            return RestBean.failure(400,"邮件发送失败,请重新尝试");
    }


}
