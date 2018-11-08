package com.lzt.SampleController;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;

import com.lzt.entity.ModelFloor;
import com.lzt.entity.PC;
import com.lzt.serivice.PCService;
import com.lzt.serivice.PcJpaService;

import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.List;


//Controller 单独可跳转页面
//RestController == Controller+ResponseBody 是无法跳转页面的
@Controller
public class PCController {

    @Autowired
    private PCService pcService;

    @Autowired
    private PcJpaService PCJpaService;

    @Autowired
    HttpServletRequest request;

    // 查询PC列表
    @RequestMapping("/PC")
    public String GetPCList(HttpServletRequest request, HttpServletResponse response,Model model) {

        String tempPage = request.getParameter("page");  //获取页码
        String jumpPage = request.getParameter("jumpPage");//获取跳转页码
        String type = request.getParameter("type");//获取页码类型
        String keyWord = request.getParameter("keyWord");//获取关键字
        String sort = request.getParameter("ss");//获取排序值
        String state = request.getParameter("st");//获取调拨值


        HashMap map = pcService.findPCByPage(tempPage, jumpPage, type, keyWord, sort, state, request);

        List<PC> pcList = (List<PC>) map.get("pcList");
        List<ModelFloor> mflist = (List<ModelFloor>) map.get("mflist");

        model.addAttribute("mflist", mflist);
        model.addAttribute("PCs", pcList);
        model.addAttribute("PCPage", map.get("Allpage"));
        model.addAttribute("currentPage", map.get("currentPage"));
        model.addAttribute("keyWord", map.get("keyWord"));

        HttpSession session = request.getSession();
        String loginName=(String)session.getAttribute("loginName");
        model.addAttribute("loginName",loginName);

        return "PCManagement";
    }

    @RequestMapping(value = "/SavePC", method = RequestMethod.POST)
    public void addPC(HttpServletResponse response) throws ServletException, IOException {

        String pcName = request.getParameter("pcName");
        String model = request.getParameter("model");
        String name = request.getParameter("name");
        String asset = request.getParameter("asset");
        String mac = request.getParameter("mac");
        String sn = request.getParameter("sn");
        String number = request.getParameter("number");
        String floor = request.getParameter("floor");
        String state = request.getParameter("state");

        PCJpaService.save(pcName, model, name, asset, mac, sn, number, floor, state);

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/UpdatePC", method = RequestMethod.POST)
    public void UpdatePC(HttpServletResponse response) throws ServletException, IOException {

        long id = Long.parseLong(request.getParameter("id"));
        String pcName = request.getParameter("pcName");
        String model = request.getParameter("model");
        String name = request.getParameter("name");
        String asset = request.getParameter("asset");
        String mac = request.getParameter("mac");
        String sn = request.getParameter("sn");
        String number = request.getParameter("number");
        String floor = request.getParameter("floor");
        String state = request.getParameter("state");

        PCJpaService.update(id, pcName, model, name, asset, mac, sn, number, floor, state);

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/QuickPC", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject QuickPC(Model model, HttpServletResponse response) throws SecurityException, IOException {
        String tempPage = request.getParameter("page");  //获取页码
        String jumpPage = request.getParameter("jumpPage");//获取跳转页码
        String type = request.getParameter("type");//获取页码类型
        String keyWord = request.getParameter("keyword");//获取关键字
        String sort = request.getParameter("ss");//获取排序值
        String state = request.getParameter("st");//获取调拨值


        HashMap map = pcService.findPCByPage(tempPage, jumpPage, type, keyWord, sort, state, request);

        List<PC> pcList = (List<PC>) map.get("pcList");


        JSONObject result = new JSONObject();
        if (pcList.size() != 0)
            result.put("success", true);
        else {
            result.put("success", false);
        }

        result.put("pclist", pcList);

        return result;
    }

    @RequestMapping(value = "/DeletePC", method = RequestMethod.POST)
    public void DeletePC(HttpServletResponse response) throws SecurityException, IOException {

        long id = Long.parseLong(request.getParameter("id"));

        PCJpaService.delete(id);
    }

    @RequestMapping(value = "/QRCode", method = RequestMethod.POST)
    public void bulidQRCode(HttpServletResponse response) throws WriterException, SecurityException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        pcService.BuildQRById(id);
    }

    @RequestMapping("/ALLQR")
    public String ALLQR() throws WriterException, IOException {
        List<PC> pclist = pcService.findAllPC();

        for (PC pc : pclist) {
            pcService.BuildQRById(pc.getId());
        }
        return "redirect:/PC";
    }
}