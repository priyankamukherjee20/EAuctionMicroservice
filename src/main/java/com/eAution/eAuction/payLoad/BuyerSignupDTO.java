package com.eAution.eAuction.payLoad;

import lombok.Data;

@Data
public class BuyerSignupDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String password;
    private String city;

    private String state;
    private String pin;

    private String phone;
    private String email;


}
