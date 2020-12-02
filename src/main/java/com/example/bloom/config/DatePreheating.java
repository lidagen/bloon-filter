package com.example.bloom.config;

import com.example.bloom.bean.User;
import com.example.bloom.service.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wang.song
 * @date 2020-12-02 20:15
 * @Desc 数据加载
 */
@Component
public class DatePreheating implements InitializingBean {
    @Autowired
    private UserService userService;

    public static List<User> USER_LIST;

    @Override
    public void afterPropertiesSet() throws Exception {
        USER_LIST = all();
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
