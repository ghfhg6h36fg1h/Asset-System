package com.lzt.serivice;

import com.lzt.entity.User;

import java.util.List;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */
public interface UserService {

    User findByName(String username);

    void updateuser(User user);
    void saveUser(User user);
    void deleteUser(long id);
    List findAll();

    User findByid(Long id);
}
