package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public Map getWeeks();

    public Map getMaps();

    public Map getAll(int page, int rows);

    public List<User> getUser();

    public void add(List<User> userList);
}
