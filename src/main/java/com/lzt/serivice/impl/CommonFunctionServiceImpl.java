package com.lzt.serivice.impl;

import com.lzt.serivice.CommonFunctionService;
import org.apache.commons.net.ftp.FTPClient;



/**
 * Created by Enzo Cotter on 2018-8-2.
 */
public class CommonFunctionServiceImpl implements CommonFunctionService {
    @Override
    public void downLoad(String url, int port,String username, String password, String remotePath,String fileName,String localPath) {

        FTPClient ftp=new FTPClient();
    }
}
