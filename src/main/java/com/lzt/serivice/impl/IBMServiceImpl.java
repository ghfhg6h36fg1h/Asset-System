package com.lzt.serivice.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.lzt.dao.IBMDao;
import com.lzt.entity.IBM;
import com.lzt.serivice.IBMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */
@Service
public class IBMServiceImpl implements IBMService {

    public static final int PrintNumber = 50;
    @Autowired
    private IBMDao ibmDao;

    @Override
    public HashMap findIBMByPage(String tempPage, String jumpPage, String type, String keyWord, HttpServletRequest request) {

        HttpSession session = request.getSession();
        long currentPage;  //当前页
        long Allpage;//总页数


        if (tempPage == null && jumpPage == null) { //初始当前页
            currentPage = 1;
            session.setAttribute("keyword", "");

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
        // 分页多条件缓存
        if (keyWord != null)
            session.setAttribute("keyword", keyWord);

        keyWord = (String) session.getAttribute("keyword");

        Allpage = this.Findcount(keyWord) / PrintNumber + 1;

//防呆
        if (currentPage > Allpage) currentPage = Allpage;
        if (currentPage <= 0) currentPage = 1;

        long StatNumber = (currentPage - 1) * PrintNumber;
        List<IBM> ibmList = ibmDao.findIBMByPage(StatNumber, PrintNumber, keyWord);

        HashMap map = new HashMap();
        map.put("ibmList", ibmList);
        map.put("Allpage", Allpage);
        map.put("currentPage", currentPage);
        map.put("keyWord", keyWord);
        return map;
    }

    @Override
    public long Findcount(String keyWord) {
        return ibmDao.finCount(keyWord);
    }

    @Override
    public void BuildQRById(long id) throws WriterException, IOException {
        IBM ibm = findByID(id);
        String filePath = "/qr/";  //后期可加时间控件区分名字
        String fileName = ibm.getName() + ibm.getSn() + "-QR.png";

        String content = "名称:  " + ibm.getName() + "\n型号:  " + ibm.getModel1() +
                "\n机型:  " + ibm.getModel2() + "\nSN:  " + ibm.getSn() +
                "\n维保时间:  " + ibm.getTime();

        int width = 60; // 图像宽度
        int height = 60; // 图像高度
        String format = "png";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        Path path = FileSystems.getDefault().getPath(filePath, fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
        System.out.println(ibm.getName() + "  输出成功.");
      //  File file = new File(filePath+fileName);
      //  file.delete();
      //  System.out.println(filePath+fileName+" 删除成功");
    }

    @Override
    public List<IBM> findAll() {

        return ibmDao.findAll();
    }

    public IBM findByID(long id) {
        return ibmDao.findIBMById(id);
    }
}
