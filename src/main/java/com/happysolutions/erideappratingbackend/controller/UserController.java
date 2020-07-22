package com.happysolutions.erideappratingbackend.controller;

import com.happysolutions.erideappratingbackend.config.JwtTokenUtil;
import com.happysolutions.erideappratingbackend.entity.User;
import com.happysolutions.erideappratingbackend.service.JwtUserDetailsService;
import com.happysolutions.erideappratingbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserController(
            UserService userService,
            JwtUserDetailsService jwtUserDetailsService,
            JwtTokenUtil jwtTokenUtil
    ) {
        this.userService = userService;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @PostMapping("/register")
    public ResponseEntity<Map> saveUser(@Valid @RequestBody User user) {
        return userService.save(user);
    }

}
