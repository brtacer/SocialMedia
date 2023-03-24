package com.berat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    private String token;
    private String username;
    private String email;
    private String address;
    private String about;
    private String phone;
    private String avatar;
}
