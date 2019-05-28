package com.wu.controller;/**
 * by wyz on 2019/5/25/025.
 */

import ch.ethz.ssh2.Connection;
import com.wu.model.RemoteConnect;
import com.wu.model.Result;
import com.wu.service.ExcuteService;
import com.wu.tools.ConnectLinuxCommand;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @program: buildss
 *
 * @description:
 *
 * @author: Mr.Wu
 *
 * @create: 2019-05-25 10:30
 **/
@Controller
@RequestMapping("/ssh")
@Data
public class SSHController {

    @Value("${LOCAL_ADDR}")
    private String LOCAL_ADDR;
    @Value("${REMOTE_ADDR}")
    private String REMOTE_ADDR;
    @Value("${SS_FILE_NAME}")
    private String SS_FILE_NAME;
    @Value("${BOOT_SHELL_FILE_NAME}")
    private String BOOT_SHELL_FILE_NAME;
    @Value("${BOOT_SHELL_REMOTE_DIR}")
    private String BOOT_SHELL_REMOTE_DIR;


    @Autowired
    private ConnectLinuxCommand connectLinuxCommand;
    @Autowired
    private ExcuteService excuteService;



    @RequestMapping(value = "/test",method = RequestMethod.POST)
    @ResponseBody
    public String connectTest(HttpServletRequest request,
                       String ip,String userName,String passWord,
                       @RequestParam(value = "file",required = false) MultipartFile file){
        Connection conn;
        RemoteConnect remoteConnect = new RemoteConnect(ip, userName,passWord);
        if (file != null){
            String realPath = request.getServletContext().getRealPath("/cacheAuth");
            System.out.println(realPath);
            String originalFilename = file.getOriginalFilename();
            File file1 = new File(realPath, originalFilename);
            if(!file1.getParentFile().exists()){
                file1.getParentFile().mkdir();
            }
            System.out.println(ip + userName );

            try {
                file.transferTo(file1);
                System.out.println(connectLinuxCommand.hashCode());
//                conn = connectLinuxCommand.login(remoteConnect);
                conn = connectLinuxCommand.loginByFileKey(remoteConnect, file1, null);
                if (conn==null){

                    return "连接出错，请重试";
                }
            } catch (IOException e) {
                String message = e.getMessage();
                e.printStackTrace();

                return "fail:" + message;
            }
        }else {
            conn = connectLinuxCommand.login(remoteConnect);
            if (conn==null){

                return "连接出错，请重试";
            }
        }
        connectLinuxCommand.shutdown(conn);
        return "success";
    }
    @RequestMapping("/submit")
    @ResponseBody
    public String submit(HttpServletRequest request,
                         String ip,String userName,String password,String port,String loginPassword,
                         @RequestParam(value = "file",required = false) MultipartFile file){
            Connection conn;
        System.out.println(port);
        System.out.println(loginPassword);
        String s = "";
//        if(conn == null){
        RemoteConnect remoteConnect = new RemoteConnect(ip, userName, password);
        if (file != null){
                String realPath = request.getServletContext().getRealPath("/cache");
                System.out.println(realPath);
                String originalFilename = file.getOriginalFilename();
                File file1 = new File(realPath, originalFilename);
                if(!file1.getParentFile().exists()){
                    file1.getParentFile().mkdir();
                }
                System.out.println(ip + userName + password);

                try {
                    file.transferTo(file1);
                    System.out.println(connectLinuxCommand.hashCode());
                    conn = connectLinuxCommand.loginByFileKey(remoteConnect, file1, null);
//                    Boolean aBoolean = connectLinuxCommand.login(remoteConnect);
                    if(conn == null){
                        return "登陆失败";
                    }

                    s = excuteAll(conn, port, loginPassword);
                } catch (IOException e) {
                    String message = e.getMessage();
                    e.printStackTrace();
                    return "fail:" + message;
                }
                return "s";
            }else {
            conn = connectLinuxCommand.login(remoteConnect);
            if (conn==null){

                return "连接出错，请重试";
            }
            s = excuteAll(conn, port, loginPassword);

        }

        return "s";
    }
    private String excuteAll(Connection conn,String port,String loginPassword){
        List installCommand = excuteService.getInstallCommand();
        for (Object command:
                installCommand) {
            connectLinuxCommand.execute((String) command, conn);
        }

        //本地新建文件
        String ssjson = "{\"server\" : \"0.0.0.0\"," +"\n" +
                "\"server_port\" : "+ port +"," +"\n" +
                "\"local_address\" : \"127.0.0.1\"," +"\n" +
                "\"local_port\":1080," +"\n" +
                "\"password\":\"" +loginPassword + "\"," +"\n" +
                "\"timeout\":300," +"\n" +
                "\"method\":\"rc4-md5\"," +"\n" +
                "\"fast_open\" : false " + "\n" +
                "}";
        //创建shadowsocks.json
        File shadowsocksJson = excuteService.createFile(ssjson, LOCAL_ADDR, SS_FILE_NAME);
        connectLinuxCommand.scpPut(conn,REMOTE_ADDR,shadowsocksJson);
        //使其自启动?各系统版本不同，自启动方式不同
        String which_ssserver = connectLinuxCommand.execute("which ssserver", conn);
        String execute = connectLinuxCommand.execute(which_ssserver + " -c " + REMOTE_ADDR + File.separator + SS_FILE_NAME + " -d start", conn);
//        String bootShell = "[Unit]\n" +
//                "Description=Shadowsocks\n" +
//                "\n" +
//                "[Service]\n" +
//                "TimeoutStartSec=0\n" +
//                "ExecStart="+ which_ssserver + " -c " + REMOTE_ADDR + File.separator + SS_FILE_NAME + "\n" +
//                "\n" +
//                "[Install]\n" +
//                "WantedBy=multi-user.target";
//        File bootShellFile = excuteService.createFile(bootShell, REMOTE_ADDR, BOOT_SHELL_FILE_NAME);
//        connectLinuxCommand.scpPut(conn,BOOT_SHELL_REMOTE_DIR,bootShellFile);

        connectLinuxCommand.shutdown(conn);
        return execute;
    }
}
