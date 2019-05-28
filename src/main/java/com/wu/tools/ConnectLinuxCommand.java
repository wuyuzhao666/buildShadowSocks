package com.wu.tools;/**
 * by wyz on 2019/5/25/025.
 */

import ch.ethz.ssh2.*;
import com.wu.model.RemoteConnect;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * @program: buildss
 *
 * @description:
 *
 * @author: Mr.Wu
 *
 * @create: 2019-05-25 14:32
 **/
@Component
@Data
public class ConnectLinuxCommand {

    private static final Logger logger = Logger.getLogger(ConnectLinuxCommand.class);

    private static String DEFAULTCHARTSET = "UTF-8";
//    private static Connection conn;

//    private static class InstanceHolder{
//        final static ConnectLinuxCommand INSTANSE = new ConnectLinuxCommand();
//    }
//
//    public static ConnectLinuxCommand getInstance(){
//        return InstanceHolder.INSTANSE;
//    }

    /**
     * @Author Mr.Wu
     * @Description //通过密钥获取远程服务器的连接，并返回此连接
     * @Date  2019/5/26/026
     * @Param
     * @return
     **/
//    public Connection getConn(){
//        return conn;
//    }
    public Connection loginByFileKey(RemoteConnect remoteConnect, File keyFile, String keyfilePass) {
        boolean flag = false;
        // 输入密钥所在路径
        Connection conn = null;
        try {
            conn = new Connection(remoteConnect.getIp());
            conn.connect();
            // 登录认证
            flag = conn.authenticateWithPublicKey(remoteConnect.getUserName(), keyFile, keyfilePass);
            if (flag) {
                logger.info("认证成功！");
                System.out.println("认证成功！");
            } else {
                logger.info("认证失败！");
                conn.close();
                return null;
//                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * @Author Mr.Wu
     * @Description //执行命令,并返回命令结果
     * @Date  2019/5/26/026
     * @Param
     * @return
     **/

    public String execute(String cmd,Connection conn){
        String result = "";
        try {
            Session session = conn.openSession();// 打开一个会话
            session.execCommand(cmd);// 执行命令
            result = processStdout(session.getStdout(), DEFAULTCHARTSET);
            // 如果为得到标准输出为空，说明脚本执行出错了
            if (StringUtils.isBlank(result)) {
                result = processStdout(session.getStderr(), DEFAULTCHARTSET);
            }
//            conn.close();                                                                                                                                                                                    l,l                                        ,
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    /**
     * @Author Mr.Wu
     * @Description //通过密码登陆
     * @Date  2019/5/26/026
     * @Param
     * @return
     **/

    public Connection login(RemoteConnect remoteConnect) {
        boolean flag = false;
        Connection conn = null;
        try {
            conn = new Connection(remoteConnect.getIp());
            conn.connect();// 连接
            flag = conn.authenticateWithPassword(remoteConnect.getUserName(), remoteConnect.getPassword());// 认证
            if (flag) {
                logger.info("认证成功！");
            } else {
                logger.info("认证失败！");
                conn.close();
                return null;

            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return conn;
    }


    /**
     * @Author Mr.Wu
     * @Description //获取命令的执行结果
     * @Date  2019/5/26/026
     * @Param
     * @return
     **/

    public String processStdout(InputStream in, String charset){
        InputStream stdout = new StreamGobbler(in);
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset));
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line + "\n");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }


    /**
     * @Author Mr.Wu
     * @Description //将文件复制到远程服务器
     * @Date  2019/5/26/026
     * @Param
     * @return
     **/
    public void scpPut(Connection conn,String remoteDir,File localFile){
        SCPClient scpClient = new SCPClient(conn);
        try {
            SCPOutputStream put = scpClient.put(localFile.getName(), localFile.length(), remoteDir, null);
            byte[] bytes = new byte[2048];
            FileInputStream fileInputStream = new FileInputStream(localFile);
            int i;
            while ((i = fileInputStream.read(bytes)) != -1){
                put.write(bytes,0 ,i);
            }
            put.flush();
            fileInputStream.close();
            put.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author Mr.Wu
     * @Description //向远程服务器下载文件
     * @Date  2019/5/26/026
     * @Param
     * @return
     **/

    public void scpGet(Connection conn,String localDir,String remoteFileDir,String remoteFile){
        SCPClient scpClient = new SCPClient(conn);
        try {
            SCPInputStream get = scpClient.get(remoteFile + File.separator + remoteFile);
            byte[] bytes = new byte[2048];
            File file = new File(localDir);
            if (!file.exists()){
                file.mkdir();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(localDir + File.separator + remoteFile);
            int i;
            while ((i = get.read(bytes)) != -1){
                fileOutputStream.write(bytes ,0 , i);
            }
            fileOutputStream.flush();
            get.close();
//            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown(Connection conn){
        conn.close();
    }

}
