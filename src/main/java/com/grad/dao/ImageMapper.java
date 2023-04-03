package com.grad.dao;

import com.grad.pojo.ImageItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImageMapper {
    public void addImage(ImageItem image);
    public List<ImageItem> selectImagesByPostId(@Param("postId") String postId);
    public List<ImageItem> getImgUrls();
    public void updateImgSize(@Param("imgId")long imgId, @Param("width") long width, @Param("height") long height);
}
