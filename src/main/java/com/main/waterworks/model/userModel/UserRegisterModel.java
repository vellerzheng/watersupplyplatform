package com.main.waterworks.model.userModel;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class UserRegisterModel {

    private String name;
    private String password;
    private String email;
    private String phone;
}
