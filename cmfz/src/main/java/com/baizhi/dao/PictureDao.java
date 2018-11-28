package com.baizhi.dao;

import com.baizhi.entity.Picture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PictureDao {
    public List<Picture> getAll(@Param("start") int start, @Param("pagesize") int pagesize);

    public int getCount();

    public void delete(int id);

    public void add(Picture picture);
}
