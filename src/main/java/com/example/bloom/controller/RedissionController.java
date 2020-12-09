package com.example.bloom.controller;

import com.pri.tool.commonredis.lock.annotation.DistributedLock;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wang.song
 * @date 2020-12-09 15:34
 * @Desc
 */
@RestController
@RequestMapping(value = "/redission")
public class RedissionController {
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @DistributedLock(key = "redTest")
    public Object invoiceCountCheck() {
     /*   try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DistributedRedisLock.release("test222");*/
        return "success";
    }

    @RequestMapping(value = "/all1", method = RequestMethod.POST)
    @DistributedLock(key = "redTest")
    public Object all1() {
        System.out.println(1234);
        return 1234;
    }
}
