package com.eAution.eAuction.controller;

import com.eAution.eAuction.Entity.Buyer;
import com.eAution.eAuction.Entity.Role;
import com.eAution.eAuction.Entity.Seller;
import com.eAution.eAuction.payLoad.BuyerLoginDTO;
import com.eAution.eAuction.payLoad.BuyerSignupDTO;
import com.eAution.eAuction.repositories.BuyerRepository;
import com.eAution.eAuction.repositories.RoleRepository;
import com.eAution.eAuction.security.JWTAuthResponse;
import com.eAution.eAuction.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BuyerAuthControllerTest {
    @Mock
    private BuyerRepository buyerRepository;

    @Mock
    RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private BuyerAuthController buyerAuthController;

    private BuyerSignupDTO buyerSignupDTO;

    @Test
    public void registerBuyerTest(){
        buyerSignupDTO  = new BuyerSignupDTO();
        buyerSignupDTO.setFirstName("test1");
        buyerSignupDTO.setLastName("test1");
        buyerSignupDTO.setCity("Bkp");
        buyerSignupDTO.setAddress("xyz");
        buyerSignupDTO.setEmail("test1@gmail.com");
        buyerSignupDTO.setPhone("12345678");
        buyerSignupDTO.setState("wb");
        buyerSignupDTO.setPassword("password");

        when(buyerRepository.existsByEmail(buyerSignupDTO.getEmail())).thenReturn(false);
        Role role = new Role();
        role.setName("ROLE_BUYER");
        when(roleRepository.findByName(role.getName())).thenReturn(Optional.of(role));
        Buyer buyer = new Buyer();
        buyer.setId(1L);
        buyer.setFirstName(buyerSignupDTO.getFirstName());
        buyer.setLastName(buyerSignupDTO.getLastName());
        buyer.setEmail(buyerSignupDTO.getEmail());
        buyer.setPin(buyerSignupDTO.getPin());
        buyer.setState(buyerSignupDTO.getState());
        buyer.setPhone(buyerSignupDTO.getPhone());
        buyer.setCity(buyerSignupDTO.getCity());
        buyer.setAddress(buyerSignupDTO.getAddress());
        buyer.setPassword("hashed-password");
        buyer.setRoles(Collections.singleton(role));

        when(buyerRepository.save(Mockito.any(Buyer.class))).thenReturn(buyer);
        ResponseEntity<?> response = buyerAuthController.registerBuyer(buyerSignupDTO);
        assertEquals("BUYER registered successfully",response.getBody());
        verify(buyerRepository,times(1)).save(any(Buyer.class));
    }

    @Test
    public void authenticateUserTest(){
        BuyerLoginDTO buyerLoginDTO = new BuyerLoginDTO();
        buyerLoginDTO.setUsernameOrEmail("buyer1@gmail.com");
        buyerLoginDTO.setPassword("password");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn("test-token");
        ResponseEntity<JWTAuthResponse> response = buyerAuthController.authenticateUser(buyerLoginDTO);
        assertEquals("test-token",response.getBody().getAccessToken());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
