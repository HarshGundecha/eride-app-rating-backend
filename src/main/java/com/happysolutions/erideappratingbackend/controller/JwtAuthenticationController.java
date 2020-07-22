package com.happysolutions.erideappratingbackend.controller;

import com.happysolutions.erideappratingbackend.config.JwtTokenUtil;
import com.happysolutions.erideappratingbackend.entity.User;
import com.happysolutions.erideappratingbackend.model.JwtRequest;
import com.happysolutions.erideappratingbackend.model.JwtResponse;
import com.happysolutions.erideappratingbackend.service.JwtUserDetailsService;
import com.happysolutions.erideappratingbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final UserService userService;

    @Autowired
    public JwtAuthenticationController(
            AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil,
            JwtUserDetailsService userDetailsService,
            UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);
//		System.out.println(userDetails.getUsername()+" is my name");
        User user = userService.getUserFromEmail(userDetails.getUsername());
        Map<String, Object> mymap = Map.of("token", new JwtResponse(token).getToken(), "user", user);
        return ResponseEntity.ok(mymap);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}