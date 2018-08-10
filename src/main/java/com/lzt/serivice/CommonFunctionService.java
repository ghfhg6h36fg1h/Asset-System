package com.lzt.serivice;

import javax.mail.MessagingException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */

public interface CommonFunctionService  {

       void synchro() throws ParseException, MessagingException;
       long getDay(String time) throws ParseException;
       void sendEmail(String namelist) throws MessagingException;
}
