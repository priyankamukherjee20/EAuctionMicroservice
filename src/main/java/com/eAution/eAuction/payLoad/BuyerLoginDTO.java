package com.eAution.eAuction.payLoad;

import lombok.Data;

@Data
public class BuyerLoginDTO {
    private String usernameOrEmail;
    private String password;
}
