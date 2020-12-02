package com.example.bloom.service.db;

import com.example.bloom.bean.User;
import com.example.bloom.config.DatePreheating;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wang.song
 * @date 2020-12-02 13:50
 * @Desc
 */
@Component
public class UserDB  {


    public static User getUser(Long id){

        List<User> userList =  DatePreheating.USER_LIST;
        for (User user : userList) {
            if (user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }

}
