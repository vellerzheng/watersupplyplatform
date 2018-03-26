package com.waterworks.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class MongoUser {

    @Id
    private String id;
    private Integer userId;

    private String title;

    private String userName;

    private String msgType;

    private String msgInfo;

    //getter setter


    @Override
    public String toString() {
        return "[ id ="+id+", title ="+title+", msgInfo="+msgInfo+" ]";
    }
}
