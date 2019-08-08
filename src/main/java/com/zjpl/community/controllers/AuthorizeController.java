package com.zjpl.community.controllers;

import com.zjpl.community.dto.AccessTokenDTO;
import com.zjpl.community.dto.GithubUser;
import com.zjpl.community.mapper.UserMapper;
import com.zjpl.community.mode.User;
import com.zjpl.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {

//    @Value("github.client.id")
//    private String clientId;
//
//    @Value("github.client.secret")
//    private String clientSecret;
//
//    @Value("github.redirect.url")
//    private String redirectUrl;
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("155263eb0570d3145304");
        accessTokenDTO.setClient_secret("651128847e42c9ebd9d1a8ae40da41075c2b8e2a");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_url("http://localhost:9090/callback");
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {
            User user = new User();
            user.setName(githubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            //userMapper.insert(user);
            //登陆成功，写入session和cookie
            request.getSession().setAttribute("user", githubUser);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
}
