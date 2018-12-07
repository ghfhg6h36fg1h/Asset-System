package com.lzt.serivice.impl;

import com.lzt.dao.RecordDao;
import com.lzt.dao.RecordJpaDao;
import com.lzt.entity.Record;
import com.lzt.serivice.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */
@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordJpaDao recordJpaDao;
    @Autowired
    private RecordDao RecordDao;

    public static final int PrintNumber = 50;

    @Override
    public void save(String username, String time, String operation, String remark) {
        Record record=new Record(username,time,operation,remark);
        recordJpaDao.save(record);
    }

    @Override
    public List findAll() {

        Iterator Imf=recordJpaDao.findAll().iterator();
        List recordList=new ArrayList<Record>();
        while (Imf.hasNext())
            recordList.add(Imf.next());
        return recordList;
    }

    @Override
    public HashMap findByPage(String tempPage, String jumpPage, String type, HttpServletRequest request) {

        long currentPage;  //当前页
        long Allpage;//总页数

        if (tempPage == null && jumpPage == null) { //初始当前页
            currentPage = 1;
        } else if (tempPage != null) {//获取当前页 +1或-1
            int page = Integer.parseInt(tempPage);
            if (type.equals("next"))
                currentPage = page + 1;
            else
                currentPage = page - 1;
        } else {  //跳转页面
            if (jumpPage=="")
                currentPage=1;
            else {
                int page = Integer.parseInt(jumpPage);
                currentPage = page;
            }
        }



        Allpage = this.Findcount() / PrintNumber + 1;

//防呆
        if (currentPage > Allpage) currentPage = Allpage;
        if (currentPage <= 0) currentPage = 1;

        long StatNumber = (currentPage - 1) * PrintNumber;


      List<Record> recordList = RecordDao.findByPage(StatNumber, PrintNumber);
    //    List<Record> recordList = RecordDao.findByPage2();
        HashMap map = new HashMap();
        map.put("recordList", recordList);
        map.put("Allpage", Allpage);
        map.put("currentPage", currentPage);

        return map;
    }

    private long Findcount() {
        return RecordDao.finCount();
    }

}
