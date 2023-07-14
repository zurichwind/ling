package com.wind.blog.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : 风间离
 * @Create 2023/7/13  12:58
 */

@Service
public interface AuthorizeService extends UserDetailsService {

    boolean sendValidateEmail(String email, String sessionId);

}
