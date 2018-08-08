package com.lzt.serivice;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */

public interface ModelFloorService {

    List findAll();
    void save(String name ,String type,String remark);

    void update(long id, String name, String type, String remark);


    void delete(long id);
}
