package com.baizhi.entity;

import lombok.Data;

@Data
public class Menu {
    private int id;
    private String title;
    private String iconcls;
    private int parent_id;
    private String url;
}
