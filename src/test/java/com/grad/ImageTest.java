package com.grad;

import com.grad.dao.ImageMapper;
import com.grad.pojo.ImageItem;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@SpringBootTest
public class ImageTest {
    @Resource
    ImageMapper imageMapper;

    @Test
    public void test1() throws IOException {
//        List<ImageItem> imageItems = imageMapper.getImgUrls();
//        for(ImageItem imageItem : imageItems){
//
//        }
    }


}
