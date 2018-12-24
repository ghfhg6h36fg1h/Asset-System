package com.lzt.SampleController;


import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;

import com.lzt.entity.ModelFloor;
import com.lzt.entity.PC;
import com.lzt.serivice.PCService;
import com.lzt.serivice.PcJpaService;

import com.lzt.serivice.RecordService;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private RecordService recordService;

    @Autowired
    HttpServletRequest request;

    // 查询PC列表
    @RequestMapping("/PC")
    public String GetPCList(HttpServletRequest request, HttpServletResponse response, Model model) {

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
        String remark = (String) session.getAttribute("remark");
        String power = (String) session.getAttribute("power");
        model.addAttribute("remark", remark);
        model.addAttribute("power", power);
        model.addAttribute("SumNumber", map.get("SumNumber"));
        if (power != null)
            return "PCManagement";
        else
            return "E404";
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
        PCJpaService.save(pcName, model, name, asset, mac, sn, number, floor, state, usb, mcafee, net);
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("loginName");
        String rename = (String) session.getAttribute("remark");
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = f.format(new Date());
        String operation = "添加";
        String remark = username + "( " + rename + ")" + operation + " PC名为：" + pcName + "的机器";
        recordService.save(username, time, operation, remark);
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
        PCJpaService.update(id, pcName, model, name, asset, mac, sn, number, floor, state, usb, mcafee, net);

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("loginName");
        String rename = (String) session.getAttribute("remark");


        DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = f.format(new Date());
        String operation = "修改";
        String remark = username + "( " + rename + ")" + operation + " PC名为：" + pcName + "的机器信息";
        recordService.save(username, time, operation, remark);

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
        String name = (String) (request.getParameter("name"));

        PCJpaService.delete(id);

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("loginName");
        String rename = (String) session.getAttribute("remark");
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = f.format(new Date());
        String operation = "删除";
        String remark = username + "(" + rename + ")" + operation + " PC名为：" + name + "的机器";
        recordService.save(username, time, operation, remark);
    }

    @RequestMapping(value = "/QRCode", method = RequestMethod.POST)
    public void bulidQRCode(HttpServletResponse response) throws WriterException, SecurityException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        pcService.BuildQRById(id);
    }


    @RequestMapping(value = "/PrintPC", method = RequestMethod.GET)
    public String getInfo(HttpServletResponse response, Model model) throws WriterException, SecurityException, IOException {
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


    @RequestMapping(value = "/DownPC")
    public void DownPC(HttpServletResponse response) throws SecurityException, IOException {
        // @SuppressWarnings("resource")
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("PCList");  //创建table工作薄

        XSSFRow row;
        int rowNumbwer = 1;
        row = sheet.createRow(0);
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
        List<PC> pcList = pcService.findAllPC();
        for (PC pc : pcList) {
            row = sheet.createRow(rowNumbwer);
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

        //导出

        //  response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("pc.xlsx", "UTF-8"));

        OutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();

    }

    @RequestMapping(value = "/InputPC", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject InputPC(HttpServletResponse response, MultipartFile file) throws WriterException, SecurityException, IOException {

        InputStream inputStream = file.getInputStream();
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);

        XSSFSheet sheet = wb.getSheetAt(0);
        sheet.removeRow(sheet.getRow(0));
        JSONObject result = new JSONObject();
        List<PC> pclistTemp = new ArrayList<PC>();
        try {
            for (Row row : sheet) {//数字+null 处理

                for (int j = 0; j < 12; j++)//对null的处理
                    if (row.getCell(j) == null)
                        row.createCell(j);

                for (int j = 0; j < 12; j++)//对数字的处理
                    row.getCell(j).setCellType(XSSFCell.CELL_TYPE_STRING);


                String pcname = row.getCell(0).getStringCellValue();
                String name = row.getCell(1).getStringCellValue();
                String number = row.getCell(2).getStringCellValue();
                String floor = row.getCell(3).getStringCellValue();
                String model = row.getCell(4).getStringCellValue();
                String mac = row.getCell(5).getStringCellValue();
                String sn = row.getCell(6).getStringCellValue();
                String asset = row.getCell(7).getStringCellValue();
                String usb = row.getCell(8).getStringCellValue();
                String state = row.getCell(9).getStringCellValue();
                String mcafee = row.getCell(10).getStringCellValue();
                String net = row.getCell(11).getStringCellValue();
                PCJpaService.save(pcname, model, name, asset, mac, sn, number, floor, state, usb, mcafee, net);
            }

            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
        }
        return result;
    }


    @RequestMapping("/ALLQR")
    public String ALLQR() throws WriterException, IOException {
        List<PC> pclist = pcService.findAllPC();

        for (PC pc : pclist) {
            pcService.BuildQRById(pc.getId());
        }
        return "redirect:/PC";
    }

    @RequestMapping("/ClearPC")
    public String ClearPC() {

        pcService.Clear();
        return "redirect:/PC";
    }
}