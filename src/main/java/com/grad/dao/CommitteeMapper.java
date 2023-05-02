package com.grad.dao;

import com.grad.ret.committee.NoteItem;
import com.grad.ret.communitynews.CommunityNews;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommitteeMapper {
    void addNote(NoteItem noteItem);
    void addNews(CommunityNews communityNews);
    void increaseNewsViewCnt(@Param("newsId")String newsId);
}
