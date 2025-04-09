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

    private int user_id;
    private String user_name;
    private String email;
    private String pw;
    private String profile_image;
    private int coin;
}
