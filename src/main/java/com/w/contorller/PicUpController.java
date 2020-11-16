package com.w.contorller;

//import com.w.utils.JSONUtils;


import com.jcraft.jsch.*;
import com.w.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

/**
 * @Author:wu
 * @Date :create in 15:48 2020/3/31
 */
@Slf4j
@Api(value = "PicUpController", tags = "图片上传模块")
@RestController
public class PicUpController {
    @Value("${ftphost}")
    private String host;
    @Value("${ftpport}")
    private Integer port;
    @Value("${ftpusername}")
    private String username;
    @Value("${ftppassword}")
    private String password;
    @Value("${ftpinputUrl}")
    private String inputUrl;
    @Value("${ftppostUrl}")
    private String postUrl;


    @ApiOperation("图片上传")
    @PostMapping("/api/image/upload")
    public Result uptolinux(HttpServletRequest request, @RequestParam MultipartFile file) throws IOException {
        Result result = new Result();
        request.setCharacterEncoding("UTF-8");
        log.info("执行图片上传");

        if (file == null) {
            result.setSuccess(false);
            result.setMessage("上传错误");
        } else {
            JSch jsch = new JSch();
            try {
                jsch.getSession(username, host, port);
                Session sshSession = jsch.getSession(username, host, port);
                log.info("chuangjiansession");
                sshSession.setPassword(password);
                Properties sshConfig = new Properties();
                sshConfig.put("StrictHostKeyChecking", "no");
                sshSession.setConfig(sshConfig);
                sshSession.connect();

                log.info("连接Session……");
                Channel channel = sshSession.openChannel("sftp");
                channel.connect();
                if (channel.isConnected() == true) {
                    log.info("连接Channel……");
                    ChannelSftp sftp = (ChannelSftp) channel;

                    sftp.cd(inputUrl);//上传时接文件的服务器的存放目录

                    String fileNames = file.getOriginalFilename();
                    String suffixName = fileNames.substring(fileNames.lastIndexOf("."));  // 后缀名
                    log.info("获取后缀名称：", suffixName);
                    String fileName = null;
                    if (StringUtils.isEmpty(suffixName)) {
                        fileName = UUID.randomUUID() + ".jpg";
                    } else {
                        fileName = UUID.randomUUID() + suffixName;
                    }
                    log.info("上传文件名：", fileName.toString());
                    InputStream input = file.getInputStream();
                    sftp.put(input, fileName, ChannelSftp.OVERWRITE);//有重名文件覆盖
                    log.info("tianjia成功");
                    sshSession.disconnect();
                    input.close();

                    String path = postUrl + fileName;
                    log.info("请求路径打印：", path.toString());
                    result.setMessage("上传成功");
                    result.setSuccess(true);
                    result.setData(path);
                } else {
                    result.setSuccess(false);
                    result.setMessage("sftp连接失败");
                }
            } catch (JSchException | SftpException | IOException e) {
                e.printStackTrace();
            }
        }
        return result;

    }
 /*   @PostMapping(value = "/api/fileUpload")
    public Result fileUpload(@RequestParam(value = "file") MultipartFile file) {

        Result result = new Result();
        if (file.isEmpty()) {
            log.info("图片为空");
            result.setSuccess(false);
            result.setCode(ResultCode.PARAM_PARAMETER_ERROR.getCode());
            result.setMessage("图片为空");
            return result;
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "D://pic//"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = filePath + fileName;

        result.setSuccess(true);
        result.setMessage("上传成功");
        result.setData(filename);

        return result;
    }*/





   /* @Autowired
    StoreService storeService;

    @PostMapping(value = "/file/upload")
    public Object upload(@RequestParam("file") MultipartFile file){
        return storeService.upload(file);
    }

    @PostMapping(value = "/file/download")
    public Object download(@RequestParam String key, HttpServletResponse response){
        storeService.download(key,response);
        return key+"图片返回成功";
    }
*/

}


