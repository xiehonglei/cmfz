package com.baizhi.service;

import com.baizhi.entity.Manager;

public interface ManagerService {
    public Manager queryOne(String name, String password);
}
