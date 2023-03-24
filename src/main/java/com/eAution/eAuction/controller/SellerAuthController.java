package com.eAution.eAuction.controller;

import com.eAution.eAuction.Entity.Role;
import com.eAution.eAuction.Entity.Seller;
import com.eAution.eAuction.payLoad.SellerLoginDTO;
import com.eAution.eAuction.payLoad.SellerSignupDTo;
import com.eAution.eAuction.repositories.RoleRepository;
import com.eAution.eAuction.repositories.SellerRepository;
import com.eAution.eAuction.security.JWTAuthResponse;
import com.eAution.eAuction.security.JwtTokenProvider;
import jakarta.validation.Valid;
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
@RequestMapping("/api/auth/seller")
public class SellerAuthController {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;



    @PostMapping("/signup")
    public ResponseEntity<?> registerSeller(@Valid  @RequestBody final SellerSignupDTo sellerSignUpDto) {

        // add check for username exists in a DB
        if (sellerRepository.existsByEmail(sellerSignUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        Seller  seller = new Seller();
       // Seller  seller = mapToEntity(sellerSignUpDto);
        seller.setPassword(passwordEncoder.encode(sellerSignUpDto.getPassword()));

        seller.setFirstName(sellerSignUpDto.getFirstName());
        seller.setLastName(sellerSignUpDto.getLastName());
        seller.setEmail(sellerSignUpDto.getEmail());
        seller.setAddress(sellerSignUpDto.getAddress());
        seller.setCity(sellerSignUpDto.getCity());
        seller.setPin(sellerSignUpDto.getPin());
        seller.setPhone(sellerSignUpDto.getPhone());
        seller.setState(sellerSignUpDto.getState());
        seller.setPassword(passwordEncoder.encode(sellerSignUpDto.getPassword()));


        Role roles = roleRepository.findByName("ROLE_SELLER").get();
        seller.setRoles(Collections.singleton(roles));

        sellerRepository.save(seller);

        return new ResponseEntity<>("SELLER registered successfully", HttpStatus.OK);

    }
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@Valid @RequestBody final SellerLoginDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }



}

