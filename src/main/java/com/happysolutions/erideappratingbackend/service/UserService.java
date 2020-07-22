package com.happysolutions.erideappratingbackend.service;

import com.happysolutions.erideappratingbackend.config.JwtTokenUtil;
import com.happysolutions.erideappratingbackend.entity.User;
import com.happysolutions.erideappratingbackend.enums.UserType;
import com.happysolutions.erideappratingbackend.model.JwtResponse;
import com.happysolutions.erideappratingbackend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder bcryptEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder bcryptEncoder,
            JwtTokenUtil jwtTokenUtil,
            JwtUserDetailsService jwtUserDetailsService) {
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    public ResponseEntity<Map> save(User user) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            userRepository.save(user);
            final UserDetails userDetails = jwtUserDetailsService
                    .loadUserByUsername(user.getEmail());
            final String token = jwtTokenUtil.generateToken(userDetails);
            Map mymap = Map.of("token", new JwtResponse(token).getToken(), "user", user);
            log.info("new registration with user: {}", user);
            return ResponseEntity.ok(mymap);
        } else {
            log.info("user:{} already exists", user);
            return ResponseEntity.ok().body(Map.of("status", "error", "message", "Email already exists"));
        }
    }

    public User getUserFromRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization").split(" ")[1];
        String email = jwtTokenUtil.getUsernameFromToken(token);
        return userRepository.findByEmail(email);
    }

    public boolean isDriver(User user) {
        return user.getUserType() == UserType.DRIVER;
    }

    public boolean isPassenger(User user) {
        return user.getUserType() == UserType.PASSENGER;
    }

    public User getUserFromEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
