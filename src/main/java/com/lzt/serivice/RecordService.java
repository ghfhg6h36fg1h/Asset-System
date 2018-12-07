package com.lzt.serivice;



import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */
public interface RecordService {

void save(String username,String time,String operation,String remark);

    List findAll();

    HashMap findByPage(String tempPage, String jumpPage, String type,  HttpServletRequest request);

}
