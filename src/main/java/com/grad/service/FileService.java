package com.grad.service;

import com.google.gson.JsonObject;
import com.grad.constants.DefaultVals;
import com.grad.dao.ImageMapper;
import com.grad.dao.PostMapper;
import com.grad.pojo.ImageItem;
import com.grad.ret.Status;
import com.grad.util.JsonUtil;
import com.grad.util.StringTool;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileService {
    @Resource
    ImageMapper imageMapper;
    @Resource
    PostMapper postMapper;

    public StorageClient client;
    public FileService() throws MyException, IOException {
        ClientGlobal.initByProperties("src/main/resources/fastdfs-client.properties");
        client = new StorageClient();
    }

    public String generateFileUrl(String[] s){
        if(s == null || s.length == 0) return null;
        String res = "";
        for(int i = 1; i < s.length; i ++)
            res += s[i];
        return s[0] + "/" + res;
    }

    public String storeImageV1(MultipartHttpServletRequest request) throws IOException, ServletException, MyException {
        String fileName = request.getPart("file").getSubmittedFileName();
        String postId = StringTool.parsePostId(fileName);
        long imgOrder = Integer.parseInt(StringTool.parseOrder(fileName));
//        log.info("posId = " + postId + "\nimageOrder = " + imgOrder);
        String extName = fileName.substring(fileName.lastIndexOf('.') + 1);
        InputStream ins = request.getFile("file").getInputStream();

        byte[] bytes = ins.readAllBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        BufferedImage sourceImg = ImageIO.read(inputStream);
        long width = sourceImg.getWidth();   // 源图宽度
        long height = sourceImg.getHeight();   // 源图高度

        String[] fileAddr = client.upload_file(bytes, extName, null);
        String fileUrl = generateFileUrl(fileAddr);
        imageMapper.addImage(new ImageItem(1, postId, imgOrder, fileUrl, width, height));
        postMapper.setPostType(postId, DefaultVals.POST_TYPE_IMG);
        ins.close();
        inputStream.close();
        return JsonUtil.objectToJson(new Status(DefaultVals.STATUS_OK));
    }

    //上传图片、视频等，返回url，不将url存入数据库了
    public ResponseEntity<JsonObject> storeFile(MultipartHttpServletRequest request) {
        try{
            String fileName = request.getPart("file").getSubmittedFileName();
            String extName = fileName.substring(fileName.lastIndexOf('.') + 1);
            InputStream ins = request.getFile("file").getInputStream();
            byte[] bytes = ins.readAllBytes();
            String[] fileAddr = client.upload_file(bytes, extName, null);
            String fileUrl = generateFileUrl(fileAddr);
            ins.close();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("fileUrl", DefaultVals.FILE_SERVER_URL + fileUrl);
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }


}
