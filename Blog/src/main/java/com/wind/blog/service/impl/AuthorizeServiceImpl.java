package com.wind.blog.service.impl;

import com.wind.blog.entity.Account;
import com.wind.blog.mapper.UserMapper;
import com.wind.blog.service.AuthorizeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/7/14  17:01
 */
@Slf4j
@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    @Value("${spring.mail.username}")
    String from;

    @Resource
    UserMapper mapper;

    @Resource
    MailSender mailSender;

    @Resource
    StringRedisTemplate template;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username == null){
            throw new UsernameNotFoundException("用户名不能为空");
        }
        Account account = mapper.findAccountByNameOrEmail(username);
        if (account == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("user")
                .build();
    }

    /**
     * 1. 先生成对应的验证码
     * 2. 把邮箱和对应的验证码直接放入到Redis里面(过期时间三分钟)
     * 3. 发送验证码到指定邮箱
     * 4. 如果发送失败，把redis里面刚刚插入的键值删除
     * 5. 用户在注册时，再从redis里面取出对应键值对，查看验证码是否一致
     */
    @Override
    public boolean sendValidateEmail(String email, String sessionId) {
        String key = "email:" + sessionId + ":" + email;
        if (Boolean.TRUE.equals(template.hasKey(key))) {
            Long expire = Optional.ofNullable(template.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
            if (expire >120){
                return false;
            }
        }
        Random random = new Random();
        int code = random.nextInt(899999) + 100000;
        log.info("code: " + code);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("您的验证邮件");
        message.setText("验证码是: " + code);
        try {
            mailSender.send(message);

            template.opsForValue().set(key, String.valueOf(code),3, TimeUnit.MINUTES);
            return true;
        } catch (MailException e){
            e.printStackTrace();
            return false;
        }


    }
}











