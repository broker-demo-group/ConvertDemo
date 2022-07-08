package com.brokerdemo.brokerconvertdemoproject.controller;

import com.brokerdemo.brokerconvertdemoproject.cache.LocalCache;
import com.brokerdemo.brokerconvertdemoproject.dao.UserRepository;
import com.brokerdemo.brokerconvertdemoproject.entity.LoginRequest;
import com.brokerdemo.brokerconvertdemoproject.entity.User;
import com.brokerdemo.brokerconvertdemoproject.response.Result;
import com.brokerdemo.brokerconvertdemoproject.service.NewUserCreator;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class Register {
    @Autowired
    private NewUserCreator newUserCreator;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LocalCache cache;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public Map<String, Object> register(@RequestParam String userName, @RequestParam String passWord, @RequestParam String email, @RequestParam String firstName, @RequestParam String lastName) {
        User user = new User(userName, passWord, new Date(), false, firstName, lastName, email);
        HashMap<String, Object> resultMap = new HashMap<>();
        if (newUserCreator.createCommonUser(user)) {
            resultMap.put("status", "success");
            return resultMap;
        }
        resultMap.put("status", "error");
        return resultMap;
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        User user = userRepository.findUserByUserName(loginRequest.getUserName());
        if (Objects.isNull(user)) {
            return Result.error(100, "该账号未注册");
        }

        if (!Objects.equals(user.getPassWord(), loginRequest.getPassWord())) {
            return Result.error(101, "密码错误");
        }

        cache.set(loginRequest.getUserName(), new Gson().toJson(user));
        response.setHeader("token", loginRequest.getUserName());

        return Result.success();

    }
}
