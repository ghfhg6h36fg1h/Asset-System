package com.lzt.SampleController;

import com.google.zxing.WriterException;
import com.lzt.entity.IBM;
import com.lzt.serivice.CommonFunctionService;
import com.lzt.serivice.IBMJpaService;
import com.lzt.serivice.IBMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Enzo Cotter on 2018-7-19.
 */
@Controller
public class IBMController {
    @Autowired
    HttpServletRequest request;

    @Autowired
    IBMService ibmService;

    @Autowired
    IBMJpaService ibmJpaService;

    @Autowired
    CommonFunctionService cfs;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping("/IBM")
    public String GetPCList(Model model) {

        String tempPage = request.getParameter("page");  //获取页码
        String jumpPage = request.getParameter("jumpPage");//获取跳转页码
        String type = request.getParameter("type");//获取页码类型
        String keyWord = request.getParameter("keyWord");//获取关键字
        String state=request.getParameter("st");//获取状态值

        HashMap map = ibmService.findIBMByPage(tempPage, jumpPage, type, keyWord,state, request);

        List<IBM> ibmList = (List<IBM>) map.get("ibmList");
        model.addAttribute("ibmList", ibmList);
        model.addAttribute("IBMPage", map.get("Allpage"));
        model.addAttribute("currentPage", map.get("currentPage"));
        model.addAttribute("keyWord", map.get("keyWord"));

        return "IBMManagement";
    }

    @RequestMapping(value="/SaveIBM" ,method = RequestMethod.POST)
    public void addIBM(HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("IBMName");
        String model1 = request.getParameter("model");
        String model2 = request.getParameter("model2");
        String sn = request.getParameter("sn");
        String time = request.getParameter("time");

        ibmJpaService.save(name, model1, model2, sn, time);

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/DeleteIBM", method = RequestMethod.POST)

    public void DeleteBIM(HttpServletResponse response) throws SecurityException, IOException {

        long id = Long.parseLong(request.getParameter("id"));

        ibmJpaService.delete(id);
    }
    @RequestMapping(value = "/UpdateIBM", method = RequestMethod.POST)
    public void UpdateIBM(HttpServletResponse response) throws ServletException, IOException {

        long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String model1 = request.getParameter("model1");
        String model2 = request.getParameter("model2");
        String sn = request.getParameter("sn");
        String time = request.getParameter("time");



        ibmJpaService.update(id, name, model1, model2,  sn, time);

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.flush();
        out.close();
    }


    @RequestMapping("/IBMQR")
    public String IBMQR() throws WriterException, IOException {
        long id = Long.parseLong(request.getParameter("IBMid"));
        ibmService.BuildQRById(id);

        return "redirect:/IBM";
    }

    @RequestMapping("/ALLIBMQR")
    public String ALLIBMQR() throws WriterException, IOException {
        List<IBM> ibmlist = ibmService.findAll();

        for (IBM ibm : ibmlist) {
            ibmService.BuildQRById(ibm.getId());
        }
        return "redirect:/IBM";
    }

    @RequestMapping("/synchro")
    public String synchro() throws ParseException, MessagingException {
     cfs.synchro();
        return "redirect:/IBM";
    }
}
