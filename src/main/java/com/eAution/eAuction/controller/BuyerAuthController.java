package com.eAution.eAuction.controller;

import com.eAution.eAuction.Entity.Buyer;
import com.eAution.eAuction.Entity.Role;
import com.eAution.eAuction.payLoad.BuyerLoginDTO;
import com.eAution.eAuction.payLoad.BuyerSignupDTO;
import com.eAution.eAuction.repositories.BuyerRepository;
import com.eAution.eAuction.repositories.RoleRepository;
import com.eAution.eAuction.security.JWTAuthResponse;
import com.eAution.eAuction.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
@RestController
@RequestMapping("/api/auth/buyer")
public class BuyerAuthController {
    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> registerBuyer(@RequestBody final BuyerSignupDTO buyerSignUpDto) {

        // add check for username exists in a DB
        if (buyerRepository.existsByEmail(buyerSignUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        Buyer buyer = new Buyer();
        buyer.setFirstName(buyerSignUpDto.getFirstName());
        buyer.setLastName(buyerSignUpDto.getLastName());
        buyer.setEmail(buyerSignUpDto.getEmail());
        buyer.setAddress(buyerSignUpDto.getAddress());
        buyer.setCity(buyerSignUpDto.getCity());
        buyer.setPin(buyerSignUpDto.getPin());
        buyer.setPhone(buyerSignUpDto.getPhone());
        buyer.setState(buyerSignUpDto.getState());
        buyer.setPassword(passwordEncoder.encode(buyerSignUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_BUYER").get();
        buyer.setRoles(Collections.singleton(roles));

        buyerRepository.save(buyer);

        return new ResponseEntity<>("BUYER registered successfully", HttpStatus.OK);

    }

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody final BuyerLoginDTO loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

}
