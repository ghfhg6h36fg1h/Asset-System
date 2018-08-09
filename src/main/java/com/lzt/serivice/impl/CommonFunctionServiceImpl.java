package com.lzt.serivice.impl;

import com.lzt.dao.IBMJpaDao;
import com.lzt.entity.IBM;
import com.lzt.serivice.CommonFunctionService;
import com.lzt.serivice.IBMJpaService;
import com.lzt.serivice.IBMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Enzo Cotter on 2018-8-2.
 */
@Service
public class CommonFunctionServiceImpl implements CommonFunctionService {

    @Autowired
    IBMJpaDao ibmdao;

    @Autowired
    IBMService ibmService;
    @Override
    public void synchro() throws ParseException {
        System.out.println("开始同步…………");
        List<IBM> ibmlist =ibmService.findAll();
        for (IBM ibm :ibmlist)
        {
            long days=getDay(ibm.getTime());
              if (days<=0) //过期
                  ibm.setState("2");
              else if (days>0 && days<=90)//即将过期
                  ibm.setState("1");
              else//大于3个月
                  ibm.setState("0");

              ibmdao.save(ibm); //update

        }
        System.out.println("同步结束");
    }

    @Override
    /** 计算时间差*/
    public long getDay(String time) throws ParseException {
        if (time==null)
            return 0;
        DateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        Date time1=f.parse(time);
        Date time2=new Date();  //当前时间
        long day=(time1.getTime()-time2.getTime())/1000/60/60/24;
        return day;
    }
}
