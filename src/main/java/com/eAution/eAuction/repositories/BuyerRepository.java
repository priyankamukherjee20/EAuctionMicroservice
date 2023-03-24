package com.eAution.eAuction.repositories;

import com.eAution.eAuction.Entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyerRepository extends JpaRepository<Buyer,Long> {
    Boolean existsByEmail(String email);
    Optional<Buyer> findByEmail(String email);
}
