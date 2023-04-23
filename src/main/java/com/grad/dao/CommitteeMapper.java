package com.grad.dao;

import com.grad.ret.committee.NoteItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommitteeMapper {
    void addNote(NoteItem noteItem);

}
