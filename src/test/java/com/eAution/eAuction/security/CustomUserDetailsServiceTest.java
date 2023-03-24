package com.eAution.eAuction.security;

import com.eAution.eAuction.Entity.Buyer;
import com.eAution.eAuction.Entity.Role;
import com.eAution.eAuction.Entity.Seller;
import com.eAution.eAuction.repositories.BuyerRepository;
import com.eAution.eAuction.repositories.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {
    @Mock
    private SellerRepository sellerRepository;
    @Mock
    private BuyerRepository buyerRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;


    @Test
    public void testLoadUserByUsernameForSeller() {
        Seller seller = new Seller();
        seller.setFirstName("test1");
        seller.setLastName("test1");
        seller.setCity("Bkp");
        seller.setAddress("xyz");
        seller.setEmail("test1@gmail.com");
        seller.setPhone("12345678");
        seller.setState("wb");
        seller.setPassword("password");

        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_SELLER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        seller.setRoles(roles);
        Optional<Seller> userOptional = Optional.of(seller);
        // Mock the Actual Database call
        when(sellerRepository.findByEmail(seller.getEmail())).thenReturn(userOptional);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("test1@gmail.com");
        assertEquals("test1@gmail.com", userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsernameForBuyer() {
        Buyer buyer = new Buyer();
        buyer.setFirstName("test1");
        buyer.setLastName("test1");
        buyer.setCity("Bkp");
        buyer.setAddress("xyz");
        buyer.setEmail("test1@gmail.com");
        buyer.setPhone("12345678");
        buyer.setState("wb");
        buyer.setPassword("password");

        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_BUYER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        buyer.setRoles(roles);
        Optional<Buyer> userOptional = Optional.of(buyer);
        // Mock the Actual Database call
        when(buyerRepository.findByEmail(buyer.getEmail())).thenReturn(userOptional);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("test1@gmail.com");
        assertEquals("test1@gmail.com", userDetails.getUsername());
    }
}
