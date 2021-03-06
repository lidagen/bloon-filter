package com.example.bloom.config;

import com.example.bloom.bean.User;
import com.google.common.collect.Lists;
import com.pri.tool.commonredis.bloom.BloomFilterHelper;
import com.pri.tool.commonredis.bloom.RedisBloomUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    private BloomFilterHelper bloomFilterHelper;

    @Autowired
    private RedisTemplate redisTemplate;

    public static List<User> USER_LIST;
    @Autowired
    private RedisBloomUtil redisServiceUtil;

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
            redisServiceUtil.addByBloomFilter(bloomFilterHelper, "bloom", String.valueOf(u.getId()));
            list.add(u);
        }
        return list;
    }
}
