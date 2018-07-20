package com.lzt.SampleController;

import com.google.zxing.WriterException;
import com.lzt.entity.IBM;
import com.lzt.serivice.IBMService;
import com.lzt.serivice.ModelFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    @RequestMapping("/IBM")
    public String GetPCList(Model model) {
        String tempPage = request.getParameter("page");  //获取页码
        String jumpPage = request.getParameter("jumpPage");//获取跳转页码
        String type = request.getParameter("type");//获取页码类型
        String keyWord = request.getParameter("keyWord");//获取关键字

        HashMap map = ibmService.findIBMByPage(tempPage, jumpPage, type, keyWord, request);

        List<IBM> ibmList = (List<IBM>) map.get("ibmList");
        model.addAttribute("ibmList", ibmList);
        model.addAttribute("IBMPage", map.get("Allpage"));
        model.addAttribute("currentPage", map.get("currentPage"));
        model.addAttribute("keyWord", map.get("keyWord"));

        return "IBMManagement";
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

}
