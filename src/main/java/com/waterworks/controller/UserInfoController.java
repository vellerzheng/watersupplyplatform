package com.waterworks.controller;

import com.waterworks.repository.entity.MongoUser;
import com.waterworks.repository.mapper.MsgInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserInfoController {

    @Autowired
    MsgInfoRepository msgInfoRepository;

    @RequestMapping("/messageInfo")
    public String messageInfo(String title, ModelMap modelMap) {

        MongoUser messageInfo = new MongoUser();
        messageInfo.setId("123456");
        messageInfo.setMsgInfo("hello world !");
        messageInfo.setTitle(title);
        messageInfo.setMsgType("1");
        msgInfoRepository.save(messageInfo);

        modelMap.addAttribute("test_mongodb",
                msgInfoRepository.queryMsgInfoByTitle("cwenao").toString());

        return "userinfo/accountInfo";
    }
}
