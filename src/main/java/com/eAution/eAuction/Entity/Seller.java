package com.eAution.eAuction.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data

@Table(name = "sellers")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String address;
    private String password;
    private String city;
    private String state;
    private String pin;
    private String phone;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="seller_role",
            joinColumns = @JoinColumn(name = "seller_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
}
