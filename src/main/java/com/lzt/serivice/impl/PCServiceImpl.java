package com.lzt.serivice.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.lzt.dao.PcDao;
import com.lzt.entity.IBM;
import com.lzt.entity.ModelFloor;
import com.lzt.entity.PC;
import com.lzt.serivice.ModelFloorService;
import com.lzt.serivice.PCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */
@Service
public class PCServiceImpl implements PCService {

    public static final int PrintNumber = 50;
    @Autowired
    private PcDao pcdao;
    @Autowired
    private ModelFloorService modelFloorService;

    @Override
    public HashMap findPCByPage(String tempPage, String jumpPage, String type, String keyWord, String sort, String state, HttpServletRequest request) {

        HttpSession session = request.getSession();
        long currentPage;  //当前页
        long Allpage;//总页数
        String sortType;  //排序类型

        if (tempPage == null && jumpPage == null) { //初始当前页 有可能为查找+排序
            currentPage = 1;
            if (sort == null) {//最初始
                session.setAttribute("keyword", "");
                session.setAttribute("sort", "model");
                session.setAttribute("sortType", "DESC");

                if (state == null)//点查询时，或初始化
                {
                    session.setAttribute("state", "");
                }
            }
        } else if (tempPage != null) {//获取当前页 +1或-1
            int page = Integer.parseInt(tempPage);
            if (type.equals("next"))
                currentPage = page + 1;
            else
                currentPage = page - 1;
        } else {  //跳转页面
            if (jumpPage == "")
                currentPage = 1;
            else {
                int page = Integer.parseInt(jumpPage);
                currentPage = page;
            }
        }
        // 分页多条件缓存
        if (keyWord != null)
            session.setAttribute("keyword", keyWord);
        if (state != null) {
            session.setAttribute("state", state);
        }
        if (sort != null) {
            if (sort.equals(session.getAttribute("sort")))  //重复选择排序方式
                session.setAttribute("sortType", "ASC");//倒叙
            else {
                session.setAttribute("sortType", "DESC");
                session.setAttribute("sort", sort);
            }
        }
        keyWord = (String) session.getAttribute("keyword");
        sort = (String) session.getAttribute("sort");
        sortType = (String) session.getAttribute("sortType");
        state = (String) session.getAttribute("state");
        long SumNumber = this.Findcount(keyWord, state);

        Allpage = SumNumber / PrintNumber + 1;

//防呆
        if (currentPage > Allpage) currentPage = Allpage;
        if (currentPage <= 0) currentPage = 1;


        long StatNumber = (currentPage - 1) * PrintNumber;
        List<PC> pcList = pcdao.findPCByPage(StatNumber, PrintNumber, keyWord, sort, sortType, state);

// 取出所有ModelFloor
        List<ModelFloor> mflist = modelFloorService.findAll();

        HashMap map = new HashMap();
        map.put("mflist", mflist);
        map.put("pcList", pcList);
        map.put("Allpage", Allpage);
        map.put("currentPage", currentPage);
        map.put("keyWord", keyWord);
        map.put("SumNumber", SumNumber);
        return map;
    }

    @Override
    public long Findcount(String keyword, String state) {
        return pcdao.Findcount(keyword, state);
    }

    @Override
    public PC findByID(long id) {

        return pcdao.findByID(id);
    }

    @Override
    public List<PC> findAllPC() {

        return pcdao.findAllPC();
    }

    @Override
    public void BuildQRById(long id) throws WriterException, IOException {
        PC pc = findByID(id);
        String filePath = "E:/qr/";  //后期可加时间控件区分名字
        String fileName = pc.getPCName() + "-QR.png";

        String content = "http://192.168.1.156:8080/PrintPC?id=" + pc.getId();

        int width = 200; // 图像宽度
        int height = 200; // 图像高度
        String format = "png";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        Path path = FileSystems.getDefault().getPath(filePath, fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
        System.out.println(pc.getPCName() + "  输出成功.");

    }

    @Override
    public void Clear() {
        pcdao.clear();
    }

    @Override
    public HashMap findSelectPC(String tempPage, String jumpPage, String keyWord, String sort, String state, String usb, String net, String type, HttpServletRequest request) {

        long currentPage = 1;
        HashMap map = new HashMap();
        HttpSession session = request.getSession();

        if (jumpPage != null) //只有点跳转不为null
            type = "jump";
        if (jumpPage == "")
            jumpPage = "1";

        if (keyWord == null && type == null) { //PC管理跳转  (初始化)
            session.setAttribute("keyWord", "");
            session.setAttribute("usb", "");
            session.setAttribute("net", "");
            session.setAttribute("state", "");
            session.setAttribute("sort", "model");
        }

        //点击查询
        if (keyWord != null) session.setAttribute("keyWord", keyWord);
        if (usb != null) session.setAttribute("usb", usb);
        if (net != null) session.setAttribute("net", net);
        if (state != null) session.setAttribute("state", state);

        if (sort == "") {//为空初始化排序
            sort = "model";
            session.setAttribute("sort", "model");
        }
        if (sort != "" && sort != null) session.setAttribute("sort", sort);

//上一页 下一页 跳转
        if (sort == null) sort = (String) session.getAttribute("sort");

        if (keyWord == null) keyWord = (String) session.getAttribute("keyWord");

        if (usb == null) usb = (String) session.getAttribute("usb");
        if (net == null) net = (String) session.getAttribute("net");
        if (state == null) state = (String) session.getAttribute("state");

        System.out.println(type);
        if (type == null)
            currentPage = 1;
        else if (type == "jump")
            currentPage = Integer.parseInt(jumpPage);
        else if (type.equals("next"))
            currentPage = Integer.parseInt(tempPage) + 1;
        else if (type.equals("last"))
            currentPage = Integer.parseInt(tempPage) - 1;


        long SumNumber = this.FindcountBySelect(keyWord, sort, net, usb, state);//待更改

        long Allpage = SumNumber / PrintNumber + 1;

//防呆
        if (currentPage > Allpage) currentPage = Allpage;
        if (currentPage <= 0) currentPage = 1;
        System.out.println(currentPage);
        System.out.println(Allpage);

        long StatNumber = (currentPage - 1) * PrintNumber;
        System.out.println(StatNumber);
//        System.out.println("-----------------------------------");
//        System.out.println(StatNumber);
//        System.out.println(PrintNumber);
//        System.out.println(keyWord);
//        System.out.println(sort);
//        System.out.println(net);
//        System.out.println(usb);
//        System.out.println(state);
//        System.out.println("-----------------------------------");

        List<PC> pcList = pcdao.findPCByPageAndSelect(StatNumber, PrintNumber, keyWord, sort, net, usb, state);
        List<ModelFloor> mflist = modelFloorService.findAll();
        map.put("mflist", mflist);
        map.put("pcList", pcList);
        map.put("Allpage", Allpage);
        map.put("currentPage", currentPage);
        map.put("keyWord", keyWord);
        map.put("sort", sort);
        map.put("net", net);
        map.put("state", state);
        map.put("usb", usb);
        map.put("SumNumber", SumNumber);
        return map;
    }

    @Override
    public long FindcountBySelect(String keyWord, String sort, String net, String usb, String state) {
        return pcdao.FindcountBySelect(keyWord, sort, net, usb, state);
    }


}
