package com.lzt.dao;


import com.lzt.entity.Record;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Enzo Cotter on 2018-6-20.
 */
@Repository
public interface RecordDao {

    @Select("select * from tb2_record ORDER by id DESC limit #{page},#{printNumber} ")
    List<Record> findByPage(@Param("page") long page, @Param("printNumber") int printNumber);



    @Select("select count(*) from tb2_record")
    long finCount();


}
