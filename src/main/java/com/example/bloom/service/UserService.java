package com.example.bloom.service;

import com.example.bloom.bean.User;
import com.example.bloom.service.db.UserDB;
import com.pri.tool.commonredis.bloom.BloomFilterHelper;
import com.pri.tool.commonredis.bloom.RedisBloomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
    private RedisBloomUtil redisServiceUtil;
    @Autowired
    private BloomFilterHelper bloomFilterHelper;


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
