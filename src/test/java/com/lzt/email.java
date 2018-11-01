package com.lzt;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Enzo Cotter on 2018/8/11.
 */
public class email {
    public static void main(String[] args) throws MessagingException {

        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 12, 15, TimeUnit.MILLISECONDS,
                workQueue, new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i=0;i<200;i++)
        {
            EmailTask et=new EmailTask(i);
            executor.execute(et);
            System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
                    executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
        }
System.out.println("over");


    }

}
