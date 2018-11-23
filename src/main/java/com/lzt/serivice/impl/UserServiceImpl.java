package com.lzt.serivice.impl;

import com.lzt.dao.UserDao;
import com.lzt.entity.User;
import com.lzt.serivice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userdao;

    @Override
    public User findByName(String username) {
        return userdao.findByName(username);
    }

    @Override
    public void updateuser(User user) {
        userdao.save(user);

    }

    @Override
    public void saveUser(User user) {
        userdao.save(user);
    }

    @Override
    public void deleteUser(long id) {
        userdao.delete(id);
    }

    @Override
    public List findAll() {

        Iterator Imf=userdao.findAll().iterator();
        List userList=new ArrayList<User>();
        while (Imf.hasNext())
            userList.add(Imf.next());
        return userList;
    }

    @Override
    public User findByid(Long id) {
        return userdao.findById(id);
    }


}
