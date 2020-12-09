package com.example.bloom.controller;

import com.example.bloom.bean.User;
import com.example.bloom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wang.song
 * @date 2020-12-02 11:37
 * @Desc
 */
@RestController
@RequestMapping(value = "/bloom")
public class BloomController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    public Object invoiceCountCheck(Long id) {
        User userById = userService.getUserById(id);
        return userById;
    }


    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public Object invoiceCountCheck() {

        return "success";
    }



}
