package com.lzt.SampleController;



import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;

import com.lzt.entity.ModelFloor;
import com.lzt.entity.PC;
import com.lzt.serivice.PCService;
import com.lzt.serivice.PcJpaService;

import jdk.nashorn.internal.ir.RuntimeNode;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

import java.net.URLEncoder;
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
        String remark=(String)session.getAttribute("remark");
        String power=(String)session.getAttribute("power");
        model.addAttribute("remark",remark);
        model.addAttribute("power",power);
        model.addAttribute("SumNumber",map.get("SumNumber"));

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
        String usb = request.getParameter("usb");
        String mcafee = request.getParameter("mcafee");
        String net = request.getParameter("net");
        PCJpaService.save(pcName, model, name, asset, mac, sn, number, floor, state,usb,mcafee,net);

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
        String usb = request.getParameter("usb");
        String mcafee = request.getParameter("mcafee");
        String net = request.getParameter("net");
        PCJpaService.update(id, pcName, model, name, asset, mac, sn, number, floor, state,usb,mcafee,net);

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


    @RequestMapping(value = "/PrintPC", method = RequestMethod.GET)
    public String getInfo(HttpServletResponse response,Model model) throws WriterException, SecurityException, IOException {
        long id = Long.parseLong(request.getParameter("id"));

        PC pc = pcService.findByID(id);

        model.addAttribute("pcName", pc.getPCName());
        model.addAttribute("user", pc.getUsername());
        model.addAttribute("floor", pc.getFloor());
        model.addAttribute("model", pc.getModel());
        model.addAttribute("mac", pc.getMAC());
        model.addAttribute("sn", pc.getSN());
        model.addAttribute("AssetNumber", pc.getAssetNumber());
        model.addAttribute("usb", pc.getUsb());

         return "PCInfo";
    }


    @RequestMapping(value = "/DownPC", method = RequestMethod.GET)
    public void DownPC(HttpServletResponse response,Model model) throws WriterException, SecurityException, IOException {
        @SuppressWarnings("resource")
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("PCList");  //创建table工作薄

        HSSFRow row;
        HSSFCell cell;
        int rowNumbwer=1;
        row=sheet.createRow(0);
        row.createCell(0).setCellValue("PC名");
        row.createCell(1).setCellValue("使用人");
        row.createCell(2).setCellValue("工号");
        row.createCell(3).setCellValue("楼层");
        row.createCell(4).setCellValue("型号");
        row.createCell(5).setCellValue("MAC");
        row.createCell(6).setCellValue("SN");
        row.createCell(7).setCellValue("描述");
        row.createCell(8).setCellValue("USB");
        row.createCell(9).setCellValue("状态");
        row.createCell(10).setCellValue("Mcafee");
        row.createCell(11).setCellValue("Net");
        List<PC> pcList=pcService.findAllPC();

         for(PC pc:pcList){
             row=sheet.createRow(rowNumbwer);
             row.createCell(0).setCellValue(pc.getPCName());
             row.createCell(1).setCellValue(pc.getUsername());
             row.createCell(2).setCellValue(pc.getUserNumber());
             row.createCell(3).setCellValue(pc.getFloor());
             row.createCell(4).setCellValue(pc.getModel());
             row.createCell(5).setCellValue(pc.getMAC());
             row.createCell(6).setCellValue(pc.getSN());
             row.createCell(7).setCellValue(pc.getAssetNumber());
             row.createCell(8).setCellValue(pc.getUsb());
             row.createCell(9).setCellValue(pc.getState());
             row.createCell(10).setCellValue(pc.getMcafee());
             row.createCell(11).setCellValue(pc.getNet());
             rowNumbwer++;
        }

        //生成文件
        FileOutputStream fos = new FileOutputStream("pc");
        wb.write(fos);
        fos.flush();
        fos.close();
        //导出
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("pc.xls", "utf-8"));
        OutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();

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