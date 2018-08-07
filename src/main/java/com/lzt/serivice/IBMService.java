package com.lzt.serivice;


import com.google.zxing.WriterException;
import com.lzt.entity.IBM;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */
public interface IBMService {

    HashMap findIBMByPage(String tempPage, String jumpPage, String type, String keyWord, HttpServletRequest request);


    long Findcount(String keyWord);

    IBM findByID(long id);

    void BuildQRById(long id) throws WriterException, IOException;

    List<IBM> findAll();


}
