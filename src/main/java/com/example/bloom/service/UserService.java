package com.example.bloom.service;

import com.example.bloom.bean.User;
import com.example.bloom.bloom.BloomFilterHelper;
import com.example.bloom.service.db.UserDB;
import com.example.bloom.util.RedisServiceUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author wang.song
 * @date 2020-12-02 11:39
 * @Desc
 */
@Service
public class UserService {
    private static final String KEY = "user_";
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisServiceUtil redisServiceUtil;
    @Autowired
    private BloomFilterHelper bloomFilterHelper;


    public List<User> getAll() {
        List<User> users = redisTemplate.boundHashOps("id").values();
        if (CollectionUtils.isEmpty(users)) {
            users = UserDB.USER_LIST;
            for (User user : users) {
                //加入redis
                redisTemplate.boundHashOps(KEY).put(user.getId(), user);
                //加入布隆
                redisServiceUtil.addByBloomFilter(bloomFilterHelper, "bloom", String.valueOf(user.getId()));
            }
        }
        return users;
    }


    public User getUserById(Long id) {
        boolean include = redisServiceUtil.includeByBloomFilter(bloomFilterHelper, "bloom", String.valueOf(id));
        //布隆过滤器中存在
        if (include) {
            //redis取
            User user = (User) redisTemplate.boundHashOps(KEY).get(id);
            if (Objects.isNull(user)) {
                //查数据库
                user = UserDB.getUser(id);
                if (Objects.nonNull(user)) {
                    redisTemplate.boundHashOps(KEY).put(user.getId(), user);
                }
            }
            return user;
        }
        //布隆不存在
        return null;
    }
}
