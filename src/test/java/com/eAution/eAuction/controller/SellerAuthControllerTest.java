package com.eAution.eAuction.controller;

import com.eAution.eAuction.Entity.Role;
import com.eAution.eAuction.Entity.Seller;
import com.eAution.eAuction.payLoad.SellerLoginDTO;
import com.eAution.eAuction.payLoad.SellerSignupDTo;
import com.eAution.eAuction.repositories.RoleRepository;
import com.eAution.eAuction.repositories.SellerRepository;
import com.eAution.eAuction.security.JWTAuthResponse;
import com.eAution.eAuction.security.JwtTokenProvider;
import com.mysql.cj.x.protobuf.MysqlxCrud;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SellerAuthControllerTest {

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private SellerAuthController sellerAuthController;

    private SellerSignupDTo sellerSignupDTo1;

    @Test
    public void registerSellerTest(){
        sellerSignupDTo1 = new SellerSignupDTo();
        sellerSignupDTo1.setFirstName("test1");
        sellerSignupDTo1.setLastName("test1");
        sellerSignupDTo1.setCity("Bkp");
        sellerSignupDTo1.setAddress("xyz");
        sellerSignupDTo1.setEmail("test1@gmail.com");
        sellerSignupDTo1.setPhone("12345678");
        sellerSignupDTo1.setState("wb");
        sellerSignupDTo1.setPassword("password");

        when(sellerRepository.existsByEmail(sellerSignupDTo1.getEmail())).thenReturn(false);
        Role role = new Role();
        role.setName("ROLE_SELLER");
        when(roleRepository.findByName(role.getName())).thenReturn(Optional.of(role));

        Seller seller = new Seller();
        seller.setId(1L);
        seller.setFirstName(sellerSignupDTo1.getFirstName());
        seller.setLastName(sellerSignupDTo1.getLastName());
        seller.setEmail(sellerSignupDTo1.getEmail());
        seller.setPin(sellerSignupDTo1.getPin());
        seller.setState(sellerSignupDTo1.getState());
        seller.setPhone(sellerSignupDTo1.getPhone());
        seller.setCity(sellerSignupDTo1.getCity());
        seller.setAddress(sellerSignupDTo1.getAddress());
        seller.setPassword("hashed-password");
        seller.setRoles(Collections.singleton(role));

        when(sellerRepository.save(Mockito.any(Seller.class))).thenReturn(seller);
        ResponseEntity<?> response = sellerAuthController.registerSeller(sellerSignupDTo1);
        assertEquals("SELLER registered successfully",response.getBody());
        verify(sellerRepository,times(1)).save(any(Seller.class));
    }


    @Test
    void authenticateUserTest(){
        SellerLoginDTO sellerLoginDTO = new SellerLoginDTO();
        sellerLoginDTO.setUsernameOrEmail("seller1@gmail.com");
        sellerLoginDTO.setPassword("password");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn("test-token");
        ResponseEntity<JWTAuthResponse> response = sellerAuthController.authenticateUser(sellerLoginDTO);
        assertEquals("test-token",response.getBody().getAccessToken());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
