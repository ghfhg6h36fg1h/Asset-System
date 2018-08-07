package com.lzt.serivice;

import com.google.zxing.WriterException;
import com.lzt.entity.PC;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Enzo Cotter on 2018-7-11.
 */
public interface CommonFunctionService {

       void downLoad(String url, int port,String username, String password, String remotePath,String fileName,String localPath);

}
