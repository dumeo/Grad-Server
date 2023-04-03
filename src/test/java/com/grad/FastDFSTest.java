package com.grad;

import com.grad.dao.ImageMapper;
import jakarta.annotation.Resource;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class FastDFSTest {

    StorageClient client;
    @Resource
    ImageMapper imageMapper;

    @Test
    void contextLoads() throws IOException {

//        try{
//            //加载配置文件
//            ClientGlobal.initByProperties("src/main/resources/fastdfs-client.properties");
//            //查看配置是否加载成功
//            System.out.println("ClientGlobal.configInfo(): " + ClientGlobal.configInfo());
//            System.out.println(ClientGlobal.getG_tracker_group().getTrackerServer(0).getInetSocketAddress().getAddress());
//            //新建客户端
//            client = new StorageClient();
//            //上传文件
//            String[] s = client.upload_file("D:\\icon\\android_e4\\appicon.png", null, null);
//
//            if(s.length != 0){
//                //输出返回的完整路径
//                System.out.println( s[0] + "/" + s[1]);
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }




    }
}
