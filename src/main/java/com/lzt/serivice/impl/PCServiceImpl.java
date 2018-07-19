package com.lzt.serivice.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.lzt.dao.PcDao;
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
    public HashMap findPCByPage(String tempPage, String jumpPage, String type, String keyWord, String sort, String state,HttpServletRequest request) {

        HttpSession session = request.getSession();
        long currentPage;  //当前页
        long Allpage;//总页数
        String sortType;  //排序类型

        if (tempPage == null && jumpPage == null) { //初始当前页 有可能为查找+排序
            currentPage = 1;
            if (sort == null) {//最初始
                session.setAttribute("keyword", "");
                session.setAttribute("sort", "username");
                session.setAttribute("sortType", "ASC");

                if (state==null)//点查询时，或初始化
                {
                    session.setAttribute("state","");
                }
            }
        } else if (tempPage != null) {//获取当前页 +1或-1
            int page = Integer.parseInt(tempPage);
            if (type.equals("next"))
                currentPage = page + 1;
            else
                currentPage = page - 1;
        } else {  //跳转页面
            int page = Integer.parseInt(jumpPage);
            currentPage = page;
        }
        // 分页多条件缓存
        if (keyWord != null)
            session.setAttribute("keyword", keyWord);
        if (state!=null) {
            session.setAttribute("state", state);
        }
        if (sort != null) {
            if (sort.equals(session.getAttribute("sort")))  //重复选择排序方式
                session.setAttribute("sortType", "DESC");//倒叙
            else {
                session.setAttribute("sortType", "ASC");
                session.setAttribute("sort", sort);
            }
        }
        keyWord = (String) session.getAttribute("keyword");
        sort = (String) session.getAttribute("sort");
        sortType = (String) session.getAttribute("sortType");
        state=(String)session.getAttribute("state");
        Allpage = this.Findcount(keyWord,state) / PrintNumber + 1;

//防呆
        if (currentPage > Allpage) currentPage = Allpage;
        if (currentPage <= 0) currentPage = 1;

        long StatNumber = (currentPage - 1) * PrintNumber;
        List<PC> pcList = pcdao.findPCByPage(StatNumber, PrintNumber, keyWord, sort, sortType,state);

// 取出所有ModelFloor
        List<ModelFloor> mflist=modelFloorService.findAll();

        HashMap map = new HashMap();
        map.put("mflist",mflist);
        map.put("pcList", pcList);
        map.put("Allpage", Allpage);
        map.put("currentPage", currentPage);
        map.put("keyWord", keyWord);
        return map;
    }

    @Override
    public long Findcount(String keyword,String state) {
        return pcdao.Findcount(keyword,state);
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
    public void BuildQRById(long id) throws WriterException,IOException {
        PC pc = findByID(id);
        String filePath = "D://二维码/";  //后期可加时间控件区分名字
        String fileName = pc.getPCName()+"-QR.png";

        String content ="PC名:  "+pc.getPCName()+"\n楼层:  "+pc.getFloor()+
                "\n型号:  "+pc.getModel()+"\n姓名:  "+pc.getUsername()+
                "\n资产编号:  "+pc.getAssetNumber()+"\nMAC:  "+pc.getMAC()+
                "\nSN:  "+pc.getSN()+"\n工号:  "+pc.getUserNumber();

        int width = 200; // 图像宽度
        int height = 200; // 图像高度
        String format = "png";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
        Path path = FileSystems.getDefault().getPath(filePath, fileName);
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
        System.out.println(pc.getPCName()+"  输出成功.");

    }


}
