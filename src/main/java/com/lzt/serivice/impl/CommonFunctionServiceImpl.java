package com.lzt.serivice.impl;

import com.lzt.dao.IBMJpaDao;
import com.lzt.entity.IBM;
import com.lzt.serivice.CommonFunctionService;
import com.lzt.serivice.IBMService;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

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
    public void synchro() throws ParseException, MessagingException {
        System.out.println("开始同步…………");
        List<IBM> ibmlist = ibmService.findAll();
        List<String> namelist = new ArrayList<String>();
        for (IBM ibm : ibmlist) {
            long days=0;
            try {
                 days = getDay(ibm.getTime());
            }catch (Exception e){}

            if (days <= 0) //过期
                ibm.setState("2");
            else if (days > 0 && days <= 90)//即将过期
            {
                ibm.setState("1");
                if (ibm.getName() != null)
                    namelist.add(ibm.getName());
            } else//大于3个月
                ibm.setState("0");
            ibmdao.save(ibm); //update
        }

        if (namelist != null) { //设置邮件内容
            StringBuffer message = new StringBuffer();
           message.append("您好!");
            for (String name : namelist) {
                message.append(name+"  ");
            }
            message.append("将要过保 请及时续保 ");
            sendEmail(message.toString());

        }
        System.out.println("同步结束");
    }

    @Override
    /** 计算时间差*/
    public long getDay(String time) throws ParseException {
        if (time == null)
            return 0;
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date time1 = f.parse(time);
        Date time2 = new Date();  //当前时间
        long day = (time1.getTime() - time2.getTime()) / 1000 / 60 / 60 / 24;
        return day;
    }

    @Override
    public void sendEmail(String str) throws MessagingException {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 12, 15, TimeUnit.MILLISECONDS,
                workQueue, new ThreadPoolExecutor.CallerRunsPolicy());
        EmailFast ef=new EmailFast(str);
        executor.execute(ef);
        System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
                executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());


    }
}
