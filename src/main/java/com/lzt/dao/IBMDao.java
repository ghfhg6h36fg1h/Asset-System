package com.lzt.dao;


import com.lzt.entity.IBM;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by Enzo Cotter on 2018-6-20.
 */
@Repository
public interface IBMDao {

    @Select("select * from tb2_ibm where " +
            "(model1 like '%' #{keyword} '%')or(model2 like '%' #{keyword} '%')or" +
            "(name like '%' #{keyword} '%' )or(sn like '%' #{keyword} '%' )" +
            " limit #{page},#{printNumber}")
    List<IBM> findIBMByPage(@Param("page")long page, @Param("printNumber")int printNumber, @Param("keyword")String keyWord);



    @Select("select count(*) from tb2_ibm where"+
            "(model1 like '%' #{keyword} '%')or(model2 like '%' #{keyword} '%')or"+
            "(name like '%' #{keyword} '%' )or(sn like '%' #{keyword} '%' )")
    long finCount(@Param("keyword")String keyWord);

    @Select("select * from tb2_ibm where id=#{id}")
    IBM findIBMById(@Param("id")long id);

    @Select("select * from tb2_ibm")
    List<IBM> findAll();

}
