package com.eAution.eAuction.payLoad;

import lombok.Data;

@Data
public class SellerLoginDTO {
    private String usernameOrEmail;
    private String password;
}
