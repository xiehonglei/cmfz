package com.baizhi.entity;

import lombok.Data;

import java.util.List;

@Data
public class Menu {
    private int id;
    private String title;
    private String iconcls;
    private int parent_id;
    private String url;
    private List<Menu> mlist;
}
