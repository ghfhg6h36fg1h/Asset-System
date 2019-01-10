package com.lzt.serivice;

import com.google.zxing.WriterException;
import com.lzt.entity.PC;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */
public interface PCService {

    HashMap findPCByPage(String tempPage, String jumpPage, String type, String keyWord, String sort,String state, HttpServletRequest request );

    long Findcount(String keyword,String state);

    PC findByID(long id);
    List<PC> findAllPC();
    void BuildQRById(long id)throws WriterException ,IOException;

    void Clear();

    HashMap findSelectPC(String tempPage, String jumpPage, String keyWord, String sort, String state, String usb, String net,String type,HttpServletRequest request );

    long FindcountBySelect(String keyWord, String sort, String net, String usb, String state);
}
