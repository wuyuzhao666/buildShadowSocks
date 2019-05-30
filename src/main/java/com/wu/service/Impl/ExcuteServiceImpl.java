package com.wu.service.Impl;/**
 * by wyz on 2019/5/28/028.
 */

import com.wu.service.ExcuteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: buildss
 *
 * @description:
 *
 * @author: Mr.Wu
 *
 * @create: 2019-05-28 09:01
 **/
@Service
public class ExcuteServiceImpl implements ExcuteService {

    @Value("${INSTALL_SS_FIREST}")
    private String INSTALL_SS_FIREST;

    @Value("${INSTALL_SS_SECOND}")
    private String INSTALL_SS_SECOND;

    @Value("${INSTALL_SS_THIRD}")
    private String INSTALL_SS_THIRD;

    @Value("${INSTALL_SS_FORTH}")
    private String INSTALL_SS_FORTH;

    @Value("${INSTALL_SS_F2}")
    private String INSTALL_SS_F2;



    @Override
    public File createFile(String writeString, String localAddr, String fileName) {
        File shadowsocksJson = new File(localAddr, fileName);
        if (!shadowsocksJson.exists()){
            try {
                shadowsocksJson.createNewFile();
                //读取文件
                FileOutputStream fileOutputStream = new FileOutputStream(localAddr + File.separator + fileName);
                fileOutputStream.write(writeString.getBytes());
                fileOutputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return shadowsocksJson;

    }

    @Override
    public List getInstallCommand() {
        List<String> list = new ArrayList();

        list.add(INSTALL_SS_FIREST);
        list.add(INSTALL_SS_SECOND);
        list.add(INSTALL_SS_THIRD);
        list.add(INSTALL_SS_FORTH);

        return list;
    }


}
