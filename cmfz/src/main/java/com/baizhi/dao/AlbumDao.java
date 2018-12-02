package com.baizhi.dao;


import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDao {
    public List<Album> getAll(@Param("start") int start, @Param("pagesize") int pagesize);

    public int getCount();

    public void add(Album album);
}
