package com.lzt.serivice;

import java.text.ParseException;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */

public interface CommonFunctionService  {

       void synchro() throws ParseException;
       long getDay(String time) throws ParseException;
}
