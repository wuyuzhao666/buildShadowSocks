package com.wu.model;/**
 * by wyz on 2019/5/25/025.
 */

import lombok.Data;

/**
 * @program: sshtest
 *
 * @description:
 *
 * @author: Mr.Wu
 *
 * @create: 2019-05-25 08:57
 **/
@Data
public class RemoteConnect {

    private String ip;
    private String userName;
    private String password;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RemoteConnect(String ip, String userName, String password) {
        this.ip = ip;
        this.userName = userName;
        this.password = password;
    }
}
