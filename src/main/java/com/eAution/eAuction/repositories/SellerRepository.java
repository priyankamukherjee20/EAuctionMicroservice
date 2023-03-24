package com.eAution.eAuction.repositories;

import com.eAution.eAuction.Entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {
    Boolean existsByEmail(String email);
    Optional<Seller> findByEmail(String email);
}
