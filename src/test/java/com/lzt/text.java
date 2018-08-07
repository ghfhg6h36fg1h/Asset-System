package com.lzt;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Enzo Cotter on 2018-7-13.
 */
public class text {

        public static void main(String[] args) throws WriterException, IOException {
            String filePath = "D://二维码/";
            String fileName = "PC-QR.png";
           // JSONObject json = new JSONObject();
          //  json.put("name", "梁棁桐"+"\n\r");
          //  json.put("PCname", "PC1008<br/>");
          //  json.put("Model","MT6500<br/>");
            String content ="名字:"+"pc1300\n"+"PC名";
            //String content = json.toJSONString();// 内容
            int width = 200; // 图像宽度
            int height = 200; // 图像高度
            String format = "png";// 图像类型
            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
            Path path = FileSystems.getDefault().getPath(filePath, fileName);
            MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
            System.out.println("输出成功.");
        }
    }
