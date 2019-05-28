package com.wu.service;

import java.io.File;
import java.util.List;

/**
 * by wyz on 2019/5/28/028.
 */
public interface ExcuteService {
    File createFile(String writeString, String localAddr, String fileName);

    List getInstallCommand();

}
