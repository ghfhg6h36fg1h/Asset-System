package com.lzt.dao;


import com.lzt.entity.PC;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Enzo Cotter on 2018-6-20.
 */
@Repository
public interface PcDao  { //extends CrudRepository<PC,Long> {
    @Select("select * from tb2_pc where " +
            "(pcname like '%' #{keyword} '%')or(model like '%' #{keyword} '%')or" +
            "(username like '%' #{keyword} '%' )or(user_number like '%' #{keyword} '%' )" +
            "or(asset_number like '%' #{keyword} '%' )or(mac like '%' #{keyword} '%')or" +
            "(floor like '%' #{keyword} '%' )or(sn like '%' #{keyword} '%' )" +
            "ORDER by ${sort} ${sortType} limit #{page},#{PrintNumber}")
    List<PC> findPCByPage(@Param("page") long page,@Param("PrintNumber") int PrintNumber,
                          @Param("keyword") String keyword,@Param("sort") String sort,
                          @Param("sortType")String sortType);

    @Select("select count(*) from tb2_pc where"+
            "(pcname like '%' #{keyword} '%')or(model like '%' #{keyword} '%')or"+
            "(username like '%' #{keyword} '%' )or(user_number like '%' #{keyword} '%' )" +
            "or(asset_number like '%' #{keyword} '%' )or(mac like '%' #{keyword} '%')or" +
            "(floor like '%' #{keyword} '%' )or(sn like '%' #{keyword} '%' )"
    )
    long Findcount(@Param("keyword")String keyword);

    @Select("select * from tb2_pc where id=#{id}")
    PC findByID(@Param("id") long id);

    @Select("select * from tb2_pc")
    List<PC> findAllPC();
}
