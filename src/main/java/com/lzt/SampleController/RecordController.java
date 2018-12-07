package com.lzt.SampleController;


import com.lzt.dao.RecordDao;
import com.lzt.entity.Record;
import com.lzt.serivice.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Enzo Cotter on 2018-11-26.
 */
@Controller
public class RecordController {

    @Autowired
    HttpServletRequest request;
    @Autowired
    private RecordService Recordservice;


    @RequestMapping("/RecordList")
    public String RecordList(Model model) {
        String tempPage = request.getParameter("page");  //获取页码
        String jumpPage = request.getParameter("jumpPage");//获取跳转页码
        String type = request.getParameter("type");//获取页码类型

        HashMap map = Recordservice.findByPage(tempPage, jumpPage, type,  request);

        List<Record> recordList = (List<Record>) map.get("recordList");

        model.addAttribute("recordList", recordList);
        model.addAttribute("RecordPage", map.get("Allpage"));
        model.addAttribute("currentPage", map.get("currentPage"));
        return "record";
    }
}
