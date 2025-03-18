package com.modam.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String userId;
    private String userName;
    private String email;
    private String pw;
    private String profileImage;
    private int coin;
}
