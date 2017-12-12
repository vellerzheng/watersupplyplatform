package com.waterworks.model.userModel;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;


@Component
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
