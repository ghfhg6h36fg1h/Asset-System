package com.lzt.serivice.impl;

import com.lzt.dao.UserDao;
import com.lzt.serivice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userdao;
    @Override
    public String findPassByName(String username) {
        return userdao.findPassByName(username);
    }
}
