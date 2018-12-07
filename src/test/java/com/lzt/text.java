package com.lzt;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Enzo Cotter on 2018-7-13.
 */
public class text {

        public static void main(String[] args) throws  ParseException {

//            String t1="2018-08-11";  //保修时间
          DateFormat f=new SimpleDateFormat("yyyy-MM-dd");

//            Date time1=f.parse(t1);
//            Date time2=new Date();  //当前时间
//            long day=(time1.getTime()-time2.getTime())/1000/60/60/24;
           System.out.println(f.format(new Date()));
        }
    }
