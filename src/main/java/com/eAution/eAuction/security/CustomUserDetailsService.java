package com.eAution.eAuction.security;


import com.eAution.eAuction.Entity.Buyer;
import com.eAution.eAuction.Entity.Role;
import com.eAution.eAuction.Entity.Seller;
import com.eAution.eAuction.repositories.BuyerRepository;
import com.eAution.eAuction.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    //private JpaRepository userRepository;



//    public CustomUserDetailsService(JpaRepository userRepository) {
//      //  userRepository instanceof SellerRepository ? ((SellerRepository) userRepository) : null;
//        this.userRepository = userRepository;
//    }

    public CustomUserDetailsService() {
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        System.out.println("seller..");
//        if( userRepository instanceof SellerRepository ){
//            sellerRepository = ((SellerRepository) userRepository);
//            Seller seller = sellerRepository.findByEmail(usernameOrEmail)
//                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail));
//            return new org.springframework.security.core.userdetails.User(seller.getEmail(), seller.getPassword(), mapRolesToAuthorities(seller.getRoles()));
//        }else{
//            buyerRepository =  ((BuyerRepository) userRepository);
//            Buyer buyer = buyerRepository.findByEmail(usernameOrEmail)
//                    .orElseThrow(() -> new UsernameNotFoundException("BUYER not found with username or email:" + usernameOrEmail));
//                 return new org.springframework.security.core.userdetails.User(buyer.getEmail(), buyer.getPassword(), mapRolesToAuthorities(buyer.getRoles()));
//
//        }
        Seller seller = sellerRepository.findByEmail(usernameOrEmail).orElse(null);
        if (seller != null){
         //   userRepository = sellerRepository;
            return new org.springframework.security.core.userdetails.User(seller.getEmail(), seller.getPassword(), mapRolesToAuthorities(seller.getRoles()));

        }else {
            Buyer buyer = buyerRepository.findByEmail(usernameOrEmail)
                    .orElse(null);
            if(buyer!=null){
             //   userRepository = buyerRepository;
                return new org.springframework.security.core.userdetails.User(buyer.getEmail(), buyer.getPassword(), mapRolesToAuthorities(buyer.getRoles())) ;
            }
        }
        throw  new  UsernameNotFoundException("User not found with username or email:" + usernameOrEmail);
    }

    public Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}