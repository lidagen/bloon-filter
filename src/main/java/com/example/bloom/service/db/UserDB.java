package com.example.bloom.service.db;

import com.example.bloom.bean.User;
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
public class UserDB implements InitializingBean {
    public static List<User> USER_LIST;

    @Override
    public void afterPropertiesSet() throws Exception {
        USER_LIST = all();
    }

    public static User getUser(Long id){
        List<User> userList = USER_LIST;
        for (User user : userList) {
            if (user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }
    private List<User> all() {
        List<User> list = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            User u = new User();
            u.setId(Long.valueOf(i));
            u.setName("ws:" + u.getId());
            list.add(u);
        }
        return list;
    }
}
