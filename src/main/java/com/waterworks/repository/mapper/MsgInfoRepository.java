package com.waterworks.repository.mapper;

import com.waterworks.repository.entity.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MsgInfoRepository extends MongoRepository<MongoUser,String> {

    MongoUser queryMsgInfoByTitle(String title);
}
