package com.example.ecommerce_java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce_java.dtos.AuthenticationDTO;
import com.example.ecommerce_java.dtos.LoginResponseDTO;
import com.example.ecommerce_java.dtos.RegisterDTO;
import com.example.ecommerce_java.models.User;
import com.example.ecommerce_java.models.UserPrincipal;
import com.example.ecommerce_java.repositories.UserRepository;
import com.example.ecommerce_java.security.TokenService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login( @RequestBody AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        try {
            var auth = this.authenticationManager.authenticate(usernamePassword);
            if (auth.getPrincipal() instanceof UserPrincipal) {
                var userPrincipal = (UserPrincipal) auth.getPrincipal();
                var token = tokenService.generateToken(userPrincipal);
                return new ResponseEntity<>(new LoginResponseDTO(token), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO data) {
        if (this.userRepository.findByLogin(data.login()) != null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.name(), data.login(), encryptedPassword);
        this.userRepository.save(newUser);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
