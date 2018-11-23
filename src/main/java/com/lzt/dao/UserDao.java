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
    @Query(value="select * from #{#entityName} where username=?1",nativeQuery = true)
    User findByName(String username);

    @Query(value="select * from #{#entityName} where id=?1",nativeQuery = true)
    User findById(Long id);

    //ct password from tb2_user where username=#{username}")
    //String aaa(@Param("username") String username);



}
