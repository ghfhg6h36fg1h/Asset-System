package com.lzt.dao;

import com.lzt.entity.User;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Enzo Cotter on 2018-6-20.
 */
@Repository
public interface UserDao extends CrudRepository<User,Long> {
    @Query(value="select password from #{#entityName} where username=?1",nativeQuery = true)
    String findPassByName(String username);

    //ct password from tb2_user where username=#{username}")
    //String aaa(@Param("username") String username);



}
