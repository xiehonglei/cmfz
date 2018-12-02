package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.Map;

public interface AlbumServcie {
    public Map getAll(int page, int rows);

    public void add(Album album);
}
