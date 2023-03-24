package com.eAution.eAuction.payLoad;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.Generated;

@Data @JsonInclude(Include.NON_NULL) @Generated
public class ErrorDetails {

    private String timestamp;
    private String fieldName;
    private String errorMessage;
}
