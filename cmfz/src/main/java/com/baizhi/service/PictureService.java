package com.baizhi.service;

import com.baizhi.entity.Picture;

import java.util.Map;

public interface PictureService {
    public Map getAll(int page, int rows);

    public void delete(int id);

    public void add(Picture picture);
}
