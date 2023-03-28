package com.grad;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class FastDFSTest {

    StorageClient client;

    @Test
    void contextLoads() {

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

//        ClassA classA = new ClassA(new ArrayList<>());
//        classA.getListA().add(1);
//        ClassB classB = new ClassB(classA.getListA());
//        classB.getListB().set(0, 2);
//        System.out.println(classA.getListA().get(0));

    }
}
