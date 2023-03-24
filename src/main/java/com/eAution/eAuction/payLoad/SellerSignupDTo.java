package com.eAution.eAuction.payLoad;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;

@Data @EqualsAndHashCode(callSuper = false) @JsonInclude(JsonInclude.Include.NON_NULL) @Generated
public class SellerSignupDTo {
    @NotBlank(message = "{firstName.null.message}") @Size(message = "{firstName.size.message}")
    private String firstName;
    @NotBlank(message = "{lastName.null.message}") @Size(message = "{lastName.size.message}")
    private String lastName;
    private String address;
    @NotBlank(message = "{password.null.message}")
    private String password;
    private String city;

    private String state;
    private String pin;

    @NotBlank(message = "{phone.number.null.message}") @Size(message = "{phone.size.message}") @Pattern(regexp = "(^$|[0-9]{10})", message = "{phone.number.invalid.message}")
    private String phone;
    @NotBlank(message = "{email.null.message}") @Email(message = "{email.format.message}")
    private String email;



}
