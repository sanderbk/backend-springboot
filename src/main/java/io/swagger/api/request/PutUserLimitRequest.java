package io.swagger.api.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PutUserLimitRequest {
    private Double dayLimit;
    private Double transLimit;
    private String userStatus;
}
